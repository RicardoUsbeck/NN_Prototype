import java.util.LinkedList;

//[THIS CLASS WORKS COMPLETELY]
/**
 * 
 * @author Tobias Turke
 *
 */
public class TheNet 
{	
	//1.	Inputneuronenliste
	private LinkedList<Neuron> input_nr = new LinkedList<Neuron>();
	
	//2.	Hiddenlayer-Neuronenliste
	private LinkedList<LinkedList<Neuron>> hidden_nr = new LinkedList<LinkedList<Neuron>>();
	
	//3.	Outputneuronenliste
	private LinkedList<Neuron> output_nr = new LinkedList<Neuron>();
	
	//4.	Der Zielwert
	private double goal_value =  Double.NaN;
	
	//5.	Error-vergleichswert
	private double err_value = Double.NaN;
	
	//6.	Gradient
	private LayerTransferWeightObj ltwo;
	
	//###################################################################################################################################
	
	/**
	 * Test
	 */
	TheNet(){}
	
	//[Works]
	/**
	 * Programmkonstruktor
	 * @param in_values
	 * @param in_funk
	 * @param anz_HNeuronen_pro_Lay
	 * @param funk_pro_HLayer
	 * @param anz_ONeuronen
	 * @param out_funk
	 */
	TheNet(	LinkedList<Double> in_values, String in_funk, LinkedList<Integer> anz_HNeuronen_pro_Lay, LinkedList<String> funk_pro_HLayer,
			int anz_ONeuronen, String out_funk, double goal_value, int input_count, LayerTransferWeightObj lto)
	{
		
		//=================================Test der Eingaben ob sie okay sind====================================================
		//*********************************************************************************************************************//
		//=======================================================================================================================
		ActivityFunctions af = new ActivityFunctions();
		Boolean existenz_test = true;
		
		//Existiert die Inputlayerfunktion
		if(in_funk.isEmpty() || !(af.getExistentFunctions().contains(in_funk)))
		{
			System.err.println("Die eingegebenen Inputschicht-Funktion existiert nicht!");
			System.exit(0);
		}
		
		//Existiert die Outputlayerfunktion
		if(out_funk.isEmpty() || !(af.getExistentFunctions().contains(out_funk)))
		{
			System.err.println("Die eingegebenen Outputschicht-Funktion existiert nicht!");
			System.exit(0);
		}
		
		//Existieren alle Hiddenlayerfunktionen
		if(funk_pro_HLayer.size() < 1 && funk_pro_HLayer.size() == anz_HNeuronen_pro_Lay.size())
		{
			System.err.println("Nicht genuegend Hiddenlayer-Funktionen eingegeben!");
			System.exit(0);
		}else{
		
			for(int z = 0; z < funk_pro_HLayer.size(); z++)
			{
				
				if(funk_pro_HLayer.get(z).isEmpty() || !(af.getExistentFunctions().contains(funk_pro_HLayer.get(z))))
				{
					existenz_test = false;
				}	
			}	
			
			if(!existenz_test)
			{
				System.err.println("Eine der eingegebenen Hiddenschicht-Funktionen existiert nicht!");
				System.exit(0);
			}
		}
		
		//Existenz min. 1 Eingabewert
		if(input_count != in_values.size())
		{
			System.err.println("Vergleichswert der Dataset input größe ist falsch gesetzt!");
			System.exit(0);
		}
		
		//Existenz min. 1 Eingabewert
		if(in_values.size() < 1)
		{
			System.err.println("Nicht genuegend Eingabewerte eingegeben!");
			System.exit(0);
		}
		
		//Existenz von Neuronencounter fuer min. 1 Layer
		if(anz_HNeuronen_pro_Lay.size() < 1 && funk_pro_HLayer.size() == anz_HNeuronen_pro_Lay.size())
		{
			System.err.println("Nicht genuegend Neuronencounter pro Hiddenlayerschicht eingegeben!");
			System.exit(0);
		}
		
		//Existenz min. 1 Outputneuron
		if(anz_ONeuronen < 1)
		{
			System.err.println("Anzahl der Outputneuronen zu gering!");
			System.exit(0);
		}
			
		this.goal_value = goal_value;
		this.ltwo = lto;
				
	}
	
	//###################################################################################################################################
	//=====================================================Layers============================================================

