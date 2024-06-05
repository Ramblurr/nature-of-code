(ns noc.chapter-1-8
  (:require
   [noc.quil.util :as u]
   [thi.ng.math.core :as tm]
   [thi.ng.geom.vector :as v]
   [quil.core :as q]))

(def size [640 240])

(defn init-state [{:keys [width height] :as state}]
  {:mover {:position  (v/vec2 (quot width 2) (quot height 2))
           :velocity (v/vec2 0 0)
           :acceleration (v/vec2 -0.001 0.01)
           :top-speed 10}})

(defn setup! [{:keys [width height]}]
  (q/background 255))

(defn enforce-bound [dimension component position]
  (let [value (component position)]
    (cond (>= value dimension) (assoc position component 0)
          (<= value 0) (assoc position component dimension)
          :else position)))

(defn tick-mover [width height {:keys [top-speed velocity acceleration] :as mover}]
  (let [new-vel (tm/limit (tm/+ velocity acceleration) top-speed)]
    (-> mover
        (assoc :velocity new-vel)
        (update :position #(tm/+ % velocity))
        (update :position #(enforce-bound width :x %))
        (update :position #(enforce-bound height :y %)))))

(defn tick [{:keys [width height] :as state}]
  (-> state
      (update :mover #(tick-mover width height %))))

(defn draw-mover [{:keys [position]}]
  (q/stroke 0)
  (q/stroke-weight 2)
  (q/fill 127)
  (q/ellipse (v/x position) (v/y position) 48 48))

(defn draw! [{:keys [mover]}]
  (q/background 255)
  (draw-mover mover))
