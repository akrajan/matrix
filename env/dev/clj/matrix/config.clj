(ns matrix.config
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [matrix.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[matrix started successfully using the development profile]=-"))
   :middleware wrap-dev})
