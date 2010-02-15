/**
 * This package provides classes to parse and generate the comma separated values format.
 * 
 * The most simple API is to use {@link diergo.csv.CommaSeparatedValues}:
 * 
 * <pre>
 * Reader in = new InputStreamReader(new FileInputStream("file.csv"), "UTF-8"));
 * for (String[] line : CommaSeparatedValues.parse(in)) {
 *   ...
 * }</pre>
 * 
 * @see <a href="http://tools.ietf.org/html/rfc4180">RFC 4180</a>
 */
package diergo.csv;