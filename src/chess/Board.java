package chess;

import chess.pieces.*;
import chess.enums.*;

/**
 * A board class that represents a chess game board. The x-values go from left to right,
 * and the y-values go from top to bottom.
 * @author Ken Tian, ytian13@illinois.edu
 */

public class Board {
	public static final int GLOBAL_BOARD_SIDE_LENGTH = 8; // The length of one side of the board for chess
	private static final int MIN_SIDE_LENGTH = 4; // The minimum length of the side of the board
	private Space spaces[][]; // the content of the board
	private int width; // the width of the board
	private int height; // the height of the board
	private Game game;
	
	/**
	 * The default constructor for the Board class. The board is initialized as an 
	 * 8x8 board of empty spaces with a black player and a white player.
	 */
	public Board(Game game) {
		spaces = new Space[GLOBAL_BOARD_SIDE_LENGTH ][GLOBAL_BOARD_SIDE_LENGTH ];
		width = GLOBAL_BOARD_SIDE_LENGTH;
		height = GLOBAL_BOARD_SIDE_LENGTH;
		populateWithEmptySpaces();
		this.game = game;
	}
	
	/**
	 * The constructor for the Board class that creates a Board object with the given
	 * width and height. The board is then populated with empty spaces.
	 * @param width
	 * @param height
	 */
	public Board(Game game, int width, int height) {
		if (width >= MIN_SIDE_LENGTH && height >= MIN_SIDE_LENGTH) {
			spaces = new Space[width][height];
			this.width = width;
			this.height = height;
		} else {
			spaces = new Space[MIN_SIDE_LENGTH][MIN_SIDE_LENGTH];
			this.width = MIN_SIDE_LENGTH;
			this.height = MIN_SIDE_LENGTH;
		}
		
		populateWithEmptySpaces();
		this.game = game;
	}
	
	/**
	 * A helper function called that populates the board with pawns and empty spaces.
	 */
	private void populatePawns() {
		for (int row = 0; row < GLOBAL_BOARD_SIDE_LENGTH; row++) {
			for (int col = 0; col < GLOBAL_BOARD_SIDE_LENGTH; col++) {
				if (row == 1) {
					spaces[col][row].addPiece(new Pawn(PieceColor.BLACK, col, row));
				} else if (row == 6) {
					spaces[col][row].addPiece(new Pawn(PieceColor.WHITE, col, row));
				}
			}
		}
	}
	
	/**
	 * A helper function to populate the first and last rows of the board with pieces
	 * for a standard chess game.
	 * @param color, the color of the pieces
	 * @param row, the row that's being populated
	 */
	private void populateBackRow(PieceColor color, int row) {
		spaces[0][row].addPiece(new Rook(color, 0, row));
		spaces[1][row].addPiece(new Knight(color, 1, row));
		spaces[2][row].addPiece(new Bishop(color, 2, row));
		spaces[5][row].addPiece(new Bishop(color, 5, row));
		spaces[6][row].addPiece(new Knight(color, 6, row));
		spaces[7][row].addPiece(new Rook(color, 7, row));
		
		if (row == 0 || row == 7) {
			spaces[3][row].addPiece(new Queen(color, 3, row));
			spaces[4][row].addPiece(new King(color, 4, row));
		}
	}
	
	/**
	 * Fills the game board with only empty spaces
	 */
	private void populateWithEmptySpaces() {
		for (int col = 0; col < width; col++) {
			for (int row = 0; row < height; row++) {
				spaces[col][row] = new Space(null);
			}
		}
	}
	
	/**
	 * Fills the initial team rosters for a chess game, and gets each piece to calculate
	 * its initial moveset.
	 */
	private void populateTeamListsUpdateMoves() {
		for (int col = 0; col < width; col++) {
			for (int row = 0; row < height; row++) {
				Piece piece = spaces[col][row].getPiece();
				if (piece != null) {
					if (row == 0 || row == 1)
						game.player[1].addToRoster(piece);
					else if (row == 6 || row == 7)
						game.player[0].addToRoster(piece);
					
					piece.updateMoveList(game);
				}
			}
		}
	}
	
	/**
	 * Clears all the pieces from the board.
	 */
	public void clearBoard() {
		for (int col = 0; col < width; col++) {
			for (int row = 0; row < height; row++) {
				spaces[col][row].removePiece();
			}
		}
	}
	
