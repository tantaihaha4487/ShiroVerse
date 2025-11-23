package net.thanachot.ShiroCore.internal.util;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Tracks player shift presses to detect rapid "spam" shifting.
 * This class is not intended for public use.
 */
public class PlayerShiftTracker {

    private static final long WINDOW_MS = 3000;
    private static final long COOLDOWN_MS = 2000;

    private final Map<UUID, Deque<Long>> presses = new ConcurrentHashMap<>();
    private final Map<UUID, Long> lastActivated = new ConcurrentHashMap<>();

    /**
     * Records a shift press for a player and returns the current number of valid
     * presses.
     *
     * @param uuid The UUID of the player.
     * @return The number of valid presses in the current window.
     */
    public int recordPress(UUID uuid) {
        long now = System.currentTimeMillis();

        Long last = lastActivated.get(uuid);
        if (last != null && (now - last) < COOLDOWN_MS) {
            return 0;
        }

        Deque<Long> deque = presses.computeIfAbsent(uuid, u -> new ArrayDeque<>());
        deque.addLast(now);

        // prune past
        long cutoff = now - WINDOW_MS;
        while (!deque.isEmpty() && deque.peekFirst() < cutoff) {
            deque.pollFirst();
        }

        return deque.size();
    }

    /**
     * Resets the shift tracking for a player and puts them on cooldown.
     *
     * @param uuid The UUID of the player.
     */
    public void reset(UUID uuid) {
        lastActivated.put(uuid, System.currentTimeMillis());
        presses.remove(uuid);
    }
}
