(ns sv.reagent.form.control.select-async
  (:require [reagent.core :as r]
            [goog.string :as gstring]
            [cljsjs.react-select])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(def SelectAsync (r/adapt-react-class js/Select.Async))

(defn- load-options-fn [get-options]
  (fn [input callback]
    (go
      (let [options (<! (get-options input))]
        (callback
         nil
         (clj->js
          {:options (if (zero? (count input))
                      (take 100 options)
                      (filter
                       #(gstring/caseInsensitiveContains
                         (:label %)
                         input)
                       options))
           :complete false}))))))

(defn select-one-async
  [{:keys [model get-options disabled onChange] :as opts}]
  [SelectAsync
   (merge
    {:multi false
     :disabled disabled
     :value (:selected @model)
     :onChange (fn [selected]
                 (let [s (js->clj selected :keywordize-keys true)]
                   (swap!
                    model
                    assoc :selected s :value (:value s)))
                 (when onChange
                   (onChange selected)))
     :placeholder (:placeholder @model)
     :loadOptions (load-options-fn get-options)}
    (dissoc opts :model :get-options :onChange))])

(defn select-multi-async
  [{:keys [model get-options disabled onChange] :as opts}]
  [SelectAsync
   (merge
    {:multi true
     :disabled disabled
     :value (clj->js (:selected @model))
     :onChange (fn [selected]
                 (let [s (js->clj selected :keywordize-keys true)]
                   (swap!
                    model
                    assoc
                    :selected (distinct s)
                    :value (set (map :value s))))
                 (when onChange
                   (onChange selected)))
     :placeholder (:placeholder @model)
     :loadOptions (load-options-fn get-options)}
    (dissoc opts :model :get-options :onChange))])
