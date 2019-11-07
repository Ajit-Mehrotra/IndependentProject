import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.awt.image.*;


public class Background {
	JPanelWithBackground frame;
	JPanel contentPane;
	JLabel label;
	JLabel chessPieceLabel;
	final ImageIcon icon = new ImageIcon("Pictures//Hopethisworks.png");
	private JLayeredPane lpane = new JLayeredPane();
	private ImageIcon chessboardImageIcon;
	private Image chessboardImage;
	private Image scaledChessboardImage;
	private ImageIcon chessPiece ;
	private ImageIcon imageIconOfScaledChessBoardImage;
	private PixelGrabber pGrabber;
	Background(){
		frame = new JPanelWithBackground("Pictures//Hopethisworks.png");
		contentPane.paintComponent(frame);
		frame.setContentPane(contentPane);	
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultLookAndFeelDecorated(true);
		contentPane.add(frame);
	}

}
