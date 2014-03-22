package chess.pieces;

import java.awt.Point;

import chess.enums.*;
import chess.Board;
import chess.Game;

/**
 * A Queen class representing a queen piece in chess, containing movement behavior exclusive
 * to queens. This is a child class of the Piece class.
 * @author Ken Tian, ytian13@illinois.edu
 */

public class Queen extends Piece {
	/**
	 * This is the constructor for the Queen class. It only calls the super constructor.
	 * @param color, the color of the Queen
	 * @param x, the starting x-coordinate
	 * @param y, the starting y-coordinate
	 */
	public Queen(PieceColor color, int x, int y) {
		super(color, Piece.Type.QUEEN, x, y);
	}
	
	/**
	 * Updates the Queen's move list with new possible moves by clearing the current list
	 * and populating the list again.
	 * @param game, the game game to look at
	 */
	@Override
	public void updateMoveList(Game game) {
		moveList.clear();
		calculateMoveList(coordinate.x + 1, coordinate.y + 1, 0, game);
		calculateMoveList(coordinate.x + 1, coordinate.y - 1, 1, game);
		calculateMoveList(coordinate.x - 1, coordinate.y + 1, 2, game);
		calculateMoveList(coordinate.x - 1, coordinate.y - 1, 3, game);
		calculateMoveList(coordinate.x, coordinate.y + 1, 4, game);
		calculateMoveList(coordinate.x, coordinate.y - 1, 5, game);
		calculateMoveList(coordinate.x - 1, coordinate.y, 6, game);
		calculateMoveList(coordinate.x + 1, coordinate.y, 7, game);
	}
	
	/**
	 * An override method that calculates the all possible moves based on the behaviors of a Queen.
	 * Queens can move and capture vertically, horizontally, or diagonally for any distance, as long as it's not blocked.
	 * @param currX, the current x-coordinate of the Piece
	 * @param currY, the current y-coordinate of the Piece
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
				if (direction == 0) // traverse lower-right
					calculateMoveList(destX + 1, destY + 1, 0, game); 
				if (direction == 1) // traverse upper-right
					calculateMoveList(destX + 1, destY - 1, 1, game);
				if (direction == 2) // traverse lower-left
					calculateMoveList(destX - 1, destY + 1, 2, game);
				if (direction == 3) // traverse upper-left
					calculateMoveList(destX - 1, destY - 1, 3, game);
				if (direction == 4) // traverse downwards
					calculateMoveList(destX, destY + 1, 4, game); 
				if (direction == 5) // traverse upwards
					calculateMoveList(destX, destY - 1, 5, game);
				if (direction == 6) // traverse left
					calculateMoveList(destX - 1, destY, 6, game);
				if (direction == 7) // traverse right
					calculateMoveList(destX + 1, destY, 7, game);
			}
		}
	}
}
