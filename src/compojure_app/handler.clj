(ns compojure-app.handler
  "Main application handler for the task manager.
   Defines routes, middleware, and rendering logic."
  (:require 
    ;; Existing requires
    [compojure.core :refer :all]
    [compojure.route :as route]
    [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
    [ring.middleware.params :refer [wrap-params]]
    [ring.adapter.jetty :refer [run-jetty]]
    [hiccup.core :as h]

    ;; Add feature flags namespace
    [compojure-app.features :as features]
    [compojure-app.tasks :as tasks]))

;; Updated home page to conditionally render features
(defn home-page 
  "Renders the main task manager page with feature-flagged elements."
  []
  (h/html
   [:html
    [:head
     [:title "Task Manager"]
     [:style "body { font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; padding: 20px; }"]]
    [:body
     [:h1 "Task Manager"]

     ;; Display feature flag statuses
     [:h2 "Feature Flag Statuses"]
     [:ul
      ;; Iterate over all feature flags and display their current status
      (for [[feature enabled?] @features/feature-flags]
        [:li (str (name feature) " is " (if enabled? "Enabled" "Disabled"))])]

     ;; Conditional task input form based on feature flag
     (when (features/feature-enabled? :task-completion-enabled)
       [:form {:method "post" :action "/tasks"}
        [:input {:type "text" :name "title" :placeholder "Enter task title"}]
        [:button {:type "submit"} "Add Task"]])

     ;; Dynamic task list rendering with feature-flagged actions
     [:ul 
      (for [task (tasks/get-tasks)]
        [:li 
         [:span (:title task)]
         [:span " - "]
         [:span (if (:completed task) "Completed" "Pending")]

         ;; Conditionally render complete link
         (when (features/feature-enabled? :task-completion-enabled)
           [:span " "]
           [:a {:href (str "/complete/" (:id task))} "[Complete]"])

         ;; Conditionally render delete link
         (when (features/feature-enabled? :task-deletion-enabled)
           [:span " "]
           [:a {:href (str "/delete/" (:id task))} "[Delete]"])])]]]))

;; Updated routes with feature flag checks
(defroutes app-routes
  ;; Root route remains the same
  (GET "/" [] (home-page))

  ;; Add task route with feature flag
  (POST "/tasks" {params :params}
    (when (features/feature-enabled? :task-completion-enabled)
      (let [title (get params "title")]
        (tasks/add-task title)
        {:status 302 :headers {"Location" "/"}})))

  ;; Complete task route with feature flag
  (GET "/complete/:id" [id]
    (when (features/feature-enabled? :task-completion-enabled)
      (let [task-id (Integer/parseInt id)]
        (tasks/complete-task task-id)
        {:status 302 :headers {"Location" "/"}})))

  ;; Delete task route with feature flag
  (GET "/delete/:id" [id]
    (when (features/feature-enabled? :task-deletion-enabled)
      (let [task-id (Integer/parseInt id)]
        (tasks/delete-task task-id)
        {:status 302 :headers {"Location" "/"}})))

  ;; 404 route remains the same
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
  (run-jetty app {:port 3000}))