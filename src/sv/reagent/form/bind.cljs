(ns sv.reagent.form.bind)

(defn get-value
  ([model f]
   (let [field @model]
     (or (:input-value field)
         (f (:value field)))))
  ([model]
   (get-value model identity)))

(defn extract-text-value [e]
  (.-value (.-target e)))

(defn bind-input-value
  ([model f]
   (fn [e]
     (swap! model assoc :input-value (f (extract-text-value e)))))
  ([model]
   (bind-input-value model identity)))

(defn bind-value
  ([model f]
   (fn [e]
     (swap!
      model
      (fn [val]
        (if-let [input-value (:input-value val)]
          (-> val
              (assoc :value (f input-value))
              (dissoc :input-value))
          val)))))
  ([model]
   (bind-value model identity)))

(defn extract-entity [value keys]
  (let [keys (set keys)]
    (into
     {}
     (map
      (fn [[k v]]
        [k (:value v)])
      (filter
       (fn [[k v]]
         (and (contains? keys k)
              (:value v)))
       value)))))
