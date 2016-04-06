(ns sv.reagent.form.validation)

(defn error-check [pred error-message]
  (fn [value]
    (when-not (pred value)
      error-message)))

(defn validate [validation-spec value]
  (reduce
   (fn [value [key error-check]]
     (if (contains? (get value key) :value)
       (update-in
        value [key :error]
        (fn [error]
          (or error
              (error-check (get-in value [key :value])))))
       value))
   value
   validation-spec))

(defn form-errors? [value required-keys]
  (some
   :error
   (vals
    (select-keys
     value
     required-keys))))

(defn validate-form [value required-keys]
  (reduce
   (fn [v required-key]
     (update-in v [required-key :value] identity))
   value
   required-keys))

(defn comp-checks [& checks]
  (fn [value]
    (some
     (fn [check]
       (check value))
     checks)))
