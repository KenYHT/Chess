package chess.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import chess.enums.*;
import chess.Game;
import chess.pieces.*;

public class PawnTest {
	
	public static Game game;
	public static Pawn pawnCorner1Black;
	public static Pawn pawnCorner1White;
	public static Pawn pawnCorner2Black;
	public static Pawn pawnCorner2White;
	public static Pawn pawnSide1Black;
	public static Pawn pawnSide3Black;
	public static Pawn pawnSide2Black;
	public static Pawn pawnSide2White;
	public static Pawn pawnMiddleWhite;
	
	/**
	 * This method is ran before every test. It creates a new board, and puts Pawns in 
	 * specific spaces for testing purposes.
	 */
	@Before
	public void setUpClass() throws Exception {
		game = new Game();
		game.board.addPiece(new Pawn(PieceColor.BLACK, 0, 0));
		game.board.addPiece(new Pawn(PieceColor.WHITE, 0, 7));
		game.board.addPiece(new Pawn(PieceColor.BLACK, 7, 0));
		game.board.addPiece(new Pawn(PieceColor.WHITE, 7, 7));
		game.board.addPiece(new Pawn(PieceColor.BLACK, 0, 3));
		game.board.addPiece(new Pawn(PieceColor.BLACK, 3, 0));
		game.board.addPiece(new Pawn(PieceColor.BLACK, 7, 3));
		game.board.addPiece(new Pawn(PieceColor.WHITE, 3, 7));
		game.board.addPiece(new Pawn(PieceColor.WHITE, 4, 4));
		pawnCorner1Black = (Pawn) game.board.getPiece(0, 0);
		pawnCorner1White = (Pawn) game.board.getPiece(0, 7);
		pawnCorner2Black = (Pawn) game.board.getPiece(7, 0);
		pawnCorner2White = (Pawn) game.board.getPiece(7, 7);
		pawnSide1Black = (Pawn) game.board.getPiece(0, 3);
		pawnSide3Black = (Pawn) game.board.getPiece(3, 0);
		pawnSide2Black = (Pawn) game.board.getPiece(7, 3);
		pawnSide2White = (Pawn) game.board.getPiece(3, 7);
		pawnMiddleWhite = (Pawn) game.board.getPiece(4, 4);
	}
	
	/**
	 * Moves the Pawn to several empty spaces.
	 */
	@Test
	public void moveToValidSpace() throws Exception {
		game.board.movePiece(pawnCorner1Black, 0, 2);
		game.board.movePiece(pawnCorner2White, 7, 5);
		game.board.movePiece(pawnSide1Black, 0, 4);
		game.board.movePiece(pawnSide2White, 3, 6);
		game.board.movePiece(pawnMiddleWhite, 4, 3);
		assertEquals(pawnCorner1Black, game.board.getPiece(0, 2));
		assertEquals(pawnCorner2White, game.board.getPiece(7, 5));
		assertEquals(pawnSide1Black, game.board.getPiece(0, 4));
		assertEquals(pawnSide2White, game.board.getPiece(3, 6));
		assertEquals(pawnMiddleWhite, game.board.getPiece(4, 3));
	}
	
	/**
	 * Tries to move Pawns to spaces occupied by pieces of the same color.
	 */
	@Test
	public void moveToOccupiedSpace() throws Exception {
		King obstruction1 = new King(PieceColor.WHITE, 0, 6);
		King obstruction2 = new King(PieceColor.WHITE, 7, 1);
		Pawn obstruction3 = new Pawn(PieceColor.BLACK, 3, 2);
		Pawn obstruction4 = new Pawn(PieceColor.BLACK, 7, 2);
		game.board.addPiece(obstruction1);
		game.board.addPiece(obstruction2);
		game.board.addPiece(obstruction3);
		game.board.addPiece(obstruction4);
		
		game.board.movePiece(pawnCorner1White, 0, 6);
		game.board.movePiece(pawnCorner2Black, 7, 1);
		game.board.movePiece(pawnSide3Black, 3, 2);
		game.board.movePiece(pawnSide2Black, 7, 2);
		assertEquals(pawnCorner1White, game.board.getPiece(0, 7));
		assertEquals(pawnCorner2Black, game.board.getPiece(7, 0));
		assertEquals(pawnSide3Black, game.board.getPiece(3, 0));
		assertEquals(pawnSide2Black, game.board.getPiece(7, 3));
	}
	
	/**
	 * Tries to move Pawns to spaces it shouldn't be able to move to, such as
	 * out-of-bounds and spaces that are outside of its movement patterns. This also
	 * tests if Pawns can jump two spaces if it's blocked and if it used up its first turn.
	 */
	@Test
	public void invalidMove() throws Exception {
		game.board.movePiece(pawnCorner1Black, 0, 5);
		game.board.movePiece(pawnCorner2White, 5, 7);
		game.board.movePiece(pawnSide1Black, 0, -1);
		game.board.movePiece(pawnSide1Black, 4, 6);
		game.board.movePiece(pawnMiddleWhite, 10, 4);
		assertEquals(pawnCorner1Black, game.board.getPiece(0, 0));
		assertEquals(pawnCorner2White, game.board.getPiece(7, 7));
		assertEquals(pawnSide1Black, game.board.getPiece(0, 3));
		assertEquals(pawnSide2White, game.board.getPiece(3, 7));
		assertEquals(pawnMiddleWhite, game.board.getPiece(4, 4));
		game.board.movePiece(pawnCorner1Black, 0, 2);
		game.board.movePiece(pawnCorner1Black, 0, 4);
		game.board.movePiece(pawnSide2White, 3, 6);
		game.board.movePiece(pawnSide2White, 3, 4);
		assertEquals(pawnCorner1Black, game.board.getPiece(0, 2));
		assertEquals(pawnSide2White, game.board.getPiece(3, 6));
		assertNull(game.board.getPiece(0, 4));
		assertNull(game.board.getPiece(3, 4));
	}
	
	/**
	 * Pawns try to capture other pieces of various types and of opposite color.
	 */
	@Test
	public void captureOtherPiece() throws Exception {
		Rook captureTarget1 = new Rook(PieceColor.BLACK, 1, 6);
		Pawn captureTarget2 = new Pawn(PieceColor.WHITE, 6, 1);
		Rook captureTarget3 = new Rook(PieceColor.WHITE, 4, 1);
		Queen captureTarget4 = new Queen(PieceColor.WHITE, 6, 4);
		game.board.addPiece(captureTarget1);
		game.board.addPiece(captureTarget2);
		game.board.addPiece(captureTarget3);
		game.board.addPiece(captureTarget4);
		
		game.board.movePiece(pawnCorner1White, 1, 6);
		game.board.movePiece(pawnCorner2Black, 6, 1);
		game.board.movePiece(pawnSide3Black, 4, 1);
		game.board.movePiece(pawnSide2Black, 6, 4);
		assertEquals(pawnCorner1White, game.board.getPiece(1, 6));
		assertEquals(pawnCorner2Black, game.board.getPiece(6, 1));
		assertEquals(pawnSide3Black, game.board.getPiece(4, 1));
		assertEquals(pawnSide2Black, game.board.getPiece(6, 4));
		assertFalse(captureTarget1.isAlive());
		assertFalse(captureTarget2.isAlive());
		assertFalse(captureTarget3.isAlive());
		assertFalse(captureTarget4.isAlive());
	}
}
