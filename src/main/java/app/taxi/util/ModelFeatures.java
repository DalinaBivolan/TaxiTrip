package app.taxi.util;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ModelFeatures {
	private String pickupHour;
	private String weekDay;
	private String pickLat;
	private String pickLong;
	private String dropLat;
	private String dropLong;
	private String totalDistance;
	private String latDiff;
	private String longDiff;
	private String logHaversineDistance;
	private String logEuclidianDistance;
	private String logManhattanDistance;
	
	public ModelFeatures(){}
	
	public ModelFeatures(String pickupHour, 
			String weekDay, 
			String pickLat,
			String pickLong,
			String dropLat, 
			String dropLong,
			String totalDistance,
			String latDiff,
			String longDiff,
			String logHaversineDistance,
			String logEuclidianDistance,
			String logManhattanDistance){
		this.pickupHour = pickupHour;
		this.weekDay = weekDay;
		this.pickLat = pickLat;
		this.pickLong = pickLong;
		this.dropLat = dropLat;
		this.dropLong = dropLong;
		this.totalDistance = totalDistance;
		this.latDiff = latDiff;
		this.longDiff = longDiff;
		this.logHaversineDistance = logHaversineDistance;
		this.logEuclidianDistance = logEuclidianDistance;
		this.logManhattanDistance = logManhattanDistance;
	}

	public String getPickupHour() {
		return pickupHour;
	}
	@XmlElement
	public void setPickupHour(String pickupHour) {
		this.pickupHour = pickupHour;
	}

	public String getWeekDay() {
		return weekDay;
	}
	@XmlElement
	public void setWeekDay(String weekDay) {
		this.weekDay = weekDay;
	}

	public String getPickLat() {
		return pickLat;
	}
	@XmlElement
	public void setPickLat(String pickLat) {
		this.pickLat = pickLat;
	}

	public String getPickLong() {
		return pickLong;
	}
	@XmlElement
	public void setPickLong(String pickLong) {
		this.pickLong = pickLong;
	}

	public String getDropLat() {
		return dropLat;
	}
	@XmlElement
	public void setDropLat(String dropLat) {
		this.dropLat = dropLat;
	}

	public String getDropLong() {
		return dropLong;
	}
	@XmlElement
	public void setDropLong(String dropLong) {
		this.dropLong = dropLong;
	}

	public String getTotalDistance() {
		return totalDistance;
	}
	@XmlElement
	public void setTotalDistance(String totalDistance) {
		this.totalDistance = totalDistance;
	}

	public String getLatDiff() {
		return latDiff;
	}
	@XmlElement
	public void setLatDiff(String latDiff) {
		this.latDiff = latDiff;
	}

	public String getLongDiff() {
		return longDiff;
	}
	@XmlElement
	public void setLongDiff(String longDiff) {
		this.longDiff = longDiff;
	}

	public String getLogHaversineDistance() {
		return logHaversineDistance;
	}
	@XmlElement
	public void setLogHaversineDistance(String logHaversineDistance) {
		this.logHaversineDistance = logHaversineDistance;
	}

	public String getLogEuclidianDistance() {
		return logEuclidianDistance;
	}
	@XmlElement
	public void setLogEuclidianDistance(String logEuclidianDistance) {
		this.logEuclidianDistance = logEuclidianDistance;
	}

	public String getLogManhattanDistance() {
		return logManhattanDistance;
	}
	@XmlElement
	public void setLogManhattanDistance(String logManhattanDistance) {
		this.logManhattanDistance = logManhattanDistance;
	}
	
	
}
