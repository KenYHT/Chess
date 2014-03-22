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

public class KnightTest {
	public static Game game;
	public static Knight knightCorner1Black;
	public static Knight knightCorner1White;
	public static Knight knightCorner2Black;
	public static Knight knightCorner2White;
	public static Knight knightSide1Black;
	public static Knight knightSide1White;
	public static Knight knightSide2Black;
	public static Knight knightSide2White;
	public static Knight knightMiddleWhite;
	
	/**
	 * This method is ran before every test. It creates a new board, and puts Knights in 
	 * specific spaces for testing purposes.
	 */
	@Before
	public void setUpClass() throws Exception {
		game = new Game();
		game.board.addPiece(new Knight(PieceColor.BLACK, 0, 0));
		game.board.addPiece(new Knight(PieceColor.WHITE, 0, 7));
		game.board.addPiece(new Knight(PieceColor.BLACK, 7, 0));
		game.board.addPiece(new Knight(PieceColor.WHITE, 7, 7));
		game.board.addPiece(new Knight(PieceColor.BLACK, 0, 3));
		game.board.addPiece(new Knight(PieceColor.WHITE, 3, 0));
		game.board.addPiece(new Knight(PieceColor.BLACK, 7, 3));
		game.board.addPiece(new Knight(PieceColor.WHITE, 3, 7));
		game.board.addPiece(new Knight(PieceColor.WHITE, 4, 4));
		knightCorner1Black = (Knight) game.board.getPiece(0, 0);
		knightCorner1White = (Knight) game.board.getPiece(0, 7);
		knightCorner2Black = (Knight) game.board.getPiece(7, 0);
		knightCorner2White = (Knight) game.board.getPiece(7, 7);
		knightSide1Black = (Knight) game.board.getPiece(0, 3);
		knightSide1White = (Knight) game.board.getPiece(3, 0);
		knightSide2Black = (Knight) game.board.getPiece(7, 3);
		knightSide2White = (Knight) game.board.getPiece(3, 7);
		knightMiddleWhite = (Knight) game.board.getPiece(4, 4);
	}
	
	/**
	 * Moves the Knight to several empty spaces
	 */
	@Test
	public void moveToValidSpace() throws Exception {
		game.board.movePiece(knightCorner1Black, 1, 2);
		game.board.movePiece(knightCorner2White, 5, 6);
		game.board.movePiece(knightSide1Black, 2, 2);
		game.board.movePiece(knightSide2White, 4, 5);
		game.board.movePiece(knightMiddleWhite, 5, 2);
		assertEquals(knightCorner1Black, game.board.getPiece(1, 2));
		assertEquals(knightCorner2White, game.board.getPiece(5, 6));
		assertEquals(knightSide1Black, game.board.getPiece(2, 2));
		assertEquals(knightSide2White, game.board.getPiece(4, 5));
		assertEquals(knightMiddleWhite, game.board.getPiece(5, 2));
	}
	
	/**
	 * Tries to move Knights to spaces occupied by pieces of the same color.
	 */
	@Test
	public void moveToOccupiedSpace() throws Exception {
		King obstruction1 = new King(PieceColor.WHITE, 1, 5);
		King obstruction2 = new King(PieceColor.BLACK, 5, 1);
		Rook obstruction3 = new Rook(PieceColor.WHITE, 1, 1);
		Queen obstruction4 = new Queen(PieceColor.BLACK, 6, 5);
		game.board.addPiece(obstruction1);
		game.board.addPiece(obstruction2);
		game.board.addPiece(obstruction3);
		game.board.addPiece(obstruction4);
		
		game.board.movePiece(knightCorner1White, 1, 5);
		game.board.movePiece(knightCorner2Black, 5, 1);
		game.board.movePiece(knightSide1White, 1, 1);
		game.board.movePiece(knightSide2Black, 6, 5);
		assertEquals(knightCorner1White, game.board.getPiece(0, 7));
		assertEquals(knightCorner2Black, game.board.getPiece(7, 0));
		assertEquals(knightSide1White, game.board.getPiece(3, 0));
		assertEquals(knightSide2Black, game.board.getPiece(7, 3));
	}
	
	/**
	 * Tries to move Pawns to spaces it shouldn't be able to move to, such as
	 * out-of-bounds and spaces that are outside of its movement patterns.
	 */
	@Test
	public void invalidMove() throws Exception {
		game.board.movePiece(knightCorner1Black, -1, 2);
		game.board.movePiece(knightCorner2White, 4, 7);
		game.board.movePiece(knightSide1Black, 1, -1);
		game.board.movePiece(knightSide2White, 4, 6);
		game.board.movePiece(knightMiddleWhite, 10, 4);
		assertEquals(knightCorner1Black, game.board.getPiece(0, 0));
		assertEquals(knightCorner2White, game.board.getPiece(7, 7));
		assertEquals(knightSide1Black, game.board.getPiece(0, 3));
		assertEquals(knightSide2White, game.board.getPiece(3, 7));
		assertEquals(knightMiddleWhite, game.board.getPiece(4, 4));
	}
	
	/**
	 * Knights try to capture other pieces of various types and of opposite color.
	 */
	@Test
	public void captureOtherPiece() throws Exception {
		Rook captureTarget1 = new Rook(PieceColor.BLACK, 1, 5);
		Knight captureTarget2 = new Knight(PieceColor.WHITE, 6, 2);
		Rook captureTarget3 = new Rook(PieceColor.BLACK, 4, 2);
		Queen captureTarget4 = new Queen(PieceColor.WHITE, 5, 4);
		game.board.addPiece(captureTarget1);
		game.board.addPiece(captureTarget2);
		game.board.addPiece(captureTarget3);
		game.board.addPiece(captureTarget4);
		
		game.board.movePiece(knightCorner1White, 1, 5);
		game.board.movePiece(knightCorner2Black, 6, 2);
		game.board.movePiece(knightSide1White, 4, 2);
		game.board.movePiece(knightSide2Black, 5, 4);
		assertEquals(knightCorner1White, game.board.getPiece(1, 5));
		assertEquals(knightCorner2Black, game.board.getPiece(6, 2));
		assertEquals(knightSide1White, game.board.getPiece(4, 2));
		assertEquals(knightSide2Black, game.board.getPiece(5, 4));
		assertFalse(captureTarget1.isAlive());
		assertFalse(captureTarget2.isAlive());
		assertFalse(captureTarget3.isAlive());
		assertFalse(captureTarget4.isAlive());
	}
}
