(ns noc.chapter-0-4
  (:require
   [quil.core :as q]))

(def size [640 240])

(defn init-state [{:keys [width height]}]
  {})

(defn setup! []
  (q/background 255))

(defn tick [s] s)

(defn draw! [_]
  (let [x (+ 320 (* 60 (q/random-gaussian)))]
    (q/no-stroke)
    (q/fill 0 10)
    (q/ellipse x 120 16 16)))
