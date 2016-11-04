(ns sv.reagent.form.control.checkbox
  (:require [sv.reagent.form.control.text-input :as t]
            [sv.reagent.form.bind :as b]))

(defn checkbox [model]
  (let [m @model]
    [:input
     {:id (str (:id m))
      :type "checkbox"
      :defaultChecked (:value @model)
      :onChange (fn [e]
                  (swap! model assoc :value (.-checked (.-target e))))
      :title (:title m)}]))
