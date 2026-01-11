package com.multiavatar;

public class TestChar05Debug {
    public static void main(String[] args) {
        // Get theme for character GEEKNOT (05) Theme A
        ThemeData.CharacterThemes ct = ThemeData.getCharacterThemes(Multiavatar.CharacterType.GEEKNOT);
        ThemeData.Theme theme = ct.getTheme(Multiavatar.CharacterTheme.A);
        String[] eyes = theme.eyes;
        System.out.println("Character GEEKNOT (05) Theme A eyes colors (" + eyes.length + " total):");
        for (int i = 0; i < eyes.length; i++) {
            System.out.println("  " + i + ": " + eyes[i]);
        }

        // Generate and check output
        String svg = Multiavatar.generate("Test", false, Multiavatar.CharacterType.GEEKNOT, Multiavatar.CharacterTheme.A);

        // Extract just the eyes SVG for comparison
        int eyesStart = svg.indexOf("<path d=\"m70.959 94.985");
        if (eyesStart > 0) {
            // Find the last </path> in the eyes section (ends with the last 4;stroke:#fff;"/>)
            int searchStart = eyesStart + 1500;
            int eyesEnd = -1;
            for (int i = 0; i < 5; i++) {
                eyesEnd = svg.indexOf("stroke:#fff;\"/>", searchStart);
                if (eyesEnd > 0) {
                    eyesEnd += 14; // length of "stroke:#fff;"/>"
                    break;
                }
                searchStart -= 200;
            }
            if (eyesEnd > eyesStart) {
                String eyesSvg = svg.substring(eyesStart, eyesEnd);
                try {
                    java.nio.file.Files.write(java.nio.file.Paths.get("/tmp/java_eyes_output.txt"), eyesSvg.getBytes());
                    System.out.println("\nWrote eyes SVG to /tmp/java_eyes_output.txt (" + eyesSvg.length() + " chars)");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // Find the stroke color before "m109.32"
        int m109Pos = svg.indexOf("m109.32");
        if (m109Pos > 0) {
            int strokePos = svg.lastIndexOf("stroke:#", m109Pos);
            if (strokePos > 0) {
                String strokeColor = svg.substring(strokePos, strokePos + 13);
                System.out.println("\nStroke color before 'm109.32': " + strokeColor);
                System.out.println("Expected: stroke:#000;");
                System.out.println("Match: " + strokeColor.equals("stroke:#000;"));
            }
        }
    }
}
