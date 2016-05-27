import java.text.NumberFormat;
import java.util.LinkedList;

//[THIS CLASS WORKS COMPLETELY]
/**
 * 
 * @author TTurke
 *
 */
public class DoubleConverter 
{
	/**
	 * This method trim the decimal place of value by the number of max_decimal_place
	 * @param max_decimal_place
	 * @param value
	 * @return trimmed double with max_decimal_place
	 */
	public double doubleConversation(int max_decimal_place, double value)
	{
		NumberFormat n = NumberFormat.getInstance();
		n.setMaximumFractionDigits(max_decimal_place);
		
		double ret = Double.parseDouble(n.format(value).replace(",", ".")); 
		
		return ret;
	}
	
	/**
	 * This method return all desired ouput for each vector of a dataset
	 * @param dataset
	 * @return
	 */
	public LinkedList<Double> gatherDesiredOutputs(LinkedList<LinkedList<Double>> dataset)
	{
		LinkedList<Double> desired_outs = new LinkedList<Double>();
		
		for(LinkedList<Double> vec : dataset)
		{
			desired_outs.add(vec.getLast());
		}
		return desired_outs;
	}

	public static void main(String[] args)
	{
		DoubleConverter dc = new DoubleConverter();
		double test = 2.34745635634567;
		double value = dc.doubleConversation(2, test);
		
		System.out.println(value);
	}
	
}