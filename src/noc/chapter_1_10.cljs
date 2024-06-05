(ns noc.chapter-1-10
  (:require
   [thi.ng.math.core :as tm]
   [thi.ng.geom.vector :as v]
   [quil.core :as q]))

(def size [640 240])

(defn init-state [{:keys [width height] :as state}]
  {:mover {:position  (v/vec2 (quot width 2) (quot height 2))
           :velocity (v/vec2 0 0)
           :top-speed 5}})

(defn setup! [{:keys [width height]}]
  (q/background 255))

(defn tick-mover [mouse-x mouse-y {:keys [position top-speed velocity] :as mover}]
  (let [mouse (v/vec2 mouse-x mouse-y)
        direction (tm/- mouse position)
        acceleration (tm/* (tm/normalize direction) 0.2)
        new-vel (tm/limit (tm/+ velocity acceleration) top-speed)]
    (-> mover
        (assoc :velocity new-vel)
        (update :position #(tm/+ % new-vel)))))

(defn tick [{:keys [mouse-x mouse-y] :as state}]
  (-> state
      (update :mover #(tick-mover mouse-x mouse-y %))))

(defn draw-mover [{:keys [position]}]
  (q/stroke 0)
  (q/stroke-weight 2)
  (q/fill 127)
  (q/ellipse (v/x position) (v/y position) 48 48))

(defn draw! [{:keys [mover]}]
  (q/background 255)
  (draw-mover mover))
