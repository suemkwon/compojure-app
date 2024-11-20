(defproject compojure-app "0.1.0-SNAPSHOT"
  :description "A simple task manager application"
  :url "http://example.com/FIXME"
  :dependencies [
    [org.clojure/clojure "1.11.1"]
    [compojure "1.7.0"]
    [ring/ring-core "1.9.6"]
    [ring/ring-jetty-adapter "1.9.6"]
    [ring/ring-json "0.5.1"]
    [hiccup "2.0.0-RC2"]
  ]
  :plugins [[lein-ring "0.12.6"]]
  :ring {
    :handler compojure-app.handler/app
  }
  :main compojure-app.handler)