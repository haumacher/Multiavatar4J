package com.multiavatar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestColorReplacement {
    public static void main(String[] args) {
        // Simplified version of character 05 eyes template (last part)
        String template = "stroke-width:4;stroke:#fff;\"/><path d=\"m156.27 105-2.403-3.4328\" style=\"fill:none;stroke-linecap:round;stroke-linejoin:round;stroke-width:4;stroke:#fff;\"/><path d=\"m82.748 114.34-8.9489-12.784\" style=\"fill:none;stroke-linecap:round;stroke-linejoin:round;stroke-width:4;stroke:#fff;\"/><path d=\"m91.408 109.79-5.7626-8.2324\" style=\"fill:none;stroke-linecap:round;stroke-linejoin:round;stroke-width:4;stroke:#fff;\"/>";

        // Last 4 colors from theme A eyes (indices 8-11)
        String[] colors = new String[]{"#fff", "#fff", "#fff", "#fff", "#000", "#000"};

        // Java replacement logic from Multiavatar.java
        Pattern pattern = Pattern.compile("#(.*?);");
        Matcher matcher = pattern.matcher(template);

        int colorIndex = 0;
        StringBuffer sb = new StringBuffer();
        System.out.println("=== Replacements ===");
        while (matcher.find() && colorIndex < colors.length) {
            String matched = matcher.group(0);
            String replacement = colors[colorIndex] + ";";
            System.out.println((colorIndex + 1) + ". \"" + matched + "\" -> \"" + replacement + "\"");
            matcher.appendReplacement(sb, Matcher.quoteReplacement(replacement));
            colorIndex++;
        }
        matcher.appendTail(sb);

        System.out.println("\n=== Result ===");
        System.out.println(sb.toString());

        System.out.println("\n=== Expected (last stroke should be #000) ===");
        System.out.println("...stroke:#000;\"/>");
    }
}
