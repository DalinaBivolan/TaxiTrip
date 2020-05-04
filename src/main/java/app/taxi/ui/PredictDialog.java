package app.taxi.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.teamdev.jxmaps.LatLng;

import app.taxi.machine.learning.model.PredictModelDefault;
import app.taxi.util.Geocoder;
import app.taxi.util.ModelFeatures;
import app.taxi.util.DefaultModelFeatures;
import app.taxi.util.ModelParameters;
import hex.genmodel.easy.exception.PredictException;


public class PredictDialog extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private static final Insets insets = new Insets(2, 2, 2, 2);
	private static PredictDialog instance = null;
	private final static String MODEL_FEATURES_FILE_NAME = "modelFeatures.xml";
	
	private JComboBox<Integer> hourList;
	private JComboBox<String> weekDayList;
	private JTextArea rezultate = new JTextArea();
	
	private PredictDialog() {
		setTitle("PREDICT");
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("../../../taxi.png")));
     	setPreferredSize(new Dimension(400, 400));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBackground(new Color(238, 197, 34));		
		setMinimumSize(getSize());
		
		JPanel mainPanel = new JPanel(new GridBagLayout());
		//mainPanel.setBorder(BorderFactory.createLineBorder(Color.MAGENTA,2));
		mainPanel.setBackground(new Color(238, 197, 34));
		
		addComponent(mainPanel, createTopPanel(), 0, 0, 1, 1, 1, 0, GridBagConstraints.NORTH, GridBagConstraints.BOTH, insets);
		addComponent(mainPanel, createMiddlePanel(), 0, 1, 1, 1, 1, 0, GridBagConstraints.NORTH, GridBagConstraints.BOTH, insets);
		addComponent(mainPanel, createBottomPanel(), 0, 2, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.BOTH, insets);
		
		add(mainPanel);
		
		pack();
		setLocationRelativeTo(null);
	}
	
	private JPanel createTopPanel() {
		JPanel panel = new JPanel(new GridBagLayout());
		//panel.setBorder(BorderFactory.createLineBorder(Color.BLUE,2));
		panel.setBackground(new Color(238, 197, 34));
		
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
		
		JTextArea text = new JTextArea("Aflati in cat timp veti ajunge la destinatie."
				+ " Completati urmatoarele campuri obligatorii.");
		text.setEditable(false);		
		text.setLineWrap(true);
		text.setWrapStyleWord(true);
		//text.setBorder(BorderFactory.createLineBorder(Color.green,2));
		text.setBackground(getBackground());
		
		JLabel ora = new JLabel("Ora : ");
		//ora.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,2));
		ora.setBackground(getBackground());
		
		Integer[] pickupHour = new Integer[24];
		int inc=0;
		for(int i=0;i<24;i++){
		    pickupHour[i]= inc;
		    inc++;
		}
		hourList = new JComboBox<>(pickupHour);
		hourList.setSelectedIndex(0);	
		//hourList.setBorder(BorderFactory.createLineBorder(Color.ORANGE,2));
		
		JLabel ziua = new JLabel("Ziua : ");
		//ziua.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
		ziua.setBackground(getBackground());
		
		String[] pickupWeekDay = { "Luni", "Marti" , "Miercuri" , "Joi" , "Vineri",
				"Sambata" , "Duminica"};
		weekDayList = new JComboBox<String>(pickupWeekDay);
		weekDayList.setSelectedIndex(0);
		//weekDayList.setBorder(BorderFactory.createLineBorder(Color.PINK,2));
		
		addComponent(panel, backButton, 0, 0, 4, 1, 0, 0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, insets);
		addComponent(panel, text, 0, 1, 4, 1, 0, 0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, insets);
		addComponent(panel, ora, 0, 2, 1, 1, 1, 0, GridBagConstraints.EAST, GridBagConstraints.NONE, insets);
		addComponent(panel, hourList, 1, 2, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, insets);
		addComponent(panel, ziua, 2, 2, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, insets);
		addComponent(panel, weekDayList, 3, 2, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 90));		
		
		return panel;
	}
	
	private JPanel createMiddlePanel() {
		JPanel panel = new JPanel(new GridBagLayout());
		//panel.setBorder(BorderFactory.createLineBorder(Color.RED,2));
		panel.setBackground(new Color(238, 197, 34));
		
		JTextArea text = new JTextArea("Deschideti harta si introduceti adresa de preluare si"
				+ " adresa destinatiei.");
		text.setEditable(false);		
		text.setLineWrap(true);
		text.setWrapStyleWord(true);
		//text.setBorder(BorderFactory.createLineBorder(Color.YELLOW,2));
		text.setBackground(getBackground());
		
		JButton mapButton = new JButton("Open map");
		mapButton.setMnemonic(KeyEvent.VK_ENTER);
		mapButton.addActionListener(this);
		mapButton.setBackground(getBackground());
		
		addComponent(panel, text, 0, 0, 1, 1, 1, 0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, insets);
		addComponent(panel, mapButton, 0, 1, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, insets);
		
		return panel;
	}
	
	private JPanel createBottomPanel() {
		JPanel panel = new JPanel(new GridBagLayout());
		//panel.setBorder(BorderFactory.createLineBorder(Color.YELLOW,2));
		panel.setBackground(new Color(238, 197, 34));
		
		JTextArea text = new JTextArea("Aflati in cat timp veti ajunge la destinatie.");
		text.setEditable(false);
		text.setLineWrap(true);
		text.setWrapStyleWord(true);
		text.setBackground(getBackground());
		
		JButton predictCreatedModel = new JButton("Predict");
		predictCreatedModel.setToolTipText("Foloseste modelul creat anterior in pagine de TRAIN.");
		predictCreatedModel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Predict created model!");
				predictWithCreatedModel(rezultate);
				
			}
		});
		predictCreatedModel.setBackground(getBackground());
		
		JButton predictDefault = new JButton("Predict default");
		predictDefault.setToolTipText("Foloseste modelul implicit al aplicatiei.");
		predictDefault.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Predict default!");
				predictWithDefaultModel();
				
			}
		});
		predictDefault.setBackground(getBackground());
		
		 rezultate.setText("Aici vor fi afisate rezultatele.");
		 rezultate.setBackground(getBackground());
		
		addComponent(panel, text, 0, 0, 2, 1, 1, 0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, insets);
		addComponent(panel, predictDefault, 0, 1, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, insets);
		addComponent(panel, predictCreatedModel, 1, 1, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, insets);
		addComponent(panel, rezultate, 0, 2, 2, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL, insets);
		
		return panel;
	}
	
	private void addComponent(Container container, Component component, int gridx, int gridy,
		      int gridwidth, int gridheight, double weightx, double weigthy, int anchor, int fill, Insets insets) {
		    GridBagConstraints gbc = new GridBagConstraints(gridx, gridy, gridwidth, gridheight, weightx, weigthy,
		        anchor, fill, insets,  1, 1);
		    container.add(component, gbc);
		  }
	
	public static PredictDialog getInstance(){
        if(instance == null){
            instance = new PredictDialog();
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
          PredictDialog ex = new PredictDialog();
          ex.setVisible(true);
      });
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("map button pressed!");
		
		Geocoder mapView = new Geocoder();

        JFrame frame = new JFrame("Harta");

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(mapView, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setIconImage(Toolkit.getDefaultToolkit().
        		getImage(getClass().getResource("../../../taxi.png")));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
		
	}
	
	private void predictWithDefaultModel(){
		int pickupHour = hourList.getItemAt(hourList.getSelectedIndex());
		String weekDay = weekDayList.getItemAt(weekDayList.getSelectedIndex());
		LatLng pickupAddress = Geocoder.getPickupAddresses().get(Geocoder.getPickupAddresses().size()-1);
		LatLng dropoffAddress = Geocoder.getDropoffAddresses().get(Geocoder.getDropoffAddresses().size()-1);
		
		System.out.println("Collected data from predict dialog : \n");
		System.out.println("pickupHour: "+pickupHour+" weekDay: "+weekDay+"\n");
		System.out.println("PickupAddress : lat:"+pickupAddress.getLat()+" long: "+pickupAddress.getLng());
		System.out.println("DropoffAddress : lat: "+dropoffAddress.getLat()+" long: "+dropoffAddress.getLng());
		
		DefaultModelFeatures features = new DefaultModelFeatures(pickupHour, weekDay,
				pickupAddress.getLat(), pickupAddress.getLng(),
				dropoffAddress.getLat(), dropoffAddress.getLng());
		PredictModelDefault model = null;
		String value = "";
		try {
			model = new PredictModelDefault(features);
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		try {
			value = model.predict();
		} catch (PredictException e1) {
			e1.printStackTrace();
		}
		rezultate.setText(value);
	}
	
	private void predictWithCreatedModel(JTextArea text){
		int pickupHour = hourList.getItemAt(hourList.getSelectedIndex());
		String weekDay = weekDayList.getItemAt(weekDayList.getSelectedIndex());
		LatLng pickupAddress = Geocoder.getPickupAddresses().get(Geocoder.getPickupAddresses().size()-1);
		LatLng dropoffAddress = Geocoder.getDropoffAddresses().get(Geocoder.getDropoffAddresses().size()-1);
		
		System.out.println("Collected data from predict dialog : \n");
		System.out.println("pickupHour: "+pickupHour+" weekDay: "+weekDay+"\n");
		System.out.println("PickupAddress : lat:"+pickupAddress.getLat()+" long: "+pickupAddress.getLng());
		System.out.println("DropoffAddress : lat: "+dropoffAddress.getLat()+" long: "+dropoffAddress.getLng());
		
		DefaultModelFeatures features = new DefaultModelFeatures(pickupHour, weekDay,
				pickupAddress.getLat(), pickupAddress.getLng(),
				dropoffAddress.getLat(), dropoffAddress.getLng());
		ModelFeatures mf = new ModelFeatures(String.valueOf(features.getPickupHour()),
				String.valueOf(features.getWeekDay()),
				String.valueOf(features.getPickLat()),
				String.valueOf(features.getPickLong()),
				String.valueOf(features.getDropLat()), 
				String.valueOf(features.getDropLong()), 
				String.valueOf(features.getTotalDistance()),
				String.valueOf(features.getLatDiff()),
				String.valueOf(features.getLongDiff()), 
				String.valueOf(features.getLogHaversineDistance()),
				String.valueOf(features.getLogEuclidianDistance()), 
				String.valueOf(features.getLogManhattanDistance()));
		
		convertObjectToXml(mf, MODEL_FEATURES_FILE_NAME);
		
		
	}
	
	private File convertObjectToXml(ModelFeatures mf, String fileName){
		File file = new File(TrainDialog.DESKTOP_PATH + fileName);
		JAXBContext jaxbContext = null;
		try {
			jaxbContext = JAXBContext.newInstance(ModelFeatures.class);
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
			jaxbMarshaller.marshal(mf, file);
		} catch (JAXBException e1) {
			e1.printStackTrace();
		}
		
		return file;

	}
}
