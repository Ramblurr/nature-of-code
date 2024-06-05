# Chapter 1

```clojure
(ns chapter-1
  {:nextjournal.clerk/visibility {:code :hide} :nextjournal.clerk/toc true}
  (:require
   [thi.ng.math.core :as tm]
   [thi.ng.geom.vector :as v]
   [clojure.java.io :as io]
   [nextjournal.clerk :as clerk]
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

| p5.js Method     | `thi.ng` Function           | Task                                                                             |
|------------------|-----------------------------|----------------------------------------------------------------------------------|
| `add()`          | `(tm/+ a b)`                | Adds vector `a` to  vector `b`                                                   |
| `sub()`          | `(tm/- a b`                 | Subtracts  vector `a` from vector `b`                                            |
| `mult()`         | `(tm/* a b)`                | Scales this vector with multiplication                                           |
| `div()`          | `(tm/div a b)`              | Scales this vector with division                                                 |
| `mag()`          | `(tm/mag a)`                | Returns the magnitude of the vector `a`                                          |
| `normalize()`    | `(tm/normalize a)`          | Normalizes this vector to a unit length of 1                                     |
| `setMag()`       | `(tm/* (tm/normalize a) x)` | Sets the magnitude of this vector `a` to `x`                                     |
| `limit()`        | `(tm/limit a)`              | Limits the magnitude of this vector                                              |
| `heading()`      | `(g/heading-xy a)`          | Returns the 2D heading of this vector expressed as an angle                      |
| `rotate()`       | `(g/rotate a angle)`        | Rotates this 2D vector by `angle` (in radians)                                   |
| `lerp()`         | `(tm/mix a b amt)`          | Linear interpolates vector `a` towards vector `b` by `amt`                       |
| `dist()`         | `(g/dist a b)`              | Returns the Euclidean distance between vector `a` and `b` (considered as points) |
| `angleBetween()` | `(g/angle-between a b )`    | Finds the angle between vector `a` and vector `b`                                |
| `dot()`          | `(tm/dot a b)`              | Returns $$\vec{a}  \cdot \vec{b} $$ (the dot product)                            |
| `cross()`        | `(tm/cross a b)`            | Returns $$\vec{a}  \times \vec{b} $$ (the cross product)                         |
| `random2D()`     | `-`                         | Returns a random 2D vector - Not available AFAICT                                |
| `random3D()`     | `-`                         | Returns a random 3D vector - Not available AFAICT                                |

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


## [Example 1.5: Vector Magnitude](https://natureofcode.com/vectors/#example-15-vector-magnitude)


```clojure
^{::clerk/no-cache true ::clerk/viewer clerk/code}
(slurp "src/noc/chapter_1_5.cljs")
(show-sketch :c1.5)
```

Here I pulled out the line helper into a util namespace, because I imagine we will use it a lot in the future.

## [Example 1.6: Normalizing a Vector](https://natureofcode.com/vectors/#example-16-normalizing-a-vector)


```clojure
^{::clerk/no-cache true ::clerk/viewer clerk/code}
(slurp "src/noc/chapter_1_6.cljs")
(show-sketch :c1.6)
```

## [Example 1.7: Motion 101 (Velocity)](https://natureofcode.com/vectors/#example-17-motion-101-velocity)


```clojure
^{::clerk/no-cache true ::clerk/viewer clerk/code}
(slurp "src/noc/chapter_1_7.cljs")
(show-sketch :c1.7)
```

Wow, so I discovered several things while writing this example. 

1. The `(v/x some-vector)` and `(v/y some-vector)` functions  (where `v` is `[thi.ng.geom.vector :as v]`) will return the corresponding x and y components. This could be handy in the future
2. But since the vector type implements all the interfaces to be treated as a map, you can also `assoc` with it while referring to the keys `:x` and `:y`

I used the 2nd discovery to implement a bounds checking function that is rather generic, while still being readable. I am quite pleased with that.


## [Example 1.8: Motion 101 (Velocity and Constant Acceleration)](https://natureofcode.com/vectors/#example-18-motion-101-velocity-and-constant-acceleration)


```clojure
^{::clerk/no-cache true ::clerk/viewer clerk/code}
(slurp "src/noc/chapter_1_8.cljs")
(show-sketch :c1.8)
```

## [Exercise 1.5: Acceleration Control](https://natureofcode.com/vectors/#exercise-15)


```clojure
^{::clerk/no-cache true ::clerk/viewer clerk/code}
(slurp "src/noc/chapter_1_5e.cljs")
```

For this exercise I got a little fancy by rendering a simple animated sprite for the car, and applying a bit of friction modeled as a scalar that is multiplied against the velocity vector.

Use `A` and `D` to move the car to the right/left.

```
(show-sketch :c1.5e)
```
The sprite is from [senderin](https://senderin.itch.io/car). The entire sheet is 10 frames:

```
(clerk/image (io/resource "assets/img/car-side-right.png"))
```


## [Example 1.9: Motion 101 (Velocity and Random Acceleration)](https://natureofcode.com/vectors/#example-19-motion-101-velocity-and-random-acceleration)


```clojure
^{::clerk/no-cache true ::clerk/viewer clerk/code}
(slurp "src/noc/chapter_1_9.cljs")
(show-sketch :c1.9)
```

## [Exercise: 1.6: A Perlin Noise Accelerated Walker](https://natureofcode.com/vectors/#exercise-16)


```clojure
^{::clerk/no-cache true ::clerk/viewer clerk/code}
(slurp "src/noc/chapter_1_6e.cljs")
(show-sketch :c1.6e)
```

This one is very nice, the contrast between this exercise and Example 1.9 is
stark. This one feels much more natural.

## [Exercise: 1.7: Pseudocode Translation](https://natureofcode.com/vectors/#exercise-17)

Of course here in Clojure we don't have any of the confusion that this section of the chapter is addressing (of course, being Clojure we have our own other sorts of confusion). 

Our function calls do not mutate any of the arguments, rather, they return a new `vec2` as the result.

```
^{:nextjournal.clerk/visibility {:code :show} :nextjournal.clerk/auto-expand-results? true}
(let [v (v/vec2 1 5)
      u (tm/* v 2)
      w (tm/- v u)]
      {:result (tm/div w 3)
      :v v
      :u u 
      :w w})
```

We can see that none of the arguments were changed.

## [Example 1.10: Accelerating Toward the Mouse](https://natureofcode.com/vectors/#example-110-accelerating-toward-the-mouse)


```clojure
^{::clerk/no-cache true ::clerk/viewer clerk/code}
(slurp "src/noc/chapter_1_10.cljs")
(show-sketch :c1.10)
```

Hover your mouse over the sketch!


## [Exercise 1.8e: Variable Magnitude of Acceleration](https://natureofcode.com/vectors/#exercise-18)


```clojure
^{::clerk/no-cache true ::clerk/viewer clerk/code}
(slurp "src/noc/chapter_1_8e.cljs")
(show-sketch :c1.8e)
```

Once again, hover your mouse over the sketch.

I used `thi.ng.geom.core/dist` to calculate the euclidean distance and then
calculate the acceleration as `2 / distance`. Why 2? Well, first I tried `1 /
distance` but the effect was rather slow, increasing the numerator speeds it up.

One problem with the sketch was that it would follow the mouse off-screen. So
I've added a check that stops the mover when the mouse is not contained in the
canvas.
