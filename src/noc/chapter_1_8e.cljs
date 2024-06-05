(ns noc.chapter-1-8e
  (:require
   [thi.ng.geom.rect :as r]
   [thi.ng.math.core :as tm]
   [thi.ng.geom.core :as g]
   [thi.ng.geom.vector :as v]
   [quil.core :as q]))

(def size [640 240])

(defn init-state [{:keys [width height] :as state}]
  {:mover {:position  (v/vec2 (quot width 2) (quot height 2))
           :velocity (v/vec2 0 0)
           :top-speed 5}})

(defn setup! [{:keys [width height]}]
  (q/background 255))

(defn tick-mover [canvas-rect mouse {:keys [position top-speed velocity] :as mover}]
  (if (g/contains-point? canvas-rect mouse)
    (let [direction (tm/- mouse position)
          dist (g/dist position mouse)
          acceleration (tm/* (tm/normalize direction) (/ 2 dist))
          new-vel (tm/limit (tm/+ velocity acceleration) top-speed)]
      (-> mover
          (assoc :velocity new-vel)
          (update :position #(tm/+ % new-vel))))
    (-> mover
        (assoc :velocity (v/vec2 0 0)))))

(defn tick [{:keys [width height mouse-x mouse-y] :as state}]
  (-> state
      (update :mover #(tick-mover (r/rect 0 0 width height) (v/vec2 mouse-x mouse-y) %))))

(defn draw-mover [{:keys [position]}]
  (q/stroke 0)
  (q/stroke-weight 2)
  (q/fill 127)
  (q/ellipse (v/x position) (v/y position) 48 48))

(defn draw! [{:keys [mover]}]
  (q/background 255)
  (draw-mover mover))
