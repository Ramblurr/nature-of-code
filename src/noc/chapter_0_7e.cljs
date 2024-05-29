(ns noc.chapter-0-7e
  (:require
   [goog.string :as gstring]
   [goog.string.format]
   [quil.core :as q]))

(def size [640 240])

(defn init-state [{:keys [width height]}]
  {:walker {:x (quot width 2)
            :y (quot height 2)
            :tx 0
            :ty 10000}
   :ui {:wrap {:type :checkbox :checked? true :label " Wrap Movement?"}}})

(defn setup! [_]
  (q/background 255))

(defn tick [{:keys [width height ui] :as state}]
  (letfn [(step-walker [{:keys [x y tx ty]}]
            (let [max-step 10
                  stepx (q/map-range (q/noise tx) 0 1 (- max-step) max-step)
                  stepy (q/map-range (q/noise ty) 0 1 (- max-step) max-step)]
              {:x (+ x stepx)
               :y (+ y stepy)
               :tx (+ tx 0.01)
               :ty (+ ty 0.01)}))
          (wrap [walker]
            (-> walker
                (update :x #(cond
                              (>= % width) 0
                              (< % 0) width
                              :else %))
                (update :y #(cond
                              (>= % height) 0
                              (< % 0) height
                              :else %))))

          (constrain [walker]
            (-> walker
                (update :x #(q/constrain % 0 (- width 1)))
                (update :y #(q/constrain % 0 (- height 1)))))]
    (update state :walker (comp
                           (if (get-in ui [:wrap :checked?])
                             wrap
                             constrain) step-walker))))

(defn draw! [{:keys [walker ui]}]
  (q/stroke-weight 2)
  (q/fill 127)
  (q/stroke 0)
  (when (:x walker)
    (q/ellipse (:x walker) (:y walker) 48 48)))
