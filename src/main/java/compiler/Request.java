package compiler;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public abstract class Request {

    public String execute(final String s) throws IOException, InterruptedException {
        String output = "";
        Process p = Runtime.getRuntime().exec(s);
        p.waitFor();
        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = reader.readLine();
        while (line != null) {
            output += line + '\n';
            line = reader.readLine();
        }
        return output;
    }

    // pretty sure this is not needed
    public static String pwd() {
        return new File(".")
                .getAbsolutePath();
    }
} 
