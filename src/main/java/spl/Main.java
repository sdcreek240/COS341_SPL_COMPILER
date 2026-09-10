package spl;

import spl.frontend.Frontend;
import spl.frontend.ui.FileSelector;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class Main {

    private static final Path PROGRAMS_DIR = Path.of("programs");

    public static void main(String[] args) {
        try {
            // Fetch files
            List<String> files = listTxtFiles(PROGRAMS_DIR);

            if (files.isEmpty()) {
                System.out.println("No .txt files found in " + PROGRAMS_DIR.toAbsolutePath());
                return;
            }

            // Select program file
            String chosen = FileSelector.select("Select an SPL test file to lex:", files);
            Path filePath = PROGRAMS_DIR.resolve(chosen);

            // Lex
            System.out.println("Lexing: " + chosen);
            System.out.println("----------------------------------------");

            // Call the new ANTLR-based frontend
            Frontend.lexFile(filePath);

        } catch (IOException e) {
            System.out.println("I/O error: " + e.getMessage());
        }
    }

    // 🎅's little helpers
    private static List<String> listTxtFiles(Path dir) throws IOException {
        if (!Files.isDirectory(dir)) {
            return List.of();
        }
        try (Stream<Path> stream = Files.list(dir)) {
            return stream
                    .filter(p -> p.toString().endsWith(".txt"))
                    .map(p -> p.getFileName().toString())
                    .sorted()
                    .toList();
        }
    }
}
