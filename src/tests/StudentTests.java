package tests;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import model.BoardCell;
import model.ClearCellGame;
import model.Game;

/* The following directive executes tests in sorted order */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class StudentTests {
	
	/* Remove the following test and add your tests */
	private static String getBoardStr(Game game) {
		int maxRows = game.getMaxRows(), maxCols = game.getMaxCols();

		String answer = "Board(Rows: " + maxRows + ", Columns: " + maxCols + ")\n";
		for (int row = 0; row < maxRows; row++) {
			for (int col = 0; col < maxCols; col++) {
				answer += game.getBoardCell(row, col).getName();
			}
			answer += "\n";
		}

		return answer;
	}
	@Test
	public void test1() {
		int maxRows = 8, maxCols = 8, strategy = 1;
		Game ccGame = new ClearCellGame(maxRows, maxCols, new Random(1L), strategy);
		ccGame.setBoardWithColor(BoardCell.BLUE);
		ccGame.setRowWithColor(maxRows - 1, BoardCell.EMPTY);
		ccGame.setRowWithColor(1, BoardCell.YELLOW);
		ccGame.setBoardCell(1, maxCols - 1, BoardCell.RED);
		ccGame.setRowWithColor(3, BoardCell.GREEN);
		ccGame.setRowWithColor(6, BoardCell.RED);
		/*String answer = "Before processCell\n\n";
		answer += getBoardStr(ccGame);
		ccGame.processCell(4, 2);
		answer += "\nAfter processCell\n";*/
		
		String answer = "Board(Rows: 8, Columns: 8)\n"
				+ "BBBBBBBB\n"
				+ "YYYYYYYR\n"
				+ "BBBBBBBB\n"
				+ "GGGGGGGG\n"
				+ "BBBBBBBB\n"
				+ "BBBBBBBB\n"
				+ "RRRRRRRR\n"
				+ "........\n";
		assertTrue(getBoardStr(ccGame).equals(answer));
	}
	@Test
	public void test2() {
		int maxRows = 4, maxCols = 5, strategy = 1;
		Game ccGame = new ClearCellGame(maxRows, maxCols, new Random(1L), strategy);
		ccGame.nextAnimationStep();
		ccGame.nextAnimationStep();

		String answer = "Board(Rows: 4, Columns: 5)\n"
				+ "RRGYY\n"
				+ "RYBYR\n"
				+ ".....\n"
				+ ".....\n";
		System.out.println(answer);
		assertTrue(getBoardStr(ccGame).equals(answer));
	}
}