	//[Works]
	/**
	 * Erstellen des Inputlayers gibt Layer als Liste Neuronen zurueck
	 * @param in_values
	 * @param in_funk
	 * @return list of Neurons (input layer)
	 */
	public LinkedList<Neuron> createInputLayer(LinkedList<Double> in_values, String in_funk)
	{
		LinkedList<Neuron> in_neuron = new LinkedList<Neuron>();
		LinkedList<Double> hd_inputs = new LinkedList<Double>();
		
		for(int z = 0; z < in_values.size(); z++)
		{
			Neuron neuron = new Neuron(in_values.get(z), in_funk);
			in_neuron.add(neuron);			
			hd_inputs.add(neuron.getSolved_In_FF());
		}
		return in_neuron;
	}
	
	//###################################################################################################################################
	
	//[Works]
	/**
	 * Erstelle einen Funktionsabschnitt des Hiddenlayer
	 * @param anz_neuronen
	 * @param func
	 * @param inputs
	 * @param randoms
	 * @return create Neuron list (1 part of the hidden layert)
	 */
	public LinkedList<Neuron> createOneHLayerPart(int anz_neuronen, String func, LinkedList<Double> inputs, LinkedList<LinkedList<Double>> randoms)
	{
		LinkedList<Neuron> neuron_list = new LinkedList<Neuron>();
		Neuron nr = null;
		
		
		if(randoms.size() < 0 || anz_neuronen < 0 || inputs.size() > randoms.size() || inputs.size() < 0)
		{
			System.out.println("Ungueltige Startbedingungen! => [Rands = "+randoms.size()+" || Neuronen = "+anz_neuronen+" || Inputs = "+inputs.size()+"]");
			System.exit(0);
		}
	
		for(int y = 0; y < anz_neuronen; y++)
		{
			if(randoms.size() != inputs.size())
			{
				System.out.println("rnd-size: "+randoms.size()+" | in-size: "+inputs.size());
				
				System.err.println("UNGUELTIGE SITUATION!");
				
			}else{
				
				LinkedList<Double> input_list = new LinkedList<Double>();
				LinkedList<Double> weight_list = new LinkedList<Double>();
				
				for(int k = 0; k < inputs.size(); k++)
				{
					input_list.add(inputs.get(k));
					weight_list.add(randoms.get(k).get(y));
				}
				nr = new Neuron(input_list, weight_list, func);
				
			}
			neuron_list.add(nr);
		}

		return neuron_list;
	}
	
	//###################################################################################################################################
	
	public void showContentNet()
	{
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		
		//Input
		System.out.println("Input-Neuron-Content:");
		
		for(int in_sh = 0; in_sh < input_nr.size(); in_sh++)
		{
			Neuron cur_nr = input_nr.get(in_sh);
			System.out.println("[I-Neuron: "+(in_sh+1)+" => Content: [Input = "+cur_nr.getInput_IL()+" | Activation-Out = "+cur_nr.getSolved_In_FF()+" | Derivate-Out = "+cur_nr.getSolved_In_BP()+"]]");
		}

		
		//Hidden 
		System.out.println("");
		System.out.println("Hidden-Neuron-Content:");
		
		for(int hd_ly = 0; hd_ly < hidden_nr.size(); hd_ly++)
		{
			System.out.println("Layer-Nr.: "+(hd_ly+1));
			
			for(int hd_sh = 0; hd_sh < hidden_nr.get(hd_ly).size(); hd_sh++)
			{
				Neuron cur_nr = hidden_nr.get(hd_ly).get(hd_sh);
				System.out.println("[H-Neuron: "+(hd_sh+1)+" => Content: [Input = "+cur_nr.getInput_HL()+" | Activation-Out = "+cur_nr.getSolved_Hd_FF()+" | Derivate-Out = "+cur_nr.getSolved_Hd_BP()+"]]");
			}
		}
		
		//Output
		System.out.println("");
		System.out.println("Output-Neuron-Content:");
		
		for(int out_sh = 0; out_sh < output_nr.size(); out_sh++)
		{
			Neuron cur_nr = output_nr.get(out_sh);
			System.out.println("[O-Neuron: "+(out_sh+1)+" => Content: [Input = "+cur_nr.getInput_OL()+" | Activation-Out = "+cur_nr.getSolved_Out_FF()+" | Derivate-Out = "+cur_nr.getSolved_Out_BP()+"]]");
		}
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	}
	
	//###################################################################################################################################
	
