(ns password-generator-clojure.core
  (:require
    [reagent.core :as r]
    [reagent.dom :as d]
    [password-generator-clojure.generators :as g]
    [antizer.reagent :as ant]
    ))

(defonce options (r/atom {
                          :length             8
                          :capitals?          true
                          :numbers?           true
                          :symbols?           true
                          :generated-password ""
                          }))

(defn set-password []
  (swap! options assoc :password (g/generate-password (:length @options) (:capitals? @options) (:numbers? @options) (:symbols? @options))))

(defn home-page []
  [:div
   [ant/divider "Options"]

   [:span "Length : "
    [ant/input-number {:min           8
                       :max           112
                       :default-value (:length @options)
                       :on-change     (fn [val] (swap! options assoc :length val))}]]

   [:div {:class "inputs"}
    [ant/checkbox {:onChange (fn [x] (swap! options assoc :capitals? (not (:capitals? @options))))
                   :checked  (:capitals? @options)} "Capitals"]
    [ant/checkbox {:onChange (fn [x] (swap! options assoc :numbers? (not (:numbers? @options))))
                   :checked  (:numbers? @options)} "Numbers"]
    [ant/checkbox {:onChange (fn [x] (swap! options assoc :symbols? (not (:symbols? @options))))
                   :checked  (:symbols? @options)} "Symbols"]]

   [ant/button {:type    "primary"
                :id      "generate"
                :onClick set-password} "Generate Password"]

   [ant/divider]

   (when (not (empty? (:password @options))) [ant/tooltip {:title "Click to copy password"}
                                              [ant/button {:type     "dashed"
                                                           :id       "password"
                                                           :icon     "copy"
                                                           :size     "large"
                                                           :on-click (fn [] (.execCommand js/document "copy"))}
                                               (:password @options)]])])

;; -------------------------
;; Initialize app

(defn mount-root []
  (d/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
