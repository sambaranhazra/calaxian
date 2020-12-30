(ns calaxian.core
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [calaxian.data :as d]))

(defn setup []
  (q/frame-rate 120)
  (q/color-mode :rgb)
  {:player {:location {:color 240 :x 350 :y 400}}})

(defn update-state [state]
  (d/update-bullets-location state))

(defn draw-player [state]
  (q/ellipse (get-in state [:player :location :x]) 400 15 15))

(defn draw-bullet [b]
  (q/ellipse (get-in b [:bullet :location :x])
             (get-in b [:bullet :location :y])
             5
             5))

(defn draw-bullets [state]
  (when-not (nil? (let [bullets (get state :bullets)]
                    (doseq [b bullets]
                      (draw-bullet b))))))

(defn draw-state [state]
  (q/background 0)
  (draw-player state)
  (draw-bullets state))

(defn update-position [state pressed-key]
  (condp = pressed-key :left (d/move-to-left state)
                       :right (d/move-to-right state)
                       :up (d/player-fire state)
                       state))

(defn update-keys [state key]
  (update-position state (:key key)))

(defn ^:export -main [& args]
  (q/defsketch calaxian.core
               :title "Battlefield Calaxian"
               :size [700 500]
               :setup setup
               :update update-state
               :draw draw-state
               :key-pressed update-keys
               :features [:keep-on-top]
               :middleware [m/fun-mode]))
