package com.multiavatar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestRegex {
    public static void main(String[] args) {
        String template = "<path d=\"m70.959 94.985h35.031c2.4086 1e-5 4.3612 1.9523 4.3612 4.3606l-2.5864 17.511c-0.3515 2.3799-1.7218 4.3606-3.8457 4.3606h-30.9c-2.1239-1e-5 -3.8457-1.9523-3.8457-4.3606l-2.5864-17.511c1e-5 -2.4082 1.9526-4.3606 4.3612-4.3606z\" style=\"fill:#1a1a1a;stroke-linecap:round;stroke-linejoin:round;stroke-width:3.0045px;stroke:#333;\"/><path d=\"m160.05 94.985h-35.031c-2.4086 1e-5 -4.3612 1.9523-4.3612 4.3606l2.5864 17.511c0.35149 2.3799 1.7218 4.3606 3.8457 4.3606h30.9c2.1239-1e-5 3.8457-1.9523 3.8457-4.3606l2.5864-17.511c-1e-5 -2.4082-1.9526-4.3606-4.3612-4.3606z\" style=\"fill:#1a1a1a;stroke-linecap:round;stroke-linejoin:round;stroke-width:3.0045px;stroke:#333;\"/>";

        Pattern pattern = Pattern.compile("#(.*?);");
        Matcher matcher = pattern.matcher(template);

        System.out.println("=== Regex Matches ===");
        int count = 0;
        while (matcher.find()) {
            count++;
            String match = matcher.group(0);
            if (match.length() > 50) {
                System.out.println(count + ". " + match.substring(0, 50) + "... (" + match.length() + " chars total)");
            } else {
                System.out.println(count + ". " + match);
            }
        }
        System.out.println("\nTotal matches: " + count);
        System.out.println("Expected: 2 (two #1a1a1a; and two #333;)");
    }
}
