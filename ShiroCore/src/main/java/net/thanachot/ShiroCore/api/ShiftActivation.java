package net.thanachot.ShiroCore.api;

import net.thanachot.ShiroCore.handler.ShiftActivationHandler;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * The main API for interacting with the ShiroCore shift-activation system.
 */
public interface ShiftActivation {

    /**
     * Registers a single material to be listened for shift activation.
     *
     * @param material The material to register.
     * @param handler  The handler to be executed on activation.
     */
    void register(@NotNull Material material, @NotNull ShiftActivationHandler handler);

    /**
     * Registers multiple materials to be listened for, all sharing the same handler.
     *
     * @param handler   The handler to be executed on activation for all specified materials.
     * @param materials A list or varargs of materials to register.
     */
    default void register(@NotNull ShiftActivationHandler handler, @NotNull Material... materials) {
        Arrays.stream(materials).forEach(material -> register(material, handler));
    }

    /**
     * Unregisters a material from being listened for shift activation.
     *
     * @param material The material to unregister.
     */
    void unregister(@NotNull Material material);

    /**
     * Checks if a material is registered for shift activation.
     *
     * @param material The material to check.
     * @return true if the material is registered, false otherwise.
     */
    boolean isRegistered(@NotNull Material material);
}
