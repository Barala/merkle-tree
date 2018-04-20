package com.merkle.tree;

import java.security.MessageDigest;
import java.util.List;

/**
 * represents binary merkle tree
 * Only leaf node will have the data
 * 
 * @author barala
 *
 */
public class MerkleTree
{
	//User can decide which hash to use 
	private final MessageDigest md;

	private MerkleTree leftMerkleTree = null;
	private MerkleTree rightMerkleTree = null;

	private LeafNode leftLeafNode = null;
	private LeafNode rightLeafNode = null;

	//hash for the node
	private byte[] digest;	

	/**
	 * specify messageDigest algo
	 * 
	 * @param md
	 */
	public MerkleTree(MessageDigest md)
	{
		this.md = md;
	}

	/**
	 * add directly trees
	 * 
	 * @param leftMerkleTree
	 * @param rightMerkleTree
	 */
	public void add(final MerkleTree leftMerkleTree, final MerkleTree rightMerkleTree)
	{
		this.leftMerkleTree = leftMerkleTree;
		this.rightMerkleTree = rightMerkleTree;

		md.update(leftMerkleTree.getDigest());
		this.digest = md.digest(rightMerkleTree.getDigest());
	}

	/**
	 * 
	 * @param leftLeafNode
	 * @param rightLeafNode
	 */
	public void add(final LeafNode leftLeafNode, final LeafNode rightLeafNode)
	{
		this.leftLeafNode = leftLeafNode;
		this.rightLeafNode = rightLeafNode;

		md.update(generateDigest(leftLeafNode));
		this.digest = md.digest(generateDigest(rightLeafNode));
	}

	public MerkleTree getLeftMerkleTree()
	{
		return this.leftMerkleTree;
	}

	public MerkleTree getRightMerkleTree()
	{
		return this.rightMerkleTree;
	}

	public LeafNode getLeftLeaf()
	{
		return this.leftLeafNode;
	}

	public LeafNode getRightLeaf()
	{
		return this.rightLeafNode;
	}

	private byte[] generateDigest(LeafNode leafNode)
	{
		List<byte[]> data = leafNode.getData();
		
		for(int i=0; i<data.size()-1;i++)
		{
			md.update(data.get(i));
		}
		return md.digest(data.get(data.size()-1));
	}

	private byte[] getDigest()
	{
		return this.digest;
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
	 * Private version of prettyPrint in which the number
	 * of spaces to indent the tree are specified
	 * 
	 * @param indent The number of spaces to indent
	 */
	private void prettyPrint(final int indent)
	{
		for(int idx=0; idx<indent; idx++)
		{
			System.out.print(" ");
		}
		
		// Print root digest
		System.out.println("Node digest: " + toHexString(getDigest()));
			
		// Print children on subsequent line, further indented
		if (rightLeafNode!=null && leftLeafNode!=null)
		{
			// Children are leaf nodes
			// Indent children an extra space
			for(int idx=0; idx<indent+1; idx++)
			{
				System.out.print(" ");
			}
			
			System.out.println("Left leaf: " + rightLeafNode.toString() +
					           " Right leaf: " + leftLeafNode.toString());
			
		}
		else if (rightMerkleTree!=null && leftMerkleTree!=null)
		{
			// Children are Merkle Trees
			// Indent children an extra space
			rightMerkleTree.prettyPrint(indent+1);
			leftMerkleTree.prettyPrint(indent+1);
		}
		else
		{
			// Tree is empty
			System.out.println("Empty tree");
		}
	}
	
	/**
	 * Formatted print out of the contents of the tree
	 */
	public void prettyPrint()
	{
		// Pretty print the tree, starting with zero indent
		prettyPrint(0);
	}
}