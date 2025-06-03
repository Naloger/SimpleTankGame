package com.htu.tankgame;

import java.util.*;
import java.util.function.BiConsumer;

public class CCollisionSystem {
    private final List<CCollider> colliders = new ArrayList<>();
    private final Set<String> currentCollisions = new HashSet<>();
    private final Map<String, BiConsumer<CCollider, CCollider>> handlers = new HashMap<>();

    public void addCollider(CCollider collider) {
        colliders.add(collider);
    }

    public void onCollision(String nameA, String nameB, BiConsumer<CCollider, CCollider> action) {
        String key = makeKey(nameA, nameB);
        handlers.put(key, action);
    }

    public void update() {
        currentCollisions.clear();

        for (int i = 0; i < colliders.size(); i++) {
            CCollider a = colliders.get(i);
            for (int j = i + 1; j < colliders.size(); j++) {
                CCollider b = colliders.get(j);

                if (a.intersects(b)) {
                    String key = makeKey(a.getName(), b.getName());
                    currentCollisions.add(key);

                    if (handlers.containsKey(key)) {
                        handlers.get(key).accept(a, b);
                    }
                }
            }
        }
    }

    public Set<String> getCurrentCollisions() {
        return Collections.unmodifiableSet(currentCollisions);
    }

    private String makeKey(String a, String b) {
        return a.compareTo(b) < 0 ? a + "|" + b : b + "|" + a;
    }
}
