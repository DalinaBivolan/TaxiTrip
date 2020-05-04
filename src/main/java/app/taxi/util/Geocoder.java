package app.taxi.util;

import com.teamdev.jxmaps.ControlPosition;
import com.teamdev.jxmaps.GeocoderCallback;
import com.teamdev.jxmaps.GeocoderRequest;
import com.teamdev.jxmaps.GeocoderResult;
import com.teamdev.jxmaps.GeocoderStatus;
import com.teamdev.jxmaps.InfoWindow;
import com.teamdev.jxmaps.LatLng;
import com.teamdev.jxmaps.Map;
import com.teamdev.jxmaps.MapOptions;
import com.teamdev.jxmaps.MapReadyHandler;
import com.teamdev.jxmaps.MapStatus;
import com.teamdev.jxmaps.MapTypeControlOptions;
import com.teamdev.jxmaps.Marker;
import com.teamdev.jxmaps.swing.MapView;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicTextFieldUI;
import java.awt.*;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * This example demonstrates how to geocode coordinates by an address and vice versa.
 */
    
public class Geocoder extends MapView {

    /**
	 * TO DO 
	 * Fix the latitude and longitude. The lists are wrong!
	 */
	private static final long serialVersionUID = 1L;

	private static final String INITIAL_LOCATION = "New York";

    private OptionsWindow optionsWindow;
    
    private LatLng location = null;
    private static ArrayList<LatLng> pickupAddresses = new ArrayList<>();
    private static ArrayList<LatLng> dropoffAddresses = new ArrayList<>();

	public static ArrayList<LatLng> getPickupAddresses() {
		return pickupAddresses;
	}
	
	public static ArrayList<LatLng> getDropoffAddresses() {
		return dropoffAddresses;
	}

	public Geocoder() {
        // Setting of a ready handler to MapView object. onMapReady will be called when map initialization is done and     
        // the map object is ready to use. Current implementation of onMapReady customizes the map object.
        setOnMapReadyHandler(new MapReadyHandler() {
            public void onMapReady(MapStatus status) {
                // Getting the associated map object
                final Map map = getMap();
                // Setting initial zoom value
                map.setZoom(7.0);
                // Creating a map options object
                MapOptions options = new MapOptions();
                // Creating a map type control options object
                MapTypeControlOptions controlOptions = new MapTypeControlOptions();
                // Changing position of the map type control
                controlOptions.setPosition(ControlPosition.TOP_RIGHT);
                // Setting map type control options
                options.setMapTypeControlOptions(controlOptions);
                // Setting map options
                map.setOptions(options);
                
                performGeocode(INITIAL_LOCATION);
            }
        });
    }

