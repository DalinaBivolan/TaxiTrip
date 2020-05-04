package app.taxi.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;

/**
 * The welcome dialog.
 * The first frame the users will see when the app will start.
 *
 */
public class WelcomeDialog extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private static final Insets insets = new Insets(2, 2, 2, 2);
	private boolean isClient = true;
	private final String TRAIN_MODE = "Manager[TRAIN]";
	private static WelcomeDialog instance = null;

	private WelcomeDialog() {
		setTitle("TaxiTrip");
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("../../../taxi.png")));
		setPreferredSize(new Dimension(400, 400));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBackground(new Color(238, 197, 34));		
		setMinimumSize(getSize());

		add(createMainPanel());

		pack();
		setLocationRelativeTo(null);
	}

	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new GridBagLayout());
		//panel.setBorder(BorderFactory.createLineBorder(Color.BLUE,2));
		panel.setBackground(new Color(238, 197, 34));

		ImageIcon image = new ImageIcon(this.getClass().
				getResource("../../../taxi_picture.png"));
		ImageIcon scaledImage =  new ImageIcon(image.getImage().
				getScaledInstance(200, 120, Image.SCALE_DEFAULT));
		JLabel pictureLabel = new JLabel();
		pictureLabel.setIcon(scaledImage);

		JTextArea text = new JTextArea("Alegeti modul de utilizare");
		text.setEditable(false);
		//text.setLineWrap(true);
		text.setWrapStyleWord(true);
		text.setBackground(getBackground());

		String[] typeOfUser = { "Manager[TRAIN]", "Client[PREDICT]" };
		JComboBox<String> userList = new JComboBox<String>(typeOfUser);
		userList.setSelectedIndex(1);		
		userList.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getItem().equals(TRAIN_MODE)) {
					isClient = false;			        
				} else {
					isClient = true;
				}

			}
		});

		JButton startAppButton = new JButton("Start");
		//startAppButton.requestFocusInWindow();
		startAppButton.setMnemonic(KeyEvent.VK_ENTER);
		startAppButton.addActionListener(this);
		startAppButton.setBackground(getBackground());

		addComponent(panel, pictureLabel, 0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, insets);
		addComponent(panel, text, 0, 1, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, insets);
		addComponent(panel, userList, 0, 2, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, insets);
		addComponent(panel, startAppButton, 0, 3, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, insets);		

		return panel;
	}

	private void addComponent(Container container, Component component, int gridx, int gridy,
			int gridwidth, int gridheight, double weightx, double weigthy, int anchor, int fill, Insets insets) {
		GridBagConstraints gbc = new GridBagConstraints(gridx, gridy, gridwidth, gridheight, weightx, weigthy,
				anchor, fill, insets,  1, 1);
		container.add(component, gbc);
	}

	public static WelcomeDialog getInstance(){
		if(instance == null){
			instance = new WelcomeDialog();
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
			WelcomeDialog ex = WelcomeDialog.getInstance();
			ex.setVisible(true);
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Pressed button!");
		if(isClient) {
			System.out.println("Show PREDICT dialog!");

			try {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			EventQueue.invokeLater(() -> {
				PredictDialog ex = PredictDialog.getInstance();
				ex.setVisible(true);
			});

			this.setVisible(false);

		} else {
			System.out.println("Show TRAIN dialog!");

			try {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			EventQueue.invokeLater(() -> {
				TrainDialog ex = TrainDialog.getInstance();
				ex.setVisible(true);
			});
			this.setVisible(false);
		}

	}
}
