(ns sv.reagent.form.prod)

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))
