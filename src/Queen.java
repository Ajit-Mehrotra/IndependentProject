import java.awt.*;
import javax.swing.*;

public class Queen extends Piece {

	private String identity;
	private boolean[][] validMoves = new boolean[8][8];

	public Queen(String color) {
		if (color.equalsIgnoreCase("white")) {
			identity = "White Queen";
		} else {
			identity = "Black Queen";
		}
	}

	public String getIdentity() {
		return identity;
	}

	public void drawPiece(Graphics g, int x, int y, int squareSize) {
		g.drawImage(new ImageIcon("Images/" + identity + ".png").getImage(), 
				x * squareSize, y * squareSize, squareSize, squareSize, null);
	}

	public void updateMoves(Piece[][] board, int iRow, int iCol) {
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[0].length; col++) {
				if (isValid(board, iRow, iCol, row, col)) {
					validMoves[row][col] = true;
				}
			}
		}
	}

	public boolean[][] getMoves() {
		return validMoves;
	}

	public boolean isValid(Piece[][] board, int iRow, int iCol, int fRow, int fCol) {
		// White piece cannot move to the position of another white piece
		if (this.getIdentity().contains("White")) {
			if (board[fRow][fCol] != null && (!board[fRow][fCol].getIdentity().contains("Black"))) {
				return false;
			}
		} else if (this.getIdentity().contains("Black")) {
			if (board[fRow][fCol] != null && (!board[fRow][fCol].getIdentity().contains("White"))) {
				return false;
			}
		}

		// Queen must move horizontally, vertically, or diagonally
		if ((!(Math.abs(fCol - iCol) > 0 && Math.abs(fRow - iRow) == 0)) &&
				(!(Math.abs(fCol - iCol) == 0 && Math.abs(fRow - iRow) > 0)) && 
				(Math.abs(fCol - iCol) != Math.abs(fRow - iRow))) {
			return false;
		} else { // If it does, check if there are pieces in the way between its initial and final position
			// If moving horizontally
			if (Math.abs(fCol - iCol) > 0 && Math.abs(fRow - iRow) == 0) {
				// If moving west
				if (iCol > fCol) {
					// Loops through all the positions between the initial and final positions
					for (int i = iCol - 1; i > fCol; i--) {
						// If there is a piece between, return false
						if (board[iRow][i] != null) {
							return false;
						}
					}
				} else { // If moving east
					for (int i = iCol + 1; i < fCol; i++) {
						// If there is a piece between, return false
						if (board[iRow][i] != null) {
							return false;
						}
					}
				}
				// If moving vertically	
			} else if (Math.abs(fCol - iCol) == 0 && Math.abs(fRow - iRow) > 0) {
				// If moving north
				if (iRow > fRow) {
					for (int i = iRow - 1; i > fRow; i--) {
						// If there is a piece between, return false
						if (board[i][iCol] != null) {
							return false;
						}
					}
				} else { // If moving south
					for (int i = iRow + 1; i < fRow; i++) {
						// If there is a piece between, return false
						if (board[i][iCol] != null) {
							return false;
						}
					}
				}
				// If moving northwest
			} else if ((iRow > fRow) && (iCol > fCol)) {
				for (int i = iRow - 1, j = iCol - 1; i > fRow && j > fCol; i--, j--) {
					// If there is a piece between, return false
					if (board[i][j] != null) {
						return false;
					}
				}
				// If moving northeast
			} else if ((iRow < fRow) && (iCol > fCol)) {
				for (int i = iRow + 1, j = iCol - 1; i < fRow && j > fCol; i++, j--) {
					// If there is a piece between, return false
					if (board[i][j] != null) {
						return false;
					}
				}
				// If moving southwest
			} else if ((iRow > fRow) && (iCol < fCol)) {
				for (int i = iRow - 1, j = iCol + 1; i > fRow && j < fCol; i--, j++) {
					// If there is a piece between, return false
					if (board[i][j] != null) {
						return false;
					}
				}
			} else { // If moving southeast
				for (int i = iRow + 1, j = iCol + 1; i < fRow && j < fCol; i++, j++) {
					// If there is a piece between, return false
					if (board[i][j] != null) {
						return false;
					}
				}
			}
		}

		return true;
	}

}



