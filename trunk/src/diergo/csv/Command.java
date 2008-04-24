package diergo.csv;

import java.io.IOException;
import java.util.Map;

import diergo.array.ArrayWriter;

public interface Command
{
    public void process(CommaSeparatedValuesReader in, ArrayWriter<String> out, Map<String, String> options)
        throws IOException;
}
