import java.io.IOException;
import java.util.LinkedList;

/**
 * 
 * @author Tobias Turke
 *
 */
public class Main 
{
	private LinkedList<LinkedList<Double>> pattern_vec_outs = null;
	private LinkedList<Double> pattern_goal_values = null;
	private LayerTransferWeightObj lto =  null;
	private double new_goal_max = Double.NaN;
	private double new_goal_min = Double.NaN;
	private double old_goal_max = Double.NaN;
	private double old_goal_min = Double.NaN;
	
	//##########################################################################################################################################################
	//##########################################################################################################################################################
	//##########################################################################################################################################################
	
	/**
	 * Hier soll das alte Intervall je Vektor des bearbeiteten Datensatzes Gespeichert werden 
	 * @param outPreprocessingPath
	 * @param datasetPath
	 * @param intervall_old
	 * @param newDatasetMax
	 * @param newDatasetMin
	 * @throws IOException
	 */
	public void savePreprocessingIntervall(String outPreprocessingPath, String datasetPath, LinkedList<Double[]> intervall_old, double newDatasetMax, double newDatasetMin) throws IOException
	{
		LinkedList<String> writing_out = new LinkedList<String>();
		FileWriterForFinalOutput fwffo = new FileWriterForFinalOutput(outPreprocessingPath);
		IntervallDefinitionCollector inDefColl = new IntervallDefinitionCollector();
		inDefColl.transform(intervall_old);
		
		//Create writing String list
		writing_out.add("Dataset location: ["+datasetPath+"]");
		writing_out.add("Dataset name: ["+datasetPath.substring(datasetPath.lastIndexOf("/")+1)+"]");
		writing_out.add("");
		writing_out.add("Old dataset ranges for each dataset column!");
		writing_out.add("Old dataset max: "+inDefColl.getMax_values().toString());
		writing_out.add("Old dataset min: "+inDefColl.getMin_values().toString());
		writing_out.add("");
		writing_out.add("New range for each dataset column [all equal]!");
		writing_out.add("New max: ["+newDatasetMax+"]");
		writing_out.add("New min: ["+newDatasetMin+"]");
		
		//Neue Savefile erstellen mit Zeistempel im Namen
		fwffo = new FileWriterForFinalOutput(outPreprocessingPath);
		fwffo.FileCreation(outPreprocessingPath);
					
		//Daten speichern
		fwffo = new FileWriterForFinalOutput(outPreprocessingPath);
		fwffo.WriteFile(writing_out);
				
		System.out.println("!! FINISHED SAVING PREPROCESSING INTERVALL !!");
	}
	
	
	/**
	 * * Hier soll der Netzzielwert/-zielwert pro Vektor in Bezug auf den jeweiligen Vektor des Datensatzes
	 * gespeichert werden
	 * @param resultPathOut
	 * @param datasetPath
	 * @param results
	 * @param vecsDesiredOut
	 * @param finalError
	 * @throws IOException
	 */
	public void saveTestingContent(String resultPathOut, String datasetPath, LinkedList<Double> results, LinkedList<Double> vecsDesiredOut, double finalError) throws IOException
	{
		FileWriterForFinalOutput fwffo;
		LinkedList<String> parse_content = new LinkedList<String>();
		
		parse_content.add("DATASET PATH: "+datasetPath+"\n");
		
		if(results.size() == vecsDesiredOut.size())
		{
			parse_content.add("Vektor n [Desired-Out | Final-Result | Difference]\n");
			parse_content.add("");
			
			for(int ea = 0; ea < results.size(); ea++)
			{
				if(results.size()-1 == ea)
				{
					parse_content.add("Vektor "+(ea+1)+"\t ["+vecsDesiredOut.get(ea)+"\t | "+results.get(ea)+"\t | "+(vecsDesiredOut.get(ea) - results.get(ea))+"]\n");
				}else{
					parse_content.add("Vektor "+(ea+1)+"\t ["+vecsDesiredOut.get(ea)+"\t | "+results.get(ea)+"\t | "+(vecsDesiredOut.get(ea) - results.get(ea))+"]");
				}
				
			}
		}else{
			System.err.println("RESULT SIZE NOT MATCH WITH DESIRED-OUTPUT SIZE!");
			System.exit(0);
		}
		
		parse_content.add("");
		parse_content.add("FINAL ERROR = "+finalError);
		
		//Neue Savefile erstellen mit Zeistempel im Namen
		fwffo = new FileWriterForFinalOutput(resultPathOut);
		fwffo.FileCreation(resultPathOut);
											
		//Daten speichern
		fwffo = new FileWriterForFinalOutput(resultPathOut);
		fwffo.WriteFile(parse_content);
				
		System.out.println("!! FINISHED SAVING FINAL TESTING INFORMATIONS !!");
	}
	
	
	/**
	 * * Hier soll der Gewichtungsgradient des erstellten ANN gespeichert werden.
	 * Außerdem ist die Pfadangabe zur ANN-Config eingetragen.
	 * @param resultPathOut
	 * @param weightPathOut
	 * @param cfgPath
	 * @param vecsDesiredOut
	 * @param results
	 * @param ltwo
	 * @param datasetPath
	 * @param rounds
	 * @param finalError
	 * @throws IOException
	 */
	public void saveTrainingContent(String resultPathOut, String weightPathOut, String cfgPath, LinkedList<Double> vecsDesiredOut, 
									LinkedList<Double> results, LayerTransferWeightObj ltwo, String datasetPath, int rounds, double finalError) throws IOException
	{
		FileWriterForFinalOutput fwffo = new FileWriterForFinalOutput(weightPathOut);
		LinkedList<LinkedList<Double>> in_to_hidden = ltwo.getIn_to_hidden();
		LinkedList<LinkedList<LinkedList<Double>>> per_HLayer = ltwo.getPer_HLayer();
		LinkedList<LinkedList<Double>> hidden_to_output = ltwo.getHidden_to_output();
		LinkedList<String> parse_content = new LinkedList<String>();

		parse_content.add("PATH FOR ANN SETUP: "+cfgPath+"\n");
		
		parse_content.add("INPUT-TO-HIDDENLAYER-WEIGHTLISTS");
		parse_content.add("Start-Neurons: "+in_to_hidden.size());
		parse_content.add("Transitions: "+in_to_hidden.get(0).size());
		parse_content.add(""+in_to_hidden+"\n");
		
		if(per_HLayer.size() > 0)
		{
			parse_content.add("BETWEEN HIDDENLAYERS-WEIGHTLISTS");
			for(int k = 0; k < per_HLayer.size(); k++)
			{
				parse_content.add("NEXT HIDDENLAYER-WEIGHTLIST "+(k+1));
				parse_content.add("Start-Neurons: "+per_HLayer.get(k).size());
				
				if((""+per_HLayer.get(k).getClass()).contains("java.util.LinkedList"))
				{
					parse_content.add("Transitions: "+per_HLayer.get(k).get(0).size());
				}
				
				parse_content.add(""+per_HLayer.get(k)+"\n");
			}
		}
		
		parse_content.add("HIDDEN-OUTPUTLAYER-WEIGHTLISTS");
		parse_content.add("Start-Neurons: "+hidden_to_output.size());
		parse_content.add("Transitions: "+hidden_to_output.get(0).size());
		parse_content.add(""+hidden_to_output);

		//Neue Savefile erstellen mit Zeistempel im Namen
		fwffo = new FileWriterForFinalOutput(weightPathOut);
		fwffo.FileCreation(weightPathOut);
							
		//Daten speichern
		fwffo = new FileWriterForFinalOutput(weightPathOut);
		fwffo.WriteFile(parse_content);
		
		//#####################################################################################
		
		parse_content = new LinkedList<String>();
		fwffo = new FileWriterForFinalOutput(resultPathOut);
						
		parse_content.add("DATASET PATH: "+datasetPath+"\n");
		
		if(results.size() == vecsDesiredOut.size())
		{
			for(int ea = 0; ea < results.size(); ea++)
			{
				if(results.size()-1 == ea)
				{
					parse_content.add("Vektor "+(ea+1)+" [Desired-Out = "+vecsDesiredOut.get(ea)+" | Final-Result = "+results.get(ea)+" | Diff. = "+(vecsDesiredOut.get(ea) - results.get(ea))+"]\n");
				}else{
					parse_content.add("Vektor "+(ea+1)+" [Desired-Out = "+vecsDesiredOut.get(ea)+" | Final-Result = "+results.get(ea)+" | Diff. = "+(vecsDesiredOut.get(ea) - results.get(ea))+"]");
				}
				
			}
		}else{
			System.err.println("RESULT SIZE NOT MATCH WITH DESIRED-OUTPUT SIZE!");
			System.exit(0);
		}
		
		parse_content.add("NECESSARY ITERATIONS = "+rounds+"\n");
		parse_content.add("FINAL ERROR = "+finalError);
		
		//Neue Savefile erstellen mit Zeistempel im Namen
		fwffo = new FileWriterForFinalOutput(resultPathOut);
		fwffo.FileCreation(resultPathOut);
									
		//Daten speichern
		fwffo = new FileWriterForFinalOutput(resultPathOut);
		fwffo.WriteFile(parse_content);
		
		System.out.println("!! FINISHED SAVING FINAL ANN TRAINING WEIGHTS !!");
	}
	
