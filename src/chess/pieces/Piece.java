package chess.pieces;

import java.awt.Point;
import java.util.ArrayList;

import chess.enums.*;
import chess.Board;
import chess.Game;

/**
 * A Piece class representing a generic piece in chess. Provides most functionality
 * common to every chess piece.
 * @author Ken Tian, ytian13@illinois.edu
 */

public abstract class Piece {
	public enum Type {
		PAWN, KNIGHT, BISHOP, ROOK, QUEEN, KING, HIGHTEMPLAR, GHOST, NOTHING
	}
	
	protected PieceColor color; 
	protected Type type;
	protected boolean alive; // flag determining if the piece is captured
	protected Point coordinate; // the current location of the piece, first is x, second is y
	protected ArrayList<Point> moveList; // a list of possible coordinates the piece can move to
	
	/**
	 * The constructor of the Piece class. It sets the basic information of the Piece object.
	 * @param color, the team color of the piece
	 * @param type, the type of the piece
	 * @param x, the starting x-coordinate
	 * @param y, the starting y-coordinate
	 */
	public Piece(PieceColor color, Type type, int x, int y) {
		this.color = color;
		this.type = type;
		coordinate = new Point(x, y);
		moveList = new ArrayList<Point>();
		alive = true;
	}

	/**
	 * Changes the status of the piece
	 * @param isAlive, whether or not the piece is still alive
	 */
	public void setAlive(boolean isAlive) {
		alive = isAlive;
	}
	
	/**
	 * Sets the location of the Piece to the given coordinate, then updates the move list. 
	 * If the coordinate isn't valid, then the piece isn't moved
	 * @param xCoord, the x-coordinate to move to
	 * @param yCoord, the y-coordinate to move to
	 */
	public void setLocation(int xCoord, int yCoord, Game game) {
		if (xCoord >= 0 && yCoord >= 0 && xCoord < Board.GLOBAL_BOARD_SIDE_LENGTH && yCoord < Board.GLOBAL_BOARD_SIDE_LENGTH) {
			coordinate.x = xCoord;
			coordinate.y = yCoord;
			updateMoveList(game);
		}
	}
	
	/**
	 * Moves the Piece to the given coordinates if it's available on the board by calculating
	 * all of it's possible moves first, then checking if the given coordinate is in the
	 * move list. It returns true if the Piece moved successfully.
	 * @param xCoord, the x-coordinate to move to
	 * @param yCoord, the y-coordinate to move to
	 * @param board, the game board to check
	 * @return true if the Piece was able to move to the given coordinates, false otherwise
	 */
	public boolean move(int xCoord, int yCoord, Game game) {
		updateMoveList(game);
		if (canMoveTo(xCoord, yCoord)) {
			Piece possibleEnemy = game.board.getPiece(xCoord, yCoord);
			if (possibleEnemy != null 
				&& isEnemy(possibleEnemy)) { // check if we're capturing an enemy
				if (possibleEnemy.getColor() != color)
					possibleEnemy.setAlive(false);
			}
			
			coordinate.setLocation(xCoord, yCoord);
			return true;
		}
		
		return false;
	}
	
	/**
	 * Checks if the Piece can move to the given coordinates by looking through the list of
	 * possible moves
	 * @param xCoord, the x-coordinate to check
	 * @param yCoord, the y-coordinate to check
	 * @param board, the game board to look at
	 */
	public boolean canMoveTo(int xCoord, int yCoord) {
		for (int i = 0; i < moveList.size(); i++) {
			Point possibleMove = moveList.get(i);
			if (possibleMove.x == xCoord && possibleMove.y == yCoord)
				return true;
		}
		
		return false;
	}
	
	/**
	 * Updates the move list with new possible moves by clearing the current list
	 * and populating the list again 
	 * @param board, the game board to look at
	 */
	public abstract void updateMoveList(Game game);
	
	/**
	 * An abstract method that calculates all the possible moves the Piece depending on the
	 * type of piece it is.
	 * @param destX, the next x-coordinate to check
	 * @param destY, the next y-coordinate to check
	 * @param direction, the direction to move to
	 * @param board, the game board to look at
	 */
	protected abstract void calculateMoveList(int destX, int destY, int direction, Game game);
	
	/**
	 * Calculates if the given piece is on the opposite team.
	 * @param piece, the piece to check
	 * @return true if the given piece is a different color, false otherwise
	 */
	protected boolean isEnemy(Piece piece) {
		return piece.getColor() != color;
	}
	
	/**
	 * Get method for the piece's color
	 * @return the piece's team color
	 */
	public PieceColor getColor() {
		return this.color;
	}
	
	/**
	 * Get method for the x-coordinate of the piece
	 * @return the current x-coordinate of the piece
	 */
	public int getX() {
		return coordinate.x;
	}
	
	/**
	 * Get method for the y-coordinate of the piece
	 * @return the current y-coordinates of the piece
	 */
	public int getY() {
		return coordinate.y;
	}
	
	/**
	 * Get method for the Point object representing the current location of the piece
	 * @return the Point object of the piece's coordinate
	 */
	public Point getCoordinate() {
		return coordinate;
	}
	
	/**
	 * Get method for the alive flag.
	 * @return the alive state of the piece
	 */
	public boolean isAlive() {
		return alive;
	}
	
	/**
	 * Get method for the Piece's type.
	 * @return the pieces type
	 */
	public Type getType() {
		return type;
	}
	
	/**
	 * Get method for the size of the Piece's move list.
	 * @return the size of the move list
	 */
	public int getMoveListSize() {
		return moveList.size();
	}
	
	/**
	 * Checks if the given coordinates is in the move list of the Piece.
	 * @param xCoord, the x-coordinate to check
	 * @param yCoord, the y-coordinate to check
	 * @return true if the coordinates is in the move list, false otherwise
	 */
	public boolean isInMoveList(int xCoord, int yCoord) {
		Point coordinate = new Point(xCoord, yCoord);
		return moveList.contains(coordinate);
	}
	
	/**
	 * Retrieves the move in the Piece's move list at the specified index.
	 * @param index, the index in the move list to get
	 * @return a Point containing the x and y coordinates of the move at the given index
	 */
	public Point getMove(int index) {
		if (index >= 0 && index < moveList.size())
			return moveList.get(index);
		
		return null;
	}
	
	/**
	 * Retrives the Piece's alive state.
	 * @return true if the Piece hasn't been captured, false otherwise
	 */
	public boolean getAlive() {
		return alive;
	}
}
