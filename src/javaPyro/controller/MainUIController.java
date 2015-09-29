package javaPyro.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import javaPyro.view.MainUI;

public class MainUIController {
	private MainUI _ui;
	private List<String[]> _input;
	private final MainController _mainController;

	public MainUIController(MainController controller) {
		_mainController = controller;
		_ui = new MainUI();

		registerListener();

		_ui.getMainFrame().pack();
		_ui.getMainFrame().setVisible(true);
	}

	private void registerListener() {
		_ui.getStartButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_mainController.startShow();
			}
		});

		_ui.getOpenButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("ZPL Files", "zpl");
				fileChooser.setFileFilter(filter);
				int returnVal = fileChooser.showOpenDialog(_ui.getMainFrame());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					_input = _mainController.openZPLFile(fileChooser.getSelectedFile());
					_mainController.calculateShow(_input);
					fillShowTable(_input);
				}
			}
		});
	}

	public void fillShowTable(List<String[]> input) {
		DefaultTableModel tableModel = (DefaultTableModel) _ui.getShowTable().getModel();
		int i = 0;
		while (i < input.size()) {
			for (String[] strA : input) {
				tableModel.addRow(strA);
				i++;
			}
		}
	}
}
