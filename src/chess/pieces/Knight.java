package chess.pieces;

import java.awt.Point;

import chess.enums.*;
import chess.Board;
import chess.Game;

/**
 * A Knight class representing a bishop piece in chess, containing movement behavior exclusive
 * to knights. This is a child class of the Piece class.
 * @author ken
 */

public class Knight extends Piece {
	/**
	 * This is the constructor for the Knight class. It only calls the super constructor.
	 * @param color, the color of the Knight
	 * @param x, the starting x-coordinate
	 * @param y, the starting y-coordinate
	 */
	public Knight(PieceColor color, int x, int y) {
		super(color, Piece.Type.KNIGHT, x, y);
	}
	
	/**
	 * Updates the Rook's move list with new possible moves by clearing the current list
	 * and populating the list again.
	 * @param board, the game board to look at
	 */
	@Override
	public void updateMoveList(Game game) {
		moveList.clear();
		calculateMoveList(coordinate.x - 1, coordinate.y - 2, -1, game);
		calculateMoveList(coordinate.x - 1, coordinate.y + 2, -1, game);
		calculateMoveList(coordinate.x + 1, coordinate.y + 2, -1 ,game);
		calculateMoveList(coordinate.x + 1, coordinate.y - 2, -1 ,game);
		calculateMoveList(coordinate.x - 2, coordinate.y - 1, -1 ,game);
		calculateMoveList(coordinate.x - 2, coordinate.y + 1, -1,game);
		calculateMoveList(coordinate.x + 2, coordinate.y + 1, -1,game);
		calculateMoveList(coordinate.x + 2, coordinate.y - 1, -1,game);
	}
	
	/**
	 * A helper method that calculates the all possible moves based on the behaviors of a Knight.
	 * Knights must move horizontally, then vertically. If the Knight moves horizontally 2 spaces,
	 * it must then move vertically one space, and vice versa.
	 * @param currX, the current x-coordinate of the Piece
	 * @param currY, the current y-coordinate of the Piece
	 * @param destX, the next x-coordinate to check
	 * @param destY, the next y-coordinate to check
	 * @param direction, the direction to move to
	 * @param board, the game board to look at
	 */
	protected void calculateMoveList(int destX, int destY, int direction, Game game) {
		if (destX >= 0 && destY >= 0
			&& destX < Board.GLOBAL_BOARD_SIDE_LENGTH
			&& destY < Board.GLOBAL_BOARD_SIDE_LENGTH
			&& (game.board.getPiece(destX, destY) == null
			|| isEnemy(game.board.getPiece(destX, destY))))
			moveList.add(new Point(destX, destY));
	}
}
