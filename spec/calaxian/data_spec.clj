(ns calaxian.data-spec
  (:require [speclj.core :refer :all]
            [calaxian.data :refer :all]))

(defn update-world [world pos]
  (assoc-in world [:player :location :x] pos))

(let [world {:player {:location {:x 350 :y 400}}}]
  (describe
    "Shift player in the world to left"
    (it "should shift player horizontally to left by 10 units when possible"
        (should= 340 (get-in (move-to-left world) [:player :location :x])))
    (it "should not shift player horizontally to left when not possible"
        (should= 5 (get-in (move-to-left (update-world world 5)) [:player :location :x]))))

  (describe
    "Shift player in the world to right"
    (it "should shift player horizontally to right by 10 units when possible"
        (should= 360 (get-in (move-to-right world) [:player :location :x])))
    (it "should not shift player horizontally to right when not possible"
        (should= 695 (get-in (move-to-right (update-world world 695)) [:player :location :x]))))

  (describe
    "Player emits bullets"
    (it "should emit bullets to the world"
        (should= {:x 350 :y 400} (get-in (first (get (player-fire world) :bullets)) [:bullet :location])))
    (it "should emit multiple bullets"
        (should= 2 (count (:bullets (player-fire (player-fire world))))))))

(let [world {:player {:location {:x 350 :y 400}} :bullets [{:bullet {:location {:x 350 :y 400}}}]}
      modified-world (assoc world :bullets [{:bullet {:location {:x 350 :y 0}}}])]
  (describe
    "Update bullet position"
    (it "should update bullet position"
        (should= {:bullet {:location {:x 350 :y 399}}} (first (get (update-bullets-location world) :bullets))))
    (it "should remove bullets hanging on the edge"
        (should= 0 (count (get (update-bullets-location modified-world) :bullets))))))