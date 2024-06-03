(ns noc.chapter-1-1
  (:require
   [quil.core :as q]))

(def size [640 240])

(defn init-state [{:keys [width height] :as state}]
  {:x 100
   :y 100
   :xspeed 2.5
   :yspeed 2})

(defn setup! [{:keys [width height]}]
  (q/background 255))

(defn tick [{:keys [x y xspeed yspeed width height] :as state}]
  (let [x (+ x xspeed)
        y (+ y yspeed)]
    (assoc state
           :x x
           :y y
           :xspeed  (if (or (>= x width) (< x 0)) (* xspeed -1) xspeed)
           :yspeed (if (or (>= y height) (< y 0)) (* yspeed -1) yspeed))))

(defn draw! [{:keys [x y]}]
  (q/background 255)
  (q/stroke 0)
  (q/fill 127)
  (q/stroke-weight 2)
  (q/ellipse x y 48 48))
