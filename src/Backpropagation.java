import java.util.LinkedList;

/**
 * 
 * @author Tobias Turke
 *
 */
public class Backpropagation 
{
	private LinkedList<LinkedList<LinkedList<Double>>> per_HLayer = new LinkedList<LinkedList<LinkedList<Double>>>();
	private LinkedList<LinkedList<Double>> hidden_to_output = new LinkedList<LinkedList<Double>>();
	LinkedList<Double> prev_errs = new LinkedList<Double>();
	private double teaching_value = Double.NaN;
	private double goal_value = Double.NaN;
	
	/**
	 * Konstruktor ohne maximale Wiederholungsdauer
	 * @param input_Layer_Rand
	 * @param hidden_Layer_Rands
	 * @param output_Layer_Rands
	 * @param input_Layer
	 * @param hidden_Layer
	 * @param output_Layer
	 * @param output_Value
	 * @param goal_Value
	 */
	public Backpropagation(	LayerTransferWeightObj ltwo, double teaching_value, double goal_value)
	{
		this.per_HLayer = ltwo.getPer_HLayer();
		this.hidden_to_output = ltwo.getHidden_to_output();
		this.teaching_value = teaching_value;
		this.goal_value = goal_value;
	}

	
	//#########################################################################################################
	//################################### The error funtions ##################################################
	//#########################################################################################################
	
	
	/**
	 * This function solve the local delta error of the OUTPUTLAYER units for the backpropagation Algoritm.
	 * Form := derivate_cur_node * (teaching_input - output_cur_node)
	 * @param neuron
	 * @return LinkedList of all Outputlayer errorvalues
	 */
	private LinkedList<Double> propagationErrFuncOutput(LinkedList<Neuron> o_neurons)
	{
		double err_value;
		LinkedList<Double> error_List = new LinkedList<Double>();
		
		for(int v = 0; v < o_neurons.size(); v++)
		{
			err_value = Double.NaN;
			err_value = o_neurons.get(v).getSolved_Out_BP() * (goal_value - o_neurons.get(v).getSolved_Out_FF());
			error_List.add(err_value);
		}
		
		return error_List;
	}

	
	
	/**
	 * This function solve the local delta error of the hiddenputlayer units for the backpropagation Algoritm.
	 * Form: cur_neuron_derivate * Summ(error_previous_lay * weight_from_cur_to_prev)
	 * @param neurons_cur
	 * @param neurons_prev
	 * @param weights_cur_to_prev
	 * @param prev_errors
	 * @param neuron_Typ
	 * @return LinkedList of all errorvalues of the current Layer
	 */
	private LinkedList<Double> propagationErrFuncHidden(LinkedList<Neuron> neuron_cur, 
														LinkedList<LinkedList<Double>> weights_cur_to_prev, LinkedList<Double> prev_errors, 
														String neurons_Typ)
	{		
		double err_value, summ, derivate;
		LinkedList<Double> error_List = new LinkedList<Double>();
		
		for(int iter = 0; iter < neuron_cur.size(); iter++)
		{
			summ = Double.NaN;
			summ = 0.0;
			err_value = Double.NaN;
			derivate = Double.NaN;

			for(int curs = 0; curs < weights_cur_to_prev.get(iter).size(); curs++)
			{			
				summ = summ + (prev_errors.get(curs) * weights_cur_to_prev.get(iter).get(curs));
			}
						
			//Derivate Hidden
			if(neurons_Typ.equals("Hidden"))
			{
				derivate = neuron_cur.get(iter).getSolved_Hd_BP();
			}
			
			err_value = derivate * summ;
			error_List.add(err_value);
		}
		return error_List;
	}
	
	
	
