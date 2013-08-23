package org.server.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;

import org.common.atms.Atm;
import org.server.Server;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AtmAdd extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField atmIdentifier;
	private JCheckBox atmStatus;
	private boolean editMode;
	private Atm atm;

	public AtmAdd() {
		init();
	}

	public AtmAdd(boolean mode, Atm atm) {
		editMode = mode;
		this.atm = atm;
		init();
	}	
	
	private void init() {
		addWindowListener(new WindowAdapter() {
            public void windowDeactivated(WindowEvent e) {
                Server.getAtms().setBlocked(false);
                Server.updateData();
            }
		});
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);		
		setTitle((!editMode) ? "Add new ATM" : "Edit ATM");
		setBounds(100, 100, 213, 164);
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
			atmIdentifier.setBounds(10, 36, 177, 20);
			if (editMode) {
				atmIdentifier.setText(atm.getId());
				atmIdentifier.setEnabled(false);
			}
			contentPanel.add(atmIdentifier);
			atmIdentifier.setColumns(10);
		}
		{
			atmStatus = new JCheckBox("Enabled");
			atmStatus.setSelected(true);
			atmStatus.setBounds(10, 63, 97, 23);
			if (editMode) {
				atmStatus.setSelected(atm.getStatus());
			}
			contentPanel.add(atmStatus);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton((!editMode) ? "OK" : "Save");
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
		AtmAdd.this.dispose();
	}
	
	private ActionListener submit() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (atmIdentifier.getText().equals("")) {
					close();
					return;
				}
				if (editMode) {
					atm.setStatus(atmStatus.isSelected());
				} else {
					if (Server.getAtms().isExisting(atmIdentifier.getText())) {
						JOptionPane.showMessageDialog(null,
							    "ATM with such identifier already existed. Change please identifier and try again.",
							    "Duplicate error",
							    JOptionPane.ERROR_MESSAGE);
						return;
					}
					Atm atm = new Atm();
					atm.setId(atmIdentifier.getText());
					atm.setStatus(atmStatus.isSelected());
					Server.getAtms().addAtm(atm);
				}
				close();
			}
		};
	}

	public void setEditMode(boolean mode) {
		editMode = mode;
	}
	
	public boolean getEditMode() {
		return editMode;
	}	
}
