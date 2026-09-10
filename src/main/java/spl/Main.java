package spl;

import spl.frontend.Frontend;
import java.nio.file.Path;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Path filePath = Path.of("programs/SPL.txt");
        try {
            System.out.println("Lexing SPL.txt");
            System.out.println("----------------------------------------");
            Frontend.lexFile(filePath);
        } catch (IOException e) {
            System.out.println("I/O error: " + e.getMessage());
        }
    }
}
