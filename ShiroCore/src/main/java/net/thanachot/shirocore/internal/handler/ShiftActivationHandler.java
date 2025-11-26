package net.thanachot.shirocore.internal.handler;

import net.thanachot.shiroverse.api.event.ShiftActivationEvent;
import org.jetbrains.annotations.NotNull;

/**
 * A functional interface for handling shift activation events for a specific
 * item.
 */
@FunctionalInterface
public interface ShiftActivationHandler {

    /**
     * Called when a shift activation is successfully completed for a registered
     * item.
     *
     * @param event The ShiftActivationEvent.
     */
    void onActivate(@NotNull ShiftActivationEvent event);
}
