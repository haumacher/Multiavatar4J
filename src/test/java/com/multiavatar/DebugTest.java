package com.multiavatar;

public class DebugTest {
    public static void main(String[] args) {
        System.out.println("Testing Multiavatar...");

        String svg = Multiavatar.generate("Test");
        System.out.println("Generated SVG length: " + svg.length());
        System.out.println("SVG content:\n" + svg);

        // Test SVG data
        String svgPart = SvgData.getSvgPart("00", Multiavatar.AvatarPart.ENV);
        System.out.println("\nSVG Part 00/ENV: " + (svgPart != null ? svgPart.substring(0, Math.min(100, svgPart.length())) : "NULL"));

        // Test theme data
        ThemeData.CharacterThemes themes = ThemeData.getCharacterThemes("00");
        System.out.println("\nThemes for 00: " + (themes != null ? "Found" : "NULL"));
        if (themes != null) {
            ThemeData.Theme themeA = themes.getTheme('A');
            System.out.println("Theme A env colors: " + java.util.Arrays.toString(themeA.env));
        }
    }
}
