(ns sv.reagent.form.modifier)

(defn atom-modifier
  [atom]
  (fn
    ([] @atom)
    ([f & args]
     (apply swap! atom f args))))

(defn path [modifier path]
  (assert (sequential? path))
  (fn
    ([] (get-in (modifier) path))
    ([f & args]
     (apply modifier update-in path f args))))

(defn modify-read [modifier f & args]
  (fn
    ([] (apply f (modifier) args))
    ([f & args]
     (apply modifier f args))))

(defn read-merge [modifier map]
  (modify-read modifier merge map))

(defn get-with [modifier key data]
  (read-merge
   (path modifier [key])
   data))
