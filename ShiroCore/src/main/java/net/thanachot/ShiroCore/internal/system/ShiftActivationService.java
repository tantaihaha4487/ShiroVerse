package net.thanachot.ShiroCore.internal.system;

import net.thanachot.shiroverse.api.ShiftActivation;
import net.thanachot.shiroverse.api.handler.ShiftActivationHandler;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Internal service that manages the registration of listenable items and their
 * corresponding handlers.
 * This class is not intended for public use.
 */
public class ShiftActivationService implements ShiftActivation {

    private final Map<Material, ShiftActivationHandler> listenableItems = new ConcurrentHashMap<>();

    @Override
    public void register(@NotNull Material material, @NotNull ShiftActivationHandler handler) {
        listenableItems.put(material, handler);
    }

    @Override
    public void unregister(@NotNull Material material) {
        listenableItems.remove(material);
    }

    @Override
    public boolean isRegistered(@NotNull Material material) {
        return listenableItems.containsKey(material);
    }

    /**
     * Gets an unmodifiable set of the listenable materials.
     *
     * @return An unmodifiable set of the listenable materials.
     */
    @NotNull
    public Set<Material> getListenableItems() {
        return Collections.unmodifiableSet(listenableItems.keySet());
    }

    /**
     * Gets the handler for a specific material.
     *
     * @param material The material to get the handler for.
     * @return The handler, or null if none is registered.
     */
    @Nullable
    public ShiftActivationHandler getHandler(@NotNull Material material) {
        return listenableItems.get(material);
    }

    private int maxProgress = 10;

    @Override
    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
    }

    @Override
    public int getMaxProgress() {
        return maxProgress;
    }
}
