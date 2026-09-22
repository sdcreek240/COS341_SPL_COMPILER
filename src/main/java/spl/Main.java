package spl;

import spl.frontend.Frontend;
import spl.frontend.exceptionHandling.CompilationResult;
import spl.frontend.exceptionHandling.DebugPrinter;
import spl.frontend.io.XMLWriter;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {

        Path filePath = Path.of("programs/SPL.txt");

        Frontend frontend = new Frontend();

        CompilationResult result = frontend.processFile(filePath);

        
        if (result.isSuccess()) {

            System.out.println("Parsed OK: " + filePath.getFileName());

            DebugPrinter.printTree(result.getTree(), result.getParser());
            System.out.println("Outputting the XML now");
            XMLWriter.write(result.getTree(), result.getParser(), Path.of("tree.xml"));
            System.out.println("Check tree.xml");

        } else {

            System.out.println(result.getDiagnostic().render());
        }
    }
}
