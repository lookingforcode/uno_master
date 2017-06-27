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
import CardModel.NumberCard;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import CardModel.WildCard;
import GameModel.Game;
import GameModel.Player;
import Interfaces.GameConstants;
import View.PlayerPanel;
import View.Session;
import View.UNOCard;
import java.util.LinkedList;

public class Server implements GameConstants {
	private Game game;
	private Session session;
	private Stack<UNOCard> playedCards;
        private String txt;   
	public boolean canPlay;
	private int mode,secondMode,sumOfCards,sumOfWildCards;

	public Server() {

		mode = requestMode();
		game = new Game(mode);
		playedCards = new Stack<UNOCard>();
                sumOfCards=2;
                sumOfWildCards=4;

		// First Card
		UNOCard firstCard = game.getCard();
		modifyFirstCard(firstCard);

		playedCards.add(firstCard);
		session = new Session(game, firstCard);

		game.whoseTurn();
		canPlay = true;
                txt="invalid move";
	}

	//return if it's 2-Player's mode or PC-mode
	private int requestMode() {

		Object[] options = { "vs PC", "Manual", "Cancel" };
                Object[] Modeoptions = { "Classic game", "Variation","Variation2", "Cancel" };

		int n = JOptionPane.showOptionDialog(null,
				"Choose a Game Mode to play", "Game Mode",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
				null, options, options[0]);
                
                if (n == 2)
                    System.exit(1);
                
                secondMode = JOptionPane.showOptionDialog(null,
				"Choose a Game Mode to play", "Game Mode",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
				null, Modeoptions, Modeoptions[0]);

		if (secondMode == 3)
                    System.exit(1);
                
		return GAMEMODES[n];
	}
	
	//coustom settings for the first card
	private void modifyFirstCard(UNOCard firstCard) {
		firstCard.removeMouseListener(CARDLISTENER);
		if (firstCard.getType() == WILD) {
			int random = new Random().nextInt() % 4;
			try {
				((WildCard) firstCard).useWildColor(UNO_COLORS[Math.abs(random)]);
			} catch (Exception ex) {
				System.out.println("something wrong with modifyFirstcard");
			}
		}
	}
	
	//return Main Panel
	public Session getSession() {
		return this.session;
	}
	
	
	//request to play a card
	public void playThisCard(UNOCard clickedCard) {

		// Check player's turn
		if (!isHisTurn(clickedCard)) {
			infoPanel.setError("It's not your turn");
			infoPanel.repaint();
		} else {

			// Card validation
			if (isValidMove(clickedCard)) {
                            if(draw(clickedCard)){
                                if(sumOfCards>2 ){
                                    if(clickedCard.getValue().equals(DRAW2PLUS)){
                                        showTheResults(clickedCard);
                                        
                                        /*performAction(clickedCard);                                        
                                        game.switchTurn();
                                        session.updatePanel(clickedCard);
                                        checkResults();*/
                                    }
                                    else{
                                        infoPanel.setError("Play the 2+ card");
                                        infoPanel.repaint();
                                    }
                                }                                
                                else
                                {
                                    if(sumOfWildCards>4){
                                        if(clickedCard.getValue().equals(W_DRAW4PLUS)){
                                            showTheResults(clickedCard);
                                            
                                           /* performWild((WildCard) clickedCard);                                            
                                            game.switchTurn();
                                            session.updatePanel(clickedCard);
                                            checkResults();*/
                                        }
                                        else{
                                            infoPanel.setError("Play the 4+ card");
                                            infoPanel.repaint();
                                        }
                                    } 
                                    else{
                                        
                                            showTheResults(clickedCard);
                                            // function cards ??
                                           /* switch (clickedCard.getType()) {
                                            case ACTION:
                                                    performAction(clickedCard);
                                                    break;
                                            case WILD:
                                                    performWild((WildCard) clickedCard);
                                                    break;
                                            default:
                                                    break;

                                            } 
                                            game.switchTurn();
                                            session.updatePanel(clickedCard);
                                            checkResults();*/
                                    }                                     
                                }
                            }
			} else {
				infoPanel.setError(txt);
				infoPanel.repaint();
			}			
		}
		pcPlay();
	}

	//Check if the game is over
	private void checkResults() {
		if (game.isOver()) {
			canPlay = false;
			infoPanel.updateText("GAME OVER");
		}
	}
	
	//check player's turn
	public boolean isHisTurn(UNOCard clickedCard) {
		for (Player p : game.getPlayers()) {
			if (p.hasCard(clickedCard) && p.isMyTurn())
                            return true;
		}
		return false;
	}

	//check if it is a valid card
	public boolean isValidMove(UNOCard playedCard) {
		UNOCard topCard = peekTopCard();

		if (playedCard.getColor().equals(topCard.getColor())
				|| playedCard.getValue().equals(topCard.getValue())) {
			return true;
		}

		else if (playedCard.getType() == WILD) {
			return true;
		} else if (topCard.getType() == WILD) {
			Color color = ((WildCard) topCard).getWildColor();
			if (color.equals(playedCard.getColor()))
				return true;
		}
		return false;
	}

