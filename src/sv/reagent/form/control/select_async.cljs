(ns sv.reagent.form.control.select-async
  (:require [reagent.core :as r]
            [goog.string :as gstring]
            [cljsjs.react-select]
            [cljs.reader :as reader])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(def SelectAsync (r/adapt-react-class js/Select.Async))

(defn- load-options-fn [get-options]
  (fn [input callback]
    (go
      (let [options (<! (get-options input))]
        (callback
         nil
         (clj->js
          {:options (map
                     #(update % :value pr-str)
                     (if (zero? (count input))
                       (take 100 options)
                       (filter
                        #(gstring/caseInsensitiveContains
                          (:label %)
                          input)
                        options)))
           :complete false}))))))

(defn- read-selected [selected-js opts]
  (let [data (js->clj
              selected-js
              :keywordize-keys true)
        read-fn #(update % :value (fn [value]
                                    (when-not (nil? value)
                                      ((or (:read-string opts) reader/read-string)
                                       value))))]
    (if (sequential? data)
      (map read-fn data)
      (read-fn data))))

(defn select-one-async
  [{:keys [model get-options disabled onChange] :as opts}]
  [SelectAsync
   (merge
    {:multi false
     :disabled disabled
     :value (:selected @model)
     :onChange (fn [selected]
                 (let [s (read-selected selected opts)]
                   (swap!
                    model
                    assoc :selected s :value (:value s)))
                 (when onChange
                   (onChange selected)))
     :placeholder (:placeholder @model)
     :loadOptions (load-options-fn get-options)}
    (dissoc opts :model :get-options :onChange))])

(defn select-multi-async
  [{:keys [model get-options disabled onChange preserve-order] :as opts}]
  [SelectAsync
   (merge
    {:multi true
     :disabled disabled
     :value (clj->js (map
                      #(update % :value pr-str)
                      (:selected @model)))
     :onChange (fn [selected]
                 (let [s (read-selected selected opts)]
                   (swap!
                    model
                    assoc
                    :selected (distinct s)
                    :value ((if (true? preserve-order)
                              distinct
                              set)
                            (map :value s))))
                 (when onChange
                   (onChange selected)))
     :placeholder (:placeholder @model)
     :loadOptions (load-options-fn get-options)}
    (dissoc opts :model :get-options :onChange))])
