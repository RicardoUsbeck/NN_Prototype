import java.io.IOException;
import java.util.LinkedList;

//[THIS CLASS WORKS COMPLETELY]
/**
* 
* @author Tobias Turke
*
*/
public class IntervallDefinitionCollector 
{
	private LinkedList<Double> max_values = null;
	private LinkedList<Double> min_values = null;
	
	/**
	 * Diese Instanz ermittelt das Intervall eines Datasets mittels Minimum und Maximum und kalkuliert diese Grenzen zur Weiterverarbeitung mit einer Toleranzschwelle
	 * @param dataset
	 * @param input_size
	 * @param toleranz
	 * @return Datasetrange wit all column ranges of the vectorial dataset
	 */
	public LinkedList<Double[]> getDatasetRange(LinkedList<LinkedList<Double>> dataset, int input_size, double toleranz)
	{
		double min=Double.MAX_VALUE;
		double max=Double.MIN_VALUE;
		Double[] min_max;
		double test = Double.MAX_VALUE;
		boolean has_changed = false;
		LinkedList<Double[]> ds_intervall_ranges = new LinkedList<Double[]>();
		
		Rounding in_max;
		Rounding in_min;
		
		//#############################################################################
		
		for(int is_it = 0; is_it < input_size; is_it++)
		{			
			min=Double.MAX_VALUE;
			max=(-1 * Double.MAX_VALUE);
			min_max = new Double[2];
			
			for(LinkedList<Double> vec : dataset)
			{
				//Prüfe ob min = max
				if(is_it == 0)
				{
					test = vec.get(is_it);
				}else{
					if(test != vec.get(is_it) && !has_changed)
					{
						has_changed = true;
					}
				}
				
				
				if (vec.get(is_it) > max){ max = vec.get(is_it); }
				if (vec.get(is_it) < min){ min = vec.get(is_it); }
			}
			
			//Kalkulation der Grenzen mit Toleranz
			in_max = new Rounding(false, max, toleranz);
			in_min = new Rounding(false, min, toleranz);
			
			min_max[0] = in_min.getOutput();
			min_max[1] = in_max.getOutput();
			
			
			if(min_max[0] == null && min_max[1] != null){ min_max[0] = min_max[1]; }
			if(min_max[0] != null && min_max[1] == null){ min_max[1] = min_max[0]; }

			ds_intervall_ranges.add(min_max);
		}
		
		//#############################################################################
		
		return ds_intervall_ranges;
	}
	
	/**
	 * Diese Instanzerstellt aus dem minima maxima Linkedlist-Array zwei einzelne LinkedLists je eine für Minima und Maxima. 
	 * Und speichert diese in den zwei Klassenvariablen.
	 * @param input
	 */
	public void transform(LinkedList<Double[]> input)
	{
		LinkedList<Double> maxima = new LinkedList<Double>();
		LinkedList<Double> minima = new LinkedList<Double>();
		
		for(int z_it = 0; z_it < input.size(); z_it++)
		{
			maxima.add(input.get(z_it)[1]);
			minima.add(input.get(z_it)[0]);
		}

		this.max_values = maxima;
		this.min_values = minima;
	}


	public LinkedList<Double> getMax_values() {
		return max_values;
	}


	public LinkedList<Double> getMin_values() {
		return min_values;
	}
	
	public static void main(String[] args) throws IOException
	{
		String s1 = "C:/Users/Subadmin/workspace/ToyBeispiel/src/test.norm";
		String s2 = "C:/Users/Subadmin/workspace/ToyBeispiel/src/train.norm";		
		FileReaderAndConverter fr = new FileReaderAndConverter(s2);
		LinkedList<LinkedList<Double>> dataset = fr.readDatasetFile();
		
		IntervallDefinitionCollector idc = new IntervallDefinitionCollector();
		
		LinkedList<Double[]> ranges = new LinkedList<Double[]>();
		
		
		ranges = idc.getDatasetRange(dataset, 2, 0.0);

		idc.transform(ranges);
		
		System.out.println("Training Mins: "+idc.getMin_values()+"");
		System.out.println("Training Maxs: "+idc.getMax_values()+"");
		
		System.out.println("\n+++++++++++++++++++++++++++++++++++++++\n");
		
		fr = new FileReaderAndConverter(s1);
		dataset = fr.readDatasetFile();
		
		idc = new IntervallDefinitionCollector();
		
		ranges = new LinkedList<Double[]>();
		
		
		ranges = idc.getDatasetRange(dataset, 2, 0.0);

		idc.transform(ranges);
		
		System.out.println("Testing Mins: "+idc.getMin_values()+"");
		System.out.println("Testing Maxs: "+idc.getMax_values()+"");
	}
	

}
