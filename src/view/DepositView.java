package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

import controller.ViewManager;
import model.BankAccount;

@SuppressWarnings("serial")
public class DepositView extends JPanel implements ActionListener {
	
	private ViewManager manager;		// manages interactions between the views, model, and database
	private BankAccount account;
	
	private JButton logOutButton;
	private JButton DepositButton;
	private JButton WithdrawButton;
	private JButton TransferButton;

	
	private JLabel welcomeText;
	private JTextField InputText;
	private JLabel statusText;

	/**
	 * Constructs an instance (or objects) of the HomeView class.
	 * 
	 * @param manager
	 */
	
	public DepositView(ViewManager manager) {
		super();
		this.manager = manager;
		
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		initialize();
	}
	
	///////////////////// PRIVATE METHODS /////////////////////////////////////////////
	
	/*
	 * Initializes the HomeView components.
	 */
	
	private void initialize() {
		Box firstRow = new Box(BoxLayout.X_AXIS);
		welcomeText = new JLabel("How much would you like to deposit?");
		firstRow.add(welcomeText);
		this.add(firstRow);
		
		this.add(Box.createVerticalStrut(100));
		Box secondRow = new Box(BoxLayout.X_AXIS);
		InputText = new JTextField();
		secondRow.add(InputText);
		this.add(secondRow);
		this.add(new Box(BoxLayout.X_AXIS));
		
		this.add(Box.createVerticalStrut(30));
		Box thirdRow = new Box(BoxLayout.X_AXIS);
		DepositButton = new JButton("Deposit");
		DepositButton.addActionListener(this);
		thirdRow.add(DepositButton);
		this.add(Box.createHorizontalStrut(0));
		logOutButton = new JButton("Return Home");
		logOutButton.addActionListener(this);
		thirdRow.add(logOutButton);
		this.add(thirdRow);
		this.add(Box.createVerticalStrut(300));
		
		//this.add(Box.createVerticalStrut(40));
		Box fourthRow = new Box(BoxLayout.X_AXIS);
		statusText = new JLabel("");
		fourthRow.add(statusText);
		this.add(fourthRow);



	}	
	/*
	 * HomeView is not designed to be serialized, and attempts to serialize will throw an IOException.
	 * 
	 * @param oos
	 * @throws IOException
	 */
	
	private void writeObject(ObjectOutputStream oos) throws IOException {
		throw new IOException("ERROR: The HomeView class is not serializable.");
	}
	
	///////////////////// OVERRIDDEN METHODS //////////////////////////////////////////
	
	/*
	 * Responds to button clicks and other actions performed in the HomeView.
	 * 
	 * @param e
	 */
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source.equals(logOutButton)) {
			manager.switchTo(ATM.HOME_VIEW);	
		}
		
		else if (source.equals(DepositButton)) {
			if (this.deposit()) {
				statusText.setText("Successful Deposit!");
			} else {
				statusText.setText("Unseccessful Deposit");
			}
		}
	}

	public boolean deposit() {
		String deposit = JOptionPane.showInputDialog("How much would you like to deposit?\n (Enter ONLY numbers greater than 0)");
		if (deposit == null) {
			return false;
		}
		double depositAmount = 0;
		try {
			depositAmount = Double.parseDouble(deposit);
		} catch (Exception e1) {
			return this.deposit();
		}
		
		if(account.deposit(depositAmount) == ATM.SUCCESS) {
			String info = String.format("Account Number: %d, Balance: $%.2f", account.getAccountNumber(), account.getBalance());
			InputText.setText(info);
			return manager.updateAccount(account);
		} else {
			return this.deposit();
		}
	}
}