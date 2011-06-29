package diergo.csv;

import static diergo.csv.Option.COMMENTS_SKIPPED;
import static diergo.csv.Option.EMPTY_AS_NULL;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * The command line utility to be used by {@code java -jar}. All supported
 * commands are registered in a {@linkplain #COMMANDS static map}.
 */
public class Main
{
  /**
   * The command registration. The key is the command name to be passed as first
   * command line argument.
   */
  public static final Map<String, Command> COMMANDS = new HashMap<String, Command>();
  static {
    registerCommand("sort", new Sort());
    registerCommand("cut", new Cut());
    registerCommand("filter", new Filter());
  }

  public static void registerCommand(String name, Command command)
  {
    COMMANDS.put(name, command);
  }

  /**
   * The first argument is the command, followed by options and input and output file.
   *
   * @see Cut
   * @see Sort
   * @see Filter
   */
  public static void main(String[] args)
  {
    Package pkg = Main.class.getPackage();
    String version = pkg == null ? "1.0" : pkg.getSpecificationVersion();
    if (args.length == 0) {
      System.out.println("Usage: java -jar diergo-csv-" + version + ".jar <command> [-<option> ...] [in|- [out]]");
      System.out.println("<command> is one of:");
      System.out.println("  cut [-fields=<n>[,...]]");
      System.out.println("  filter [-header=(false|true)] -field=<n> -value=<value>]");
      System.out.println("  sort [-header=(false|true)] [-order=<n>[n][,...]]");
      System.out.println("all commands allow the following options:");
      System.out.println("  -encoding=<charset> (default: system charset)");
      System.out.println("  -separator=<char> (default: ,)");
      return;
    }
    String command = args[0];
    Map<String, String> options = new HashMap<String, String>();
    options.put("encoding", System.getProperty("file.encoding"));
    options.put("separator", "\0");
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
      CommaSeparatedValuesWriter out = createOutput(writeToStdOut ? null : args[1], encoding, in);
      cmd.process(in, out, options);
      in.close();
      out.close();
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
  }

  private Main()
  {
  }

  private static CommaSeparatedValuesReader createInput(String filename, String encoding, char separator)
      throws IOException
  {
    Reader in = new InputStreamReader(filename == null ? System.in : new FileInputStream(filename), encoding);
    return separator == '\0' ? new CommaSeparatedValuesReader(in, new AutoSeparatorDeterminer(), EMPTY_AS_NULL, COMMENTS_SKIPPED)
        : new CommaSeparatedValuesReader(in, separator);
  }

  private static CommaSeparatedValuesWriter createOutput(String filename, String encoding, CommaSeparatedValuesReader in)
      throws IOException
  {
    OutputStream out = filename == null ? System.out : new FileOutputStream(filename);
    Writer writer = new BufferedWriter(new OutputStreamWriter(out, encoding));
    return new CommaSeparatedValuesWriter(writer, new DelegatingSeparatorDeterminer(in), EMPTY_AS_NULL, COMMENTS_SKIPPED);
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
