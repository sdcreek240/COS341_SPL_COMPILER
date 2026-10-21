package spl.testing.negative;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import spl.testing.support.CompilerTestHarness;

/**
 * Every *.txt under programs/negative/ must be rejected somewhere in the
 * pipeline (parse, scope, type, or codegen). Each fixture is documented by
 * its filename: the name says which stage should catch it.
 */
class NegativeFixtureTest {

    private static final Path NEG_ROOT =
            Paths.get("src/test/resources/programs/negative");

    static Stream<Path> fixtures() throws IOException {
        if (!Files.isDirectory(NEG_ROOT)) return Stream.empty();
        try (Stream<Path> walk = Files.walk(NEG_ROOT)) {
            return walk
                    .filter(p -> p.toString().endsWith(".txt"))
                    .sorted()
                    .collect(Collectors.toList())
                    .stream();
        }
    }//END_fixtures

    @ParameterizedTest(name = "{0}")
    @MethodSource("fixtures")
    void isRejected(Path splFile) throws IOException {
        String source = Files.readString(splFile, StandardCharsets.UTF_8);

        // CompilerTestHarness.run() calls Assertions.fail on any rejection
        // (parse, scope, type). Codegen failures throw IllegalStateException.
        // So ANY of these is a valid rejection; the only invalid outcome is
        // "returns without complaint."
        assertThrows(Throwable.class,
                () -> CompilerTestHarness.run(source),
                "Expected rejection for " + splFile + " but pipeline succeeded");
    }//END_isRejected

}//END_NegativeFixtureTest