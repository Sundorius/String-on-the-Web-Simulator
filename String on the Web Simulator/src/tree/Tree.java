package tree;

import static java.lang.System.exit;
import java.util.Set;

import treeNode.TreeNode;


/**
 * Creates a tree with nodes that contain information about letters.
 * 
 * @author Sundorius
 *
 */
public class Tree
{
    /* Private Data. */
    private TreeNode treeRoot = null;
    private int wordsCounter = 0;

    
    /**
     * Creates an empty tree.
     */
    public Tree()
    {
        this.treeRoot = new TreeNode('0');
    }

    
    /**
     * Return the root of the tree.
     * @return The root of the tree.
     */
    public TreeNode getTreeRoot()
    {
        return this.treeRoot;
    }
    
    
    /**
     * Returns the number of the words in the tree.
     * @return The number of the words in the tree.
     */
    public int getWordsCounter()
    {
        return this.wordsCounter;
    }
    
    
    
    /**
     * Increments the number of the words of the tree.
     */
    public void incrementWordsCounter()
    {
        this.wordsCounter++;
    }


    /**
     * Traversing algorithm that traverses the tree and adds the new node(if needed)
     * in the correct position in the tree.
     * 
     * @param letter The letter to add.
     * @param node The node to start from.
     * @return TreeNode if successful, else null.
     */
    public TreeNode CheckorAdd(char letter, TreeNode node)
    {
        /* Runtime check. */
        if (node == null)
        {
            System.out.println("'node' that was given as argument in: Tree.CheckorAdd() IS NULL!");
            exit(1);
        }
        TreeNode searchNode = node.getChild();
        /* If Tree root has no children. */
        if (treeRoot.getChild() == null)
        {
            TreeNode newNode = new TreeNode(letter);
            treeRoot.setChild(newNode);
            newNode.setParent(treeRoot);
            this.wordsCounter++;
            return newNode;
        }
        /* If node has children. */
        TreeNode prevNode = null;
        while (searchNode != null)
        {
            if (letter == searchNode.getLetter())  return searchNode;
            else
            {
                if (letter > searchNode.getLetter())
                {
                    prevNode = searchNode;
                    searchNode = searchNode.getNextSibling();
                }
                else   break; /* If letter < searchNode.getLetter().  */
            }
        }
        if (prevNode != null)
        {
            TreeNode newNode = new TreeNode(letter);
            newNode.setParent(node);
            newNode.setNextSibling(prevNode.getNextSibling());
            prevNode.setNextSibling(newNode);
            return newNode;
        }
        else
        {
            if (prevNode == null) /* If newNode must go at the left of a first child. */
            {
                TreeNode newNode = new TreeNode(letter);
                newNode.setParent(node);
                node.setChild(newNode);
                newNode.setNextSibling(searchNode);
                return newNode;
            }
        }
        return null;
    }

    
    /**
     * It prints all the words of the tree in ascending alphabetical order,
     * it also prints the frequency and the probability  of each word.
     * 
     * @param node The starting node.
     * @param wordToForm The word.
     */
    public void RecursivePrint(TreeNode node, String wordToForm)
    {
        /* Runtime checks. */
        if (node == null)
        {
            System.out.println("'node' that was given as argument in: Tree.RecursivePrint() IS NULL!");
            exit(1);
        }
        if (wordToForm == null)
        {
            System.out.println("'wordToForm' that was given as argument in: Tree.RecursivePrint() IS NULL!");
            exit(1);
        }
        if (node.getChild() != null)
        {
            RecursivePrint(node.getChild(), wordToForm.concat(String.valueOf(node.getLetter())));
            if (node.getFrequency() != 0) /* If it is the last letter of the word. */
            {
                System.out.println("Word: " + wordToForm.concat(String.valueOf(node.getLetter())));
                System.out.println("  Frequency: " + node.getFrequency()); /* We want a new line here to separate the words. */
                System.out.println("  Probability: " + node.getFrequency() / this.wordsCounter); /* FOR SOME REASON IT PRINTS 0 ALL THE TIME!! */
                /* Set <String> keys=node.getFilesMap().keySet(); */
                /* System.out.println("  Files: "+keys.toString()+"\n"); */
            }
        }
        else
        {
            if (node.getFrequency() != 0) /* If it is the last letter of the word. */
            {
                System.out.println("Word: " + wordToForm.concat(String.valueOf(node.getLetter())));
                System.out.println("  Frequency: " + node.getFrequency()); /* We want a new line here to separate the words. */
                System.out.println("  Probability: " + node.getFrequency() / this.wordsCounter); /* FOR SOME REASON IT PRINTS 0 ALL THE TIME!! */
                /* Set <String> keys=node.getFilesMap().keySet(); */
                /* System.out.println("  Files: "+keys.toString()+"\n"); */
            }
        }
        if (node.getNextSibling() != null)  RecursivePrint(node.getNextSibling(), wordToForm);
    }


    /**
     * If word is found in the tree, then it prints the files that contain the specific word, else it prints a message.
     * 
     * @param word The word.
     */
    public void printTheFiles(String word)
    {
        TreeNode target = treeRoot.getChild();
        TreeNode prev = null;
        /* Runtime check. */
        if (target == null)
        {
            System.out.println("'treeRoot.child' is null in: Tree.printTheFiles()!");
            exit(1);
        }
        char[] letter = word.toCharArray();
        int breakFlag = 0;
        for (int i = 0; i < letter.length; i++)
        {
            while (target != null)
            {
                if (letter[i] == target.getLetter())
                {
                    breakFlag = 1;
                    break;
                }
                else
                {
                    prev = target;
                    target = target.getNextSibling();
                }
            }
            if (breakFlag == 1) /* If the letter is found in a node, then continue to the next letter. */
            {
                prev = target;
                target = target.getChild();
            }
            else
            {
                System.out.println("Word " + word + " not found!");
                return;
            }
            breakFlag = 0;
        }
        System.out.println("Word " + word + " found! ");
        Set<String> keys;
        
        if (prev == null)  keys = target.getFilesMap().keySet();
        else  keys = prev.getFilesMap().keySet();
        
        System.out.println("    Files, in ascending order, that contain the word " + word + ": " + keys.toString());
    }
}
