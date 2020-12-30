(ns calaxian.data)

(defn move-to-right [world]
  (update-in world [:player :location :x] #(let [value (+ %1 %2)] (if (<= value 690) value %1)) 10))

(defn move-to-left [world]
  (update-in world [:player :location :x] #(let [value (- %1 %2)] (if (>= value 10) value %1)) 10))

(defn player-fire [world]
  (assoc world :bullets (cons
                          {:bullet {:location
                                    {:x (get-in world [:player :location :x])
                                     :y (get-in world [:player :location :y])}}}
                          (get world :bullets))))

(defn update-bullet [bullet]
  (let [y (dec (get-in bullet [:bullet :location :y]))]
    (assoc-in bullet [:bullet :location :y] y)))

(defn is-in-frame? [bullet]
  (>= (get-in bullet [:bullet :location :y]) 0))

(defn update-bullets-location [world]
  (let [bullets (:bullets world)
        updated-bullets (filter is-in-frame? (map update-bullet bullets))]
    (assoc world :bullets updated-bullets)))



