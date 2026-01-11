#!/usr/bin/env node

const multiavatar = require('./dist/commonjs/index.js');
const fs = require('fs');

// Test inputs covering various scenarios
const testCases = [
    // Basic examples (no version parameter)
    { input: "Binx Bond", sansEnv: false },
    { input: "Test User", sansEnv: false },
    { input: "Alice", sansEnv: false },
    { input: "Bob", sansEnv: false },

    // Sans environment
    { input: "Example", sansEnv: true },
    { input: "No Background", sansEnv: true },

    // Empty and special cases
    { input: "", sansEnv: false },
    { input: "test", sansEnv: false },
    { input: "TEST", sansEnv: false },

    // Special characters
    { input: "user@example.com", sansEnv: false },
    { input: "User Name 123!", sansEnv: false },
    { input: "测试用户", sansEnv: false },
    { input: "user-with-dashes", sansEnv: false },

    // Version forced (sample of characters)
    { input: "Test", sansEnv: false, version: { part: "00", theme: "A" } },
    { input: "Test", sansEnv: false, version: { part: "01", theme: "A" } },
    { input: "Test", sansEnv: false, version: { part: "05", theme: "A" } },
    { input: "Test", sansEnv: false, version: { part: "10", theme: "A" } },
    { input: "Test", sansEnv: false, version: { part: "15", theme: "A" } },

    // Different themes
    { input: "Theme", sansEnv: false, version: { part: "01", theme: "A" } },
    { input: "Theme", sansEnv: false, version: { part: "01", theme: "B" } },
    { input: "Theme", sansEnv: false, version: { part: "01", theme: "C" } },

    // Longer strings
    { input: "This is a longer test string", sansEnv: false },
    { input: "john.doe@example.com", sansEnv: false },

    // Numbers
    { input: "12345", sansEnv: false },
    { input: "User123", sansEnv: false },
];

console.log('Generating test vectors from JavaScript implementation...');
console.log(`Total test cases: ${testCases.length}\n`);

const results = [];

for (let i = 0; i < testCases.length; i++) {
    const testCase = testCases[i];
    const { input, sansEnv, version } = testCase;

    try {
        // Call with appropriate parameters
        const svg = version ? multiavatar(input, sansEnv, version) : multiavatar(input, sansEnv);

        results.push({
            id: i,
            input: input,
            sansEnv: sansEnv,
            version: version || null,
            output: svg,
            length: svg.length
        });

        const versionStr = version ? ` [${version.part}${version.theme}]` : '';
        console.log(`✓ Case ${i}: "${input.substring(0, 25)}${input.length > 25 ? '...' : ''}"${versionStr} -> ${svg.length} chars`);
    } catch (error) {
        console.error(`✗ Case ${i}: "${input}" failed:`, error.message);
        results.push({
            id: i,
            input: input,
            sansEnv: sansEnv,
            version: version || null,
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