	//##########################################################################################################################################################
	//##########################################################################################################################################################
	//##########################################################################################################################################################
	
	/**
	 * This function start the whole prozess of solving of just one dataset 
	 * @param in_values
	 * @param in_funk
	 * @param h_Layer_Depth
	 * @param anz_HNeuronen_pro_Lay
	 * @param funk_pro_HLayer
	 * @param out_funk
	 * @param goal_value
	 * @param teaching_value
	 * @param output_count
	 * @param net
	 * @param isTesting
	 * @return a weightobject
	 * @throws IOException
	 */
	public LayerTransferWeightObj init(	LinkedList<Double> in_values, String in_funk, int h_Layer_Depth, 
										LinkedList<Integer> anz_HNeuronen_pro_Lay, LinkedList<String> funk_pro_HLayer,
										String out_funk, double goal_value, double teaching_value, int output_count, TheNet net, boolean isTesting) throws IOException
	{
		//Nicht Eingabevariablen Initialisierung
		LinkedList<Double> cur_vec_outs = new LinkedList<Double>();
		LayerTransferWeightObj nltwo;
		double cur_output = Double.NaN;
			
		//***********************************************************************************************************************
		
		//Feed Forward
		net.feedforward(h_Layer_Depth, anz_HNeuronen_pro_Lay, output_count, in_funk, in_values, funk_pro_HLayer, out_funk);
						
		//***********************************************************************************************************************

		//Erstellen der Vektoroutputliste
		for(int outs_it = 0; outs_it < net.getOutput_nr().size(); outs_it++)
		{
			cur_output = net.getOutput_nr().get(outs_it).getSolved_Out_FF();
			cur_vec_outs.add(cur_output);
		}
		
		if(cur_vec_outs.size() > 0 && cur_vec_outs != null )
		{
			pattern_vec_outs.add(cur_vec_outs);
		}else{
			System.err.println("Vektoren-Output-Liste ist leer!");
			System.exit(0);
		}
		
		//***********************************************************************************************************************
		
		//Einsammeln der Goalvalues
		getPattern_goal_values().add(goal_value);

		//***********************************************************************************************************************
		
		if(!isTesting)
		{
			//Backpropagation
			nltwo = new LayerTransferWeightObj();
			Backpropagation bp = new Backpropagation(net.getLtwo(), teaching_value, goal_value);
			nltwo = bp.theBackpropagation(net.getOutput_nr(), net.getHidden_nr(), net.getInput_nr(), net.getLtwo());
			net.setLtwo(nltwo.changeToUpdated(net.getLtwo(), nltwo));
		}
		
		
		//************************************************************************************************************************

		return net.getLtwo();
	}	

