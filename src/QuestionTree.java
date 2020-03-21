import java.io.PrintStream;
import java.util.Scanner;


public class QuestionTree {
    private int totalGames = 0;
    private int gamesWon = 0;
    private QTNode root;
    private UserInterface uI;


    /*
    In this constructor you should initialize your new question tree. You are passed an object representing
    the user interface for input/output. Your tree will use this user interface for printing output messages
    and asking questions in the game (see next page). Initially the tree starts out containing only a single
    answer leaf node with the word "computer" in it. The tree will grow larger as games are played or as a
    new tree is loaded with the load method described below.
     */
    public QuestionTree(UserInterface ui){

        root = new QTNode("A: Computer", null, null);
        this.uI = ui;

    }

    /*
    A call to this method should play one complete guessing game with the user, asking yes/no questions until reaching
    an answer object to guess. A game begins with the root node of the tree and ends upon reaching an answer leaf node.
    If the computer wins the game, print a message saying so. Otherwise your tree must ask the user what object he/she
    was thinking of, a question to distinguish that object from the player's guess, and whether the player's object is
    the yes or no answer for that question. The two boxed partial logs on the first page are examples of output from
    single calls to play. All user input/output should be done through the UserInterface object passed to your tree's
    constructor.
    After the game is over, the provided client program will prompt the user whether or not to play again; this is not
    part of your play method. Leave this functionality to the client program.
     */

    public void play(){
        String question = "Q: ";
        String answer = "A: ";
        String theirAnswer = "A: ";
        Boolean left = true;
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
            System.out.println("I lose. What is your object?");
            theirAnswer += uI.nextLine();
            System.out.println("Type a yes/no question to distinguish your item from computer:");
            question += uI.nextLine();
            System.out.println("And what is the answer for your object?");

            String placeholder = current.data;
            if (uI.nextBoolean()) {
                current.left = new QTNode(theirAnswer);
                current.right = new QTNode(placeholder);
            } else {
                current.right = new QTNode(theirAnswer);
                current.left = new QTNode(placeholder);
            }
            current.data = question;
            totalGames++;
        }
    }

    /*
    In this method you should store the current tree state to an output file represented by the given PrintStream. In
    this way your question tree can grow each time the user runs the program. (You don't save the number of games
    played/won.) A tree is specified by a sequence of lines, one for each node. Each line must start with either Q: to
    indicate a question node or A: to indicate an answer (a leaf). All characters after these first two should
    represent the text for that node (the question or answer). The nodes should appear in the order produced by a
    preorder traversal of the tree. For example, the two trees shown in the diagram and logs on the preceding pages
    would be represented by the following contents:
     */
    public void save(PrintStream output){
        preOrder(root, output);
    }

    private void preOrder(QTNode root, PrintStream output){
        if (root == null){
            return;
        }
        output.println(root.data);
        preOrder(root.left, output);
        preOrder(root.right, output);
    }

    /*
    In this method you should replace the current tree by reading another tree from a file. Your method will be passed
    a Scanner that reads from a file and should replace the current tree nodes with a new tree using the information in
    the file. Assume the file exists and is in proper standard format. Read entire lines of input using calls on
    Scanner's nextLine. (You don't load the number of games played/won, just the tree. Calling this method doesn't
    change games played/won.)
     */
    public void load(Scanner input) {
        while (input.hasNextLine()){
            root = loadNode(input);
        }
    }

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

    public int gamesWon(){
        return gamesWon;
    }
}
