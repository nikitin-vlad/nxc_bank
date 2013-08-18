package org.server.panels;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

import org.server.dialogs.AccountAdd;
import org.server.dialogs.AtmAdd;

public class MainForm {

	public JFrame frmBankServer;
	
	private JButton accountsBtnAdd, accountsBtnEdit, accountsBtnRemove;
	private JButton atmsBtnAdd, atmsBtnEdit, atmsBtnRemove;
	
	private DefaultListModel<String> accountsListModel = new DefaultListModel<String>();
	private DefaultListModel<String> atmsListModel = new DefaultListModel<String>();
	private JList<String> accountsList, atmsList;

	public MainForm() {
		initialize();
	}

	private void initialize() {
		frmBankServer = new JFrame();
		frmBankServer.setAlwaysOnTop(true);
		frmBankServer.setTitle("Bank - Server side");
		frmBankServer.setResizable(false);
		frmBankServer.setBounds(100, 100, 640, 480);
		frmBankServer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBankServer.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 614, 431);
		frmBankServer.getContentPane().add(tabbedPane);
		
		
		/** ACCOUNTS **/
		JPanel accountsPanel = new JPanel();
		tabbedPane.addTab("Accounts", null, accountsPanel, null);
		tabbedPane.setEnabledAt(0, true);
		accountsPanel.setLayout(null);
		
		JToolBar accountsToolBar = new JToolBar();
		accountsToolBar.setBounds(0, 0, 609, 23);
		accountsToolBar.setFloatable(false);
		accountsPanel.add(accountsToolBar);
		
		accountsBtnAdd = new JButton("Add");
		accountsBtnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				try {
					AccountAdd dialog = new AccountAdd();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		accountsToolBar.add(accountsBtnAdd);
		
		accountsBtnEdit = new JButton("Edit");
		accountsBtnEdit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		accountsToolBar.add(accountsBtnEdit);
		
		accountsBtnRemove = new JButton("Remove");
		accountsBtnRemove.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		accountsToolBar.add(accountsBtnRemove);
		
		JPanel accountsListPanel = new JPanel();
		accountsListPanel.setBounds(10, 34, 190, 358);
		accountsPanel.add(accountsListPanel);
		accountsListPanel.setLayout(new BorderLayout(0, 0));
		
		accountsListModel.addElement("156165165161561");
		accountsListModel.addElement("816516516515665");
		accountsListModel.addElement("516516516515655");		
		accountsListModel.addElement("615156165156165");		
		accountsListModel.addElement("561561561561656");		
		
		accountsList = new JList<String>(accountsListModel);
		accountsList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
		        if (e.getValueIsAdjusting() == false) {
		        	 
		            if (accountsList.getSelectedIndex() != -1) {
		            	System.out.println(accountsListModel.getElementAt( accountsList.getSelectedIndex()).toString() );
		            }
		        }
			}
		});
		accountsList.setValueIsAdjusting(true);
		accountsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		accountsList.setSelectedIndex(0);
		accountsList.setVisibleRowCount(22);		
		
		JScrollPane accountsScrollPane = new JScrollPane(accountsList);
		accountsScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		accountsListPanel.add(accountsScrollPane);
		
		JPanel accountDetailsPanel = new JPanel();
		accountDetailsPanel.setBounds(210, 34, 389, 358);
		accountsPanel.add(accountDetailsPanel);

		
		
		/** ATMS **/
		JPanel atmsPanel = new JPanel();
		tabbedPane.addTab("Atms", null, atmsPanel, null);
		atmsPanel.setLayout(null);
		
		JToolBar atmsToolBar = new JToolBar();
		atmsToolBar.setFloatable(false);
		atmsToolBar.setBounds(0, 0, 609, 23);
		atmsPanel.add(atmsToolBar);
		
		atmsBtnAdd = new JButton("Add");
		atmsBtnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				try {
					AtmAdd dialog = new AtmAdd();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}				
			}
		});
		atmsToolBar.add(atmsBtnAdd);
		
		atmsBtnEdit = new JButton("Edit");
		atmsBtnEdit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		atmsToolBar.add(atmsBtnEdit);
		
		atmsBtnRemove = new JButton("Remove");
		atmsBtnRemove.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		atmsToolBar.add(atmsBtnRemove);
		
		JPanel atmsListPanel = new JPanel();
		atmsListPanel.setBounds(10, 34, 190, 358);
		atmsPanel.add(atmsListPanel);
		atmsListPanel.setLayout(new BorderLayout(0, 0));
		
		atmsListModel.addElement("ATM-1");
		atmsListModel.addElement("ATM-2");
		atmsListModel.addElement("ATM-3");		
		atmsListModel.addElement("ATM-4");		
		
		atmsList = new JList<String>(atmsListModel);
		atmsList.setValueIsAdjusting(true);
		atmsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		atmsList.setSelectedIndex(0);
		atmsList.setVisibleRowCount(22);		
		
		JScrollPane atmsScrollPane = new JScrollPane(atmsList);
		atmsScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		atmsListPanel.add(atmsScrollPane);
		
		JPanel atmsDetailsPanel = new JPanel();
		atmsDetailsPanel.setBounds(210, 34, 389, 358);
		atmsPanel.add(atmsDetailsPanel);
	}
}
