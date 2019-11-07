import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TestingQueen implements MouseListener {

	JFrame frame;

	private Visual visual;
	private Piece[][] chessBoard;
	private boolean isSelected = false;
	private boolean valid = false;
	private int turn = 1;
	private int iRow, iCol;
	private int fRow, fCol;
	private int boardSize;
	private int squareSize;

	public TestingQueen(Piece[][] board, int boardSize) {
		chessBoard = board;
		this.boardSize = (boardSize / 8) * 8;
		squareSize = this.boardSize / 8;

		frame = new JFrame("Chess");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(this.boardSize + 6 + (3 * squareSize), this.boardSize + 29);
		frame.setResizable(false);

		for (int row = 0; row < chessBoard.length; row++) {
			for (int col = 0; col < chessBoard[0].length; col++) {
				chessBoard[row][col] = board[row][col];
			}
		}
		visual = new Visual();
		visual.addMouseListener(this);
		frame.add(visual);

		// Center the UI
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public class Visual extends JPanel {
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(3));
			Color clearGreen = new Color(0, 255, 0, 127);

			makeBackground(g);

			// Update piece positions
			for (int row = 0; row < chessBoard.length; row++) {
				for (int col = 0; col < chessBoard[0].length; col++) {
					if (chessBoard[row][col] != null) {
						chessBoard[row][col].drawPiece(g, col, row, squareSize);
					}
				}
			}

			// Highlight the initial and final positions
			if (!valid) {
				if (isSelected) {
					for (int row = 0; row < chessBoard.length; row++) {
						for (int col = 0; col < chessBoard[0].length; col++) {
							if (row == iRow && col == iCol) {
								g.setColor(Color.white);
								g.drawRect(col * squareSize, row * squareSize, squareSize, squareSize);
								chessBoard[row][col].drawPiece(g, col, row, squareSize);
								g.setColor(clearGreen);
								for (int i = 0; i < chessBoard[row][col].getMoves().length; i++) {
									for (int j = 0; j < chessBoard[row][col].getMoves()[0].length; j++) {
										if (chessBoard[row][col].getMoves()[i][j] == true) {
											g.drawRect(j * squareSize, i * squareSize, squareSize, squareSize);
										}
									}
								}
							}
						}
					}
				}
			} else {
				if (isSelected) {
					for (int row = 0; row < chessBoard.length; row++) {
						for (int col = 0; col < chessBoard[0].length; col++) {
							if (row == iRow && col == iCol) {
								g.setColor(Color.white);
								g.drawRect(col * squareSize, row * squareSize, squareSize, squareSize);
								chessBoard[row][col].drawPiece(g, col, row, squareSize);
							}
						}
					}
				} else {
					for (int row = 0; row < chessBoard.length; row++) {
						for (int col = 0; col < chessBoard[0].length; col++) {
							if (row == iRow && col == iCol) {
								g.setColor(Color.white);
								g.drawRect(col * squareSize, row * squareSize, squareSize, squareSize);
								// chessBoard[row][col].drawPiece(g, col, row, squareSize);
							}
						}
					}

					for (int row = 0; row < chessBoard.length; row++) {
						for (int col = 0; col < chessBoard[0].length; col++) {
							if (row == fRow && col == fCol) {
								g.setColor(Color.white);
								g.drawRect(col * squareSize, row * squareSize, squareSize, squareSize);
								chessBoard[row][col].drawPiece(g, col, row, squareSize);
							}
						}
					}
				}
			}

		}

		private void makeBackground(Graphics g) {
			g.setColor(Color.lightGray);
			g.fillRect(0, 0, boardSize, boardSize);

			g.setColor(Color.gray);
			// Odd rows
			for (int row = 0; row <= 6; row += 2) {
				for (int col = 1; col <= 7; col += 2) {
					g.fillRect(col * squareSize, row * squareSize, squareSize, squareSize);
				}
			}
			// Even rows
			for (int row = 1; row <= 7; row += 2) {
				for (int col = 0; col <= 6; col += 2) {
					g.fillRect(col * squareSize, row * squareSize, squareSize, squareSize);
				}
			}
		}
	}

	@Override
	// Press and release
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	// Just press
	public void mousePressed(MouseEvent e) {

	}

	@Override
	// Just release
	public void mouseReleased(MouseEvent e) {
		if ((e.getX() <= boardSize && e.getY() <= boardSize)) {
			int row = e.getY() / squareSize;
			int col = e.getX() / squareSize;

			if (!isSelected) {
				if (isSelectionValid(row, col)) {
					isSelected = true;
					iRow = row;
					iCol = col;
					visual.repaint();
				}
			} else {
				isSelected = false;
				if (isPlacementValid(row, col)) {
					valid = true;
					fRow = row;
					fCol = col;
					chessBoard[fRow][fCol] = chessBoard[iRow][iCol];
					chessBoard[iRow][iCol] = null;
					turn += 1;
					visual.repaint();
				} else {
					valid = false;
					visual.repaint();
				}
			}
		}
	}

	public boolean isSelectionValid(int r, int c) {
		// If turn == odd --> white's turn
		if (turn % 2 == 1) {
			if (chessBoard[r][c] != null && chessBoard[r][c].getIdentity().contains("White")) {
				return true;
			}
		} else { // Turn is even --> black's turn
			if (chessBoard[r][c] != null && chessBoard[r][c].getIdentity().contains("Black"))  {
				return true;
			}
		}

		return false;
	}

	public boolean isPlacementValid(int r, int c) {
		if (chessBoard[iRow][iCol].getIdentity().contains("Queen")) {
			chessBoard[iRow][iCol].updateMoves(chessBoard, iRow, iCol);
			if (chessBoard[iRow][iCol].isValid(chessBoard, iRow, iCol, r, c)) {
				return true;
			} else {
				return false;
			}
		}
		// If turn == odd --> white's turn
		if (turn % 2 == 1) {
			if (chessBoard[r][c] == null || chessBoard[r][c].getIdentity().contains("Black")) {
				return true;
			}
		} else { // Turn is even --> black's turn
			if (chessBoard[r][c] == null || chessBoard[r][c].getIdentity().contains("White")) {
				return true;
			}
		}

		return false;
	}

	public static void main(String[] args) {
		Piece[][] classic = {
				{new Rook("Black"), new Knight("Black"), new Bishop("Black"), new Queen("Black"), new King("Black"), new Bishop("Black"), new Knight("Black"), new Rook("Black")}, 
				{new Pawn("Black"), new Pawn("Black"), new Pawn("Black"), new Pawn("Black"), new Pawn("Black"), new Pawn("Black"), new Pawn("Black"), new Pawn("Black")}, 
				{null, null, null, null, null, null, null, null}, 
				{null, null, null, null, null, null, null, null}, 
				{null, null, null, null, null, null, null, null}, 
				{null, null, null, null, null, null, null, null}, 
				{new Pawn("White"), new Pawn("White"), new Pawn("White"), new Pawn("White"), new Pawn("White"), new Pawn("White"), new Pawn("White"), new Pawn("White")}, 
				{new Rook("White"), new Knight("White"), new Bishop("White"), new Queen("White"), new King("White"), new Bishop("White"), new Knight("White"), new Rook("White")}
		};
		new TestingQueen(classic, 700);
	}

}


