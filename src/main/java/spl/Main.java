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
import spl.backend.scoping.TypeAnalyser;
import spl.backend.tree.SyntaxTree;
//Code gen
import spl.backend.generator.CodeGenerator;

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

            try {
                SyntaxTree tree = SyntaxTree.load(xmlPath);
                
                Path codePath = Path.of("target.c");
                new CodeGenerator(table, tree).generateToFile(codePath);
                System.out.println("Code generation OK - written to " + codePath.toAbsolutePath());
            } catch (RuntimeException e){
                System.err.println("Code generation failed: "+e.getMessage());
                throw e;
            }

        } else {

            System.out.println(result.getDiagnostic().render());
        }
    }
}