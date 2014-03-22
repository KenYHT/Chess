package chess;

import java.util.ArrayList;

import chess.enums.*;
import chess.pieces.Piece;

/**
 * A Player class that handles the information about the player and its interaction with
 * the board.
 * @author ken
 */
public class Player {
	private PieceColor color; // the team color of the player
	private ArrayList<Piece> teamRoster; // the list of alive pieces on the player's team
	private ArrayList<Piece> graveyard; // the list of dead pieces on the player's team
	
	/**
	 * The constructor for the Player class, which initializes its lists and sets the
	 * color to represent the Player.
	 * @param color, the team color of the Player
	 */
	public Player(PieceColor color) {
		this.color = color;
		teamRoster = new ArrayList<Piece>();
		graveyard = new ArrayList<Piece>();
	}
	
	/**
	 * Adds the given piece to the team roster. If the piece is invalid, then it won't
	 * be inserted
	 * @param piece, the piece to insert
	 */
	public void addToRoster(Piece piece) {
		if (piece != null && piece.getColor() == color)
			teamRoster.add(piece);
	}
	
	/**
	 * Removes the given Piece from the team's roster. The roster is unchanged
	 * if the Piece isn't in the roster.
	 * @param piece, the piece to remove
	 */
	public void removeFromTeam(Piece piece) {
		teamRoster.remove(piece);
	}
	
	/**
	 * If the given Piece is on the team, we add that Piece to the graveyard and remove
	 * the Piece from the roster. Otherwise, we remove piece 
	 * @param piece
	 */
	public void getCaptured(Piece piece) {
		for (int i = 0; i < teamRoster.size(); i++)
			if (piece == teamRoster.get(i))
				graveyard.add(piece);
		
		teamRoster.remove(piece);
	}
	
	/**
	 * Clears the entire team roster.
	 */
	public void clearTeam() {
		teamRoster.clear();
	}
	
	/**
	 * Retrieves the Piece from the team roster at the given index. Null is returned
	 * if the index is out of bounds.
	 * @param index, the index of the Piece to retrieve
	 * @return
	 */
	public Piece getFromRoster(int index) {
		if (index >= teamRoster.size() || index < 0)
			return null;
		
		return teamRoster.get(index);
	}
	
	/**
	 * Retrieves the size of the current team roster.
	 * @return the number of alive black team Pieces
	 */
	public int getRosterSize() {
		return teamRoster.size();
	}
	
	/**
	 * Retrieves the team color of the Player.
	 * @return the player's team color
	 */
	public PieceColor getColor() {
		return color;
	}
}
