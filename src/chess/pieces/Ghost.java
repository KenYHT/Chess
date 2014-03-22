package chess.pieces;

import java.awt.Point;

import chess.Board;
import chess.Game;
import chess.enums.PieceColor;

public class Ghost extends Piece {
	private final int MAX_RANGE = 5;
	
	public Ghost(PieceColor color, int x, int y) {
		super(color, Piece.Type.GHOST, x, y);
	}
	
	/**
	 * Moves the Ghost to the given coordinates if it's available on the board by calculating
	 * all of it's possible moves first, then checking if the given coordinate is in the
	 * move list. If there is a High Templar within 5 range, then a Ghost can capture it, but
	 * it won't be able to move for that turn. Otherwise, it can capture and move in any direction
	 * for 1 space.
	 * @param xCoord, the x-coordinate to move to
	 * @param yCoord, the y-coordinate to move to
	 * @param board, the game board to check
	 * @return true if the Piece was able to move to the given coordinates, false otherwise
	 */
	@Override
	public boolean move(int xCoord, int yCoord, Game game) {
		updateMoveList(game);
		if (canMoveTo(xCoord, yCoord)) {
			Piece possibleEnemy = game.board.getPiece(xCoord, yCoord);
			// Check if a templar is in range first
			if (Math.abs(xCoord - coordinate.x) >= 2 || Math.abs(yCoord - coordinate.y) >= 2
				|| Math.abs(xCoord - coordinate.x) + Math.abs(yCoord - coordinate.y) >= 3
				&& possibleEnemy != null && isEnemy(possibleEnemy) 
				&& possibleEnemy.getType() == Piece.Type.HIGHTEMPLAR) {
				possibleEnemy.setAlive(false);
				return true;
			} else if (Math.abs(xCoord - coordinate.x) < 2 && Math.abs(yCoord - coordinate.y) < 2
					&& Math.abs(xCoord - coordinate.x) + Math.abs(yCoord - coordinate.y) < 3 
					&& possibleEnemy != null && isEnemy(possibleEnemy)) // check if we're capturing an enemy
				possibleEnemy.setAlive(false);
				
			coordinate.setLocation(xCoord, yCoord);
			return true;
		}
		
		return false;
	}
	
	/**
	 * Updates the Ghost's move list with new possible moves by clearing the current list
	 * and populating the list again.
	 * @param board, the game board to look at
	 */
	@Override
	public void updateMoveList(Game game) {
		moveList.clear();
		calculateMoveList(coordinate.x + 1, coordinate.y, -1, game);
		calculateMoveList(coordinate.x - 1, coordinate.y, -1, game);
		calculateMoveList(coordinate.x, coordinate.y + 1, -1, game);
		calculateMoveList(coordinate.x, coordinate.y - 1, -1, game);
		calculateMoveList(coordinate.x + 1, coordinate.y + 1, -1, game);
		calculateMoveList(coordinate.x - 1, coordinate.y + 1, -1, game);
		calculateMoveList(coordinate.x + 1, coordinate.y - 1, -1, game);
		calculateMoveList(coordinate.x - 1, coordinate.y - 1, -1, game);
		calculateMoveList(coordinate.x, coordinate.y, 0, game);
		calculateMoveList(coordinate.x, coordinate.y, 1, game);
		calculateMoveList(coordinate.x, coordinate.y, 2, game);
		calculateMoveList(coordinate.x, coordinate.y, 3, game);
		calculateMoveList(coordinate.x, coordinate.y, 4, game);
		calculateMoveList(coordinate.x, coordinate.y, 5, game);
		calculateMoveList(coordinate.x, coordinate.y, 6, game);
		calculateMoveList(coordinate.x, coordinate.y, 7, game);
	}

	/**
	 * A helper method that calculates the all possible moves based on the behaviors of a Ghost.
	 * Ghosts can move/capture one space around it, or it can snipe any High Templar within
	 * 5 spaces in any direction. Sniping will not move the Ghost to the High Templar's position.
	 * @param currX, the current x-coordinate of the Piece
	 * @param currY, the current y-coordinate of the Piece
	 * @param destX, the next x-coordinate to check
	 * @param destY, the next y-coordinate to check
	 * @param direction, the direction to move to
	 * @param board, the game board to look at
	 */
	@Override
	protected void calculateMoveList(int destX, int destY, int direction, Game game) {
		if (destY + 1 >= Board.GLOBAL_BOARD_SIDE_LENGTH || destY - 1 < 0
			|| destX + 1 >= Board.GLOBAL_BOARD_SIDE_LENGTH || destX - 1 < 0
			|| !isEnemy(game.board.getPiece(destX, destY)))
			return;
		if (direction != -1) {
			for (int i = 1; i < MAX_RANGE; i++) {
				switch(direction) {
					case 0: // left
						if (game.board.getPiece(destX - i, destY).getType() == Piece.Type.HIGHTEMPLAR
							&& isEnemy(game.board.getPiece(destX - i, destY)))
							moveList.add(new Point(destX - i, destY));
						break;
					case 1: // upper-left
						if (game.board.getPiece(destX - i, destY - i).getType() == Piece.Type.HIGHTEMPLAR
						&& isEnemy(game.board.getPiece(destX - i, destY - i)))
							moveList.add(new Point(destX - i, destY - i));
						break;
					case 2: // up
						if (game.board.getPiece(destX, destY - i).getType() == Piece.Type.HIGHTEMPLAR
						&& isEnemy(game.board.getPiece(destX, destY - i)))
							moveList.add(new Point(destX, destY - i));
						break;
					case 3: // upper-right
						if (game.board.getPiece(destX + i, destY - i).getType() == Piece.Type.HIGHTEMPLAR
						&& isEnemy(game.board.getPiece(destX + i, destY - i)))
							moveList.add(new Point(destX + i, destY - i));
						break;
					case 4: // right
						if (game.board.getPiece(destX + i, destY).getType() == Piece.Type.HIGHTEMPLAR
						&& isEnemy(game.board.getPiece(destX + i, destY)))
							moveList.add(new Point(destX + i, destY));
						break;
					case 5: // lower-right
						if (game.board.getPiece(destX + i, destY + i).getType() == Piece.Type.HIGHTEMPLAR
						&& isEnemy(game.board.getPiece(destX + i, destY + i)))
							moveList.add(new Point(destX + i, destY + i));
						break;
					case 6: // down
						if (game.board.getPiece(destX, destY + i).getType() == Piece.Type.HIGHTEMPLAR
						&& isEnemy(game.board.getPiece(destX, destY + i)))
							moveList.add(new Point(destX, destY + i));
						break;
					case 7: // lower-left
						if (game.board.getPiece(destX - i, destY + i).getType() == Piece.Type.HIGHTEMPLAR
						&& isEnemy(game.board.getPiece(destX - i, destY + i)))
							moveList.add(new Point(destX - i, destY + i));
				}
			}
			
			return;
		}
		
		moveList.add(new Point(destX, destY));
	}
}
