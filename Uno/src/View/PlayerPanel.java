/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

/**
 *
 * @author pavlos
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import GameModel.Player;
import Interfaces.GameConstants;
import ServerController.MyButtonListener;

public class PlayerPanel extends JPanel implements GameConstants {

	private Player player;
	private String name;

	private Box myLayout;
	private JLayeredPane cardHolder;
	private Box controlPanel;

	private JButton draw;
	private JButton sayUNO;
        private JButton pass;
	private JLabel nameLbl;
	private MyButtonHandler handler;

	// Constructor
	public PlayerPanel(Player newPlayer) {
		setPlayer(newPlayer);

		myLayout = Box.createHorizontalBox();
		cardHolder = new JLayeredPane();
		cardHolder.setPreferredSize(new Dimension(600, 175));

		// Set
		setCards();
		setControlPanel();

		myLayout.add(cardHolder);
		myLayout.add(Box.createHorizontalStrut(40));
		myLayout.add(controlPanel);
		add(myLayout);

		// Register Listeners
		handler = new MyButtonHandler();
		draw.addActionListener(BUTTONLISTENER);
		draw.addActionListener(handler);
		
		sayUNO.addActionListener(BUTTONLISTENER);
		sayUNO.addActionListener(handler);
                
                pass.addActionListener(BUTTONLISTENER);
		pass.addActionListener(handler);
	}

	public void setCards() {
		cardHolder.removeAll();

		// Origin point at the center
		Point origin = getPoint(cardHolder.getWidth(), player.getTotalCards());
		int offset = calculateOffset(cardHolder.getWidth(),
				player.getTotalCards());

		int i = 0;
		for (UNOCard card : player.getAllCards()) {
			card.setBounds(origin.x, origin.y, card.CARDSIZE.width,
					card.CARDSIZE.height);
			cardHolder.add(card, i++);
			cardHolder.moveToFront(card);
			origin.x += offset;
		}
		repaint();
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
		setPlayerName(player.getName());
	}

	public void setPlayerName(String playername) {
		this.name = playername;
	}

	private void setControlPanel() {
		draw = new JButton("Draw");
		sayUNO = new JButton("Say UNO");
		nameLbl = new JLabel(name);
                pass=new JButton("Pass");

		// style
		draw.setBackground(new Color(79, 129, 189));
		draw.setFont(new Font("Arial", Font.BOLD, 20));
		draw.setFocusable(false);

		sayUNO.setBackground(new Color(149, 55, 53));
		sayUNO.setFont(new Font("Arial", Font.BOLD, 20));
		sayUNO.setFocusable(false);
                
                pass.setBackground(new Color(73, 139, 149));
		pass.setFont(new Font("Arial", Font.BOLD, 20));
		pass.setFocusable(false);

		nameLbl.setForeground(Color.WHITE);
		nameLbl.setFont(new Font("Arial", Font.BOLD, 15));

		controlPanel = Box.createVerticalBox();
		controlPanel.add(nameLbl);
		controlPanel.add(draw);
		controlPanel.add(Box.createVerticalStrut(15));
		controlPanel.add(sayUNO);
                controlPanel.add(pass);
	}

	private int calculateOffset(int width, int totalCards) {
		int offset = 71;
		if (totalCards <= 8) {
			return offset;
		} else {
			double o = (width - 100) / (totalCards - 1);
			return (int) o;
		}
	}

	private Point getPoint(int width, int totalCards) {
		Point p = new Point(0, 20);
		if (totalCards >= 8) {
			return p;
		} else {
			p.x = (width - calculateOffset(width, totalCards) * totalCards) / 2;
			return p;
		}
	}
	
	class MyButtonHandler implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			
			if(player.isMyTurn()){
				
				if(e.getSource()==draw)
					BUTTONLISTENER.drawCard();
				else if(e.getSource()==sayUNO)
					BUTTONLISTENER.sayUNO();
                                else if(e.getSource()==pass)
                                        BUTTONLISTENER.TurnPass();
			}
		}
	}
}
