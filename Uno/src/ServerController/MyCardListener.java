/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerController;

/**
 *
 * @author pavlos
 */
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.omg.CORBA.Bounds;
import View.MainFrame;
import View.UNOCard;

public class MyCardListener extends MouseAdapter {
	
	UNOCard sourceCard;
	Server myServer;
	
	public void setServer(Server server){
		myServer = server;
	}
	
	public void mousePressed(MouseEvent e) {		
		sourceCard = (UNOCard) e.getSource();
		
		try{
			if(myServer.canPlay)
				myServer.playThisCard(sourceCard);			
			
		}catch(NullPointerException ex){
			ex.printStackTrace();
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		super.mouseEntered(e);		
		
		sourceCard = (UNOCard) e.getSource();
		Point p = sourceCard.getLocation();
		p.y -=20;
		sourceCard.setLocation(p);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		sourceCard = (UNOCard) e.getSource();
		Point p = sourceCard.getLocation();
		p.y +=20;
		sourceCard.setLocation(p);
	}	

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}	

}
