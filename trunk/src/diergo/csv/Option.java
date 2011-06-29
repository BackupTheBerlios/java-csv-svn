package diergo.csv;



/**
 * Options for CSV parser and generator.
 * @since 2.0
 * @see CommaSeparatedValuesGenerator
 * @see CommaSeparatedValuesParser
 */
public enum Option
{
  TRIM,
  COMMENTED_HEADER,
  COMMENTS_SKIPPED,
  EMPTY_AS_NULL;
}