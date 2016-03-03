(ns sv.reagent.form.control.integer-input
  (:require [sv.reagent.form.control.text-input :as t]
            [goog.string :as gstring]
            [sv.reagent.form.bind :as b]
            [clojure.string :as str]))

(defn integer-input [modifier]
  [:input
   (merge
    (t/text-input-attrs (modifier))
    {:value (b/get-value modifier str)
     :onChange (fn [e]
                 (let [value (.-value (.-target e))]
                   (when (gstring/isNumeric
                          (str/replace value #"^-" ""))
                     ((b/bind-input-value modifier) e))))
     :onBlur (b/bind-value
              modifier
              (fn [v]
                (gstring/parseInt v)))})])
