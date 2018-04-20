package com.merkle.tree;

import java.util.List;

/**
 * represents leaf node in the merkle tree
 * 
 * @author barala
 *
 */
public class LeafNode
{
	private final List<byte[]> data;

	public LeafNode(final List<byte[]> data)
	{
		this.data = data;
	}

	public List<byte[]> getData()
	{
		return this.data;
	}

	private String toHexString(final byte[] array)
	{
		final StringBuilder str = new StringBuilder();
		
		str.append("[");
		
		boolean isFirst = true;
		for(int idx=0; idx<array.length; idx++)
		{
			final byte b = array[idx];
			
			if (isFirst)
			{			
				//str.append(Integer.toHexString(i));
				isFirst = false;
			}
			else
			{
				//str.append("," + Integer.toHexString(i));
				str.append(",");
			}
			
			final int hiVal = (b & 0xF0) >> 4;
	        final int loVal = b & 0x0F;
	        str.append((char) ('0' + (hiVal + (hiVal / 10 * 7))));
	        str.append((char) ('0' + (loVal + (loVal / 10 * 7))));
		}
		
		str.append("]");
		
		return(str.toString());
	}
	
	/**
	 * Returns a string representation of the data block
	 */
	public String toString()
	{
		final StringBuilder str = new StringBuilder();
		
		for(byte[] block: data)
		{
			str.append(toHexString(block));
		}
		
		return(str.toString());
	}
}
