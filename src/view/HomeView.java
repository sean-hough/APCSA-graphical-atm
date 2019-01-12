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
import javax.swing.JOptionPane;

import controller.ViewManager;
import model.BankAccount;

@SuppressWarnings("serial")
public class HomeView extends JPanel implements ActionListener {
	
	private ViewManager manager;		// manages interactions between the views, model, and database
	private BankAccount account;
	
	private JButton logOutButton;
	private JButton DepositButton;
	private JButton WithdrawButton;
	private JButton TransferButton;

	
	private JLabel welcomeText;
	private JLabel InfoText;
	private JLabel statusText;

	/**
	 * Constructs an instance (or objects) of the HomeView class.
	 * 
	 * @param manager
	 */
	
	public HomeView(ViewManager manager) {
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
		welcomeText = new JLabel("");
		firstRow.add(welcomeText);
		firstRow.add(Box.createHorizontalStrut(40));
		logOutButton = new JButton("Log Out");
		logOutButton.addActionListener(this);
		firstRow.add(logOutButton);
		this.add(firstRow);
		
		Box secondRow = new Box(BoxLayout.X_AXIS);
		InfoText = new JLabel("");
		secondRow.add(InfoText);
		this.add(secondRow);
		this.add(new Box(BoxLayout.X_AXIS));
		
		this.add(Box.createVerticalStrut(50));
		Box thirdRow = new Box(BoxLayout.X_AXIS);
		DepositButton = new JButton("Deposit");
		DepositButton.addActionListener(this);
		thirdRow.add(DepositButton);
		WithdrawButton = new JButton("Withdraw");
		WithdrawButton.addActionListener(this);
		thirdRow.add(WithdrawButton);
		TransferButton = new JButton("Transfer");
		TransferButton.addActionListener(this);
		thirdRow.add(TransferButton);
		this.add(thirdRow);
		this.add(Box.createVerticalGlue());
		
		this.add(Box.createVerticalStrut(40));
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
			this.account = null;
			manager.logOut();	
		}
		
		else if (source.equals(DepositButton)) {
			if (this.deposit()) {
				statusText.setText("Successful Deposit!");
			} else {
				statusText.setText("Unseccessful Deposit");
			}
		}
		
		else if (source.equals(WithdrawButton)) {
			if (this.withdraw()) {
				statusText.setText("Successful Withdraw!");
			} else {
				statusText.setText("Unseccessful Withdraw");
			}
		}
		
		else if (source.equals(TransferButton)) {
			if (this.transfer()) {
				statusText.setText("Successful Transfer!");
			} else {
				statusText.setText("Unseccessful Transfer");
			}
			String info = String.format("Account Number: %d, Balance: $%.2f", account.getAccountNumber(), account.getBalance());
			InfoText.setText(info);
		}
	}

	private boolean transfer() {
		String transfer = "";
		while (transfer != null) {
			transfer = JOptionPane.showInputDialog("How much would you like to deposit?\n (Enter ONLY numbers greater than 0 and less than your balance)");
			try {
				double transferAmount = Double.parseDouble(transfer);
				if (transferAmount <= account.getBalance() && transferAmount > 0) {
					String account = JOptionPane.showInputDialog("What account number would you like to transfer to?\n (Enter ONLY valid account numbers)");
					while (account != null) {
						try {
							long accountNum = Long.parseLong(account);
							return manager.transfer(accountNum, transferAmount);
						} catch (Exception e1) { }

					}
				}
			} catch (Exception e1) { }

		}
		return false;
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
			InfoText.setText(info);
			return manager.updateAccount(account);
		} else {
			return this.deposit();
		}
	}
	
	public boolean withdraw() {
		String withdraw = JOptionPane.showInputDialog("How much would you like to withdraw?\n (Enter ONLY numbers greater than 0 and less than your balance)");
		if (withdraw == null) {
			return false;
		}
		double withdrawAmount = 0;
		try {
			withdrawAmount = Double.parseDouble(withdraw);
		} catch (Exception e1) {
			return this.withdraw();
		}
		
		if(account.withdraw(withdrawAmount) == ATM.SUCCESS) {
			String info = String.format("Account Number: %d, Balance: $%.2f", account.getAccountNumber(), account.getBalance());
			InfoText.setText(info);
			return manager.updateAccount(account);
		} else {
			return this.withdraw();
		}
	}
	
	public void setAccount(BankAccount account) {
		this.account = account;
		if (this.account != null) {
			String welcome = String.format("Welcome %s %s!", account.getUser().getFirstName(), account.getUser().getLastName());
			welcomeText.setText(welcome);
			
			String info = String.format("Account Number: %d, Balance: $%.2f", account.getAccountNumber(), account.getBalance());
			InfoText.setText(info);
		} 
	}
}