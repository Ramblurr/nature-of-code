(ns noc.quil-clerk
  (:require [nextjournal.clerk :as clerk]))

^{:nextjournal.clerk/visibility {:result :hide}}
(defn make-sketch-viewer []
  {:transform-fn clerk/mark-presented
   :render-fn 'noc.render/render-quil-sketch})

^{:nextjournal.clerk/visibility {:result :hide}}
(defn show-sketch [sketch-name]
  (clerk/with-viewer (make-sketch-viewer) sketch-name))
