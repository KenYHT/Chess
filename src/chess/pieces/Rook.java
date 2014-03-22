package chess.pieces;

import java.awt.Point;

import chess.enums.*;
import chess.Board;
import chess.Game;

/**
 * A Rook class representing a rook piece in chess, containing movement behavior exclusive
 * to rooks. This is a child class of the Piece class.
 * @author Ken Tian, ytian13@illinois.edu
 */

public class Rook extends Piece {
	
	/**
	 * Constructor for the Rook class, which only calls the super constructor.
	 * @param color, the color of the Rook
	 * @param x, the starting x-coordinate
	 * @param y, the starting y-coordinate
	 */
	public Rook(PieceColor color, int x, int y) {
		super(color, Piece.Type.ROOK, x, y);
	}
	
	/**
	 * Updates the Rook's move list with new possible moves by clearing the current list
	 * and populating the list again.
	 * @param game, the game game to look at
	 */
	@Override
	public void updateMoveList(Game game) {
		moveList.clear();
		calculateMoveList(coordinate.x, coordinate.y + 1, 0, game);
		calculateMoveList(coordinate.x, coordinate.y - 1, 1, game);
		calculateMoveList(coordinate.x - 1, coordinate.y, 2, game);
		calculateMoveList(coordinate.x + 1, coordinate.y, 3, game);
	}

	/**
	 * An override method that calculates the all possible moves based on the behaviors of a Rook.
	 * Rooks can move and capture vertically or horizontally for any distance, as long as it's not blocked.
	 * @param destX, the next x-coordinate to check
	 * @param destY, the next y-coordinate to check
	 * @param direction, the direction to move to
	 * @param game, the game game to look at
	 */
	@Override
	protected void calculateMoveList(int destX, int destY, int direction, Game game) {
		if (destX < 0 || destY < 0
			|| destX >= Board.GLOBAL_BOARD_SIDE_LENGTH || destY >= Board.GLOBAL_BOARD_SIDE_LENGTH
			|| (game.board.getPiece(destX, destY) != null
			&& !isEnemy(game.board.getPiece(destX, destY))))
				return;
		else {
			moveList.add(new Point(destX, destY));
			if (game.board.getPiece(destX, destY) == null) {
				if (direction == 0) // traverse downwards
					calculateMoveList(destX, destY + 1, 0, game); 
				if (direction == 1) // traverse upwards
					calculateMoveList(destX, destY - 1, 1, game);
				if (direction == 2) // traverse left
					calculateMoveList(destX - 1, destY, 2, game);
				if (direction == 3) // traverse right
					calculateMoveList(destX + 1, destY, 3, game);
			}
		}
	}
}
