(ns noc.chapter-0-5e
  (:require
   [goog.string :as gstring]
   [goog.string.format]
   [quil.core :as q]))

(def size [640 240])

(defn init-state [{:keys [width height]}]
  {:walker {:x (quot width 2)
            :y (quot height 2)}
   :ui     {:stdev {:type  :slider
                    :min   0 :max 10
                    :step 1
                    :value 5}
            :mean {:type  :slider
                   :min   0 :max 10
                   :step 1
                   :value 1}}})

(defn setup! [_]
  (q/background 255))

(defn rand-gaus [mean stdev] (+ mean (* stdev (q/random-gaussian))))

(defn tick [{:keys [width height ui] :as state}]
  (let [choice  (Math/floor (q/random 4))
        stdev (get-in ui [:stdev :value])
        mean (get-in ui [:mean :value])
        step (fn [{:keys [x y]}]
               (let [step (rand-gaus mean stdev)]
                 (condp = choice
                   0 {:x (+ x step) :y y}
                   1 {:x (- x step) :y y}
                   2 {:x x :y (+ y step)}
                   3 {:x x :y (- y step)})))
        constrain (fn [{:keys [x y]}]
                    {:x (q/constrain x 0 (- width 1))
                     :y (q/constrain y 0 (- height 1))})]
    (update state :walker (comp constrain step))))

(defn draw! [{:keys [walker ui]}]
  (let [stdev (get-in ui [:stdev :value])
        mean (get-in ui [:mean :value])]
    (q/stroke 0)
    (q/point (:x walker) (:y walker))
    (q/fill 255)
    (q/no-stroke)
    (q/rect 0 0 100 35)
    (q/fill 0)
    (q/text (gstring/format "mean: %.2f" mean) 10 20)
    (q/text (gstring/format "stdev: %.2f" stdev) 10 30)))
