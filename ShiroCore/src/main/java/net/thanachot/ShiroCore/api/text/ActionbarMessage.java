package net.thanachot.ShiroCore.api.text;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;

public class ActionbarMessage {

    public static Component getLoadingBar(int current, int max) {
        double percentage = (max == 0) ? 0 : ((double) current / max) * 100;
        int rounded = (int) Math.round(percentage);
        int greenBars = rounded / 10;

        TextComponent.Builder progressBar = Component.text();

        TextColor green = TextColor.fromHexString("#69D84F");
        TextColor gray = TextColor.fromHexString("#443344");

        if (rounded == 0) {
            progressBar.append(Component.text("╞══════════╡ 0%").color(gray));
            return progressBar.build();
        }

        if (rounded == 100) {
            progressBar.append(Component.text("╞══════════╡ 100%").color(green));
            return progressBar.build();
        }

        progressBar.append(Component.text("╞").color(green));

        for (int i = 0; i < 10; i++) {
            if (i < greenBars) {
                progressBar.append(Component.text("═").color(green));
            } else if (i == greenBars) {
                progressBar.append(Component.text("▰").color(green));
            } else {
                progressBar.append(Component.text("═").color(gray));
            }
        }

        progressBar.append(Component.text("╡").color(gray));
        progressBar.append(Component.text(" " + rounded + "%"));

        return progressBar.build();
    }

}
