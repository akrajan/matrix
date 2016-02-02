(ns matrix.app.helper
  (:require [org.httpkit.client :as http]
            [cheshire.core :refer :all]
            [com.rpl.specter :as s]
            [clojure.pprint :refer (pprint)]))


(defn get-json-response [url]
  (let [{:keys [status headers body error] :as resp} @(http/get url)]
    (parse-string body true)))



(defn refers-one [collection
                  reference-location
                  merge-key
                  lambda]
  (if-let [reference (first (s/select reference-location collection))]
    (let [other (lambda reference)
          prefix (butlast reference-location)]
      (s/transform (conj prefix (s/putval other))
                   (fn [x y]
                     (assoc y merge-key x))
                   collection))
    collection))



(defn refers-many [collection
                   reference-location
                   merge-key
                   referred-location
                   lambda]
  (if-let [references (s/select reference-location collection)]
    (let [others (lambda references)
          with-reference (s/select (concat [s/ALL s/VAL] referred-location) others)
          prefix (butlast reference-location)]
      (pprint collection)
      (s/transform (concat prefix [(s/collect-one (last reference-location))])
                   (fn [reference-key current-val]
                     (assoc current-val
                       merge-key
                       (ffirst
                        (filter (fn [[val referred-key]]
                                  (= reference-key referred-key))
                                with-reference))))
                   collection))
    collection))
