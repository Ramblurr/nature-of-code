(ns noc.chapter-0-2
  (:require [quil.core :as q]))

(def sketch
  {:size [640 240]
   :setup (fn []
            {:counts (into [] (repeat 20 0))})

   :tick (fn [{:keys [counts] :as state}]
           (let [idx (Math/floor (q/random (count counts)))]
             (update-in state [:counts idx] inc)))

   :draw (fn [{:keys [counts]}]
           (q/background 255)
           (q/stroke 0)
           (q/fill 127)
           (let [w (quot (q/width) (count counts))]
             (doseq [[idx v] (map-indexed vector counts)]
               (q/rect (* idx w)
                       (- (q/height) v)
                       (dec w)
                       v))))})
