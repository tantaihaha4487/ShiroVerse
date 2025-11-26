package net.thanachot.shiroverse.api;

import net.thanachot.shiroverse.api.handler.ShiftActivationHandler;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Optional;

/**
 * The main API for interacting with the ShiroCore shift-activation system.
 */
public interface ShiftActivation {

    /**
     * Gets the ShiftActivation API from the Bukkit services manager.
     *
     * @return An Optional containing the ShiftActivation API instance if the
     * service is registered, otherwise empty.
     */
    @NotNull
    static Optional<ShiftActivation> get() {
        RegisteredServiceProvider<ShiftActivation> provider = Bukkit.getServicesManager()
                .getRegistration(ShiftActivation.class);
        return Optional.ofNullable(provider).map(RegisteredServiceProvider::getProvider);
    }

    /**
     * Gets the ShiftActivation API from the Bukkit services manager.
     *
     * @return The ShiftActivation API instance.
     * @throws IllegalStateException if the service is not registered.
     */
    @NotNull
    static ShiftActivation getOrThrow() {
        return get().orElseThrow(
                () -> new IllegalStateException("ShiftActivation service not found! Is ShiroCore enabled?"));
    }

    /**
     * Registers a single material to be listened for shift activation.
     *
     * @param material The material to register.
     * @param handler  The handler to be executed on activation.
     */
    void register(@NotNull Material material, @NotNull ShiftActivationHandler handler);

    /**
     * Registers multiple materials to be listened for, all sharing the same
     * handler.
     *
     * @param handler   The handler to be executed on activation for all specified
     *                  materials.
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

    /**
     * Gets the number of shift presses required to trigger activation.
     *
     * @return The number of presses required.
     */
    int getMaxProgress();

    /**
     * Sets the number of shift presses required to trigger activation.
     * Default is usually 10.
     *
     * @param maxProgress The number of presses required.
     */
    void setMaxProgress(int maxProgress);
}