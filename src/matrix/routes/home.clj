(ns matrix.routes.home
  (:require [matrix.layout :as layout]
            [compojure.core :refer [defroutes GET]]
            [ring.util.http-response :refer [ok]]
            [clojure.java.io :as io]
            [matrix.app.assignment :as assignment]
            [cheshire.core :refer :all]))

(defn home-page []
  (layout/render
    "home.html" {:docs (-> "docs/docs.md" io/resource slurp)}))

(defn about-page []
  (layout/render "about.html"))

(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/about" [] (about-page))
  (GET "/assignments/:assignment-id" [assignment-id]
       {:status 200
        :headers {"Content-Type" "application/json; charset=utf-8"}
        :body (generate-string (assignment/get-assignment assignment-id))}))

