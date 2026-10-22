package spl.testing.tools;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import spl.testing.support.CompilerTestHarness;

/**
 * One-off tool: regenerates every golden .expected.c from the current generator.
 * Run with `mvn test -Dtest=RegenerateGoldens`, then delete this file.
 */
class RegenerateGoldens {

    private static final Path ROOT = Paths.get("src/test/resources/programs/generator");

    @Test
    void regenerate() throws IOException {
        if (!Files.isDirectory(ROOT)) {
            System.out.println("No generator fixtures found at " + ROOT);
            return;
        }

        List<Path> splFiles;
        try (Stream<Path> walk = Files.walk(ROOT)) {
            splFiles = walk
                    .filter(p -> p.toString().endsWith(".txt"))
                    .sorted()
                    .collect(Collectors.toList());
        }

        int regenerated = 0;
        for (Path splFile : splFiles) {
            String source = Files.readString(splFile, StandardCharsets.UTF_8);
            CompilerTestHarness.Result result = CompilerTestHarness.run(source);

            String base = splFile.toString().substring(0, splFile.toString().length() - 4);
            Path expected = Paths.get(base + ".expected.c");

            // Match the normalisation in GoldenFixtureAssert
            String normalised = result.targetCode
                    .replace("\r\n", "\n")
                    .replaceAll("\\s+$", "");
            if (!normalised.endsWith("\n")) normalised += "\n";

            Files.writeString(expected, normalised, StandardCharsets.UTF_8);
            System.out.println("Regenerated " + expected);
            regenerated++;
        }
        System.out.println("Total regenerated: " + regenerated);
    }//END_regenerate

}//END_RegenerateGoldens