    @Override
    public void addNotify() {
        super.addNotify();

        optionsWindow = new OptionsWindow(this, new Dimension(350, 40)) {
            @Override
            public void initContent(JWindow contentWindow) {
                JPanel content = new JPanel(new GridBagLayout());
                content.setBackground(Color.white);

                Font robotoPlain13 = new Font("Roboto", 0, 13);
                final JTextField searchFieldCurrentLocation = new JTextField();
                //searchFieldCurrentLocation.setText(INITIAL_LOCATION);
                searchFieldCurrentLocation.setToolTipText("Enter pickup address...");
                searchFieldCurrentLocation.setBorder(BorderFactory.createEmptyBorder());
                searchFieldCurrentLocation.setFont(robotoPlain13);
                searchFieldCurrentLocation.setForeground(new Color(0x21, 0x21, 0x21));
                searchFieldCurrentLocation.setUI(new SearchFieldUI(searchFieldCurrentLocation));
                
                final JTextField searchFieldDestLoc = new JTextField();
                //searchFieldDestLoc.setText(INITIAL_LOCATION);
                searchFieldDestLoc.setToolTipText("Enter dropoff address...");
                searchFieldDestLoc.setBorder(BorderFactory.createEmptyBorder());
                searchFieldDestLoc.setFont(robotoPlain13);
                searchFieldDestLoc.setForeground(new Color(0x21, 0x21, 0x21));
                searchFieldDestLoc.setUI(new SearchFieldUI(searchFieldDestLoc));

                final JButton searchButton = new JButton();
                searchButton.setIcon(new ImageIcon(Geocoder.class.getResource("../../../search.png")));
                searchButton.setRolloverIcon(new ImageIcon(Geocoder.class.getResource("../../../search_hover.png")));     
                searchButton.setBorder(BorderFactory.createEmptyBorder());
                searchButton.setUI(new BasicButtonUI());
                searchButton.setOpaque(false);
                ActionListener searchActionListener = new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        LatLng location = performGeocode(searchFieldCurrentLocation.getText());
                        System.out.println("Pickup address: lat: "+location.getLat()+" long: "+location.getLng());
                        pickupAddresses.add(location);
                    }
                };
                searchButton.addActionListener(searchActionListener);
                searchFieldDestLoc.addActionListener(searchActionListener);
                
                final JButton searchButton2 = new JButton();
                searchButton2.setIcon(new ImageIcon(Geocoder.class.getResource("../../../search.png")));
                searchButton2.setRolloverIcon(new ImageIcon(Geocoder.class.getResource("../../../search_hover.png")));     
                searchButton2.setBorder(BorderFactory.createEmptyBorder());
                searchButton2.setUI(new BasicButtonUI());
                searchButton2.setOpaque(false);
                ActionListener searchActionListener2 = new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                    	LatLng location = performGeocode(searchFieldDestLoc.getText());
                    	System.out.println("Dropoff address: lat: "+location.getLat()+" long: "+location.getLng());
                    	dropoffAddresses.add(location);
                    }
                };
                searchButton2.addActionListener(searchActionListener2);
                searchFieldDestLoc.addActionListener(searchActionListener2);

                content.add(searchFieldCurrentLocation, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0,
                        GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(11, 11, 11, 0), 0, 0));
                content.add(searchButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(11, 0, 11, 11), 0, 0));
                content.add(searchFieldDestLoc, new GridBagConstraints(2, 0, 1, 1, 1.0, 0.0,
                        GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(11, 11, 11, 0), 0, 0));
                content.add(searchButton2, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(11, 0, 11, 11), 0, 0));

                contentWindow.getContentPane().add(content);
            }

            @Override
            protected void updatePosition() {
                if (parentFrame.isVisible()) {
                    Point newLocation = parentFrame.getContentPane().getLocationOnScreen();
                    newLocation.translate(56, 11);
                    contentWindow.setLocation(newLocation);
                    contentWindow.setSize(340, 40);
                }
            }
        };
    }

    class SearchFieldUI extends BasicTextFieldUI {
        private final JTextField textField;

        public SearchFieldUI(JTextField textField) {
            this.textField = textField;
        }

        @Override
        protected void paintBackground(Graphics g) {
            super.paintBackground(g);
            String toolTipText = textField.getToolTipText();
            String text = textField.getText();
            if (toolTipText != null && text.isEmpty()) {
                paintPlaceholderText(g, textField);
            }
        }

        protected void paintPlaceholderText(Graphics g, JComponent c) {
            g.setColor(new Color(0x75, 0x75, 0x75));
            g.setFont(c.getFont());
            String text = textField.getToolTipText();
            if (g instanceof Graphics2D) {
                Graphics2D graphics2D = (Graphics2D) g;
                graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            }
            g.drawString(text, 0, 14);
        }
    }

    @Override
    public void removeNotify() {
        super.removeNotify();
        optionsWindow.dispose();
    }

    private LatLng performGeocode(String text) {
        // Getting the associated map object
        final Map map = getMap();
        // Creating a geocode request
        GeocoderRequest request = new GeocoderRequest();
        // Setting address to the geocode request
        request.setAddress(text);
        
        // Geocoding position by the entered address
        getServices().getGeocoder().geocode(request, new GeocoderCallback(map) {
            @Override
            public void onComplete(GeocoderResult[] results, GeocoderStatus status) {
                // Checking operation status
                if ((status == GeocoderStatus.OK) && (results.length > 0)) {
                    // Getting the first result
                    GeocoderResult result = results[0];
                    // Getting a location of the result
                    location = result.getGeometry().getLocation();
                    // Setting the map center to result location
                    map.setCenter(location);
                    // Creating a marker object
                    Marker marker = new Marker(map);
                    // Setting position of the marker to the result location
                    marker.setPosition(location);
                    // Creating an information window
                    InfoWindow infoWindow = new InfoWindow(map);
                    // Putting the address and location to the content of the information window
                    infoWindow.setContent("<b>" + result.getFormattedAddress() + "</b><br>" + location.toString());
                    // Moving the information window to the result location
                    infoWindow.setPosition(location);
                    // Showing of the information window
                    infoWindow.open(map, marker);
                    
                    System.out.println("Latitudine din perform geocode: " + location.getLat());
                    System.out.println("Longitudine : " + location.getLng());
                    
                }
            }
        });
        
        return location;
    }


    public static void main(String[] args) {
        final Geocoder mapView = new Geocoder();

        JFrame frame = new JFrame("Geocoder");

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(mapView, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
