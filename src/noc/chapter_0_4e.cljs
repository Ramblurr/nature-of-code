(ns noc.chapter-0-4e
  (:require
   [quil.core :as q]))

(def size [640 240])

(defn init-state [_]
  {:ui {:stdev {:type :slider
                :min 5 :max 120
                :value 10}}})

(defn setup! [_]
  (q/color-mode :hsl 360)
  (q/background 360))

(defn tick [s]
  s)

(defn rand-gaus [mean stdev] (+ mean (* stdev (q/random-gaussian))))

(defn draw! [{:keys [width height ui]}]
  (let [stdev (get-in ui [:stdev :value] 0)
        x (rand-gaus (quot width 2) stdev)
        y (rand-gaus (quot height 2) stdev)]
    (q/no-stroke)
    (q/fill (rand-gaus 0 stdev) 200 200 200)
    (q/ellipse x y 16 16)))
