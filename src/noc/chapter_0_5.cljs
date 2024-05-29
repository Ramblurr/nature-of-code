(ns noc.chapter-0-5

  (:require [quil.core :as q]))

(def size [640 240])

(defn init-state [_]
  {:counts (into [] (repeat 20 0))})

(defn setup! [_])

(defn accept-reject!
  "Pick a random number using a simple monte carlo method.
  Generates a random number between 0 and 1.
  Higher numbers are more likely to be generated."
  []
  (->>
   (repeatedly #(q/random 1)) ;;  an infinite sequence of random numbers between 0 and 1
   (filter #(< (q/random 1) %)) ;; keep only the numbers that qualify (probability formula y = x)
   (first)                      ;; pick the first number that qualifies
   ))

(defn tick [{:keys [counts] :as state}]
  (let [idx (int (* (count counts) (accept-reject!)))]
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
