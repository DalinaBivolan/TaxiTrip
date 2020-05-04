package app.taxi.util;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ModelParameters {
	private String trainPath;
	private String eta;
	private String objective;
	private String maxDepth;
	private String booster;
	private String silent;
	private String nthread;
	private String subsample;
	private String colsampleByTree;
	private String evalMetric;
	private String testPath;
	
	private String savePath;
	
	public ModelParameters(){
		
	}
	
	public ModelParameters(
			String trainPath,
			String eta,
			String objective,
			String maxDepth,
			String booster,
			String silent,
			String nthread,
			String subsample,
			String colsampleByTree,
			String evalMetric,
			String testPath){
		
		this.trainPath = trainPath;
		this.eta = eta;
		this.objective = objective;
		this.maxDepth = maxDepth;
		this.booster = booster;
		this.silent = silent;
		this.nthread = nthread;
		this.subsample = subsample;
		this.colsampleByTree = colsampleByTree;
		this.evalMetric = evalMetric;
		this.testPath = testPath;
	}
	
	public ModelParameters(
			String trainPath,
			String eta,
			String objective,
			String maxDepth,
			String booster,
			String silent,
			String nthread,
			String subsample,
			String colsampleByTree,
			String evalMetric,
			String testPath,
			String savePath){
		
		this.trainPath = trainPath;
		this.eta = eta;
		this.objective = objective;
		this.maxDepth = maxDepth;
		this.booster = booster;
		this.silent = silent;
		this.nthread = nthread;
		this.subsample = subsample;
		this.colsampleByTree = colsampleByTree;
		this.evalMetric = evalMetric;
		this.testPath = testPath;
		this.savePath = savePath;
	}

	public String getSavePath() {
		return savePath;
	}
	@XmlElement
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public String getTrainPath() {
		return trainPath;
	}
	@XmlElement
	public void setTrainPath(String trainPath) {
		this.trainPath = trainPath;
	}

	public String getEta() {
		return eta;
	}
	@XmlElement
	public void setEta(String eta) {
		this.eta = eta;
	}

	public String getObjective() {
		return objective;
	}
	@XmlElement
	public void setObjective(String objective) {
		this.objective = objective;
	}

	public String getMaxDepth() {
		return maxDepth;
	}
	@XmlElement
	public void setMaxDepth(String maxDepth) {
		this.maxDepth = maxDepth;
	}

	public String getBooster() {
		return booster;
	}
	@XmlElement
	public void setBooster(String booster) {
		this.booster = booster;
	}

	public String getSilent() {
		return silent;
	}
	@XmlElement
	public void setSilent(String silent) {
		this.silent = silent;
	}

	public String getNthread() {
		return nthread;
	}
	@XmlElement
	public void setNthread(String nthread) {
		this.nthread = nthread;
	}

	public String getSubsample() {
		return subsample;
	}
	@XmlElement
	public void setSubsample(String subsample) {
		this.subsample = subsample;
	}

	public String getColsampleByTree() {
		return colsampleByTree;
	}
	@XmlElement
	public void setColsampleByTree(String colsampleByTree) {
		this.colsampleByTree = colsampleByTree;
	}
	
	public String getEvalMetric() {
		return evalMetric;
	}
	@XmlElement
	public void setEvalMetric(String evalMetric) {
		this.evalMetric = evalMetric;
	}

	public String getTestPath() {
		return testPath;
	}
	@XmlElement
	public void setTestPath(String testPath) {
		this.testPath = testPath;
	}
}
