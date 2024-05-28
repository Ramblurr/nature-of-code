(ns noc.chapter-0-1
  (:require
   [quil.core :as q]))

(def size [640 240])

(defn init-state [{:keys [width height]}]
  {:walker {:x (quot width 2)
            :y (quot height 2)}})

(defn setup! []
  (q/background 255))

(defn tick [{:keys [walker] :as state}]
  (let [choice  (Math/floor (q/random 4))
        {:keys [x y]} walker]
    (assoc state :walker
           (condp = choice
             0 {:x (inc x) :y y}
             1 {:x (dec x) :y y}
             2 {:x x :y (inc y)}
             3 {:x x :y (dec y)}))))

(defn draw! [{:keys [walker]}]
  (q/stroke 0)
  (q/point (:x walker) (:y walker)))

