(ns noc.chapter-0-2
  (:require [quil.core :as q]))

(def size [640 240])

(defn init-state [_]
  {:counts (into [] (repeat 20 0))})

(defn setup! [_])

(defn tick [{:keys [counts] :as state}]
  (let [idx (Math/floor (q/random (count counts)))]
    (update-in state [:counts idx] #(+ % 1))))

(defn draw! [{:keys [counts]}]
  (q/background 255)
  (q/stroke 0)
  (q/stroke-weight 2)
  (q/fill 127)
  (let [w (quot (q/width) (count counts))]
    (doseq [[idx v] (map-indexed vector counts)]
      (q/rect (* idx w)
              (- (q/height) v)
              (dec w)
              v))))
