(ns scurvy.random
  (:require [cljx-sampling.random :as random]))

(def ^:dynamic prng (random/create))

(defn roll-die [sides]
  (inc (random/next-int! prng sides)))

(defn choose-from [inp-list]
  (nth inp-list (dec (roll-die (count inp-list)))))

(defn set-seed!
  ([] (set-seed! (rand)))
  ([inp-seed]
   (set! prng (random/create inp-seed))))