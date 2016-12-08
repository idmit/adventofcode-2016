(defproject adventofcode-2016 "0.1.0-SNAPSHOT"
  :description "Advent of Code 2016"
  :url "github.com/idmit/adventofcode-2016"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :main ^:skip-aot adventofcode-2016.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
