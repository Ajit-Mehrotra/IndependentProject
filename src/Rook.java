import java.awt.*;
import javax.swing.*;

public class Rook extends Piece {

	private String identity;
	private boolean[][] validMoves = new boolean[8][8];

	public Rook(String color) {
		if (color.equalsIgnoreCase("white")) {
			identity = "White Rook";
		} else {
			identity = "Black Rook";
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
		return false;
	}

}



