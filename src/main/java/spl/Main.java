package spl;

import spl.frontend.Frontend;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {

        Path filePath = Path.of("programs/SPL.txt");

        Frontend frontend = new Frontend(false);

        frontend.processFile(filePath);
    }
}
