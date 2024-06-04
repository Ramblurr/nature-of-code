(ns noc.chapter-1-5
  (:require
   [noc.quil.util :as u]
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
  (let [zero (v/vec2 0 0)
        mouse (v/vec2 mouse-x mouse-y)
        center (v/vec2 (quot width 2) (quot height 2))
        subtracted (tm/- mouse center)
        m (tm/mag subtracted)]
    (q/background 255)
    (q/fill 0)
    (q/rect 10 10 m 10)
    (q/translate (quot width 2) (quot height 2))
    (u/line zero subtracted)))
