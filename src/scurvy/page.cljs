(ns ^:figwheel-hooks scurvy.page
  (:require [reagent.dom :as r.dom]
            [reagent.core :as r.core]
            [scurvy.character :as char]))


(defonce state (r.core/atom nil))


(defn get-present-seed! []
  (.. js/window -location -hash))


(defn randomize-url! []
  (set! (.. js/window -location -hash) (rand-int (.. js/Number -MAX_SAFE_INTEGER))))


(defn load-or-set-url! []
  (when (=  "" (get-present-seed!))
    (randomize-url!)))


(defn set-character-from-url! []
  (reset! state (char/generate-character (get-present-seed!))))


(defn generate-new-character []
  (randomize-url!)
  (set-character-from-url!))


(defn stat-readout [stat-name stat-map]
  [:div.level.is-mobile
   [:div.level-item [:p (get stat-map (keyword (str stat-name "-defense")))]]
   [:div.level-item [:p.is-capitalized.has-text-weight-light stat-name]]
   [:div.level-item [:p (get stat-map (keyword (str stat-name "-bonus")))]]])

(defn stats-panel [inp-stats]
  [:div.column.is-one-quarter
   [:div.box
    [:div.level.is-mobile
     [:div.level-item [:p.has-text-weight-semibold "Defense"]]
     [:div.level-item [:p.has-text-weight-semibold "Stat"]]
     [:div.level-item [:p.has-text-weight-semibold "Bonus"]]]
    (for [stat ["wisdom" "strength" "intelligence" "constitution" "charisma" "dexterity" "armor"]]
      [:div {:key stat}
       [:hr]
       [stat-readout stat inp-stats]])]])


(defn inventory-item [inp-item]
  [:tr
   [:td (:item-name inp-item)]
   [:td (:slots inp-item)]
   [:td (apply str
               (interpose ", "
                          (filter identity
                                  [(when (:damage inp-item) (str "Damage " (:damage inp-item)))
                                   (when (:quality inp-item) (str "Quality " (:quality inp-item)))
                                   (when (:armor-adjust inp-item) (str "Armor " (:armor-adjust inp-item)))
                                   (when (:hands inp-item) (if (= 1 (:hands inp-item))
                                                             "1 Handed"
                                                             "2 Handed"))])))]])


(defn inventory-table [inp-inventory]
  [:table.table.is-fullwidth
   [:thead
    [:tr
     [:th "Item"]
     [:th "Slots"]
     [:th "Notes"]]]
   [:tbody
    (for [item inp-inventory]
      [inventory-item item])]])


(defn inventory-panel
  [inp-character]
  (let [slots-max (get-in inp-character [:stats :constitution-defense])
        slots-occupied  (get-in inp-character [:inventory :slot-info :slot-count])]
    [:div.column.is-one-half
     [:div.box
      [inventory-table (get-in inp-character [:inventory :items])]
      [:div.level
       [:div.level-item
        [:h1.is-title (str "Slots " slots-occupied "/" slots-max)]]]]]))


(defn background-panel [inp-character]
  (let [backstory (:backstory inp-character)]
    [:div.column.is-one-quarter
     [:div.box
      [:table.table.is-fullwidth
       [:tbody
        (for [[pres-key pres-value] backstory]
          [:tr {:key pres-key}
           [:td [:p.is-capitalized.has-text-weight-light pres-key]]
           [:td pres-value]])]]]]))

(defn vitals-bar [inp-character]
  [:div.box
   [:div.level
    (when (:name inp-character)
      [:div.level-item
       [:div
        [:p (:name inp-character)]]])
    [:div.level-item
     [:div
      [:p (str (:health inp-character) " Hitpoints")]]]
    [:div.level-item
     [:div
      [:p "Combat Speed 40ft"]]]
    [:div.level-item
     [:div
      [:p "Exploration Speed 120ft"]]]]])

(defn warnings-bar [inp-character]
  (let [slots-max (get-in inp-character [:stats :constitution-defense])
        slots-occupied  (get-in inp-character [:inventory :slot-info :slot-count])]
    [:div.level
     (when (> slots-occupied slots-max)
       [:div.level-item [:div.notification.is-warning
                         [:p "You rolled too many items for your slot limit. Talk to your GM about this."]]])
     (when (> 5 (:health inp-character))
       [:div.level-item
        [:div.notification.is-warning
         [:p "Some GMs will let you re-roll your hitpoints"]]])]))

(defn topbar []
  [:div.level
   [:div.level-left
    [:div.has-text-centered-mobile
     [:p.title.is-size-1 "Scurvy"]
     [:p.subtitle.is-size-3 "A character generator for Knave"]]]
   [:div.level-right
    [:div.has-text-centered
     [:button.button
      {:on-click (fn [e]
                   (.preventDefault e)
                   (generate-new-character))}
      "Another!"]]]])

(defn footer []
  [:footer
   [:div.content.has-text-centered
    [:p "Save a Knave by bookmarking it, or generate as many as you want."]
    [:p
     [:a {:href "https://www.drivethrurpg.com/product/250888/Knave"} "Knave"]
     " by "
     [:a {:href "http://questingblog.com/"} "Questing Beast"]
     ", Scurvy made with "
     [:a {:href "https:https://reagent-project.github.io/"} "Reagent"]
     ", "
     [:a {:href "https://clojurescript.org/"} "ClojureScript"]
     ", and "
     [:a {:href "https://bulma.io"} "Bulma."]]
    [:p
     " Names sourced from "
     [:a {:href "https://www.reddit.com/r/DnDBehindTheScreen/comments/50pcg1/a_post_about_names_names_for_speakers_of_the/"}
      "OrkishBlade"]
     " and project inspired by "
     [:a {:href "https://lawbkreader.herokuapp.com"} "Lawbreaker "]]]])

(defn app []
  [:div
   [:div.container
    [topbar]]
   [:section.section
    [:div.container
     [vitals-bar  @state]
     [:div.columns
      [stats-panel (:stats @state)]
      [inventory-panel @state]
      [background-panel @state]]
     [warnings-bar @state]]]
   [footer]])


(defn mount []
  (r.dom/render [app] (js/document.getElementById "root")))

(defn ^:after-load re-render []
  (mount)
  (set-character-from-url!))

(defonce start-up
  (do
    (mount)
    (load-or-set-url!)
    (set-character-from-url!)
    true))