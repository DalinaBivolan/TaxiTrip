package app.taxi.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.filechooser.FileSystemView;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import app.taxi.util.ModelParameters;
import app.taxi.util.SaveModelScript;
import app.taxi.util.TrainModelScript;

public class TrainDialog extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Insets insets = new Insets(2, 2, 2, 2);
	private static TrainDialog instance = null;
	public final static String TRAIN_MODEL_SCRIPT_NAME = "createXGBmodel.py";
	public final static String SERIALIZATON_FILE_NAME = "modelParameters.xml";
	public final static String DESKTOP_PATH = "C:/Users/Dalina/Desktop/";
	public final static String SAVE_MODEL_SCRIPT_NAME = "saveXGBmodel.py";
	
	private File selectedTrainFile;
	private JComboBox<String> etaValues;
	private JComboBox<String> objectiveValues;
	private JComboBox<String> maxDepthValues;
	private JComboBox<String> boosterValues;
	private JComboBox<String> silentValues;
	private JComboBox<String> nthreadValues; 
	private JComboBox<String> subsampleValues;
	private JComboBox<String> colsampleByTreeValues;
	private JComboBox<String> evalMetricValues;
	private File selectedTestFile;
	private static File selectedSaveFile;

	private TrainDialog() {
		setTitle("TRAIN");
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("../../../taxi.png")));
		setPreferredSize(new Dimension(400, 400));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBackground(new Color(238, 197, 34));		
		setMinimumSize(getSize());


		JPanel mainPanel = new JPanel(new GridBagLayout());
		//mainPanel.setBorder(BorderFactory.createLineBorder(Color.MAGENTA,2));
		mainPanel.setBackground(new Color(238, 197, 34));

		JButton backButton = new JButton("Back");
		backButton.setBackground(getBackground());
		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				WelcomeDialog dialog = WelcomeDialog.getInstance();
				dialog.setVisible(true);
				dispose();
			}
		});

		addComponent(mainPanel, backButton, 0, 0, 1, 1, 0, 0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, insets);
		addComponent(mainPanel, createTopPanel(), 0, 1, 1, 1, 1, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, insets);
		addComponent(mainPanel, createMiddlePanel(), 0, 2, 1, 1, 0, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, insets);
		addComponent(mainPanel, createBottomPanel(), 0, 3, 1, 1, 0, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, insets);

		add(mainPanel);

		pack();
		setLocationRelativeTo(null);

	}

	private JPanel createTopPanel(){
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBackground(getBackground());
		//panel.setBorder(BorderFactory.createLineBorder(Color.BLUE,2));

		JLabel trainPath = new JLabel("Incarcati datele de TRAIN : ");
		trainPath.setBackground(getBackground());

		JTextField pathTextField = new JTextField();
		pathTextField.setEditable(true);


		ImageIcon image = new ImageIcon(this.getClass().
				getResource("../../../Open16.png"));
		JLabel chooseLabel = new JLabel(image);
		chooseLabel.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {
				chooseLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView()
						.getHomeDirectory());
				jfc.setDialogTitle("Datele de TRAIN");

				int returnValue = jfc.showOpenDialog(null);

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					selectedTrainFile = jfc.getSelectedFile();
					pathTextField.setText(selectedTrainFile.getAbsolutePath());
				}
			}
		});

		JLabel algLabel = new JLabel("Selectati algoritmul : ");
		algLabel.setBackground(getBackground());

		String[] algorithmNames = { "XGBoost"};
		JComboBox<String> algList = new JComboBox<String>(algorithmNames);
		algList.setSelectedIndex(0);

		addComponent(panel, trainPath, 0, 0, 1, 1, 0, 0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, insets);
		addComponent(panel, pathTextField, 1, 0, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, insets);
		addComponent(panel, chooseLabel, 2, 0, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.NONE, insets);

		addComponent(panel, algLabel, 0, 1, 1, 1, 0, 0, GridBagConstraints.FIRST_LINE_END, GridBagConstraints.NONE, insets);
		addComponent(panel, algList, 1, 1, 2, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, insets);

		return panel;
	}

	private JPanel createMiddlePanel(){
		JPanel mainPanel = new JPanel(new GridBagLayout());
		mainPanel.setBackground(getBackground());
		
		JLabel parametersLabel = new JLabel("Reglati parametrii : ");
		
		JPanel tunningPanel = new JPanel(new GridBagLayout());
		
		JLabel  etaParameter = new JLabel("eta");
		String[] eta = {"0.3" , "0.1" , "0.2"};
		etaValues = new JComboBox<String>(eta);
		etaValues.setEditable(true);
		etaValues.setSelectedIndex(0);
		etaValues.setToolTipText("De obicei ia valori intre 0.01 si 0.3.");
		
		JLabel objectiveParameter = new JLabel("objective");
		String[] objective  = {"reg:linear" , "binary:logistic"};
		objectiveValues = new JComboBox<String>(objective);
		objectiveValues.setEditable(true);
		objectiveValues.setSelectedIndex(0);
		
		JLabel maxDepthParameter = new JLabel("max_depth");
		String[] maxDepth  = {"6" , "5", "7", "4", "9"};
		maxDepthValues = new JComboBox<String>(maxDepth);
		maxDepthValues.setEditable(true);
		maxDepthValues.setSelectedIndex(0);
		maxDepthValues.setToolTipText("De obicei ia valori intre 3 si 10.");
		
		JLabel boosterParameter = new JLabel("booster");
		String[] booster  = {"gbtree", "gblinear"};
		boosterValues = new JComboBox<String>(booster);
		boosterValues.setEditable(false);
		boosterValues.setSelectedIndex(0);
		boosterValues.setToolTipText("De obicei se foloseste gbtree(rezultate mai bune).");
		
		JLabel silentParameter = new JLabel("silent");
		String[] silent  = {"0", "1"};
		silentValues = new JComboBox<String>(silent);
		silentValues.setEditable(false);
		silentValues.setSelectedIndex(0);
		silentValues.setToolTipText("E bine sa fie 0,pentru a afisa mesaje din timpul rularii.");
		
		JLabel nthreadParameter = new JLabel("nthread");
		String[] nthread  = {"-1"};
		nthreadValues = new JComboBox<String>(nthread);
		nthreadValues.setEditable(true);
		nthreadValues.setSelectedIndex(0);
		nthreadValues.setToolTipText("Folosit pentru procesarea paralela, ar trebui introdus numarul de nuclee(cores).");
		
		JLabel subsampleParameter = new JLabel("subsample");
		String[] subsample  = {"1", "0.7", "0.9"};
		subsampleValues = new JComboBox<String>(subsample);
		subsampleValues.setEditable(true);
		subsampleValues.setSelectedIndex(0);
		subsampleValues.setToolTipText("De obicei ia valori intre 0.5 si 1.");
		
		JLabel colsampleByTreeParameter = new JLabel("colsample_bytree ");
		String[] colsampleByTree  = {"1", "0.7", "0.9"};
		colsampleByTreeValues = new JComboBox<String>(colsampleByTree);
		colsampleByTreeValues.setEditable(true);
		colsampleByTreeValues.setSelectedIndex(0);
		colsampleByTreeValues.setToolTipText("De obicei ia valori intre 0.5 si 1.");
		
		JLabel evalMetricParameter = new JLabel("eval_metric ");
		String[] evalMetric  = {"rmse"};
		evalMetricValues = new JComboBox<String>(evalMetric);
		evalMetricValues.setEditable(false);
		evalMetricValues.setSelectedIndex(0);
		evalMetricValues.setToolTipText("Metrica folosita pentru validarea datelor..");
		
		addComponent(tunningPanel, etaParameter, 1, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, insets);
		addComponent(tunningPanel, etaValues, 2, 0, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, insets);
		
		addComponent(tunningPanel, objectiveParameter, 1, 1, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, insets);
		addComponent(tunningPanel, objectiveValues, 2, 1, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, insets);
		
		addComponent(tunningPanel, maxDepthParameter, 1, 2, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, insets);
		addComponent(tunningPanel, maxDepthValues, 2, 2, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, insets);
		
		addComponent(tunningPanel, boosterParameter, 1, 3, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, insets);
		addComponent(tunningPanel, boosterValues, 2, 3, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, insets);
		
		addComponent(tunningPanel, silentParameter, 1, 4, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, insets);
		addComponent(tunningPanel, silentValues, 2, 4, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, insets);
		
		addComponent(tunningPanel, nthreadParameter, 1, 5, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, insets);
		addComponent(tunningPanel, nthreadValues, 2, 5, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, insets);
		
		addComponent(tunningPanel, subsampleParameter, 1, 6, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, insets);
		addComponent(tunningPanel, subsampleValues, 2, 6, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, insets);
		
		addComponent(tunningPanel, colsampleByTreeParameter, 1, 7, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, insets);
		addComponent(tunningPanel, colsampleByTreeValues, 2, 7, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, insets);
		
		addComponent(tunningPanel, evalMetricParameter, 1, 8, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, insets);
		addComponent(tunningPanel, evalMetricValues, 2, 8, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, insets);
		
		JScrollPane scrollPane = new JScrollPane(tunningPanel);
		
		addComponent(mainPanel, parametersLabel, 0, 0, 1, 1, 0, 0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, insets);
		addComponent(mainPanel, scrollPane, 1, 0, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, insets);

		return mainPanel;
	}

	private JPanel createBottomPanel(){
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBackground(getBackground());
		//panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

		JLabel testPath = new JLabel("Incarcati datele de TEST : ");
		testPath.setBackground(getBackground());

		JTextField pathTextField = new JTextField();
		pathTextField.setEditable(true);


		ImageIcon image = new ImageIcon(this.getClass().
				getResource("../../../Open16.png"));
		JLabel chooseLabel = new JLabel(image);
		chooseLabel.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {
				chooseLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView()
						.getHomeDirectory());
				jfc.setDialogTitle("Datele de TEST");

				int returnValue = jfc.showOpenDialog(null);

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					selectedTestFile = jfc.getSelectedFile();
					pathTextField.setText(selectedTestFile.getAbsolutePath());
				}
			}
		});
		
		JLabel savePath = new JLabel("Alegeti unde sa salvati modelul : ");
		savePath.setBackground(getBackground());

		JTextField savePathTextField = new JTextField();
		savePathTextField.setEditable(true);


		ImageIcon jfcImage = new ImageIcon(this.getClass().
				getResource("../../../Open16.png"));
		JLabel saveChooseLabel = new JLabel(jfcImage);
		saveChooseLabel.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {
				saveChooseLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView()
						.getHomeDirectory());
				jfc.setDialogTitle("Save Model");

				int returnValue = jfc.showSaveDialog(null);

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					selectedSaveFile = jfc.getSelectedFile();
					savePathTextField.setText(selectedSaveFile.getAbsolutePath());
				}
			}
		});
		
		JTextArea validationField = new JTextArea("Aici veti vedea rezultatele...");
		validationField.setLineWrap(true);
		validationField.setWrapStyleWord(true);
		validationField.setCaretPosition(validationField.getText().length()-1);
		
		JButton trainButton = new JButton("Train model");
		trainButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ModelParameters mp = new ModelParameters(selectedTrainFile.getPath(),
						etaValues.getItemAt(etaValues.getSelectedIndex()),
						objectiveValues.getItemAt(objectiveValues.getSelectedIndex()),
						maxDepthValues.getItemAt(maxDepthValues.getSelectedIndex()),
						boosterValues.getItemAt(boosterValues.getSelectedIndex()), 
						silentValues.getItemAt(silentValues.getSelectedIndex()), 
						nthreadValues.getItemAt(nthreadValues.getSelectedIndex()), 
						subsampleValues.getItemAt(subsampleValues.getSelectedIndex()), 
						colsampleByTreeValues.getItemAt(colsampleByTreeValues.getSelectedIndex()), 
						evalMetricValues.getItemAt(evalMetricValues.getSelectedIndex()), 
						selectedTestFile.getPath());
				validationField.append("\nSe serializeaza parametrii introdusi...");
				validationField.update(validationField.getGraphics());
				convertObjectToXml(mp, SERIALIZATON_FILE_NAME);
				try {
					new TrainModelScript(TRAIN_MODEL_SCRIPT_NAME, validationField);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		JButton saveButton = new JButton("Save model");
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ModelParameters mp = new ModelParameters(selectedTrainFile.getPath(),
						etaValues.getItemAt(etaValues.getSelectedIndex()),
						objectiveValues.getItemAt(objectiveValues.getSelectedIndex()),
						maxDepthValues.getItemAt(maxDepthValues.getSelectedIndex()),
						boosterValues.getItemAt(boosterValues.getSelectedIndex()), 
						silentValues.getItemAt(silentValues.getSelectedIndex()), 
						nthreadValues.getItemAt(nthreadValues.getSelectedIndex()), 
						subsampleValues.getItemAt(subsampleValues.getSelectedIndex()), 
						colsampleByTreeValues.getItemAt(colsampleByTreeValues.getSelectedIndex()), 
						evalMetricValues.getItemAt(evalMetricValues.getSelectedIndex()), 
						selectedTestFile.getPath(),
						selectedSaveFile.getPath());
				validationField.append("\nSe serializeaza parametrii introdusi...");
				validationField.update(validationField.getGraphics());
				convertObjectToXml(mp, SERIALIZATON_FILE_NAME);
				try {
					new SaveModelScript(SAVE_MODEL_SCRIPT_NAME, validationField, selectedSaveFile.getPath());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		
		validationField.setEditable(false);

		addComponent(panel, testPath, 0, 0, 1, 1, 0, 0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, insets);
		addComponent(panel, pathTextField, 1, 0, 1, 1, 1, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, insets);
		addComponent(panel, chooseLabel, 2, 0, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.NONE, insets);
		
		addComponent(panel, savePath, 0, 1, 1, 1, 0, 0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, insets);
		addComponent(panel, savePathTextField, 1, 1, 1, 1, 1, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, insets);
		addComponent(panel, saveChooseLabel, 2, 1, 1, 1, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.NONE, insets);
		
		JScrollPane scrollPane = new JScrollPane(validationField);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		addComponent(panel, trainButton, 0, 2, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, insets);
		addComponent(panel, saveButton, 1, 2, 2, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, insets);
		addComponent(panel, scrollPane, 0, 3, 3, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.BOTH, insets);

		return panel;
	}

	public static TrainDialog getInstance(){
		if(instance == null){
			instance = new TrainDialog();
		}
		return instance;
	}

	public static void main( String[] args )
	{
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}

		EventQueue.invokeLater(() -> {
			TrainDialog ex = new TrainDialog();
			ex.setVisible(true);
		});
	}

	private void addComponent(Container container, Component component, int gridx, int gridy,
			int gridwidth, int gridheight, double weightx, double weigthy, int anchor, int fill, Insets insets) {
		GridBagConstraints gbc = new GridBagConstraints(gridx, gridy, gridwidth, gridheight, weightx, weigthy,
				anchor, fill, insets,  1, 1);
		container.add(component, gbc);
	}
	
	private File convertObjectToXml(ModelParameters mp, String fileName){
		File file = new File(DESKTOP_PATH + fileName);
		JAXBContext jaxbContext = null;
		try {
			jaxbContext = JAXBContext.newInstance(ModelParameters.class);
		} catch (JAXBException e4) {
			e4.printStackTrace();
		}
		Marshaller jaxbMarshaller = null;
		try {
			jaxbMarshaller = jaxbContext.createMarshaller();
		} catch (JAXBException e3) {
			e3.printStackTrace();
		}

		try {
			jaxbMarshaller.marshal(mp, file);
		} catch (JAXBException e1) {
			e1.printStackTrace();
		}
		
		return file;

	}
}
