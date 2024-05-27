;; # Quil in Clerk

(ns quil-clerk
  {:nextjournal.clerk/visibility {:code :hide}}
  (:require [nextjournal.clerk :as clerk]
            [noc.quil-clerk :refer [show-sketch]]))

;; This is a proof of concept on how to embed interactive Quil animations in a Clerk notebook.
(show-sketch :dots)
