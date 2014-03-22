package chess.pieces;

import java.awt.Point;

import chess.Board;
import chess.Game;
import chess.enums.PieceColor;

/**
 * This is a High Templar class that represents a High Templar unit from Starcraft
 * in chess.
 * @author Ken Tian, ytian13@illinois.edu
 */
public class HighTemplar extends Piece{
	
	int energy; // the resource High Templars use to cast its spell
	
	public HighTemplar(PieceColor color, int x, int y) {
		super(color, Piece.Type.HIGHTEMPLAR, x, y);
		energy = 0;
	}
	
	/**
	 * Moves the High Templar to the given coordinates if it's available on the board by calculating
	 * all of it's possible moves first, then checking if the given coordinate is in the
	 * move list. If there is an enemy 3-4 spaces in front of behind and the High Templar has enough energy,
	 * it can cast a storm that captures the enemy Piece and the Piece behind it, regardless of
	 * the color of that Piece.
	 * @param xCoord, the x-coordinate to move to
	 * @param yCoord, the y-coordinate to move to
	 * @param board, the game board to check
	 * @return true if the Piece was able to move to the given coordinates, false otherwise
	 */
	@Override
	public boolean move(int xCoord, int yCoord, Game game) {
		updateMoveList(game);
		energy++;
		if (canMoveTo(xCoord, yCoord)) {
			Piece possibleEnemy = game.board.getPiece(xCoord, yCoord);
			if (possibleEnemy != null 
				&& isEnemy(possibleEnemy)) { // check if we're capturing an enemy
//				board.removeFromTeam(possibleEnemy.getColor(), possibleEnemy);
				if ((yCoord == coordinate.y + 3 || yCoord == coordinate.y + 4
					|| yCoord == coordinate.y - 3 || yCoord == coordinate.x - 4) // check if it can cast its spell
					&& xCoord == coordinate.x && energy > 3) {
					energy = 0; // use all of its energy
					if (yCoord + 1 < Board.GLOBAL_BOARD_SIDE_LENGTH) { // also capture the piece behind the target
						Piece behind = game.board.getPiece(xCoord, yCoord + 1);
						if (behind != null)
							behind.setAlive(false);
//							board.removeFromTeam(behind.getColor(), behind);
					}
					
					if (yCoord - 1 < Board.GLOBAL_BOARD_SIDE_LENGTH) {
						Piece ahead = game.board.getPiece(xCoord, yCoord - 1);
						if (ahead != null)
							ahead.setAlive(false);
//							board.removeFromTeam(ahead.getColor(), ahead);
					}
					
					return true;
				}
			}
			
			coordinate.setLocation(xCoord, yCoord);
			return true;
		}
		
		return false;
	}
	
	/**
	 * Updates the High Templar's move list with new possible moves by clearing the current list
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
		calculateMoveList(coordinate.x, coordinate.y + 3, -1, game);
		calculateMoveList(coordinate.x, coordinate.y + 4, -1, game);
		calculateMoveList(coordinate.x, coordinate.y - 3, -1, game);
		calculateMoveList(coordinate.x, coordinate.y - 4, -1, game);
	}

	/**
	 * A helper method that calculates the all possible moves based on the behaviors of a High Templar.
	 * High Templars can move/capture one space around it, or it can cast a spell on an enemy unit
	 * that's 3-4 spaces away, which also kills anything behind it as well.
	 * @param currX, the current x-coordinate of the Piece
	 * @param currY, the current y-coordinate of the Piece
	 * @param destX, the next x-coordinate to check
	 * @param destY, the next y-coordinate to check
	 * @param direction, the direction to move to
	 * @param board, the game board to look at
	 */
	@Override
	protected void calculateMoveList(int destX, int destY, int direction, Game game) {
		if (destY >= Board.GLOBAL_BOARD_SIDE_LENGTH || destY < 0
			|| destX >= Board.GLOBAL_BOARD_SIDE_LENGTH || destX < 0
			|| (game.board.getPiece(destX, destY) != null && !isEnemy(game.board.getPiece(destX, destY))))
			return;
		
		moveList.add(new Point(destX, destY));
	}
}
