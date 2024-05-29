(ns noc.chapter-0-6e
  (:require
   [goog.string :as gstring]
   [goog.string.format]
   [quil.core :as q]))

(def size [640 240])

(defn init-state [{:keys [width height]}]
  {:walker {:x (quot width 2)
            :y (quot height 2)}
   :ui     {:max-step {:type :slider
                       :min 1 :max 20
                       :step 1
                       :value 10}}})

(defn setup! [_]
  (q/background 255))

(defn random-step-size!
  "Return a random number between 0 and max, where the probability in the monte carlo method is a quadratic function.  This makes the likelihood that a value is picked equal to the value squared"
  [max]
  (->>
   (repeatedly #(q/random 0 max))
   (filter #(let [r2 (q/random 0 1)
                  probability (/ (* % %) (* max max))]
              (< r2 probability)))
   (first)))

(defn tick [{:keys [width height ui] :as state}]
  (let [choice    (Math/floor (q/random 4))
        max-step  (get-in ui [:max-step :value])
        step      (fn [{:keys [x y]}]
                    (let [step (random-step-size! max-step)]
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
  (let [max-step (get-in ui [:max-step :value])]
    (q/stroke 0)
    (q/point (:x walker) (:y walker))
    (q/fill 255)
    (q/no-stroke)
    (q/rect 0 0 100 35)
    (q/fill 0)
    (q/text (gstring/format "max-step: %.2f" max-step) 10 20)))
