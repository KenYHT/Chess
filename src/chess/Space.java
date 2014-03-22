package chess;

import chess.pieces.Piece;

/**
 * A space class containing information about individual spaces on a game board
 * @author Ken Tian, ytian13@illinois.edu
 */
public class Space {
	
	private Piece piece; // the Piece object that currently occupies this space, may be null
	
	/**
	 * The constructor for the Space class, it takes a Piece object, and keeps a reference
	 * of that piece object if it exists. Otherwise, the reference is null.
	 * @param piece, the Piece object to occupy the Space.
	 */
	public Space(Piece piece) {
		this.piece = piece;
	}
	
	/**
	 * Adds a reference of the given Piece.
	 * @param other, the Piece to add to this Space
	 */
	public void addPiece(Piece piece) {
		if (piece != null)
			this.piece = piece;
	}
	
	/**
	 * Removes the Piece occupying this Space.
	 */
	public void removePiece() {
		piece = null;
	}
	
	/**
	 * Retrieves the Piece occupying this Space.
	 * @return the Piece object occupying this Space.
	 */
	public Piece getPiece() {
		return piece;
	}
}
