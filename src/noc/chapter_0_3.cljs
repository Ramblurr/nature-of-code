(ns noc.chapter-0-3
  (:require
   [quil.core :as q]))

(def size [640 240])

(defn init-state [{:keys [width height]}]
  {:walker {:x (quot width 2)
            :y (quot height 2)}})

(defn setup! [_]
  (q/background 255))

(defn tick [{:keys [width height] :as state}]
  (let [r             (q/random 1)
        step (fn [{:keys [x y]}]
               (cond
                 (< r 0.4) {:x (inc x) :y y}
                 (< r 0.6) {:x (dec x) :y y}
                 (< r 0.8) {:x x :y (inc y)}
                 :else     {:x x :y (dec y)}))
        constrain (fn [{:keys [x y]}]
                    {:x (q/constrain x 0 (- width 1))
                     :y (q/constrain y 0 (- height 1))})]
    (update state :walker (comp constrain step))))

(defn draw! [{:keys [walker]}]
  (q/stroke 0)
  (q/point (:x walker) (:y walker)))
