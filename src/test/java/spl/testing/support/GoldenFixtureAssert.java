package spl.testing.support;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;

public final class GoldenFixtureAssert {

    private GoldenFixtureAssert() { }

    public static void assertMatchesGolden(GoldenFixtures.Fixture fx) throws IOException {
        String source   = readClasspathResource(fx.splFile);
        String expected = normalize(readClasspathResource(fx.expected));

        CompilerTestHarness.Result result = CompilerTestHarness.run(source);
        String actual = normalize(result.targetCode);

        if (expected.equals(actual)) return;

        fail(buildDiffMessage(fx.splFile.toString(), expected, actual));
    }//END_assertMatchesGolden

    private static String buildDiffMessage(String name, String expected, String actual) {
        List<String> expLines = expected.lines().toList();
        List<String> actLines = actual.lines().toList();

        int max = Math.max(expLines.size(), actLines.size());
        StringBuilder sb = new StringBuilder();
        sb.append("\n===== MISMATCH: ").append(name).append(" =====\n");
        sb.append("expected (").append(expLines.size()).append(" lines) vs actual (")
          .append(actLines.size()).append(" lines)\n");
        sb.append("----------------------------------------------------\n");

        int differences = 0;
        for (int i = 0; i < max; i++) {
            String e = i < expLines.size() ? expLines.get(i) : "<EOF>";
            String a = i < actLines.size() ? actLines.get(i) : "<EOF>";
            if (!e.equals(a)) {
                differences++;
                sb.append(String.format("line %3d  expected: %s%n", i + 1, e));
                sb.append(String.format("          actual:   %s%n", a));
            }
        }

        if (differences == 0) {
            sb.append("(no line differences detected; trailing whitespace or encoding)\n");
        } else {
            sb.append("----------------------------------------------------\n");
            sb.append(differences).append(" line(s) differ\n");
        }
        return sb.toString();
    }//END_buildDiffMessage

    private static String readClasspathResource(Path fileOnDisk) throws IOException {
        String resourcePath = "/" + fileOnDisk.toString()
                .replace('\\', '/')
                .substring("src/test/resources/".length());
        URL url = GoldenFixtureAssert.class.getResource(resourcePath);
        if (url == null) throw new IOException("Resource not on test classpath: " + resourcePath);
        try (InputStream in = url.openStream()) {
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        }
    }//END_readClasspathResource

    private static String normalize(String s) {
        return s.replace("\r\n", "\n").replaceAll("\\s+$", "");
    }//END_normalize

}//END_GoldenFixtureAssert