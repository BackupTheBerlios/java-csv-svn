package example;

import diergo.array.mapped.MappingIterator;
import diergo.csv.CommaSeparatedValues;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Reads CSV data and write all data as XML.
 *
 * The CSV data file has to contain a header.
 * The XML will be a document with toplevel element &lt;csv&gt;,
 * each data line will become a &lt;data&gt; element with sub elements containing the values as text named as the column headers of the CSV data.
 *
 * CSV input:
 * <pre>id,verb,noun
 *1,parse,text
 *2,write,data
 *3,execute</pre>
 * 
 * will become:
 * 
 * <pre>&lt;csv&gt;
 * &lt;data&gt;
 *  &lt;id&gt;1&lt;/id&gt;
 *  &lt;verb&gt;parse&lt;/verb&gt;
 *  &lt;noun&gt;text&lt;/noun&gt;
 * &lt;/data&gt;
 * &lt;data&gt;
 *  &lt;id&gt;2&lt;/id&gt;
 *  &lt;verb&gt;write&lt;/verb&gt;
 *  &lt;noun&gt;data&lt;/noun&gt;
 * &lt;/data&gt;
 * &lt;data&gt;
 *  &lt;id&gt;3&lt;/id&gt;
 *  &lt;verb&gt;execute&lt;/verb&gt;
 * &lt;/data&gt;
 *&lt;/csv&gt;</pre>
 *
 * The first argument will become the CSV input file, the second the XML output file.
 */
public class ReadCsvFileAndWriteAsXml {

  public static void main(String... args) {
    try {
      Reader in = args.length == 0 ? new InputStreamReader(System.in) : new FileReader(new File(args[0]));
      Writer out = args.length > 1 ? new FileWriter(new File(args[1])) : new OutputStreamWriter(System.out);
      out.append("<?xml version='1.0' encoding='").append(Charset.defaultCharset().name()).append("'?>");
      out.append("<csv>");
      for (Map<String,String> data : MappingIterator.createWithHeaders(CommaSeparatedValues.parse(in))) {
        out.append(writeData(data));
      }
      out.append("</csv>");
      out.close();
      if (args.length != 0) {
        in.close();
      }
    }
    catch (IOException e) {
      System.err.println("Error: " + e);
    }
  }

  private static String writeData(Map<String,String> data) {
    StringBuilder result = new StringBuilder();
    result.append("<data>");
    for (Map.Entry<String,String> entry : data.entrySet()) {
      result.append(writeEntry(entry));
    }
    result.append("<data>");
    return result.toString();
  }

  private static String writeEntry(Map.Entry<String,String> entry) {
    StringBuilder result = new StringBuilder()
        .append('<')
        .append(entry.getKey());
    if (entry.getValue() == null) {
      result.append("/>");
    } else {
      result
      .append('>')
      .append(entry.getValue())
      .append("</")
      .append(entry.getKey())
      .append('>');
    }
    return result.toString();
  }
}
