package javaPyro.view;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class MainUI {
	private JFrame _mainFrame;
	private JPanel _mainPanel;
	private JPanel _bottomPanel;
	private JTable _showTable;
	private JButton _startButton = new JButton("Start Show");
	private JButton _openButton = new JButton("load Show");

	public MainUI() {
		_mainFrame = new JFrame();
		_mainPanel = new JPanel(new BorderLayout());
		_bottomPanel = new JPanel();
		_showTable = new JTable(new DefaultTableModel(new Object[] { "Module", "Channel", "Firing point",
				"effect start", "effect duration", "delay", "description", "position", "angle" }, 0));
		JScrollPane scrollPane = new JScrollPane(_showTable);

		_bottomPanel.add(_startButton);
		_bottomPanel.add(_openButton);

		_mainPanel.add(scrollPane, BorderLayout.CENTER);
		_mainPanel.add(_bottomPanel, BorderLayout.SOUTH);

		_mainFrame.add(_mainPanel);
	}

	public JButton getStartButton() {
		return _startButton;
	}

	public JButton getOpenButton() {
		return _openButton;
	}

	public JFrame getMainFrame() {
		return _mainFrame;
	}

	public JPanel getMainPanel() {
		return _mainPanel;
	}

	public JTable getShowTable() {
		return _showTable;
	}
}
