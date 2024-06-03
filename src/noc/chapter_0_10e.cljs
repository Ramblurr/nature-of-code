(ns noc.chapter-0-10e
  (:require
   [quil.sketch :as ap :include-macros true]
   [quil.core :as q]))

(def size [640 240])

(defn init-terrain [cell-size width height]
  (let [cols (quot width cell-size)
        rows (quot height cell-size)]
    {:cell-size cell-size
     :w width
     :h height
     :cols  cols
     :rows  rows
     :zoff 0.0
     :cells []}))

;; My initial naive impl
#_(defn tick-terrain [{:keys [cols rows zoff] :as terrain}]
    (let [updated-cells (vec (map-indexed
                              (fn [i _]
                                (vec (map-indexed
                                      (fn [j _]
                                        (q/map-range (q/noise (+ (* i 0.1) zoff) (+ (* j 0.1) zoff) zoff) 0 1 -120 120))
                                      (range rows))))
                              (range cols)))
          new-zoff (+ zoff 0.01)]
      (assoc terrain :cells updated-cells :zoff new-zoff)))

;; Using transients to try and speed it up
(defn tick-terrain [{:keys [cols rows zoff] :as terrain}]
  (let [updated-cells (persistent!
                       (reduce
                        (fn [acc i]
                          (assoc! acc i
                                  (persistent!
                                   (reduce
                                    (fn [inner-acc j]
                                      (assoc! inner-acc j
                                              (q/map-range (q/noise (+ (* i 0.1) zoff) (+ (* j 0.1) zoff) zoff) 0 1 -120 120)))
                                    (transient []) (range rows)))))
                        (transient []) (range cols)))
        new-zoff (+ zoff 0.01)]
    (assoc terrain :cells updated-cells :zoff new-zoff)))

(defn init-state [{:keys [width height]}]
  {:terrain
   (tick-terrain (init-terrain 20 800 400))
   :theta 0.0})

(defn setup! [{:keys [width height terrain]}]
  (q/background 255))

(defn tick [state]
  (-> state
      (update :terrain tick-terrain)
      (update :theta #(+ % 0.0025))))

(defn draw-terrain! [{:keys [cells cell-size cols rows w h]}]
  (doseq [x (range (dec cols))]
    ;;  Quil bug https://github.com/quil/quil/issues/415
    (.beginShape (ap/current-applet) (aget js/p5.prototype "QUAD_STRIP"))
    (doseq [y (range rows)]
      (q/stroke 0)
      (let [current-elevation (get-in cells [x y])
            current-shade (q/map-range current-elevation -120 120 0 255)
            x-coordinate (- (* x cell-size) (/ w 2))
            y-coordinate (- (* y cell-size) (/ h 2))]
        (q/fill current-shade 255)
        (q/vertex x-coordinate y-coordinate current-elevation)
        (q/vertex (+ x-coordinate cell-size) y-coordinate (get-in cells [(inc x) y]))))
    (q/end-shape)))

(defn draw! [{:keys [terrain theta] :as state}]
  (q/background 255)
  (q/push-style)
  (q/push-matrix)
  (q/translate 0 20 -200)
  (q/rotate-x (/ q/PI 3))
  (q/rotate-z theta)
  (draw-terrain! terrain)
  (q/pop-matrix)
  (q/pop-style))
