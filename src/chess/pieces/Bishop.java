package chess.pieces;

import java.awt.Point;

import chess.enums.*;
import chess.Board;
import chess.Game;

/**
 * A Bishop class representing a bishop piece in chess, containing movement behavior exclusive
 * to bishops. This is a child class of the Piece class.
 * @author Ken Tian, ytian13@illinois.edu
 */

public class Bishop extends Piece {
	/**
	 * The constructor for the Bishop class, which only calls the super constructor.
	 * @param color, the color of the Bishop
	 * @param x, the starting x-coordinate
	 * @param y, the starting y-coordinate
	 */
	public Bishop(PieceColor color, int x, int y) {
		super(color, Piece.Type.BISHOP, x, y);
	}
	
	/**
	 * An override method that updates the move list based on the behaviors of a Bishop.
	 * @param board, the game board to look at
	 */
	@Override
	public void updateMoveList(Game game) {
		moveList.clear();
		calculateMoveList(coordinate.x + 1, coordinate.y + 1, 0, game);
		calculateMoveList(coordinate.x + 1, coordinate.y - 1, 1, game);
		calculateMoveList(coordinate.x - 1, coordinate.y + 1, 2, game);
		calculateMoveList(coordinate.x - 1, coordinate.y - 1, 3, game);
	}
	
	/**
	 * A helper method that calculates the all possible moves based on the behaviors of a Bishop.
	 * Bishops can move and capture diagonally for any distance, as long as it's not blocked.
	 * @param currX, the current x-coordinate of the Piece
	 * @param currY, the current y-coordinate of the Piece
	 * @param destX, the next x-coordinate to check
	 * @param destY, the next y-coordinate to check
	 * @param direction, the direction to move to
	 * @param board, the game board to look at
	 */
	@Override
	protected void calculateMoveList(int destX, int destY, int direction, Game game) {
		// check if the spot isn't out of bounds or occupied by a piece on the same team
		if (destX < 0 || destY < 0
			|| destX >= Board.GLOBAL_BOARD_SIDE_LENGTH || destY >= Board.GLOBAL_BOARD_SIDE_LENGTH
			|| (game.board.getPiece(destX, destY) != null
			&& !isEnemy(game.board.getPiece(destX, destY))))
			return;
		else {
			moveList.add(new Point(destX, destY));
			if (game.board.getPiece(destX, destY) == null) {
				if (direction == 0) // traverse lower-right
					calculateMoveList(destX + 1, destY + 1, 0, game); 
				if (direction == 1) // traverse upper-right
					calculateMoveList(destX + 1, destY - 1, 1, game);
				if (direction == 2) // traverse lower-left
					calculateMoveList(destX - 1, destY + 1, 2, game);
				if (direction == 3) // traverse upper-right
					calculateMoveList(destX - 1, destY - 1, 3, game);
			}
		}
	}
}
