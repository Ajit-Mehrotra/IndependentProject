import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Chess implements MouseListener {

	JFrame frame;
	
	private Visual visual;
	private Piece[][] chessBoard;
	private boolean isSelected = false;
	private boolean valid = false;
	private int initialRow, initialCol;
	private int finalRow, finalCol;
	private int boardSize;
	private int squareSize;
	private int turn = 1;
	
	public Chess(Piece[][] board, int boardSize) {
		chessBoard = board;
		this.boardSize = boardSize;
		squareSize = this.boardSize / 8;
		
		frame = new JFrame("Chess");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(boardSize + 6 + (3 * squareSize), boardSize + 29);
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
			Visual test = new Visual();
			makeBackground(g);

			// Update piece positions
			for (int row = 0; row < chessBoard.length; row++) {
				for (int col = 0; col < chessBoard[0].length; col++) {
					test.placePiece(g, chessBoard, row, col);
				}
			}
			
			// Highlight the initial and final positions
			if (!valid) {
				if (isSelected) {
					for (int row = 0; row < chessBoard.length; row++) {
						for (int col = 0; col < chessBoard[0].length; col++) {
							if (row == initialRow && col == initialCol) {
								g.setColor(Color.white);
								g.drawRect(col * squareSize, row * squareSize, squareSize, squareSize);
								test.placePiece(g, chessBoard, row, col);
							}
						}
					}
				}
			} else {
				if (isSelected) {
					for (int row = 0; row < chessBoard.length; row++) {
						for (int col = 0; col < chessBoard[0].length; col++) {
							if (row == initialRow && col == initialCol) {
								g.setColor(Color.white);
								g.drawRect(col * squareSize, row * squareSize, squareSize, squareSize);
								test.placePiece(g, chessBoard, row, col);
							}
						}
					}
				} else {
					for (int row = 0; row < chessBoard.length; row++) {
						for (int col = 0; col < chessBoard[0].length; col++) {
							if (row == initialRow && col == initialCol) {
								g.setColor(Color.white);
								g.drawRect(col * squareSize, row * squareSize, squareSize, squareSize);
								test.placePiece(g, chessBoard, row, col);
							}
						}
					}
					
					for (int row = 0; row < chessBoard.length; row++) {
						for (int col = 0; col < chessBoard[0].length; col++) {
							if (row == finalRow && col == finalCol) {
								g.setColor(Color.white);
								g.drawRect(col * squareSize, row * squareSize, squareSize, squareSize);
								test.placePiece(g, chessBoard, row, col);
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

		private void drawWhiteKing(Graphics g, int x, int y) {
			g.drawImage(new King(true).getImage(),
					x * squareSize, y * squareSize, squareSize, squareSize, null);
		}

		private void drawWhiteQueen(Graphics g, int x, int y) {
			g.drawImage(new Queen(true).getImage(), 
					x * squareSize, y * squareSize, squareSize, squareSize, null);
		}

		private void drawWhiteRook(Graphics g, int x, int y) {
			g.drawImage(new Rook(true).getImage(), 
					x * squareSize, y * squareSize, squareSize, squareSize, null);
		}

		private void drawWhiteBishop(Graphics g, int x, int y) {
			g.drawImage(new Bishop(true).getImage(), 
					x * squareSize, y * squareSize, squareSize, squareSize, null);
		}

		private void drawWhiteKnight(Graphics g, int x, int y) {
			g.drawImage(new Knight(true).getImage(), 
					x * squareSize, y * squareSize, squareSize, squareSize, null);
		}

		private void drawWhitePawn(Graphics g, int x, int y) {
			g.drawImage(new Pawn(true).getImage(), 
					x * squareSize, y * squareSize, squareSize, squareSize, null);
		}

		private void drawBlackKing(Graphics g, int x, int y) {
			g.drawImage(new King(false).getImage(), 
					x * squareSize, y * squareSize, squareSize, squareSize, null);
		}

		private void drawBlackQueen(Graphics g, int x, int y) {
			g.drawImage(new Queen(false).getImage(), 
					x * squareSize, y * squareSize, squareSize, squareSize, null);
		}

		private void drawBlackRook(Graphics g, int x, int y) {
			g.drawImage(new Rook(false).getImage(), 
					x * squareSize, y * squareSize, squareSize, squareSize, null);
		}

		private void drawBlackBishop(Graphics g, int x, int y) {
			g.drawImage(new Bishop(false).getImage(), 
					x * squareSize, y * squareSize, squareSize, squareSize, null);
		}

		private void drawBlackKnight(Graphics g, int x, int y) {
			g.drawImage(new Knight(false).getImage(), 
					x * squareSize, y * squareSize, squareSize, squareSize, null);
		}

		private void drawBlackPawn(Graphics g, int x, int y) {
			g.drawImage(new Pawn(false).getImage(), 
					x * squareSize, y * squareSize, squareSize, squareSize, null);
		}
		
		public void placePiece(Graphics g, Piece[][] board, int r, int c) {
			if(board[r][c] == null){
				//System.out.println("Row: " + r +"\n" + "Column: " + c);
			}
			if (board[r][c] != null) {
				if (board[r][c].getType().equals("White King")) {
					drawWhiteKing(g, c, r);
				} else if (board[r][c].getType().equals("White Queen")) {
					drawWhiteQueen(g, c, r);
				} else if (board[r][c].getType().equals("White Rook")) {
					drawWhiteRook(g, c, r);
				} else if (board[r][c].getType().equals("White Bishop")) {
					drawWhiteBishop(g, c, r);
				} else if (board[r][c].getType().equals("White Knight")) {
					drawWhiteKnight(g, c, r);
				} else if (board[r][c].getType().equals("White Pawn")) {
					drawWhitePawn(g, c, r);
				} else if (board[r][c].getType().equals("Black King")) {
					drawBlackKing(g, c, r);
				} else if (board[r][c].getType().equals("Black Queen")) {
					drawBlackQueen(g, c, r);
				} else if (board[r][c].getType().equals("Black Rook")) {
					drawBlackRook(g, c, r);
				} else if (board[r][c].getType().equals("Black Bishop")) {
					drawBlackBishop(g, c, r);
				} else if (board[r][c].getType().equals("Black Knight")) {
					drawBlackKnight(g, c, r);
				} else if (board[r][c].getType().equals("Black Pawn")) {
					drawBlackPawn(g, c, r);
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
				//System.out.println("What the freak!");
				if (isSelectionValid(row, col)) {
					isSelected = true;
					initialRow = row;
					initialCol = col;
					visual.repaint();
					
			}
			} else {
				isSelected = false;
				if (isPlacementValid(row, col)) {
					valid = true;
					finalRow = row;
					finalCol = col;
					chessBoard[finalRow][finalCol] = chessBoard[initialRow][initialCol];
					chessBoard[initialRow][initialCol] = null;
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
			if (chessBoard[r][c].isWhite()) {
				return false;
			}
		} else { // Turn is even --> black's turn
			return true;
		}
		
		return false;
	}
	
	public boolean isPlacementValid(int r, int c) {
		// If turn == odd --> white's turn
		if (turn % 2 == 1) {
			if (chessBoard[r][c].isWhite()) {
				return false;
			}
		} else { // Turn is even --> black's turn
			return false;
		}
		
		return true;
	}
	
	public static void main(String[] args) {
		Piece[][] classic = {
				{new Rook(false), new Knight(false), new Bishop(false), new Queen(false), new King(false), new Bishop(false), new Knight(false), new Rook(false)}, 
				{new Pawn(false), new Pawn(false), new Pawn(false), new Pawn(false), new Pawn(false), new Pawn(false), new Pawn(false), new Pawn(false)}, 
				{null, null, null, null, null, null, null, null}, 
				{null, null, null, null, null, null, null, null}, 
				{null, null, null, null, null, null, null, null}, 
				{null, null, null, null, null, null, null, null}, 
				{new Pawn(true), new Pawn(true), new Pawn(true), new Pawn(true), new Pawn(true), new Pawn(true), new Pawn(true), new Pawn(true)},
				{new Rook(true), new Knight(true), new Bishop(true), new Queen(true), new King(true), new Bishop(true), new Knight(true), new Rook(true)} 
				
		};
		new Chess(classic, 600);
	}
	
	/*public Piece getPiece(int r, int c) {
		for (Piece[] row : chessBoard) {
			for (Piece p : row) {
				
			}
		}
	}*/
}


