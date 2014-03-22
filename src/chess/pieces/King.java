package chess.pieces;

import java.awt.Point;

import chess.enums.*;
import chess.Board;
import chess.Game;

/**
 * A King class representing a king piece in chess, containing movement behavior exclusive
 * to kings. This is a child class of the Piece class.
 * @author Ken Tian, ytian13@illinois.edu
 */

public class King extends Piece{
	
//	private boolean inCheck; // a flag determining if the King is in check.
	
	/**
	 * This is the constructor for the King class. It calls the super constructor, then sets
	 * inCheck to false.
	 * @param color, the color of the King
	 * @param x, the starting x-coordinate
	 * @param y, the starting y-coordinate
	 */
	public King(PieceColor color, int x, int y) {
		super(color, Piece.Type.KING, x, y);
//		inCheck = false;
	}

	/**
	 * Updates the King's move list with new possible moves by clearing the current list
	 * and populating the list again.
	 * @param board, the game board to look at
	 */
	@Override
	public void updateMoveList(Game game) {
		moveList.clear();
		calculateMoveList(coordinate.x + 1, coordinate.y, -1, game);
		calculateMoveList(coordinate.x - 1, coordinate.y, -1, game);
		calculateMoveList(coordinate.x + 1, coordinate.y + 1, -1, game);
		calculateMoveList(coordinate.x + 1, coordinate.y - 1, -1, game);
		calculateMoveList(coordinate.x - 1, coordinate.y + 1, -1, game);
		calculateMoveList(coordinate.x - 1, coordinate.y - 1, -1, game);
		calculateMoveList(coordinate.x, coordinate.y + 1, -1, game);
		calculateMoveList(coordinate.x, coordinate.y - 1, -1, game);
	}
	
	/**
	 * A helper function for the calculateMoveList method. It takes in a coordinate, checking if
	 * it's within bounds and checks that the space isn't blocked and moving to that space
	 * won't put the King in check.
	 * @param destX, the x-coordinate to check
	 * @param destY, the y-coordinate to check
	 * @param direction, the direction to move to
	 * @param board, the game board to look at
	 */
	@Override
	protected void calculateMoveList(int destX, int destY, int direction, Game game) {
		// check if the coordinates is in bounds
		if (destX >= 0 && destY >= 0
			&& destX < Board.GLOBAL_BOARD_SIDE_LENGTH && destY < Board.GLOBAL_BOARD_SIDE_LENGTH) {
			if (game.board.getPiece(destX, destY) != null 
				&& !isEnemy(game.board.getPiece(destX, destY))) // check if the space is occupied by an ally
				return;
			
			Point potentialMove = new Point(destX, destY);
			for (int i = 0; i < game.getNumPlayers(); i++) {
				PieceColor enemyColor = game.getPlayerColor(i);
				if (enemyColor != color && movesIntoCheck(potentialMove, enemyColor, game))
					return;
			}
			
			moveList.add(potentialMove);
		}
	}
	
	/**
	 * Checks if the given potential move will move the King to a space that's within
	 * the move list of a piece with the given enemy color.
	 * @param potentialMove, the potential coordinate to check
	 * @param enemyColor, the color of the enemy team
	 * @param board, the board to check
	 * @return true if the potential move coordinates is within the enemy movelist, otherwise false
	 */
	private boolean movesIntoCheck(Point potentialMove, PieceColor enemyColor, Game game) {
		for (int i = 0; i < game.getRosterSize(enemyColor); i++) { // iterate through the enemy's roster
			Piece enemy = game.getPieceFromRoster(enemyColor, i);
			for (int enemyMove = 0; enemyMove < enemy.moveList.size(); enemyMove++) {
				if (potentialMove.equals(enemy.moveList.get(enemyMove))) // checks if the potential spot to move to is within the enemy's movelist
					return true;
			}
		}
		
		return false;
	}
}
