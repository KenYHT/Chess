package chess.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import chess.Board;
import chess.pieces.Piece;

/**
 * A View class that displays the state of the board and allows the user to interact with
 * the game.
 * @author Ken Tian, ytian13@illinois.edu
 */

public class View {
	
	private Square squares[][]; // an array of squares for the board
	private JMenuBar menubar;
	private JMenu file;
	private JMenuItem exit;
	private JMenuItem newGame;
	private JLabel player1Label; // a text label for player 1's name and score
	private JLabel player2Label; // a text label for player 2's name and score
	private JButton player1Forfeit; // a button that lets player 1 forfeit the game
	private JButton player2Forfeit; // a button that lets player 2 forfeit the game
	private JButton restart; // a button that allows the game to be restarted if both players allow it
	private JButton undo; // a button that undoes the last move.
	private JPanel myPanel;
	private int player1Score;
	private int player2Score;
	private String player1Name;
	private String player2Name;
	private String currentTurn;
	
	/**
	 * The constructor for the View class. It sets up the GUI for a chess game and 
	 * initializes all the squares and pieces.
	 * @param game, the game model to use
	 */
    public View(Controller app){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e) {
            //silently ignore
        }
        
        player1Score = 0;
        player2Score = 0;
        player1Name = "Player 1";
        player2Name = "Player 2";
        JFrame window = new JFrame("wow such Chess");
        window.setSize(650, 650);
        window.setResizable(false);
        myPanel = initializePanel();
        initializeButtons(myPanel);
        setUpMenu(window);
        window.setContentPane(myPanel);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    /**
     * Initializes all the buttons by setting its colors to match a chess board, then it
     * adds every piece to the board.
     * @param myPanel
     */
    private void initializeButtons(JPanel myPanel) {
    	squares = new Square[8][8];
    	for (int row = 0; row < squares.length; row++) {
    		for (int col = 0; col < squares[0].length; col++) {
    			squares[col][row] = new Square(col, row);
    			if ((row + col) % 2 == 0) {
    				squares[col][row].setBackground(new Color(31, 31, 173));
    				squares[col][row].setForeground(Color.DARK_GRAY);
    			} else {
    				squares[col][row].setBackground(new Color(173, 173, 31));
    				squares[col][row].setForeground(Color.blue);
    			}
    				
    			squares[col][row].setBorder(new LineBorder(Color.blue));
    			squares[col][row].setFocusPainted(false);
    			squares[col][row].setContentAreaFilled(false);
    			squares[col][row].setBorderPainted(false);
    			squares[col][row].setOpaque(true);
    			myPanel.add(squares[col][row]);
    		}
    	}
    }
    
    /**
     * Returns the color of the piece as a String. If the piece or the color doesn't exist,
     * then this method returns null.
     * @param piece, the piece to check
     * @return a String representation of the color of the given Piece
     */
    private String parseColor(Piece piece) {
    	if (piece != null) {
	    	switch (piece.getColor()) {
	    		case WHITE:
	    			return "white";
	    		case BLACK:
	    			return "black";
	    		case BLUE:
	    			return "blue";
	    		case RED:
	    			return "red";
	    		case GREEN:
	    			return "green";
	    		case YELLOW:
	    			return "yellow";
	    		case PURPLE:
	    			return "purple";
	    		case ORANGE:
	    			return "orange";
	    	}
    	}
    	
    	return null;
    }
    
    /**
     * Returns the type of the piece as a String. If the piece or the type doesn't exist,
     * then this method returns null.
     * @param piece, the piece to check
     * @return a String representation of the type of the given Piece
     */
    private String parseType(Piece piece) {
    	if (piece != null) {
	    	switch (piece.getType()) {
	    	case PAWN:
	    		return "Pawn";
    		case ROOK:
    			return "Rook";
    		case KING:
    			return "King";
    		case KNIGHT:
    			return "Knight";
    		case QUEEN:
    			return "Queen";
    		case BISHOP:
    			return "Bishop";
    		case HIGHTEMPLAR:
    			return "Hightemplar";
    		case GHOST:
    			return "Ghost";
			default:
				break;
	    	}
    	}
    	
    	return null;
    }
 
    /**
     * Creates a new JPanel and initializes it.
     * @return the newly created JPanel
     */
    private JPanel initializePanel() {
        JPanel myPanel = new JPanel();
        myPanel.setPreferredSize(new Dimension(500,500));
        myPanel.setLayout(new GridLayout(8, 8));
        return myPanel;
    }
 
    /**
     * Creates a menu bar at the top of the JFrame.
     * @param window, the JFrame to add the menu bar to
     */
    private void setUpMenu(JFrame window) {
        menubar = new JMenuBar();
        menubar.setMargin(new Insets(2, 2, 2, 2));
        file = new JMenu("File");
        exit = new JMenuItem("Exit");
        newGame = new JMenuItem("New Game");
        player1Label = new JLabel();
        player2Label = new JLabel();
        player1Forfeit = new JButton(player1Name + " forfeit");
        player2Forfeit = new JButton(player2Name + " forfeit");
        restart = new JButton("Restart");
        undo = new JButton("Undo");
        file.add(newGame);
        file.add(exit);
        menubar.add(file);
        menubar.add(player1Forfeit);
        menubar.add(player2Forfeit);
        menubar.add(restart);
        menubar.add(undo);
        menubar.add(player1Label);
        menubar.add(player2Label);
        window.setJMenuBar(menubar);
    }
    
