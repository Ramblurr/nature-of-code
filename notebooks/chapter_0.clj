;; # Chapter 0

(ns chapter-0
  {:nextjournal.clerk/visibility {:code :hide}}
  (:require [nextjournal.clerk :as clerk]
            [noc.quil-clerk :refer [show-sketch]]))

;; ## [Example 0.1: A Traditional Random Walk](https://natureofcode.com/random/#example-01-a-traditional-random-walk)

^{::clerk/no-cache true ::clerk/viewer clerk/code}
(slurp "dev/noc/chapter_0_1.cljs")

(show-sketch :walker)

;; ## [Example 0.2: A Random-Number Distribution](https://natureofcode.com/random/#example-02-a-random-number-distribution)

^{::clerk/no-cache true ::clerk/viewer clerk/code}
(slurp "dev/noc/chapter_0_2.cljs")

(show-sketch :rand-dist)
