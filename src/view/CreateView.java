package view;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.stream.IntStream;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.ViewManager;
import model.User;
import model.BankAccount;

@SuppressWarnings("serial")
public class CreateView extends JPanel implements ActionListener {
	
	private ViewManager manager;		// manages interactions between the views, model, and database
	
	private JLabel errorMessageLabel;
	private JButton cancelButton;
	private JButton createButton;

	private JTextField firstName;
	private JTextField lastName;
	
	private JComboBox<String> day;
	private JComboBox<String> month;
	private JComboBox<String> year;
	
	private JTextField phone1;
	private JTextField phone2;
	private JTextField phone3;
	private JTextField address;
	private JTextField city;
	private JComboBox<String> state;
	private JTextField postal;
	private JPasswordField pinField;
	
	/**
	 * Constructs an instance (or object) of the CreateView class.
	 * 
	 * @param manager
	 */
	
	public CreateView(ViewManager manager) {
		super();
		
		this.manager = manager;
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		initialize();
	}
	
	///////////////////// PRIVATE METHODS /////////////////////////////////////////////
	
	/*
	 * Initializes the CreateView components.
	 */
	
	private void initialize() {
		errorMessageLabel = new JLabel();
		errorMessageLabel.setText("Please fill in all required fields");
		this.add(errorMessageLabel);
		initFirstNameField();
		initLastNameField();
		initBirthdayFields();
		initPhoneField();
		initAddressField();
		initCityField();
		initStateField();
		initPostalField();
		initPinField();
		initButtons();
		this.setAlignmentX(Component.LEFT_ALIGNMENT);;

	}
	
	private void initFirstNameField() {
		JPanel pane = new JPanel();
		JLabel label = new JLabel("First Name", SwingConstants.RIGHT);
		label.setLabelFor(firstName);
		label.setFont(new Font("DialogInput", Font.BOLD, 14));
	
		firstName = new JTextField(30);
		
		pane.add(label);
		pane.add(firstName);
		this.add(pane);
		
	}
	
	private void initLastNameField() {
		JPanel pane = new JPanel();
		JLabel label = new JLabel("Last Name", SwingConstants.RIGHT);
		label.setLabelFor(lastName);
		label.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		lastName = new JTextField(30);
		
		pane.add(label);
		pane.add(lastName);
		this.add(pane);
	}
	
	//TODO maybe: make dob start with blank combo boxes and only let you continue if you fill em out
	private void initBirthdayFields() {
		JPanel pane = new JPanel();
		int[] days = IntStream.range(1,32).toArray();
		String[] stringdays = Arrays.toString(days).replace("[", "[,").split("[\\[\\]]")[1].split(",");
		
		int[] years = IntStream.range(1900,2019).toArray();
		String[] stringyears = Arrays.toString(years).replace("[", "[,").split("[\\[\\]]")[1].split(",");
		
		String[] stringmonths = {"", "January", "Febuary", "March", "April", "May", "June", "July",
		                          "August", "September", "October", "November", "December"};
		
		
		JLabel label = new JLabel("Birthday", SwingConstants.CENTER);
		label.setLabelFor(day);
		label.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		day = new JComboBox<String>(stringdays);
		month = new JComboBox<String>(stringmonths);
		year = new JComboBox<String>(stringyears);
		
		pane.add(label);
		pane.add(day);
		pane.add(month);
		pane.add(year);
		this.add(pane);
	}
	
	private void initPhoneField() {
		JPanel pane = new JPanel();
		JLabel label = new JLabel("Phone Num.", SwingConstants.CENTER);
		label.setLabelFor(phone1);
		label.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		phone1 = new JTextField(3);
		phone2 = new JTextField(3);
		phone3 = new JTextField(4);
		pane.add(label);
		pane.add(phone1);
		pane.add(phone2);
		pane.add(phone3);
		this.add(pane);
	}
	
	
	private void initButtons() {	
		JPanel pane = new JPanel();

		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		createButton = new JButton("Create Account");
		createButton.addActionListener(this);

		pane.add(cancelButton);
		pane.add(createButton);
		this.add(pane);

	}
	
	private void initAddressField() {
		JPanel pane = new JPanel();
		JLabel label = new JLabel("Street Address", SwingConstants.RIGHT);
		label.setLabelFor(address);
		label.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		address = new JTextField(30);
		
		pane.add(label);
		pane.add(address);
		this.add(pane);
	}
	
	private void initCityField() {
		JPanel pane = new JPanel();
		JLabel label = new JLabel("City", SwingConstants.RIGHT);
		label.setLabelFor(city);
		label.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		city = new JTextField(30);
		
		pane.add(label);
		pane.add(city);
		this.add(pane);
	}
	
