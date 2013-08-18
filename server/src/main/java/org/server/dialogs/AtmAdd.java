package org.server.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;

import org.common.atms.Atm;
import org.server.Server;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AtmAdd extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField atmIdentifier, atmSSLFingerprint;
	private JCheckBox atmStatus;

	public AtmAdd() {
		setTitle("Add new ATM");
		setAlwaysOnTop(true);
		setBounds(100, 100, 213, 225);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("ATM Identifier");
			lblNewLabel.setBounds(10, 11, 143, 14);
			contentPanel.add(lblNewLabel);
		}
		{
			atmIdentifier = new JTextField();
			atmIdentifier.setBounds(10, 36, 143, 20);
			contentPanel.add(atmIdentifier);
			atmIdentifier.setColumns(10);
		}
		{
			JLabel lblAtmSslKey = new JLabel("ATM SSL Key fingerprint");
			lblAtmSslKey.setBounds(10, 67, 143, 14);
			contentPanel.add(lblAtmSslKey);
		}
		{
			atmSSLFingerprint = new JTextField();
			atmSSLFingerprint.setColumns(10);
			atmSSLFingerprint.setBounds(10, 92, 175, 20);
			contentPanel.add(atmSSLFingerprint);
		}
		{
			atmStatus = new JCheckBox("Enabled");
			atmStatus.setSelected(true);
			atmStatus.setBounds(10, 119, 97, 23);
			contentPanel.add(atmStatus);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(submit());
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						close();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	private void close() {
		dispose();
		Server.getAtms().setBlocked(false);
	}
	
	private ActionListener submit() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (atmIdentifier.getText().equals("")) {
					close();
					return;
				}
				if (atmSSLFingerprint.getText().equals("")) {
					close();
					return;
				}				
				Atm atm = new Atm();
				atm.setBalance(0.00);
				atm.setId(atmIdentifier.getText());
				atm.setSslKeyMark(atmSSLFingerprint.getText());
				atm.setStatus(true);
				Server.getAtms().addAtm(atm);
				close();
				Server.updateData();
			}
		};
	}

}
