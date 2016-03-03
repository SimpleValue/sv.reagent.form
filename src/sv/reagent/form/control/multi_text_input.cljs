(ns sv.reagent.form.control.multi-text-input
  (:require [sv.reagent.form.control.text-input :as t]
            [clojure.string :as str]
            [sv.reagent.form.bind :as b]))

(defn multi-text-input [modifier]
  [:input
   (merge
    (t/text-input-attrs (modifier))
    {:value (b/get-value
             modifier
             (fn [val]
               (str/join ", " val)))
     :onChange (b/bind-input-value modifier)
     :onBlur (b/bind-value
              modifier
              (fn [value]
                (set
                 (sort
                  (distinct
                   (filter
                    seq
                    (map str/trim (str/split value #","))))))))})])
