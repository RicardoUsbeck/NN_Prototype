import java.util.LinkedList;

//[THIS CLASS WORKS COMPLETELY]
/**
 * 
 * @author Tobias Turke
 *
 */
public class Neuron 
{
	private double solved_value_in_ff;
	private double solved_value_in_bp;
	private double input_IL;
	
	private double solved_value_hd_ff;
	private double solved_value_hd_bp;
	private LinkedList<Double> input_HL;
	
	private double solved_value_out_ff;
	private double solved_value_out_bp;
	private LinkedList<Double> input_OL;
	
	private LinkedList<Double> output_in_weigts;
	private LinkedList<Double> hidden_in_weigts;
	
	private String neuron_type;
	
	//Neuronen Konstruktoren

	/**
	 * Inputneuron
	 * @param input_value
	 */
	public Neuron(double input_value, String func_name)
	{
		setNeuron_type("Input-Neuron");
		
		ActivityFunctions af = new ActivityFunctions(func_name, input_value);
		
		//Speichern des Inputs
		setInput_IL(input_value);
		
		//Aktivierungfunktion
		setSolved_In_FF(af.result);
	
		//Differenzierte Aktivierungsfunktion
		setSolved_In_BP(af.derivate);
	}
	

	/**
	 * Hiddenneuron
	 * @param in_value_list
	 * @param in_weight_list
	 * @param func_name
	 */
	public Neuron(LinkedList<Double> in_value_list, LinkedList<Double> in_weight_list, String func_name)
	{
		
		double length = in_value_list.size();
		double tmp = 0.0;
		
		setNeuron_type("Hidden-Neuron");
		
		//Summe der Produkte aller inputs und deren zugehoerige Gewichte
		for(int k = 0; k < length; k++)
		{
			tmp += (in_value_list.get(k) * in_weight_list.get(k));
		}
		
		ActivityFunctions af = new ActivityFunctions(func_name, tmp);
		
		//Speichern des Inputs
		setInput_HL(in_value_list);
		
		//Speicher eingabe Gewichte
		setHidden_in_weigts(in_weight_list);
		
		//Aktivierungfunktion
		setSolved_Hd_FF(af.result);
	
		//Differenzierte Aktivierungsfunktion
		setSolved_Hd_BP(af.derivate);
	}
	
	
	/**
	 * Outputneuron 
	 * @param func_name
	 * @param hd_value_list
	 * @param hd_weight_list
	 */
	public Neuron(String func_name, LinkedList<Double> hd_value_list, LinkedList<Double> hd_weight_list)
	{
		double length = Math.min(hd_value_list.size(), hd_weight_list.size());;
		double tmp = 0.0;
		
		setNeuron_type("Output-Neuron");
		
		//Summe der Produkte aller inputs und deren zugehoerige Gewichte
		for(int k = 0; k < length; k++)
		{
			tmp += (hd_value_list.get(k) * hd_weight_list.get(k));
		}
		
		ActivityFunctions af = new ActivityFunctions(func_name, tmp);
		
		//Speichern des Inputs
		setInput_OL(hd_value_list);
		
		//Speichern weights
		setOutput_in_Weigts(hd_weight_list);
		
		//Aktivierungfunktion
		setSolved_Out_FF(af.result);
	
		//Differenzierte Aktivierungsfunktion
		setSolved_Out_BP(af.derivate);
	}
	
	//*********************************************************************************************************************//
	//===================================================== SET =============================================================
	//*********************************************************************************************************************//
	
	//*********************************************************************************************************************//
	//Input setter
	
	public void setSolved_In_FF(double solv_in)
	{
		this.solved_value_in_ff = solv_in;
	}
	
	public void setSolved_In_BP(double derv_in)
	{
		this.solved_value_in_bp = derv_in;
	}
	
	public void setInput_IL(double in_ins)
	{
		this.input_IL = in_ins;
	}
	
	//*********************************************************************************************************************//
	//Hidden setter
	
	public void setSolved_Hd_FF(double solv_hd)
	{
		this.solved_value_hd_ff = solv_hd;
	}
	
	public void setSolved_Hd_BP(double derv_hd)
	{
		this.solved_value_hd_bp = derv_hd;
	}
	
	public void setInput_HL(LinkedList<Double> hd_ins)
	{
		this.input_HL = hd_ins;
	}
	
	public void setHidden_in_weigts(LinkedList<Double> hidden_in_weigts) {
		this.hidden_in_weigts = hidden_in_weigts;
	}
	
	//*********************************************************************************************************************//
	//Output setter
	
	public void setSolved_Out_FF(double solv_out)
	{
		this.solved_value_out_ff = solv_out;
	}
	
	public void setSolved_Out_BP(double derv_out)
	{
		this.solved_value_out_bp = derv_out;
	}
	
	public void setInput_OL(LinkedList<Double> out_ins)
	{
		this.input_OL = out_ins;
	}
	
	public void setOutput_in_Weigts(LinkedList<Double> output_in_weigts) {
		this.output_in_weigts = output_in_weigts;
	}
	
	//*********************************************************************************************************************//
	//===================================================== GET =============================================================
	//*********************************************************************************************************************//
	
	//Input getter
	
	public double getSolved_In_FF()
	{
		return this.solved_value_in_ff;
	}
	
	public double getSolved_In_BP()
	{
		return this.solved_value_in_bp;
	}
	
	public double getInput_IL()
	{
		return this.input_IL;
	}
	
	//*********************************************************************************************************************//
	//Hidden getter
	
	public double getSolved_Hd_FF()
	{
		return this.solved_value_hd_ff;
	}
	
	public double getSolved_Hd_BP()
	{
		return this.solved_value_hd_bp;
	}
	
	public LinkedList<Double> getInput_HL()
	{
		return this.input_HL;
	}
	
	public LinkedList<Double> getHidden_in_weigts() {
		return hidden_in_weigts;
	}
	
	//*********************************************************************************************************************//
	//Output getter
	
	public double getSolved_Out_FF()
	{
		return this.solved_value_out_ff;
	}
	
	public double getSolved_Out_BP()
	{
		return this.solved_value_out_bp;
	}
	
	public LinkedList<Double> getInput_OL()
	{
		return this.input_OL;
	}
	
	public LinkedList<Double> getOutput_in_Weigts() {
		return output_in_weigts;
	}


	public String getNeuron_type() {
		return neuron_type;
	}


	public void setNeuron_type(String neuron_type) {
		this.neuron_type = neuron_type;
	}
	
	
}
