import java.util.LinkedList;

//[THIS CLASS WORKS COMPLETELY]
/**
 * 
 * @author Tobias Turke
 *
 */
public class LayerTransferWeightObj 
{

	private LinkedList<LinkedList<Double>> in_to_hidden = new LinkedList<LinkedList<Double>>();
	
	private LinkedList<LinkedList<LinkedList<Double>>> per_HLayer = new LinkedList<LinkedList<LinkedList<Double>>>();
	
	private LinkedList<LinkedList<Double>> hidden_to_output = new LinkedList<LinkedList<Double>>();

	
	//###########################################################################################################################
	
	//[WORKS] fuer den Editteil
	/**
	 * This constructor is only used for the edit part!
	 */
	public LayerTransferWeightObj()
	{
		
	}
	
	//***************************************************************************************************************************
	
	
	//[WORKS]
	/**
	 * Generator for completely all start weights 
	 * @param input_count
	 * @param layerlist_Hidden_Count
	 * @param output_count
	 */
	public LayerTransferWeightObj(int input_count, LinkedList<Integer> layerlist_Hidden_Count, int output_count, double min, double max)
	{
		RandValueCreator rvc = new RandValueCreator();
		int moves = input_count;
		int layerdepth = layerlist_Hidden_Count.get(0);
		int layer;
		int following;
		

		do
		{
			in_to_hidden.add(rvc.rnd_weights(layerdepth, max, min));

		}while(input_count != in_to_hidden.size());
		

		//*********************************************************************
		
		//Hiddenlayersrnds
		//Layerrnazahl
		layer = layerlist_Hidden_Count.size();
		
		//Hidden to Hidden weightlist
		LinkedList<LinkedList<Double>> tmp_weight_lists = new LinkedList<LinkedList<Double>>();
		LinkedList<Double> values = new LinkedList<Double>();
		
		for(int cur = 0; cur < layer; cur++)
		{
			
			//Hidden to Hidden weightlist
			tmp_weight_lists = new LinkedList<LinkedList<Double>>();
			
			//Aktuelle Layerneuronenanzahl
			moves = layerlist_Hidden_Count.get(cur);
			
			//Nachfolgerlayer
			following = cur+1;
			
			//Pruefe auf Nachfolgerlayer
			if(following < layer)
			{				
				
				
				//Wieiviel Nachfolgerneuronen
				layerdepth = layerlist_Hidden_Count.get(following);

				//Erzeuge Randoms je Neuron des aktuellen Layer
				for(int iter = 0; iter < moves; iter++)
				{
					values = rvc.rnd_weights(layerdepth, max, min);
					tmp_weight_lists.add(values);
				}
				
				per_HLayer.add(tmp_weight_lists);
			}
			
		}
		
		//*********************************************************************
		
		//Outputrnds
		layer = layerlist_Hidden_Count.size()-1;
		moves  = layerlist_Hidden_Count.get(layer);
		layerdepth = output_count;
//		System.out.println("LD: "+layerdepth);
		
		while(moves > 0)
		{
			hidden_to_output.add(rvc.rnd_weights(layerdepth, max, min));
			moves--;
		}
	}

	
	//###########################################################################################################################
	
	
	public LinkedList<LinkedList<Double>> getIn_to_hidden() {
		return in_to_hidden;
	}

	public void setIn_to_hidden(LinkedList<LinkedList<Double>> in_to_hidden) {
		this.in_to_hidden = in_to_hidden;
	}

	public LinkedList<LinkedList<LinkedList<Double>>> getPer_HLayer() {
		return per_HLayer;
	}

	public void setPer_HLayer(LinkedList<LinkedList<LinkedList<Double>>> per_HLayer) {
		this.per_HLayer = per_HLayer;
	}

	public LinkedList<LinkedList<Double>> getHidden_to_output() {
		return hidden_to_output;
	}

	public void setHidden_to_output(LinkedList<LinkedList<Double>> hidden_to_output) {
		this.hidden_to_output = hidden_to_output;
	}
	
	//###########################################################################################################################

