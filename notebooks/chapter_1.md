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


## Exercises 1.1 and 1.2

I am skipping these exercises as they don't add much to what we did in Example 1

## [Exercise 1.3: Bouncing Sphere](https://natureofcode.com/vectors/#exercise-13)


```clojure
^{::clerk/no-cache true ::clerk/viewer clerk/code}
(slurp "src/noc/chapter_1_3e.cljs")
(show-sketch :c1.3e)
```

This was more an exercise in 3d than vectors, haha! I fake the boundary condition for the sphere, but it looks more or less right.


## More Vector Math

This section isn't an example or exercise, but this list of vector math related functions is very useful! The `thi.ng/geom` toolkit is very powerful, but doesn't have an easy to use getting started page.

So I'm reproducing the vector math function table from the book, but with functions from `thi.ng/geom`.

To create a vector use [`thi.ng.geom.vector/vec2`](https://cljdoc.org/d/thi.ng/geom/1.0.1/api/thi.ng.geom.vector#vec2) or [`thi.ng.geom.vector/vec3`](https://cljdoc.org/d/thi.ng/geom/1.0.1/api/thi.ng.geom.vector#vec3).

Example:

```
(clerk/code "

(let [pos (v/vec2 100 100)
  [x y] pos]
  ;; use x y
  )

(let [pos (v/vec3 100 100 100)
  [x y z] pos]
  ;; use x y z
  )
")

```

In the following table the namespaces referenced are:

* [`[thi.ng.math.core :as tm]`](https://github.com/thi-ng/math/blob/0.3.2/src/core.org)
* [`[thi.ng.geom.core :as g]`](https://cljdoc.org/d/thi.ng/geom/1.0.1/api/thi.ng.geom.core)

| p5.js Method     | `thi.ng` Function        | Task                                                                             |
|------------------|--------------------------|----------------------------------------------------------------------------------|
| `add()`          | `(tm/+ a b)`             | Adds vector `a` to  vector `b`                                                   |
| `sub()`          | `(tm/- a b`              | Subtracts  vector `a` from vector `b`                                            |
| `mult()`         | `(tm/* a b)`             | Scales this vector with multiplication                                           |
| `div()`          | `(tm/div a b)`           | Scales this vector with division                                                 |
| `mag()`          | `(tm/mag a)`             | Returns the magnitude of the vector `a`                                          |
| `setMag()`       | `-`                      | Sets the magnitude of this vector- Not available AFAICT                          |
| `normalize()`    | `(tm/normalize a)`       | Normalizes this vector to a unit length of 1                                     |
| `limit()`        | `(tm/limit a)`           | Limits the magnitude of this vector                                              |
| `heading()`      | `(g/heading-xy a)`       | Returns the 2D heading of this vector expressed as an angle                      |
| `rotate()`       | `(g/rotate a angle)`     | Rotates this 2D vector by `angle` (in radians)                                   |
| `lerp()`         | `(tm/mix a b amt)`       | Linear interpolates vector `a` towards vector `b` by `amt`                       |
| `dist()`         | `(g/dist a b)`           | Returns the Euclidean distance between vector `a` and `b` (considered as points) |
| `angleBetween()` | `(g/angle-between a b )` | Finds the angle between vector `a` and vector `b`                                |
| `dot()`          | `(tm/dot a b)`           | Returns $$\vec{a}  \cdot \vec{b} $$ (the dot product)                            |
| `cross()`        | `(tm/cross a b)`         | Returns $$\vec{a}  \times \vec{b} $$ (the cross product)                         |
| `random2D()`     | `-`                      | Returns a random 2D vector - Not available AFAICT                                |
| `random3D()`     | `-`                      | Returns a random 3D vector - Not available AFAICT                                |

Some extra notes:

Most of the math functions in `thi.ng` tend to convert integers to doubles. For example `(tm/mag (v/vec2 4 3))` produces `5.0` not `5` (that makes sense for magnitude since it is often a fractional number). 

But this it may be surprising. `(tm/+ (v/vec2 1 1) (v/vec2 1 1))` produces a vector with `2.0` as the x and y components, not `2`.


## [Example 1.3: Vector Subtraction](https://natureofcode.com/vectors/#example-13-vector-subtraction)

```clojure
^{::clerk/no-cache true ::clerk/viewer clerk/code}
(slurp "src/noc/chapter_1_3.cljs")
(show-sketch :c1.3)
```


## [Example 1.4: Multiplying a Vector](https://natureofcode.com/vectors/#example-14-multiplying-a-vector)


```clojure
^{::clerk/no-cache true ::clerk/viewer clerk/code}
(slurp "src/noc/chapter_1_4.cljs")
(show-sketch :c1.4)
```

Ok, for this one I got tired of destructuring the vectors or using `first` and `second` to yoink out the components to pass to quil's `line` function. A little helper goes a long way.

By the way, Daniel's exposition on the vector math in this chapter is really great. Don't just look at the code, refer to the text and the illustrations to really get an understanding of the vector math.
