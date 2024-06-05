(ns noc.quil.util
  (:require [quil.core :as q]))

(defn line [[x1 y1] [x2 y2]]
  (q/line x1 y1 x2 y2))

(def memo-graphics (memoize (fn [w h] (q/create-graphics w h))))

(defn wrap-trans-rot
  "Perform a translation, a rotation, invoke the supplied
  function (probably drawing a sprite, then reset the transform matrix
  to the identity."
  [[x y] r f]
  (q/push-matrix)
  (q/translate x y)
  (q/rotate (q/radians r))
  (f)
  (q/pop-matrix))

(defn draw-sprite
  "Draw the sprite from the spritesheet at the given position, rotation on the canvas.
  x,y and w,h will be used to pick the sprite off the spritesheet"
  [pos rotation spritesheet w h x-offset y-offset]
  (assert spritesheet)
  (assert pos)
  (assert w)
  (assert h)
  (let [g (memo-graphics w h)]
    (q/with-graphics g
      (.clear g)
      (q/image spritesheet (- x-offset) (- y-offset)))
    (wrap-trans-rot pos rotation
                    #(q/image g (- (/ w 2)) (- (/ h 2))))))
