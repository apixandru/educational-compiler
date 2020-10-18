package compiler;

import exceptions.UnexpectedException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class IoUtils {

    public static String execute(final String command) {
        try {
            String output = "";
            Process p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = reader.readLine();
            while (line != null) {
                output += line + '\n';
                line = reader.readLine();
            }
            return output;
        } catch (IOException | InterruptedException e) {
            throw new UnexpectedException("Failed to execute process: " + e);
        }
    }

    public static BufferedReader buffer(Reader reader) {
        if (reader instanceof BufferedReader) {
            return (BufferedReader) reader;
        }
        return new BufferedReader(reader);
    }

}
