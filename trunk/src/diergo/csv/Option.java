package diergo.csv;



/**
 * Options for CSV parser and generator.
 * @since 2.0
 * @see CommaSeparatedValuesGenerator
 * @see CommaSeparatedValuesParser
 */
public enum Option
{
  /** trim values after read and before generated */TRIM,
  /** any first line starting with # is treated as header and read without the #, when generated, the first line is generated as comment*/COMMENTED_HEADER,
  /** commented lines (starting with #) are skipped on read */COMMENTS_SKIPPED,
  /** an empty value will read as null, null will be generated as empty */EMPTY_AS_NULL;
}