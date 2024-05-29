(ns noc.quil.ui
  (:require
   [quil.sketch :as ap :include-macros true]))

(defn slider
  "Creates a slider <input></input> element.
  ref: https://p5js.org/reference/#/p5/createSlider"
  ([{:keys [value step position size min max]
     :or {min 0 max 100 value 0 step 0} :as opts}]
   (let [slider (.createSlider (ap/current-applet) min max value step)]
     (when position
       (.position slider (first position) (second position)))
     (when size
       (.size slider size))
     slider)))

(defn slider-value [s]
  (.value s))

(defn checkbox
  [{:keys [checked? label] :or {checked? false label ""}}]
  (.createCheckbox (ap/current-applet) label checked?))

(defn checkbox-value [c]
  (.checked c))

(defn prepare-element [state id {:keys [type] :as def}]
  (condp = type
    :slider (assoc-in state [:_ui id] (slider def))
    :checkbox (assoc-in state [:_ui id] (checkbox def))
    state))

(defn prepare-ui [{:keys [ui] :as state}]
  (if ui
    (reduce (fn [state [id ui-def]]
              (prepare-element state id ui-def)) state ui)
    state))

(defn update-element [state id]
  (let [handle (get-in state [:_ui id])
        def (get-in state [:ui id])]
    (if def
      (condp = (:type def)
        :slider (assoc-in state [:ui id :value] (slider-value handle))
        :checkbox (assoc-in state [:ui id :checked?] (checkbox-value handle))
        state)
      (do
        (.remove handle)
        (update-in state [:_ui] #(dissoc % id))))))

(defn update-ui
  "Populates the state map with the current values of any ui elements."
  [state]
  (if (:_ui state)
    (reduce (fn [state [id _]]
              (update-element state id)) state (:_ui state))
    state))

(defn remove-all!
  "Removes all UI elements from the DOM, returns nil. "
  [state]
  (when (:_ui state)
    (doseq [[_ handle] (:_ui state)]
      (when handle
        (.remove handle)))))
