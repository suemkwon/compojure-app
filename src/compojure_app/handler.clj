(ns compojure-app.handler
  "Main application handler for the task manager.
   Defines routes, middleware, and rendering logic."
  (:require 
    ;; Compojure routing library
    [compojure.core :refer :all]
    [compojure.route :as route]
    
    ;; Ring middleware for JSON and parameter handling
    [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
    [ring.middleware.params :refer [wrap-params]]
    
    ;; Jetty web server adapter
    [ring.adapter.jetty :refer [run-jetty]]
    
    ;; HTML generation library
    [hiccup.core :as h]
    
    ;; Local tasks namespace
    [compojure-app.tasks :as tasks]))

;; Generate the home page HTML
(defn home-page 
  "Renders the main task manager page.
   Uses Hiccup to generate HTML with dynamic task list."
  []
  (h/html
   [:html
    ;; Page head with basic styling
    [:head
     [:title "Task Manager"]
     ;; Inline CSS for basic styling
     [:style "body { font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; padding: 20px; }"]]
    [:body
     ;; Page title
     [:h1 "Task Manager"]
     
     ;; Task input form
     [:form {:method "post" :action "/tasks"}
      ;; Text input for new task title
      [:input {:type "text" :name "title" :placeholder "Enter task title"}]
      ;; Submit button
      [:button {:type "submit"} "Add Task"]]
     
     ;; Dynamic task list rendering
     [:ul 
      ;; Loop through all tasks and create list items
      (for [task (tasks/get-tasks)]
        [:li 
         [:span (:title task)]
         [:span " - "]
         [:span (if (:completed task) "Completed" "Pending")]
         [:span " "]
         [:a {:href (str "/complete/" (:id task))} "[Complete]"]
         [:span " "]
         [:a {:href (str "/delete/" (:id task))} "[Delete]"]])]]]))

;; Define application routes
(defroutes app-routes
  ;; Root route - display home page
  (GET "/" [] (home-page))
  
  ;; Route to add a new task via POST
  (POST "/tasks" {params :params}
    ;; Extract task title from form parameters
    (let [title (get params "title")]
      ;; Add task to the task list
      (tasks/add-task title)
      ;; Redirect back to home page
      {:status 302 :headers {"Location" "/"}}))
  
  ;; Route to mark a task as complete
  (GET "/complete/:id" [id]
    ;; Convert ID to integer
    (let [task-id (Integer/parseInt id)]
      ;; Complete the specified task
      (tasks/complete-task task-id)
      ;; Redirect back to home page
      {:status 302 :headers {"Location" "/"}}))
  
  ;; Route to delete a task
  (GET "/delete/:id" [id]
    ;; Convert ID to integer
    (let [task-id (Integer/parseInt id)]
      ;; Delete the specified task
      (tasks/delete-task task-id)
      ;; Redirect back to home page
      {:status 302 :headers {"Location" "/"}}))
  
  ;; 404 route for undefined paths
  (route/not-found "Not Found"))

;; Compose middleware for the application
(def app 
  ;; Chain middleware to handle parameters and JSON responses
  (-> app-routes
      ;; Middleware to parse request parameters
      wrap-params
      ;; Middleware to handle JSON responses
      wrap-json-response))

;; Main function to start the server
(defn -main 
  "Entry point for starting the web server.
   Runs Jetty on port 3000."
  []
  ;; Start Jetty web server with our app handler
  (run-jetty app {:port 3000}))