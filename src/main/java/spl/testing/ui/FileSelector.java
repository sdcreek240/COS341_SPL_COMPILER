package spl.testing.ui;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.util.List;

public final class FileSelector {

    private FileSelector() {
    }

    public static String select(String prompt, List<String> options) throws IOException {

        if (options.isEmpty()) {
            throw new IllegalArgumentException("No options to select from.");
        }

        try (Terminal terminal = TerminalBuilder.builder()
                .system(true)
                .build()) {

            terminal.enterRawMode();
            var reader = terminal.reader();
            var writer = terminal.writer();

            int selected = 0;
            render(writer, prompt, options, selected);

            //navigate
            while (true) {

                int key = reader.read();

                if (key == 27) {
                    int bracket = reader.read();
                    int direction = reader.read();

                    if (bracket == '[' && direction == 'A') { // up
                        selected = (selected - 1 + options.size()) % options.size();
                        render(writer, prompt, options, selected);
                    } else if (bracket == '[' && direction == 'B') { // down
                        selected = (selected + 1) % options.size();
                        render(writer, prompt, options, selected);
                    }
                } else if (key == '\r' || key == '\n') {//make selection

                    writer.println();
                    writer.flush();
                    return options.get(selected);
                } else if (key == 3) {//Cancel

                    throw new IOException("Selection cancelled.");
                }
            }
        }//END_try
    }//END_Select

    private static void render(java.io.PrintWriter writer, String prompt, List<String> options, int selected) {

        //clear
        writer.print("\033[H\033[2J");
        writer.println(prompt);
        writer.println("(use \u2191/\u2193 then Enter)");
        writer.println();

        for (int i=0; i<options.size(); i++) {

            if (i == selected) {

                writer.println("  > " + options.get(i));
            } else {

                writer.println("    " + options.get(i));
            }
        }//END_i

        writer.flush();
    }//END_render
}//END_FileSelector