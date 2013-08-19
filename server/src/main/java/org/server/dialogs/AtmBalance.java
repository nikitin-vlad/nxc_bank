package org.server.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.common.atms.Atm;

public class AtmBalance extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField atmBillName;
	private JTextField atmBillAmount;
	private JTable atmBillsTable;
	private Atm atm;

	public AtmBalance(Atm atm) {
		this.atm = atm;
		
		setAlwaysOnTop(true);
		setTitle("ATM Balance");
		setBounds(100, 100, 390, 374);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblBalanceAmount = new JLabel("Balance amount:");
		lblBalanceAmount.setBounds(10, 11, 90, 14);
		contentPanel.add(lblBalanceAmount);
		
		JLabel atmBalanceAmountLabel = new JLabel("0.00");
		atmBalanceAmountLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		atmBalanceAmountLabel.setBounds(96, 11, 128, 14);
		contentPanel.add(atmBalanceAmountLabel);
		
		JLabel lblBillName = new JLabel("Bill name");
		lblBillName.setBounds(10, 37, 46, 14);
		contentPanel.add(lblBillName);
		
		atmBillName = new JTextField();
		atmBillName.setBounds(58, 34, 86, 20);
		contentPanel.add(atmBillName);
		atmBillName.setColumns(10);
		
		JLabel lblBillAmount = new JLabel("Bill amount");
		lblBillAmount.setBounds(162, 37, 62, 14);
		contentPanel.add(lblBillAmount);
		
		atmBillAmount = new JTextField();
		atmBillAmount.setColumns(10);
		atmBillAmount.setBounds(220, 34, 86, 20);
		contentPanel.add(atmBillAmount);
		
		JButton atmBtnAddBill = new JButton("Add");
		atmBtnAddBill.setBounds(316, 33, 51, 23);
		contentPanel.add(atmBtnAddBill);
		
		JPanel atmBillsPanel = new JPanel();
		atmBillsPanel.setBounds(10, 62, 357, 230);
		contentPanel.add(atmBillsPanel);
		atmBillsPanel.setLayout(new BorderLayout(0, 0));
		
		String[] atmBillsColumnNames = {"Bill name", "Bill amount"};
		//Object[][] atmBillsTableData = this.atm.getBillsData();
		Object[][] atmBillsTableData = {
			{0, 0}
		};
		atmBillsTable = new JTable(atmBillsTableData, atmBillsColumnNames);
		
		JScrollPane atmBillsScrollPane = new JScrollPane(atmBillsTable);
		atmBillsTable.setFillsViewportHeight(true);
		atmBillsPanel.add(atmBillsScrollPane);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			
			JButton btnRefresh = new JButton("Refresh");
			buttonPane.add(btnRefresh);
		}
	}
}
