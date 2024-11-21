(ns compojure-app.tasks
  "Namespace for managing tasks with in-memory storage using an atom.")

;; Define an atom to store tasks, initialized with some sample tasks
;; Each task is a map with :id, :title, and :completed keys
(defonce tasks (atom [{:id 1 :title "Learn Clojure" :completed false}
                      {:id 2 :title "Build Task Manager" :completed false}]))

;; Retrieve all current tasks from the atom
(defn get-tasks []
  "Returns the current list of tasks."
  @tasks)

;; Add a new task to the tasks list
(defn add-task [title]
  "Adds a new task with the given title.
  Generates a unique ID by incrementing the maximum existing ID.
  Returns the new task's ID."
  (let [new-id (inc (apply max (map :id @tasks)))]
    (swap! tasks conj {:id new-id :title title :completed false})
    new-id))

;; Mark a specific task as completed
(defn complete-task [id]
  "Marks the task with the given ID as completed.
  Uses swap! to update the atom, mapping over the tasks 
  and changing the :completed flag for the matching task."
  (swap! tasks 
         (fn [current-tasks] 
           (map #(if (= (:id %) id) 
                   (assoc % :completed true) 
                   %) 
                current-tasks))))

;; Remove a task from the tasks list
(defn delete-task [id]
  "Removes the task with the given ID from the tasks list.
  Uses swap! to update the atom, filtering out the task 
  with the matching ID."
  (swap! tasks 
         (fn [current-tasks] 
           (remove #(= (:id %) id) 
                   current-tasks))))