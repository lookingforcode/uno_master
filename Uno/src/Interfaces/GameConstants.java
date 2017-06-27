/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

/**
 *
 * @author pavlos
 */
import java.awt.Color;

import javax.swing.JTextArea;

import ServerController.MyButtonListener;
import ServerController.MyCardListener;
import View.InfoPanel;


public interface GameConstants extends UNOConstants {
	
	int TOTAL_CARDS = 108;
	int FIRSTHAND = 8;
	
	Color[] UNO_COLORS = {RED, BLUE, GREEN, YELLOW};
	Color WILD_CARDCOLOR = BLACK;
	
	int[] UNO_NUMBERS =  {0,1,2,3,4,5,6,7,8,9};	
	String[] ActionTypes = {REVERSE,SKIP,DRAW2PLUS};	
	String[] WildTypes = {W_COLORPICKER, W_DRAW4PLUS};
	
	int vsPC = 1;
	int MANUAL = 2;
	
	int[] GAMEMODES = {vsPC, MANUAL};
	
	MyCardListener CARDLISTENER = new MyCardListener();
	MyButtonListener BUTTONLISTENER = new MyButtonListener();
	
	InfoPanel infoPanel = new InfoPanel();
}