	/**
	 * Resets the board to its original position.
	 */
	public void reset() {
		spaces = null;
		spaces = new Space[GLOBAL_BOARD_SIDE_LENGTH][GLOBAL_BOARD_SIDE_LENGTH];
		populateWithEmptySpaces();
	}
	
	/**
	 * Sets up the board for a standard chess game
	 */
	public void setUpGame() {
		populatePawns();
		populateBackRow(PieceColor.BLACK, 0);
		populateBackRow(PieceColor.WHITE, 7);
		populateTeamListsUpdateMoves();
	}
	
	/**
	 * Retrieves the Piece object at the given coordinates. Return null if there's no
	 * Piece object to return.
	 * @param xCoord, the x-coordinate to check
	 * @param yCoord, the y-coordinate to check
	 * @return the Space to retrieve
	 */
	public Piece getPiece(int xCoord, int yCoord) {
		//check for invalid coordinates
		if (xCoord < 0 || yCoord < 0 || 
			xCoord >= width || yCoord >= height)
			return null;
		else
			return spaces[xCoord][yCoord].getPiece();
	}
	
	/**
	 * Adds the given Piece to the board at the given coordinates, as long as the
	 * coordinates are valid.
	 * @param piece, the Piece to add
	 */
	public void addPiece(Piece piece) {
		if (piece.getX() >= 0 && piece.getY() >= 0 &&
			piece.getX() < width && piece.getY() < height)
			spaces[piece.getX()][piece.getY()].addPiece(piece);
	}
	
	/**
	 * Removes the Piece occupying the given coordinates, as long as the coordinate
	 * is valid and the coordinate is occupied by a Piece.
	 * @param xCoord
	 * @param yCoord
	 */
	public void removePiece(int xCoord, int yCoord) {
		if (xCoord >= 0 && yCoord >= 0 && 
			xCoord < width && yCoord < height)
			spaces[xCoord][yCoord].removePiece();
	}
	
	/**
	 * Sets the Piece of the Player with the given color as captured. It removes the given Piece from the
	 * board, and the moves the Piece from the player's roster to the graveyard.
	 * @param color, the color of Player to capture the Piece from
	 * @param piece, the Piece to capture
	 */
	public void setCapturedPiece(Piece piece) {
		for (int i = 0; i < game.getNumPlayers(); i++) {
			if (game.player[i].getColor() == piece.getColor()) {
				game.player[i].getCaptured(piece);
				piece.setAlive(false);
				return;
			}
		}
	}
	
	/**
	 * Moves the piece at the source coordinates to the destination coordinate if possible.
	 * @param piece, the piece to check
	 * @param xDest, the x-coordinate to move to
	 * @param yDest, the y-coordinate to move to
	 * @return true if the piece successfully moved, false otherwise
	 */
	public boolean movePiece(Piece piece, int xDest, int yDest) {
		Piece destination = getPiece(xDest, yDest); // piece currently at the destination
		if (piece != null && piece.move(xDest, yDest, game)) {
			if (piece.getType() == Piece.Type.HIGHTEMPLAR &&
				(yDest == piece.getY() + 3 || yDest == piece.getY() + 4
				|| yDest == piece.getY() - 3 || yDest == piece.getY() - 4)) {
				if (destination != null)
					setCapturedPiece(destination);
				
				Piece behind = getPiece(xDest, yDest + 1);
				Piece ahead = getPiece(xDest, yDest - 1);
				if (behind != null)
					setCapturedPiece(behind);
				
				if (ahead != null)
					setCapturedPiece(ahead);
				
				updateBoard(game);
				return true;
			}
			
			if (destination != null)
				setCapturedPiece(destination);
			
			updateBoard(game);
			return true;
		}
		
		return false;
	}
	
	/**
	 * Updates the board based on where the pieces' coordinates are.
	 * @param game, the game to update the board on
	 */
	public void updateBoard(Game game) {
		clearBoard();
		for (int i = 0; i < game.getNumPlayers(); i++) {
			for (int j = 0; j < game.player[i].getRosterSize(); j++) {
				addPiece(game.player[i].getFromRoster(j));
				game.player[i].getFromRoster(j).updateMoveList(game);
			}
		}
	}
}
