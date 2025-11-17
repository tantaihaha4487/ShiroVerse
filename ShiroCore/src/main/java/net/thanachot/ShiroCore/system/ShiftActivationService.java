package net.thanachot.ShiroCore.system;

import net.thanachot.ShiroCore.handler.ShiftActivationHandler;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Internal service that manages the registration of listenable items and their corresponding handlers.
 * This class is not intended for public use.
 */
public class ShiftActivationService {

    private final Map<Material, ShiftActivationHandler> listenableItems = new HashMap<>();

    /**
     * Registers a material to be listened for shift activation with a specific handler.
     *
     * @param material The material to register.
     * @param handler  The handler to be executed on activation.
     */
    public void registerListenableItem(@NotNull Material material, @NotNull ShiftActivationHandler handler) {
        listenableItems.put(material, handler);
    }

    /**
     * Unregisters a material from being listened for shift activation.
     *
     * @param material The material to unregister.
     */
    public void unregisterListenableItem(@NotNull Material material) {
        listenableItems.remove(material);
    }

    /**
     * Checks if a material is registered for shift activation.
     *
     * @param material The material to check.
     * @return true if the material is registered, false otherwise.
     */
    public boolean isListenableItem(@NotNull Material material) {
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
}
