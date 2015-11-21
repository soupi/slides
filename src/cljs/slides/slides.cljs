(ns slides.slides
    (:require [cljs.core.match :refer-macros [match]]))

;; ---------------------------------
;; Slides EDSL

(def none :empty)

(defn title [string]
  [:title string])

(defn ulist [& elements]
  [:ulist elements])

(defn image [url]
  [:image url])

(defn text [string]
  [:text string])

(defn h-align [& elements]
 [:h-align elements])

(defn v-align [& elements]
 [:v-align elements])

(defn left [element]
  [:left element])

(defn right [element]
  [:right element])

(defn center [element]
  [:center element])

(defn top [element]
  [:top element])

(defn bottom [element]
  [:bottom element])


;; ------------------
;; eval

(declare render)

(defn wrap-render-elements [outer inner elements]
  (apply
    vector outer
    (mapv (comp (partial vector inner)
                (partial render ()))
         elements)))

(defn render [dimentions element]
  (match [element]
    [[:title string]] [:h1 string]
    [[:text string]] [:p string]
    [[:v-align elements]] (wrap-render-elements :span :div  elements)
    [[:h-align elements]] (wrap-render-elements :span :span elements)
    [[:ulist   elements]] (wrap-render-elements :ul :li elements)))

;; -------------------------
;; Slides

(def slide1
  (v-align
    (title "Hello Title!")
    (text "First row text!")
    (text "Second row text!")))

(def slide2
  (v-align
    (title "A list with 2 bullets")
    (ulist
      (text "bullet 1")
      (text "bullet 2"))))

