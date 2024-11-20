(ns compojure-app.features
  "Namespace for managing application feature flags.
   Provides a centralized way to enable/disable features.")

;; Define a configuration map for feature flags
;; Each feature is a key with a boolean value indicating whether it's enabled
(defonce feature-flags 
  (atom {
    :task-deletion-enabled true    ; Control task deletion feature
    :task-completion-enabled true  ; Control task completion feature
    :dark-mode-enabled false       ; UI feature flag
  }))

;; Function to check if a specific feature is enabled
(defn feature-enabled? 
  "Check if a given feature flag is enabled.
   
   Args:
   - feature-key: Keyword representing the feature to check
   
   Returns:
   Boolean indicating whether the feature is enabled"
  [feature-key]
  (get @feature-flags feature-key false))

;; Function to update a feature flag
(defn set-feature! 
  "Enable or disable a specific feature flag.
   
   Args:
   - feature-key: Keyword representing the feature to modify
   - enabled?: Boolean value to set the feature flag"
  [feature-key enabled?]
  (swap! feature-flags assoc feature-key enabled?))