	//############################################################################################################################################################

	/**
	 * Aufsammeln (ausschlieslich) der Eingabewerte welche kein Zielwert sind
	 * @param full_in
	 * @return Eingabewerteliste
	 */
	public LinkedList<Double> get_In_Values(LinkedList<Double> full_in)
	{
		LinkedList<Double> output = new LinkedList<Double>();
		
		for(int z = 0; z < full_in.size()-1; z++)
		{
			output.add(full_in.get(z));
		}
		
		return output;
	}

	public LinkedList<Double> getPattern_goal_values() {
		return pattern_goal_values;
	}

	public void setPattern_goal_values(LinkedList<Double> pattern_goal_values) {
		this.pattern_goal_values = pattern_goal_values;
	}


	public LinkedList<LinkedList<Double>> getPattern_vec_outs() {
		return pattern_vec_outs;
	}

	public void setPattern_vec_outs(LinkedList<LinkedList<Double>> pattern_vec_outs) {
		this.pattern_vec_outs = pattern_vec_outs;
	}

	public LayerTransferWeightObj getLto() 
	{
		return lto;
	}

	public void setLto(LayerTransferWeightObj lto) 
	{
		this.lto = lto;
	}

	public double getNew_goal_max() {
		return new_goal_max;
	}

	public void setNew_goal_max(double new_goal_max) {
		this.new_goal_max = new_goal_max;
	}

