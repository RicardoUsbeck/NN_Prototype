import java.util.LinkedList;

/**
 * 
 * @author Tobias Turke
 *
 */
public class IntervallDefinitionObject 
{
	
	private LinkedList<Double> origin_max = new LinkedList<Double>();
	private LinkedList<Double> origin_min = new LinkedList<Double>();
	private LinkedList<Double> updated_max = new LinkedList<Double>();
	private LinkedList<Double> updated_min = new LinkedList<Double>();
	
	public IntervallDefinitionObject(){}
	
	public IntervallDefinitionObject(	LinkedList<Double> origin_max, LinkedList<Double> origin_min,
										double updated_max, double updated_min)
	{
		this.origin_max = origin_max;
		this.origin_min = origin_min;
		
		for(int k = 0; k < origin_max.size();k++)
		{
			this.updated_max.add(updated_max);
		}
		
		for(int k = 0; k < origin_min.size();k++)
		{
			this.updated_min.add(updated_min);
		}	
	}
	
	public void showContent()
	{
		System.out.println("Intervall Definition");
		System.out.println("Origin max.: "+getOrigin_max());
		System.out.println("Origin min.: "+getOrigin_min());
		System.out.println("Update max.: "+getUpdated_max());
		System.out.println("Update min.: "+getUpdated_min());
	}
	
	
	public LinkedList<Double> getOrigin_max() {
		return origin_max;
	}
	public void setOrigin_max(LinkedList<Double> origin_max) {
		this.origin_max = origin_max;
	}
	public LinkedList<Double> getOrigin_min() {
		return origin_min;
	}
	public void setOrigin_min(LinkedList<Double> origin_min) {
		this.origin_min = origin_min;
	}
	public LinkedList<Double> getUpdated_max() {
		return updated_max;
	}
	public void setUpdated_max(LinkedList<Double> updated_max) {
		this.updated_max = updated_max;
	}
	public LinkedList<Double> getUpdated_min() {
		return updated_min;
	}
	public void setUpdated_min(LinkedList<Double> updated_min) {
		this.updated_min = updated_min;
	}
	
}
