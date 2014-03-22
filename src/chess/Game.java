package chess;

import java.awt.Point;

import chess.enums.PieceColor;
import chess.pieces.*;
import chess.pieces.Piece.Type;

/**
 * A Game class that contains all the logic for running a game of chess.
 * @author Ken Tian, ytian13@illinois.edu
 */

public class Game {
	public static final int PLAYER1 = 0;
	public static final int PLAYER2 = 1;
	
	public Board board; // the board to play on
	public Player player[]; // the players playing the game
	private boolean player1Win; // determines if player 1 has put player 2 in checkmate
	private boolean player2Win; // determines if player 2 has put player 1 in checkmate
	private boolean inCheck; // determines if there the current player is in check
	private boolean tie; // determines if there's a stalemate
	private int turn; // determines which player can move a piece
	private CommandManager commandManager; // manages the commands taken during the game
	
	/**
	 * The constructor for the Game class. 
	 */
	public Game() {
		board = new Board(this);
		player1Win = false;
		player2Win = false;
		tie = false;
		turn = PLAYER1;
		player = new Player[2];
		player[0] = new Player(PieceColor.WHITE);
		player[1] = new Player(PieceColor.BLACK);
		commandManager = new CommandManager();
	}
	
	/**
	 * Enters a loop that continuously checks for an end-game situation.
	 */
	public void gameLoop() {
		while (!(player1Win || player2Win || tie)) {
			if (turn == PLAYER1)
				while (turn == PLAYER1);
			else
				while (turn == PLAYER2);
			
			if (!player1Win) player1Win = checkCheckmate(getPlayerColor(PLAYER2));
			if (!player2Win) player2Win = checkCheckmate(getPlayerColor(PLAYER1));
			if (!tie) tie = checkStalemate();
		}
		// reset all the variables
		tie = false;
		inCheck = false;
		turn = PLAYER1;
	}
	
