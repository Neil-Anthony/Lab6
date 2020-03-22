/*
Neil Miller
Professor Darrell Criss
CS 145 Winter 2020
Purpose:    To construct a binary tree which will play a version of the game 20 questions
            which will learn a new question from the user when it fails to guess correctly.
 */

import java.io.PrintStream;
import java.util.Scanner;


public class QuestionTree {
    private int totalGames = 0;
    private int gamesWon = 0;
    private QTNode root;
    private UserInterface uI;

    /*
    Constructor:
    Initializes new QuestionTree with one node containing "A: Computer"
    Initializes the User Interface.
     */
    public QuestionTree(UserInterface ui){
        root = new QTNode("A: Computer", null, null);
        this.uI = ui;
    }

    /*
    Runs a round of the game.
    Start at the root.
    While the current node has children:
        print node.data (a question)
        Traverse left or right determined by the user.
    Once we reach a leaf (a node with no children):
        Print data (An answer).
        Ask if it was right or wrong, if wrong get a new question from the user, if right win.
     */

    public void play(){
        QTNode current = root;
        while(current.left != null && current.right != null){
            uI.println(current.data); // Ask question
            if(uI.nextBoolean()){
                current = current.left;
            }else{
                current = current.right;
            }
        }
        System.out.println(current.data);

        if (uI.nextBoolean()){
            System.out.println("I win");
            totalGames++;
            gamesWon++;
        }else {
            learnedNode(current);
        }
    }

    private void learnedNode(QTNode current){
        String question = "Q: ";
        String theirAnswer = "A: ";
        String placeholder = current.data;

        System.out.println("I lose. What is your object?");
        theirAnswer += uI.nextLine();
        System.out.println("Type a yes/no question to distinguish your item from computer:");
        question += uI.nextLine();
        System.out.println("And what is the answer for your object?");

        if (uI.nextBoolean()) {
            current.left = new QTNode(theirAnswer);
            current.right = new QTNode(placeholder);
        }
        else {
            current.right = new QTNode(theirAnswer);
            current.left = new QTNode(placeholder);
        }

        current.data = question;
        totalGames++;
    }

    /*
    Runs the preorder traversal of the binary tree.
     */
    public void save(PrintStream output){
        preOrder(root, output);
    }

    /*
    Preorder traversal of a binary tree.
     */
    private void preOrder(QTNode root, PrintStream output){
        if (root == null){
            return;
        }
        output.println(root.data);
        preOrder(root.left, output);
        preOrder(root.right, output);
    }

    /*
    Creates a binary tree from a preorder list.
     */
    public void load(Scanner input) {
        while (input.hasNextLine()){
            root = loadNode(input);
        }
    }

    /*
    Make a new QTNode with the next line in the text document.
    If it is a question call loadNode() left, then call loadNode() right, then return the node.
     */
    private QTNode loadNode(Scanner input){
            String data = input.nextLine();
            QTNode nextNode = new QTNode(data);

            if (data.charAt(0) == 'Q') {
                nextNode.left = loadNode(input);
                nextNode.right = loadNode(input);
            }
            return nextNode;
    }

    public int totalGames(){ return totalGames;}

    public int gamesWon(){ return gamesWon;}
}
