(ns noc.chapter-0-9e
  (:require
   [goog.string :as gstring]
   [goog.string.format]
   [quil.core :as q]))

(def size [640 240])

(defn init-state [_]
  {:z 0.0
   :ui {:octaves {:type :slider
                  :min 1 :max 20
                  :step 1 :value 4}
        :falloff {:type :slider
                  :min 0 :max 0.5
                  :step 0.01
                  :label "test"
                  :value 0}
        :xoff-incr {:type :slider
                    :min 0 :max 0.1
                    :step 0.01
                    :value 0.01}

        :yoff-incr {:type :slider
                    :min 0 :max 0.1
                    :step 0.01
                    :value 0.01}
        :animate {:type :checkbox :checked? false}}})

(defn setup! [{:keys [width height]}]
  (q/noise-seed (rand-int (-> js/Number (.-MAX_SAFE_INTEGER))))
  (q/background 255))

(defn gen-noise-image [width height xoff-incr yoff-incr z]
  (let [rand-color (fn [t1 t2]
                     (Math/floor (q/map-range (q/noise t1 t2 z) 0 1 0 255)))
        img  (q/create-image width height)
        roff 0
        goff 10000
        boff 100000
        px (q/pixels img)
        xoffs (take width (iterate #(+ % xoff-incr) 0.0))]
    (doseq [[x xoff] (map-indexed vector xoffs)]
      (let [yoffs (take height (iterate #(+ % yoff-incr) 0.0))]
        (doseq [[y yoff] (map-indexed vector yoffs)]
          (let [idx (* 4 (+ x (* y width)))
                r  (rand-color (+ roff xoff) (+ roff yoff))
                g  (rand-color (+ goff xoff) (+ goff yoff))
                b  (rand-color (+ boff xoff) (+ boff yoff))]
            (aset px idx r)
            (aset px (+ 1 idx) g)
            (aset px (+ 2 idx) b)
            (aset px (+ 3 idx) 255)))))
    (q/update-pixels img)
    img))

(defn tick [{:keys [old-octaves old-falloff old-yoff-incr old-xoff-incr width height z] :as state}]
  (let [octaves (-> state :ui :octaves :value)
        falloff (-> state :ui :falloff :value)
        xoff-incr (-> state :ui :xoff-incr :value)
        yoff-incr (-> state :ui :yoff-incr :value)
        animate? (-> state :ui :animate :checked?)
        z (+ 0.1 z)]
    (if (or animate?
            (not= octaves old-octaves)
            (not= falloff old-falloff)
            (not= yoff-incr old-yoff-incr)
            (not= xoff-incr old-xoff-incr))
      (do
        (q/noise-detail octaves falloff)
        (assoc state
               :z z
               :old-octaves octaves
               :old-falloff falloff
               :old-xoff-incr xoff-incr
               :old-yoff-incr yoff-incr
               :img (gen-noise-image width height xoff-incr yoff-incr z)))
      state)))

(defn draw! [{:keys [ui img]}]
  (let [octaves (-> ui :octaves :value)
        falloff (-> ui :falloff :value)
        xoff-incr (-> ui :xoff-incr :value)
        yoff-incr (-> ui :yoff-incr :value)]

    (when img
      (q/image img 0 0))
    (q/no-stroke)
    (q/fill 255)
    (q/rect 0 0 100 55)
    (q/fill 0)
    (q/text (gstring/format "octaves: %.2f" octaves) 10 20)
    (q/text (gstring/format "falloff: %.2f" falloff) 10 30)
    (q/text (gstring/format "xoff-incr: %.2f" xoff-incr) 10 40)
    (q/text (gstring/format "yoff-incr: %.2f" yoff-incr) 10 50)))
