package chess.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import chess.enums.*;
import chess.Game;
import chess.pieces.*;

public class QueenTest {
	
	public static Game game;
	public static Queen queenCorner1;
	public static Queen queenCorner2;
	public static Queen queenCorner3;
	public static Queen queenCorner4;
	public static Queen queenSide1;
	public static Queen queenSide2;
	public static Queen queenSide3;
	public static Queen queenSide4;
	public static Queen queenMiddle;
	
	/**
	 * This method is ran before every test. It creates a new board, and puts Queens in 
	 * specific spaces for testing purposes.
	 */
	@Before
	public void setUpClass() throws Exception {
		game = new Game();
		game.board.addPiece(new Queen(PieceColor.WHITE, 0, 0));
		game.board.addPiece(new Queen(PieceColor.WHITE, 0, 7));
		game.board.addPiece(new Queen(PieceColor.WHITE, 7, 0));
		game.board.addPiece(new Queen(PieceColor.WHITE, 7, 7));
		game.board.addPiece(new Queen(PieceColor.WHITE, 0, 3));
		game.board.addPiece(new Queen(PieceColor.WHITE, 3, 0));
		game.board.addPiece(new Queen(PieceColor.WHITE, 7, 3));
		game.board.addPiece(new Queen(PieceColor.WHITE, 3, 7));
		game.board.addPiece(new Queen(PieceColor.WHITE, 4, 4));
		queenCorner1 = (Queen) game.board.getPiece(0, 0);
		queenCorner2 = (Queen) game.board.getPiece(0, 7);
		queenCorner3 = (Queen) game.board.getPiece(7, 0);
		queenCorner4 = (Queen) game.board.getPiece(7, 7);
		queenSide1 = (Queen) game.board.getPiece(0, 3);
		queenSide2 = (Queen) game.board.getPiece(3, 0);
		queenSide3 = (Queen) game.board.getPiece(7, 3);
		queenSide4 = (Queen) game.board.getPiece(3, 7);
		queenMiddle = (Queen) game.board.getPiece(4, 4);
	}
	
	/**
	 * Moves the Queen to several empty spaces.
	 */
	@Test
	public void moveToValidSpace() throws Exception {
		game.board.movePiece(queenCorner1, 1, 0); // queenCorner1
		game.board.movePiece(queenCorner2, 0, 4); // queenCorner2
		game.board.movePiece(queenCorner3, 6, 1); // queenCorner3
		game.board.movePiece(queenCorner4, 6, 6); // queenCorner4
		game.board.movePiece(queenSide1, 2, 3); // queenSide1
		game.board.movePiece(queenSide2, 6, 3); // queenSide2
		game.board.movePiece(queenSide3, 4, 0); // queenSide3
		game.board.movePiece(queenSide4, 6, 7); // queenSide4
		assertEquals(queenCorner1, game.board.getPiece(1, 0));
		assertEquals(queenCorner2, game.board.getPiece(0, 4));
		assertEquals(queenCorner3, game.board.getPiece(6, 1));
		assertEquals(queenCorner4, game.board.getPiece(6, 6));
		assertEquals(queenSide1, game.board.getPiece(2, 3));
		assertEquals(queenSide2, game.board.getPiece(6, 3));
		assertEquals(queenSide3, game.board.getPiece(4, 0));
		assertEquals(queenSide4, game.board.getPiece(6, 7));
	}
	
	/**
	 * Tries to move Queens to spaces occupied by pieces of the same color.
	 */
	@Test
	public void moveToOccupiedSpace() throws Exception {
		game.board.movePiece(queenCorner1, 0, 7); // queenCorner1
		game.board.movePiece(queenSide2, 0, 0); // queenSide1
		game.board.movePiece(queenSide3, 7, 0); // queenSide3
		game.board.movePiece(queenSide4, 3, 0); // queenSide4
		game.board.movePiece(queenCorner1, 4, 4); // queenCorner1
		game.board.movePiece(queenSide1, 3, 0); // queenSide1
		game.board.movePiece(queenSide2, 0, 3); // queenSide2
		assertEquals(queenCorner1, game.board.getPiece(0,  0));
		assertEquals(queenSide2, game.board.getPiece(3, 0));
		assertEquals(queenSide3, game.board.getPiece(7, 3));
		assertEquals(queenSide4, game.board.getPiece(3, 7));
		assertEquals(queenCorner1, game.board.getPiece(0,  0));
		assertEquals(queenSide1, game.board.getPiece(0, 3));
		assertEquals(queenSide2, game.board.getPiece(3, 0));
	}
	
