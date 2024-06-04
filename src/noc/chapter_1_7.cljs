(ns noc.chapter-1-7
  (:require
   [noc.quil.util :as u]
   [thi.ng.math.core :as tm]
   [thi.ng.geom.vector :as v]
   [quil.core :as q]))

(def size [640 240])

(defn init-state [{:keys [width height] :as state}]
  {:mover {:position  (v/vec2 (q/random width) (q/random height))
           :velocity (v/vec2 (q/random -2 2) (q/random -2 2))}})

(defn setup! [{:keys [width height]}]
  (q/background 255))

;; These were my first pass at the implementation
;; but I wasn't happy that it has so much repeated code
(comment
  (defn enforce-bound-x [width position]
    (let [x (v/x position)]
      (cond (>= x width) (v/vec2 0 (v/y position))
            (<= x 0) (v/vec2 width (v/y position))
            :else position)))

  (defn enforce-bound-y [height position]
    (let [y (v/y position)]
      (cond (>= y height) (v/vec2 (v/y position) 0)
            (<= y 0) (v/vec2 (v/y position) height)
            :else position))))

;; ... so I refactored it to this
;; `dimension` is either width or height
;; `component` is either :x or :y, perhaps there's a better name?
(defn enforce-bound [dimension component position]
  (let [value (component position)]
    (cond (>= value dimension) (assoc position component 0)
          (<= value 0) (assoc position component dimension)
          :else position)))

(defn tick-mover [width height {:keys [velocity] :as mover}]
  (-> mover
      (update :position #(tm/+ % velocity))
      (update :position #(enforce-bound width :x %))
      (update :position #(enforce-bound height :y %))))

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
