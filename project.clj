(defproject compojure-app "0.1.0-SNAPSHOT"
  ;; Project metadata
  :description "A simple task manager application"
  :url "http://example.com/FIXME"
  
  ;; Dependencies required for the project
  :dependencies [
    ;; Clojure core language
    [org.clojure/clojure "1.11.1"]
    
    ;; Compojure for routing
    [compojure "1.7.0"]
    
    ;; Ring for web server abstraction
    [ring/ring-core "1.9.6"]
    [ring/ring-jetty-adapter "1.9.6"]
    
    ;; Middleware for JSON handling
    [ring/ring-json "0.5.1"]
    
    ;; HTML generation library
    [hiccup "2.0.0-RC2"]
  ]
  
  ;; Leiningen plugins
  :plugins [[lein-ring "0.12.6"]]
  
  ;; Ring handler configuration
  :ring {
    ;; Specify the main application handler
    :handler compojure-app.handler/app
  }
  
  ;; Main namespace for running the application
  :main compojure-app.handler)