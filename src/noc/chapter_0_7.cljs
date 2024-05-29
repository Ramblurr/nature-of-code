(ns noc.chapter-0-7
  (:require
   [quil.core :as q]))

(def size [640 240])

(defn init-state [_]
  {})

(defn setup! [{:keys [width height]}]
  (q/noise-seed (rand-int (-> js/Number (.-MAX_SAFE_INTEGER))))
  (q/background 255)
  (let [img  (q/create-image width height)
        xoffs (take width (iterate #(+ % 0.01) 0.0))]
    (doseq [[x xoff] (map-indexed vector xoffs)]
      (let [yoffs (take height (iterate #(+ % 0.01) 0.0))]
        (doseq [[y yoff] (map-indexed vector yoffs)]
          (let [bright (Math/floor (q/map-range (q/noise xoff yoff) 0 1 0 255))]
            (q/set-pixel img x y bright)))))
    (q/update-pixels img)
    (q/image img 0 0)))

(defn tick [state]
  state)

(defn draw! [_])
