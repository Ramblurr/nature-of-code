(ns noc.render
  (:require
   [noc.sketch]))

(def icon-reset [:svg
                 {:stroke "currentColor",
                  :aria-hidden "true",
                  :fill "none",
                  :width "1em",
                  :xmlns "http://www.w3.org/2000/svg",
                  :stroke-width "2",
                  :class "h-[15px] w-[15px]",
                  :viewBox "0 0 24 24",
                  :height "1em"}
                 [:path
                  {:stroke-linecap "round",
                   :stroke-linejoin "round",
                   :d
                   "M4 4v5h.582m15.356 2A8.001 8.001 0 004.582 9m0 0H9m11 11v-5h-.581m0 0a8.003 8.003 0 01-15.357-2m15.357 2H15"}]])

(def icon-pause
  [:svg
   {:stroke "currentColor",
    :aria-hidden "true",
    :fill "none",
    :width "1em",
    :xmlns "http://www.w3.org/2000/svg",
    :stroke-width "2",
    :class "h-4 w-4",
    :viewBox "0 0 24 24",
    :height "1em"}
   [:path
    {:stroke-linecap "round",
     :stroke-linejoin "round",
     :d "M10 9v6m4-6v6m7-3a9 9 0 11-18 0 9 9 0 0118 0z"}]])

(defn render-quil-sketch [sketch]
  (let [sketch-opts (noc.sketch/load-sketch sketch)
        applet* (atom nil)]
    [:div {:class "clear-both my-4 overflow-hidden rounded border bg-gray-100"}
     [:div {:class "relative overflow-hidden rounded-t bg-white"}
      (if sketch-opts
        [:div {:class "w-full border-none transition-opacity opacity-100"
               :ref (fn [el]
                      (prn sketch)
                      (when el
                        (reset! applet* (noc.sketch/show-sketch sketch-opts el))))}]
        [:div (str "No sketch named " sketch " found")])]
     [:div {:class "flex items-center justify-between border-t"}
      [:div
       {:class "flex"}
       [:button {:class "flex items-center border-r px-2.5 py-1.5 text-sm hover:bg-gray-200"
                 :on-click (fn [_]
                             (noc.sketch/reset applet*))}
        icon-reset
        [:span {:class "ml-1.5"} "Reset"]]
       [:button
        {:class "flex items-center border-r px-2.5 py-1.5 text-sm hover:bg-gray-200"
         :on-click (fn [_]
                     (noc.sketch/toggle-pause applet*))}
        icon-pause
        [:span {:class "ml-1"} "Pause"]]]]]))
