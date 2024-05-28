(ns noc.render
  (:require
   [nextjournal.clerk.viewer])
  #?(:clj (:import (java.awt.image BufferedImage))))

(def image-viewer-ext
  {#?@(:clj [:pred #(instance? BufferedImage %)
             :transform-fn (fn [{image :nextjournal/value applied-viewer :nextjournal/applied-viewer}]
                             (-> {:nextjournal/value (nextjournal.clerk.viewer/buffered-image->bytes image)
                                  :nextjournal/content-type "image/png"
                                  :nextjournal/width (nextjournal.clerk.viewer/image-width image)
                                  :attrs (:attrs applied-viewer)}
                                 nextjournal.clerk.viewer/mark-presented))])
   :attrs {:class "foobar"}
   :render-fn '(fn [blob-or-url opts]
                 (prn "ATTRV" (-> opts :viewer :attrs))
                 [:img (merge {:src #?(:clj  (nextjournal.clerk.render/url-for blob-or-url)
                                       :cljs blob-or-url)}
                              (-> opts :viewer :attrs))])})

(defn image-ext
  ([viewer-opts image-or-url]
   (nextjournal.clerk.viewer/with-viewer
     (if viewer-opts
       (assoc image-viewer-ext :attrs viewer-opts)
       image-viewer-ext)
     viewer-opts
     #?(:cljs image-or-url :clj (nextjournal.clerk.viewer/read-image image-or-url)))))
