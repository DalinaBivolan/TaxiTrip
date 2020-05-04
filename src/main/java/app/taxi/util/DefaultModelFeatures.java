package app.taxi.util;

import org.apache.commons.math3.ml.distance.EuclideanDistance;
import org.apache.commons.math3.ml.distance.ManhattanDistance;

public class DefaultModelFeatures {
	public static final double R = 6372.8; // In kilometers
	
	private int pickupHour;
	private String weekDay;
	private double pickLat;
	private double pickLong;
	private double dropLat;
	private double dropLong;
	private double totalDistance;
	private double latDiff;
	private double longDiff;
	private double logHaversineDistance;
	private double logEuclidianDistance;
	private double logManhattanDistance;

	public DefaultModelFeatures(){}
	
	public DefaultModelFeatures(int pickupHour, String weekDay, double pickLat,
			double pickLong, double dropLat, double dropLong){
		this.pickupHour = pickupHour;
		this.weekDay = weekDay;
		this.pickLat = pickLat;
		this.pickLong = pickLong;
		this.dropLat = dropLat;
		this.dropLong = dropLong;
		
		EuclideanDistance euclidian = new EuclideanDistance();
	    ManhattanDistance manhattan = new ManhattanDistance();
	    
	    this.totalDistance = haversine(pickLat, pickLong, dropLat, dropLong)*1000;//in meters
	    this.latDiff = lat_diff(pickLat, dropLat);
	    this.longDiff = long_diff(pickLong, dropLong);
	    
	    double[] pickup = new double[]{pickLat,pickLong};
	    double[] dropoff = new double[]{dropLat,dropLong};
	    
	    this.logEuclidianDistance = Math.log1p(euclidian.compute(pickup, dropoff));
	    this.logHaversineDistance = Math.log1p(haversine(pickLat, pickLong, dropLat, dropLong));
	    this.logManhattanDistance = Math.log1p(manhattan.compute(pickup, dropoff));
	}

	public double getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(double totalDistance) {
		this.totalDistance = totalDistance;
	}

	public double getLatDiff() {
		return latDiff;
	}

	public void setLatDiff(double latDiff) {
		this.latDiff = latDiff;
	}

	public double getLongDiff() {
		return longDiff;
	}

	public void setLongDiff(double longDiff) {
		this.longDiff = longDiff;
	}

	public double getLogHaversineDistance() {
		return logHaversineDistance;
	}

	public void setLogHaversineDistance(double logHaversineDistance) {
		this.logHaversineDistance = logHaversineDistance;
	}

	public double getLogEuclidianDistance() {
		return logEuclidianDistance;
	}

	public void setLogEuclidianDistance(double logEuclidianDistance) {
		this.logEuclidianDistance = logEuclidianDistance;
	}

	public double getLogManhattanDistance() {
		return logManhattanDistance;
	}

	public void setLogManhattanDistance(double logManhattanDistance) {
		this.logManhattanDistance = logManhattanDistance;
	}

	public int getPickupHour() {
		return pickupHour;
	}

	public void setPickupHour(int pickupHour) {
		this.pickupHour = pickupHour;
	}

	public String getWeekDay() {
		return weekDay;
	}

	public void setWeekDay(String weekDay) {
		this.weekDay = weekDay;
	}

	public double getPickLat() {
		return pickLat;
	}

	public void setPickLat(double pickLat) {
		this.pickLat = pickLat;
	}

	public double getPickLong() {
		return pickLong;
	}

	public void setPickLong(double pickLong) {
		this.pickLong = pickLong;
	}

	public double getDropLat() {
		return dropLat;
	}

	public void setDropLat(double dropLat) {
		this.dropLat = dropLat;
	}

	public double getDropLong() {
		return dropLong;
	}

	public void setDropLong(double dropLong) {
		this.dropLong = dropLong;
	}

	private double lat_diff(double pickLat, double dropLat){
		double diff = pickLat - dropLat;
		return diff;
	}

	private double long_diff(double pickLong, double dropLong){
		double diff = pickLong - dropLong;
		return diff;
	}

	private double haversine(double lat1, double lon1, double lat2, double lon2) {
		double dLat = Math.toRadians(lat2 - lat1);
		double dLon = Math.toRadians(lon2 - lon1);
		lat1 = Math.toRadians(lat1);
		lat2 = Math.toRadians(lat2);

		double a = Math.pow(Math.sin(dLat / 2),2) + Math.pow(Math.sin(dLon / 2),2) * Math.cos(lat1) * Math.cos(lat2);
		double c = 2 * Math.asin(Math.sqrt(a));
		return R * c;
	}

}
