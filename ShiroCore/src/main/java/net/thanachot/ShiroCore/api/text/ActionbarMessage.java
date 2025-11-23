package net.thanachot.ShiroCore.api.text;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

/**
 * A utility class for creating formatted action bar messages.
 */
public class ActionbarMessage {

    /**
     * Creates an alert message with the given content and color.
     * The format is "(i) [content]".
     *
     * @param content The text content of the message.
     * @param color   The color of the content.
     * @return A formatted {@link Component} for the action bar.
     */
    public static Component getAlert(String content, TextColor color) {
        return createAlert(Component.text(content, color));
    }

    /**
     * Creates an alert message with the given content, color, and text decoration.
     * The format is "(i) [content]".
     *
     * @param content        The text content of the message.
     * @param color          The color of the content.
     * @param textDecoration The text decoration for the content.
     * @return A formatted {@link Component} for the action bar.
     */
    public static Component getAlert(String content, TextColor color, TextDecoration textDecoration) {
        return createAlert(Component.text(content, color, textDecoration));
    }

    /**
     * Creates an alert message with the given component.
     * The format is "(i) [component]".
     *
     * @param component The component to be included in the alert.
     * @return A formatted {@link Component} for the action bar.
     */
    public static Component getAlert(Component component) {
        return createAlert(component);
    }

    private static Component createAlert(Component content) {
        return Component.text()
                .append(Component.text("(", NamedTextColor.GOLD, TextDecoration.BOLD))
                .append(Component.text("i", NamedTextColor.RED, TextDecoration.BOLD))
                .append(Component.text(")", NamedTextColor.GOLD, TextDecoration.BOLD))
                .append(Component.text(" "))
                .append(content)
                .build();
    }

    /**
     * Creates a loading bar component.
     * The format is "╞═══▰════╡ 50%".
     *
     * @param current The current progress value.
     * @param max     The maximum progress value.
     * @return A {@link Component} representing the loading bar.
     */
    public static Component getLoadingBar(int current, int max) {
        float percentage = (max == 0) ? 0 : ((float) current / max) * 100f;
        return getLoadingBar(percentage);
    }

    /**
     * Creates a loading bar component from a percentage.
     * The format is "╞═══▰════╡ 50%" with a smooth transition character.
     *
     * @param percentage The progress percentage.
     * @return A {@link Component} representing the loading bar.
     */
    public static Component getLoadingBar(float percentage) {
        TextColor filledColor = NamedTextColor.GREEN;
        TextColor emptyColor = NamedTextColor.BLACK;

        int rounded = (int) Math.round(percentage);
        int greenBars = rounded / 10;

        TextComponent.Builder progressBar = Component.text();

        // Edge case: 0%
        if (rounded == 0) {
            progressBar.append(Component.text("╞══════════╡ 0%").color(emptyColor));
            return progressBar.build();
        }

        // Edge case: 100%
        if (rounded == 100) {
            progressBar.append(Component.text("╞══════════╡ 100%").color(filledColor));
            return progressBar.build();
        }

        // Opening bracket
        progressBar.append(Component.text("╞").color(filledColor));

        // Progress bar with transition character
        for (int i = 0; i < 10; i++) {
            if (i < greenBars) {
                // Filled section
                progressBar.append(Component.text("═").color(filledColor));
            } else if (i == greenBars) {
                // Transition character between filled and empty
                progressBar.append(Component.text("▰").color(filledColor));
            } else {
                // Empty section
                progressBar.append(Component.text("═").color(emptyColor));
            }
        }

        // Closing bracket and percentage
        progressBar.append(Component.text("╡").color(emptyColor));
        progressBar.append(Component.text(" " + rounded + "%"));

        return progressBar.build();
    }

    /**
     * Creates a stylized loading bar with custom colors and enhanced visual design.
     * The format is "【█▓▒░░░░░░░】 40%".
     *
     * @param current     The current progress value.
     * @param max         The maximum progress value.
     * @param filledColor The color for filled sections.
     * @param emptyColor  The color for empty sections.
     * @return A {@link Component} representing the stylized loading bar.
     */
    public static Component getStylizedLoadingBar(int current, int max, TextColor filledColor, TextColor emptyColor) {
        double percentage = (max == 0) ? 0 : ((double) current / max) * 100;
        int rounded = (int) Math.round(percentage);
        int filledBars = rounded / 10;

        TextComponent.Builder progressBar = Component.text();

        // Edge case: 0%
        if (rounded == 0) {
            progressBar.append(Component.text("【░░░░░░░░░░】 0%").color(emptyColor));
            return progressBar.build();
        }

        // Edge case: 100%
        if (rounded == 100) {
            progressBar.append(Component.text("【██████████】 100%").color(filledColor));
            return progressBar.build();
        }

        // Opening bracket
        progressBar.append(Component.text("【").color(filledColor));

        // Multi-level gradient effect: █ ▓ ▒ ░
        for (int i = 0; i < 10; i++) {
            if (i < filledBars - 1) {
                progressBar.append(Component.text("█").color(filledColor));
            } else if (i == filledBars - 1) {
                progressBar.append(Component.text("█").color(filledColor));
            } else if (i == filledBars) {
                progressBar.append(Component.text("▓").color(filledColor));
            } else if (i == filledBars + 1) {
                progressBar.append(Component.text("▒").color(emptyColor));
            } else {
                progressBar.append(Component.text("░").color(emptyColor));
            }
        }

        // Closing bracket and percentage
        progressBar.append(Component.text("】").color(emptyColor));
        progressBar.append(Component.text(" " + rounded + "%"));

        return progressBar.build();
    }

    /**
     * Creates a minimalist loading bar with dots.
     * The format is "●●●●○○○○○○ 40%".
     *
     * @param current The current progress value.
     * @param max     The maximum progress value.
     * @return A {@link Component} representing the dot-based loading bar.
     */
    public static Component getDotLoadingBar(int current, int max) {
        double percentage = (max == 0) ? 0 : ((double) current / max) * 100;
        int rounded = (int) Math.round(percentage);
        int filledDots = rounded / 10;

        TextColor filledColor = NamedTextColor.GREEN;
        TextColor emptyColor = NamedTextColor.DARK_GRAY;

        TextComponent.Builder progressBar = Component.text();

        for (int i = 0; i < 10; i++) {
            if (i < filledDots) {
                progressBar.append(Component.text("●").color(filledColor));
            } else {
                progressBar.append(Component.text("○").color(emptyColor));
            }
        }

        progressBar.append(Component.text(" " + rounded + "%").color(NamedTextColor.WHITE));

        return progressBar.build();
    }

}
