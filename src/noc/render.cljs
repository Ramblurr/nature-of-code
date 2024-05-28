(ns noc.render
  (:require
   [nextjournal.clerk.render.hooks :as hooks]
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

(def icon-play
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
     :d
     "M14.752 11.168l-3.197-2.132A1 1 0 0010 9.87v4.263a1 1 0 001.555.832l3.197-2.132a1 1 0 000-1.664z"}]
   [:path
    {:stroke-linecap "round",
     :stroke-linejoin "round",
     :d "M21 12a9 9 0 11-18 0 9 9 0 0118 0z"}]])

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
  (let [!aspect-ratio (hooks/use-state (/ 8 3))
        !is-paused (hooks/use-state false)
        adjust-frame (fn  [el]
                       (when-let [canvas (.querySelector el "canvas")]
                         (reset! !aspect-ratio (/ (.-width canvas) (.-height canvas)))
                         (aset (.-style canvas) "width" "100%")
                         (aset (.-style canvas) "height" "100%")))
        !container-ref (hooks/use-ref nil)

        sketch-opts (noc.sketch/load-sketch sketch)
        !sketch (hooks/use-state nil)
        reset (fn []
                (noc.sketch/reset !sketch)
                (noc.sketch/resume !sketch)
                (reset! !is-paused false))

        toggle-running (fn []
                         (if @!is-paused
                           (do
                             (noc.sketch/resume !sketch)
                             (reset! !is-paused false))
                           (do
                             (noc.sketch/pause !sketch)
                             (reset! !is-paused true))))]
    ;; Pauses the sketch when it is not in view, and resumes it when it is in view
    (hooks/use-effect (fn []
                        (let [curr @!container-ref]
                          (when (and curr (not @!is-paused) @!sketch)
                            (let [intersection-callback (fn [entries]
                                                          (when (odd? (count entries))
                                                            (let [entry (last entries)]
                                                              (if (.-isIntersecting entry)
                                                                (noc.sketch/resume !sketch)
                                                                (noc.sketch/pause !sketch)))))
                                  observer              (js/IntersectionObserver. intersection-callback #js{:root       nil
                                                                                                            :rootMargin "0px"
                                                                                                            :threshold  0})]
                              (.observe observer curr)
                              (fn [] (.unobserve observer curr))))))
                      [!container-ref !is-paused !sketch])
    ;; Loads the sketch when the container is mounted
    (hooks/use-layout-effect (fn []
                               (when-let [el @!container-ref]
                                 (reset! !sketch (noc.sketch/show-sketch adjust-frame sketch-opts el)))))

    [:div {:class "clear-both my-4 overflow-hidden rounded border bg-gray-100"}
     [:div {:class "relative overflow-hidden rounded-t bg-white"}
      (if sketch-opts
        [:div {:class "w-full border-none transition-opacity opacity-100"
               :ref   !container-ref}]
        [:div (str "No sketch named " sketch " found")])]
     [:div {:class "flex items-center justify-between border-t"}
      [:div
       {:class "flex"}
       [:button {:class "flex items-center border-r px-2.5 py-1.5 text-sm hover:bg-gray-200"
                 :on-click reset}
        icon-reset
        [:span {:class "ml-1.5"} "Reset"]]
       [:button
        {:class "flex items-center border-r px-2.5 py-1.5 text-sm hover:bg-gray-200"
         :on-click toggle-running}
        (if @!is-paused
          icon-play
          icon-pause)
        (if @!is-paused
          [:span {:class "ml-1"} "Play"]
          [:span {:class "ml-1"} "Pause"])]]]]))