	//[Works]
	/**
	 * Dient dem erstellen eines Deep Hiddenlayer
	 * @param depth
	 * @param start_inputs
	 * @param anz_neuronen
	 * @param functions
	 * @param randoms
	 * @return List of Layers containing Neurons 
	 */
	public LinkedList<LinkedList<Neuron>> createCompleteHlayer(	int depth, LinkedList<Double> start_inputs, 
																LinkedList<Integer> anz_neuronen, LinkedList<String> functions,
																LinkedList<LinkedList<LinkedList<Double>>> randoms)
	{
		//Ausgabeliste
		LinkedList<LinkedList<Neuron>> Hiddenlayer_Deep = new LinkedList<LinkedList<Neuron>>();
		
		if(depth == 0)
		{
			System.out.println("Falscher Tiefenwert fuer Hiddenlayer!!!");
			System.exit(0);
		}
		
		
		//Erster Layerpart
		LinkedList<Neuron> first_part = new LinkedList<Neuron>();
		
		first_part = createOneHLayerPart(anz_neuronen.get(0), functions.get(0), start_inputs, randoms.get(0));	//Erstelle 1 Part
		Hiddenlayer_Deep.add(first_part);	//Schreibe 1 Part in ausgabe Liste
		LinkedList<Double> input = inputCreatorSonderfall(first_part, "Input"); //Erstelle die ersten Inputs
		LinkedList<Double> inputs = null;
		LinkedList<Neuron> next_part;
		
		//Weitere Layer
		for(int x = 1; x < depth; x++)
		{	 		
			if(x == 1) //Weil der erste Input vom Inputlayer kommt
			{
				next_part = createOneHLayerPart(anz_neuronen.get(x), functions.get(x), input, randoms.get(x)); //Erstelle 1st Part				
				Hiddenlayer_Deep.add(next_part);	//Schreibe 1st Part in ausgabe Liste
			}else
			
			{
				next_part = createOneHLayerPart(anz_neuronen.get(x), functions.get(x), inputs, randoms.get(x)); //Erstelle next Part
				Hiddenlayer_Deep.add(next_part);	//Schreibe next Part in ausgabe Liste
			}
			
			inputs = inputCreator(next_part, "Hidden"); //Erstelle naechsten Input aus den letzten Neuronen
		}
		
		return Hiddenlayer_Deep;	//Rueckgabe Hiddenlayer
	}
	
	//###################################################################################################################################
	
	//[Works]
	/**
	 * Erstellen des Outputlayers gibt den Endwert zurueck.
	 * Aber nicht die Fehlerfunktion!
	 * @param anz_ONeuronen
	 * @param out_funk
	 * @return LinkedList<Neuron>
	 */
	public LinkedList<Neuron> createOutputLayer(LinkedList<LinkedList<Double>> randoms, int anz_ONeuronen, String out_funk, LinkedList<Double> inputs)
	{
		LinkedList<Neuron> neurons = new LinkedList<Neuron>();
		LinkedList<Double> tmps;
		Neuron nr = null;
				
		for(int io = 0; io < anz_ONeuronen; io++)
		{	
			if(randoms.size() < 1)
			{ 
				System.err.println("Zu wenig Randoms"); 
				System.exit(0);
			}else if(randoms.size() != inputs.size())
			{ 
				System.err.println("Anzahl Inputs und Anzahl Weiglisten stimmt nicht überein"); 
				System.exit(0);
			} else 
			{
					tmps = new LinkedList<Double>();
					//System.out.println("Rands: "+randoms);
					
					for(int c = 0;c < randoms.size(); c++)
					{
						if(anz_ONeuronen > 1 || inputs.size() > 1)
						{
							if(inputs.size() == randoms.size())
							{
								tmps.add(randoms.get(c).get(io));
							}else{
								System.err.println("ERROR: Verhältnis Inputs zu Gewichten passt nicht! [MULTI]");
								System.exit(0);
							}
						}else{
							
							if(inputs.size() == randoms.get(io).size())
							{
								tmps = randoms.get(io);
							}else{
								System.err.println("ERROR: Verhältnis Inputs zu Gewichten passt nicht! [SINGLE]");
								System.exit(0);
							}
						}
					}
					nr = new Neuron(out_funk, inputs, tmps);
			}
			neurons.add(nr);
		}
		return neurons;
	}
	
	
	//###################################################################################################################################
	//=================================================== Inputcreator ==================================================================
	