	private void initStateField() {
		JPanel pane = new JPanel();
		JLabel label = new JLabel("State", SwingConstants.RIGHT);
		label.setLabelFor(state);
		label.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		String[] abbrivs = {"", "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"};
		state = new JComboBox<String>(abbrivs);
		
		pane.add(label);
		pane.add(state);
		this.add(pane);
	}
	
	private void initPostalField() {
		JPanel pane = new JPanel();
		JLabel label = new JLabel("Postal Code", SwingConstants.RIGHT);
		label.setLabelFor(postal);
		label.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		postal = new JTextField(5);
		
		pane.add(label);
		pane.add(postal);
		this.add(pane);
	}
	private void initPinField() {
		JPanel pane = new JPanel();
		JLabel label = new JLabel("PIN", SwingConstants.RIGHT);
		label.setLabelFor(pinField);
		label.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		pinField = new JPasswordField(4);
		
		pane.add(label);
		pane.add(pinField);
		this.add(pane);
	}

	/*
	 * CreateView is not designed to be serialized, and attempts to serialize will throw an IOException.
	 * 
	 * @param oos
	 * @throws IOException
	 */
	
	private void writeObject(ObjectOutputStream oos) throws IOException {
		throw new IOException("ERROR: The CreateView class is not serializable.");
	}
	
	///////////////////// OVERRIDDEN METHODS //////////////////////////////////////////
	
	/*
	 * Responds to button clicks and other actions performed in the CreateView.
	 * 
	 * @param e
	 */
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if (source.equals(cancelButton)) {
			manager.switchTo(ATM.LOGIN_VIEW);
			this.removeAll();
			initialize();
		} else if(source.equals(createButton)) {
			boolean create = true;

			String first = firstName.getText();
			String last = firstName.getText();
			if (first.equals(null) || last.equals(null) || first.length() > 15 || last.length() > 20) {
				errorMessageLabel.setText("Name Formatted Incorrectly - If your first name is greater than 15 characters or your last is greater than 20 please only enter the first 15/20 characters.");
				create = false;
			} 
			
			int dob = 0;
			if (year.getSelectedIndex() != 0 || day.getSelectedIndex() != 0 || month.getSelectedIndex() != 0) {
				dob = Integer.parseInt((""+(year.getSelectedIndex()+1899)+(month.getSelectedIndex()+0)+(day.getSelectedIndex())));
			} else {
				errorMessageLabel.setText("Fill in all fields (Birthday missing)");
				create = false;

			}
			long phone = 0;
			if( !phone1.getText().matches("\\d{3}") || !phone2.getText().matches("\\d{3}") || !phone3.getText().matches("\\d{4}")) {
				errorMessageLabel.setText("Phone Number Formatted Incorrectly");
				create = false;
			} else {
				phone = Long.parseLong((phone1.getText()+phone2.getText()+phone3.getText()));
			}
			
			
			String a = address.getText();
			String c = city.getText();
			String s = state.getItemAt(state.getSelectedIndex());
			String zip = postal.getText();
			//TODO -- check if postal is numeric
			if(a.equals(null) || c.equals(null) || s.equals("") || !zip.matches("\\d{5}") || a.length() > 30 || c.length() > 30 || zip.length() != 5) {
				errorMessageLabel.setText("Residency Formatted Incorrectly");
				create = false;
			}
			
			//TODO check if the length is 4
			char[] pinChars = null;
			if(!pinField.getPassword().equals(null)) {
				pinChars= pinField.getPassword();
				for( char digit : pinChars) {
					if (!Character.isDigit(digit)) {
						errorMessageLabel.setText("PIN Formatted Incorrectly");
						create = false;
					}
				}
			}
			
			long num = 0;
			try {
				num = manager.maxAccountNumber()+1;
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			if(create) {
				String pinString = "";
				for (char ch : pinChars) {
					pinString += ch;
				}
				int pin = Integer.parseInt(pinString); 
				User user = new User(pin, dob, phone, first, last, a, c, s, zip);
				BankAccount acc = new BankAccount('Y',num,0,user);
				manager.insertAccount_wrapp(acc);
				manager.login(String.valueOf(num), pinChars);
				this.removeAll();
				initialize();
			}
		}
		
		// TODO
		//
		// this is where you'll setup your action listener, which is responsible for
		// responding to actions the user might take in this view (an action can be a
		// user clicking a button, typing in a textfield, etc.).
		//
		// feel free to use my action listener in LoginView.java as an example.
	}
}