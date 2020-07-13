package main;

import tree.Tree;
import treeNode.TreeNode;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * The class that creates the user interface and starts the whole program.
.
 * @author Sundorius
 *
 */

public class Main
{
    /**
     * The main class.
     * 
     * @param args
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException
    {
        Tree tree = new Tree();

        JFrame frame = new JFrame("Strings on the web simulator");
        JPanel panel = new JPanel();
        JButton openFileBtn = new JButton("Open File");
        JButton searchBtn = new JButton("Search Words");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 100);
        frame.setLocationRelativeTo(null); /* Place it at the center of the screen. */
        frame.setResizable(false);
        frame.add(panel);
        panel.add(openFileBtn);
        panel.add(searchBtn);
        frame.setVisible(true);

        /* It opens a frame to choose a file. */
        openFileBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                frame.setVisible(false); /* Hide the menu frame. */
                
                BufferedReader reader = null;
                try
                {
                    String line = null;
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.showOpenDialog(null);
                    File file = fileChooser.getSelectedFile();
                    String filename = file.getName();
                    reader = new BufferedReader(new FileReader(file));
                    while ((line = reader.readLine()) != null) /* Read line by line. */
                    {
                        String[] words = line.toUpperCase().split(",|\\.|\\s"); /* Break the words of the line into an array of strings. */
                        int i = 0;
                        TreeNode newLetterNode;
                        while (i < words.length)
                        {
                            newLetterNode = tree.getTreeRoot();
                            int j = 0;
                            while (j < words[i].length()) /* Iterate each word by character. */
                            {
                                newLetterNode = tree.CheckorAdd(words[i].charAt(j), newLetterNode);
                                if (j == words[i].length() - 1)
                                {
                                    if(newLetterNode!=null) newLetterNode.updateFilesMap(filename);
                                    tree.incrementWordsCounter();
                                }
                                j++;
                            }
                            i++;
                        }
                    }
                    System.out.println("\n====================================================================\n"
                                        + "Printing all the words of the tree in ascending alphabetical order.\n"
                                        + "====================================================================");
                    tree.RecursivePrint(tree.getTreeRoot().getChild(), new String()); /* Print all the words of the tree. */
                    System.out.println("====================================================================");
                }
                catch (FileNotFoundException ex)
                {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                catch (IOException ex)
                {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                finally
                {
                    try
                    {
                        reader.close();
                    }
                    catch (IOException ex)
                    {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                frame.setVisible(true); /* Make the menu frame visible again. */
            }
        });

        /* User types words and program prints the files they belong to, only if words are found,
            else it prints that the word is not found. */
        searchBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                frame.setVisible(false); /* Hide the menu frame. */
                
                /* If there are no words in the tree, pops a frame with an error message. */
                if(tree.getTreeRoot().getChild() == null)
                {
                    JFrame errorFrame = new JFrame("Error: Empty Tree");
                    JPanel errorPane = new JPanel();
                    JLabel errorLabel1 = new JLabel("There are no words in the tree!");
                    JLabel errorLabel2 = new JLabel("Please insert words from a file and try again!\n");
                    errorFrame.setSize(350, 150);
                    errorFrame.setLocationRelativeTo(null); /* Place it at the center of the screen. */
                    errorFrame.setResizable(false);
                    errorFrame.add(errorPane);
                    errorPane.add(errorLabel1);
                    errorPane.add(errorLabel2);
                    errorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    
                    frame.setVisible(true); /* Make the menu frame visible again. */
                    errorFrame.setVisible(true); /* We want the error frame to be placed in front of the menu frame. */
                    return;
                }
                
                JFrame searchFrame = new JFrame("Insert word/s");
                JPanel searchPanel = new JPanel();
                JTextField userText = new JTextField(20);
                JLabel label = new JLabel("Give input:");
                searchFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                searchFrame.setLocationRelativeTo(null); /* Place it at the center of the screen. */
                searchFrame.setSize(350, 150);
                searchFrame.setResizable(false);
                searchFrame.add(searchPanel);
                searchPanel.add(label);
                searchPanel.add(userText);
                searchFrame.setVisible(true);
                searchFrame.setFocusable(true);

                userText.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        String[] words = userText.getText().toUpperCase().split(",|\\.|\\s+");
                        System.out.println("\n");
                        for (int i = 0; i < words.length; i++)  tree.printTheFiles(words[i]);/* Iterate between words. */
                        searchFrame.dispose(); /* Remove searchFrame from the screen. */
                        
                        frame.setVisible(true); /* Make the menu frame visible again. */
                    }
                });
            }
        });
    }
}