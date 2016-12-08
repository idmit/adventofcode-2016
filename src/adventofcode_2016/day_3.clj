(ns adventofcode-2016.day-3
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def row-side-specs
  (->> "input/day_3.txt"
       io/resource
       slurp
       (str/split-lines)
       (map #(mapv read-string (-> % str/trim (str/split #"\s+"))))))

(def col-side-specs
  (->> (apply map vector row-side-specs)
       (apply concat)
       (partition 3)
       (map vec)))

(defn nb-triangles
  [specs]
  (reduce (fn [nb-triangles [a b c]]
            (if (or (>= a (+ b c))
                    (>= b (+ a c))
                    (>= c (+ a b)))
              nb-triangles
              (inc nb-triangles))
            ) 0 specs))

(def row-nb-triangles
  (nb-triangles row-side-specs))

(def col-nb-triangles
  (nb-triangles col-side-specs))
