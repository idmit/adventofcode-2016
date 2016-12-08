(ns adventofcode-2016.day-1
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn shift-vec-left
  [v]
  (vec (concat (drop 1 v) (take 1 v))))

(defn shift-vec-right
  [v]
  (vec (concat (take-last 1 v) (drop-last 1 v))))

(defn move
  [{:keys [state instr]}]
  (let [turn-fn (case (first instr)
                  \L shift-vec-right
                  \R shift-vec-left)
        new-dir (-> state :dir turn-fn)
        dist (->> instr (drop 1) (apply str) read-string)]
    (-> state
        (assoc :dir new-dir)
        (update-in [:pos (first new-dir)] (partial + dist)))))

(def route
  (-> "input/day_1.txt"
      io/resource
      slurp
      (str/split #", ")))

(defn norm-state
  [{:keys [pos] :as state}]
  (let [max-v (apply min ((juxt :up :down) pos))
        dec-v #(- % max-v)
        max-h (apply min ((juxt :left :right) pos))
        dec-h #(- % max-h)]
    (update state :pos (fn [p]
                         (-> p
                             (update :up dec-v)
                             (update :down dec-v)
                             (update :left dec-h)
                             (update :right dec-h))))))

(defn manh
  [pos]
  (apply + (vals pos)))

(def final-pos
  (:pos (reduce (fn [state instr]
                  (-> {:state state
                       :instr instr}
                      move
                      norm-state))
                {:pos {:up 0 :right 0 :down 0 :left 0}
                 :dir [:up :right :down :left]}
                route)))

