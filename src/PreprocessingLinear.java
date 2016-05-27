import java.util.LinkedList;

//[THIS CLASS WORKS COMPLETELY]
/**
 * 
 * @author Tobias Turke
 *
 */
public class PreprocessingLinear 
{
	private double oldMax = Double.NaN;
	private double oldMin = Double.NaN;
	private boolean single_intervall_set = false;
	
	private LinkedList<Double> oldMax_DS = new LinkedList<Double>();
	private LinkedList<Double> oldMin_DS = new LinkedList<Double>();
	private boolean vec_intervall_set= false;
	
	PreprocessingLinear(double oldMax, double oldMin)
	{
		this.oldMax = oldMax;
		this.oldMin = oldMin;
		this.single_intervall_set = true;
	}
	
	PreprocessingLinear(LinkedList<Double> oldMax, LinkedList<Double> oldMin)
	{
		this.oldMax_DS = oldMax;
		this.oldMin_DS = oldMin;
		this.vec_intervall_set = true;
	}
	
	
	/**
	 * Min-Max Normalisierung einer Value mittels ursprünglichen und neuen Intervallgrenzen 
	 * @param value
	 * @param interval_maximum
	 * @param interval_minimum
	 * @param new_maximum
	 * @param new_minimum
	 * @return normalisierte Value
	 */
	public double singleValueProceedPreprocessing(	double value, double interval_maximum,
													double interval_minimum, double new_maximum, double new_minimum)
	{
		double ret = ((value - interval_minimum)/(interval_maximum - interval_minimum))*(new_maximum - new_minimum) + new_minimum;
		return ret;
	}

	
	/**
	 * Min-Max Normalisierung auf einem ganzen Datensatz Spaltenweise
	 * @param dataset
	 * @param interval_maximum
	 * @param interval_minimum
	 * @param new_maximum
	 * @param new_minimum
	 * @param input_range
	 * @return
	 */
	public LinkedList<LinkedList<Double>> datasetProceedPP(	LinkedList<LinkedList<Double>> dataset, LinkedList<Double> interval_maximum,
																		LinkedList<Double> interval_minimum, double new_maximum, 
																		double new_minimum, int input_range)
	{
		LinkedList<LinkedList<Double>> resultDataset = new LinkedList<LinkedList<Double>>();
		double current_change = Double.NaN;
		
		for(int dat_it = 0; dat_it < dataset.size(); dat_it++)
		{
			resultDataset.add(new LinkedList<Double>());
		}
		
		for(int in_it = 0; in_it < input_range; in_it++)
		{
			for(int dat_it = 0; dat_it < dataset.size(); dat_it++)
			{
				if(dataset.size() > 1)
				{

					current_change = singleValueProceedPreprocessing(dataset.get(dat_it).get(in_it), interval_maximum.get(in_it), interval_minimum.get(in_it), new_maximum, new_minimum); 
					
					resultDataset.get(dat_it).add(current_change);
				}else{
					System.out.println("Insertion is no Dataset!");
					System.err.println("ERROR: SYSTEM STOP!");
					System.exit(0);
				}
				
			}
		}
		return resultDataset;
	}
	
	
	
	public LinkedList<Double> normalizeTestVector(	LinkedList<Double> inputs, LinkedList<Double> interval_maximum,
													LinkedList<Double> interval_minimum, LinkedList<Double> new_maximum, 
													LinkedList<Double> new_minimum)
	{
		LinkedList<Double> ret = new LinkedList<Double>();
		
		for(int vec_it = 0; vec_it < inputs.size(); vec_it++)
		{
			//Test hier
			if(inputs.get(vec_it) >= interval_minimum.get(vec_it) && inputs.get(vec_it) <= interval_maximum.get(vec_it))
			{
				ret.add(singleValueProceedPreprocessing(inputs.get(vec_it), interval_maximum.get(vec_it), interval_minimum.get(vec_it), new_maximum.get(vec_it), new_minimum.get(vec_it)));
			}else{
				System.err.println("Testing-Vector has not the correct range!?");
				System.out.println("");
				
				if(!(inputs.get(vec_it) >= interval_minimum.get(vec_it)))
				{
					System.out.println("test_min = "+inputs.get(vec_it));
					System.out.println("ds_min = "+ interval_minimum.get(vec_it));
					System.err.println("test_min >= ds_min : false");
				}
				
				if(!(inputs.get(vec_it) <= interval_maximum.get(vec_it)))
				{
					System.out.println("test_max = "+inputs.get(vec_it));
					System.out.println("ds_max = "+interval_maximum.get(vec_it));
					System.err.println("test_max <= ds_max : false");
				}
				
				System.exit(0);
			}
		}
		
		return ret;
	}
	
	//######################################
	
	public LinkedList<LinkedList<Double>> normalizeTestDataset(	LinkedList<LinkedList<Double>> inputs, LinkedList<Double> interval_maximum,
			LinkedList<Double> interval_minimum, LinkedList<Double> new_maximum, 
			LinkedList<Double> new_minimum)
	{
		LinkedList<LinkedList<Double>> ret = new LinkedList<LinkedList<Double>>();
		
		for(int ds_it = 0; ds_it < inputs.size(); ds_it++)
		{
			ret.add(normalizeTestVector(inputs.get(ds_it), interval_maximum, interval_minimum, new_maximum, new_minimum));
		}
		
		return ret;
	}
	
	
	//######################################
	
	
	/**
	 * Erstellen des Outputs nach der Form der Datasetfiles
	 * @param for_conversion
	 * @return
	 */
	public LinkedList<String> buildOutput(LinkedList<LinkedList<Double>> for_conversion)
	{
		LinkedList<String> output_ready = new LinkedList<String>();
		
		for(int or_it = 0; or_it < for_conversion.size(); or_it++)
		{
			String converting = "";
			
			for(int dor_it = 0; dor_it < for_conversion.get(or_it).size(); dor_it++)
			{
				
				
				if(dor_it == for_conversion.get(or_it).size()-1)
				{
					converting = converting + for_conversion.get(or_it).get(dor_it);
					
				}else if(dor_it == for_conversion.get(or_it).size()-2)
				{
					converting = converting + for_conversion.get(or_it).get(dor_it) + "      ";
					
				}else{
					converting = converting + for_conversion.get(or_it).get(dor_it) + " ";
				}
				
			}
			
			output_ready.add(converting);
		}
		
		
		return output_ready;
	}
	
	public double getOldMax()
	{
		if(single_intervall_set)
		{
			return oldMax;
		}else{
			System.out.println("Checkvalue is ["+single_intervall_set+"] so content was not set!");
		}
	
		return Double.NaN;
	}

	public double getOldMin()
	{
		if(single_intervall_set)
		{
			return oldMin;
		}else{
			System.out.println("Checkvalue is ["+single_intervall_set+"] so content was not set!");
		}
	
		return Double.NaN;
	}

	public LinkedList<Double> getOldMax_DS() 
	{
		if(vec_intervall_set)
		{
			return oldMax_DS;
		}else{
			System.out.println("Checkvalue is ["+vec_intervall_set+"] so content was not set!");
		}
	
		return null;
	}

	public LinkedList<Double> getOldMin_DS() 
	{
		if(vec_intervall_set)
		{
			return oldMin_DS;
		}else{
			System.out.println("Checkvalue is ["+vec_intervall_set+"] so content was not set!");
		}

		return null;
	}
	
}
