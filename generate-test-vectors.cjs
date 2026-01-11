#!/usr/bin/env node

// Script to generate test vectors from the JavaScript implementation
// These vectors will be used to verify Java implementation compatibility

const fs = require('fs');

// Load the multiavatar function by evaluating the script in global scope
const scriptContent = fs.readFileSync('./multiavatar.js', 'utf8');
eval(scriptContent);

// Test inputs covering various scenarios
const testCases = [
    // Basic examples
    { input: "Binx Bond", sansEnv: false, version: null },
    { input: "Test User", sansEnv: false, version: null },
    { input: "Alice", sansEnv: false, version: null },
    { input: "Bob", sansEnv: false, version: null },

    // Sans environment
    { input: "Example", sansEnv: true, version: null },
    { input: "No Background", sansEnv: true, version: null },

    // Empty and special cases
    { input: "", sansEnv: false, version: null },
    { input: "test", sansEnv: false, version: null },
    { input: "TEST", sansEnv: false, version: null },

    // Special characters
    { input: "user@example.com", sansEnv: false, version: null },
    { input: "User Name 123!", sansEnv: false, version: null },
    { input: "测试用户", sansEnv: false, version: null },
    { input: "user-with-dashes", sansEnv: false, version: null },

    // Version forced (all characters, theme A)
    { input: "Test", sansEnv: false, version: { part: "00", theme: "A" } },
    { input: "Test", sansEnv: false, version: { part: "01", theme: "A" } },
    { input: "Test", sansEnv: false, version: { part: "02", theme: "A" } },
    { input: "Test", sansEnv: false, version: { part: "03", theme: "A" } },
    { input: "Test", sansEnv: false, version: { part: "04", theme: "A" } },
    { input: "Test", sansEnv: false, version: { part: "05", theme: "A" } },
    { input: "Test", sansEnv: false, version: { part: "06", theme: "A" } },
    { input: "Test", sansEnv: false, version: { part: "07", theme: "A" } },
    { input: "Test", sansEnv: false, version: { part: "08", theme: "A" } },
    { input: "Test", sansEnv: false, version: { part: "09", theme: "A" } },
    { input: "Test", sansEnv: false, version: { part: "10", theme: "A" } },
    { input: "Test", sansEnv: false, version: { part: "11", theme: "A" } },
    { input: "Test", sansEnv: false, version: { part: "12", theme: "A" } },
    { input: "Test", sansEnv: false, version: { part: "13", theme: "A" } },
    { input: "Test", sansEnv: false, version: { part: "14", theme: "A" } },
    { input: "Test", sansEnv: false, version: { part: "15", theme: "A" } },

    // Different themes
    { input: "Theme", sansEnv: false, version: { part: "01", theme: "A" } },
    { input: "Theme", sansEnv: false, version: { part: "01", theme: "B" } },
    { input: "Theme", sansEnv: false, version: { part: "01", theme: "C" } },

    // Longer strings
    { input: "This is a longer test string with multiple words", sansEnv: false, version: null },
    { input: "john.doe@example.com", sansEnv: false, version: null },

    // Numbers
    { input: "12345", sansEnv: false, version: null },
    { input: "User123", sansEnv: false, version: null },
];

// Generate all pure avatars (all 16 characters × 3 themes × 2 background modes = 96 total)
const characters = ["00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15"];
const themes = ["A", "B", "C"];
const characterNames = ["Robo", "Girl", "Blonde", "Guy", "Country", "Geeknot", "Asian", "Punk", "Afrohair", "Normie Female", "Older", "Firehair", "Blond", "Ateam", "Rasta", "Street"];

for (let charIdx = 0; charIdx < characters.length; charIdx++) {
    for (let theme of themes) {
        // Add with background
        testCases.push({
            input: `${characterNames[charIdx]} - Theme ${theme}`,
            sansEnv: false,
            version: { part: characters[charIdx], theme: theme }
        });
        // Add without background
        testCases.push({
            input: `${characterNames[charIdx]} - Theme ${theme} (no background)`,
            sansEnv: true,
            version: { part: characters[charIdx], theme: theme }
        });
    }
}

console.log('Generating test vectors from JavaScript implementation...');
console.log(`Total test cases: ${testCases.length}\n`);

const results = [];

for (let i = 0; i < testCases.length; i++) {
    const testCase = testCases[i];
    const { input, sansEnv, version } = testCase;

    try {
        // multiavatar expects null or undefined for hash-based generation
        const svg = multiavatar(input, sansEnv === true, version || undefined);

        results.push({
            id: i,
            input: input,
            sansEnv: sansEnv,
            version: version,
            output: svg,
            length: svg.length
        });

        console.log(`✓ Case ${i}: "${input.substring(0, 30)}${input.length > 30 ? '...' : ''}" -> ${svg.length} chars`);
    } catch (error) {
        console.error(`✗ Case ${i}: "${input}" failed:`, error.message);
        results.push({
            id: i,
            input: input,
            sansEnv: sansEnv,
            version: version,
            error: error.message
        });
    }
}

// Write results to JSON file
const outputFile = 'test-vectors.json';
fs.writeFileSync(outputFile, JSON.stringify(results, null, 2));

console.log(`\n✓ Test vectors written to ${outputFile}`);
console.log(`  Total: ${results.length} test cases`);
console.log(`  Success: ${results.filter(r => !r.error).length}`);
console.log(`  Failed: ${results.filter(r => r.error).length}`);
