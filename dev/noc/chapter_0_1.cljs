(ns noc.chapter-0-1
  (:require [quil.core :as q]))

(def sketch
  {:size [640 240]
   :setup (fn []
            (q/background 255)
            {:walker {:x (quot (q/width) 2)
                      :y (quot (q/height) 2)}})
   :tick (fn [{:keys [walker] :as state}]
           (let [choice  (Math/floor (q/random 4))
                 {:keys [x y]} walker]
             (assoc state :walker
                    (condp = choice
                      0 {:x (inc x) :y y}
                      1 {:x (dec x) :y y}
                      2 {:x x :y (inc y)}
                      3 {:x x :y (dec y)}))))
   :draw (fn [{:keys [walker]}]
           (q/stroke 0)
           (q/point (:x walker) (:y walker)))})
