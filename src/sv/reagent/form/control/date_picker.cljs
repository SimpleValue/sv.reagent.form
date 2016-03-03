(ns sv.reagent.form.control.date-picker
  (:require [reagent.core :as r]
            cljsjs.moment
            cljsjs.react-datepicker))

(def DatePicker (r/adapt-react-class js/DatePicker))

(defn date-picker [modifier]
  [DatePicker
   {:selected (when-let [date (modifier)]
                (.utc js/moment date))
    :onChange (fn [moment-date]
                (modifier (fn [_] (.toDate moment-date))))}])
