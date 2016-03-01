(ns sv.reagent.form.group)

(defn error-block [val]
  (when-let [error (:error val)]
    [:span.help-block error]))

(defn form-group [val & content]
  (let [value (:value val)
        error (:error val)]
    (apply
     conj
     [:div
      {:class (str "form-group " (when error "has-error"))}]
     content)))

(defn labeled-form-group
  "Form group for a horizontal form (add class form-horizontal to the
   form tag)."
  [field & content]
  [form-group
   field
   [:label
    {:for (str (:id field))
     :class "col-sm-2 control-label"
     :title (:title field)}
    (or (:label field) (name (:id field "<no id>")))]
   [:div.col-sm-10
    (apply vector :div content)
    [error-block field]]])
