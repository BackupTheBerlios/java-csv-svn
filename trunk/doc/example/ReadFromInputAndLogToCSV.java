package example;

import static diergo.csv.FixedSeparatorDeterminer.fixedSeparator;
import static diergo.csv.Option.COMMENTS_SKIPPED;
import static diergo.csv.Option.EMPTY_AS_NULL;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

import diergo.csv.CommaSeparatedValuesWriter;

/**
 * Logs the input as CSV data with a time stamp column.
 *
 * Example input:
 *
 * <pre>
 * &gt; Hello world!
 * &gt; these are some words
 * &gt; I said, "Do it!"
 * &gt;
 * </pre>
 *
 * will create CSV output (time stamps will vary):
 *
 * <pre>
 * timestamp,input
 * 009-02-25 11:03:22,Hello world!
 * 009-02-25 11:04:13,these are some words
 * 009-02-25 11:04:57,"I said, ""Do it!"""
 * </pre>
 *
 * The argument will become the CSV output file.
 */
public class ReadFromInputAndLogToCSV
{
  private static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  /**
   * An optional argument is the file to write the log.
   */
  public static void main(String... args)
  {
    try {
      Writer out = args.length == 0 ? new OutputStreamWriter(System.out) : new FileWriter(new File(args[0]));
      BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
      CommaSeparatedValuesWriter csv = new CommaSeparatedValuesWriter(out, fixedSeparator(','), EMPTY_AS_NULL, COMMENTS_SKIPPED);
      csv.write("timestamp", "input");
      do {
        String line = in.readLine();
        if (line == null || line.length() == 0) {
          break;
        }
        csv.write(TIMESTAMP_FORMAT.format(new Date()), line.trim());
      } while (true);
      if (args.length != 0) {
        out.close();
      }
    } catch (IOException e) {
      System.err.println("Error: " + e);
    }
  }
}