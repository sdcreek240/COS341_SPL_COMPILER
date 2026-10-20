package spl.testing.generator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import spl.testing.support.CompilerTestHarness;

/**
 * Discovers every *.txt under src/test/resources/programs/generator/** and
 * runs it through the full pipeline, comparing to the sibling *.expected.c.
 *
 * Add a fixture = drop a .txt + .expected.c in any subdirectory.
 * No Java changes required.
 */
class GoldenFixtureTest {

    private static final Path GENERATOR_ROOT =
            Paths.get("src/test/resources/programs/generator");

    /** Enumerates every .txt fixture, paired with its expected output file. */
    static Stream<Fixture> fixtures() throws IOException {
        if (!Files.isDirectory(GENERATOR_ROOT)) {
            return Stream.empty();
        }
        try (Stream<Path> walk = Files.walk(GENERATOR_ROOT)) {
            List<Fixture> all = walk
                    .filter(p -> p.toString().endsWith(".txt"))
                    .sorted()
                    .map(splFile -> {
                        String base   = splFile.toString().substring(0, splFile.toString().length() - 4);
                        Path expected = Paths.get(base + ".expected.c");
                        return new Fixture(splFile, expected);
                    })
                    .collect(Collectors.toList());

            // Fail early if any fixture is missing its .expected.c counterpart.
            List<String> missing = all.stream()
                    .filter(f -> !Files.isRegularFile(f.expected))
                    .map(f -> f.expected.toString())
                    .collect(Collectors.toList());
            if (!missing.isEmpty()) {
                throw new IOException("Missing .expected.c files: " + String.join(", ", missing));
            }
            return all.stream();
        }
    }//END_fixtures

    @ParameterizedTest(name = "{0}")
    @MethodSource("fixtures")
    void fixtureMatchesGolden(Fixture fx) throws IOException {
        String source   = readClasspathResource(fx.splFile);
        String expected = readClasspathResource(fx.expected);

        CompilerTestHarness.Result result = CompilerTestHarness.run(source);
        assertNotNull(result.targetCode);

        assertEquals(
                normalize(expected),
                normalize(result.targetCode),
                "Golden output mismatch for " + fx.splFile);
    }//END_fixtureMatchesGolden

    // ---- helpers ----

    /** Loads the file from the test classpath (so it works regardless of working dir). */
    private static String readClasspathResource(Path fileOnDisk) throws IOException {
        String resourcePath = "/" + fileOnDisk.toString()
                .replace('\\', '/')
                .substring("src/test/resources/".length());
        URL url = GoldenFixtureTest.class.getResource(resourcePath);
        if (url == null) throw new IOException("Resource not on test classpath: " + resourcePath);
        try (InputStream in = url.openStream()) {
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        }
    }//END_readClasspathResource

    private static String normalize(String s) {
        return s.replace("\r\n", "\n").replaceAll("\\s+$", "");
    }//END_normalize

    /** Human-readable name for the parameterised test; also carries the two paths. */
    static final class Fixture {
        final Path splFile;
        final Path expected;

        Fixture(Path splFile, Path expected) {
            this.splFile  = splFile;
            this.expected = expected;
        }

        @Override
        public String toString() {
            String root = GENERATOR_ROOT.toString() + "/";
            return splFile.toString().startsWith(root)
                    ? splFile.toString().substring(root.length())
                    : splFile.toString();
        }
    }//END_Fixture

}//END_GoldenFixtureTest