package app.taxi.machine.learning.model;

import java.io.IOException;

import app.taxi.util.DefaultModelFeatures;
import hex.genmodel.MojoModel;
import hex.genmodel.easy.EasyPredictModelWrapper;
import hex.genmodel.easy.RowData;
import hex.genmodel.easy.exception.PredictException;
import hex.genmodel.easy.prediction.RegressionModelPrediction;

public class PredictModelDefault {
	private EasyPredictModelWrapper model = null;
	private DefaultModelFeatures features;

	public PredictModelDefault(DefaultModelFeatures features) throws IOException{
		EasyPredictModelWrapper.Config config = new EasyPredictModelWrapper.Config().
				setModel(MojoModel.load("C:/Users/Dalina/Desktop/TaxiCompany/gbm_model.zip"));
		 this.model = new EasyPredictModelWrapper(config);
		 this.features = features;
	}
	
	private int dayToNumber(){
		int day = 0;
		switch (features.getWeekDay()) {
		case "Luni":
			day = 0;
			break;
		case "Marti":
			day = 1;
			break;
		case "Miercuri":
			day = 2;
			break;
		case "Joi":
			day = 3;
			break;
		case "Vineri":
			day = 4;
			break;
		case "Sambata":
			day = 5;
			break;
		case "Duminica":
			day = 6;
			break;
		default:
			System.out.println("Error dayToNumber method!");
			break;
		}
		System.out.println("DoyToNumber : "+day);
		return day;
	}

	private RowData addFeatureValues(){
		RowData row = new RowData();
		row.put("pickup_hour", String.valueOf(features.getPickupHour()));
		row.put("pickup_latitude", features.getPickLat());
		row.put("pickup_longitude", features.getPickLong());
		row.put("dropoff_latitude", features.getDropLat());
		row.put("dropoff_longitude", features.getDropLong());
		row.put("pickup_weekday", String.valueOf(dayToNumber()));
		row.put("total_distance", features.getTotalDistance());
		row.put("lat_diff", features.getLatDiff());
		row.put("lon_diff", features.getLongDiff());
		row.put("log_haversine_distance", features.getLogHaversineDistance());
		row.put("log_euclidian_distance", features.getLogEuclidianDistance());
		row.put("log_manhattan_distance", features.getLogManhattanDistance());

		return row;
	}

	public String predict() throws PredictException{
		RegressionModelPrediction p = model.predictRegression(addFeatureValues());
		double value = Math.exp(p.value)-1;//in seconds
		String result = "";
		if(value >= 60){
	    	result = "Călătoria cu taxiul va dura : " + value/60 + " minute.";
	    } else if(value >= 3600){
	    	result = "Călătoria cu taxiul va dura : " + value/3600 + " ore.";
	    }else{
	    	result = "Călătoria cu taxiul va dura : " + value + " secunde.";
	    }
		
		return result;
	}
}
