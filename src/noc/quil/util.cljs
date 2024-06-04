(ns noc.quil.util
  (:require [quil.core :as q]))

(defn line [[x1 y1] [x2 y2]]
  (q/line x1 y1 x2 y2))
