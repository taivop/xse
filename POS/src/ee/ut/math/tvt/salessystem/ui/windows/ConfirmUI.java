package ee.ut.math.tvt.salessystem.ui.windows;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;
import ee.ut.math.tvt.salessystem.ui.panels.PurchaseItemPanel;
import ee.ut.math.tvt.salessystem.ui.tabs.PurchaseTab;

public class ConfirmUI extends JFrame {

	private static final Logger log = Logger.getLogger(ConfirmUI.class);
	/*
	 * will show the total sum of the order. will have a field to enter the
	 * payment amount. will show the change amount (tagastusraha). will have the
	 * buttons to accept or cancel the payment. If the payment is accepted then
	 * order should be accepted and saved. If the payment is canceled, then the
	 * screen should be closed/hided and the shopping cart should restore the
	 * state when it was left.
	 */
	Double changeValue;
	
	JLabel changeLabel;

	JButton confirmButton;

	JButton cancelButton;

	PurchaseTab purchaseTab;

	public ConfirmUI(final PurchaseTab purchaseTab) {

		this.purchaseTab = purchaseTab;

		setTitle("Confirm");
		setSize(300, 300);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());

		confirmButton = new JButton("Confirm");
		cancelButton = new JButton("Cancel");

		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// check if we had enough money
				if (changeValue != null) {		// check if changeValue is set
					
					if (changeValue < 0) {
						String notEnoughMoneyMessage = "Amount paid was smaller than order sum.";
						JOptionPane.showMessageDialog(null,
								notEnoughMoneyMessage, "Warning",
								JOptionPane.WARNING_MESSAGE);
					} else {
						// Save order
						purchaseTab.updateHistory();
						dispose();
					}
				} else {
					String noPaymentInsertedMessage = "Amount paid not entered. (You must press ENTER after entering the sum.)";
					JOptionPane.showMessageDialog(null,
							noPaymentInsertedMessage, "Warning",
							JOptionPane.WARNING_MESSAGE);
				}

			}
		});

		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		GridBagConstraints gc1 = getConstraintsForPaymentInformation();
		GridBagConstraints gc2 = getConstraintsForButtons();

		panel.add(drawPaymentInf(), gc1);
		panel.add(drawButtons(), gc2);

		add(panel);

		setVisible(true);

	}

	JPanel drawPaymentInf() {
		JPanel paymentInformation = new JPanel();
		paymentInformation.setSize(100, 100);
		paymentInformation.setLayout(new GridLayout(3, 2));
		paymentInformation.setBorder(BorderFactory
				.createTitledBorder("Payment information"));

		// Current shopping cart after confirm
		PurchaseInfoTableModel currentPurchaseTable = PurchaseItemPanel
				.getModel().getCurrentPurchaseTableModel();

		// Sum the shopping cart
		final double calculatedSum = currentPurchaseTable.sumRow(4);

		JLabel sum = new JLabel(String.valueOf(calculatedSum));
		paymentInformation.add(new JLabel("Sum"));
		paymentInformation.add(sum);

		// Enter amount of money given by customer

		final JTextField amount = new JTextField();

		paymentInformation.add(new JLabel("Amount paid"));
		paymentInformation.add(amount);

		final String incorrectDataConfirmUIMessage = String
				.format("You inserted incorrect data. Price and quantity must be nonnegative");

		final String incorrectPaymentAmountMessage = String
				.format("Inserted payment is less than order cost.");

		amount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!amount.getText().equals("") && isNumeric(amount.getText().replace(',', '.'))
						&& Double.parseDouble(amount.getText().replace(',', '.')) >= 0) {
			

					double paid = Math.round(Double.valueOf(amount.getText().replace(',', '.'))* 100.0) / 100.0;
					
					changeValue = Math.round((paid - calculatedSum)*100.0)/100.0;
		
					if (changeValue >= 0) {					// show change amount only if entered amount was large enough
						String change = changeValue.toString();
						changeLabel.setText(change);
					} else {
						String notEnoughMoneyMessage = "Amount paid was smaller than order sum.";
						JOptionPane.showMessageDialog(null,
								notEnoughMoneyMessage, "Warning",
								JOptionPane.WARNING_MESSAGE);
					
					}
					
					

				} else {
					// Open pop-up window with warning

					JOptionPane.showMessageDialog(null,
							incorrectDataConfirmUIMessage, "Warning",
							JOptionPane.WARNING_MESSAGE);
					log.error(incorrectDataConfirmUIMessage);
				}
			}
		});

		changeLabel = new JLabel();
		paymentInformation.add(new JLabel("Change:"));
		paymentInformation.add(changeLabel);

		return paymentInformation;

	}

	JPanel drawButtons() {
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setSize(50, 20);
		buttonsPanel.setLayout(new GridLayout(1, 2));
		buttonsPanel.add(cancelButton);
		buttonsPanel.add(confirmButton);

		return buttonsPanel;
	}

	GridBagConstraints getConstraintsForPaymentInformation() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.anchor = GridBagConstraints.WEST;
		gc.weightx = 1;
		gc.weighty = 1;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.fill = GridBagConstraints.BOTH;

		return gc;
	}

	GridBagConstraints getConstraintsForButtons() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.fill = GridBagConstraints.BOTH;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.weightx = 1.0d;
		gc.weighty = 1.0;

		return gc;
	}

	boolean isNumeric(String o) {
		try {
			Double.parseDouble(o);

		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
}