	/**
	 * Tries to move Queen to spaces it shouldn't be able to move to, such as
	 * out-of-bounds and spaces that are outside of its movement patterns.
	 */
	@Test
	public void moveToInvalidSpace() throws Exception {
		game.board.movePiece(queenSide1, -1, 3); // queenSide1
		game.board.movePiece(queenSide2, 12, 3); // queenSide2
		game.board.movePiece(queenSide4, 3, 10); // queenSide4
		game.board.movePiece(queenMiddle, 3, 2); // queenMiddle
		game.board.movePiece(queenCorner4, 8, 8); // queenCorner4
		game.board.movePiece(queenSide3, 8, 2); // queenSide3
		game.board.movePiece(queenSide4, -1, 3); //queenSide4
		assertEquals(queenSide1, game.board.getPiece(0, 3));
		assertEquals(queenSide2, game.board.getPiece(3, 0));
		assertEquals(queenSide4, game.board.getPiece(3, 7));
		assertEquals(queenMiddle, game.board.getPiece(4, 4));
		assertEquals(queenCorner4, game.board.getPiece(7, 7));
		assertEquals(queenSide3, game.board.getPiece(7, 3));
		assertEquals(queenSide4, game.board.getPiece(3, 7));
	}
	
	/**
	 * Queens try to capture other pieces of various types and of opposite color.
	 */
	@Test
	public void captureOtherPiece() throws Exception {
		Knight captureTarget1 = new Knight(PieceColor.BLACK, 1, 0); 
		Pawn captureTarget2 = new Pawn(PieceColor.BLACK, 0, 4);
		Rook captureTarget3 = new Rook(PieceColor.BLACK, 4, 3);
		Bishop captureTarget4 = new Bishop(PieceColor.BLACK, 7, 2);
		Rook captureTarget5 = new Rook(PieceColor.BLACK, 1, 1);
		Pawn captureTarget6 = new Pawn(PieceColor.BLACK, 4, 1);
		Rook captureTarget7 = new Rook(PieceColor.BLACK, 5, 5);
		game.board.addPiece(captureTarget1);
		game.board.addPiece(captureTarget2);
		game.board.addPiece(captureTarget3);
		game.board.addPiece(captureTarget4);
		game.board.addPiece(captureTarget5);
		game.board.addPiece(captureTarget6);
		game.board.addPiece(captureTarget7);
		
		game.board.movePiece(queenCorner1, 1, 0); // queenCorner1
		game.board.movePiece(queenSide1, 0, 4); // queenSide1
		game.board.movePiece(queenSide3, 4, 3); // queenSide3
		game.board.movePiece(queenCorner3, 7, 2); // queenCorner3
		game.board.movePiece(queenMiddle, 1, 1); // queenMiddle
		game.board.movePiece(queenSide2, 4, 1); // queenSide2
		game.board.movePiece(queenCorner4, 5, 5); // queenCorner4
		assertEquals(queenCorner1, game.board.getPiece(1, 0));
		assertEquals(queenSide1, game.board.getPiece(0, 4));
		assertEquals(queenSide3, game.board.getPiece(4, 3));
		assertEquals(queenCorner3, game.board.getPiece(7, 2));
		assertEquals(queenMiddle, game.board.getPiece(1, 1));
		assertEquals(queenSide2, game.board.getPiece(4, 1));
		assertEquals(queenCorner4, game.board.getPiece(5, 5));
		assertFalse(captureTarget1.isAlive());
		assertFalse(captureTarget2.isAlive());
		assertFalse(captureTarget3.isAlive());
		assertFalse(captureTarget4.isAlive());
		assertFalse(captureTarget5.isAlive());
		assertFalse(captureTarget6.isAlive());
		assertFalse(captureTarget7.isAlive());
		assertFalse(captureTarget4.isAlive());
	}
}
