(ns noc.sketch
  (:require-macros [noc.sketch :refer [sketch-> sketch-3d->]])
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [quil.sketch :as ap :include-macros true]
            [noc.quil.ui :as ui]
            [noc.chapter-0-1 :as c0.1]
            [noc.chapter-0-2 :as c0.2]
            [noc.chapter-0-3 :as c0.3]
            [noc.chapter-0-3e :as c0.3e]
            [noc.chapter-0-4 :as c0.4]
            [noc.chapter-0-4e :as c0.4e]
            [noc.chapter-0-5e :as c0.5e]
            [noc.chapter-0-5 :as c0.5]
            [noc.chapter-0-6e :as c0.6e]
            [noc.chapter-0-6 :as c0.6]
            [noc.chapter-0-7e :as c0.7e]
            [noc.chapter-0-7 :as c0.7]
            [noc.chapter-0-8e :as c0.8e]
            [noc.chapter-0-9e :as c0.9e]
            [noc.chapter-0-10e :as c0.10e]
            [noc.chapter-1-1 :as c1.1]
            [noc.chapter-1-2 :as c1.2]
            [noc.chapter-1-3e :as c1.3e]
            [noc.chapter-1-3 :as c1.3]
            [noc.chapter-1-4 :as c1.4]
            [noc.chapter-1-5 :as c1.5]
            [noc.chapter-1-6 :as c1.6]
            [noc.chapter-1-7 :as c1.7]
            [noc.chapter-1-8 :as c1.8]
            [noc.chapter-1-5e :as c1.5e]
            [noc.chapter-1-9 :as c1.9]
            [noc.chapter-1-6e :as c1.6e]
            [noc.chapter-1-10 :as c1.10]
            [noc.chapter-1-8e :as c1.8e]))

(def sketches
  {:walker (sketch-> c0.1)
   :rand-dist (sketch-> c0.2)
   :walker-right (sketch-> c0.3)
   :walker-dynamic (sketch-> c0.3e)
   :random-gaussian (sketch-> c0.4)
   :c0.4e (sketch-> c0.4e)
   :c0.5e (sketch-> c0.5e)
   :c0.5 (sketch-> c0.5)
   :c0.6e (sketch-> c0.6e)
   :c0.6 (sketch-> c0.6)
   :c0.7e (sketch-> c0.7e)
   :c0.7 (sketch-> c0.7)
   :c0.8e (sketch-> c0.8e)
   :c0.9e (sketch-> c0.9e)
   :c0.10e (sketch-3d-> c0.10e)
   :c1.1 (sketch-> c1.1)
   :c1.2 (sketch-> c1.2)
   :c1.3e (sketch-3d-> c1.3e)
   :c1.3 (sketch-> c1.3)
   :c1.4 (sketch-> c1.4)
   :c1.5 (sketch-> c1.5)
   :c1.6 (sketch-> c1.6)
   :c1.7 (sketch-> c1.7)
   :c1.8 (sketch-> c1.8)
   :c1.5e (sketch-> c1.5e)
   :c1.9 (sketch-> c1.9)
   :c1.6e (sketch-> c1.6e)
   :c1.10 (sketch-> c1.10)
   :c1.8e (sketch-> c1.8e)})

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

(defn setup-state [init-fn]
  (-> (default-state (time-now!))
      (merge (init-fn {:width (q/width) :height (q/height)}))
      (ui/prepare-ui)
      (assoc :width (q/width) :height (q/height))))

(defn reset [sketch*]
  (let [{:keys [applet opts]} @sketch*
        {:keys [setup init]} opts]
    (ap/with-sketch applet
      (let [state* (q/state-atom)]
        (ui/remove-all! @state*)
        (reset! state* (setup-state init))
        (setup @state*)))))

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

(defn setup-wrapper [adjust-frame init setup]
  (let [{:keys [target-frame-rate] :as init-state} (setup-state init)]
    (adjust-frame)
    (q/frame-rate target-frame-rate)
    (setup init-state)
    init-state))

(defn update-inputs [state]
  (-> state
      (assoc :mouse-x (q/mouse-x)
             :mouse-y (q/mouse-y)
             :mouse-button (q/mouse-button)
             :mouse-pressed? (q/mouse-pressed?)
             :key-pressed? (q/key-pressed?)
             :key (q/key-as-keyword)
             :key-code (q/key-code)
             :raw-key (q/raw-key))))

(defn tick-wrapper [tick state]
  (->
   state
   (update-inputs)
   (ui/update-ui)
   (assoc :width (q/width) :height (q/height))
   (tick-time (time-now!) (q/current-frame-rate))
   (tick)))

(defn draw-wrapper [draw state]
  (draw state))

(defn show-sketch [adjust-frame {:keys [init setup tick draw size] :as opts} el]
  {:applet (apply q/sketch (apply concat
                                  (-> opts
                                      (assoc :middleware [m/fun-mode])
                                      (assoc :host el)
                                      (assoc :update (partial tick-wrapper tick))
                                      (assoc :setup (partial setup-wrapper (partial adjust-frame el) init setup))
                                      (assoc :draw (partial draw-wrapper draw)))))
   :sketch-name (:sketch-name opts)
   :opts opts
   :el el})
