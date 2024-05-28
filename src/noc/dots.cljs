(ns noc.dots
  (:require [quil.core :as q]))

(defn setup []
  (q/frame-rate 1)
  (q/background 200))

(defn tick [_])

(defn draw []
  (q/stroke (q/random 255))
  (q/stroke-weight (q/random 10))
  (q/fill (q/random 255))
  (let [diam (q/random 100)
        x    (q/random (q/width))
        y    (q/random (q/height))]
    (q/ellipse x y diam diam)))

(def sketch
  {:size [640 480]
   :setup setup
   :tick tick
   :draw draw})