	public LayerTransferWeightObj changeToUpdated(LayerTransferWeightObj unchanged, LayerTransferWeightObj changing_values)
	{
		LayerTransferWeightObj changed = new LayerTransferWeightObj();
		LinkedList<LinkedList<LinkedList<Double>>> multiple_layer;
		LinkedList<LinkedList<Double>> single_layer;
		LinkedList<Double> single_neuron;
		double changed_data;
		int len_weights, len_neuron, len_lay;
		
		//***************************************************************************************************************************
		
		//Check all content size are equal!
		
		if(unchanged.getIn_to_hidden().size() != changing_values.getIn_to_hidden().size())
		{
			System.err.println("The Size of the 'Input to Hidden'-weights-list of 'unchanged' Weigtobject and Weigtobject containing 'changing values' does not equal / is not similar !");
			System.exit(0);
		}
		
		if(unchanged.getPer_HLayer().size() != changing_values.getPer_HLayer().size())
		{
			System.err.println("The Size of ALL 'Hidden to Hidden'-weights-list of 'unchanged' Weigtobject and Weigtobject containing 'changing values' does not equal / is not similar !");
			System.exit(0);
		}
		
		if(unchanged.getHidden_to_output().size() != changing_values.getHidden_to_output().size())
		{
			System.err.println("The Size of the 'Hidden to Ouput'-weights-list of 'unchanged' Weigtobject and Weigtobject containing 'changing values' does not equal / is not similar !");
			System.exit(0);
		}
		
		//***************************************************************************************************************************
		
		//Change I -> 1st H
		
		len_neuron = unchanged.getIn_to_hidden().size();
		single_layer = new LinkedList<LinkedList<Double>>();
		
		for(int cursor = 0; cursor < len_neuron; cursor++)
		{
			len_weights = unchanged.getIn_to_hidden().get(cursor).size();
			single_neuron = new LinkedList<Double>();
			
			for(int picker = 0; picker < len_weights; picker++)
			{
				changed_data 	= unchanged.getIn_to_hidden().get(cursor).get(picker) 
								+ changing_values.getIn_to_hidden().get(cursor).get(picker);
				
				single_neuron.add(changed_data);
			}
			single_layer.add(single_neuron);
		}
		
		//Set new content
		changed.setIn_to_hidden(single_layer);
		
		//***************************************************************************************************************************
		
		//Change all from 1st H -> last H
		
		len_lay = unchanged.getPer_HLayer().size();
		multiple_layer = new LinkedList<LinkedList<LinkedList<Double>>>();
		
		for(int iterator = 0; iterator < len_lay; iterator++)
		{			
			if(unchanged.getPer_HLayer().get(iterator).size() !=  changing_values.getPer_HLayer().get(iterator).size())
			{
				System.err.println("The Size of the CURRENT 'Hidden to Hidden'-weights-list of 'unchanged' Weigtobject and Weigtobject containing 'changing values' does not equal / is not similar !");
				System.exit(0);
				
			}else{
				
				len_neuron = unchanged.getPer_HLayer().get(iterator).size();
				single_layer = new LinkedList<LinkedList<Double>>();
				
				for(int cursor = 0; cursor < len_neuron; cursor++)
				{
					len_weights = unchanged.getPer_HLayer().get(iterator).get(cursor).size();
					single_neuron = new LinkedList<Double>();
					
					for(int picker = 0; picker < len_weights; picker++)
					{
						changed_data 	= unchanged.getPer_HLayer().get(iterator).get(cursor).get(picker) 
										+ changing_values.getPer_HLayer().get(iterator).get(cursor).get(picker);
						
						single_neuron.add(changed_data);
					}
					single_layer.add(single_neuron);
				}
				multiple_layer.add(single_layer);
			}
			
		}
		
		//Set new content
		changed.setPer_HLayer(multiple_layer);
		
		//***************************************************************************************************************************

		//Change last H -> O
		
		len_neuron = unchanged.getHidden_to_output().size();
		single_layer = new LinkedList<LinkedList<Double>>();
		
		for(int cursor = 0; cursor < len_neuron; cursor++)
		{
			len_weights = unchanged.getHidden_to_output().get(cursor).size();
			single_neuron = new LinkedList<Double>();
			
			for(int picker = 0; picker < len_weights; picker++)
			{
				changed_data 	= unchanged.getHidden_to_output().get(cursor).get(picker) 
								+ changing_values.getHidden_to_output().get(cursor).get(picker);
		
				single_neuron.add(changed_data);
			}
			single_layer.add(single_neuron);
		}
		
		//Set new content
		changed.setHidden_to_output(single_layer);
		
		return changed;
	}
	
