(ns noc.chapter-1-6
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
        normed (tm/* (tm/normalize subtracted) 50)]
    (q/background 255)
    (q/translate (quot width 2) (quot height 2))
    (q/stroke 200)
    (q/stroke-weight 2)
    (u/line zero subtracted)
    (q/stroke 0)
    (q/stroke-weight 8)
    (u/line zero normed)))
