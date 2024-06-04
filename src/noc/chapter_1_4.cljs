(ns noc.chapter-1-4
  (:require

   [thi.ng.math.core :as tm]
   [thi.ng.geom.vector :as v]
   [quil.core :as q]))

(def size [640 240])

(defn init-state [{:keys [width height] :as state}]
  {})

(defn setup! [{:keys [width height]}]
  (q/background 255))

(defn tick [state]
  state)

(defn line [[x1 y1] [x2 y2]]
  (q/line x1 y1 x2 y2))

(defn draw! [{:keys [width height mouse-x mouse-y]}]
  (let [zero (v/vec2 0 0)
        mouse (v/vec2 mouse-x mouse-y)
        center (v/vec2 (quot width 2) (quot height 2))
        subtracted (tm/- mouse center)
        multed (tm/* subtracted 0.5)]
    (q/background 255)
    (q/translate (quot width 2) (quot height 2))
    (q/stroke-weight 2)
    (q/stroke 200)
    (line zero subtracted)
    (q/stroke 0)
    (q/stroke-weight 4)
    (line zero multed)))
