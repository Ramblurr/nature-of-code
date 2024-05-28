(ns noc.sketch
  (:require-macros [noc.sketch :refer [sketch->]])
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [quil.sketch :as ap :include-macros true]
            [noc.chapter-0-1 :as c0.1]
            [noc.chapter-0-2 :as c0.2]
            [noc.chapter-0-3 :as c0.3]
            [noc.chapter-0-3e :as c0.3e]))

(def sketches {:walker (sketch-> c0.1)
               :rand-dist (sketch-> c0.2)
               :walker-right (sketch-> c0.3)
               :walker-dynamic (sketch-> c0.3e)})

(defn load-sketch [s]
  (when-let [sk (get sketches s)]
    (assoc sk :sketch-name s)))

(defn time-now!
  "Return the current time in milliseconds, must be monotonically increasing."
  []
  (-> js/window (.-performance) (.now)))

(defn tick-time
  "Updates the time state. Calculates fps, delta time, etc."
  [{:keys [_time] :as state} now current-frame-rate]
  (let [{:keys [fps time frames start-time time-acc target-frame-rate]} _time
        frames (inc frames)
        second? (= 0 (mod frames 60))
        diff (- now time)
        diff-1  (last time-acc)
        newacc (vec (take-last (* 2 target-frame-rate) (conj time-acc diff)))
        average-fps (if second?
                      (let [average-diff (/ (reduce + newacc) (count newacc))]
                        (long (/ 1000 average-diff))) fps)]
    (assoc state :_time {;; Total number of frames since start was called.
                         :frames frames
                        ;; The number of milliseconds since setup was called.
                         :uptime (- now start-time)
                        ;; The time of the tick
                         :time now
                        ;; The number of milliseconds that passed between the previous two ticks
                         :diff diff-1
                         :frame-rate current-frame-rate
                         :target-frame-rate target-frame-rate
                         :dt (Math/floor (/ 1000.0 (- now time)))
                         :time-acc newacc
                        ;; Average fps over the last two seconds
                         :fps average-fps
                         :second? second?})))

(defn default-state [start-time]
  {:_paused? false
   :_time {:frames            0
           :uptime            0
           :start-time  start-time
           :time              0
           :target-frame-rate 60
           :time-acc          [1]
           :second?           false}})

(defn setup-state [init-state]
  (merge (default-state (time-now!)) init-state))

(defn reset [sketch*]
  (let [{:keys [applet opts init-state]} @sketch*
        {:keys [setup]} opts]
    (ap/with-sketch applet
      (let [state* (q/state-atom)]
        (reset! state* (setup-state init-state))
        (setup)))))

(defn pause [sketch*]
  (let [{:keys [applet]} @sketch*]
    (ap/with-sketch applet
      (q/frame-rate 0)
      (when-let [state* (q/state-atom)]
        (swap! state* assoc :_paused? true)))))

(defn resume [sketch*]
  (let [{:keys [applet]} @sketch*]
    (ap/with-sketch applet
      (when-let [state* (q/state-atom)]
        (q/frame-rate (-> @state* :_time :target-frame-rate))
        (swap! state* assoc :_paused? false)))))

(defn setup-wrapper [adjust-frame {:keys [target-frame-rate] :as init-state} setup]
  (adjust-frame)
  (q/frame-rate target-frame-rate)
  (setup)
  (setup-state init-state))

(defn update-inputs [state]
  (-> state
      (assoc :mouse-x (q/mouse-x)
             :mouse-y (q/mouse-y)
             :mouse-button (q/mouse-button)
             :key (q/key-as-keyword)
             :key-code (q/key-code)
             :raw-key (q/raw-key))))

(defn tick-wrapper [tick state]
  (->
   state
   (update-inputs)
   (assoc :width (q/width) :height (q/height))
   (tick-time (time-now!) (q/current-frame-rate))
   (tick)))

(defn draw-wrapper [draw state]
  (draw state))

(defn show-sketch [adjust-frame {:keys [init setup tick draw size] :as opts} el]
  (let [init-state (init {:width (first size) :height (last size)})]
    {:applet (apply q/sketch (apply concat
                                    (-> opts
                                        (assoc :middleware [m/fun-mode])
                                        (assoc :host el)
                                        (assoc :update (partial tick-wrapper tick))
                                        (assoc :setup (partial setup-wrapper (partial adjust-frame el) init-state setup))
                                        (assoc :draw (partial draw-wrapper draw)))))
     :sketch-name (:sketch-name opts)
     :init-state init-state
     :opts opts
     :el el}))
