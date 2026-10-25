package spl;

import java.nio.file.Files;
import java.nio.file.Path;

import spl.backend.generator.CodeGenException;
import spl.backend.generator.CodeGenerator;
import spl.backend.scoping.SemanticAnalyser;
import spl.backend.scoping.SemanticException;
import spl.backend.scoping.SymbolTable;
import spl.backend.scoping.TypeAnalyser;
import spl.backend.tree.SyntaxTree;
import spl.frontend.Frontend;
import spl.frontend.exceptionHandling.CompilationResult;
import spl.frontend.exceptionHandling.DebugPrinter;
import spl.frontend.io.XMLWriter;

public class Main {
    public static void main(String[] args) {

        Path filePath = Path.of(args.length > 0 ? args[0] : "programs/SPL.txt");

        if (!Files.isRegularFile(filePath)) {
            System.err.println("Input file not found: " + filePath.toAbsolutePath());
            System.err.println("Usage: java -jar group-21.jar <path-to-SPL-file>");
            System.exit(1);
        }

        Frontend frontend = new Frontend();

        try {
            Files.deleteIfExists(Path.of("tree.xml"));
            Files.deleteIfExists(CodeGenerator.DEFAULT_OUTPUT);
        } catch (java.io.IOException e) {
            System.err.println("Warning: could not delete output from a previous run: " + e.getMessage());
        }

        CompilationResult result = frontend.processFile(filePath);

        if (result.isSuccess()) {

            System.out.println("Parsed OK: " + filePath.getFileName());

            DebugPrinter.printTree(result.getTree(), result.getParser());
            System.out.println("Outputting the XML now");
            Path xmlPath = Path.of("tree.xml");
            XMLWriter.write(result.getTree(), result.getParser(), xmlPath);
            System.out.println("Check " + xmlPath.toAbsolutePath());

            // Scope analysis
            SymbolTable table;
            try {
                table = SemanticAnalyser.analyse(xmlPath, xmlPath);   // 2a: scopes and names
                System.out.println("Scope analysis OK");

                TypeAnalyser.analyse(xmlPath, xmlPath, table);                    // 2b: types
                System.out.println("Type analysis OK - written to " + xmlPath.toAbsolutePath());
            } catch (SemanticException e) {
                System.out.println("Semantic error: " + e.getMessage());
                return; //dont run code gen
            }

            SyntaxTree tree = SyntaxTree.load(xmlPath);

            try {
                new CodeGenerator(table, tree).generateToFile();
                System.out.println("Code generation OK - written to " +
                        CodeGenerator.DEFAULT_OUTPUT.toAbsolutePath());
                    } catch (CodeGenException e) {
            System.out.println("Code generation error: " + e.getMessage());
            return; // error in the SPL program: clean message only
        } catch (RuntimeException e) {
            System.err.println("Code generation failed: " + e.getMessage());
            throw e; // compiler bug: keep the full stack trace
        }

        } else {

            System.out.println(result.getDiagnostic().render());
        }
    }
}