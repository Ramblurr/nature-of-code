# Chapter 0

```clojure
(ns chapter-0
  {:nextjournal.clerk/visibility {:code :hide} :nextjournal.clerk/toc true}
  (:require [nextjournal.clerk :as clerk]
            [noc.quil-clerk :refer [show-sketch]]))
```

## [Example 0.1: A Traditional Random Walk](https://natureofcode.com/random/#example-01-a-traditional-random-walk)

```clojure
^{::clerk/no-cache true ::clerk/viewer clerk/code}
(slurp "src/noc/chapter_0_1.cljs")

(show-sketch :walker)
```


## [Example 0.2: A Random-Number Distribution](https://natureofcode.com/random/#example-02-a-random-number-distribution)


```clojure
^{::clerk/no-cache true ::clerk/viewer clerk/code}
(slurp "src/noc/chapter_0_2.cljs")

(show-sketch :rand-dist)
```

## [Example 0.3: A Walker That Tends to Move to the Right](https://natureofcode.com/random/#example-03-a-walker-that-tends-to-move-to-the-right)

```clojure
^{::clerk/no-cache true ::clerk/viewer clerk/code}
(slurp "src/noc/chapter_0_3.cljs")

(show-sketch :walker-right)
```

## [Exercise 0.3: Dynamic Walker](https://natureofcode.com/random/#exercise-03)

```clojure
^{::clerk/no-cache true ::clerk/viewer clerk/code}
(slurp "src/noc/chapter_0_3e.cljs")
```

For this exercise I the walker has a 50% chance of moving in the direction of the mouse by one step.
The other 50% of the time it moves randomly up, down, left, right, or back towards the center.

The move-towards function uses the Euclidean distance between the walker and the mouse to determine the direction.

```clojure
(clerk/tex "d = \\sqrt{(x_2 - x_1)^2 + (y_2 - y_1)^2}")
```

To move a point B towards point A by a small step using the Euclidean distance, you calculate the direction vector (dx, dy) and normalize it.

1. **Calculate the Direction Vector:**
    Find the difference in x-coordinates and y-coordinates between points A and B.

    $$
    dx = x_A - x_B
    \\
    dy = y_A - y_B
    $$

2. **Normalize the Direction Vector:**
    Calculate the Euclidean distance (magnitude) between points A and B to normalize the direction vector.

    $$
    \text{distance} = \sqrt{dx^2 + dy^2}")
    $$

    Then, divide each component of the direction vector by this distance to get the unit vector in the direction from B to A.

    $$
    \text{unit\_dx} = \frac{dx}{\text{distance}}
    \\
    \text{unit\_dy} = \frac{dy}{\text{distance}}
    $$

3. **Scale the Unit Vector:**
   Multiply the unit vector by the desired step size.
 
    $$
    \text{step\_dx} = \text{step} \times \text{unit\_dx}
    \\
    \text{step\_dy} = \text{step} \times \text{unit\_dy}
    $$

4. **Update the Coordinates of B:**
   Add the scaled vector to the coordinates of B to get the new position.

    $$
    x_B' = x_B + \text{step\_dx}
    \\
    y_B' = y_B + \text{step\_dy}
    $$

```clojure
(show-sketch :walker-dynamic)
```