	public double getNew_goal_min() {
		return new_goal_min;
	}

	public void setNew_goal_min(double new_goal_min) {
		this.new_goal_min = new_goal_min;
	}

	public double getOld_goal_max() {
		return old_goal_max;
	}

	public void setOld_goal_max(double old_goal_max) {
		this.old_goal_max = old_goal_max;
	}

	public double getOld_goal_min() {
		return old_goal_min;
	}

	public void setOld_goal_min(double old_goal_min) {
		this.old_goal_min = old_goal_min;
	}
	
	//############################################################################################################################################################

	/**
	 * START
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException
	{
		LinkedList<LinkedList<Double>> ld_content = new LinkedList<LinkedList<Double>>();
		LinkedList<LinkedList<Neuron>> end_out = new LinkedList<LinkedList<Neuron>>();
		LinkedList<LinkedList<Double>> end_content = new LinkedList<LinkedList<Double>>();
		LinkedList<Integer> anz_HNeuronen_pro_Lay = new LinkedList<Integer>();
		LinkedList<String> funk_pro_HLayer = new LinkedList<String>();
		LinkedList<String> raw = new LinkedList<String>();
		LinkedList<Double> zielwerte = new LinkedList<Double>();
		LinkedList<Double> in_values = new LinkedList<Double>();
		LinkedList<String> test = new LinkedList<String>();
		LinkedList<LinkedList<Double>> dataset = new LinkedList<LinkedList<Double>>();
		LinkedList<LinkedList<Double>> normalized_dataset = new LinkedList<LinkedList<Double>>();
		LinkedList<Double> weight_range = new LinkedList<Double>();
		LinkedList<Double> finals = new LinkedList<Double>();
		LinkedList<Double[]> intervall_old = new LinkedList<Double[]>();
		LinkedList<Double> reprocessedOut = new LinkedList<Double>();
		
		PreprocessingLinear p = null;
		LayerTransferWeightObj ANNlto = null;
		IntervallDefinitionCollector idc = null;
		FileReaderAndConverter fac = null;
		FileReaderAndConverter fr = null;
		Main m = new Main();
		TheNet tn = null;
		ConfigObject co = null;
		DoubleConverter dc = null;
		
		String path_new_file = null;
		String path_datasets = null;
		String out_funk = null;
		String in_funk = null;
		String path_cfg = null;
		String error_measure = null;
		String tmp_pp_path = null;
		String weightPathOut = null;
		
		boolean isTesting = false;
		
		double dataset_error = Double.NaN;
		double goal_value = Double.NaN;
		double max_pat_err = Double.NaN;
		double teaching_value = Double.NaN;
		double final_value = Double.NaN;
		
		int cur_round = 1;
		int input_count = -1;
		int maxIterations = -1;
		int h_Layer_Depth = -1;
		int output_count = -1;
		int report_update = -1;
		int ds_vec_size = 0;
		
		System.out.println("****************************************************************************");
		System.out.println("*                              Dear User!                                  *");
		System.out.println("*  This programm contains the construction of an ANN and its calculation.  *");
		System.out.println("*   Additionaly this programm allows to test datasets on inserted ANN's.   *");
		System.out.println("*                              Let's go!                                   *");
		System.out.println("****************************************************************************");
		System.out.println("*                     Developed by Tobias Turke                            *");
		System.out.println("****************************************************************************");
		System.out.println("");

		co = new ConfigObject();
		System.out.println();
		path_cfg = co.applicationFolder()+"/"+args[0];
		
		if(path_cfg.contains("\\"))
		{
			path_cfg = path_cfg.replace("\\", "/");
		}
		
		fac = new FileReaderAndConverter(path_cfg);
		test = fac.readFile();		
		co = fac.loadConfig(test);
		co.showContent(co);
		
		System.out.println("");
		System.out.println("####################################### WORK IN PROGRESS ########################################");
		System.out.println("");
		
		//Setze Vektorsize
		ds_vec_size = co.getnInput_count()+1;
		dc = new DoubleConverter();
		//=======================================================================
		
		if(co.isPreprocessing_enabled())
		{
			if(co.isTraining_enabled())
			{
				path_datasets = co.getTraining();
				path_new_file = co.getTrainingResultsOut();
				
			}else{
				path_datasets = co.getTesting();
				path_new_file = co.getTestingResultsOut();
			}
			
			//Intervall check des Dataset für jeden Vektor
			fr = new FileReaderAndConverter(path_datasets);
			dataset = fr.readDatasetFile();
			
			
			if(co.isTraining_enabled())
			{
				
				idc = new IntervallDefinitionCollector();
				intervall_old = idc.getDatasetRange(dataset, ds_vec_size, co.getPreprocessingToleranz());
				idc.transform(intervall_old);
				
				//Preprocessing
				p = new PreprocessingLinear(idc.getMax_values(), idc.getMin_values());
				normalized_dataset = p.datasetProceedPP(dataset,idc.getMax_values(), idc.getMin_values(),co.getPreprocessingMax(), co.getPreprocessingMin(), ds_vec_size);
				
				//speichere alte Gewichtszuordnung
				m.savePreprocessingIntervall(co.getPreprocessingFileOut(), path_datasets, intervall_old, co.getPreprocessingMax(), co.getPreprocessingMin());
				m.setNew_goal_max(co.getPreprocessingMax());
				m.setNew_goal_min(co.getPreprocessingMin());
				m.setOld_goal_max(idc.getMax_values().getLast());
				m.setOld_goal_min(idc.getMin_values().getLast());
				
			}else{
								
				//read and parse Gewichtsfile
				fr = new FileReaderAndConverter(co.getANNWeightIn());
				raw = fr.readFile();
				ANNlto = fr.loadANN(raw);
				
				//read and parse Intervallfile
				fr = new FileReaderAndConverter(co.getpreprocIntervallIn());
				raw = fr.readFile();
				IntervallDefinitionObject ANNido = fr.loadIntervallDefinition(raw);
				
				//create test object
				p = new PreprocessingLinear(ANNido.getOrigin_max(), ANNido.getOrigin_min());
				normalized_dataset = p.normalizeTestDataset(dataset, ANNido.getOrigin_max(), ANNido.getOrigin_min(), ANNido.getUpdated_max(), ANNido.getUpdated_min());
				
				m.setNew_goal_max(ANNido.getUpdated_max().getLast());
				m.setNew_goal_min(ANNido.getUpdated_min().getLast());
				m.setOld_goal_max(ANNido.getOrigin_max().getLast());
				m.setOld_goal_min(ANNido.getOrigin_min().getLast());
			}
			
			
			
			
			
			//Speichern der Normalisierten Daten
			tmp_pp_path = co.getDirectory_path()+"/TempPP.dat";
			FileWriterForFinalOutput fwffo = new FileWriterForFinalOutput(tmp_pp_path);
			fwffo.FileCreation(tmp_pp_path);
			fwffo.WriteFile(p.buildOutput(normalized_dataset));
	
			path_datasets = tmp_pp_path;
			
			System.out.println("PREPROCESSING FINISHED!");
			
			
			
		}else{ 

			if(co.isTraining_enabled())
			{
				path_datasets = co.getTraining();
				path_new_file = co.getTrainingResultsOut();
			}else{
				
				//read and parse Gewichtsfile
				fr = new FileReaderAndConverter(co.getANNWeightIn());
				raw = fr.readFile();
				ANNlto = fr.loadANN(raw);
				
				path_datasets = co.getTesting();
				path_new_file = co.getTestingResultsOut();
			}
		}
		
		System.out.println("\nWorking on Path => "+path_datasets);
		
		//Hole notwendigen Content aus den Dateien
		fac = new FileReaderAndConverter(path_datasets);
		ld_content = fac.readDatasetFile();
		zielwerte = dc.gatherDesiredOutputs(ld_content);
				
		//Initialdurchgang
		in_values = m.get_In_Values(ld_content.get(0));
		input_count = in_values.size();
		goal_value = ld_content.get(0).getLast();
		
		//Error Threshold abfragen
		max_pat_err = co.getErrorThreshold();
		
		//Error Measure abfragen
		error_measure = co.getErrorMeasure();
		
		//Durchlaufanzahlabfragen
		maxIterations = co.getMaxIterations();
		
		//Teachingvalue-Abfrage
		teaching_value = co.getLearningRate();
		
		//Prüfen ob richtige Inputanzahl
		if(co.getnInput_count() != (ld_content.get(0).size()-1))
		{
			if(co.getnInput_count() < ld_content.get(0).size()){
				System.err.println("Input counter zu hoch! => Content: "+(ld_content.get(0).size()-1)+" | Vec_Size: "+co.getnInput_count());
			}else{
				System.err.println("Input counter zu niedrig! => Content: "+(ld_content.get(0).size()-1)+" | Vec_Size: "+co.getnInput_count());
			}
			System.exit(0);
		}

		//Hiddenlayertiefenabfrage
		h_Layer_Depth = co.getnHiddenLayer_size();
		
		//Anzahl der Neuronen je Hiddenlayer Abfrage
		anz_HNeuronen_pro_Lay = co.getnHidden_count();

		//Outputlayertiefenabfrage
		output_count = co.getnOutput_count();
		
		//Inputs Aktivierungsfunktion
		in_funk = co.getInputType();			 
		
		//Hiddens Aktivierungsfunktion
		funk_pro_HLayer = co.getHiddenLayerType();
			
		//Output Aktivierungsfunktion
		out_funk = co.getOutputType();
			 
		//WeightRange definition
		weight_range = co.getWeightRange();
		
		//Report update definition
		report_update = co.getReportUpdate();
		
		//Prüfe auf Testing
		isTesting = co.isTesting_pref();
		
		//Setze WeightANNDefinition
		weightPathOut = co.getWeightFileOut();
			
		//Initial Layer
		LayerTransferWeightObj lto = new LayerTransferWeightObj(input_count, anz_HNeuronen_pro_Lay, output_count, weight_range.get(1), weight_range.get(0));
		
		if(co.isTesting_enabled())
		{
			m.setLto(ANNlto);
		}

		if(co.isTraining_enabled())
		{
			m.setLto(lto);
		}
		
			do
			{
				end_content = new LinkedList<LinkedList<Double>>();
				end_out = new LinkedList<LinkedList<Neuron>>();
				
				m.setPattern_goal_values(new LinkedList<Double>());
				m.setPattern_vec_outs(new LinkedList<LinkedList<Double>>());
				
				for(int move = 0; move < ld_content.size(); move++)
				{	
					//Setzen
					in_values = m.get_In_Values(ld_content.get(move));
					
					goal_value = ld_content.get(move).getLast();
					
					//Baue das Netz für die aktuellen Eingaben
					tn = new TheNet(in_values, in_funk, anz_HNeuronen_pro_Lay, funk_pro_HLayer, output_count, out_funk, goal_value, input_count, m.getLto());

					//Setze Werte des folgenden Vectors
					lto = m.init(in_values, in_funk, h_Layer_Depth, anz_HNeuronen_pro_Lay, funk_pro_HLayer, out_funk, goal_value, teaching_value, output_count, tn, isTesting);

					//Speichere aktuellen Weightcontent
					m.setLto(lto);
					
					//Aktuelle Ausgabe speichern
					end_content.add(ld_content.get(move));
					end_out.add(tn.getOutput_nr());
				}
				
				//Berechne Dataset-Error anhand der Measuretyp-Eingabe
				ErrorFunctionCalculation ec = new ErrorFunctionCalculation(error_measure, m.getPattern_vec_outs(), m.getPattern_goal_values());
				dataset_error = ec.getSolved();
				
				//Iterator aufaddieren
				cur_round++;
				
				
				if(co.isPreprocessing_enabled())
				{
					
					final_value = 0;
					finals = new LinkedList<Double>();
					
					for(int eo_it = 0; eo_it < end_out.size(); eo_it++)
					{
						final_value = 0;
						for(int o_it = 0; o_it < end_out.get(eo_it).size(); o_it++)
						{
							final_value = final_value + end_out.get(eo_it).get(o_it).getSolved_Out_FF();
						}
						
						final_value = final_value / end_out.get(eo_it).size();
						final_value = p.singleValueProceedPreprocessing(final_value, m.getNew_goal_max(), m.getNew_goal_min(), m.getOld_goal_max(), m.getOld_goal_min());
						finals.add(final_value);
					}
					
				}else{
					final_value = 0;
					finals = new LinkedList<Double>();
					
					for(int eo_it = 0; eo_it < end_out.size(); eo_it++)
					{
						final_value = 0;
						for(int o_it = 0; o_it < end_out.get(eo_it).size(); o_it++)
						{
							final_value = final_value + end_out.get(eo_it).get(o_it).getSolved_Out_FF();
						}
						
						final_value = final_value / end_out.get(eo_it).size();
						
						finals.add(final_value);
					}
				}
				
				//Report Update
				if(((cur_round-1) % report_update) == 0)
				{
					System.out.println("Necessary Iterations: "+(cur_round-1)+" \t\tCurrent Error: "+dataset_error);
				}

			}while(maxIterations >= cur_round && max_pat_err < dataset_error || maxIterations == -1 && max_pat_err < dataset_error);
			
			//Delete tmp-file
			if(co.isPreprocessing_enabled())
			{
				fac.deleteFileIfExist(tmp_pp_path);
				
				for(int eo = 0; eo < zielwerte.size(); eo++) //Reprocessing Desired Outputs
				{
					reprocessedOut.add(p.singleValueProceedPreprocessing(zielwerte.get(eo), m.getNew_goal_max(), m.getNew_goal_min(), m.getOld_goal_max(), m.getOld_goal_min()));
				}
				zielwerte = reprocessedOut;
			}
			
			
			
			//Show final Informations
			System.out.println("");
			System.out.println("########################################### WORK END ############################################");
			System.out.println("");
			System.out.println("Necessary Iterations: "+(cur_round-1)+" \t\tCurrent Error: "+dataset_error);
			System.out.println("Finaloutput: "+finals);
			System.out.println("Goalvalues: "+zielwerte);
			System.out.println("");

			if(isTesting)
			{
				m.saveTestingContent(path_new_file, path_datasets, finals, zielwerte, dataset_error);
			}else{
					
				m.saveTrainingContent(path_new_file, weightPathOut, path_cfg, zielwerte, finals, m.getLto(), path_datasets, cur_round, dataset_error);
			}

			System.out.println("WORK COMPLETELY FINISHED!");
	}
}
