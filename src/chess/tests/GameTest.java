package chess.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import chess.Game;
import chess.enums.PieceColor;
import chess.pieces.King;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class GameTest {

	public static Game game;
	
	public static King kingWhite;
	public static King kingBlack;
	
	/**
	 * This method is ran before every test. It creates a new game, and puts Kings in 
	 * specific spaces for testing purposes.
	 */
	@Before
	public void setUpClass() throws Exception {
		game = new Game();
		game.board.addPiece(new King(PieceColor.WHITE, 3, 6));
		game.board.addPiece(new King(PieceColor.BLACK, 5, 1));
		kingWhite = (King) game.board.getPiece(3, 6);
		kingBlack = (King) game.board.getPiece(5, 1);
		game.addToTeam(PieceColor.WHITE, kingWhite);
		game.addToTeam(PieceColor.BLACK, kingBlack);
		kingWhite.updateMoveList(game);
		kingBlack.updateMoveList(game);
	}
	
	@After
	public void after() throws Exception {
		game.removeFromTeam(PieceColor.WHITE, kingWhite);
		game.removeFromTeam(PieceColor.BLACK, kingBlack);
	}
	
	/**
	 * Moves the King to several empty spaces.
	 */
	@Test
	public void testCheckmate() throws Exception {
		Queen blackEnemy = new Queen(PieceColor.BLACK, 2, 5);
		Rook blackEnemy2 = new Rook(PieceColor.BLACK, 4, 7);
		game.addToTeam(PieceColor.BLACK, blackEnemy);
		game.addToTeam(PieceColor.BLACK, blackEnemy2);
		game.board.addPiece(blackEnemy);
		game.board.addPiece(blackEnemy2);
		blackEnemy.updateMoveList(game);
		blackEnemy2.updateMoveList(game);
		assertEquals(game.checkCheckmate(PieceColor.WHITE), true);
	}
	
	/**
	 * Tries to move Kings to spaces occupied by pieces of the same color.
	 */
	@Test
	public void testFalseCheckmate() throws Exception {
		Queen whiteEnemy = new Queen(PieceColor.WHITE, 7, 3);
		Rook whiteEnemy2 = new Rook(PieceColor.WHITE, 3, 0);
		Rook whiteEnemy3 = new Rook(PieceColor.WHITE, 4, 4);
		Rook whiteEnemy4 = new Rook(PieceColor.WHITE, 6, 5);
		Queen blackAlly = new Queen(PieceColor.BLACK, 4, 2);
 		game.addToTeam(PieceColor.WHITE, whiteEnemy);
		game.addToTeam(PieceColor.WHITE, whiteEnemy2);
		game.addToTeam(PieceColor.WHITE, whiteEnemy3);
		game.addToTeam(PieceColor.WHITE, whiteEnemy4);
		game.addToTeam(PieceColor.BLACK, blackAlly);
		game.board.addPiece(whiteEnemy);
		game.board.addPiece(whiteEnemy2);
		game.board.addPiece(whiteEnemy3);
		game.board.addPiece(whiteEnemy4);
		game.board.addPiece(blackAlly);
		whiteEnemy.updateMoveList(game);
		whiteEnemy2.updateMoveList(game);
		whiteEnemy3.updateMoveList(game);
		whiteEnemy4.updateMoveList(game);
		blackAlly.updateMoveList(game);
		assertEquals(game.checkCheckmate(PieceColor.BLACK), false);
	}
	
	/**
	 * Tries to move Kings to spaces it shouldn't be able to move to, such as
	 * out-of-bounds and spaces that are outside of its movement patterns. This also
	 * tries to move the King into a position that would put it into check.
	 */
	@Test
	public void testStalemate() throws Exception {
		game.board.removePiece(3, 6);
		game.board.removePiece(5, 1);
		game.removeFromTeam(PieceColor.BLACK, kingBlack);
		game.removeFromTeam(PieceColor.WHITE, kingWhite);
		assertEquals(game.checkStalemate(), true);
	}
}