	// ActionCards
	private void performAction(UNOCard actionCard) {
		// Draw2PLUS
            int k=1;
		if (actionCard.getValue().equals(DRAW2PLUS)){
                    if(secondMode==1 && game.getPlayers()[0]!=game.getPC() ){
                        if(game.checkOpponetCards(DRAW2PLUS)){                                                       
                                Object[] options = {"I will play the 2+ card","Draw cards and lose my turn"};
                                k=JOptionPane.showOptionDialog(null, "Choose",null,
                                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                                        null, options, options[0]); 
                        }
                    }
                    if(k==1)
                    {
                        game.drawPlus(sumOfCards);
                        game.switchTurn();
                        sumOfCards=2;
                    }
                    else
                    {
                        sumOfCards+=2;
                        txt="Play the 2+ card";
                        
                    }
                }
		else if (actionCard.getValue().equals(REVERSE))
			game.switchTurn();
		else if (actionCard.getValue().equals(SKIP))
			game.switchTurn();
	}
        
        private void performNumber(NumberCard actionCard) {
            if ((actionCard.getValue().equals("7") || actionCard.getValue().equals("0")) && secondMode==2){
                PlayerPanel p1 = session.getPlayers()[0];
                PlayerPanel p2 = session.getPlayers()[1];
                Player player1 = p1.getPlayer();
                Player player2 = p2.getPlayer();
                
                LinkedList<UNOCard> p1Cards = player1.getAllCards();
                LinkedList<UNOCard> p2Cards = player2.getAllCards();
                LinkedList<UNOCard> temp = p2Cards;
                p2Cards = p1Cards;
                p1Cards = temp;
                
                player1.setCards(p1Cards);
                player2.setCards(p2Cards);
                p1.setPlayer(player1);
                p2.setPlayer(player2);
            }
        }

	private void performWild(WildCard functionCard) {		

		//System.out.println(game.whoseTurn());
		if(mode==1 && game.isPCsTurn()){			
			int random = new Random().nextInt() % 4;
			functionCard.useWildColor(UNO_COLORS[Math.abs(random)]);
		}
		else{
			
			ArrayList<String> colors = new ArrayList<String>();
			colors.add("RED");
			colors.add("BLUE");
			colors.add("GREEN");
			colors.add("YELLOW");
			
			String chosenColor = (String) JOptionPane.showInputDialog(null,
					"Choose a color", "Wild Card Color",
					JOptionPane.DEFAULT_OPTION, null, colors.toArray(), null);
	
			functionCard.useWildColor(UNO_COLORS[colors.indexOf(chosenColor)]);
		}
		
		if (functionCard.getValue().equals(W_DRAW4PLUS)){
                    int k=1;
                     if(secondMode==1 && game.getPlayers()[0]!=game.getPC()){
                        if(game.checkOpponetCards(W_DRAW4PLUS)){
                            Object[] options = {"I will play the 4+ card","Draw cards and lose my turn"};
                            k=JOptionPane.showOptionDialog(null,
                                    "Choose",null,
                                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                                    null, options, options[0]);   
                        }
                    }
                    if(k==1){
			game.drawPlus(sumOfWildCards);
                        game.switchTurn();
                        sumOfWildCards=4;
                        
                    }
                    else{
                        sumOfWildCards+=4;
                        txt="Play the 4+ card";
                    }
                }
	}
	
	public void requestCard() {
		game.drawCard(peekTopCard());
		
		session.refreshPanel();
	}

	public UNOCard peekTopCard() {
		return playedCards.peek();
	}

	public void submitSaidUNO() {
		game.setSaidUNO();
	}
        
        public void Pass() {
            game.PassSwitchTurn();
            pcPlay();
        }
        public void pcPlay(){
            if(mode==vsPC && canPlay){
			if(game.isPCsTurn()){
				game.playPC(peekTopCard());
                                session.refreshPanel();
			}
		}
        }
        
        public boolean draw(UNOCard clickedCard){
            for (Player p : game.getPlayers()) {
                if(p.isMyTurn()){
                    if(p.haveDraw()){
                        if(p.getAllCards().getLast().equals(clickedCard))
                            return true;
                        else{
                            infoPanel.setError("invalid move");
                            infoPanel.repaint();
                            return false;
                        }
                    }
                    return true;
                }
            }
            return false;
        }
        
        public void showTheResults(UNOCard clickedCard){
            clickedCard.removeMouseListener(CARDLISTENER);
            playedCards.add(clickedCard);                                
            game.removePlayedCard(clickedCard);
           
            //function cards ??
            switch (clickedCard.getType()) {
                case ACTION:
                        performAction(clickedCard);
                        break;
                case WILD:
                        performWild((WildCard) clickedCard);
                        break;
                case NUMBERS:
                    performNumber((NumberCard) clickedCard);
                       break;
                default:
                        break;
            }
            game.switchTurn();
            session.updatePanel(clickedCard);
            checkResults();    
        }
}
