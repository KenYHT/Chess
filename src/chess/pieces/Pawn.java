package chess.pieces;

import java.awt.Point;

import chess.enums.*;
import chess.Board;
import chess.Game;

/**
 * A Pawn class representing a pawn piece in chess, containing movement behavior exclusive
 * to pawns. This is a child class of the Piece class.
 * @author Ken Tian, ytian13@illinois.edu
 */

public class Pawn extends Piece {

	private boolean firstMove; // a flag determining if the Pawn still has to take its first move
	
	/**
	 * This is the constructor for the Pawn class. It calls the super constructor sets
	 * firstMove to true.
	 * @param color, the color of the King
	 * @param x, the starting x-coordinate
	 * @param y, the starting y-coordinate
	 */
	public Pawn(PieceColor color, int x, int y) {
		super(color, Piece.Type.PAWN, x, y);
		firstMove = true;
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
			firstMove = false;
			coordinate.setLocation(xCoord, yCoord);
			return true;
		}
		
		return false;
	}

	/**
	 * An override method that updates the move list based on the behaviors of a Pawn.
	 * @param board, the game board to look at
	 */
	@Override
	public void updateMoveList(Game game) {
		moveList.clear();
		calculateMoveList(coordinate.x, coordinate.y, -1, game);
	}
	
	/**
	 * A helper method that calculates the all possible moves based on the behaviors of a Pawn.
	 * Pawn can move forward 2 spaces on their first move if their path isn't obstructed.
	 * Otherwise, they can only move one space forward if it's open. They can only capture one space
	 * diagonally forward.
	 * @param currX, the current x-coordinate of the Piece
	 * @param currY, the current y-coordinate of the Piece
	 * @param destX, the next x-coordinate to check
	 * @param destY, the next y-coordinate to check
	 * @param direction, the direction to move to
	 * @param board, the game board to look at
	 */
	protected void calculateMoveList(int destX, int destY, int direction, Game game) {
		if (color == PieceColor.BLACK) { // check if the Pawn is on the black team
			if (destY + 1 >= Board.GLOBAL_BOARD_SIDE_LENGTH) // check for out-of-bounds
				return;
			// check if the Pawn hasn't made its first move yet, and check the next two space in front are empty
			if (firstMove 
				&& game.board.getPiece(destX, destY + 1) == null
				&& game.board.getPiece(destX, destY + 2) == null)
				moveList.add(new Point(destX, destY + 2));
			
			// check if the space in front is empty
			if (game.board.getPiece(destX, destY + 1) == null)
				moveList.add(new Point(destX, destY + 1));
			// check if the space in the front-right is occupied by a piece of the other color
			if (destX + 1 >= 0
				&& game.board.getPiece(destX + 1, destY + 1) != null
				&& isEnemy(game.board.getPiece(destX + 1, destY + 1)))
				moveList.add(new Point(destX + 1, destY + 1));
			// check if the space in the front-left is occupied by a piece of the other color
			if (destX - 1 >= 0
				&& game.board.getPiece(destX - 1, destY + 1) != null
				&& isEnemy(game.board.getPiece(destX - 1, destY + 1)))
				moveList.add(new Point(destX - 1, destY + 1));
		} else { //the Pawn must be on the white team
			if (destY - 1 <= 0) // check for out-of-bounds
				return;
			// check if the Pawn hasn't made its first move yet, and check the next two space in front are empty
			if (firstMove 
				&& game.board.getPiece(destX, destY - 1) == null
				&& game.board.getPiece(destX, destY - 2) == null) 
				moveList.add(new Point(destX, destY - 2));

			// check if the space in front is empty
			if (game.board.getPiece(destX, destY - 1) == null)
				moveList.add(new Point(destX, destY - 1));
			// check if the space in the front-right is occupied by a piece of the other color
			if (destX + 1 >= 0
				&& game.board.getPiece(destX + 1, destY - 1) != null
				&& isEnemy(game.board.getPiece(destX + 1, destY - 1)))
				moveList.add(new Point(destX + 1, destY - 1));
			// check if the space in the front-left is occupied by a piece of the other color
			if (destX - 1 >= 0
				&& game.board.getPiece(destX - 1, destY - 1) != null
				&& isEnemy(game.board.getPiece(destX - 1, destY - 1)))
				moveList.add(new Point(destX - 1, destY - 1));
		}
	}
	
	/**
	 * Sets the firstMove flag to the given parameter
	 * @param firstMove, the state whether the Pawn can make it's first move again
	 */
	public void setFirstMove(boolean firstMove) {
		this.firstMove = firstMove;
	}
	
	/**
	 * Retrieves the state of whether or not the Pawn has made its first move.
	 * @return true if its first move has been taken, otherwise false
	 */
	public boolean getFirstMove() {
		return firstMove;
	}
}