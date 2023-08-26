package model;

import java.util.Random;

/**
 * This class extends GameModel and implements the logic of the clear cell game.
 * We define an empty cell as BoardCell.EMPTY. An empty row is defined as one
 * where every cell corresponds to BoardCell.EMPTY.
 * 
 * @author Department of Computer Science, UMCP
 */

public class ClearCellGame extends Game {
	Random random;
	int strategy;
	int score=0;
	BoardCell[][] board1=board;

	/**
	 * Defines a board with empty cells. It relies on the super class constructor to
	 * define the board. The random parameter is used for the generation of random
	 * cells. The strategy parameter defines which clearing cell strategy to use
	 * (for this project it will be 1). For fun, you can add your own strategy by
	 * using a value different that one.
	 * 
	 * @param maxRows
	 * @param maxCols
	 * @param random
	 * @param strategy
	 */
	public ClearCellGame(int maxRows, int maxCols, Random random, int strategy) {
		super(maxRows, maxCols);
		this.random=random;
		this.strategy=strategy;
	}

	/**
	 * The game is over when the last board row (row with index board.length -1) is
	 * different from empty row.
	 */
	public boolean isGameOver() {
		for(int i=0;i<maxCols;i++) {
			if(board[board.length-1][i]!=BoardCell.EMPTY) {
				return true;
			}
		}
		return false;
	}

	public int getScore() {
		return score;
	}

	/**
	 * This method will attempt to insert a row of random BoardCell objects if the
	 * last board row (row with index board.length -1) corresponds to the empty row;
	 * otherwise no operation will take place.
	 */
	public void nextAnimationStep() {
		BoardCell[][] board1=new BoardCell[maxRows][maxCols];
		if(isGameOver()!=true) {
			for(int j=0;j<maxCols;j++) {
				board1[0][j]=BoardCell.getNonEmptyRandomBoardCell(random);
			}
			for(int i=1;i<maxRows;i++) {
				for(int j=0;j<maxCols;j++) {
					board1[i][j]=board[i-1][j];
				}
			}
			for(int i=0;i<maxRows;i++) {
				for(int j=0;j<maxCols;j++) {
					board[i][j]=board1[i][j];
				}
			}
		}
	}

	/**
	 * This method will turn to BoardCell.EMPTY the cell selected and any adjacent
	 * surrounding cells in the vertical, horizontal, and diagonal directions that
	 * have the same color. The clearing of adjacent cells will continue as long as
	 * cells have a color that corresponds to the selected cell. Notice that the
	 * clearing process does not clear every single cell that surrounds a cell
	 * selected (only those found in the vertical, horizontal or diagonal
	 * directions).
	 * 
	 * IMPORTANT: Clearing a cell adds one point to the game's score.<br />
	 * <br />
	 * 
	 * If after processing cells, any rows in the board are empty,those rows will
	 * collapse, moving non-empty rows upward. For example, if we have the following
	 * board (an * represents an empty cell):<br />
	 * <br />
	 * RRR<br />
	 * GGG<br />
	 * YYY<br />
	 * * * *<br/>
	 * <br />
	 * then processing each cell of the second row will generate the following
	 * board<br />
	 * <br />
	 * RRR<br />
	 * YYY<br />
	 * * * *<br/>
	 * * * *<br/>
	 * <br />
	 * IMPORTANT: If the game has ended no action will take place.
	 * 
	 * 
	 */
	public void processCell(int rowIndex, int colIndex) {
		BoardCell b1 = board[rowIndex][colIndex];
		board[rowIndex][colIndex] = BoardCell.EMPTY;
		score++;
		for (int i = colIndex + 1; i < maxCols; i++) {
			if (board[rowIndex][i] == b1) {
				board[rowIndex][i] = BoardCell.EMPTY;
				score++;
			} else {
				break;
			}
		}
		for (int i = colIndex - 1; i >= 0; i--) {
			if (board[rowIndex][i] == b1) {
				board[rowIndex][i] = BoardCell.EMPTY;
				score++;
			} else {
				break;
			}
		}
		for (int i = rowIndex + 1; i < maxRows; i++) {
			if (board[i][colIndex] == b1) {
				board[i][colIndex] = BoardCell.EMPTY;
				score++;
			} else {
				break;
			}
		}
		for (int i = rowIndex - 1; i >= 0; i--) {
			if (board[i][colIndex] == b1) {
				board[i][colIndex] = BoardCell.EMPTY;
				score++;
			} else {
				break;
			}
		}
		int check = maxCols > maxRows ? maxCols : maxRows;
		for (int i = 1; i < check; i++) {
			if (rowIndex - i >= 0 && colIndex - i >= 0) {
				if (board[rowIndex - i][colIndex - i] == b1) {
					board[rowIndex - i][colIndex - i] = BoardCell.EMPTY;
					score++;
				} else {
					break;
				}
			} else {
				break;
			}
		}
		for (int i = 1; i < check; i++) {
			if (rowIndex + i < maxRows && colIndex + i < maxCols) {
				if (board[rowIndex + i][colIndex + i] == b1) {
					board[rowIndex + i][colIndex + i] = BoardCell.EMPTY;
					score++;
				} else {
					break;
				}
			} else {
				break;
			}
		}
		for (int i = 1; i < check; i++) {
			if (rowIndex - i >= 0 && colIndex + i < maxCols) {
				if (board[rowIndex - i][colIndex + i] == b1) {
					board[rowIndex - i][colIndex + i] = BoardCell.EMPTY;
					score++;
				} else {
					break;
				}
			} else {
				break;
			}
		}
		for (int i = 1; i < check; i++) {
			if (rowIndex + i < maxRows && colIndex - i >= 0) {
				if (board[rowIndex + i][colIndex - i] == b1) {
					board[rowIndex + i][colIndex - i] = BoardCell.EMPTY;
					score++;
				} else {
					break;
				}
			} else {
				break;
			}
		}
		// collapse cells
		for (int i = 0; i < maxRows - 1; i++) {
			int sum1 = 0;
			for (int j = 0; j < maxCols; j++) {
				if (board[i][j] == BoardCell.EMPTY) {
					sum1++;
				}
			}
			if (sum1 == maxCols) {
				for (int y = i; y < maxRows - 1; y++) {
					for (int k = 0; k < maxCols; k++) {
						board[y][k] = board[y + 1][k];
					}
				}
			}
		}
	}
}