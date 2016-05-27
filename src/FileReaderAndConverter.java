import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//[THIS CLASS WORKS COMPLETELY]
/**
 * 
 * @author Tobias Turke
 *
 */
public class FileReaderAndConverter 
{
	private String f_path;
	
	/**
	 * Constructor
	 * @param path
	 */
	public FileReaderAndConverter(String path)
	{
		this.f_path = path;
	}
	
	//############################################################################################################################################################
	
	//[WORKS]
	/**
	 * Diese Methode liest die Dataset.txt und wandelt diese in eine LinkedList<LinkedList<Double>> 
	 * fuer die Weiterverarbeitung um.
	 * @author TTurke
	 * @return LinkedList<LinkedList<Double>>
	 * @throws IOException
	 */
	public LinkedList<LinkedList<Double>> readDatasetFile() throws IOException
	{
		String[] i_parts = null;
		String zeile;
		LinkedList<LinkedList<Double>> obj = new LinkedList<LinkedList<Double>>();
		LinkedList<Double> tmp_ll;
				
		InputStream is = new FileInputStream(f_path);

		if (is != null) 
		{
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader reader = null;

			try {
				reader = new BufferedReader(isr);
	            zeile = null;
	            	while ((zeile = reader.readLine()) != null) 
	            	{
	            		tmp_ll = new LinkedList<Double>();
	            		if(!zeile.isEmpty())
	            		{
	            			i_parts = zeile.split("\\s+");
	            		}	            		
	            		
	            		for(int m = 0; m < i_parts.length; m++)
	            		{
	            			tmp_ll.add(Double.parseDouble(i_parts[m]));
	            		}
	            		
	            		obj.add(tmp_ll);	            		
	            	}
	            	zeile = " ";
	            	
	        	}catch (IOException e)
	        	{
	            e.printStackTrace();  
	            
	        	}finally{
	        		
	            	if (reader != null)
	            		try 
	            		{	
	                		reader.close();
	                		isr.close();
	                		is.close();
	            		} catch (IOException e) {
	            			e.printStackTrace();
	            		}
	        	}
	      }
		return obj;
	}
	
	//############################################################################################################################################################
	
	//[WORKS]
	/**
	 * This instance read the config file
	 * @return list of content
	 * @throws FileNotFoundException
	 */
	public LinkedList<String> readFile() throws FileNotFoundException
	{
		LinkedList<String> loading_content = new LinkedList<String>();
		String zeile;
				
		InputStream is = new FileInputStream(f_path);

		if (is != null) 
		{
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader reader = null;

			try {
				reader = new BufferedReader(isr);
	            zeile = null;
	            	while ((zeile = reader.readLine()) != null) 
	            	{
	            		
	            		if(!zeile.isEmpty())
	            		{
	            			loading_content.add(zeile);
	            		}	            		
	            		
	            	}
	            	zeile = " ";
	            	
	        	}catch (IOException e)
	        	{
	            e.printStackTrace();  
	            
	        	}finally{
	        		
	            	if (reader != null)
	            		try 
	            		{	
	                		reader.close();
	                		isr.close();
	                		is.close();
	            		} catch (IOException e) {
	            			e.printStackTrace();
	            		}
	        	}
	      }
		
		return loading_content;
	}
	
	//############################################################################################################################################################
	
