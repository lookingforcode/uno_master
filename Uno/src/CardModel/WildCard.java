/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CardModel;

/**
 *
 * @author pavlos
 */
import java.awt.Color;

import View.UNOCard;

public class WildCard extends UNOCard {
	
	private int Function = 0;
	private Color chosenColor;
	
	public WildCard() {
	}
	
	public WildCard(String cardValue){
		super(BLACK, WILD, cardValue);		
	}
	
	public void useWildColor(Color wildColor){
		chosenColor = wildColor;
	}
	
	public Color getWildColor(){
		return chosenColor;
	}

}
