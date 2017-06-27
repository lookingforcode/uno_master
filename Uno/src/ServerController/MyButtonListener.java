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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyButtonListener implements ActionListener {
		
	Server myServer;
	
	public void setServer(Server server){
		myServer = server;
	}
	
	public void drawCard() {
		if(myServer.canPlay)
			myServer.requestCard();	
	}
	
	public void sayUNO() {
		if(myServer.canPlay)
			myServer.submitSaidUNO();
	}
        
        public void TurnPass() {
            if(myServer.canPlay)
                myServer.Pass();
        }

	@Override
	public void actionPerformed(ActionEvent e) {
	}

	
}