	/**
	 * This function creates the edit value for the rands between the current given Layer and the previous
	 * @param neuronen
	 * @param error_Values
	 * @param neuron_Typ
	 * @return LinkedList<LinkedList<Double>> Layerslist with edited rands
	 */
	private LinkedList<Double> propagationAlgo(Neuron cur_neuron, LinkedList<Double> prev_err_Values, String neuron_Typ)
	{
		LinkedList<Double> new_rnds_cur_neur = new LinkedList<Double>();
		int prev_size = prev_err_Values.size();
		double change_rand;
		
		for(int cit = 0; cit < prev_size; cit++)
		{
			change_rand = Double.NaN;			
			
			if(prev_size < 1)
			{
				System.out.println("No previous Neuron exists!");
				System.exit(0);
			}else{
				
				if(!neuron_Typ.equals("Input") && !neuron_Typ.equals("Hidden"))
				{
					System.out.println("False Neuron Typ!");
					System.exit(0);
				}
				
				if(neuron_Typ.equals("Input"))
				{
					
					change_rand = teaching_value * cur_neuron.getSolved_In_FF() * prev_err_Values.get(cit);
					
					if(cur_neuron.getSolved_In_FF() == 0 || prev_err_Values.get(cit) == 0)
					{
						change_rand = Math.abs(change_rand);
					}
				}
				
				if(neuron_Typ.equals("Hidden"))
				{
					change_rand = teaching_value * cur_neuron.getSolved_Hd_FF() * prev_err_Values.get(cit);
					
					if(cur_neuron.getSolved_Hd_FF() == 0 || prev_err_Values.get(cit) == 0)
					{
						change_rand = Math.abs(change_rand);
					}
				}				
			}
			new_rnds_cur_neur.add(change_rand);
		}
		return new_rnds_cur_neur;
	}
	
	
	//#########################################################################################################
	//################################## The Backpropagation ##################################################
	//#########################################################################################################
	
	
	/**
	 * This function solves the whole Backpropagation-Work of the ANN
	 * @param output_Layer
	 * @param hidden_Layer
	 * @param input_Layer
	 * @param old_ltwo
	 * @return an Object of typ LayerTransferWeightObj with all new/edited weights for each connection
	 */
	public LayerTransferWeightObj theBackpropagation(LinkedList<Neuron> output_Layer, LinkedList<LinkedList<Neuron>> hidden_Layer, LinkedList<Neuron> input_Layer, LayerTransferWeightObj old_ltwo)
	{
		//Erzeugen der Errorvalues jedes Output & Hiddenknoten
		LinkedList<Double> output_errs = new LinkedList<Double>();
		LinkedList<Double> hidden_errs = new LinkedList<Double>();		
		LayerTransferWeightObj new_ltwo = new LayerTransferWeightObj();
		
		//Temporaere Datentypen fuer die weight-Anpassungen
		LinkedList<LinkedList<Double>> one_to_many = new LinkedList<LinkedList<Double>>();
		LinkedList<LinkedList<LinkedList<Double>>> many_to_many = new LinkedList<LinkedList<LinkedList<Double>>>();
		LinkedList<LinkedList<LinkedList<Double>>> sorted_weights = new LinkedList<LinkedList<LinkedList<Double>>>();
		
		//Knotentypen
		String n_typ_hd = "Hidden";
		String n_typ_in = "Input";
		int last_h_l = hidden_Layer.size()-1;
				
		//##################### H <-- O #####################
		//O_errs
		output_errs = propagationErrFuncOutput(output_Layer);
		
		//Iterator ueber letzten Hiddenlayerpart
		for(int iter = 0; iter < hidden_Layer.get(last_h_l).size(); iter++)
		{
			one_to_many.add(propagationAlgo(hidden_Layer.get(last_h_l).get(iter), output_errs, n_typ_hd));
		}

		//uebergebe an Weight-Object
		new_ltwo.setHidden_to_output(one_to_many);

		//*********************************************************************************************************
		
		//##################### H <-- H #####################
		//Iteration rueckwaerts ueber die Layer
		for(int iter = last_h_l; -1 < iter; iter--)
		{
			one_to_many = new LinkedList<LinkedList<Double>>();
			hidden_errs = new LinkedList<Double>();
			
			if(iter == hidden_Layer.size()-1)
			{
				hidden_errs = propagationErrFuncHidden(hidden_Layer.get(iter), getHidden_to_output(), output_errs, n_typ_hd);
				setPrev_errs(hidden_errs);
			}else{ 
				hidden_errs = propagationErrFuncHidden(hidden_Layer.get(iter), getPer_HLayer().get(iter), getPrev_errs(), n_typ_hd);
				setPrev_errs(hidden_errs);
			}
			
			//Neue weights nur innerhalb des Hiddenlayers berechnen
			if(iter > 0 )
			{
				for(int curs = 0; curs < hidden_Layer.get(iter-1).size(); curs++)
				{
					one_to_many.add(propagationAlgo(hidden_Layer.get(iter-1).get(curs), hidden_errs, n_typ_hd));
				}
				many_to_many.add(one_to_many);
			}		
		}
		
		//Liste korrekt sortieren
		sorted_weights = new LinkedList<LinkedList<LinkedList<Double>>>();
		
		for(int iter = many_to_many.size()-1; -1 < iter; iter--)
		{
			sorted_weights.add(many_to_many.get(iter));
		}
		
		
		//uebergebe korrekt sortierte Liste an Weight-Object
		new_ltwo.setPer_HLayer(sorted_weights);
				
		
		//*********************************************************************************************************
		
		//##################### I <-- H #####################
		
		//Inputlayer
		one_to_many = new LinkedList<LinkedList<Double>>();

		for(int iter = 0; iter < input_Layer.size(); iter++)
		{
			one_to_many.add(propagationAlgo(input_Layer.get(iter), hidden_errs, n_typ_in));
		}
		
		//uebergebe an Weight-Object
		new_ltwo.setIn_to_hidden(one_to_many);
		return new_ltwo;
	}


	public LinkedList<LinkedList<LinkedList<Double>>> getPer_HLayer() {
		return per_HLayer;
	}

	public LinkedList<LinkedList<Double>> getHidden_to_output() {
		return hidden_to_output;
	}

	public LinkedList<Double> getPrev_errs() {
		return prev_errs;
	}

	public void setPrev_errs(LinkedList<Double> prev_errs) {
		this.prev_errs = prev_errs;
	}

	
	
}
