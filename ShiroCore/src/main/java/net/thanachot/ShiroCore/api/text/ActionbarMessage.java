package net.thanachot.ShiroCore.api.text;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class ActionbarMessage {


    public static Component getAlert(String content, TextColor color) {
        return Component.text()
                .append(Component.text("(", NamedTextColor.GOLD, TextDecoration.BOLD))
                .append(Component.text("i", NamedTextColor.RED, TextDecoration.BOLD))
                .append(Component.text(")", NamedTextColor.GOLD, TextDecoration.BOLD))
                .append(Component.text(" "))
                .append(Component.text(content, color))
                .build();
    }


    public static Component getAlert(String content, TextColor color, TextDecoration textDecoration) {
        return Component.text()
                .append(Component.text("(", NamedTextColor.GOLD, TextDecoration.BOLD))
                .append(Component.text("i", NamedTextColor.RED, TextDecoration.BOLD))
                .append(Component.text(")", NamedTextColor.GOLD, TextDecoration.BOLD))
                .append(Component.text(" "))
                .append(Component.text(content, color, textDecoration))
                .build();
    }


    public static Component getAlert(Component component) {
        return Component.text()
                .append(Component.text("(", NamedTextColor.GOLD, TextDecoration.BOLD))
                .append(Component.text("i", NamedTextColor.RED, TextDecoration.BOLD))
                .append(Component.text(")", NamedTextColor.GOLD, TextDecoration.BOLD))
                .append(Component.text(" "))
                .append(component)
                .build();
    }


    public static Component getLoadingBar(int current, int max) {
        double percentage = (max == 0) ? 0 : ((double) current / max) * 100;
        TextColor filledColor = NamedTextColor.GREEN;
        TextColor emptyColor = NamedTextColor.BLACK;

        int rounded = (int) Math.round(percentage);
        int greenBars = rounded / 10;

        TextComponent.Builder progressBar = Component.text();


        if (rounded == 0) {
            progressBar.append(Component.text("╞══════════╡ 0%").color(emptyColor));
            return progressBar.build();
        }

        if (rounded == 100) {
            progressBar.append(Component.text("╞══════════╡ 100%").color(filledColor));
            return progressBar.build();
        }

        progressBar.append(Component.text("╞").color(filledColor));

        for (int i = 0; i < 10; i++) {
            if (i < greenBars) {
                progressBar.append(Component.text("═").color(filledColor));
            } else if (i == greenBars) {
                progressBar.append(Component.text("▰").color(filledColor));
            } else {
                progressBar.append(Component.text("═").color(emptyColor));
            }
        }

        progressBar.append(Component.text("╡").color(emptyColor));
        progressBar.append(Component.text(" " + rounded + "%"));

        return progressBar.build();
    }

}
