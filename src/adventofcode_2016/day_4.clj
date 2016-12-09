(ns adventofcode-2016.day-4
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def room-specs
  (->> "input/day_4.txt"
       io/resource
       slurp
       (str/split-lines)
       (mapv #(str/split % #"-"))
       (map (fn [room]
              (let [name (->> room (drop-last 1) str/join)
                    [id sum] (->> room last (re-find #"(\d+)\[(.*)\]") rest vec)]
                {:name name
                 :id   (read-string id)
                 :sum  sum})))))

(defn max-key-n
  [m n]
  {:pre [(<= n (count m))]}
  (loop [max-entries []
         tm m
         tn (count m)]
    (if (> tn 0)
      (let [entry (apply max-key val tm)]
        (recur (conj max-entries entry)
               (dissoc tm (key entry))
               (dec tn)))
      (->> max-entries
           (partition-by val)
           (map (comp sort keys))
           (apply concat)
           (take n)))))


(defn real-rooms
  [room-specs]
  (filter (fn [{:keys [name sum]}]
            (= (-> name frequencies (max-key-n 5) str/join)
               sum))
          room-specs))

(def real-rooms-sector-id-sum
  (->> room-specs
       real-rooms
       (map :id)
       (apply +)))

(defn cycle-char
  [n ch]
  (let [c (int ch)
        a-code (int \a)
        z-code (int \z)
        shift (rem n (+ z-code (- a-code) 1))
        new-code (if (> shift (+ z-code (- c)))
                   (+ a-code (- shift (- z-code c) 1))
                   (+ c shift))]
    (char new-code)))

(def storage-room (->> (real-rooms room-specs)
                       (filter (fn [room]
                                 (let [s (str/join (map (partial cycle-char (:id room)) (:name room)))]
                                   (= s "northpoleobjectstorage")))
                               )
                       first))

(println storage-room)
