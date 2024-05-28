(ns noc.chapter-0-3e
  (:require
   [quil.core :as q]))

(def size [640 240])

(defn init-state [{:keys [width height]}]
  {:walker {:x (quot width 2)
            :y (quot height 2)}})

(defn setup! []
  (q/background 255))

(defn move-towards [ax ay bx by step]
  (let [dx (- ax bx)
        dy (- ay by)
        distance (Math/sqrt (+ (* dx dx) (* dy dy)))
        unit-dx (/ dx distance)
        unit-dy (/ dy distance)]
    {:x (+ bx (* step unit-dx))
     :y (+ by (* step unit-dy))}))

(defn tick [{:keys [width height] :as state}]
  (let [r             (q/random 1)
        mx (q/mouse-x)
        my (q/mouse-y)
        centerx (quot width 2)
        centery (quot height 2)
        step (fn [{:keys [x y]}]
               (cond
                 (< r 0.5) (move-towards mx my x y 1)
                 (< r 0.6) {:x (inc x) :y y}
                 (< r 0.7) {:x (dec x) :y y}
                 (< r 0.8) {:x x :y (inc y)}
                 (< r 0.9) {:x x :y (dec y)}
                 :else (move-towards centerx centery x y 1)))

        constrain (fn [{:keys [x y]}]
                    {:x (q/constrain x 0 (- width 1))
                     :y (q/constrain y 0 (- height 1))})]
    (update state :walker (comp constrain step))))

(defn draw! [{:keys [walker]}]
  (q/stroke 0)
  (q/point (:x walker) (:y walker)))
