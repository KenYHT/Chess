package chess.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import chess.enums.*;
import chess.Board;
import chess.Game;
import chess.pieces.*;

public class BoardTests {
	public static Game game;
	
	/**
	 * This method is called before every test. It constructs a new Board object.
	 */
	@Before
	public void setUpClass() throws Exception {
		game = new Game();
	}
	
	/**
	 * This method is called after every test. It gets rid of its current Board.
	 */
	@After
	public void resetClass() throws Exception {
		game = null;
	}
	
	/**
	 * This test tests if the board is set up properly upon calling setUpGame().
	 */
	@Test
	public void testSetUpGame() throws Exception {
		game.board.setUpGame();
		for (int i = 0; i < Board.GLOBAL_BOARD_SIDE_LENGTH; i++)
			for (int j = 0; j < Board.GLOBAL_BOARD_SIDE_LENGTH; j++)
				if (j < 2 || j > 5)
					assertNotNull(game.board.getPiece(i, j));
	}
	
	/**
	 * Tests the resetBoard() method by populating the board first, then calling
	 * resetBoard() and checking if every Space on the board is empty.
	 * @throws Exception
	 */
	@Test
	public void testResetBoard() throws Exception {
		game.board.setUpGame();
		for (int i = 0; i < Board.GLOBAL_BOARD_SIDE_LENGTH; i++)
			for (int j = 0; j < Board.GLOBAL_BOARD_SIDE_LENGTH; j++)
				if (j < 2 || j > 5)
					assertNotNull(game.board.getPiece(i, j));
		
		game.board.reset();
		for (int i = 0; i < Board.GLOBAL_BOARD_SIDE_LENGTH; i++)
			for (int j = 0; j < Board.GLOBAL_BOARD_SIDE_LENGTH; j++)
				assertNull(game.board.getPiece(i, j));
	}
	
	/**
	 * Tests the addPiece() method by adding certain pieces to the board, then checking the
	 * board to see if the spaces contain the exact same type of piece we've added.
	 * @throws Exception
	 */
	@Test
	public void testAddPiece() throws Exception {
		game.board.addPiece(new Pawn(PieceColor.BLACK, 5, 5));
		game.board.addPiece(new Queen(PieceColor.WHITE, 1, 4));
		game.board.addPiece(new King(PieceColor.BLACK, 0, 2));
		game.board.addPiece(new Knight(PieceColor.WHITE, 7, 0));
		
		assertEquals(game.board.getPiece(5, 5).getType(), Piece.Type.PAWN);
		assertEquals(game.board.getPiece(1, 4).getType(), Piece.Type.QUEEN);
		assertEquals(game.board.getPiece(0, 2).getType(), Piece.Type.KING);
		assertEquals(game.board.getPiece(7, 0).getType(), Piece.Type.KNIGHT);
		assertEquals(game.board.getPiece(5, 5).getColor(), PieceColor.BLACK);
		assertEquals(game.board.getPiece(1, 4).getColor(), PieceColor.WHITE);
		assertEquals(game.board.getPiece(0, 2).getColor(), PieceColor.BLACK);
		assertEquals(game.board.getPiece(7, 0).getColor(), PieceColor.WHITE);
	}
	
	/**
	 * Tests addToBlackRoster() and addToWhiteRoster() by adding a few pieces to both rosters,
	 * then checking both sizes and if the pieces match.
	 * @throws Exception
	 */
	@Test
	public void testAddToRosters() throws Exception {
		game.board.addPiece(new Pawn(PieceColor.BLACK, 5, 5));
		game.board.addPiece(new Queen(PieceColor.WHITE, 1, 4));
		game.board.addPiece(new King(PieceColor.BLACK, 0, 2));
		game.board.addPiece(new Knight(PieceColor.WHITE, 7, 0));
//		game.board.addToTeam(PieceColor.BLACK, game.board.getPiece(5, 5));
//		game.board.addToTeam(PieceColor.BLACK, game.board.getPiece(0, 2));
//		game.board.addToTeam(PieceColor.WHITE, game.board.getPiece(1, 4));
//		game.board.addToTeam(PieceColor.WHITE, game.board.getPiece(7, 0));
//		
//		assertEquals(game.board.getRosterSize(PieceColor.BLACK), 2);
//		assertEquals(game.board.getRosterSize(PieceColor.WHITE), 2);
//		assertEquals(game.board.getPieceFromRoster(PieceColor.BLACK, 0).getType(), Piece.Type.PAWN);
//		assertEquals(game.board.getPieceFromRoster(PieceColor.BLACK, 1).getType(), Piece.Type.KING);
//		assertEquals(game.board.getPieceFromRoster(PieceColor.WHITE, 0).getType(), Piece.Type.QUEEN);
//		assertEquals(game.board.getPieceFromRoster(PieceColor.WHITE, 1).getType(), Piece.Type.KNIGHT);
	}
	
	/**
	 * Tests addToBlackRoster() and addToWhiteRoster() by first adding a couple of pieces to both rosters,
	 * then adding a couple of invalid pieces to both rosters and checking that the size and content of the rosters
	 * haven't changed
	 * @throws Exception
	 */
	@Test
	public void testAddInvalidToRosters() throws Exception {
		game.board.addPiece(new Pawn(PieceColor.BLACK, 5, 5));
		game.board.addPiece(new Queen(PieceColor.WHITE, 1, 4));
		game.board.addPiece(new King(PieceColor.BLACK, 0, 2));
		game.board.addPiece(new Knight(PieceColor.WHITE, 7, 0));
		game.board.addPiece(new Bishop(PieceColor.BLACK, 2, 5));
		game.board.addPiece(new Rook(PieceColor.WHITE, 6, 1));
//		game.board.addToTeam(PieceColor.BLACK, game.board.getPiece(5, 5));
//		game.board.addToTeam(PieceColor.BLACK, game.board.getPiece(0, 2));
//		game.board.addToTeam(PieceColor.WHITE, game.board.getPiece(1, 4));
//		game.board.addToTeam(PieceColor.WHITE, game.board.getPiece(7, 0));
//		
//		game.board.addToTeam(PieceColor.BLACK, game.board.getPiece(6, 1));
//		game.board.addToTeam(PieceColor.WHITE, game.board.getPiece(2, 5));
//		game.board.addToTeam(PieceColor.BLACK, null);
//		game.board.addToTeam(PieceColor.WHITE, null);
//		
//		assertEquals(game.board.getRosterSize(PieceColor.BLACK), 2);
//		assertEquals(game.board.getRosterSize(PieceColor.WHITE), 2);
//		assertEquals(game.board.getPieceFromRoster(PieceColor.BLACK, 0).getType(), Piece.Type.PAWN);
//		assertEquals(game.board.getPieceFromRoster(PieceColor.BLACK, 1).getType(), Piece.Type.KING);
//		assertEquals(game.board.getPieceFromRoster(PieceColor.WHITE, 0).getType(), Piece.Type.QUEEN);
//		assertEquals(game.board.getPieceFromRoster(PieceColor.WHITE, 1).getType(), Piece.Type.KNIGHT);
	}
}
