(ns scurvy.character
  (:require [scurvy.tables :as tables]
            [scurvy.random :as rnd]
            [scurvy.name :as names]))

(defn roll-die [sides]
  (rnd/roll-die sides))

(defn stat-roll
  []
  (apply min (for [_x (range 3)]
               (roll-die 6))))

(defn generate-stats []
  (reduce merge (for [stat-name ["wisdom" "strength" "dexterity" "intelligence" "charisma" "constitution"]]
                  (let [roll (stat-roll)]
                    {(keyword (str stat-name "-bonus")) roll
                     (keyword (str stat-name "-defense")) (+ 10 roll)}))))



(defn add-arrows-if-neccesary [inp-items]
  (if (reduce #(or %1 (:ammo %2)) false inp-items)
    (conj inp-items (tables/build-item "Arrows, 20"))
    inp-items))

(defn add-items "Adds all non-nil items to the first list, keeping it flat"
  [inp-inventory item-list]
  (into inp-inventory (filter map? item-list)))

(defn generate-gear []
  (-> '()
      (conj (rnd/choose-from tables/weapon-gear))
      ;; if we have a bow, add arrows here
      (add-arrows-if-neccesary)
      (conj (rnd/choose-from tables/dungeoneering-gear))
      (conj (rnd/choose-from tables/dungeoneering-gear))
      (conj (rnd/choose-from tables/general-gear-1))
      (conj (rnd/choose-from tables/general-gear-2))
      (conj (tables/build-item "Travel Rations (1 Day)"))
      (conj (tables/build-item "Travel Rations (1 Day)"))))

(defn get-armor [armor-roll]
  (cond
    (>= 3 armor-roll)  (identity nil)
    (>= 14 armor-roll)  (tables/build-item "Gambeson" 1 :armor-adjust 2 :quality 3)
    (>= 19 armor-roll)  (tables/build-item "Brigandine" 2 :armor-adjust 3 :quality 4)
    (>= 20 armor-roll)  (tables/build-item "Chain" 3 :armor-adjust 4 :quality 5)))

(defn get-shield [shield-roll]
  (cond
    (>= 13 shield-roll)  (identity nil)
    (>= 16 shield-roll)  (tables/build-item "Helmet" 1 :armor-adjust 1 :quality 1)
    (>= 19 shield-roll)  (tables/build-item "Shield" 2 :armor-adjust 1 :quality 1)
    (>= 20 shield-roll)  (list
                          (tables/build-item "Helmet" 1 :armor-adjust 1 :quality 1)
                          (tables/build-item "Shield" 2 :armor-adjust 1 :quality 1))))

(defn generate-armor []
  (let [armor-roll (roll-die 20)
        shield-roll (roll-die 20)
        armor (get-armor armor-roll)
        secondary (get-shield shield-roll)]
    (if (list? secondary)
      (into secondary armor)
      (list armor secondary))))


(defn generate-alignment []
  (let [roll (roll-die 20)]
    (cond
      (<= roll 5) "Law"
      (<= roll 15) "Neutrality"
      (<= roll 20) "Chaos")))


(defn generate-backstory []
  (reduce merge
          {:alignment (generate-alignment)}
          (for [element-key (keys tables/backstory-lists)]
            {element-key (rnd/choose-from (tables/backstory-lists element-key))})))


(defn generate-inventory []
  (let [item-list (-> '()
                      (add-items (generate-gear))
                      (add-items (generate-armor)))
        slot-count (reduce #(+ %1 (:slots %2)) 0 item-list)]
    {:slot-info {:slot-count slot-count}
     :items item-list}))


(defn perform-armor-calculation [inp-character]
  (let [item-list (get-in inp-character [:inventory :items])
        armor-bonus (max 1 (reduce #(+ %1 (:armor-adjust %2)) 0 item-list))]
    (-> inp-character
        (assoc-in [:stats :armor-bonus] armor-bonus)
        (assoc-in [:stats :armor-defense] (+ 10 armor-bonus)))))


(defn generate-hitpoints []
  (roll-die 8))

(defn generate-character
  [random-seed]
  (rnd/set-seed! random-seed)
  (perform-armor-calculation
   {:stats (generate-stats)
    :health (generate-hitpoints)
    :name (names/generate-name)
    :inventory (generate-inventory)
    :backstory (generate-backstory)}))