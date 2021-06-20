package com.inquiremusic.app.view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;

import com.inquiremusic.app.controller.MainController.KeyboardListener;

/**
 * MainView is the primary entry point for the UI
 * @author Thomas P. Kovalchuk
 *
 */
public class MainView extends JFrame{
	protected MainPanel panelMain;
	protected JDialog errorDialog;

	/**
	 * MainView instance sets up new window and provides a getter to access itself and any sub-components
	 */
	public MainView(){
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("InquireMusic - SWENG861");
		setResizable(false);
		getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		panelMain = new MainPanel();
		
		JOptionPane errorPane = new JOptionPane("Error during execution, please try again later.");
		errorPane.setOptionType(JOptionPane.CLOSED_OPTION);
		errorPane.setMessageType(JOptionPane.ERROR_MESSAGE);
		errorDialog = errorPane.createDialog("Error");
				
		getRootPane().setDefaultButton(this.getMainPanel().btnSearch);
		
		getContentPane().add(panelMain);

		resetSize();
	}
	public void resetSize(){
		pack();
		setSize(new Dimension(900, 600));
	}
	public MainPanel getMainPanel(){
		return this.panelMain;
	}
	
	public JDialog getErrorDialog() {
		return this.errorDialog;
	}

	/**
	 * Sets up listeners for all UI components that perform actions
	 * @param listener
	 * @param listListener
	 * @param keyboardListener
	 */
	public void addListeners(ActionListener listener, ListSelectionListener listListener, KeyboardListener keyboardListener){
		panelMain.btnSearch.addActionListener(listener); 
		panelMain.listResults.addListSelectionListener(listListener);
		panelMain.textSearch.addKeyListener(keyboardListener);
		panelMain.cbxSearchType.addActionListener(listener);
	}
	
}
