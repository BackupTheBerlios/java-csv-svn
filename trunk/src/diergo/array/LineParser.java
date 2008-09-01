package diergo.array;

public interface LineParser<E>
{
    public E[] parseLine(String line);

    public String generateLine(E... values);
}
