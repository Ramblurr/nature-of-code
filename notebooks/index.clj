(ns index
  {:nextjournal.clerk/visibility {:code :hide}}
  (:require
   [noc.render]
   [noc.clerk-ext :as clerk-ext]
   [nextjournal.clerk :as clerk]
   [clojure.java.io :as io]))

;; # Nature of Code in Clojure

;;

(clerk/html
 [:div {:class "md:flex md:flex-row md:gap-4"}
  [:div {:class "flex items-center justify-center flex-auto  not-prose max-w-48"}
   (noc.render/image-ext {:width "100%" :style {:object-fit "contain"}}
                         (io/resource "assets/img/noc-cover.png"))]
  [:div {:class "flex flex-col items-center"}
   [:div {:class "prose dark:prose-invert"}
    [:p
     "This is my ClojureScript implementation of the samples and exercises from "
     [:a {:href "https://natureofcode.com/"} "Nature of Code (2nd Edition)"] " by Daniel Shiffman."]
    [:p
     "The book will be released in July 2024. It is available online for free, but the pre-order for the hardback and ebook editions are open now. "
     [:a {:href "https://natureofcode.com/"} "Go check it out!"]]
    [:p "You can find the source code for this document and my Clojure sketches at " [:a {:href "https://github.com/Ramblurr/nature-of-code"} "https://github.com/Ramblurr/nature-of-code"]]]]])

(clerk-ext/admonition "This project is a work in progress and isn't yet finished. I add to it when I find the time, which may not be often." :style :warning)

;; ### Why?

;; I've been a fan of Daniel Shiffman's work (the 1st edition of Nature of Code,
;; as well as their excellent [Coding Train](https://thecodingtrain.com/)) for a
;; long time. I've always wanted to work through the book. When I learned that
;; they were releasing a new edition, I figured now was the time.

;; Implementing the examples and exercises in Clojure is a great way to move
;; through the material in a piece-wise manner and explore the various Clojure ecosystem around creative coding
;; and math/geometry (in particular [thi.ng/geom](https://github.com/thi-ng/geom))

;; ### How?

;; Though I've tried to keep the code as close to the original as possible,
;; Clojure is an immutable functional programming language, and my translation
;; of the examples adhere to that property.

(clerk/html
 [:p "To learn how I've structured the code to avoid willy-nilly mutation or what tech I'm using to render all of this, check out the "
  [:a {:href (clerk/doc-url "notebooks/background")} "background"] "."])

;; ## Table of Contents

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
     :description "Randomness"}
    {:title "Chapter 1"
     :preview (io/resource "assets/img/chapter1.png")
     :path "notebooks/chapter_1"
     :description "Vectors"}])))
