package chess.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import chess.enums.*;
import chess.Game;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class KingTest {
	
	public static Game game;
	public static King kingCorner1Black;
	public static King kingCorner1White;
	public static King kingCorner2Black;
	public static King kingCorner2White;
	public static King kingSide1Black;
	public static King kingSide1White;
	public static King kingSide2Black;
	public static King kingSide2White;
	public static King kingMiddleWhite;
	
	/**
	 * This method is ran before every test. It creates a new board, and puts Kings in 
	 * specific spaces for testing purposes.
	 */
	@Before
	public void setUpClass() throws Exception {
		game = new Game();
		game.board.addPiece(new King(PieceColor.BLACK, 0, 0));
		game.board.addPiece(new King(PieceColor.WHITE, 0, 7));
		game.board.addPiece(new King(PieceColor.BLACK, 7, 0));
		game.board.addPiece(new King(PieceColor.WHITE, 7, 7));
		game.board.addPiece(new King(PieceColor.BLACK, 0, 3));
		game.board.addPiece(new King(PieceColor.WHITE, 3, 0));
		game.board.addPiece(new King(PieceColor.BLACK, 7, 3));
		game.board.addPiece(new King(PieceColor.WHITE, 3, 7));
		game.board.addPiece(new King(PieceColor.WHITE, 4, 4));
		kingCorner1Black = (King) game.board.getPiece(0, 0);
		kingCorner1White = (King) game.board.getPiece(0, 7);
		kingCorner2Black = (King) game.board.getPiece(7, 0);
		kingCorner2White = (King) game.board.getPiece(7, 7);
		kingSide1Black = (King) game.board.getPiece(0, 3);
		kingSide1White = (King) game.board.getPiece(3, 0);
		kingSide2Black = (King) game.board.getPiece(7, 3);
		kingSide2White = (King) game.board.getPiece(3, 7);
		kingMiddleWhite = (King) game.board.getPiece(4, 4);
	}
	
	/**
	 * Moves the King to several empty spaces.
	 */
	@Test
	public void moveToValidSpace() throws Exception {
		game.board.movePiece(kingCorner1Black, 1, 0);
		game.board.movePiece(kingCorner2White, 7, 6);
		game.board.movePiece(kingSide1Black, 1, 2);
		game.board.movePiece(kingSide2White, 4, 6);
		game.board.movePiece(kingMiddleWhite, 4, 5);
		assertEquals(kingCorner1Black, game.board.getPiece(1, 0));
		assertEquals(kingCorner2White, game.board.getPiece(7, 6));
		assertEquals(kingSide1Black, game.board.getPiece(1, 2));
		assertEquals(kingSide2White, game.board.getPiece(4, 6));
		assertEquals(kingMiddleWhite, game.board.getPiece(4, 5));
	}
	
	/**
	 * Tries to move Kings to spaces occupied by pieces of the same color.
	 */
	@Test
	public void moveToOccupiedSpace() throws Exception {
		King obstruction1 = new King(PieceColor.WHITE, 0, 6);
		King obstruction2 = new King(PieceColor.BLACK, 6, 1);
		Rook obstruction3 = new Rook(PieceColor.WHITE, 4, 0);
		Queen obstruction4 = new Queen(PieceColor.BLACK, 6, 4);
		game.board.addPiece(obstruction1);
		game.board.addPiece(obstruction2);
		game.board.addPiece(obstruction3);
		game.board.addPiece(obstruction4);
		
		game.board.movePiece(kingCorner1White, 0, 6);
		game.board.movePiece(kingCorner2Black, 6, 1);
		game.board.movePiece(kingSide1White, 4, 0);
		game.board.movePiece(kingSide2Black, 6, 4);
		assertEquals(kingCorner1White, game.board.getPiece(0, 7));
		assertEquals(kingCorner2Black, game.board.getPiece(7, 0));
		assertEquals(kingSide1White, game.board.getPiece(3, 0));
		assertEquals(kingSide2Black, game.board.getPiece(7, 3));
	}
	
	/**
	 * Tries to move Kings to spaces it shouldn't be able to move to, such as
	 * out-of-bounds and spaces that are outside of its movement patterns. This also
	 * tries to move the King into a position that would put it into check.
	 */
	@Test
	public void invalidMove() throws Exception {
		game.board.movePiece(kingCorner1Black, -1, 2);
		game.board.movePiece(kingCorner2White, 4, 7);
		game.board.movePiece(kingSide1Black, 1, -1);
		game.board.movePiece(kingSide2White, 4, 0);
		game.board.movePiece(kingMiddleWhite, 10, 4);
		assertEquals(kingCorner1Black, game.board.getPiece(0, 0));
		assertEquals(kingCorner2White, game.board.getPiece(7, 7));
		assertEquals(kingSide1Black, game.board.getPiece(0, 3));
		assertEquals(kingSide2White, game.board.getPiece(3, 7));
		assertEquals(kingMiddleWhite, game.board.getPiece(4, 4));
		
		Queen whiteEnemy = new Queen(PieceColor.WHITE, 3, 3);
		Knight blackEnemy = new Knight(PieceColor.BLACK, 5, 5);
		game.board.addPiece(whiteEnemy);
		game.board.addPiece(blackEnemy);
//		game.board.addToTeam(PieceColor.WHITE, whiteEnemy);
//		game.board.addToTeam(PieceColor.BLACK, blackEnemy);
//		game.board.movePiece(3, 3, 3, 3);
//		game.board.movePiece(5, 5, 5, 5);
		game.board.movePiece(kingCorner1Black, 1, 1);
		game.board.movePiece(kingCorner2White, 6, 7);
		assertEquals(kingCorner1Black, game.board.getPiece(0, 0));
		assertEquals(kingCorner2White, game.board.getPiece(7, 7));
	}
	
	/**
	 * Kings try to capture other pieces of various types and of opposite color.
	 */
	@Test
	public void captureOtherPiece() throws Exception {
		Rook captureTarget1 = new Rook(PieceColor.BLACK, 1, 7);
		King captureTarget2 = new King(PieceColor.WHITE, 6, 0);
		Rook captureTarget3 = new Rook(PieceColor.BLACK, 4, 1);
		Queen captureTarget4 = new Queen(PieceColor.WHITE, 6, 4);
		game.board.addPiece(captureTarget1);
		game.board.addPiece(captureTarget2);
		game.board.addPiece(captureTarget3);
		game.board.addPiece(captureTarget4);
		
		game.board.movePiece(kingCorner1White, 1, 7);
		game.board.movePiece(kingCorner2Black, 6, 0);
		game.board.movePiece(kingSide1White, 4, 1);
		game.board.movePiece(kingSide2Black, 6, 4);
		assertEquals(kingCorner1White, game.board.getPiece(1, 7));
		assertEquals(kingCorner2Black, game.board.getPiece(6, 0));
		assertEquals(kingSide1White, game.board.getPiece(4, 1));
		assertEquals(kingSide2Black, game.board.getPiece(6, 4));
		assertFalse(captureTarget1.isAlive());
		assertFalse(captureTarget2.isAlive());
		assertFalse(captureTarget3.isAlive());
		assertFalse(captureTarget4.isAlive());
	}
}
