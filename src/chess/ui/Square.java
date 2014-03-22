package chess.ui;

import javax.swing.JButton;

public class Square extends JButton{
	public int x;
	public int y;
	
	public Square(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
}
