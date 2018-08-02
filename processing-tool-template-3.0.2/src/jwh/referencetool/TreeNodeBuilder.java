package jwh.referencetool;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.*;
import java.io.*;

/**
 * This class uses an implementation of Oliver Watkins' TreeNodeBuilder.
 * 
 * Tree widget which allows the tree to be filtered on keystroke time. Only nodes who's
 * toString matches the search field will remain in the tree or its parents.
 *
 * Copyright (c) Oliver.Watkins
 */

/**
 * Class that prunes off all leaves which do not match the search string.
 *
 * @author Oliver.Watkins
 */

public class TreeNodeBuilder {
	private String textToMatch;
	HashSet<String> header_subheaderNames;
	HashMap<String, String> savedHTML;
	HashMap<String, String> searchAllDescriptions;
	HashMap<String, String> searchAllExamples;
	
	public TreeNodeBuilder(String textToMatch, HashMap<String, String> savedHTML, HashSet<String> header_subheaderNames, HashMap<String, String> searchAllExamples, HashMap<String, String> searchAllDescriptions) {
		this.savedHTML = savedHTML;
		this.header_subheaderNames = header_subheaderNames;
		this.textToMatch = textToMatch;
		this.searchAllExamples = searchAllExamples;
		this.searchAllDescriptions = searchAllDescriptions;
	}
	
	public DefaultMutableTreeNode prune(DefaultMutableTreeNode root, boolean searchAllSelected) {
		boolean badLeaves = true;
		
		while(badLeaves) {
			badLeaves = removeBadLeaves(root, searchAllSelected);
		}
		
		return root;
	}
	
	private boolean removeBadLeaves(DefaultMutableTreeNode root, boolean searchAllSelected) {
		boolean badLeaves = false;
		NodeNameGenerator gen = new NodeNameGenerator(header_subheaderNames);
		
		DefaultMutableTreeNode leaf = root.getFirstLeaf();
		
		if(leaf.isRoot())
			return false;
		
		int leafCount = root.getLeafCount();
		if(searchAllSelected) {
			for(int i = 0; i < leafCount; i++) {
				DefaultMutableTreeNode nextLeaf = leaf.getNextLeaf();
				DefaultMutableTreeNode parent = (DefaultMutableTreeNode) leaf.getParent();
				
				String nodeName = gen.generator(leaf);
				String code = "";
				String description = "";
				
				if(searchAllExamples.containsKey(nodeName) || searchAllDescriptions.containsKey(nodeName)) {
					if(nodeName.equals("FloatDict")) {
						System.out.println("yippie ki ye");
					}
					code = searchAllExamples.get(nodeName).toLowerCase();
					description = searchAllDescriptions.get(nodeName).toLowerCase();

					if(!code.contains(textToMatch.toLowerCase()) && !description.contains(textToMatch.toLowerCase())) {

						
						if(!leaf.toString().toLowerCase().startsWith(textToMatch.toLowerCase())) {
							if(nodeName.equals("FloatDict")) {
								System.out.println("yippie ki ye2");
							}
						} else if(!code.contains(textToMatch.toLowerCase())) {
							if(nodeName.equals("FloatDict")) {
								System.out.println("yippie ki ye3");
							}
							
						} else if(!description.contains(textToMatch.toLowerCase())) {
							if(nodeName.equals("FloatDict")) {
								System.out.println("yippie ki ye4");
							}
						}
						
						if(parent != null) {
							parent.remove(leaf);
						}
					
						badLeaves = true;
					}
					
				} else {
					if(!savedHTML.containsKey(nodeName)) {
						if(parent!= null) {
							parent.remove(leaf);
						}
						
						badLeaves = true;
					}
				}
				
				leaf = nextLeaf;
			}

		} else {
			for(int i = 0; i < leafCount; i++) {
				DefaultMutableTreeNode nextLeaf = leaf.getNextLeaf();
				DefaultMutableTreeNode parent = (DefaultMutableTreeNode) leaf.getParent();

				String parentstring = leaf.getParent().toString().toLowerCase();
				String gparentstring = "";
				String rootstring = root.toString().toLowerCase();
				if(leaf.getParent().getParent() != null) {
					gparentstring = leaf.getParent().getParent().toString().toLowerCase();
				}
				

				if(!leaf.getUserObject().toString().startsWith(textToMatch) 
						&& !leaf.getUserObject().toString().startsWith(textToMatch.toLowerCase())
						&& !leaf.getUserObject().toString().toLowerCase().contains(textToMatch.toLowerCase()) 
						&& !parentstring.contains(textToMatch.toLowerCase())
						&& !gparentstring.contains(textToMatch.toLowerCase())){
					
					
					if(parent != null) {
						parent.remove(leaf);
					}
					
					badLeaves = true;
				} else {
					String nodeName = gen.generator(leaf);
					
					if(!savedHTML.containsKey(nodeName)) {
						if(parent!= null) {
							parent.remove(leaf);
						}
						
						badLeaves = true;
					}
				}
				
				leaf = nextLeaf;
			}
		}

		return badLeaves;
	}
}