	//[WORKS]
	/**
	 * Loading Configobject
	 * @param content
	 * @return a Configobject
	 */
	public ConfigObject loadConfig(LinkedList<String> content)
	{
		String preprocessing = null;
		double preprocessingMin = Double.NaN;
		double preprocessingMax = Double.NaN;
		double preprocessingToleranz = Double.NaN;
		String preprocessingFileOut = null;
		String training = null;
		String testing = null;
		String trainingResultsOut = null;
		String testingResultsOut = null;
		String weightFileOut = null;
		double errorThreshold = Double.NaN;
		String errorMeasure = null;
		int maxIterations = 0;
		double learningRate = Double.NaN;
		int nInput_count = 0;
		int nHiddenLayer_size = 0;
		LinkedList<Integer> nHidden_count = null;
		int nOutput_count = 0;
		String inputType = null;
		LinkedList<String> hiddenLayerType = null;
		String outputType = null;
		LinkedList<Double> weightRange = null;
		int reportUpdate = -1;
		String aNNWeightIn = null;
		String preprocIntervallIn = null;
		
		String[] tmp_str;
		double tmp_double;
		//int tmp_int;
		
		if(content == null || content.size() < 1)
		{
			System.out.println("Not enough or no content loaded!");
			System.err.println("SYSTEM EXIT!");
			System.exit(0);
		}else{
			
			for(int k = 0; k < content.size(); k++)
			{
				tmp_str = null;
				tmp_double = Double.NaN;
				//tmp_int = -1;
				
				
				if(content.get(k).contains("PreprocessingEnabled"))
				{
					tmp_str = content.get(k).split("\\s+");
					
					if(tmp_str[1].length() > 0)
					{
						preprocessing = tmp_str[1];				
					}
				}
				
				//§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
				
				if(content.get(k).contains("PreprocessingMin"))
				{
					tmp_str = content.get(k).split("\\s+");
					
					if(tmp_str[1].length() > 0)
					{
						preprocessingMin = Double.parseDouble(tmp_str[1]);		
					}					
				}
				
				//§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
				
				if(content.get(k).contains("PreprocessingMax"))
				{
					tmp_str = content.get(k).split("\\s+");
					
					if(tmp_str[1].length() > 0)
					{
						preprocessingMax = Double.parseDouble(tmp_str[1]);				
					}	
				}
				
				//§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
				
				if(content.get(k).contains("PreprocessingFileOut"))
				{
					tmp_str = content.get(k).split("\\s+");
									
					if(tmp_str[1].length() > 0)
					{
						preprocessingFileOut = tmp_str[1];		
					}
				}
				
				//§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
				
				if(content.get(k).contains("Training") && !content.get(k).contains("TrainingResultsOut"))
				{
					tmp_str = content.get(k).split("\\s+");
					
					if(tmp_str[1].length() > 0)
					{
						training = tmp_str[1];			
					}
				}
				
				//§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
				
				if(content.get(k).contains("Testing") && !content.get(k).contains("TestingResultsOut"))
				{
					tmp_str = content.get(k).split("\\s+");
					
					if(tmp_str[1].length() > 0)
					{
						testing = tmp_str[1];			
					}
				}
				
				//§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
				
				if(content.get(k).contains("TrainingResultsOut"))
				{
					tmp_str = content.get(k).split("\\s+");
					
					if(tmp_str[1].length() > 0)
					{
						trainingResultsOut = tmp_str[1];		
					}
				}
				
				//§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
				
				if(content.get(k).contains("TestingResultsOut"))
				{
					tmp_str = content.get(k).split("\\s+");
					
					if(tmp_str[1].length() > 0)
					{
						testingResultsOut = tmp_str[1];		
					}
				}
				
				//§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§

				if(content.get(k).contains("PreprocIntervallIn"))
				{
					tmp_str = content.get(k).split("\\s+");
					
					if(tmp_str[1].length() > 0)
					{
						preprocIntervallIn = tmp_str[1];				
					}
				}
				
				//§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
				
				if(content.get(k).contains("ANNWeightIn"))
				{
					tmp_str = content.get(k).split("\\s+");
					
					if(tmp_str[1].length() > 0)
					{
						aNNWeightIn = tmp_str[1];				
					}
				}
				
				//§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
				
				if(content.get(k).contains("WeightFileOut"))
				{
					tmp_str = content.get(k).split("\\s+");
					
					if(tmp_str[1].length() > 0)
					{
						weightFileOut = tmp_str[1];			
					}
				}
				
				//§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
				
				if(content.get(k).contains("ErrorThreshold"))
				{
					tmp_str = content.get(k).split("\\s+");
					tmp_double = Double.parseDouble(tmp_str[1]);
					
					if(tmp_str[1].length() > 0)
					{
						errorThreshold = tmp_double;			
					}
				}
				
				//§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
				
				if(content.get(k).contains("ErrorMeasure"))
				{
					tmp_str = content.get(k).split("\\s+");
					
					if(tmp_str[1].length() > 0)
					{
						errorMeasure = tmp_str[1];			
					}
				}
				
				//§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
				
				if(content.get(k).contains("MaxIterations"))
				{
					tmp_str = content.get(k).split("\\s+");
					
					if(tmp_str[1].length() > 0)
					{
						maxIterations = Integer.parseInt(tmp_str[1]);		
					}
				}
				
				//§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
				
				if(content.get(k).contains("LearningRate"))
				{
					tmp_str = content.get(k).split("\\s+");
					
					if(tmp_str[1].length() > 0)
					{
						learningRate = Double.parseDouble(tmp_str[1]);			
					}
				}
				
				//§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
				
				if(content.get(k).contains("NInput"))
				{
					tmp_str = content.get(k).split("\\s+");
					
					if(tmp_str[1].length() > 0)
					{
						nInput_count = Integer.parseInt(tmp_str[1]);			
					}
				}
				
				//§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
				
				if(content.get(k).contains("NHiddenLayer"))
				{
					tmp_str = content.get(k).split("\\s+");
					
					if(tmp_str[1].length() > 0)
					{
						nHiddenLayer_size = Integer.parseInt(tmp_str[1]);			
					}
				}
				
				//§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
				
				if(content.get(k).contains("NHidden") && !content.get(k).contains("NHiddenLayer"))
				{
					nHidden_count = new LinkedList<Integer>();
					tmp_str = content.get(k).split("\\s+");
					
					if(tmp_str[1].length() > 0)
					{
						for(int z = 1; z < tmp_str.length; z++)
						{
							if(tmp_str[z].length() > 0)
							{
								nHidden_count.add(Integer.parseInt(tmp_str[z]));				
							}
						}			
					}
				}
				
				//§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
				
				if(content.get(k).contains("NOutput"))
				{
					tmp_str = content.get(k).split("\\s+");
					
					if(tmp_str[1].length() > 0)
					{
						nOutput_count = Integer.parseInt(tmp_str[1]);	
					}
				}
				
				//§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
				
				if(content.get(k).contains("InputType"))
				{
					tmp_str = content.get(k).split("\\s+");
					
					if(tmp_str[1].length() > 0)
					{
						inputType = tmp_str[1];			
					}
				}
				
				//§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
				
				if(content.get(k).contains("HiddenLayerType"))
				{
					hiddenLayerType = new LinkedList<String>();
					tmp_str = content.get(k).split("\\s+");
					
					if(tmp_str.length > 0)
					{
						for(int m = 1; m < tmp_str.length; m++)
						{
							hiddenLayerType.add(tmp_str[m]);
						}				
					}
				}
				
				//§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
				
				if(content.get(k).contains("OutputType"))
				{
					tmp_str = content.get(k).split("\\s+");
					
					if(tmp_str[1].length() > 0)
					{
						outputType = tmp_str[1];				
					}
				}
				
				//§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
				
				if(content.get(k).contains("WeightRange"))
				{
					weightRange = new LinkedList<Double>();
					tmp_str = content.get(k).split("\\s+");

					if(tmp_str[1].length() > 0 && tmp_str[2].length() > 0)
					{
						weightRange.add(Double.parseDouble(tmp_str[1]));
						weightRange.add(Double.parseDouble(tmp_str[2]));			
					}
				}
				
				//§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
				
				if(content.get(k).contains("ReportUpdate"))
				{
					tmp_str = content.get(k).split("\\s+");
					
					if(tmp_str[1].length() > 0)
					{
						reportUpdate = Integer.parseInt(tmp_str[1]);				
					}
				}	
				
				//§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
				
				if(content.get(k).contains("PreprocessingToleranz"))
				{
					tmp_str = content.get(k).split("\\s+");
					
					if(tmp_str[1].length() > 0)
					{
						preprocessingToleranz = Double.parseDouble(tmp_str[1]);		
					}					
				}
				
			}	
		}
		
		
		
		ConfigObject co = new ConfigObject(	preprocessing, preprocessingMin, preprocessingMax, preprocessingToleranz, preprocessingFileOut, training, testing, trainingResultsOut, 
											testingResultsOut, weightFileOut, errorThreshold, errorMeasure,  maxIterations,  learningRate, 
											nInput_count, nHiddenLayer_size, nHidden_count, nOutput_count, inputType, hiddenLayerType, outputType, 
											weightRange, reportUpdate, aNNWeightIn, preprocIntervallIn);
		return co;
	}
		
