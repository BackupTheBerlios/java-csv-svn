package diergo.csv;

import java.io.IOException;
import java.util.Map;

import diergo.array.ArrayWriter;

public class Cut
        implements Command
{

    public void process(CommaSeparatedValuesReader in, ArrayWriter<String> out, Map<String, String> options)
        throws IOException
    {
        String fields = options.get("fields");
        if (fields == null) {
            fields = "1";
        }
        out = createCutter(out, fields);
        for (String[] line : in) {
            out.write(line);
        }
    }

    private static ArrayWriter<String> createCutter(ArrayWriter<String> out, String option)
    {
        String[] args = option.split(",");
        int[] fields = new int[args.length];
        int i = 0;
        for (String arg : args) {
            fields[i++] = Integer.parseInt(arg) - 1;
        }
        return StringArrayCutter.cut(out, fields);
    }
}
