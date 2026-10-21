package spl.testing.runtime;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import spl.testing.support.RuntimeFixtureHarness;

class RuntimeFixtureTest {

    private static final Path RUNTIME_ROOT =
            Paths.get("src/test/resources/programs/runtime");

    static Stream<Fixture> fixtures() throws IOException {
        if (!Files.isDirectory(RUNTIME_ROOT)) return Stream.empty();
        try (Stream<Path> walk = Files.walk(RUNTIME_ROOT)) {
            List<Fixture> all = walk
                    .filter(p -> p.toString().endsWith(".txt"))
                    .filter(p -> !p.toString().endsWith(".expected.txt"))
                    .sorted()
                    .map(splFile -> {
                        String base = splFile.toString().substring(0, splFile.toString().length() - 4);
                        Path expected = Paths.get(base + ".expected.txt");
                        return new Fixture(splFile, expected);
                    })
                    .collect(Collectors.toList());

            List<String> missing = all.stream()
                    .filter(f -> !Files.isRegularFile(f.expected))
                    .map(f -> f.expected.toString())
                    .collect(Collectors.toList());
            if (!missing.isEmpty()) {
                throw new IOException("Missing .expected.txt files: " + String.join(", ", missing));
            }
            return all.stream();
        }
    }//END_fixtures

    @ParameterizedTest(name = "{0}")
    @MethodSource("fixtures")
    void outputMatches(Fixture fx) throws IOException, InterruptedException {
        String splSource = RuntimeFixtureHarness.readClasspathResource(fx.splFile);
        String expected  = RuntimeFixtureHarness.readClasspathResource(fx.expected);

        String actual = RuntimeFixtureHarness.runAndCaptureStdout(splSource);

        assertEquals(normalize(expected), normalize(actual),
                "Runtime output mismatch for " + fx.splFile);
    }//END_outputMatches

    private static String normalize(String s) {
        return s.replace("\r\n", "\n").replaceAll("\\s+$", "");
    }//END_normalize

    static final class Fixture {
        final Path splFile;
        final Path expected;

        Fixture(Path splFile, Path expected) {
            this.splFile = splFile;
            this.expected = expected;
        }

        @Override
        public String toString() {
            String root = RUNTIME_ROOT.toString() + "/";
            return splFile.toString().startsWith(root)
                    ? splFile.toString().substring(root.length())
                    : splFile.toString();
        }
    }//END_Fixture

}//END_RuntimeFixtureTest