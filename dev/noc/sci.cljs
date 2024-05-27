(ns noc.sci
  (:require [nextjournal.clerk.sci-env]
            noc.sketch
            noc.render
            quil.core
            [sci.ctx-store]
            [sci.core :as sci]))

;; ## SCI Customization

(def custom-namespaces
  {'noc.sketch
   (sci/copy-ns noc.sketch (sci/create-ns 'noc.sketch))
   'q
   (sci/copy-ns quil.core (sci/create-ns 'quil.core))
   'noc.render
   (sci/copy-ns noc.render (sci/create-ns 'noc.render))
   ;; Add any more namespaces here! Make sure to `:require` anything you add at
   ;; the top.
   })

(def custom-aliases
  {'sketch 'noc.sketch})

(sci.ctx-store/swap-ctx!
 sci/merge-opts
 {:classes    {'Math js/Math}
  :aliases    custom-aliases
  :namespaces custom-namespaces})
