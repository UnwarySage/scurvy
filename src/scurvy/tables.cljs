(ns scurvy.tables
  (:require [clojure.string :as str]))


(def dungeoneering-gear-strings
  '("Rope, 50ft"
    "Crowbar"
    "Lantern"
    "Pole, 10ft"
    "Pulleys"
    "Tinderbox"
    "Lamp oil"
    "Sack"
    "Candles, 5"
    "Grappling hook"
    "Padlock"
    "Tent"
    "Chain, 10ft"
    "Hammer"
    "Manacles"
    "Spikes, 5"
    "Chalk"
    "Waterskin "
    "Mirror"
    "Torches, 5"))

(def general-gear-1-strings
  '("Air bladder"
    "Saw"
    "Fishing rod"
    "Net"
    "Bear trap"
    "Bucket"
    "Marbles"
    "Tongs"
    "Shovel"
    "Caltrops"
    "Glue"
    "Lockpicks"
    "Bellows"
    "Chisel"
    "Pick"
    "Metal file"
    "Grease"
    "Drill"
    "Hourglass"
    "Nails"))

(def general-gear-2-strings
  '("Incense"
    "Bottle"
    "Fake jewels"
    "Face paint"
    "Sponge"
    "Soap"
    "Blank book"
    "Whistle"
    "Musical Instrument"
    "Spyglass"
    "Card deck"
    "Lens"
    "Perfume"
    "Tar pot"
    "Dice set"
    "Quill & Ink"
    "Horn"
    "Twine"
    "Cook pots"
    "Small bell"))

(def physique-strings
  '("Athletic"
    "Hulking"
    "Short"
    "Stout"
    "Brawny"
    "Lanky"
    "Sinewy"
    "Tiny"
    "Corpulent"
    "Ripped"
    "Slender"
    "Towering"
    "Delicate"
    "Rugged"
    "Flabby"
    "Willowy"
    "Gaunt"
    "Scrawny"
    "Statuesque"
    "Wiry"))

(def face-strings
  '("Bloated"
    "Elongated"
    "Impish"
    "Sharp"
    "Blunt"
    "Patrician"
    "Narrow"
    "Soft"
    "Bony"
    "Pinched"
    "Rat-like"
    "Square"
    "Chiseled"
    "Hawkish"
    "Round"
    "Wide"
    "Delicate"
    "Broken"
    "Sunken"
    "Wolfish"))

(def skin-strings
  '("Battle Scar"
    "Oily"
    "Reeking"
    "Sunburned"
    "Birthmark"
    "Pale"
    "Tattooed"
    "Tanned"
    "Burn Scar"
    "Perfect"
    "Rosy"
    "War Paint"
    "Dark"
    "Pierced"
    "Rough"
    "Weathered"
    "Makeup"
    "Pocked"
    "Sallow"
    "Whip Scar"))

(def hair-strings
  '("Bald"
    "Disheveled"
    "Limp"
    "Ponytail"
    "Braided"
    "Dreadlocks"
    "Long"
    "Silky"
    "Bristly"
    "Filthy"
    "Luxurious"
    "Topknot"
    "Cropped"
    "Frizzy"
    "Mohawk"
    "Wavy"
    "Curly"
    "Greased"
    "Oily"
    "Wispy"))

(def clothing-strings
  '("Antique"
    "Elegant"
    "Foreign"
    "Patched"
    "Bloody"
    "Fashionable"
    "Frayed"
    "Perfumed"
    "Ceremonial"
    "Filthy"
    "Frumpy"
    "Rancid"
    "Decorated"
    "Flamboyant"
    "Livery"
    "Torn"
    "Eccentric"
    "Stained"
    "Oversized"
    "Undersized"))

(def virtue-strings
  '("Ambitious"
    "Disciplined"
    "Honorable"
    "Merciful"
    "Cautious"
    "Focused"
    "Humble"
    "Righteous"
    "Courageous"
    "Generous"
    "Idealistic"
    "Serene"
    "Courteous"
    "Gregarious"
    "Just"
    "Stoic"
    "Curious"
    "Honest"
    "Loyal"
    "Tolerant"))

