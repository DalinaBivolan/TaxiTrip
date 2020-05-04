package app.taxi.machine.learning.model;

//import java.io.*;
import hex.genmodel.easy.RowData;
import hex.genmodel.easy.EasyPredictModelWrapper;
import hex.genmodel.easy.prediction.*;

import org.apache.commons.math3.ml.distance.EuclideanDistance;
import org.apache.commons.math3.ml.distance.ManhattanDistance;

import hex.genmodel.MojoModel;

public class MojoMain {
	public static final double R = 6372.8; // In kilometers
	
  public static void main(String[] args) throws Exception {
    EasyPredictModelWrapper.Config config = new EasyPredictModelWrapper.Config().setModel(MojoModel.load("C:/Users/Dalina/Desktop/TaxiCompany/gbm_model.zip"));
    EasyPredictModelWrapper model = new EasyPredictModelWrapper(config);
    
    EuclideanDistance euclidian = new EuclideanDistance();
    ManhattanDistance manhattan = new ManhattanDistance();
    
    String pickupHour = "12";
    String weekDay = "0"; //Luni
    
    double pickLat = 44.330179;
    double pickLong = 23.794881;
    double dropLat = 44.026882;
    double dropLong = 23.345405;
    
    double[] pickup = new double[]{pickLat,pickLong};
    double[] dropoff = new double[]{dropLat,dropLong};

    RowData row = new RowData();
    row.put("pickup_hour", pickupHour);
    row.put("pickup_latitude", pickLat);
    row.put("pickup_longitude", pickLong);
    row.put("dropoff_latitude", dropLat);
    row.put("dropoff_longitude", dropLong);
    row.put("pickup_weekday", weekDay);
    row.put("total_distance", haversine(pickLat, pickLong, dropLat, dropLong)*1000);
    row.put("lat_diff", lat_diff(pickLat, dropLat));
    row.put("lon_diff", long_diff(pickLong, dropLong));
    row.put("log_haversine_distance", Math.log1p(haversine(pickLat, pickLong, dropLat, dropLong)));
    row.put("log_euclidian_distance", Math.log1p(euclidian.compute(pickup, dropoff)));
    row.put("log_manhattan_distance", Math.log1p(manhattan.compute(pickup, dropoff)));

    RegressionModelPrediction p = model.predictRegression(row);
    
    double value = Math.exp(p.value)-1;
    System.out.println("Value="+value);
    System.out.println("Euclidian distance : " + euclidian.compute(pickup, dropoff));
    System.out.println("Manhattan distance : " + manhattan.compute(pickup, dropoff));
    System.out.println("Haversine distance : " + haversine(pickLat, pickLong, dropLat, dropLong));
    
    if(value >= 60){
    	System.out.println("Predicted trip duration is : " + value/60 + " minutes.");
    } else if(value >= 3600){
    	System.out.println("Predicted trip duration is : " + value/3600 + " hours.");
    }else{
    	System.out.println("Predicted trip duration is : " + value + " seconds.");
    }
  }
  
  private static double lat_diff(double pickLat, double dropLat){
  	double diff = pickLat - dropLat;
  	return diff;
  }
  
  private static double long_diff(double pickLong, double dropLong){
	  	double diff = pickLong - dropLong;
	  	return diff;
	  }
  
  public static double haversine(double lat1, double lon1, double lat2, double lon2) {
      double dLat = Math.toRadians(lat2 - lat1);
      double dLon = Math.toRadians(lon2 - lon1);
      lat1 = Math.toRadians(lat1);
      lat2 = Math.toRadians(lat2);

      double a = Math.pow(Math.sin(dLat / 2),2) + Math.pow(Math.sin(dLon / 2),2) * Math.cos(lat1) * Math.cos(lat2);
      double c = 2 * Math.asin(Math.sqrt(a));
      return R * c;
  }
}
