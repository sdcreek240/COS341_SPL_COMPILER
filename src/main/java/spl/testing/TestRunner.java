package spl.testing;

import spl.frontend.Frontend;
import spl.frontend.FrontendException;
import spl.testing.ui.FileSelector;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import org.antlr.v4.runtime.tree.ParseTree;

public class TestRunner {

    private static final Path PROGRAMS_DIR = Path.of("programs");

    public static void main(String[] args) {

        //Get debug
        boolean debug = args.length>0 && args[0].equalsIgnoreCase("debug");
        Frontend frontend = new Frontend(debug);

        try {

            // choose valids or inValids
            List<String> topDirs = listSubDirs(PROGRAMS_DIR);
            String chosenTop = FileSelector.select("Choose test category:", topDirs);
            Path chosenTopPath = PROGRAMS_DIR.resolve(chosenTop);

            Path filePath;

            if ("valids".equals(chosenTop)) {

                // list valid files
                List<String> files = listTxtFiles(chosenTopPath);
                String chosenFile = FileSelector.select("Choose valid program:", files);
                filePath = chosenTopPath.resolve(chosenFile);
            } else {

                // syntax or grammar
                List<String> subDirs = listSubDirs(chosenTopPath);
                String chosenSub = FileSelector.select("Choose invalid type:", subDirs);
                Path chosenSubPath = chosenTopPath.resolve(chosenSub);

                List<String> files = listTxtFiles(chosenSubPath);
                String chosenFile = FileSelector.select("Choose invalid program:", files);
                filePath = chosenSubPath.resolve(chosenFile);
            }

           // Call frontend to run on input file
           ParseTree tree = frontend.processFile(filePath);

        } catch (IOException e) {
            System.out.println("Input file error: "+ e.getMessage());
        }
    }//END_main

    private static List<String> listSubDirs(Path dir) throws IOException {

        try (Stream<Path> stream = Files.list(dir)) {

            return stream.filter(Files::isDirectory)
                         .map(p -> p.getFileName().toString())
                         .sorted()
                         .toList();
        }
    }//END_listTxtFiles

    private static List<String> listTxtFiles(Path dir) throws IOException {

        try (Stream<Path> stream = Files.list(dir)) {

            return stream.filter(p -> p.toString().endsWith(".txt"))
                         .map(p -> p.getFileName().toString())
                         .sorted()
                         .toList();
        }
    }//END_listTxtFiles
}
