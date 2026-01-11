package com.multiavatar;

import com.multiavatar.SvgData.Template;

public class DebugTest {
    public static void main(String[] args) {
        System.out.println("Testing Multiavatar...");

        String svg = Multiavatar.generate("Test");
        System.out.println("Generated SVG length: " + svg.length());
        System.out.println("SVG content:\n" + svg);

        // Test SVG data
        Template svgPart = SvgData.getSvgTemplate(Multiavatar.AvatarCharacter.ROBO, Multiavatar.AvatarPart.ENV);
        System.out.println("\nSVG Part ROBO/ENV: " + (svgPart != null ? svgPart.toSource().substring(0, Math.min(100, svgPart.toSource().length())) : "NULL"));

        // Test theme data
        ThemeData.CharacterThemes themes = ThemeData.getCharacterThemes(Multiavatar.AvatarCharacter.ROBO);
        System.out.println("\nThemes for ROBO: " + (themes != null ? "Found" : "NULL"));
        if (themes != null) {
            ThemeData.Theme themeA = themes.getTheme('A');
            System.out.println("Theme A env colors: " + java.util.Arrays.toString(themeA.env));
        }
    }
}
