package net.thanachot.ShiroCore.api;

import net.thanachot.ShiroCore.handler.ShiftActivationHandler;
import net.thanachot.ShiroCore.system.ShiftActivationService;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;

/**
 * The main API class for interacting with the ShiroCore shift-activation system.
 */
public final class ShiftActivation {

    private static ShiftActivationService service;

    // Private constructor to prevent instantiation
    private ShiftActivation() {
    }

    /**
     * @return The ShiftActivationService instance.
     */
    @NotNull
    private static ShiftActivationService getService() {
        return Objects.requireNonNull(service, "ShiftActivation service is not initialized. Is ShiroCore loaded and enabled?");
    }

    /**
     * <b>Internal use only.</b> Sets the service instance.
     *
     * @param service The ShiftActivationService instance from the core plugin.
     */
    public static void setService(ShiftActivationService service) {
        ShiftActivation.service = service;
    }

    /**
     * Registers a single material to be listened for shift activation.
     *
     * @param material The material to register.
     * @param handler  The handler to be executed on activation.
     */
    public static void register(@NotNull Material material, @NotNull ShiftActivationHandler handler) {
        getService().registerListenableItem(material, handler);
    }

    /**
     * Registers multiple materials to be listened for, all sharing the same handler.
     *
     * @param handler   The handler to be executed on activation for all specified materials.
     * @param materials A list or varargs of materials to register.
     */
    public static void register(@NotNull ShiftActivationHandler handler, @NotNull Material... materials) {
        ShiftActivationService currentService = getService();
        Arrays.stream(materials).forEach(material -> currentService.registerListenableItem(material, handler));
    }

    /**
     * Unregisters a material from being listened for shift activation.
     *
     * @param material The material to unregister.
     */
    public static void unregister(@NotNull Material material) {
        getService().unregisterListenableItem(material);
    }

    /**
     * Checks if a material is registered for shift activation.
     *
     * @param material The material to check.
     * @return true if the material is registered, false otherwise.
     */
    public static boolean isRegistered(@NotNull Material material) {
        return getService().isListenableItem(material);
    }
}
