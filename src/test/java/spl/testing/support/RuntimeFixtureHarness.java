package spl.testing.support;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Assertions;

/**
 * Compiles the compiler's output to a native executable, runs it, and returns stdout.
 * Used by RuntimeFixtureTest to compare against a fixture's expected .txt file.
 */
public final class RuntimeFixtureHarness {

    /** Result of compiling and running the generated C++. */
    public static final class Result {
        public final int exitCode;
        public final String stdout;
        public final String stderr;
        public final Path workDir;

        Result(int exitCode, String stdout, String stderr, Path workDir) {
            this.exitCode = exitCode;
            this.stdout = stdout;
            this.stderr = stderr;
            this.workDir = workDir;
        }
    }//END_Result

    private RuntimeFixtureHarness() { }

    /**
     * Runs the full pipeline on {@code splSource}, then g++-compiles and runs the
     * resulting C++, capturing stdout. Fails the test if anything goes wrong.
     */
    public static String runAndCaptureStdout(String splSource) throws IOException, InterruptedException {
        // 1. Generate target.cpp via the existing harness
        CompilerTestHarness.Result gen = CompilerTestHarness.run(splSource);
        Path workDir = gen.workDir;
        Path cpp = workDir.resolve("target.cpp");
        Files.writeString(cpp, gen.targetCode, StandardCharsets.UTF_8);

        // 2. Compile
        Path exe = workDir.resolve("program");
        ProcessBuilder gpp = new ProcessBuilder(
                "g++", "-std=c++17", "-Wall", "-Wextra",
                "-o", exe.toString(), cpp.toString());
        gpp.redirectErrorStream(false);
        Process compileProc = gpp.start();
        String compileErr = new String(compileProc.getErrorStream().readAllBytes(), StandardCharsets.UTF_8);
        int compileExit = compileProc.waitFor();
        Assertions.assertEquals(0, compileExit,
                () -> "C++ compilation failed\nstderr:\n" + compileErr + "\ngenerated source:\n" + gen.targetCode);

        // 3. Run
        ProcessBuilder run = new ProcessBuilder(exe.toString());
        run.redirectErrorStream(false);
        Process runProc = run.start();
        String stdout = new String(runProc.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        String stderr = new String(runProc.getErrorStream().readAllBytes(), StandardCharsets.UTF_8);
        int runExit = runProc.waitFor();
        Assertions.assertEquals(0, runExit,
                () -> "Program exited with code " + runExit + "\nstderr:\n" + stderr);

        return stdout;
    }//END_runAndCaptureStdout

    /** Loads a resource from the test classpath (see GoldenFixtures for the same pattern). */
    public static String readClasspathResource(Path fileOnDisk) throws IOException {
        String resourcePath = "/" + fileOnDisk.toString()
                .replace('\\', '/')
                .substring("src/test/resources/".length());
        URL url = RuntimeFixtureHarness.class.getResource(resourcePath);
        if (url == null) throw new IOException("Resource not on test classpath: " + resourcePath);
        try (InputStream in = url.openStream()) {
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        }
    }//END_readClasspathResource

}//END_RuntimeFixtureHarness