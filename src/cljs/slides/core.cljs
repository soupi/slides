(ns slides.core
    (:require [reagent.core :as reagent :refer [atom]]
              [slides.slides :as slides]
              [reagent.session :as session]
              [secretary.core :as secretary :include-macros true]
              [accountant.core :as accountant]))

;; -------------------------
;; Views

(defn home-page []
  [:div [:h2 "Welcome to slides"]
   (slides/render () slides/slide4)
   [:div [:a {:href "/about"} "go to about page"]]
   [:div [:a {:href "/another"} "go to another page"]]])

(defn about-page []
  [:div [:h2 "About slides"]
   (slides/render () slides/slide1)
   [:div [:a {:href "/"} "go to the home page"]]
   [:div [:a {:href "/another"} "go to another page"]]])

(defn another-page []
  [:div [:h2 "Another slides"]
   (slides/render () slides/slide3)
   [:div [:a {:href "/"} "go to the home page"]]])

(defn current-page []
  [:div [(session/get :current-page)]])

;; -------------------------
;; Routes

(secretary/defroute "/" []
  (session/put! :current-page #'home-page))

(secretary/defroute "/about" []
  (session/put! :current-page #'about-page))

(secretary/defroute "/another" []
  (session/put! :current-page #'another-page))

;; -------------------------
;; Initialize app

(defn mount-root []
  (reagent/render [current-page] (.getElementById js/document "app")))

(defn init! []
  (accountant/configure-navigation!)
  (accountant/dispatch-current!)
  (mount-root))
