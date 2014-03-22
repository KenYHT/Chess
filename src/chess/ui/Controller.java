package chess.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import chess.Game;
import chess.pieces.Piece;

/**
 * A Controller class that handles user interaction between the player and the game.
 * @author Ken Tian, ytian13@illinois.edu
 */

public class Controller {
	public Game game;
	private View view;
	private boolean pieceSelected; // flag that determines if there's a piece that's already been selected
	private Piece actionPiece; // the currently selected piece, may be null
	
	/**
	 * The constructor for the Controller class. It sets up the model and the view, then
	 * initializes all the ActionListeners for the view. It starts the game loop at the end.
	 */
	public Controller() {
		view = new View(this);
		game = new Game();
		pieceSelected = false;
		actionPiece = null;
		initMenuListeners();
		initSquareListeners();
		initPlayer1Forfeit();
		initPlayer2Forfeit();
		initRestart();
		initUndo();
	}
	
	/**
	 * This initializes and creates an ActionListener for the JMenuItems in the view. When
	 * "New Game" is clicked, the board will reset to its default state, and a new chess game
	 * will start. When "Exit" is clicked, the program terminates, and the window will close
	 * upon termination.
	 */
	public void initMenuListeners() {
		view.addNewGameListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				view.newGamePrompt();
				restartGame();
			}
		});
		
		view.addExitListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
	}
	
	/**
	 * This initializes and creates an ActionListener for the Squares in the view. The first
	 * time a Square is clicked, that Square becomes selected, and if that Square contains a
	 * piece owned by the player that owns the current turn, then the game will attempt to move
	 * that piece to the new location given by the second click.
	 */
	public void initSquareListeners() {
		view.addSquareListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Square square = (Square)event.getSource();
				Piece selectedPiece = game.board.getPiece(square.x, square.y);
				if (!pieceSelected && selectedPiece != null) { // a piece hasn't been selected yet
						actionPiece = selectedPiece;
						pieceSelected = true;
				} else { // a piece has already been selected
					// try to move the piece to the selected location
					if (actionPiece != null && game.playerMove(actionPiece, square.x, square.y)) {
						view.noWarning();
						if (game.checkCheckmate(game.getPlayerColor(game.getTurn()))) {
							if (game.getTurn() == game.PLAYER1)
								game.setPlayer1Win(true);
							else if (game.getTurn() == game.PLAYER2)
								game.setPlayer2Win(true);
						}
					} else
						view.invalidMoveWarning();
						
					pieceSelected = false;
					view.drawPieces(game.board);
				}
				
				if (game.getInCheck())
					view.checkWarning();
				
				if (game.getPlayer1Win()) {
					view.player1WinMessage();
					game.setPlayer1Win(false);
					view.incrementPlayer1Score();
					restartGame();
				}
				
				if (game.getPlayer2Win()) {
					view.player2WinMessage();
					game.setPlayer2Win(false);
					view.incrementPlayer2Score();
					restartGame();
				}
			}
		});
	}
	
	/**
	 * This initializes the ActionListener for the "Player 1 Forfeit" JButton. If the button
	 * is clicked, then player 2's score increases by 1 and a new game is started.
	 */
	public void initPlayer1Forfeit() {
		view.addPlayer1ForfeitListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				view.incrementPlayer2Score();
				restartGame();
			}
		});
	}
	
	/**
	 * This initializes the ActionListener for the "Player 2 Forfeit" JButton. If the button
	 * is clicked, then player 1's score increases by 1 and a new game is started.
	 */
	public void initPlayer2Forfeit() {
		view.addPlayer2ForfeitListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				view.incrementPlayer1Score();
				restartGame();
			}
		});
	}
	
	/**
	 * This initializes the ActionListener for the "Restart" JButton. If the button is clicked,
	 * then both players are asked if they want to restart. If both players agree, then the
	 * game is restarted and no points are added. Otherwise, the game continues.
	 */
	public void initRestart() {
		view.addRestartListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (view.promptPlayersRestart()) {
					restartGame();
				}
			}
		});
	}
	
	public void initUndo() {
		view.addUndoListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				game.undo();
				game.board.updateBoard(game);
				view.drawPieces(game.board);
			}
		});
	}
	
	/**
	 * Helper function that restarts the game and redraws the board.
	 */
	private void restartGame() {
		game.resetGame();
		view.noWarning();
		view.drawPieces(game.board);
	}

	// running the game
	public static void main(String args[]) {
		new Controller();
	}
}
