(ns sv.reagent.form.bind)

(defn get-value
  ([modifier f]
   (let [field (modifier)]
     (or (:input-value field)
         (f (:value field)))))
  ([modifier]
   (get-value modifier identity)))

(defn extract-text-value [e]
  (let [target (.-target e)]
    (if (= (.-type target) "checkbox")
      (.-checked target)
      (.-value target))))

(defn bind-input-value
  ([modifier f]
   (fn [e]
     (modifier assoc :input-value (f (extract-text-value e)))))
  ([modifier]
   (bind-input-value modifier identity)))

(defn bind-value
  ([modifier f]
   (fn [e]
     (modifier
      (fn [val]
        (if-let [input-value (:input-value val)]
          (-> val
              (assoc :value (f input-value))
              (dissoc :input-value))
          val)))))
  ([modifier]
   (bind-value modifier identity)))

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