	//############################################################################################################################################################
	
	//[WORKS]
	/**
	 * Loading LayerTransferWeightObj 
	 * @param content
	 * @return LayerTransferWeightObj
	 */
	public LayerTransferWeightObj loadANN(LinkedList<String> content)
	{
		LayerTransferWeightObj loaded = new LayerTransferWeightObj();
		LinkedList<Double> transitions = new LinkedList<Double>();
		LinkedList<Double> all_trans = new LinkedList<Double>();
		LinkedList<LinkedList<Double>> layer_trans = new LinkedList<LinkedList<Double>>();
		
		String[] line_content;
		Pattern p = Pattern.compile("(\\+|-)?([0-9]+(\\.[0-9]+))");
		Matcher m = null;
		int nr_count = 0, trans_count = 0, counter = 0, cur_hl = -1;
		
		boolean i_to_h = false;
		boolean h_to_h = false;
		boolean h_to_o = false;
		
		for(int ait = 0; ait < content.size(); ait++)
		{
			if(content.get(ait).equals("INPUT-TO-HIDDENLAYER-WEIGHTLISTS"))
			{
				i_to_h = true;
				h_to_h = false;
				h_to_o = false;
				continue;
			}
			
			if(content.get(ait).equals("BETWEEN HIDDENLAYERS-WEIGHTLISTS"))
			{
				i_to_h = false;
				h_to_h = true;
				h_to_o = false;
				continue;
			}
			
			if(content.get(ait).equals("HIDDEN-OUTPUTLAYER-WEIGHTLISTS"))
			{
				i_to_h = false;
				h_to_h = false;
				h_to_o = true;
				continue;
			}
			
			//I->H
			if(i_to_h && !h_to_h && !h_to_o)
			{		
				if(content.get(ait).contains("Start-Neurons"))
				{
					line_content = content.get(ait).split("\\s+");
					nr_count = Integer.parseInt(line_content[1]);
					
				}else if(content.get(ait).contains("Transitions"))
				{
					line_content = content.get(ait).split("\\s+");
					trans_count = Integer.parseInt(line_content[1]);
					
				}else
				{
					layer_trans = new LinkedList<LinkedList<Double>>();
					all_trans = new LinkedList<Double>();
					counter = 0;
					m = p.matcher(content.get(ait));
					
					while (m.find()) 
					{
				        all_trans.add(Double.parseDouble(m.group()));
				    }
					
					
					for(int nit = 0; nit < nr_count; nit++)
					{
						transitions = new LinkedList<Double>();
						
						for(int tit = 0; tit < trans_count; tit++)
						{
							transitions.add( all_trans.get(tit+counter));
						}
						
						counter += trans_count;
						layer_trans.add(transitions);
					}
					loaded.setIn_to_hidden(layer_trans);
				}
				
				if(m != null)
				{
					m.reset();
				}

				continue;
			}else
			
			//##################################################################################
			
			//H->H
			if(!i_to_h && h_to_h && !h_to_o)
			{
				line_content = content.get(ait).split("\\s+");
				
				if(content.get(ait).contains("NEXT HIDDENLAYER-WEIGHTLIST"))
				{
					line_content = content.get(ait).split("\\s+");					
					cur_hl = Integer.parseInt(line_content[2]);
					
				}else if(content.get(ait).contains("Start-Neurons"))
				{
					line_content = content.get(ait).split("\\s+");
					nr_count = Integer.parseInt(line_content[1]);
					
				}else if(content.get(ait).contains("Transitions"))
				{
					line_content = content.get(ait).split("\\s+");
					trans_count = Integer.parseInt(line_content[1]);
				}

				else
				{
					layer_trans = new LinkedList<LinkedList<Double>>();
					all_trans = new LinkedList<Double>();
					counter = 0;
					m = p.matcher(content.get(ait));
					
					while (m.find()) 
					{
				        all_trans.add(Double.parseDouble(m.group()));
				    }
					
					for(int nit = 0; nit < nr_count; nit++)
					{
						transitions = new LinkedList<Double>();
						
						for(int tit = 0; tit < trans_count; tit++)
						{
							transitions.add( all_trans.get(tit+counter));
						}
						
						counter += trans_count;
						layer_trans.add(transitions);
					}
					loaded.getPer_HLayer().add(layer_trans);
				}

				if(m != null)
				{
					m.reset();
				}
				
				continue;
			}else
			
			//##################################################################################
				
			//H->O
			if(!i_to_h && !h_to_h && h_to_o)
			{
				
				if(content.get(ait).contains("Start-Neurons"))
				{
					line_content = content.get(ait).split("\\s+");
					nr_count = Integer.parseInt(line_content[1]);
					
				}else if(content.get(ait).contains("Transitions"))
				{
					line_content = content.get(ait).split("\\s+");
					trans_count = Integer.parseInt(line_content[1]);
					
				}else
				{
					layer_trans = new LinkedList<LinkedList<Double>>();
					all_trans = new LinkedList<Double>();
					counter = 0;
					m = p.matcher(content.get(ait));
					
					while (m.find()) 
					{
				        all_trans.add(Double.parseDouble(m.group()));
				    }
					
					for(int nit = 0; nit < nr_count; nit++)
					{
						transitions = new LinkedList<Double>();
						
						for(int tit = 0; tit < trans_count; tit++)
						{
							transitions.add( all_trans.get(tit+counter));
						}
						
						counter += trans_count;
						layer_trans.add(transitions);
					}
					loaded.setHidden_to_output(layer_trans);
				}
				
				if(m != null)
				{
					m.reset();
				}
				
				continue;
			}
		}
		return loaded;
	}
	
