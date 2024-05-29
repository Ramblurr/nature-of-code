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
^{::clerk/no-cache true ::clerk/viewer clerk/code}
(slurp "src/noc/chapter_0_3e.cljs")
(show-sketch :walker-dynamic)
```


## [Example 0.4: A Gaussian Distribution](https://natureofcode.com/random/#example-04-a-gaussian-distribution)
```clojure
^{::clerk/no-cache true ::clerk/viewer clerk/code}
(slurp "src/noc/chapter_0_4.cljs")
(show-sketch :random-gaussian)
```


## [Exercise 0.4: Gaussian Paint Splatter](https://natureofcode.com/random/#exercise-04)

Our first bit of UI. Quil doesn't expose the p5.js UI widgets like
[`createSlider`](https://p5js.org/reference/#/p5/createSlider).

I didn't want to call those non-pure functions inside our pure `init-state` and `tick`, so I implemented them behind the scenes. As you can see the init state function can return a map containing a `:ui` key. This lets us declaratively handle the ui widgets.

For the exercise itself the slider controls the standard deviation as suggested by the prompt. I went with a HSL color-mode that tends to be more reddish thanks to the normal distribution, but can be made a little more colorful by increasing the stdev.

```clojure
^{::clerk/no-cache true ::clerk/viewer clerk/code}
(slurp "src/noc/chapter_0_4e.cljs")
(show-sketch :c0.4e)
```


## [Exercise 0.5: Gaussian Random Walk](https://natureofcode.com/random/#exercise-05)

Here I expand on the original random walk by constraining the walker inside the
bounds of the sketch, and adjusting the step size based on a normal distribution.

Play with the sliders to adjust the mean and standard deviation.

```clojure
^{::clerk/no-cache true ::clerk/viewer clerk/code}
(slurp "src/noc/chapter_0_5e.cljs")
(show-sketch :c0.5e)
```


## [Example 0.5: An Accept-Reject Distribution](https://natureofcode.com/random/#example-05-an-accept-reject-distribution)

Lazy sequences!

`(repeatedly #(q/random 1))` generates an infinite lazy sequence of random numbers between 0 and 1. In Clojure, lazy sequences are evaluated on demand, meaning elements are only generated when needed.

`(filter #(< (q/random 1) %))` filters the infinite sequence according to the probability function

`(first)` picks the first number from the filtered sequence that matches the condition. Due to the lazy nature of sequences in Clojure, the sequence is generated and filtered element by element until a match is found.

The code does not enter an infinite loop because the lazy evaluation of the sequence ensures that elements are only generated and processed as needed. The filter function processes each element in the sequence one by one, and as soon as an element satisfies the condition, first returns that element, terminating the evaluation.

```clojure
(show-sketch :c0.5)
^{::clerk/no-cache true ::clerk/viewer clerk/code}
(slurp "src/noc/chapter_0_5.cljs")
```


## [Exercise 0.6: Accept-Reject Random Walk](https://natureofcode.com/random/#exercise-06)

Another minor variation on the random walk, this time using a quadratic
probability distribution.  The value squared (e.g., $r1^2$) can be much larger
than the value itself, especially as `max` increases. To keep the probability in
the range [0, 1], I normalize it by dividing by the maximum possible squared
value $\text{max}^2$.

$$
r2 < \frac{r1^2}{\text{max}^2}
$$

So, higher values of $r1$ (closer to `max`) have a proportionally higher chance
of being less than $r2$, thus more likely to be selected.



```clojure
^{::clerk/no-cache true ::clerk/viewer clerk/code}
(slurp "src/noc/chapter_0_6e.cljs")
(show-sketch :c0.6e)
```
