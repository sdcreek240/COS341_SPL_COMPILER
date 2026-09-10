package spl.frontend;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.List;

import spl.antlr.SPLLexer;
import spl.antlr.SPLParser;
import spl.frontend.exceptionHandling.CompilationResult;
import spl.frontend.exceptionHandling.Diagnostic;
import spl.frontend.exceptionHandling.SplErrorListener;

public final class Frontend {

    public CompilationResult processFile(Path filePath) {

        String source;

        try {

            source = Files.readString(filePath);
        } catch (IOException e) {

            Diagnostic ioDiagnostic = new Diagnostic(
                "Lexing", "could not read file '" + filePath + "': " + e.getMessage(),
                -1, -1, List.of(), null);
            return CompilationResult.failure(ioDiagnostic, null);
        }

        String[] sourceLines = source.split("\n", -1);

        // Lex
        CharStream input = CharStreams.fromString(source);
        SPLLexer lexer = new SPLLexer(input);
        lexer.removeErrorListeners();
        SplErrorListener lexListener = new SplErrorListener("Lexing", sourceLines);
        lexer.addErrorListener(lexListener);

        CommonTokenStream tokens = new CommonTokenStream(lexer);
        tokens.fill();

        if (lexListener.hasError())
            return CompilationResult.failure(lexListener.getDiagnostic(), tokens);

        //Parse
        SPLParser parser = new SPLParser(tokens);
        parser.removeErrorListeners();
        SplErrorListener parseListener = new SplErrorListener("Parsing", sourceLines);
        parser.addErrorListener(parseListener);
        
        ParseTree tree = parser.spl_prog();

        if (parseListener.hasError())
            return CompilationResult.failure(parseListener.getDiagnostic(), tokens);

        return CompilationResult.success(tree, tokens, parser);
    }//END_processFile
}//END_Frontend