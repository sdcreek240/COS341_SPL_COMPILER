package spl.frontend.exceptionHandling;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.IntervalSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class SplErrorListener extends BaseErrorListener {

    private final String stage;
    private final String[] sourceLines;
    private Diagnostic diagnostic;

    public SplErrorListener(String stage, String[] sourceLines) {

        this.stage = stage;
        this.sourceLines = sourceLines;
    }//END_SplErrorListener

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                             int line, int charPositionInLine, String msg,
                             RecognitionException e) {

        List<String> expected = Collections.emptyList();
        if (recognizer instanceof Parser) {
            IntervalSet expectedTokens = ((Parser) recognizer).getExpectedTokens();
            expected = new ArrayList<>();
            for (int t : expectedTokens.toList()) {
                expected.add(recognizer.getVocabulary().getDisplayName(t));
            }
        }

        String snippet = (sourceLines != null && line - 1 >= 0 && line - 1 < sourceLines.length)
                ? sourceLines[line - 1] : null;

        if (diagnostic == null)
            diagnostic = new Diagnostic(stage, msg, line, charPositionInLine + 1, expected, snippet);
    }//SyntaxError

    public boolean hasError() { return diagnostic != null; }

    public Diagnostic getDiagnostic() { return diagnostic; }
}//END_SplErrorListener