	/**
	 * Checks if the Player with the given color is in checkmate. The check is done by checking if the King
	 * has no more available moves, and the Player has no Piece that can block the King from being in check.
	 * @param color, the color of the team to check checkmate for
	 * @return true if the team of the given color is in checkmate
	 */
	public boolean checkCheckmate(PieceColor color) {
		if (isInCheck(color)) {
			for (int i = 0; i < getRosterSize(color); i++) {
				Piece ally = getPieceFromRoster(color, i);
				if (ally.getType() != Piece.Type.KING) {
					Point originalPosition = new Point(ally.getX(), ally.getY());
					for (int moves = 0; moves < ally.getMoveListSize(); moves++) {
						Point potentialPoint = ally.getMove(moves);
//						System.out.println("Ally x: " + potentialPoint.x + ", y: " + potentialPoint.y);
						board.movePiece(ally, potentialPoint.x, potentialPoint.y);
						if (!isInCheck(color)) {
//							System.out.println("asdfasdfasdf");
							board.movePiece(ally, originalPosition.x, originalPosition.y);
							return false;
						}
						
						board.movePiece(ally, originalPosition.x, originalPosition.y);
					}
				}
			}
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * Checks for a stalemate by seeing if all the teams have no more valid moves to make.
	 * @param board, the board to check
	 * @return true if no teams have any valid moves, false otherwise
	 */
	public boolean checkStalemate() {
		for (int i = 0; i < getNumPlayers(); i++) { // iterate through every player
			PieceColor playerColor = getPlayerColor(i); // get their color
			for (int j = 0; j < getRosterSize(playerColor); j++) { // iterate through each player's rosters
				if (getPieceFromRoster(playerColor, j).getMoveListSize() > 0) //check if there are any available moves
					return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Calculates if the King of the given color is in check by seeing if any enemy Pieces can
	 * attack the King.
	 * @param color, the color of the King to check
	 * @param board, the board to check
	 * @return true if there is a Piece that can attack the King
	 */
	private boolean isInCheck(PieceColor color) {
		Piece king = getPieceOfType(color, Type.KING);
		Point currentPosition = new Point(king.getX(), king.getY());
		for (int i = 0; i < getNumPlayers(); i++) {
			PieceColor enemyColor = getPlayerColor(i);
			if (enemyColor != color) {
				for (int j = 0; j < getRosterSize(enemyColor); j++) {
					Piece enemy = getPieceFromRoster(enemyColor, j);
					enemy.updateMoveList(this);
//					for (int k = 0; k < enemy.getMoveListSize(); k++)
//						if (enemy.getMove(k).equals(currentPosition))
//							System.out.println("Enemy x: " + enemy.getMove(k).x + ", y: " + enemy.getMove(k).y);
			
					if (enemy.isInMoveList(currentPosition.x, currentPosition.y)) {
						inCheck = true;
						return true;
					}
				}
			}
		}
		
		inCheck = false;
		return false;
	}
	
	/**
	 * Clears the team roster of every player.
	 */
	private void clearTeams() {
		for (int i = 0; i < player.length; i++)
			player[i].clearTeam();
	}
	
	/**
	 * Starts a new game by clearing the current pieces off the board and new pieces put in place.
	 * All states in the game are also reset to false.
	 */
	public void resetGame() {
		forceTie();
		board.clearBoard();
		clearTeams();
		board.setUpGame();
		gameLoop();
	}
	
	/**
	 * Attempts to move the piece to the given coordinate only if it's the player's turn, and
	 * the player owns that piece. If the player successfully moves a piece, it becomes the
	 * other player's turn to move. The move is done through a command, and the command is
	 * stored in the CommandManager.
	 * @param piece, the piece to move
	 * @param x, the x-coordinate to move to
	 * @param y, the y-coordinate to move to
	 * @return true if the move was successful, otherwise false
	 */
	public boolean playerMove(Piece piece, int x, int y) {
		if (piece.getColor() == player[turn].getColor() && piece.canMoveTo(x, y)) {
			commandManager.executeCommand(new MoveCommand(this, piece, x, y));
			return true;
		} else
			return false;
	}
	
	/**
	 * Checks the CommandManager if it's possible to undo the last command.
	 * @return true if there's a command to undo, otherwise false
	 */
	public boolean canUndo() {
		return commandManager.isUndoAvailable();
	}
	
	/**
	 * Undoes the last command through the CommandManager.
	 */
	public void undo() {
		commandManager.undo();
	}
	
	/**
	 * Player 2 wins unconditionally.
	 */
	public void player1Forfeit() {
		player2Win = true;
	}
	
	/**
	 * Player 1 wins unconditionally
	 */
	public void player2Forfeit() {
		player1Win = true;
	}
	
	/**
	 * A tie is put into place unconditionally
	 */
	public void forceTie() {
		tie = true;
	}
	
	/**
	 * Finds and returns the Player with the given team color.
	 * @param color, the color of the player
	 * @return the Player with the given color
	 */
	private Player findPlayer(PieceColor color) {
		for (int i = 0; i < player.length; i++)
			if (player[i].getColor() == color)
				return player[i];
		
		return null;
	}
	
	/**
	 * Retrieves which player's turn the game is in.
	 * @return the turn 
	 */
	public int getTurn() {
		return turn;
	}
	
	/**
	 * Sets the Piece of the Player with the given color as captured. It removes the given Piece from the
	 * board, and the moves the Piece from the player's roster to the graveyard.
	 * @param color, the color of Player to capture the Piece from
	 * @param piece, the Piece to capture
	 */
	public void setCapturedPiece(Piece piece) {
		Player person = findPlayer(piece.getColor());
			if (person != null)
			person.getCaptured(piece);
	}
	
	/**
	 * Adds the given Piece to the team roster of the Player with the given Color.
	 * The roster remains unchanged if the 
	 * @param color
	 * @param piece
	 */
	public void addToTeam(PieceColor color, Piece piece) {
		Player person = findPlayer(color);
		if (person != null && piece != null)
			person.addToRoster(piece);
	}
	
	/**
	 * Removes the given Piece from the team roster of the Player with the given Color. 
	 * The roster is unchanged if the Piece isn't in the roster, or the player doesn't exist.
	 * @param color, the color of the player to remove the piece from
	 * @param piece, the piece to remove
	 */
	public void removeFromTeam(PieceColor color, Piece piece) {
		Player person = findPlayer(color);
		if (person != null)
			person.removeFromTeam(piece);
	}
	
	/**
	 * Returns the roster size of the player with the given color. If there is no player with
	 * the given color, then a size of -1 is returned.
	 * @param color, the Color of the Player to check
	 * @return the size of the team roster of the specified Player
	 */
	public int getRosterSize(PieceColor color) {
		Player player = findPlayer(color);
		if (player != null) {
			return player.getRosterSize();
		}
		
		return -1;
	}
	
	/**
	 * Retrieves the Piece from the Player with the given color at the given index in their roster.
	 * If there's no player with the given color, or an invalid index is given, null is returned.
	 * @param color, the color of the Player to retrieve the Piece from
	 * @param index, the index of the Piece in the roster
	 * @return the Piece from the team roster of the specified Player
	 */
	public Piece getPieceFromRoster(PieceColor color, int index) {
		Player person = findPlayer(color);
		if (person != null) 
			return person.getFromRoster(index);
		
		return null;
	}
	
	/**
	 * Retrieves the Piece of the given Type from the roster of the Player with the given
	 * Color. Null is returned if no such player exists, or no Piece of that type exists
	 * in the roster.
	 * @param color, the color of the Player to check
	 * @param type, the type of the Piece to retrieve
	 * @return the Piece with the specified Type, or null if it doesn't exist
	 */
	public Piece getPieceOfType(PieceColor color, Type type) {
		Player person = findPlayer(color);
		if (person != null)
			for (int i = 0; i < person.getRosterSize(); i++) {
				if (person.getFromRoster(i).getType() == type)
					return person.getFromRoster(i);
			}
		
		return null;
	}
	
	/**
	 * Retrieves the Color of the Player from the player list at the given index. If the index is
	 * invalid, then null is returned.
	 * @param index, the index of the player list to check
	 * @return the Color of the Player, or null if the index is invalid
	 */
	public PieceColor getPlayerColor(int index) {
		if (index < player.length && index >= 0)
			return player[index].getColor();
		
		return null;
	}
	
	/**
	 * Retrieves the number of players in the game.
	 * @return the number of players
	 */
	public int getNumPlayers() {
		return player.length;
	}
	
	/**
	 * Retrieves the check status in the game.
	 * @return true if the current player is in check, false otherwise
	 */
	public boolean getInCheck() {
		return inCheck;
	}
	
	/**
	 * Retrieves Player 1's win status, and resets the win status back to false if it was true.
	 * @return true if player 1 has won the game, false otherwise
	 */
	public boolean getPlayer1Win() {
		if (player1Win == true) {
			player1Win = false;
			return true;
		}
		
		return false;
	}
	
	/**
	 * Retrieves Player 2's win status, and resets the win status back to false if it was true.
	 * @return true if player 2 has won the game, false otherwise
	 */
	public boolean getPlayer2Win() {
		if (player2Win == true) {
			player2Win = false;
			return true;
		}
		
		return false;
	}
	
	/**
	 * Setter method for the player1Win flag.
	 * @param player1Win, the state determining whether Player 1 has won or not
	 */
	public void setPlayer1Win(boolean player1Win) {
		this.player1Win = player1Win;
	}
	
	/**
	 * Setter method for the player2Win flag.
	 * @param player2Win, the state determining whether Player 2 has won or not
	 */
	public void setPlayer2Win(boolean player2Win) {
		this.player2Win = player2Win;
	}
	
	private class MoveCommand implements Command {

		private Game model;
		
		private Piece srcPiece; // the piece being moved
		private Piece destPiece; // the piece being acted upon
		private int prevSrcPieceX; // the previous x-coordinate of the source piece
		private int prevSrcPieceY; // the previous y-coordinate of the source piece
		private int prevDestPieceX; // the previous x-coordinate of the destination piece
		private int prevDestPieceY; // the previous y-coordinate of the destination piece
		private int prevTurn; // player with the previous turn
		private int destX; // the x-coordinate to move to
		private int destY; // the y-coordinate to move to
		private boolean firstMove; // a flag determining if the piece is a pawn and if it has made its first move
		
		/**
		 * The constructor for the MoveCommand class. It stores the previous state of the 
		 * command.
		 * @param model, the game to use
		 * @param srcPiece, the piece to move
		 * @param x, the x-coordinate to move to
		 * @param y, the y-coordinate to move to
		 */
		private MoveCommand(Game model, Piece srcPiece, int x, int y) {
			this.model = model;
			this.srcPiece = srcPiece;
			this.destPiece = model.board.getPiece(x, y);
			destX = x;
			destY = y;
			prevSrcPieceX = srcPiece.getCoordinate().x;
			prevSrcPieceY = srcPiece.getCoordinate().y;
			prevTurn = model.getTurn();
			if (destPiece != null) {
				prevDestPieceX = destPiece.getCoordinate().x;
				prevDestPieceY = destPiece.getCoordinate().y;
			}
			
			if (srcPiece.getType() == Piece.Type.PAWN) {
				Pawn piece = (Pawn) srcPiece;
				firstMove = piece.getFirstMove();
			} else
				firstMove = false;
		}
		
		/**
		 * Executes the command by incrementing the turn and moving the Piece to the
		 * destination coordinate.
		 */
		@Override
		public void execute() {
			turn = (turn + 1) % 2; // set the turn to be the other player
			board.movePiece(srcPiece, destX, destY);
		}
		
		/**
		 * Undoes the command by restoring the previous state before the command was executed.
		 */
		@Override
		public void undo() {
			if (srcPiece.getType() == Piece.Type.PAWN) {
				Pawn piece = (Pawn) srcPiece;
				piece.setFirstMove(firstMove);
				System.out.println("pawn!");
			}
			
			srcPiece.setLocation(prevSrcPieceX, prevSrcPieceY, model);
			model.board.updateBoard(model);
			srcPiece.updateMoveList(model);
			System.out.println("srcPiece movelist size: " + srcPiece.getMoveListSize());
			model.turn = prevTurn;
			if (destPiece != null) {
				destPiece.setAlive(true);
				model.addToTeam(destPiece.getColor(), destPiece);
				destPiece.setLocation(prevDestPieceX, prevDestPieceY, model);
				model.board.updateBoard(model);
				destPiece.updateMoveList(model);
			}
		}
	}
}