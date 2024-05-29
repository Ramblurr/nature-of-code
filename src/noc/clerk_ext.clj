(ns noc.clerk-ext
  (:require [nextjournal.clerk :as clerk]))

(def icons {:info [:svg
                   {:xmlns "http://www.w3.org/2000/svg",
                    :fill "none",
                    :viewBox "0 0 24 24",
                    :stroke-width "1.5",
                    :stroke "currentColor",
                    :class "size-8 flex-shrink-0 inline me-3"}
                   [:path
                    {:stroke-linecap "round",
                     :stroke-linejoin "round",
                     :d
                     "m11.25 11.25.041-.02a.75.75 0 0 1 1.063.852l-.708 2.836a.75.75 0 0 0 1.063.853l.041-.021M21 12a9 9 0 1 1-18 0 9 9 0 0 1 18 0Zm-9-3.75h.008v.008H12V8.25Z"}]]
            :warning  [:svg
                       {:xmlns "http://www.w3.org/2000/svg",
                        :fill "none",
                        :viewBox "0 0 24 24",
                        :stroke-width "1.5",
                        :stroke "currentColor",
                        :class "size-8 flex-shrink-0 inline me-3"}
                       [:path
                        {:stroke-linecap "round",
                         :stroke-linejoin "round",
                         :d
                         "M12 9v3.75m-9.303 3.376c-.866 1.5.217 3.374 1.948 3.374h14.71c1.73 0 2.813-1.874 1.948-3.374L13.949 3.378c-.866-1.5-3.032-1.5-3.898 0L2.697 16.126ZM12 15.75h.007v.008H12v-.008Z"}]]})

(def color {:info "text-blue-800 bg-blue-50 dark:bg-gray-800 dark:text-blue-400"
            :warning "text-yellow-800 bg-yellow-50 dark:bg-gray-800 dark:text-yellow-300"})

(defn admonition [body & {:keys [style]
                          :or {style :info}}]
  (clerk/html
   [:div
    {:class
     (str
      "flex items-center p-4 mb-4 text-sm rounded-lg "
      (get color style))
     :role "alert"}
    (get icons style)
    [:span {:class "sr-only"} "Info"]
    [:div
     body]]))
