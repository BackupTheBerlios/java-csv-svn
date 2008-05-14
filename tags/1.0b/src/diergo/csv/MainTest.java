package diergo.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import diergo.array.ArrayWriter;

public class MainTest
{
    private Map<String, String> _options;
    private ByteArrayOutputStream _systemOut;
    private ByteBuffer _systemIn;

    @Before
    public void registerTestCommand()
    {
        Main.COMMANDS.put("test", new TestCommand());
    }

    @Before
    public void mockSystemOut()
    {
        _systemOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(_systemOut));
    }

    @Before
    public void mockSystemIn()
    {
        _systemIn = ByteBuffer.allocate(1000);
        System.setIn(new InputStream()
        {

            public int read()
            {
                if (_systemIn.hasRemaining()) {
                    return _systemIn.get();
                }
                return -1;
            }

        });
    }

    @Test
    public void defaultOptionsArePassed()
    {
        Main.main(new String[] { "test" });
        assertEquals(",", _options.get("separator"));
        assertEquals(System.getProperty("file.encoding"), _options.get("encoding"));
    }

    @Test
    public void zeroArgumentsUsesStdinAndStdout()
        throws IOException
    {
        String in = "1a,1b\n2a,2b";
        setStdIn(in, "UTF-8");
        Main.main(new String[] { "test" });
        assertEquals(in, new String(_systemOut.toByteArray(), "UTF-8"));
    }

    @Test
    public void optionsAreParsedAndRemovedFromArguments()
        throws IOException
    {
        String in = "1a,1b\n2a,2b";
        File testFile = createInputFile(in);
        Main.main(new String[] { "test", "-foo=bar", testFile.getAbsolutePath() });
        assertEquals("bar", _options.get("foo"));
        assertEquals(in, new String(_systemOut.toByteArray(), "UTF-8"));
    }

    @Test
    public void oneArgumentUsesStdout()
        throws IOException
    {
        String in = "1a,1b\n2a,2b";
        File testFile = createInputFile(in);
        Main.main(new String[] { "test", testFile.getAbsolutePath() });
        assertEquals(in, new String(_systemOut.toByteArray(), "UTF-8"));
    }

    @Test
    public void twoArgumentsAreInAndOut()
        throws IOException
    {
        String in = "1a,1b\n2a,2b";
        File testFile1 = createInputFile(in);
        File testFile2 = File.createTempFile(getClass().getName(), ".csv");
        testFile2.deleteOnExit();
        Main.main(new String[] { "test", testFile1.getAbsolutePath(), testFile2.getAbsolutePath() });
        assertTrue(testFile2.exists());
        CharBuffer buffer = CharBuffer.allocate(1000);
        buffer.limit(new FileReader(testFile2).read(buffer));
        buffer.rewind();
        assertEquals(in, buffer.toString());
    }
    
    @Test
    public void encodingIsUsed()
    	throws IOException
    {
        String in = "ä,ö,ü,ß";
        setStdIn(in, "UTF-8");
        Main.main(new String[] { "test", "-encoding=UTF-8" });
        assertEquals(in, new String(_systemOut.toByteArray(), "UTF-8"));
        mockSystemIn();
        mockSystemOut();
        setStdIn(in, "ISO-8859-1");
        Main.main(new String[] { "test", "-encoding=ISO-8859-1" });
        assertEquals(in, new String(_systemOut.toByteArray(), "ISO-8859-1"));
    }

    private File createInputFile(String content)
        throws IOException
    {
        File testFile = File.createTempFile(getClass().getName(), ".csv");
        testFile.deleteOnExit();
        FileWriter writer = new FileWriter(testFile);
        writer.write(content);
        writer.close();
        return testFile;
    }

    private void setStdIn(String in, String encoding)
        throws UnsupportedEncodingException
    {
		byte[] bytes = in.getBytes(encoding);
        _systemIn.put(bytes);
        _systemIn.rewind();
        _systemIn.limit(bytes.length);
    }

    private class TestCommand
            implements Command
    {

        public void process(CommaSeparatedValuesReader in, ArrayWriter<String> out, Map<String, String> options)
            throws IOException
        {
            MainTest.this._options = options;
            for (String[] line : in) {
                out.write(line);
            }
        }

    }
}
