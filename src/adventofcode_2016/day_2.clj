(ns adventofcode-2016.day-2
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def in-command-seqs
  (-> "input/day_2.txt"
      io/resource
      slurp
      (str/split-lines)))

(defn standard-move
  [[r c] command]
  (case command
    \U [(max 0 (- r 1)) c]
    \D [(min 2 (+ r 1)) c]
    \L [r (max 0 (- c 1))]
    \R [r (min 2 (+ c 1))]))

(defn move-seq
  [move-fn start-pos command-seq]
  (reduce move-fn start-pos command-seq))

(defn pos-seq
  [move-fn start-pos command-seqs]
  (-> (reduce (fn [positions command-seq]
                (->> (move-seq move-fn (peek positions) command-seq)
                     (conj positions))) [start-pos] command-seqs)
      rest))

(defn standard-pos->code
  [[r c]]
  (+ (* r 3) c 1))

(def standard-code-seq
  (mapv standard-pos->code (pos-seq standard-move [1 1] in-command-seqs)))

(def alien-bounds
  [[2 2] [1 3] [0 4] [1 3] [2 2]])

(defn alien-move
  [[r c] command]
  (case command
    \U [(max (get-in alien-bounds [c 0]) (- r 1)) c]
    \D [(min (get-in alien-bounds [c 1]) (+ r 1)) c]
    \L [r (max (get-in alien-bounds [r 0]) (- c 1))]
    \R [r (min (get-in alien-bounds [r 1]) (+ c 1))]))

(defn alien-pos->code
  [[r c]]
  (let [num-code (->> (take r alien-bounds)
                 (map (partial apply (comp (partial + 1) - -)))
                 (apply +)
                 (+ (- (+ c 1) (get-in alien-bounds [r 0]))))]
    (if (< num-code 10)
      num-code
      ((vec "DCBA") (- 13 num-code)))))

(def alien-code-seq
  (mapv alien-pos->code (pos-seq alien-move [0 2] in-command-seqs)))
