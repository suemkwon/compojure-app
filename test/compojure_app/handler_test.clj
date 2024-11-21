(ns compojure-app.handler-test
  "Test namespace for verifying task management functionality"
  (:require 
   [clojure.test :refer :all]     
   [compojure-app.handler :refer :all]  
   [compojure-app.tasks :as tasks]
   [compojure-app.features :as features]))

;; Test case for adding a new task with feature flag
(deftest test-add-task
  (testing "Adding a new task when feature is enabled"
    ;; Enable task completion feature for the test
    (features/set-feature! :task-completion-enabled true)
    (let [initial-count (count (tasks/get-tasks))
          _ (tasks/add-task "Test Task")]
      (is (= (inc initial-count) (count (tasks/get-tasks))))
    
    (testing "Adding a task when feature is disabled"
      ;; Disable task completion feature
      (features/set-feature! :task-completion-enabled false)
      (let [current-count (count (tasks/get-tasks))]
        (tasks/add-task "Disabled Feature Task")
        ;; Count should remain the same when feature is disabled
        (is (= current-count (count (tasks/get-tasks))))))))

;; Test case for completing a task with feature flag
(deftest test-complete-task
  (testing "Completing a task when feature is enabled"
    (features/set-feature! :task-completion-enabled true)
    (let [task-id (tasks/add-task "Task to Complete")
          _ (tasks/complete-task task-id)
          completed-task (first (filter #(= (:id %) task-id) (tasks/get-tasks)))]
      (is (:completed completed-task)))
  
  (testing "Task not completed when feature is disabled"
    (features/set-feature! :task-completion-enabled false)
    (let [task-id (tasks/add-task "Task Not to Complete")
          _ (tasks/complete-task task-id)
          unchanged-task (first (filter #(= (:id %) task-id) (tasks/get-tasks)))]
      (is (not (:completed unchanged-task))))))

;; Test case for deleting a task with feature flag
(deftest test-delete-task
  (testing "Deleting a task when feature is enabled"
    (features/set-feature! :task-deletion-enabled true)
    (let [initial-count (count (tasks/get-tasks))
          task-id (tasks/add-task "Task to Delete")
          _ (tasks/delete-task task-id)]
      (is (= initial-count (count (tasks/get-tasks)))))
  
  (testing "Task not deleted when feature is disabled"
    (features/set-feature! :task-deletion-enabled false)
    (let [initial-count (count (tasks/get-tasks))
          task-id (tasks/add-task "Task Not to Delete")
          _ (tasks/delete-task task-id)]
      (is (= (inc initial-count) (count (tasks/get-tasks)))))))