	//############################################################################################################################################################
	
	//[WORKS]
	/**
	 * Loading IntervallDefinitionObject
	 * @param content
	 * @return IntervallDefinitionObject
	 */
	public IntervallDefinitionObject loadIntervallDefinition(LinkedList<String> content)
	{
		IntervallDefinitionObject ido = null;
		Pattern p = Pattern.compile("(\\+|-)?([0-9]+(\\.[0-9]+))");
		Matcher m = null;
		
		double updated_max = Double.NaN, updated_min = Double.NaN;
		LinkedList<Double> origin_max = new LinkedList<Double>();
		LinkedList<Double> origin_min = new LinkedList<Double>();
		
		for(int ait = 0; ait < content.size(); ait++)
		{
			if(content.get(ait).contains("Old dataset max"))
			{				
				m = p.matcher(content.get(ait));
				
				while (m.find()) 
				{
			        
					origin_max.add(Double.parseDouble(m.group()));
			    }

				if(m != null)
				{
					m.reset();
				}
				
				continue;
			}
			
			if(content.get(ait).contains("Old dataset min"))
			{				
				m = p.matcher(content.get(ait));
				
				while (m.find()) 
				{
			        
					origin_min.add(Double.parseDouble(m.group()));
			    }

				if(m != null)
				{
					m.reset();
				}
				
				continue;
			}
			
			
			if(content.get(ait).contains("New max"))
			{
				m = p.matcher(content.get(ait));
				
				while (m.find()) 
				{
					updated_max = Double.parseDouble(m.group());
			    }

				if(m != null)
				{
					m.reset();
				}
				
				continue;
			}
			
			if(content.get(ait).contains("New min"))
			{
				m = p.matcher(content.get(ait));
				
				while (m.find()) 
				{
					updated_min = Double.parseDouble(m.group());
			    }

				if(m != null)
				{
					m.reset();
				}
				
				continue;
			}
		}
		
		if(	updated_max != Double.NaN && updated_min != Double.NaN
				&& !origin_max.isEmpty() && !origin_min.isEmpty())
		{
			ido = new IntervallDefinitionObject(origin_max, origin_min, updated_max, updated_min);
		}
		
		return ido;
	}
	
	//############################################################################################################################################################
	
	//[WORKS]
	/**
	 * Delete a File in the Pathfolder if it exists
	 * @param path
	 * @return a boolean that give you the deleting status
	 */
	public boolean deleteFileIfExist(String path)
	{
		boolean wasDeleted = false;
		
		try{
	        File fileTemp = new File(path);
	        
	        if (fileTemp.exists())
	        {
	        	wasDeleted = fileTemp.delete();
	        }
	          
	    }catch(Exception e)
	    {
	         e.printStackTrace();
	    }
		return wasDeleted;
	}
		
}
