package com.merkle.tree;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author barala
 *
 */
public class MerkleTreeApp 
{
    public static void main( String[] args )
    {
    	MessageDigest md = null;
		try 
		{
			md = MessageDigest.getInstance("SHA");
		} 
		catch (NoSuchAlgorithmException e) 
		{
			// Should never happen, we specified SHA, a valid algorithm
			assert false;
		}

		// Create some data blocks to be assigned to leaf nodes
		final byte[] block1 = {(byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04};
		final byte[] block2 = {(byte) 0xae, (byte) 0x45, (byte) 0x98, (byte) 0xff};
		final byte[] block3 = {(byte) 0x5f, (byte) 0xd3, (byte) 0xcc, (byte) 0xe1};
		final byte[] block4 = {(byte) 0xcb, (byte) 0xbc, (byte) 0xc4, (byte) 0xe2};
		final byte[] block5 = {(byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04};
		final byte[] block6 = {(byte) 0xae, (byte) 0x45, (byte) 0x98, (byte) 0xff};
		final byte[] block7 = {(byte) 0x5f, (byte) 0xd3, (byte) 0xcc, (byte) 0xe1};
		final byte[] block8 = {(byte) 0xcb, (byte) 0xbc, (byte) 0xc4, (byte) 0xe2};
		
		// Create leaf nodes containing these blocks
		final List<byte[]> blocks1and2 = new ArrayList<byte[]>();
		blocks1and2.add(block1);
		blocks1and2.add(block2);
		
		final List<byte[]> blocks3and4 = new ArrayList<byte[]>();
		blocks3and4.add(block3);
		blocks3and4.add(block4);
		
		final List<byte[]> blocks5and6 = new ArrayList<byte[]>();
		blocks5and6.add(block5);
		blocks5and6.add(block6);
		
		final List<byte[]> blocks7and8 = new ArrayList<byte[]>();
		blocks7and8.add(block7);
		blocks7and8.add(block8);
		
		final LeafNode leaf1 = new LeafNode(blocks1and2);
		final LeafNode leaf2 = new LeafNode(blocks3and4);
		final LeafNode leaf3 = new LeafNode(blocks5and6);
		final LeafNode leaf4 = new LeafNode(blocks7and8);
		
		// Build up the Merkle Tree from the leaves
		final MerkleTree branch1 = new MerkleTree(md);
		branch1.add(leaf1, leaf2);
		
		final MerkleTree branch2 = new MerkleTree(md);
		branch2.add(leaf3, leaf4);
		
		final MerkleTree merkleTree = new MerkleTree(md);
		merkleTree.add(branch1, branch2);
		
		// Return the digest for the entire tree
		merkleTree.prettyPrint();
    }
}