    /**
     * Draws the pieces in the View based on the state of the game board.
     */
    public void drawPieces(Board board) {
    	// iterate through the whole board
    	for (int col = 0; col < Board.GLOBAL_BOARD_SIDE_LENGTH; col++) {
    		for (int row = 0; row < Board.GLOBAL_BOARD_SIDE_LENGTH; row++) {
    			Piece currentPiece = board.getPiece(col, row); // get each piece
    			if (currentPiece != null) {
    				String pieceColor = parseColor(currentPiece); // get the piece color
    				String pieceType = parseType(currentPiece); // get the piece type
    				// set the piece icon on that square
    				try {
    					ImageIcon icon = new ImageIcon(getClass().getResource("/chess/images/"+ pieceColor + pieceType + ".png"));
    					squares[col][row].setIcon(icon);
    				} catch (Exception e) {
    					System.out.println("image doesn't exist");
    				}
    			} else {
    				squares[col][row].setIcon(null);
    			}
    		}
    	}
    }
    
    /**
     * Adds an action listener to all the Squares on the board.
     * @param a, the action listener determining the behavior of Square
     */
    public void addSquareListener(ActionListener a) {
    	for (int i = 0; i < squares.length; i++)
    		for (int j = 0; j < squares[0].length; j++)
    			squares[i][j].addActionListener(a);
    }
    
    /**
     * Adds an action listener to the "Player 1 Forfeit" JButton.
     * @param a, the action listener determining the behavior of the "Player 1 Forfeit" JButton
     */
    public void addPlayer1ForfeitListener(ActionListener a) {
    	player1Forfeit.addActionListener(a);
    }
    
    /**
     * Adds an action listener to the "Player 2 Forfeit" JButton.
     * @param a, the action listener determining the behavior of the "Player 2 Forfeit" JButton
     */
    public void addPlayer2ForfeitListener(ActionListener a) {
    	player2Forfeit.addActionListener(a);
    }
    
    public void addRestartListener(ActionListener a) {
    	restart.addActionListener(a);
    }
    
    /**
     * Adds and action listener to the "Undo" JButton.
     * @param a, the action listener determining the behavior of the "Undo" JButton
     */
    public void addUndoListener(ActionListener a) {
    	undo.addActionListener(a);
    }
    
    /**
     * Adds an action listener to the "New Game" JMenuItem.
     * @param a, the action listener determining the behavior of the "New Game" JMenuItem
     */
    public void addNewGameListener(ActionListener a) {
    	newGame.addActionListener(a);
    }
    
    /**
     * Adds an action listener to the "Exit" JMenuItem.
     * @param a, the action listener determining the behavior of the "Exit" JMenuItem
     */
    public void addExitListener(ActionListener a) {
    	exit.addActionListener(a);
    }
    
    /**
     * Warns the player if they're making an invalid move by creating a yellow border
     * around the screen.
     */
    public void invalidMoveWarning() {
    	myPanel.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
    }
    
    /**
     * Warns the player if they're in check by creating a orange border around the screen.
     */
    public void checkWarning() {
    	myPanel.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 3));
    }
    
    /**
     * Removes any border from the screen, showing that there's no warning messages.
     */
    public void noWarning() {
    	myPanel.setBorder(null);
    }
    
    /**
     * Increments Player 1's score by 1.
     */
    public void incrementPlayer1Score() {
    	player1Score++;
    	player1Label.setText(player1Name + ": " + player1Score + "\t");
    }
    
    /**
     * Increments Player 2's score by 1.
     */
    public void incrementPlayer2Score() {
    	player2Score++;
    	player2Label.setText(player2Name + ": " + player2Score + "\t");
    }
    
    /**
     * Prompts both players to see if they wish to restart the game.
     * @return true if both players say "yes", otherwise false
     */
    public boolean promptPlayersRestart() {
    	int player1Answer = JOptionPane.showConfirmDialog(null, player1Name + ", do you agree to restart the game?", 
    													  null, JOptionPane.YES_NO_OPTION);
    	int player2Answer = JOptionPane.showConfirmDialog(null, player2Name + ", do you agree to restart the game?", 
				  										  null, JOptionPane.YES_NO_OPTION);
    	return player1Answer == JOptionPane.YES_OPTION && player2Answer == JOptionPane.YES_OPTION;
    }
    
    /**
     * Displays a dialogue stating that Player 1 has won.
     */
    public void player1WinMessage() {
    	JOptionPane.showMessageDialog(null, player1Name + " has put " + player2Name + " into checkmate!");
    }
    
    /**
     * Displays a dialogue stating that Player 2 has won.
     */
    public void player2WinMessage() {
    	JOptionPane.showMessageDialog(null, player2Name + " has put " + player1Name + " into checkmate!");
    }
    
    /**
     * Resets the scores to 0, and asks for the players to set their names. The names will default
     * to "Player 1" and "Player 2" respectively if no name is given.
     */
    public void newGamePrompt() {
    	player1Score = 0;
    	player2Score = 0;
    	player1Name = JOptionPane.showInputDialog("Please enter Player 1's name.");
        player2Name = JOptionPane.showInputDialog("Please enter Player 2's name.");
        if (player1Name == null || player1Name.equals(""))
        	player1Name = "Player 1";
        
        if (player2Name == null || player2Name.equals(""))
        	player2Name = "Player 2";
        
        player1Label.setText(player1Name + ": " + player1Score + "\t");
        player2Label.setText(player2Name + ": " + player2Score + "\t");
        player1Forfeit.setText(player1Name + " forfeit");
        player2Forfeit.setText(player2Name + " forfeit");
    }
}