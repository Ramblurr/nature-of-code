(ns noc.chapter-1-3e
  (:require
   [thi.ng.math.core :as tm]
   [thi.ng.geom.vector :as v]
   [quil.core :as q]))

(def size [640 300])

(defn init-state [{:keys [width height] :as state}]
  {:angle 0.0
   :pos (v/vec3 0 0 0)
   :vel (v/vec3 2 2.5 3)
   :thresh 75})

(defn setup! [{:keys [width height]}]
  (q/background 255)
  (q/no-fill))

(defn tick [{:keys [angle pos vel thresh]}]
  (let [[x y z] (tm/+ pos vel)
        [vx vy vz] vel
        angle (+ angle 0.05)
        vx (if (> (abs x) thresh) (* vx -1) vx)
        vy (if (> (abs y) thresh) (* vy -1) vy)
        vz (if (> (abs z) thresh) (* vz -1) vz)]
    {:pos (tm/+ pos vel)
     :thresh thresh
     :vel (v/vec3 vx vy vz)
     :angle angle}))

(defn draw! [{:keys [pos angle]}]
  (let [[x y z] pos]
    (q/background 255)
    (q/directional-light 255 255 255 1 1 -1)
    (q/stroke 0)
    (q/box 200)
    (q/push-matrix)
    (q/translate x y z)
    (q/rotate-x angle)
    (q/rotate-y angle)
    (q/stroke 0)
    (q/sphere 30)
    (q/pop-matrix)))
