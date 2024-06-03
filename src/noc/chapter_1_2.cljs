(ns noc.chapter-1-2
  (:require
   [thi.ng.math.core :as tm]
   [thi.ng.geom.vector :as v]
   [quil.core :as q]))

(def size [640 240])

(defn init-state [{:keys [width height] :as state}]
  {:position (v/vec2 100 100)
   :velocity (v/vec2 2.5 2)})

(defn setup! [{:keys [width height]}]
  (q/background 255))

(defn tick [{:keys [position velocity width height] :as state}]
  (let [[x y] (tm/+ position velocity)
        [vx vy] velocity
        vx (if (or (>= x width) (< x 0)) (* vx -1) vx)
        vy (if (or (>= y height) (< y 0)) (* vy -1) vy)]
    (assoc state :position (v/vec2 x y)
           :velocity (v/vec2 vx vy))))

(defn draw! [state]
  (let [[x y] (:position state)]
    (q/background 255)
    (q/stroke 0)
    (q/fill 127)
    (q/stroke-weight 2)
    (q/ellipse x y  48 48)))
