package diergo.array.mapped;

import java.util.Iterator;

/**
 * The original class has been renamed, use {@link MappingIterator} now.
 *
 * @deprecated Use the super class instead
 */
@Deprecated
public class MappedArrayIterator<E> extends MappingIterator<E>
{

	public MappedArrayIterator(String[] header, Iterator<E[]> iterator)
	{
		super(header, iterator);
	}

}
