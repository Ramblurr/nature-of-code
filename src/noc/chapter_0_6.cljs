(ns noc.chapter-0-6
  (:require
   [goog.string :as gstring]
   [goog.string.format]
   [quil.core :as q]))

(def size [640 240])

(defn init-state [{:keys [width height]}]
  {:walker {:x nil
            :y nil
            :tx 0
            :ty 10000}})

(defn setup! [_]
  (q/background 255))

(defn step-walker [width height {:keys [tx ty] :as walker}]
  {:x (q/map-range (q/noise tx) 0 1 0 width)
   :y (q/map-range (q/noise ty) 0 1 0 height)
   :tx (+ tx 0.01)
   :ty (+ ty 0.01)})

(defn tick [{:keys [width height] :as state}]
  (update state :walker (partial step-walker width height)))

(defn draw! [{:keys [walker ui]}]
  (q/stroke-weight 2)
  (q/fill 127)
  (q/stroke 0)
  (when (:x walker)
    (q/ellipse (:x walker) (:y walker) 48 48)))
