import java.awt.*;

public abstract class Piece {

	public abstract String getIdentity();
	public abstract void drawPiece(Graphics g, int x, int y, int squareSize);
	public abstract void updateMoves(Piece[][] board, int iRow, int iCol);
	public abstract boolean[][] getMoves();
	public abstract boolean isValid(Piece[][] board, int iRow, int iCol, int fRow, int fCol);

}



