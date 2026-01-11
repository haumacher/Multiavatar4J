package com.multiavatar;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for Multiavatar
 */
public class MultiavatarTest {

    @Test
    public void testGenerateBasic() {
        String svg = Multiavatar.generate("Binx Bond");
        assertNotNull("SVG should not be null", svg);
        assertTrue("SVG should start with <svg", svg.startsWith("<svg"));
        assertTrue("SVG should end with </svg>", svg.endsWith("</svg>"));
        assertTrue("SVG should contain viewBox", svg.contains("viewBox"));
        assertTrue("SVG should contain metadata with creator", svg.contains("<dc:creator>Multiavatar</dc:creator>"));
        assertTrue("SVG should contain metadata with source", svg.contains("<dc:source>https://multiavatar.com</dc:source>"));
    }

    @Test
    public void testGenerateSansEnv() {
        String svgWithEnv = Multiavatar.generate("Test User", false);
        String svgWithoutEnv = Multiavatar.generate("Test User", true);

        assertNotNull("SVG with env should not be null", svgWithEnv);
        assertNotNull("SVG without env should not be null", svgWithoutEnv);

        // Both should be valid SVGs
        assertTrue("SVG with env should be valid", svgWithEnv.startsWith("<svg"));
        assertTrue("SVG without env should be valid", svgWithoutEnv.startsWith("<svg"));
    }

    @Test
    public void testDeterministic() {
        String svg1 = Multiavatar.generate("Same Input");
        String svg2 = Multiavatar.generate("Same Input");

        assertEquals("Same input should produce same output", svg1, svg2);
    }

    @Test
    public void testDifferentInputs() {
        String svg1 = Multiavatar.generate("Input A");
        String svg2 = Multiavatar.generate("Input B");

        assertNotEquals("Different inputs should produce different outputs", svg1, svg2);
    }

    @Test
    public void testEmptyString() {
        String svg = Multiavatar.generate("");
        assertNotNull("Empty string should return empty string", svg);
        assertEquals("Empty string should return empty string", "", svg);
    }

    @Test
    public void testNullString() {
        String svg = Multiavatar.generate(null);
        assertNotNull("Null should return empty string", svg);
        assertEquals("Null should return empty string", "", svg);
    }

    @Test
    public void testSpecialCharacters() {
        String svg = Multiavatar.generate("Test@User#123!");
        assertNotNull("Special characters should work", svg);
        assertTrue("SVG should be valid", svg.startsWith("<svg"));
    }

    @Test
    public void testUnicodeCharacters() {
        String svg = Multiavatar.generate("测试用户");
        assertNotNull("Unicode should work", svg);
        assertTrue("SVG should be valid", svg.startsWith("<svg"));
    }

    @Test
    public void testVersion() {
        Multiavatar.Version version = new Multiavatar.Version("01", 'A');
        String svg = Multiavatar.generate("Test", false, version);

        assertNotNull("Version-forced SVG should not be null", svg);
        assertTrue("SVG should be valid", svg.startsWith("<svg"));
    }

    @Test
    public void testVersionDifferentThemes() {
        Multiavatar.Version versionA = new Multiavatar.Version("01", 'A');
        Multiavatar.Version versionB = new Multiavatar.Version("01", 'B');
        Multiavatar.Version versionC = new Multiavatar.Version("01", 'C');

        String svgA = Multiavatar.generate("Test", false, versionA);
        String svgB = Multiavatar.generate("Test", false, versionB);
        String svgC = Multiavatar.generate("Test", false, versionC);

        // Different themes should produce different SVGs
        assertNotEquals("Theme A and B should differ", svgA, svgB);
        assertNotEquals("Theme B and C should differ", svgB, svgC);
        assertNotEquals("Theme A and C should differ", svgA, svgC);
    }

    @Test
    public void testSvgStructure() {
        String svg = Multiavatar.generate("Structure Test");

        // SVG should contain xmlns
        assertTrue("Should contain xmlns", svg.contains("xmlns"));

        // SVG should contain paths
        assertTrue("Should contain path elements", svg.contains("<path"));

        // SVG should have proper structure
        int openTags = countOccurrences(svg, "<svg");
        int closeTags = countOccurrences(svg, "</svg>");
        assertEquals("Should have matching svg tags", openTags, closeTags);
    }

    private int countOccurrences(String str, String substr) {
        int count = 0;
        int index = 0;
        while ((index = str.indexOf(substr, index)) != -1) {
            count++;
            index += substr.length();
        }
        return count;
    }
}
