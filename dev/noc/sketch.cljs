(ns noc.sketch
  (:require [quil.core :as q]
            [quil.sketch :as ap :include-macros true]
            [noc.chapter-0-1 :as c0.1]
            [noc.chapter-0-2 :as c0.2]
            [noc.dots :as dots]))

(def sketches {:dots dots/sketch
               :walker c0.1/sketch
               :rand-dist c0.2/sketch})

(defn load-sketch [s]
  (get sketches s))

(def default-state {:paused? false
                    :default-frame-rate 60})

(defn setup-state [init-state]
  (merge default-state init-state))

(defn reset [sketch*]
  (let [{:keys [applet opts]} @sketch*
        {:keys [setup]} opts]
    (ap/with-sketch applet
      (let [state* (q/state :state)
            new-state (setup-state (setup))]
        (reset! state* new-state)))))

(defn toggle-pause [sketch*]
  (let [{:keys [applet]} @sketch*]
    (ap/with-sketch applet
      (let [state* (q/state :state)
            state @state*]
        (if (= 0 (.frameRate applet))
          (do
            (q/frame-rate (:default-frame-rate state))
            (swap! state* assoc :paused? false))
          (do
            (q/frame-rate 0)
            (swap! state* assoc :paused? true)))))))

(defn setup-wrapper [setup]
  (let [state (setup-state (setup))]
    (q/set-state! :state (atom state) :init-state state)))

(defn draw-wrapper [tick draw]
  (let [state* (q/state :state)]
    (reset! state* (tick @state*))
    (draw @state*)))

(defn show-sketch [{:keys [setup tick draw] :as opts} el]
  {:applet (apply q/sketch (apply concat
                                  (-> opts
                                      (assoc :host el)
                                      (assoc :setup (partial setup-wrapper setup))
                                      (assoc :draw (partial draw-wrapper tick draw)))))
   :opts opts
   :el el})
