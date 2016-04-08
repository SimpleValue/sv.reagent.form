(ns sv.reagent.form.util
  (:require [reagent.core :as r]))

(defn with [ratom f & args]
  (r/cursor
   (fn
     ([k] (apply f @ratom args))
     ([k v] (swap! ratom (fn [_] v))))
   []))
