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

(def concatv
  (partial
    (comp
      (partial apply vector)
      concat)))

(declare render)

(defn wrap-render-elements [outer inner elements]
  (apply
    concatv (seq outer)
    (vector
      (mapv (comp (partial concatv (seq inner))
                  (partial vector)
                  (partial render ()))
         elements))))

(defn render [dimentions element]
  (match [element]
    [[:title string]] [:h1 string]
    [[:text  string]] [:p  string]
    [[:v-align elements]] (wrap-render-elements [:span] [:div] elements)
    [[:h-align elements]] (wrap-render-elements [:span.flexbox] [:span] elements)
    [[:ulist   elements]] (wrap-render-elements [:ul] [:li] elements)))

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

(def slide3
  (h-align slide1 slide2))

(def slide4
  (v-align slide3 slide3))