(def vice-strings
  '("Aggressive"
    "Deceitful"
    "Lazy"
    "Suspicious"
    "Arrogant"
    "Flippant"
    "Nervous"
    "Vain"
    "Bitter"
    "Gluttonous"
    "Prejudiced"
    "Vengeful"
    "Cowardly"
    "Greedy"
    "Reckless"
    "Wasteful"
    "Cruel"
    "Irascible"
    "Rude"
    "Whiny"))

(def speech-strings
  '("Blunt"
    "Droning"
    "Mumbling"
    "Dialect"
    "Booming"
    "Flowery"
    "Precise"
    "Slow"
    "Breathy"
    "Formal"
    "Quaint"
    "Squeaky"
    "Cryptic"
    "Gravelly"
    "Rambling"
    "Stuttering"
    "Drawling"
    "Hoarse"
    "Rapid-fire"
    "Whispery"))

(def background-strings
  '("Alchemist"
    "Cleric"
    "Magician"
    "Performer"
    "Beggar"
    "Cook"
    "Mariner"
    "Pickpocket"
    "Butcher"
    "Cultist"
    "Mercenary"
    "Smuggler"
    "Burglar"
    "Gambler"
    "Merchant"
    "Student"
    "Charlatan"
    "Herbalist"
    "Outlaw"
    "Tracker"))

(def misfortune-strings
  '("Abandoned"
    "Defrauded"
    "Framed"
    "Pursued"
    "Addicted"
    "Demoted"
    "Haunted"
    "Rejected"
    "Blackmailed"
    "Discredited"
    "Kidnapped"
    "Replaced"
    "Condemned"
    "Disowned"
    "Mutilated"
    "Robbed"
    "Cursed"
    "Exiled"
    "Poor"
    "Suspected"))

(def backstory-lists
  {:physique physique-strings
   :face face-strings
   :skin skin-strings
   :hair hair-strings
   :clothing clothing-strings
   :virtue virtue-strings
   :vice vice-strings
   :speech speech-strings
   :background background-strings
   :misfortune misfortune-strings})

(defn build-item
  ([item-name slots & item-extras]
   (merge
    {:item-name item-name
     :slots slots} (apply hash-map item-extras)))
  ([item-name]
   (build-item item-name 1)))


(def dungeoneering-gear
  (map build-item dungeoneering-gear-strings))

(def general-gear-1
  (map build-item general-gear-1-strings))

(def general-gear-2
  (map build-item general-gear-2-strings))

(def weapon-gear
  [(build-item "Dagger" 1 :damage "1d6" :quality 3 :hands 1)
   (build-item "Cudgel" 1 :damage "1d6" :quality 3 :hands 1)
   (build-item "Sickle" 1 :damage "1d6" :quality 3 :hands 1)
   (build-item "Staff" 1 :damage "1d6" :quality 3 :hands 1)
   (build-item "Spear" 2 :damage "1d8" :quality 3 :hands 1)
   (build-item "Mace" 2 :damage "1d8" :quality 3 :hands 1)
   (build-item "Axe" 2 :damage "1d8" :quality 3 :hands 1)
   (build-item "Flail" 2 :damage "1d8" :quality 3 :hands 1)
   (build-item "Halberd" 3 :damage "1d10" :quality 3 :hands 2)
   (build-item "War Hammer" 3 :damage "1d10" :quality 3 :hands 2)
   (build-item "Long Sword" 3 :damage "1d10" :quality 3 :hands 2)
   (build-item "Battle Axe" 3 :damage "1d10" :quality 3 :hands 2)
   (build-item "Sling" 1 :damage "1d4" :hands 1 :quality 3 :ammo false)
   (build-item "Bow" 2 :damage "1d6" :hands 2 :quality 3 :ammo true)
   (build-item "Crossbow" 3 :damage "1d8" :hands 2 :ammo true)])

(defn- copy-parse [inp-string]
  (map str/trim (str/split inp-string #"[ \d+ |\n] ")))