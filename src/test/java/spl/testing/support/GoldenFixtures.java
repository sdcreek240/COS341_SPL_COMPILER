package spl.testing.support;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class GoldenFixtures {

    private static final Path GENERATOR_ROOT =
            Paths.get("src/test/resources/programs/generator");

    private GoldenFixtures() { }

    /** Discovers every .txt under generator/<category>/, paired with its .expected.c. */
    public static Stream<Fixture> inCategory(String category) throws IOException {
        Path dir = GENERATOR_ROOT.resolve(category);
        if (!Files.isDirectory(dir)) return Stream.empty();

        try (Stream<Path> walk = Files.walk(dir)) {
            List<Fixture> all = walk
                    .filter(p -> p.toString().endsWith(".txt"))
                    .sorted()
                    .map(splFile -> {
                        String base   = splFile.toString().substring(0, splFile.toString().length() - 4);
                        Path expected = Paths.get(base + ".expected.c");
                        return new Fixture(splFile, expected);
                    })
                    .collect(Collectors.toList());

            List<String> missing = all.stream()
                    .filter(f -> !Files.isRegularFile(f.expected))
                    .map(f -> f.expected.toString())
                    .collect(Collectors.toList());
            if (!missing.isEmpty()) {
                throw new IOException("Missing .expected.c files in " + category
                        + ": " + String.join(", ", missing));
            }
            return all.stream();
        }
    }//END_inCategory

    public static final class Fixture {
        public final Path splFile;
        public final Path expected;

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

}//END_GoldenFixtures