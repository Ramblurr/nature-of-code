```clojure
(ns background
  {:nextjournal.clerk/visibility {:code :hide}}
  (:require
   [nextjournal.clerk :as clerk]))
```

# Background

In addition to Clojure itself, I'm using a few stellar libraries that make this beautiful and readable in the browser.

**Rendering**: I am using [Quil](http://quil.info/) for rendering. Quil is a Clojure wrapper around p5.js, so much of the rendering code is thankfully the same.

**Static Publishing**:  I am also using [Clerk](https://clerk.vision/) to render the sketches among prose. Clerk is a Clojure library that allows you to embed interactive Clojure code in a notebook.

### Mutation-free Sketches

Though I've tried to keep the code as close to the original as possible, Clojure is an immutable functional programming language, and my translation of the examples adhere to that property (I mean, that's what makes this a fun exercise in the first place!).

You will always see at least three functions in each example

```clojure
^{:nextjournal.clerk/visibility {:code :show :result :hide}}
(defn init-state
  "Calculate the initial state given the width and height of the sketch.

  This pure function is called once at the beginning of the sketch, and again
  whenever the sketch is restarted.  The return value must be a map that contains the
  initial state of the sketch."
  [{:keys [width height]}])

```

```clojure
^{:nextjournal.clerk/visibility {:code :show :result :hide}}
(defn setup!
  "Setup initial canvas

  This function is called once at the beginning of the sketch, and again
  whenever the sketch is restarted, and it is called in the Quil context, so it
  can make drawing calls. The return value is ignored."
  [])
```

```clojure
^{:nextjournal.clerk/visibility {:code :show :result :hide}}
(defn tick
  "Update the state of the sketch

  This pure* function is called once per frame. It takes the current state and returns
  the next state."
  [state])
```

\* Sort of.. the tick fns will probably use [`q/random`](http://quil.info/api/math/random) to generate random numbers, which technically makes them not pure.

```clojure
^{:nextjournal.clerk/visibility {:code :show :result :hide}}
(defn draw!
  "Draw the current state of the sketch.

  This function is called once per frame after the tick function. It takes the
  current state and draws it to the screen. The return value of this function is ignored."
  [state])
```
