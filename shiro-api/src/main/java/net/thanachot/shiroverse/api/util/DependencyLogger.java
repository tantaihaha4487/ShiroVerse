package net.thanachot.shiroverse.api.util;

import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

/**
 * Utility class for logging ShiroCore dependency-related messages.
 * Provides standardized warning messages for plugins that depend on ShiroCore.
 */
public final class DependencyLogger {

    private static final String BORDER_TOP = "╔════════════════════════════════════════════════════════════╗";
    private static final String BORDER_BOTTOM = "╚════════════════════════════════════════════════════════════╝";
    private static final String SHIROCORE_URL = "https://modrinth.com/plugin/shirocore";

    // Private constructor to prevent instantiation
    private DependencyLogger() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Logs a warning message when ShiroCore is not found.
     * 
     * @param logger          the logger to use for output
     * @param pluginName      the name of the plugin requiring ShiroCore
     * @param requiredVersion the minimum required version of ShiroCore
     */
    public static void logShiroCoreNotFound(@NotNull Logger logger,
            @NotNull String pluginName,
            @NotNull String requiredVersion) {
        logger.warning(BORDER_TOP);
        logger.warning("║  ShiroCore NOT FOUND!                                      ║");
        logger.warning(formatLine(pluginName + " requires ShiroCore v" + requiredVersion + "+"));
        logger.warning("║  for abilities to work.                                    ║");
        logger.warning("║                                                            ║");
        logger.warning("║  The plugin will continue without ability features.        ║");
        logger.warning("║                                                            ║");
        logger.warning("║  Download ShiroCore from:                                  ║");
        logger.warning("║  → " + SHIROCORE_URL + "                   ║");
        logger.warning(BORDER_BOTTOM);
    }

    /**
     * Logs a warning message when ShiroCore version is incompatible.
     * 
     * @param logger          the logger to use for output
     * @param foundVersion    the version of ShiroCore that was found
     * @param requiredVersion the minimum required version
     */
    public static void logIncompatibleVersion(@NotNull Logger logger,
            @NotNull String foundVersion,
            @NotNull String requiredVersion) {
        logger.warning(BORDER_TOP);
        logger.warning("║  INCOMPATIBLE ShiroCore VERSION!                           ║");
        logger.warning("║  Found: " + String.format("%-49s", foundVersion) + "║");
        logger.warning("║  Required: v" + String.format("%-44s", requiredVersion + "+") + "║");
        logger.warning("║                                                            ║");
        logger.warning("║  The plugin will continue without ability features.        ║");
        logger.warning("║                                                            ║");
        logger.warning("║  Please update ShiroCore:                                  ║");
        logger.warning("║  → " + SHIROCORE_URL + "                   ║");
        logger.warning(BORDER_BOTTOM);
    }

    /**
     * Logs a generic dependency error message.
     * 
     * @param logger   the logger to use for output
     * @param title    the error title
     * @param messages the error messages to display (max 5 lines recommended)
     */
    public static void logDependencyError(@NotNull Logger logger,
            @NotNull String title,
            @NotNull String... messages) {
        logger.warning(BORDER_TOP);
        logger.warning(formatLine(title));
        logger.warning("║                                                            ║");

        for (String message : messages) {
            logger.warning(formatLine(message));
        }

        logger.warning("║                                                            ║");
        logger.warning("║  Download ShiroCore from:                                  ║");
        logger.warning("║  → " + SHIROCORE_URL + "                   ║");
        logger.warning(BORDER_BOTTOM);
    }

    /**
     * Formats a line to fit within the bordered message box.
     * Centers the text if it's shorter than the available space.
     * 
     * @param text the text to format
     * @return the formatted line
     */
    @NotNull
    private static String formatLine(@NotNull String text) {
        int availableWidth = 58; // Total width (60) minus borders (2)

        if (text.length() > availableWidth) {
            // Truncate if too long
            return "║  " + text.substring(0, availableWidth - 2) + "║";
        }

        // Pad with spaces to fill the line
        int paddingTotal = availableWidth - text.length();
        int paddingLeft = 2; // Always 2 spaces on the left
        int paddingRight = paddingTotal - paddingLeft;

        return "║" + " ".repeat(paddingLeft) + text + " ".repeat(paddingRight) + "║";
    }

    /**
     * Creates a formatted info line for use with custom logging.
     * 
     * @param info the info text
     * @return formatted line
     */
    @NotNull
    public static String formatInfoLine(@NotNull String info) {
        return formatLine(info);
    }

    /**
     * Gets the standard ShiroCore download URL.
     * 
     * @return the ShiroCore plugin URL
     */
    @NotNull
    public static String getShiroCoreUrl() {
        return SHIROCORE_URL;
    }
}
