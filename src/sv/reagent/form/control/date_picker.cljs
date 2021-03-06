(ns sv.reagent.form.control.date-picker
  (:require [reagent.core :as r]
            cljsjs.moment
            cljsjs.react-datepicker))

(def DatePicker (r/adapt-react-class js/DatePicker))

(defn date-picker [model]
  [DatePicker
   {:selected (when-let [date (:value @model)]
                (.utc js/moment date))
    :onChange (fn [moment-date]
                (let [date (js/Date.
                            (.UTC js/Date
                                  (.year moment-date)
                                  (.month moment-date)
                                  (.date moment-date)))]
                  (swap! model assoc :value date)))}])
