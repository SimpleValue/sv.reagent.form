(ns sv.reagent.form.control.checkbox
  (:require [sv.reagent.form.control.text-input :as t]
            [sv.reagent.form.bind :as b]))

(defn checkbox [modifier]
  (let [field (modifier)]
    [:input
     {:id (str (:id field))
      :type "checkbox"
      :checked (:value field)
      :onChange (fn [e]
                  (modifier assoc :value (.-checked (.-target e))))
      :title (:title field)}]))
