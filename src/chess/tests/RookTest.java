package chess.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import chess.enums.*;
import chess.Game;
import chess.pieces.*;

public class RookTest {
	
	public static Game game;
	public static Rook rookCorner1;
	public static Rook rookCorner2;
	public static Rook rookCorner3;
	public static Rook rookCorner4;
	public static Rook rookSide1;
	public static Rook rookSide2;
	public static Rook rookSide3;
	public static Rook rookSide4;
	public static Rook rookMiddle;
	
	/**
	 * This method is ran before every test. It creates a new board, and puts Rooks in 
	 * specific spaces for testing purposes.
	 */
	@Before
	public void setUpClass() throws Exception {
		game = new Game();
		game.board.addPiece(new Rook(PieceColor.WHITE, 0, 0));
		game.board.addPiece(new Rook(PieceColor.WHITE, 0, 7));
		game.board.addPiece(new Rook(PieceColor.WHITE, 7, 0));
		game.board.addPiece(new Rook(PieceColor.WHITE, 7, 7));
		game.board.addPiece(new Rook(PieceColor.WHITE, 0, 3));
		game.board.addPiece(new Rook(PieceColor.WHITE, 3, 0));
		game.board.addPiece(new Rook(PieceColor.WHITE, 7, 3));
		game.board.addPiece(new Rook(PieceColor.WHITE, 3, 7));
		game.board.addPiece(new Rook(PieceColor.WHITE, 4, 4));
		rookCorner1 = (Rook) game.board.getPiece(0, 0);
		rookCorner2 = (Rook) game.board.getPiece(0, 7);
		rookCorner3 = (Rook) game.board.getPiece(7, 0);
		rookCorner4 = (Rook) game.board.getPiece(7, 7);
		rookSide1 = (Rook) game.board.getPiece(0, 3);
		rookSide2 = (Rook) game.board.getPiece(3, 0);
		rookSide3 = (Rook) game.board.getPiece(7, 3);
		rookSide4 = (Rook) game.board.getPiece(3, 7);
		rookMiddle = (Rook) game.board.getPiece(4, 4);
	}
	
	/**
	 * Moves the Rook to several empty spaces.
	 */
	@Test
	public void moveToValidSpace() throws Exception {
		game.board.movePiece(rookCorner1, 1, 0); // rookCorner1
		game.board.movePiece(rookCorner2, 0, 4); // rookCorner2
		game.board.movePiece(rookSide1, 2, 3); // rookSide1
		game.board.movePiece(rookSide4, 6, 7); // rookSide4
		assertEquals(rookCorner1, game.board.getPiece(1, 0));
		assertEquals(game.board.getPiece(0, 0), null);
		assertEquals(rookCorner2, game.board.getPiece(0, 4));
		assertEquals(game.board.getPiece(0, 7), null);
		assertEquals(rookSide1, game.board.getPiece(2, 3));
		assertEquals(rookSide4, game.board.getPiece(6, 7));
	}
	
	/**
	 * Tries to move Rooks to spaces occupied by pieces of the same color.
	 */
	@Test
	public void moveToOccupiedSpace() throws Exception {
		game.board.movePiece(rookCorner1, 0, 7);
		game.board.movePiece(rookSide2, 0, 0);
		game.board.movePiece(rookSide3, 7, 0);
		game.board.movePiece(rookSide4, 3, 0);
		assertEquals(rookCorner1, game.board.getPiece(0,  0));
		assertEquals(rookSide2, game.board.getPiece(3, 0));
		assertEquals(rookSide3, game.board.getPiece(7, 3));
		assertEquals(rookSide4, game.board.getPiece(3, 7));
	}
	
	/**
	 * Tries to move Rooks to spaces it shouldn't be able to move to, such as
	 * out-of-bounds and spaces that are outside of its movement patterns.
	 */
	@Test
	public void moveToInvalidSpace() throws Exception {
		game.board.movePiece(rookSide1, -1, 3);
		game.board.movePiece(rookSide2, 12, 3);
		game.board.movePiece(rookSide4, 3, 10);
		game.board.movePiece(rookMiddle, 3, 2);
		assertEquals(rookSide1, game.board.getPiece(0, 3));
		assertEquals(rookSide2, game.board.getPiece(3, 0));
		assertEquals(rookSide4, game.board.getPiece(3, 7));
		assertEquals(rookMiddle, game.board.getPiece(4, 4));
	}
	
	/**
	 * Rooks try to capture other pieces of various types and of opposite color.
	 */
	@Test
	public void captureOtherPiece() throws Exception {
		Bishop captureTarget1 = new Bishop(PieceColor.BLACK, 1, 0);
		Pawn captureTarget2 = new Pawn(PieceColor.BLACK, 0, 4);
		Bishop captureTarget3 = new Bishop(PieceColor.BLACK, 4, 3);
		Queen captureTarget4 = new Queen(PieceColor.BLACK, 7, 2);
		game.board.addPiece(captureTarget1);
		game.board.addPiece(captureTarget2);
		game.board.addPiece(captureTarget3);
		game.board.addPiece(captureTarget4);
		
		game.board.movePiece(rookCorner1, 1, 0); // rookCorner1
		game.board.movePiece(rookSide1, 0, 4); // rookSide1
		game.board.movePiece(rookSide3, 4, 3); // rookSide3
		game.board.movePiece(rookCorner3, 7, 2); // rookCorner3
		assertEquals(rookCorner1, game.board.getPiece(1, 0));
		assertEquals(rookSide1, game.board.getPiece(0, 4));
		assertEquals(rookSide3, game.board.getPiece(4, 3));
		assertEquals(rookCorner3, game.board.getPiece(7, 2));
		assertFalse(captureTarget1.isAlive());
		assertFalse(captureTarget2.isAlive());
		assertFalse(captureTarget3.isAlive());
		assertFalse(captureTarget4.isAlive());
	}
}
