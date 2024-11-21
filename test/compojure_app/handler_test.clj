(ns compojure-app.handler-test
  "Test namespace for verifying task management functionality"
  (:require 
   ;; Import testing and application namespaces
   [clojure.test :refer :all]     ; Clojure testing framework macros
   [compojure-app.handler :refer :all]  ; Main application handler
   [compojure-app.tasks :as tasks]))    ; Tasks management namespace

;; Test case for adding a new task
(deftest test-add-task
  ;; Description of what this test verifies
  (testing "Adding a new task"
    ;; Store the initial number of tasks
    (let [initial-count (count (tasks/get-tasks))
          ;; Add a new task and capture its ID
          _ (tasks/add-task "Test Task")]
      ;; Assert that the task count has increased by 1
      (is (= (inc initial-count) (count (tasks/get-tasks)))))))

;; Test case for completing a task
(deftest test-complete-task
  ;; Description of what this test verifies
  (testing "Completing a task"
    ;; Add a new task to complete
    (let [task-id (tasks/add-task "Task to Complete")
          ;; Mark the task as complete
          _ (tasks/complete-task task-id)
          ;; Find the completed task in the task list
          completed-task (first (filter #(= (:id %) task-id) (tasks/get-tasks)))]
      ;; Assert that the task is now marked as completed
      (is (:completed completed-task)))))

;; Test case for deleting a task
(deftest test-delete-task
  ;; Description of what this test verifies
  (testing "Deleting a task"
    ;; Store the initial number of tasks
    (let [initial-count (count (tasks/get-tasks))
          ;; Add a task to delete
          task-id (tasks/add-task "Task to Delete")
          ;; Delete the task
          _ (tasks/delete-task task-id)]
      ;; Assert that the task count has returned to the initial count
      (is (= initial-count (count (tasks/get-tasks)))))))