	//[Works]
	/**
	 * Ermittelt Feedforward inputs jedes Layers
	 * @param neuronen
	 * @param neuron_Typ
	 * @return LinkedList<Double>
	 */
	public LinkedList<Double> inputCreator(LinkedList<Neuron> neuronen, String neuron_Typ)
	{
		LinkedList<Double> output = new LinkedList<Double>();

		for(int it = 0; it < neuronen.size(); it++)
		{			
			if(neuron_Typ.equals("Input"))
			{
				output.add(neuronen.get(it).getSolved_In_FF());
			}
			
			if(neuron_Typ.equals("Hidden"))
			{
				output.add(neuronen.get(it).getSolved_Hd_FF());
			}

			if(neuron_Typ.equals("Output"))
			{
				output.add(neuronen.get(it).getSolved_Out_FF());
			}
		}
		
		if(output.isEmpty() || output == null)
		{
			System.out.println("Ouput ist Leer!!");
			System.exit(0);
		}
		
		return output;
	}
	
	//###################################################################################################################################
	
	//[Works]
	/**
	 * Ermittelt sonderfall Feedforward inputs des ersten teils des Hidden Layers
	 * @param neuronen
	 * @param neuron_Typ
	 * @return
	 */
	public LinkedList<Double> inputCreatorSonderfall(LinkedList<Neuron> neuronen, String neuron_Typ)
	{
		LinkedList<Double> output = new LinkedList<Double>();
		
		
		
		for(int it = 0; it < neuronen.size(); it++)
		{			
			if(neuron_Typ.equals("Input"))
			{
				output.add(neuronen.get(it).getSolved_Hd_FF());
			}
		}
		
		if(output.isEmpty() || output == null)
		{
			System.out.println("Ouput ist Leer!!");
			System.exit(0);
		}
		
		return output;
	}

	
	//###################################################################################################################################
	
	public LinkedList<Neuron> getInput_nr() {
		return input_nr;
	}
	

	public LinkedList<LinkedList<Neuron>> getHidden_nr() {
		return hidden_nr;
	}
	

	public LinkedList<Neuron> getOutput_nr() {
		return output_nr;
	}
	

	public Double getGoal_value() {
		return goal_value;
	}
	

	public Double getErr_value() {
		return err_value;
	}
	
	
	public LayerTransferWeightObj getLtwo() {
		return ltwo;
	}
	
	//###################################################################################################################################
	
	public void setInput_nr(LinkedList<Neuron> input_nr) {
		this.input_nr = input_nr;
	}

	
	public void setHidden_nr(LinkedList<LinkedList<Neuron>> hidden_nr) {
		this.hidden_nr = hidden_nr;
	}

	
	public void setOutput_nr(LinkedList<Neuron> output_nr) {
		this.output_nr = output_nr;
	}
	
	
	public void setLtwo(LayerTransferWeightObj ltwo) {
		this.ltwo = ltwo;
	}
	
	//###################################################################################################################################

	//[Works]
	/**
	 * Erstellen aller Layer und Durchfuehrung des Feedforward schrittes einmal.
	 * @param size_Hidden
	 * @param layerlist_Hidden_Count
	 * @param output_count
	 * @param in_funk
	 * @param in_values
	 * @param h_l_functions
	 * @param o_l_functions
	 */
	public void feedforward(	int size_Hidden, LinkedList<Integer> layerlist_Hidden_Count, int output_count,
								String in_funk, LinkedList<Double> in_values,
								LinkedList<String> h_l_functions,
								String o_l_functions)
	{
		LinkedList<LinkedList<Double>> w_i = new LinkedList<LinkedList<Double>>();
		LinkedList<LinkedList<LinkedList<Double>>> w_h = new LinkedList<LinkedList<LinkedList<Double>>>();
		LinkedList<LinkedList<LinkedList<Double>>> rands = new LinkedList<LinkedList<LinkedList<Double>>>();
		LinkedList<Neuron> last_layer = new LinkedList<Neuron>();
		
		
		//Weights_Object
		LayerTransferWeightObj ltwo = getLtwo();
		setLtwo(ltwo);
		
		//Inputlayer
		setInput_nr(createInputLayer(in_values, in_funk));
		
		
		//Hiddenlayer
		w_i = ltwo.getIn_to_hidden();
		w_h = ltwo.getPer_HLayer();
		rands = ltwo.merge_I_H(w_i, w_h);		
		
		
		for(int z = 0; z < getInput_nr().size(); z++)
		{
			last_layer.add(getInput_nr().get(z));
		}
				
		in_values = inputCreator(last_layer, "Input");
		setHidden_nr(createCompleteHlayer(size_Hidden, in_values, layerlist_Hidden_Count, h_l_functions, rands));
		
		
		//Outputlayer
		last_layer = hidden_nr.getLast();
		in_values = inputCreator(last_layer, "Hidden");
		setOutput_nr(createOutputLayer(ltwo.getHidden_to_output(), output_count, o_l_functions, in_values));		
	}
	
}
