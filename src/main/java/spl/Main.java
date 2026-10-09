package spl;

import java.nio.file.Files;
import java.nio.file.Path;

import spl.frontend.Frontend;
import spl.frontend.exceptionHandling.CompilationResult;
import spl.frontend.exceptionHandling.DebugPrinter;
import spl.frontend.io.XMLWriter;

//Backend Scoping
import spl.backend.scoping.SemanticAnalyser;
import spl.backend.scoping.SemanticException;
import spl.backend.scoping.SymbolTable;

public class Main {
    public static void main(String[] args) {

        Path filePath = Path.of(args.length > 0 ? args[0] : "programs/SPL.txt");

        if (!Files.isRegularFile(filePath)) {
            System.err.println("Input file not found: " + filePath.toAbsolutePath());
            System.err.println("Usage: java -jar group-21.jar <path-to-SPL-file>");
            System.exit(1);
        }

        Frontend frontend = new Frontend();

        CompilationResult result = frontend.processFile(filePath);

        if (result.isSuccess()) {

            System.out.println("Parsed OK: " + filePath.getFileName());

            DebugPrinter.printTree(result.getTree(), result.getParser());
            System.out.println("Outputting the XML now");
            Path xmlPath = Path.of("tree.xml");
            XMLWriter.write(result.getTree(), result.getParser(), xmlPath);
            System.out.println("Check " + xmlPath.toAbsolutePath());

            // Scope analysis
            try {
                SemanticAnalyser.analyse(xmlPath, xmlPath);
                System.out.println("Scope analysis OK - symbols written to " + xmlPath.toAbsolutePath());
            } catch (SemanticException e) {
                System.out.println("Semantic error: " + e.getMessage());
            }

        } else {

            System.out.println(result.getDiagnostic().render());
        }
    }
}