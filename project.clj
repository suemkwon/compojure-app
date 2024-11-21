(defproject compojure-app "0.1.0-SNAPSHOT"
  ;; Project metadata remains the same
  :description "A simple task manager application"
  :url "http://example.com/FIXME"
  
  ;; Dependencies remain unchanged
  :dependencies [
    [org.clojure/clojure "1.11.1"]
    [compojure "1.7.0"]
    [ring/ring-core "1.9.6"]
    [ring/ring-jetty-adapter "1.9.6"]
    [ring/ring-json "0.5.1"]
    [hiccup "2.0.0-RC2"]
  ]
  
  ;; Leiningen plugins remain the same
  :plugins [[lein-ring "0.12.6"]]
  
  ;; Ring handler configuration
  :ring {
    :handler compojure-app.handler/app
  }
  
  ;; Main namespace for running the application
  :main compojure-app.handler)