package compiler;

import exceptions.MismatchException;
import generator.X86;
import lexer.SeaLexer;
import parser.SeaParser;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static compiler.IoUtils.execute;

public class SeaCompiler {

    static boolean asmf = false;
    static boolean guif = false;
    static boolean debug = true;
    static private String output = "";

    private SeaCompiler(String path) throws IOException {
        String[] array = path.split("/");
        String temp = array[array.length - 1];
		if (temp.length() > 4) {
			if (!path.substring(path.length() - 4, path.length()).equals(".sea")) {
				throw new MismatchException("ERROR: Filename must have the 'sea' extension");
			}
			String x;
			if (asmf) {
				x = path.substring(0, path.length() - 3) + "asm";
			} else {
				x = "/tmp/ilon.asm";
			}

            SeaParser parser = new SeaParser(new SeaLexer(new FileReader(path)));
			parser.parse();

            BufferedWriter writeToFile = new BufferedWriter(new FileWriter(x));
			writeToFile.write(new X86().output());
			writeToFile.close();

			if (!asmf) {
				if (debug) {
					execute("as -gstabs --32 -o /tmp/ilon.o " + x);
					execute("ld -melf_i386 -o " + "/tmp/ilon" + " /tmp/ilon.o");
					output = execute("/tmp/ilon");
				} else {
					execute("as -gstabs --32 -o /tmp/ilon.o " + x);
					execute("ld -melf_i386 -o " + path.subSequence(0, path.length() - 4) + " /tmp/ilon.o");
				}
			}
		} else {
			throw new MismatchException("ERROR: Filename too short");
		}
    }

    public static void compile(String path) throws IOException {
        compileFile(path);
    }

    public static String debug(String path) throws IOException {
        return debugFile(path);
    }

    static void compileFile(String path) throws IOException {
        System.out.println(debugFile(path));
    }

    static String debugFile(String path) throws IOException {
        return new SeaCompiler(path)
                .output();
    }

    public static void main(String args[]) throws IOException, InterruptedException {
        if (debug) {
            compileFile("./tests/algorithms/fibonacci.sea");
            compileFile("./tests/algorithms/timestamp.sea");
        } else {
            try {
                String s = getArgs(args);
				if (guif) {
					s = new FileChooser().name();
				} else {
					compileFile(s);
				}
                String x = "";
				if (asmf) {
					x = ".asm";
				}
                System.out.println(s.substring(0, s.length() - 4) + x + " was successfully created.");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }

    public static String getArgs(String args[]) throws Exception {
        String path = "";
		boolean pathSet = false;
        if (args.length < 1) {
            System.out.println("you need to specify at least one argument");
            System.exit(1);
        }
        for (String arg : args) {
            switch (arg) {
                case "-asm":
					if (asmf) {
						throw new MismatchException("the asm flag was already set once.");
					}
                    asmf = true;
                    break;
                case "-gui":
					if (guif) {
						throw new Exception("the gui flag was already set once.");
					}
                    guif = true;
                    break;
                default:
					if (pathSet) {
						throw new Exception("path was already set to: " + path);
					}
                    path = arg;
                    pathSet = true;
            }
        }
        return path;
    }

    private String output() {
        return output;
    }
}
