(ns matrix.config
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[matrix started successfully]=-"))
   :middleware identity})
