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
        return Component.text()
                .append(Component.text("(", NamedTextColor.GOLD, TextDecoration.BOLD))
                .append(Component.text("i", NamedTextColor.RED, TextDecoration.BOLD))
                .append(Component.text(")", NamedTextColor.GOLD, TextDecoration.BOLD))
                .append(Component.text(" "))
                .append(Component.text(content, color))
                .build();
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
        return Component.text()
                .append(Component.text("(", NamedTextColor.GOLD, TextDecoration.BOLD))
                .append(Component.text("i", NamedTextColor.RED, TextDecoration.BOLD))
                .append(Component.text(")", NamedTextColor.GOLD, TextDecoration.BOLD))
                .append(Component.text(" "))
                .append(Component.text(content, color, textDecoration))
                .build();
    }

    /**
     * Creates an alert message with the given component.
     * The format is "(i) [component]".
     *
     * @param component The component to be included in the alert.
     * @return A formatted {@link Component} for the action bar.
     */
    public static Component getAlert(Component component) {
        return Component.text()
                .append(Component.text("(", NamedTextColor.GOLD, TextDecoration.BOLD))
                .append(Component.text("i", NamedTextColor.RED, TextDecoration.BOLD))
                .append(Component.text(")", NamedTextColor.GOLD, TextDecoration.BOLD))
                .append(Component.text(" "))
                .append(component)
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
     * The format is "╞═══▰════╡ 50%".
     *
     * @param percentage The progress percentage.
     * @return A {@link Component} representing the loading bar.
     */
    public static Component getLoadingBar(float percentage) {
        TextColor filledColor = NamedTextColor.GREEN;
        TextColor emptyColor = NamedTextColor.GRAY;

        int rounded = (int) Math.round(percentage);
        int greenBars = (int) (percentage / 10);

        TextComponent.Builder progressBar = Component.text();


        if (percentage <= 0) {
            progressBar.append(Component.text("╞══════════╡ 0%").color(emptyColor));
            return progressBar.build();
        }

        if (percentage >= 100) {
            progressBar.append(Component.text("╞══════════╡ 100%").color(filledColor));
            return progressBar.build();
        }

        progressBar.append(Component.text("╞").color(filledColor));

        for (int i = 0; i < 10; i++) {
            if (i < greenBars) {
                progressBar.append(Component.text("═").color(filledColor));
            } else {
                progressBar.append(Component.text("═").color(emptyColor));
            }
        }

        progressBar.append(Component.text("╡").color(emptyColor));
        progressBar.append(Component.text(" " + rounded + "%"));

        return progressBar.build();
    }

}
