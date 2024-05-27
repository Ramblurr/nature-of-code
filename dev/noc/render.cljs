(ns noc.render
  (:require
   [noc.custom]))

(defn render-quil-sketch [sketch]
  (if (noc.custom/has-sketch? sketch)
    [:div {:ref (fn [el]
                  (prn sketch)
                  (when el
                    (noc.custom/load-sketch sketch el)))}]
    [:div (str "No sketch named " sketch "found")]))
