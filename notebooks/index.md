```clojure
(ns index
  {:nextjournal.clerk/visibility {:code :hide}}
  (:require
   [noc.render]
   [nextjournal.clerk :as clerk]
   [clojure.java.io :as io]))
```

# Nature of Code in Clojure

This is my ClojureScript implementation of the samples and exercises from [Nature of Code (2nd Edition)][noc2] by Daniel Shiffman.

The book will be released in July 2024 but is available for pre-order now, [go check it out!][noc2]

[noc2]: https://github.com/nature-of-code/noc-book-2

This project is a work in progress and isn't yet finished. I add to it when I find the time, which may not be often.


### Why?

I've been a fan of Daniel Shiffman's work (the 1st edition of Nature of Code, as well as his excellent [Coding Train](https://thecodingtrain.com/) for a long time.  I've always wanted to work through the book. This is a great way to do it!

Implementing it in Clojure lets me train my functional muscles and it's rather fun.

### How?

Though I've tried to keep the code as close to the original as possible, Clojure is an immutable functional programming language, and my translation of the examples adhere to that property.

To learn how I've structured the code to avoid willy-nilly mutation or what tech I'm using to render all of this, check out the [background](notebooks/background).

## Table of Contents

```clojure
(clerk/html
 (into
  [:div.md:grid.md:gap-8.md:grid-cols-2.pb-8]
  (map
   (fn [{:keys [path preview title description]}]
     [:a.rounded-lg.shadow-lg.border.border-gray-300.relative.flex.flex-col.hover:border-indigo-600.group.mb-8.md:mb-0
      {:href (clerk/doc-url path) :title path :style {:height 300}}
      [:div.flex-auto.overflow-hidden.rounded-t-md.flex.items-center.px-3.py-4
      (when preview
        (noc.render/image-ext {:width "100%" :style {:object-fit "contain"}}
                               preview))]
      [:div.sans-serif.border-t.border-gray-300.px-4.py-2.group-hover:border-indigo-600
       [:div.font-bold.block.group-hover:text-indigo-600 title]
       [:div.text-xs.text-gray-500.group-hover:text-indigo-600.leading-normal description]]])
   [{:title "Chapter 0"
     :preview (io/resource "assets/img/chapter0.png")
     :path "notebooks/chapter_0"
     :description "Randomness"}])))
```
