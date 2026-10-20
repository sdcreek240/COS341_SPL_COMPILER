package spl.backend.generator;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import spl.backend.scoping.SymbolTable;

public final class CodeGenerator {

    //output path
    public static final Path DEFAULT_OUTPUT = Paths.get("target.c");

    private final SymbolTable symbols;

    //counters
    private int tempCounter  = 0;
    private int labelCounter = 0;

    public CodeGenerator(SymbolTable symbols) {
        if (symbols == null) throw new IllegalArgumentException("symbols must not be null");
        this.symbols = symbols;
    }

    /** Generate target code and output target file to default path */
    public void generateToFile() {
        generateToFile(DEFAULT_OUTPUT);
    }//END_generateToFile

    /** Generate target code and writes to target file at given path */
    public void generateToFile(Path outputPath) {
        String code = generate();
        try {
            Path parent = outputPath.toAbsolutePath().getParent();
            if (parent != null) Files.createDirectories(parent);
            try (BufferedWriter w = Files.newBufferedWriter(outputPath, StandardCharsets.UTF_8)) {
                w.write(code);
            }
        } catch (IOException e) {
            throw new UncheckedIOException("failed to write target code to " + outputPath, e);
        }
    }//END_generateToFile

    /**
     * Produces the full target program as a string.
     */
    public String generate() {
        return "/* end */\n";
    }//END_generate

    //🎅's little helpers

    /** Allocates a fresh temporary variable name (e.g. vN0, vN1). */
    String freshTemp() {
        return "vN" + (tempCounter++);
    }

    /** Allocates a fresh jump label (e.g. L0, L1). */
    String freshLabel() {
        return "L" + (labelCounter++);
    }

    /** Exposed for the type rule in step 4: int vs float, decided once per program. */
    SymbolTable symbols() {
        return symbols;
    }
}//END_CodeGenerator