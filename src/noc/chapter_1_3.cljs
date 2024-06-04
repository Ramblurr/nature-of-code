(ns noc.chapter-1-3
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

(defn draw! [{:keys [width height mouse-x mouse-y]}]
  (let [mouse (v/vec2 mouse-x mouse-y)
        center (v/vec2 (quot width 2) (quot height 2))
        subtracted (tm/- mouse center)]
    (q/background 255)
    (q/stroke-weight 4)
    (q/stroke 200)
    (q/line 0 0 mouse-x mouse-y)
    (q/line 0 0 (first center) (second center))
    (q/stroke 0)
    (q/translate (quot width 2) (quot height 2))
    (q/line 0 0 (first subtracted) (second subtracted))))
