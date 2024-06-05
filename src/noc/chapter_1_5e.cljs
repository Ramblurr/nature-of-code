(ns noc.chapter-1-5e
  (:require
   [noc.quil.util :as u]
   [quil.core :as q]
   [thi.ng.geom.core :as g]
   [thi.ng.geom.vector :as v]
   [thi.ng.math.core :as tm]))

(def size [640 240])

(def sprite-data
  "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAA5gAAABECAYAAADk3Jp0AAAAAXNSR0IArs4c6QAABIBJREFUeJzt3DFOY1cUBuDriAVQZgkUUUTJBlJmCWYHFNEEOiqUZpCVgh3AEtJYygbQVGiUgiWknB2QIlX8nsY3z7+PH+Pv63gynHN/HkhHzz6tAQAAQMCi94VfPra3fTYyV6c3/RklybuWvGvJu5a8a8m7lrxrybuWvGvJO+O75A8DAADgeBkwAQAAiDBgAgAAEGHABAAAIMKACQAAQIQBEwAAgAgDJgAAABEGTAAAACIMmAAAAEQYMAEAAIgwYAIAABBhwAQAACDCgAkAAEDEorXWvnxsb4du5Bic3si7krxrybuWvGvJu5a8a8m7lrxrybvW6U1beIIJAABAhAETAACACAMmAAAAEQZMAAAAIgyYAAAARBgwAQAAiDBgAgAAEGHABAAAIMKACQAAQMRJ7wv/+GufbczXzz8cpq68a8m7lrxrybuWvGvJu5a8a8m7lrwzPMEEAAAgwoAJAABAhAETAACAiO7PYI5ZPqbaGPd0edh6cyPvWvKulTz/+fn54NqHH18OWm9upp5/7Kz39/eDa38//RSpN1azp97c9Jy/N9vr6+vBtan3d0/NnnpzM3b+nvvo6upqcO319XVwref/98XFxeDa3d3d1po99ebm++Wfg2ub983Ly/R7ZvP8Y/Vub28H156fnyP15ua3T2eDaw8PD//5uvdeHrN5/p56rbW2Wq0G19br9f+uNzdj5+/Nssfm+Vefh/+Xd/n72VZvHzzBBAAAIMKACQAAQIQBEwAAgAgDJgAAABE7LfkB+owt0mhtf0syqusd0tSlM8ma73HpTI/dls7sr2ay3iH13EdjZx1d9tBx/t6lM13LWWae99nZcCnHZr67LEHpqdeT7S4156T7Pg0ZWyYzdaHPezS2YGfzd5C8r8bqJZcqzV313+g+/3aqeIIJAABAhAETAACACAMmAAAAEQZMAAAAIiz5OVKWzuyPpTP7Y+nMflUvnZn6+3yPS2em3kdTlz2M1etdOvMtLJiYwxKUbzXbMdXnWq/XpfXmJrmgqscxLfQhwxNMAAAAIgyYAAAARBgwAQAAiDBgAgAAELForbWny/Z26EaOwfJR3pXkXUveteRdS9615F1L3rXkXUvetZaPbeEJJgAAABEGTAAAACIMmAAAAEQYMAEAAIgwYAIAABBhwAQAACDCgAkAAECEARMAAICIk/QPXD5uf83TZbpqRk/vrc2rf3nXknctedeSdy1515J3LXnXkncteX+dJ5gAAABEGDABAACIMGACAAAQYcAEAAAgYqclP2MfEv31wy/bv2/1++Ba9Qdhp/be2rD/qt7l/S95byfvREf95J3oqJ+8Ex31k3eio37yTnTUT96JjvrJO9FRv0Pl7QkmAAAAEQZMAAAAIgyYAAAARBgwAQAAiDhprbXlY1tM/P63VCM79DDVwXqX927k3UXeteRdS9615F1L3rXkXUvetQ7SuyeYAAAARBgwAQAAiDBgAgAAELGP9wH3vNe3+v3HvXrfpzyn/uVdS9615F1L3rXkXUveteRdS9615P0V/wBeHCDzUdYqLwAAAABJRU5ErkJggg==")

(defn init-state [{:keys [width height]}]
  {:mover {:position  (v/vec2 (quot width 2) (quot height 2))
           :velocity (v/vec2 0 0)
           :acceleration (v/vec2 0.1 0)
           :friction 0.9
           :top-speed 10
           :sprite {:width 92
                    :height 68
                    :sprite-offset 92
                    :x-off 0
                    :frame 0
                    :max-frames 10
                    :frame-delay 10
                    :delay-count 1
                    :spritesheet (q/load-image sprite-data)}}})

(defn setup! [_]
  (q/background 255))

(defn enforce-bound [dimension component position]
  (let [value (component position)]
    (cond (>= value dimension) (assoc position component 0)
          (<= value 0) (assoc position component dimension)
          :else position)))

(defn tick-mover [key-pressed? key width height {:keys [top-speed velocity acceleration friction] :as mover}]
  (let [new-vel (cond (and key-pressed? (= key :d)) (tm/limit (tm/+ velocity acceleration) top-speed)
                      (and key-pressed? (= key :a)) (tm/limit (tm/- velocity acceleration) top-speed)
                      :else
                      (tm/* velocity friction))]
    (-> mover
        (assoc :velocity new-vel)
        (update :position #(tm/+ % velocity))
        (update :position #(enforce-bound width :x %))
        (update :position #(enforce-bound height :y %)))))

(defn tick-frame-delay
  [{:keys [frame-delay] :as sprite}]
  (let [new-delay-count (if (= 0 frame-delay)
                          0
                          (mod (inc (:delay-count sprite)) frame-delay))]
    (assoc sprite :delay-count new-delay-count)))

(defn tick-frame
  [{:keys [max-frames delay-count] :as sprite} dir]
  (if (zero? delay-count)
    (as-> sprite s
      (update s :frame #(mod (dir %) max-frames))
      (assoc s :x-off (* (:frame s) (:sprite-offset s))))
    sprite))

(defn close-to
  [x y epsilon]
  (<= (abs (- x y)) epsilon))

(defn tick-sprite [sprite velocity]
  (let [dir (if (= 0 (g/heading velocity)) inc dec)]
    (if (close-to (tm/mag velocity) 0 0.1)
      (assoc sprite :frame 0)
      (-> sprite
          (tick-frame-delay)
          (tick-frame dir)))))

(defn tick [{:keys [key-pressed? key width height] :as state}]
  (-> state
      (update :mover #(tick-mover key-pressed? key width height %))
      (update :mover  #(assoc % :sprite (tick-sprite (:sprite %) (:velocity %))))))

(defn draw-mover [{:keys [position sprite]}]
  (let [{:keys [spritesheet x-off width height]} sprite]
    (u/draw-sprite position 0 spritesheet width height x-off 0)))

(defn draw! [{:keys [mover]}]
  (q/background 255)
  (draw-mover mover))
