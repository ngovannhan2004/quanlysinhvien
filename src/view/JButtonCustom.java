package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;

public class JButtonCustom extends JButton {

	public JButtonCustom(String text) {
		super(text);
		this.setPreferredSize(new Dimension(100, 3));
		this.setFont(new Font("Roboto", Font.PLAIN, 15));
		this.setBorderPainted(false);
		this.setFocusPainted(false);
		this.setBackground( new Color(0, 128, 130));
		this.setForeground(Color.WHITE);
		this.setBorder(null);
	}

}
