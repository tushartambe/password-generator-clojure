(ns password-generator-clojure.prod
  (:require
    [password-generator-clojure.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
