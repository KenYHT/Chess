package chess.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import chess.enums.*;
import chess.*;
import chess.pieces.*;

public class BishopTest {
	
	public static Game game;
	public static Bishop bishopCorner1;
	public static Bishop bishopCorner2;
	public static Bishop bishopCorner3;
	public static Bishop bishopCorner4;
	public static Bishop bishopSide1;
	public static Bishop bishopSide2;
	public static Bishop bishopSide3;
	public static Bishop bishopSide4;
	public static Bishop bishopMiddle;
	
	/**
	 * This method is ran before every test. It creates a new board, and puts Bishops in 
	 * specific spaces for testing purposes.
	 */
	@Before
	public void setUpClass() throws Exception {
		game = new Game();
		game.board.addPiece(new Bishop(PieceColor.WHITE, 0, 0));
		game.board.addPiece(new Bishop(PieceColor.WHITE, 0, 7));
		game.board.addPiece(new Bishop(PieceColor.WHITE, 7, 0));
		game.board.addPiece(new Bishop(PieceColor.WHITE, 7, 7));
		game.board.addPiece(new Bishop(PieceColor.WHITE, 0, 3));
		game.board.addPiece(new Bishop(PieceColor.WHITE, 3, 0));
		game.board.addPiece(new Bishop(PieceColor.WHITE, 7, 3));
		game.board.addPiece(new Bishop(PieceColor.WHITE, 3, 7));
		game.board.addPiece(new Bishop(PieceColor.WHITE, 4, 4));
		bishopCorner1 = (Bishop) game.board.getPiece(0, 0);
		bishopCorner2 = (Bishop) game.board.getPiece(0, 7);
		bishopCorner3 = (Bishop) game.board.getPiece(7, 0);
		bishopCorner4 = (Bishop) game.board.getPiece(7, 7);
		bishopSide1 = (Bishop) game.board.getPiece(0, 3);
		bishopSide2 = (Bishop) game.board.getPiece(3, 0);
		bishopSide3 = (Bishop) game.board.getPiece(7, 3);
		bishopSide4 = (Bishop) game.board.getPiece(3, 7);
		bishopMiddle = (Bishop) game.board.getPiece(4, 4);
	}
	
	/**
	 * Moves the Bishop to several empty spaces.
	 */
	@Test
	public void moveToValidSpace() throws Exception {
		game.board.movePiece(bishopCorner1, 1, 1); // bishopCorner1
		game.board.movePiece(bishopCorner2, 1, 6); // bishopCorner2
		game.board.movePiece(bishopCorner3, 6, 1); // bishopCorner3
		game.board.movePiece(bishopCorner4, 6, 6); // bishopCorner4
		game.board.movePiece(bishopSide1, 2, 1); // bishopSide1
		game.board.movePiece(bishopSide2, 6, 3); // bishopSide2
		game.board.movePiece(bishopSide3, 4, 0); // bishopSide3
		game.board.movePiece(bishopSide4, 1, 5); // bishopSide4
		assertEquals(bishopCorner1, game.board.getPiece(1, 1));
		assertEquals(bishopCorner2, game.board.getPiece(1, 6));
		assertEquals(bishopCorner3, game.board.getPiece(6, 1));
		assertEquals(bishopCorner4, game.board.getPiece(6, 6));
		assertEquals(bishopSide1, game.board.getPiece(2, 1));
		assertEquals(bishopSide2, game.board.getPiece(6, 3));
		assertEquals(bishopSide3, game.board.getPiece(4, 0));
		assertEquals(bishopSide4, game.board.getPiece(1, 5));
	}
	
	/**
	 * Tries to move Bishops to spaces occupied by pieces of the same color.
	 */
	@Test
	public void moveToOccupiedSpace() throws Exception {
		game.board.movePiece(bishopCorner1, 4, 4);
		game.board.movePiece(bishopSide2, 0, 3);
		game.board.movePiece(bishopSide1, 3, 0);
		assertEquals(bishopCorner1, game.board.getPiece(0,  0));
		assertEquals(bishopSide2, game.board.getPiece(3, 0));
		assertEquals(bishopSide1, game.board.getPiece(0, 3));
	}
	
	/**
	 * Tries to move Bishops to spaces it shouldn't be able to move to, such as
	 * out-of-bounds and spaces that are outside of its movement patterns.
	 */
	@Test
	public void moveToInvalidSpace() throws Exception {
		game.board.movePiece(bishopCorner4, 8, 8); // bishopCorner4
		game.board.movePiece(bishopSide3, 8, 2); // bishopSide3
		game.board.movePiece(bishopSide4, -1, 3); //bishopSide4
		assertEquals(bishopCorner4, game.board.getPiece(7, 7));
		assertEquals(bishopSide3, game.board.getPiece(7, 3));
		assertEquals(bishopSide4, game.board.getPiece(3, 7));
	}
	
	/**
	 * Bishops try to capture other pieces of various types and of opposite color.
	 */
	@Test
	public void captureOtherPiece() throws Exception {
		Rook captureTarget1 = new Rook(PieceColor.BLACK, 1, 1);
		Pawn captureTarget2 = new Pawn(PieceColor.BLACK, 4, 1);
		Rook captureTarget3 = new Rook(PieceColor.BLACK, 5, 5);
		Queen captureTarget4 = new Queen(PieceColor.BLACK, 2, 5);
		game.board.addPiece(captureTarget1);
		game.board.addPiece(captureTarget2);
		game.board.addPiece(captureTarget3);
		game.board.addPiece(captureTarget4);
		
		game.board.movePiece(bishopCorner1, 1, 1); // bishopCorner1
		game.board.movePiece(bishopSide2, 4, 1); // bishopSide2
		game.board.movePiece(bishopMiddle, 5, 5); // bishopMiddle
		game.board.movePiece(bishopSide1, 2, 5); // bishopSide1
		assertEquals(bishopCorner1, game.board.getPiece(1, 1));
		assertEquals(bishopSide2, game.board.getPiece(4, 1));
		assertEquals(bishopMiddle, game.board.getPiece(5, 5));
		assertEquals(bishopSide1, game.board.getPiece(2, 5));
		assertFalse(captureTarget1.isAlive());
		assertFalse(captureTarget2.isAlive());
		assertFalse(captureTarget3.isAlive());
		assertFalse(captureTarget4.isAlive());
	}
}
