(ns matrix.app.assignment
  (:require [org.httpkit.client :as http]
            [cheshire.core :refer :all]
            [com.rpl.specter :as s]
            [clojure.pprint :refer (pprint)]
            [matrix.app.helper :refer (get-json-response refers-one refers-many)]))

(def api-server "")

(defn get-ml [ml-id]
  (get-json-response (str api-server "/magic-list-service/magic_lists/" ml-id)))


(defn get-skus [sku-ids]
  (let [sku-param (clojure.string/join "," sku-ids)]
    (get-json-response (str api-server "/ui/1.0/catalog/basic_sku_info.json?sku_ids=" sku-param))))


(defn get-popa [popa-id]
  (get-json-response (str api-server "/customer-order-service/partial_order_assignments/" popa-id)))


(defn get-assignment [assignment-id]
  (let [popa (get-popa assignment-id)
        with-ml (refers-one popa
                            [:partialOrder :magicListId]
                            :magicList
                            get-ml)]
    (refers-many with-ml
                 [:partialOrder :magicList :magicListItems s/ALL s/LAST :skuId]
                 :sku
                 [:sku :id]
                 get-skus)))

