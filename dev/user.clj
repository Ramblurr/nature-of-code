(ns user
  (:require
   [babashka.fs :as fs]
   [clojure.java.shell :refer [sh]]
   [clojure.string]
   [nextjournal.clerk :as clerk]
   [nextjournal.clerk.config :as config]
   [nextjournal.clerk.viewer :as cv]
   [shadow.cljs.devtools.server :as shadow.server]
   [shadow.cljs.devtools.server.npm-deps :as npm-deps]
   [shadow.cljs.devtools.api :as shadow.api]
   [thi.ng.math.core :as tm]))

(def build-target
  {:git/url "https://github.com/ramblurr/nature-of-code"})

(def ^{:doc "static site defaults for local and github-pages modes."}
  defaults
  {:out-path   "public"
   :cas-prefix "/"})

(defn start!
  "Starts a Clerk server process "
  ([] (start! {}))
  ([opts]
   (let [defaults {:port 7777
                   :browse? false
                   :watch-paths ["notebooks"]}]

     (clerk/serve!
      (merge defaults opts))
     (Thread/sleep 1000)
     #_(clerk/show! "notebooks/hello.clj"))))

(defn start-with-shadow!
  ([] (start-with-shadow! {}))
  ([opts]
   (npm-deps/main {} {})
   (shadow.server/start!)
   (shadow.api/watch :clerk)
   (swap! config/!resource->url
          assoc
          "/js/viewer.js"
          "http://localhost:8765/js/main.js")
   (start! opts)))

(defn git-sha
  "Returns the sha hash of this project's current git revision."
  []
  (-> (sh "git" "rev-parse" "HEAD")
      (:out)
      (clojure.string/trim)))

(defn static-build!
  "This task is used to generate static sites for local use, github pages
  deployment and Clerk's Garden CDN.

  Accepts a map of options `opts` and runs the following tasks:

  - Slurps the output of the shadow-cljs `:clerk` build from `public/js/main.js`
    and pushes it into a CAS located at `(str (:out-path opts) \"/js/_data\")`.

  - Configures Clerk to generate files that load the js from the generated name
    above, stored in `(str (:cas-prefix opts) \"/js/_data\")`

  - Creates a static build of the single namespace [[index]] at `(str (:out-path
    opts) \"/index.html\")`

  All `opts` are forwarded to [[nextjournal.clerk/build!]]."
  [opts]
  (npm-deps/main {} {})
  (shadow.api/release! :clerk)
  (let [{:keys [out-path cas-prefix]} (merge defaults opts)
        sha (or (:git/sha opts) (git-sha))
        cas (cv/store+get-cas-url!
             {:out-path (str out-path "/js") :ext "js"}
             (fs/read-all-bytes "public/js/main.js"))]
    (swap! config/!resource->url assoc
           "/js/viewer.js"
           (str cas-prefix "js/" cas))
    (clerk/build!
     (merge build-target
            (assoc opts
                   :out-path out-path
                   :git/sha sha)))))

(defn garden!
  "Standalone executable function that runs [[static-build!]] configured for
  Clerk's Garden. See [[garden-defaults]] for customizations
  to [[static-build!]]."
  [opts]
  (let [sha (or (:git/sha opts) (git-sha))]
    (static-build!
     (merge {:cas-prefix (str "/ramblurr/nature-of-code/commit/" sha "/")
             :git/sha sha}
            opts))))

(comment

  (do
    (start-with-shadow!)
    (require '[portal.api :as p])
    (def p (p/open {:theme :portal.colors/gruvbox}))
    (add-tap #'p/submit)) ;; rcf

  (garden!
   {:paths    ["src/**"]
    :index "notebooks/index.clj"
    :out-path "public"})

  (static-build! {:paths    ["notebooks/**"]
                  :index "notebooks/index.clj"
                  :out-path "public"})

  (update-in {:_ui {:foobar {:values :yay}}}
             [:_ui]
             #(dissoc % :foobar))
  (range 10)

  ()

  (require '[thi.ng.math.core :as tm])
  (require '[thi.ng.geom.vector :as v])
  (require '[thi.ng.geom.core :as g])
  (let [v1 (v/vec2 2 2)
        v2 (v/vec2 1 1)
        v3 (v/vec2 3 4)]
    (= v3 (tm/+ v1 v2))
    (=  (tm/* v1 4) (v/vec2 8.0 8.0))
    (= 5.0 (tm/mag (v/vec2 4 3)))
    (tm/+ (v/vec2 1 1) (v/vec2 1 1))
    (tm/normalize (v/vec2 2 3))
    (g/heading-xy (v/vec2 2 3))
    (g/rotate (v/vec2 1 1) Math/PI)
    (tm/mix (v/vec2 1 1) (v/vec2 2 2) 0.5)
    (g/dist (v/vec2 1 1) (v/vec2 2 2))
    (g/angle-between (v/vec2 1 1) (v/vec2 2 3))

    ;;
    )
  (tm/limit (v/vec2 1 1) 0.5)
  (mod (dec 0) 10)

  (let [v (v/vec2 1 5)
        u (tm/* v 2)
        w (tm/- v u)]
    (tm/div w 3))

  ;;
  )
