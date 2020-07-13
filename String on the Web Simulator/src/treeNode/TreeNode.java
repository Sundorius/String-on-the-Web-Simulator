package treeNode;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class represents a node of the tree. It stores a letter, the frequency
 * of the letter the files the letter is shown in, the child, next sibling and
 * parent of the node.
 * 
 * @author Sundorius
 */
public class TreeNode
{
    /* Private Data. */
    private final char letter;   /* The letter must not change, so it is declared 'final'. */
    private int frequency = 0;   /* If this letter is the end of a word, it shows how many times the WORD is shown in the files. */
    private Map filesMap = null; /* The key is the name of the file and value is the number of the times the WORD is shown in that file.  */
    private TreeNode parent;    /* The parent of the node. */
    private TreeNode nextSibling;
    private TreeNode child;

    
    /**
     * Creates a new tree node with the letter specified.
     * 
     * @param letter The letter to add to the node.
     */
    public TreeNode(char letter)
    {
        this.letter = letter;
        this.filesMap = new HashMap();
        this.child = null;
        this.parent = null;
        this.nextSibling = null;
    }

    
    /**
     * If the filesMap contains the file, then increment the value by 1
     *	else include the file in the filesMap and set the value to 1.
     * 
     * @param file The file.
     */
    public void updateFilesMap(String file)
    {
        this.frequency++;
        if (this.filesMap.containsKey(file))
        {
            this.filesMap.put(file, (int) filesMap.get(file) + 1);
            return;
        }
        this.filesMap.put(file, 1);
    }


    
    /**
     * Sorting algorithm for returning in ascending order the values of a Map. 
     * Declared private so it can not be accessible outside the class.
     * 
     * @param <K>
     * @param <V>
     * @param map The map to sort.
     * @return The sorted map.
     */
    private <K, V extends Comparable<? super V>> Map<K, V> ascendingSortByValue(Map<K, V> map)
    {
        return map.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e2, e1) -> e1, LinkedHashMap::new));
    }

    
    /**
     * Sets the child of the node.
     * 
     * @param child The child of the node.
     */
    public void setChild(TreeNode child)
    {
        this.child = child;
    }
    
    
    /**
     * Sets the parent of the node.
     * 
     * @param parent The parent of the node.
     */
    public void setParent(TreeNode parent)
    {
        this.parent = parent;
    }
    
    
    /**
     * Sets the sibling of the node.
     * 
     * @param sibling The sibling of the node.
     */
    public void setNextSibling(TreeNode sibling)
    {
        this.nextSibling = sibling;
    }

    
    /**
     * Returns the frequency of the file(number of occurrences).
     * @return The frequency of the file.
     */
    public int getFrequency()
    {
        return this.frequency;
    }
    
    
    /**
     * Returns the letter of the node.
     * 
     * @return The letter of the node.
     */
    public char getLetter()
    {
        return this.letter;
    }
    
    
    /**
     * Returns the Map with the files in ascending order.
     * 
     * @return The map with the files. 
     */
    public Map getFilesMap()
    {
        return this.ascendingSortByValue(filesMap);
    }
    
    
    /**
     * Returns the child of the node.
     * 
     * @return The child of the node.
     */
    public TreeNode getChild()
    {
        return this.child;
    }
    
    
    /**
     * Returns the parent of the node.
     * 
     * @return The parent of the node.
     */
    public TreeNode getParent()
    {
        return this.parent;
    }
    
    
    /**
     * Returns the next sibling of the node.
     * 
     * @return The next sibling of the node.
     */
    public TreeNode getNextSibling()
    {
        return this.nextSibling;
    }
}
