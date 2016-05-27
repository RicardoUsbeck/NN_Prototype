import java.util.LinkedList;


//[THIS CLASS WORKS COMPLETELY]
/**
 * 
 * @author Tobias Turke
 *
 */
public class ErrorFunctionCalculation 
{
		private double solved = Double.NaN;
		private boolean measure_called = false;
		private String name_of_measure_funcs = "SumSq, Absolute";
		
		/*
		 * Hinweis das Adden einer neuen Funktion bedarf 3 Schritte
		 * 1. Name in "name_of_measure_funcs" eintragen
		 * 2. Funktion anlegen
		 * 3. Im Konstruktor Funktionsabfrage realisieren wie die uebrigen Funktionen
		 */
	
		/**
		 * Konstruktor für die Vektorerrorberechnung
		 */
		public ErrorFunctionCalculation(){}
	
		/**
		 * Konstruktor für den Errormeasure 
		 * @param measure_typ
		 * @param pattern_vector_errs
		 * @param targets
		 */
		public ErrorFunctionCalculation(String measure_typ, LinkedList<LinkedList<Double>> pattern_vector_errs, LinkedList<Double> targets)
		{
			if(name_of_measure_funcs.contains(measure_typ))
			{
				if(measure_typ.equals("SumSq") || measure_typ.equals("sumsq"))
				{
					this.solved = error_Measure_Calc_SumSq(pattern_vector_errs, targets);
					this.measure_called = true;
				}
				
				if(measure_typ.equals("Absolute") || measure_typ.equals("absolute"))
				{
					this.solved = error_Measure_Calc_Absolute(pattern_vector_errs, targets);
					this.measure_called = true;
				}
			}
		}
		
		//#####################################################################################
	
		/**
		 * Die Errorfunktion zum ermitteln der Abweichung zum Zielwert
		 * @param output_results_FF
		 * @return double
		 */
		public double errFuction(double output_results_FF, double goal_value)
		{ 
			//Error des aktuellen Outputneurons [E = 1/2 * (t - o)²]
			return (0.5 * (goal_value - output_results_FF) * (goal_value - output_results_FF));
		}
		
		//#####################################################################################

		/**
		 * Dataset error measure calculation sumsq
		 * @param pattern_vector_errs
		 * @param targets
		 * @return SumSq error als double
		 */
		public double error_Measure_Calc_SumSq(LinkedList<LinkedList<Double>> pattern_vector_errs, LinkedList<Double> targets)
		{
			LinkedList<Double> cur_vektor_outlist = new LinkedList<Double>();
			double summsq_error = 0;
			double summ_vec_target_diffs = 0;
			double target_diff = 0;
			double cur_output = 0;
			
			
			if(pattern_vector_errs == null || pattern_vector_errs.isEmpty())
			{
				System.err.println("Eingabe 'Fehlervector' ist leer!");
				System.exit(0);
			}
			
			//Berechnung basiert auf [fast-2.2.manual-error_measures.pdf] SumSq Error Measure
			for(int vit_sq = 0; vit_sq < pattern_vector_errs.size(); vit_sq++)
			{
				cur_vektor_outlist = pattern_vector_errs.get(vit_sq);
				
				for(int sub_it_sq = 0; sub_it_sq < cur_vektor_outlist.size(); sub_it_sq++)
				{
					cur_output = cur_vektor_outlist.get(sub_it_sq);
					target_diff = targets.get(vit_sq) - cur_output;	
					summ_vec_target_diffs = summ_vec_target_diffs + (target_diff * target_diff);
				}
				summsq_error = summsq_error + (summ_vec_target_diffs);
			}
			return summsq_error;
		}
		
		//#####################################################################################
		
		/**
		 * Dataset error measure calculation absolute
		 * @param pattern_vector_errs
		 * @param targets
		 * @return Absolute error als double
		 */
		public double error_Measure_Calc_Absolute(LinkedList<LinkedList<Double>> pattern_vector_errs, LinkedList<Double> targets)
		{
			LinkedList<Double> cur_vektor_outlist = new LinkedList<Double>();
			double absolute_error = 0;
			double dataset_target_diffs = 0;
			double summ_vec_target_diffs = 0;
			double target_diff = 0;
			double cur_output = 0;
			
			if(pattern_vector_errs == null || pattern_vector_errs.isEmpty())
			{
				System.out.println("Eingabe 'Fehlermatrix' ist leer!");
				System.exit(0);
			}
					
			//Berechnung basiert auf [fast-2.2.manual-error_measures.pdf] Absolute Error Measure
			for(int vit_abs = 0; vit_abs < pattern_vector_errs.size(); vit_abs++)
			{
				cur_vektor_outlist = pattern_vector_errs.get(vit_abs);

				for(int sub_it_abs = 0; sub_it_abs < cur_vektor_outlist.size(); sub_it_abs++)
				{
					cur_output = cur_vektor_outlist.get(sub_it_abs);
					target_diff = targets.get(vit_abs) - cur_output;
					summ_vec_target_diffs = summ_vec_target_diffs + Math.abs(target_diff);
				}
				dataset_target_diffs = dataset_target_diffs + summ_vec_target_diffs;
			}
			absolute_error = (dataset_target_diffs / (pattern_vector_errs.size() * cur_vektor_outlist.size()));
			return absolute_error;
		}

		//#####################################################################################
		
		public double getSolved() 
		{
			if(measure_called)
			{
				return solved;
			}else{
				System.out.println("Kein Measure call daher kein Ergebnis!");
			}
			return Double.NaN;
		}

		public boolean isMeasure_called() {
			return measure_called;
		}			
}
