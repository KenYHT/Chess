package chess.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import chess.Game;
import chess.enums.PieceColor;
import chess.pieces.Bishop;
import chess.pieces.Pawn;
import chess.pieces.HighTemplar;

public class HighTemplarTest {

	public static Game game;
	public static HighTemplar highTemplarCorner1;
	public static HighTemplar highTemplarCorner2;
	public static HighTemplar highTemplarMiddle;
	
	/**
	 * This method is ran before every test. It creates a new game.board., and puts HighTemplars in 
	 * specific spaces for testing purposes.
	 */
	@Before
	public void setUpClass() throws Exception {
		game = new Game();
		game.board.addPiece(new HighTemplar(PieceColor.WHITE, 0, 0));
		game.board.addPiece(new HighTemplar(PieceColor.WHITE, 0, 7));
		game.board.addPiece(new HighTemplar(PieceColor.WHITE, 4, 4));
		highTemplarCorner1 = (HighTemplar) game.board.getPiece(0, 0);
		highTemplarCorner2 = (HighTemplar) game.board.getPiece(0, 7);
		highTemplarMiddle = (HighTemplar) game.board.getPiece(4, 4);
	}
	
	/**
	 * Moves the HighTemplar to several empty spaces.
	 */
	@Test
	public void moveToValidSpace() throws Exception {
		game.board.movePiece(highTemplarCorner2, 1, 6); // highTemplarCorner2
		assertEquals(highTemplarCorner2, game.board.getPiece(1, 6));
	}
	
	/**
	 * Tries to move HighTemplars to spaces occupied by pieces of the same color.
	 */
	@Test
	public void moveToOccupiedSpace() throws Exception {
		Pawn obstruction = new Pawn(PieceColor.WHITE, 1, 1);
		game.board.addPiece(obstruction);
		game.board.movePiece(highTemplarCorner1, 1, 1);
		assertEquals(highTemplarCorner1, game.board.getPiece(0,  0));
	}
	
	/**
	 * Tries to move a HighTemplar to spaces it shouldn't be able to move to.
	 */
	@Test
	public void moveToInvalidSpace() throws Exception {
		game.board.movePiece(highTemplarMiddle, 3, 2);
		assertEquals(highTemplarMiddle, game.board.getPiece(4, 4));
	}
	
	/**
	 * HighTemplars try to capture other pieces of various types and of opposite color.
	 * It also tries to cast its spell on two enemy units
	 */
	@Test
	public void captureOtherPiece() throws Exception {
		Bishop captureTarget1 = new Bishop(PieceColor.BLACK, 0, 1);
		Pawn captureTarget2 = new Pawn(PieceColor.BLACK, 0, 0);
		Bishop captureTarget3 = new Bishop(PieceColor.BLACK, 3, 4);
		game.board.addPiece(captureTarget1);
		game.board.addPiece(captureTarget2);
		game.board.addPiece(captureTarget3);
		
		game.board.movePiece(highTemplarMiddle, 3, 4); // highTemplarMiddle
		game.board.movePiece(highTemplarMiddle, 2, 4);
		game.board.movePiece(highTemplarMiddle, 1, 4);
		game.board.movePiece(highTemplarMiddle, 0, 4);
		game.board.movePiece(highTemplarMiddle, 0, 1);
		assertEquals(highTemplarMiddle, game.board.getPiece(0, 4));
		assertFalse(captureTarget3.isAlive());
		assertFalse(captureTarget1.isAlive());
		assertFalse(captureTarget2.isAlive());
	}
}
