import java.io.File;
import java.util.LinkedList;

//[THIS CLASS WORKS COMPLETELY]
/**
* 
* @author Tobias Turke
*
*/
public class ConfigObject 
{

private boolean preprocessing_enabled = false; 
private boolean testing_enabled = false;
private boolean training_enabled = false;
private boolean testing_pref = false;
private String preprocessing = null;	
private double preprocessingMin = Double.NaN;
private double preprocessingMax = Double.NaN;
private double preprocessingToleranz = Double.NaN;
private String preprocessingFileOut = null;
private String training = null;
private String testing = null;
private String trainingResultsOut = null;
private String testingResultsOut = null;
private String weightFileOut = null;
private double errorThreshold = Double.NaN;
private String errorMeasure = null;
private int maxIterations = -1;
private double learningRate = Double.NaN;
private int nInput_count = -1;
private int nHiddenLayer_size = -1;
private LinkedList<Integer> nHidden_count = null;
private int nOutput_count = -1;
private String inputType = null;
private LinkedList<String> hiddenLayerType = null;
private String outputType = null;
private LinkedList<Double> weightRange = null;
private int reportUpdate = 1000;
private String ANNWeightIn = null;
private String preprocIntervallIn = null; 

private String directory_path = ""; 

//############################################################################################################################################################

/**
 * Constructor for Directorypath
 */
public ConfigObject(){}

//[WORKS]
/**
 * Constructor
 * @param preprocessing
 * @param preprocessingMin
 * @param preprocessingMax
 * @param preprocessingFileIn
 * @param preprocessingFileOut
 * @param training
 * @param testing
 * @param trainingResultsOut
 * @param testingResultsOut
 * @param weightFileOut
 * @param errorThreshold
 * @param errorMeasure
 * @param maxIterations
 * @param learningRate
 * @param nInput_count
 * @param nHiddenLayer_size
 * @param nHidden_count
 * @param nOutput_count
 * @param inputType
 * @param hiddenLayerType
 * @param outputType
 * @param weightRange
 * @param reportUpdate
 * @param aNNWeightIn
 */
public ConfigObject(String preprocessing, double preprocessingMin, double preprocessingMax, double preprocessingToleranz, String preprocessingFileOut, String training, String testing,
					String trainingResultsOut, String testingResultsOut, String weightFileOut, double errorThreshold,
					String errorMeasure, int maxIterations, double learningRate, int nInput_count,
					int nHiddenLayer_size, LinkedList<Integer> nHidden_count, int nOutput_count, String inputType, 
					LinkedList<String> hiddenLayerType, String outputType, LinkedList<Double> weightRange, int reportUpdate, String aNNWeightIn, String preprocIntervallIn)
{
	//Set Directorypath
	directory_path = applicationFolder();
	
	//§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§	
	
	if(preprocessing != null)
	{
		this.preprocessing = preprocessing;

		if(preprocessing.equals("YES") || preprocessing.equals("Yes") || preprocessing.equals("yes"))
		{			
			this.preprocessing_enabled = true;
		}
	}
	
	
	//§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
	
	if(isPreprocessing_enabled() && preprocessingMin != Double.NaN)
	{
		this.preprocessingMin = preprocessingMin;
	}else if(preprocessingMin != Double.NaN && isPreprocessing_enabled())
	{
		System.out.println("PreprocessingMin was not set!");
		System.err.println("SYSTEM EXIT!");
		System.exit(0);
	}
	
	//§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
	
	if(getPreprocessingMin() != Double.NaN && isPreprocessing_enabled() && preprocessingMax != Double.NaN)
	{		
		if(getPreprocessingMin() < preprocessingMax)
		{
			this.preprocessingMax = preprocessingMax;
		}else{
			System.out.println("PreprocessingMin not lower then PreprocessingMax!");
			System.err.println("SYSTEM EXIT!");
			System.exit(0);
		}
	}
	
	//§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
	
	if(isPreprocessing_enabled())
	{		
		if(preprocessingToleranz != Double.NaN)
		{
			this.preprocessingToleranz = preprocessingToleranz;
		}else{
			System.out.println("PreprocessingToleranz is  missing or incorrect set!");
			System.err.println("SYSTEM EXIT!");
			System.exit(0);
		}
	}

	//§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
	
	if(preprocessingFileOut != null && isPreprocessing_enabled())
	{
		this.preprocessingFileOut = getDirectory_path()+"/"+preprocessingFileOut;
	}else{
		
		if(preprocessingFileOut == null)
		{
			System.out.println("Preprocessing outputfilepath not set! Value: "+preprocessingFileOut);
			System.err.println("SYSTEM EXIT!");
			System.exit(0);
		}else{
			this.preprocessingFileOut = "DISABLED";
		}
	}	
	
	//§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
	
	if(training != null)
	{
		training = training.replace("\\", "/");
		
		if(!trainingResultsOut.contains("#") && !training.contains("#"))
		{
			training = getDirectory_path()+"/"+training;
			this.training = training;
		}else
		{
			this.training = "DISABLED";
		}

	}else{
		System.out.println("Training datasetpath not set! Value: "+training);
		this.training = "DISABLED";
	}
	
	//§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
	
	if(testing != null)
	{
		
		testing = testing.replace("\\", "/");
		
		if(!testingResultsOut.contains("#") && !testing.contains("#"))
		{
			testing = getDirectory_path()+"/"+testing;
			this.testing = testing;
		}else{
			this.testing = "DISABLED";
		}
		
	}else{
		System.out.println("Testing datasetpath not set! Value: "+testing);
		this.testing = "DISABLED";
	}
	
	//§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
	
	if(training == null && testing == null && isPreprocessing_enabled())
	{
		System.out.println("Neither trainingpath nor testingpath was set in the configfile!");
		System.err.println("SYSTEM EXIT!");
		System.exit(0);
	}
	

	//§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
	
	if(trainingResultsOut != null && !training.contains("DISABLED"))
	{		
		trainingResultsOut = trainingResultsOut.replace("\\", "/");
		
		if(!trainingResultsOut.contains("#") && !training.contains("#"))
		{
			trainingResultsOut = getDirectory_path()+"/"+trainingResultsOut;
			this.trainingResultsOut = trainingResultsOut;
			this.training_enabled = true;
		}else{
			this.trainingResultsOut = "DISABLED";
		}
		
	}else{
		
		if(training != null && !training.contains("DISABLED"))
		{
			System.out.println("Trainingresult outputpath not set! Value: "+trainingResultsOut);
			System.err.println("SYSTEM EXIT!");
			System.exit(0);
		}
	}

	//§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
		
	if(testingResultsOut != null && !getTesting().contains("DISABLED"))
	{
		testingResultsOut = testingResultsOut.replace("\\", "/");
		
		if(!testingResultsOut.contains("#") && !testing.contains("#"))
		{
			testingResultsOut = getDirectory_path()+"/"+testingResultsOut;
			this.testingResultsOut = testingResultsOut;
			this.testing_enabled = true;
		}else{
			this.testingResultsOut = "DISABLED";
		}
		
		
		
	}else{
		
		if(testing != null && !getTesting().contains("DISABLED"))
		{
			System.out.println("Testingresult outputpath not set! Value: "+testingResultsOut);
			System.err.println("SYSTEM EXIT!");
			System.exit(0);
		}	
	}

	//§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
	
	if(isTraining_enabled())
	{
		 this.testing = "DISABLED";
		 this.testingResultsOut = "DISABLED";
		 this.testing_enabled = false;
	}
	
	if(isTesting_enabled() && !isTraining_enabled())
	{
		this.training = "DISABLED";
		this.trainingResultsOut = "DISABLED";
		this.training_enabled = false;
		this.testing_pref = true;
	}
	
	if(!isTraining_enabled() && !isTesting_enabled())
	{
		this.testing = "DISABLED";
		this.testingResultsOut = "DISABLED";
		this.testing_enabled = false;
		
		this.training = "DISABLED";
		this.trainingResultsOut = "DISABLED";
		this.training_enabled = false;
		
		System.err.println("Neither Testing nor Training was chosen!");
		System.exit(0);
	}
	
	//§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
	
	if(preprocIntervallIn != null && isTesting_pref())
	{
		preprocIntervallIn = preprocIntervallIn.replace("\\", "/");
		
		if(!preprocIntervallIn.contains("#"))
		{
			preprocIntervallIn = getDirectory_path()+"/"+preprocIntervallIn;
			this.preprocIntervallIn = preprocIntervallIn;
		}else{
			this.preprocIntervallIn = "DISABLED";
		}
		
		
	}else if(preprocIntervallIn != null && !isTesting_pref())
	{
		this.preprocIntervallIn = "DISABLED";
	}else{
		
		if(isTesting_pref())
		{
			System.out.println("Preprocessing-Intervall-In Inputpath is not set! Value: "+preprocIntervallIn);
			System.err.println("SYSTEM EXIT!");
			System.exit(0);
		}
	}

	//§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
	
	if(aNNWeightIn != null && isTesting_pref())
	{
		aNNWeightIn = aNNWeightIn.replace("\\", "/");
		
		if(!aNNWeightIn.contains("#"))
		{
			aNNWeightIn = getDirectory_path()+"/"+aNNWeightIn;
			this.ANNWeightIn = aNNWeightIn;
		}else{
			this.ANNWeightIn = "DISABLED";
		}
		
		
	}else if(aNNWeightIn != null && !isTesting_pref())
	{
		this.ANNWeightIn = "DISABLED";
	}else{
		
		if(testing != null)
		{
			System.out.println("ANN-Weight Inputpath is not set! Value: "+aNNWeightIn);
			System.err.println("SYSTEM EXIT!");
			System.exit(0);
		}
	}
	
	//§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
		
	if(weightFileOut != null && isTraining_enabled())
	{
		weightFileOut = weightFileOut.replace("\\", "/");
		
		if(!weightFileOut.contains("#"))
		{
			weightFileOut = getDirectory_path()+"/"+weightFileOut;
			this.weightFileOut = weightFileOut;
		}else{
			this.weightFileOut = "DISABLED";
		}
		
	}else{
		
		if(isTraining_enabled())
		{
			System.out.println("Final weights outputpath is not set! Value: "+weightFileOut);
			System.err.println("SYSTEM EXIT!");
			System.exit(0);
		}else{
			this.weightFileOut = "DISABLED";
		}
	}
	
	//§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
	
	if(errorThreshold > -1)
	{
		this.errorThreshold = errorThreshold;
	}else{
		
		if(errorThreshold == Double.NaN)
		{
			System.out.println("Error threshhold is not set! Value: "+errorThreshold);
			System.err.println("SYSTEM EXIT!");
			System.exit(0);
			
		}else{
			
			System.out.println("Error threshhold is negativ! Value: "+errorThreshold);
			System.err.println("SYSTEM EXIT!");
			System.exit(0);
		}
	}

	//§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
		
	if(errorMeasure != null)
	{
		this.errorMeasure = errorMeasure;
	}else{
		System.out.println("Error measure is incorrect set! Value: "+errorMeasure);
		System.err.println("SYSTEM EXIT!");
		System.exit(0);
	}

	//§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
	
	if(maxIterations > -1 && !testing_pref)
	{
		this.maxIterations = maxIterations;
	}
	
	if(maxIterations > -1 && testing_pref)
	{
		this.maxIterations = 1;
	}
	
	if(maxIterations < 0 )
	{
		System.out.println("Maximal Iteration value is incorrect set! Value: "+maxIterations);
		System.err.println("SYSTEM EXIT!");
		System.exit(0);
	}
	
	//§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§

	if(learningRate > 0)
	{
		this.learningRate = learningRate;
	}else{
		
		if(learningRate == Double.NaN)
		{
			System.out.println("Learningrate is not set! Value: "+learningRate);
			System.err.println("SYSTEM EXIT!");
			System.exit(0);
			
		}else{
			
			System.out.println("Learningrate is negativ! Value: "+learningRate);
			System.err.println("SYSTEM EXIT!");
			System.exit(0);
		}
	}
	
	//§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§

	
	if(nInput_count > -1)
	{
		this.nInput_count = nInput_count;
	}else{
		System.out.println("Neuron Inputcounter is incorrect set! Value: "+nInput_count);
		System.err.println("SYSTEM EXIT!");
		System.exit(0);
	}
	
	//§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
	
	if(nHiddenLayer_size > -1)
	{
		this.nHiddenLayer_size = nHiddenLayer_size;
	}else{
		System.out.println("Neuron Hiddenlayersize is incorrect set! Value: "+nHiddenLayer_size);
		System.err.println("SYSTEM EXIT!");
		System.exit(0);
	}

	//§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
		
	if(nHidden_count != null && nHidden_count.size() == nHiddenLayer_size)
	{
		this.nHidden_count = nHidden_count;	
	}else{
		System.out.println("Neuron Hiddencouters has incorrect Size! Value: "+nHidden_count.size());
		System.err.println("SYSTEM EXIT!");
		System.exit(0);
	}

	//§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
	
	if(nOutput_count > -1)
	{
		this.nOutput_count = nOutput_count;
	}else{
		System.out.println("Neuron Outputcouter is incorrect set! Value: "+nOutput_count);
		System.err.println("SYSTEM EXIT!");
		System.exit(0);
	}
	
	
	
	//§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
		
	if(inputType != null)
	{
		this.inputType = inputType;
	}else{
		System.out.println("Input activation function is incorrect set! Value: "+inputType);
		System.err.println("SYSTEM EXIT!");
		System.exit(0);
	}

	//§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
		
	if(hiddenLayerType != null && hiddenLayerType.size() == nHiddenLayer_size)
	{
		
		this.hiddenLayerType = hiddenLayerType;	
		
	}else{
		System.out.println("Hiddenlayers activation functions has incorrect Size! Value: "+hiddenLayerType.size());
		System.err.println("SYSTEM EXIT!");
		System.exit(0);
	}

	//§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
		
	if(outputType != null)
	{
		this.outputType = outputType;
	}else{
		System.out.println("Output activation function is incorrect set! Value: "+outputType);
		System.err.println("SYSTEM EXIT!");
		System.exit(0);
	}

	//§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
		
	if(weightRange != null && weightRange.size() == 2)
	{
		this.weightRange = weightRange;
	}else{
		System.err.println("Weights range is incorrect set!");
		System.exit(0);
	}
	
	if(reportUpdate > -1)
	{
		this.reportUpdate = reportUpdate;
	}
	
}

//############################################################################################################################################################

//[WORKS]
/**
 * Show the whole content of an ConfigObject
 * @param co
 */
public void showContent(ConfigObject co)
{	
	System.out.println("~~~~~~~~~~~~~~~~~~~~~ WORK SETUP ~~~~~~~~~~~~~~~~~~~~~");
	System.out.println("PreprocessingEnabled: \t["+co.isPreprocessing_enabled()+"]");
	System.out.println("PreprocessingMin: \t["+co.getPreprocessingMin()+"]");
	System.out.println("PreprocessingMax: \t["+co.getPreprocessingMax()+"]");
	System.out.println("PreprocessingToleranz: \t["+co.getPreprocessingToleranz()+"]");
	System.out.println("PreprocessingFileOut: \t["+co.getPreprocessingFileOut()+"]");
	System.out.println("TrainingEnabled: \t["+co.isTraining_enabled()+"]");
	System.out.println("Training: \t\t["+co.getTraining()+"]");
	System.out.println("TrainingResultsOut: \t["+co.getTrainingResultsOut()+"]");
	System.out.println("WeightFileOut: \t\t["+co.getWeightFileOut()+"]");
	System.out.println("TestingEnabled: \t["+co.isTesting_enabled()+"]");
	System.out.println("Testing: \t\t["+co.getTesting()+"]");
	System.out.println("TestingResultsOut: \t["+co.getTestingResultsOut()+"]");
	System.out.println("PreprocIntervallIn: \t["+co.getpreprocIntervallIn()+"]");
	System.out.println("ANNWeightIn: \t\t["+co.getANNWeightIn()+"]");
	
	System.out.println("");
	System.out.println("~~~~~~~~~~~~~~~~~~~~~ ANN SETUP ~~~~~~~~~~~~~~~~~~~~~");
	
	System.out.println("ErrorThreshold: \t["+co.getErrorThreshold()+"]");
	System.out.println("ErrorMeasure: \t\t["+co.getErrorMeasure()+"]");
	System.out.println("MaxIterations: \t\t["+co.getMaxIterations()+"]");
	System.out.println("LearningRate: \t\t["+co.getLearningRate()+"]");
	System.out.println("NInput: \t\t["+co.getnInput_count()+"]");
	System.out.println("NHiddenLayer: \t\t["+co.getnHiddenLayer_size()+"]");
	System.out.println("NHidden: \t\t"+co.getnHidden_count()+"");
	System.out.println("NOutput: \t\t["+co.getnOutput_count()+"]");
	System.out.println("InputType: \t\t["+co.getInputType()+"]");
	System.out.println("HiddenLayerType: \t"+co.getHiddenLayerType()+"");
	System.out.println("OutputType: \t\t["+co.getOutputType()+"]");
	System.out.println("WeightRange: \t\t"+co.getWeightRange()+"");
	System.out.println("ReportUpdate: \t\t["+co.getReportUpdate()+"]");
}

//############################################################################################################################################################

//[WORKS]
/**
 * This instance get back the current applicationfolderpath
 * @return String containing the path
 */
public String applicationFolder()
{
	String currentDirectory = null;
	File file = new File("");
	currentDirectory = file.getAbsolutePath();
	currentDirectory = currentDirectory.replace("\\", "/");
	return currentDirectory;
}

//############################################################################################################################################################


public boolean isTesting_enabled() {
	return testing_enabled;
}

public boolean isTraining_enabled() {
	return training_enabled;
}

public boolean isPreprocessing_enabled() {
	return preprocessing_enabled;
}

public String getPreprocessing() {
	return preprocessing;
}

public double getPreprocessingMin() {
	return preprocessingMin;
}

public double getPreprocessingMax() {
	return preprocessingMax;
}

public String getPreprocessingFileOut() {
	return preprocessingFileOut;
}

public String getTraining() {
	return training;
}

public String getTesting() {
	return testing;
}

public String getTrainingResultsOut() {
	return trainingResultsOut;
}

public String getTestingResultsOut() {
	return testingResultsOut;
}

public String getWeightFileOut() {
	return weightFileOut;
}

public double getErrorThreshold() {
	return errorThreshold;
}

public String getErrorMeasure() {
	return errorMeasure;
}

public int getMaxIterations() {
	return maxIterations;
}

public double getLearningRate() {
	return learningRate;
}

public int getnInput_count() {
	return nInput_count;
}

public int getnHiddenLayer_size() {
	return nHiddenLayer_size;
}

public LinkedList<Integer> getnHidden_count() {
	return nHidden_count;
}

public int getnOutput_count() {
	return nOutput_count;
}

public String getInputType() {
	return inputType;
}

public LinkedList<String> getHiddenLayerType() {
	return hiddenLayerType;
}

public String getOutputType() {
	return outputType;
}

public LinkedList<Double> getWeightRange() {
	return weightRange;
}

public String getDirectory_path() {
	return directory_path;
}

public int getReportUpdate() {
	return reportUpdate;
}

public boolean isTesting_pref() {
	return testing_pref;
}

public String getANNWeightIn() {
	return ANNWeightIn;
}

public void setDirectory_path(String directory_path) {
	this.directory_path = directory_path;
}

public String getpreprocIntervallIn() {
	return preprocIntervallIn;
}

public void setpreprocIntervallIn(String preprocIntervallIn) {
	this.preprocIntervallIn = preprocIntervallIn;
}

public double getPreprocessingToleranz() {
	return preprocessingToleranz;
}


}



