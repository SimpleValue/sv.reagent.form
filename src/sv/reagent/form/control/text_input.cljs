(ns sv.reagent.form.control.text-input
  (:require [sv.reagent.form.bind :as b]))

(defn text-input-binding [modifier]
  {:value (b/get-value modifier)
   :onChange (b/bind-input-value modifier)
   :onBlur (b/bind-value modifier)})

(defn text-input-attrs [field]
  {:type "text"
   :id (str (:id field))
   :class "form-control"
   :placeholder (:placeholder field)
   :title (:title field)})

(defn text-input [modifier]
  (let [field (modifier)]
    [:input (merge
             (text-input-attrs field)
             (text-input-binding modifier))]))
