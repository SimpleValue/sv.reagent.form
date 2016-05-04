(ns sv.reagent.form.control.text-input
  (:require [sv.reagent.form.bind :as b]))

(defn text-input-binding [model]
  {:value (b/get-value model)
   :onChange (b/bind-input-value model)
   :onBlur (b/bind-value model)})

(defn text-input-attrs [field]
  {:type "text"
   :id (str (:id field))
   :class "form-control"
   :placeholder (:placeholder field)
   :title (:title field)
   :disabled (:disabled field)})

(defn text-input [model]
  (let [field @model]
    [:input (merge
             (text-input-attrs field)
             (text-input-binding model))]))
