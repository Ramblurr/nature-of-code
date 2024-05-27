(ns noc.custom
  (:require [quil.core :as q]
            [noc.dots :as dots]))

(def sketches {:dots dots/sketch})

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

(defn sketch [opts]
  (apply q/sketch (apply concat opts)))

(defn load-sketch [s el]
  (let [opts (get sketches s)]
    (-> opts
        (assoc :host el)
        (sketch))))

(defn has-sketch? [s]
  (contains? sketches s))
