(ns noc.chapter-0-4e
  (:require
   [goog.string :as gstring]
   [goog.string.format]
   [quil.core :as q]))

(def size [640 240])

(defn init-state [_]
  {:ui {:stdev {:type :slider
                :min 5 :max 120
                :value 50}}})

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
    (q/ellipse x y 16 16)
    (q/no-stroke)
    (q/fill 360)
    (q/rect 0 0 100 35)
    (q/fill 0)
    (q/text (gstring/format "stdev: %.2f" stdev) 10 20)))
