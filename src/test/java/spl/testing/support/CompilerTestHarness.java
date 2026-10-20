package spl.testing.support;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;

import spl.backend.generator.CodeGenerator;
import spl.backend.scoping.SemanticAnalyser;
import spl.backend.scoping.SemanticException;
import spl.backend.scoping.SymbolTable;
import spl.backend.scoping.TypeAnalyser;
import spl.backend.tree.SyntaxTree;
import spl.frontend.Frontend;
import spl.frontend.exceptionHandling.CompilationResult;
import spl.frontend.io.XMLWriter;

public final class CompilerTestHarness {

    public static final class Result {
        public final String targetCode;
        public final String treeXml;
        public final Path   workDir;

        Result(String targetCode, String treeXml, Path workDir) {
            this.targetCode = targetCode;
            this.treeXml    = treeXml;
            this.workDir    = workDir;
        }
    }//END_Result

    private CompilerTestHarness() { }

    /** Runs full pipeline; fails test if any stage rejects. Returns generated code. */
    public static Result run(String splSource) {
        try {
            Path workDir = Files.createTempDirectory("spl-test-");
            Path splFile    = workDir.resolve("program.txt");
            Path xmlFile    = workDir.resolve("tree.xml");
            Path targetFile = workDir.resolve("target.c");

            Files.writeString(splFile, splSource, StandardCharsets.UTF_8);

            Frontend frontend = new Frontend();
            CompilationResult parsed = frontend.processFile(splFile);
            Assertions.assertTrue(
                    parsed.isSuccess(),
                    () -> "Fixture failed to parse: "
                            + (parsed.getDiagnostic() == null ? "(no diagnostic)" : parsed.getDiagnostic().render()));

            XMLWriter.write(parsed.getTree(), parsed.getParser(), xmlFile);

            SymbolTable symbols;
            try {
                symbols = SemanticAnalyser.analyse(xmlFile, xmlFile);
            } catch (SemanticException e) {
                Assertions.fail("Fixture failed scope analysis: " + e.getMessage());
                return null;
            }
            try {
                TypeAnalyser.analyse(xmlFile, xmlFile, symbols);
            } catch (SemanticException e) {
                Assertions.fail("Fixture failed type analysis: " + e.getMessage());
                return null;
            }

            SyntaxTree tree = SyntaxTree.load(xmlFile);
            new CodeGenerator(symbols, tree).generateToFile(targetFile);

            String targetCode = Files.readString(targetFile, StandardCharsets.UTF_8);
            String treeXml    = Files.readString(xmlFile,    StandardCharsets.UTF_8);

            return new Result(targetCode, treeXml, workDir);

        } catch (IOException e) {
            throw new RuntimeException("I/O error while running SPL pipeline", e);
        }
    }//END_run

    /** Best-effort recursive delete of the per-test temp dir. */
    public static void cleanup(Result result) {
        if (result == null || result.workDir == null) return;
        try (Stream<Path> walk = Files.walk(result.workDir)) {
            walk.sorted(Comparator.reverseOrder()).forEach(p -> {
                try { Files.deleteIfExists(p); } catch (IOException ignored) { }
            });
        } catch (IOException ignored) { }
    }//END_cleanup

}//END_CompilerTestHarness