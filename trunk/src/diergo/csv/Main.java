package diergo.csv;

import java.io.*;
import java.util.*;

public class Main
{
    static Map<String, Command> COMMANDS;

    static {
        COMMANDS = new HashMap<String, Command>();
        COMMANDS.put("sort", new Sort());
        COMMANDS.put("cut", new Cut());
    }

    public static void main(String[] args)
    {
        if (args.length == 0) {
            System.out.println("Usage: " + COMMANDS.keySet() + " [-options] [in|- [out]]");
            return;
        }
        String command = args[0];
        Map<String, String> options = new HashMap<String, String>();
        options.put("encoding", System.getProperty("file.encoding"));
        options.put("header", "true");
        options.put("separator", ";");
        args = parseOptions(options, Arrays.asList(args).subList(1, args.length));
        boolean readFromStdIn = args.length == 0 || args[0].equals("-");
        boolean writeToStdOut = args.length < 2;
        Command cmd = COMMANDS.get(command);
        if (cmd == null) {
            System.err.println("Unknown command: " + command);
            return;
        }
        try {
            String encoding = options.get("encoding");
            char separator = options.get("separator").charAt(0);
            CommaSeparatedValuesReader in = createInput(readFromStdIn ? null : args[0], encoding, separator);
            CommaSeparatedValuesWriter out = createOutput(writeToStdOut ? null : args[1], encoding, separator);
            cmd.process(in, out, options);
            in.close();
            out.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private static CommaSeparatedValuesReader createInput(String filename, String encoding, char separator)
        throws IOException
    {
        InputStream in = filename == null ? System.in : new FileInputStream(filename);
        return new CommaSeparatedValuesReader(new BufferedReader(new InputStreamReader(in, encoding)), separator, true);
    }

    private static CommaSeparatedValuesWriter createOutput(String filename, String encoding, char separator)
        throws IOException
    {
        OutputStream out = filename == null ? System.out : new FileOutputStream(filename);
        Writer writer = new BufferedWriter(new OutputStreamWriter(out, encoding));
        return new CommaSeparatedValuesWriter(writer, separator);
    }

    private static String[] parseOptions(Map<String, String> options, List<String> args)
    {
        args = new ArrayList<String>(args);
        Iterator<String> i = args.iterator();
        while (i.hasNext()) {
            String option = i.next();
            if (option.startsWith("-")) {
                i.remove();
                if (option.equals("--")) {
                    break;
                }
                int o = option.indexOf('=');
                if (o == -1) {
                    options.put(option.substring(1), null);
                } else {
                    options.put(option.substring(1, o), option.substring(o + 1));
                }
            }
        }
        return args.toArray(new String[args.size()]);
    }
}
