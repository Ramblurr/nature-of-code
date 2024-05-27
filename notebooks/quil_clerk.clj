;; # Quil in Clerk

(ns quil-clerk
  {:nextjournal.clerk/visibility {:code :hide}}
  (:require [nextjournal.clerk :as clerk]))

;; This is a proof of concept on how to embed interactive Quil animations in a Clerk notebook.

^{:nextjournal.clerk/visibility {:result :hide}}
(defn make-sketch-viewer []
  {:transform-fn clerk/mark-presented
   :render-fn 'noc.render/render-quil-sketch})

^{:nextjournal.clerk/visibility {:result :hide}}
(defn show-sketch [sketch-name]
  (clerk/with-viewer (make-sketch-viewer) sketch-name))

(show-sketch :dots)
