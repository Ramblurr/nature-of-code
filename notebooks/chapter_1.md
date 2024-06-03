# Chapter 1

```clojure
(ns chapter-1
  {:nextjournal.clerk/visibility {:code :hide} :nextjournal.clerk/toc true}
  (:require [nextjournal.clerk :as clerk]
            [noc.quil-clerk :refer [show-sketch]]))
```

Chapter 1! Here we go!

## [Example 1.1: Bouncing Ball with No Vectors](https://natureofcode.com/vectors/#example-11-bouncing-ball-with-no-vectors)

```clojure
^{::clerk/no-cache true ::clerk/viewer clerk/code}
(slurp "src/noc/chapter_1_1.cljs")
(show-sketch :c1.1)
```

Not much special going on here.


## [Example 1.2: Bouncing Ball with Vectors!](https://natureofcode.com/vectors/#example-12-bouncing-ball-with-vectors)

```clojure
^{::clerk/no-cache true ::clerk/viewer clerk/code}
(slurp "src/noc/chapter_1_2.cljs")
(show-sketch :c1.2)
```

The book says:

> At this stage, you might feel somewhat disappointed. After all, these changes may appear to have made the code more complicated than the original version.

That's definitely the case. I don't thing adding the vector type made the code
any better for this minimal example, though Daniel admits as much.

What's important is that we introduce the [`[thi.ng.geom.vector]`](https://cljdoc.org/d/thi.ng/geom/1.0.1/api/thi.ng.geom.vector) and [`[thi.ng.math.core]`](https://cljdoc.org/d/thi.ng/math/0.3.2/api/thi.ng.math.core) namespaces which will be seen *a lot* in future examples and exercises. 

Why did I choose to pull in `thi.ng/geom` rather than use p5.js's `createVector` and other associated functions?

Well, Quil doesn't expose these functions because the P5.js and Processing APIs for vectors is not at all compatible. Furthermore I want the code in this notebook to be more or less Clojure and Clojurescript compatible. The `thi.ng/geom` library provides a bunch of tools that we will use in the future.