	//###########################################################################################################################
	
	//[WORKS]
	/**
	 * Show all actual stored values of all Lists
	 */
	public void showContent()
	{
		int steps = getPer_HLayer().size();
		int contentpartsize;
		
		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		
		System.out.println("In -> Hd Content");
		System.out.println("IN-Contentsize: "+getIn_to_hidden().size());
		System.out.println("In -> Hd Rands: "+getIn_to_hidden());
		System.out.println(" ");
		
		//==============================================================================================
		
		System.out.println("Hd-Layers Content");
		System.out.println("Steps: "+steps);
		
		for(int k = 0; k <= steps-1; k++)
		{
			contentpartsize = getPer_HLayer().get(k).size();
			System.out.println("HD-Contentsize: "+contentpartsize);
			System.out.println("HD Rands: "+getPer_HLayer().get(k));
			
		}
		System.out.println(" ");
		
		//==============================================================================================
		
		System.out.println("Hd -> Out Content");
		System.out.println("OUT-Contentsize: "+getHidden_to_output().size());
		System.out.println("Hd -> Out Rands: "+getHidden_to_output());
		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		System.out.println(" ");
		
	}
	
	//###########################################################################################################################
	
	//[WORKS]
	/**
	 * Merge Weigths from Input to Hidden to all Hidden
	 * @param w_i
	 * @param w_h
	 * @return LinkedList<LinkedList<LinkedList<Double>>> containing expression content 
	 */
	public LinkedList<LinkedList<LinkedList<Double>>> merge_I_H(LinkedList<LinkedList<Double>> w_i, LinkedList<LinkedList<LinkedList<Double>>> w_h)
	{
		LinkedList<LinkedList<LinkedList<Double>>> out_list = new LinkedList<LinkedList<LinkedList<Double>>>();
		int size = w_h.size();
		
		out_list.add(w_i);
		
		for(int s = 0; s < size; s++)
		{
			out_list.add(w_h.get(s));
		}
		
		return out_list;
	}
	
	//###########################################################################################################################
	
	//[WORKS]
	/**
	 * This instance merge all weights from 1st hiddenlayer to the outputlayer
	 * @param w_o
	 * @param w_h
	 * @return LinkedList<LinkedList<LinkedList<Double>>> containing expression content
	 */
	public LinkedList<LinkedList<LinkedList<Double>>> merge_H_O(LinkedList<LinkedList<Double>> w_o, LinkedList<LinkedList<LinkedList<Double>>> w_h)
	{
		LinkedList<LinkedList<LinkedList<Double>>> out_list = new LinkedList<LinkedList<LinkedList<Double>>>();
		int size = w_h.size();
		
		
		for(int s = 0; s < size; s++)
		{
			out_list.add(w_h.get(s));
		}
		
		out_list.add(w_o);

		return out_list;
	}
	
	//###########################################################################################################################
	
	
	/*
	 * EXAMPLE [WORKS]
	 */
	public static void main(String[] args)
	{
		double max = 10;
		double min = -10;
		int input_count = 5;
		int size_Hidden = 3;
		int output_count = 1;
		LinkedList<Integer> layerlist_Hidden_Count = new LinkedList<Integer>();		
		
		for(int k = 0; k < size_Hidden ; k++)
		{
			if(k == 0)
			{
				layerlist_Hidden_Count.add(4);
			}else{
				layerlist_Hidden_Count.add(k);
			}
		}

		LayerTransferWeightObj ltw1 = new LayerTransferWeightObj(input_count, layerlist_Hidden_Count, output_count, max, min);
		LayerTransferWeightObj ltw2 = new LayerTransferWeightObj(input_count, layerlist_Hidden_Count, output_count, max, min);
		ltw1.showContent();
		ltw2.showContent();
		
		System.out.println("");
		System.out.println("<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>");
		System.out.println("");

		LayerTransferWeightObj changed = ltw1.changeToUpdated(ltw1, ltw2);
		
		changed.showContent();
		
	}
	
}
