//import java.math.BigDecimal;

//[THIS CLASS WORKS COMPLETELY]
/**
 * 
 * @author Tobias Turke
 *
 */
public class ActivityFunctions 
{
	//Enthaelt alle Namen aller hier existenten Funktionen
	private String names_of_all_functions = "Tangens, Sigmoid, Quadratisch, Identitaet";
	private String kuerzel = "tan, sig, quad, id";
	
	
	/*
	 * Hinweis das Adden einer neuen Funktion bedarf 3 Schritte
	 * 1. Name in "names_of_all_functions" und "kuerzel" eintragen
	 * 2. Funktion und ihr Derivat anlegen
	 * 3. Im Konstruktor Funktionsabfrage realisieren wie die uebrigen Funktionen
	 */
	
	public double result = 0.0; 
	public double derivate = 0.0;
	
	/**
	 * Leerer Konstruktor fuer abfrage der existenten Funktionen only!!!! 
	 */
	public ActivityFunctions(){}
	
	/**
	 * Der Konstruktor erstellt bei Eingabe eines Funktionsnamens und eines Inputs ein Resultat 
	 * welche in der Klassenvariable result hinterlegt wird.
	 * @param func_name
	 * @param solving_input
	 */
	public ActivityFunctions(String func_name, double solving_input)
	{
		if(kuerzel.contains(func_name))
		{
			//Berechne Tangens Hyperbolicus Aktivierungsfunktion
			if(func_name.equals("Tan") || func_name.equals("tan"))
			{
				this.result = tanhyperbolic(solving_input);
				this.derivate = tanhyperbolic_derivate(solving_input);
			}
			
			//Berechne Sigmoide/logistische Aktivierungsfunktion
			if(func_name.equals("Sig") || func_name.equals("sig"))
			{
				this.result = sigmoid(solving_input);
				this.derivate = sigmoid_derivate(solving_input);
			}
			
			//Berechne quadratische Aktivierungsfunktion
			if(func_name.equals("Quad") || func_name.equals("quad"))
			{
				this.result = quadratic(solving_input);
				this.derivate = quadratic_derivate(solving_input);
			}
			
			//Berechne indentitaets Aktivierungsfunktion
			if(func_name.equals("Id") || func_name.equals("id"))
			{
				this.result = identity(solving_input);
				this.derivate = identity_derivate(solving_input);
			}
		}else{
			System.err.println("Eine angegebene Aktivierungsfunktion ist nicht enthalten!");
			System.exit(0);
		}
		
				
	}
	
	//=====================================================================================================================
	
	/**
	 * Der Tangens Hyperbilocus
	 * Ergebnisintervall (-1;1)
	 * @param value
	 * @return Double
	 */
	public double tanhyperbolic(double value)
	{
		return Math.tanh(value);
	}
	
	/**
	 * Das Derivat des Tangens Hyperbilocus sprich Sekans Hyperbolicus
	 * @param value
	 * @return Double
	 */
	public double tanhyperbolic_derivate(double value)
	{
	
		//double tmp1 = Math.exp((-1)*value);					//exp^(-1*value)
		//double tmp2 = Math.exp(value);						//exp^(value)
		//double tmp3 = (2/(tmp2 + tmp1))*(2/(tmp2 + tmp1)); 	//sech²(value) = (exp^(-1*value)*exp^(value))²

		//Aequivalent dazu aber da wir auf sehr langen Nachkommastellen
		//arbeiten etwas genauer als die obere ab der 9-stelle
		double tmp3 = (1+Math.tanh(value))*(1-Math.tanh(value));
		
		return tmp3;
	}
	
	//=====================================================================================================================
	
	/**
	 * Die Sigmoide Funktion
	 * Ergebnisintervall (0;1)
	 * @param value
	 * @return Double
	 */
	public double sigmoid(double value)
	{
		//BigDecimal var = new BigDecimal(result);
		//result = var.setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue();
		return (1/(1+Math.exp((-1)*value)));
	}
	
	/**
	 *  Das Derivat der sigmoiden Funktion
	 * @param value
	 * @return Double
	 */
	public double sigmoid_derivate(double value)
	{
		//BigDecimal var = new BigDecimal(result);
		//result = var.setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue();
		
		double tmp = (1/(1+Math.exp((-1)*value)));
		return (tmp*(1-tmp));	
	}
	
	//=====================================================================================================================
	
	/**
	 * Die Parabelfunktion
	 * @param value
	 * @return Double
	 */
	public double quadratic(double value)
	{
		return (value*value);
	}
	
	/**
	 * Das Derivat der Parabelfunktion
	 * @param value
	 * @return
	 */
	public double quadratic_derivate(double value)
	{
		return 2*value;
	}
	
	//=====================================================================================================================
	
	/**
	 * Die Identitaetsfunktion
	 * @param value
	 * @return
	 */
	public double identity(double value)
	{
		return value;
	}
	
	/**
	 * Das Derivat der Identitaetsfunktion
	 * @param value
	 * @return
	 */
	public double identity_derivate(double value)
	{
		return 1.0;
	}
	
	//=====================================================================================================================
	
	/**
	 * Gibt String mit allen Funktionsnamen zurueck welche implementiert wurden
	 * @return String
	 */
	public String getExistentFunctions()
	{
		return this.kuerzel;
	}
	
	public String getNames_of_all_functions() 
	{
		return this.names_of_all_functions;
	}
	
	//=====================================================================================================================

	/*
	 * Testingground
	 */
	public static void main(String[] args)
	{
		String f_name_2 = "Tan";
		String f_name_3 = "Sig";
		
		double input = -4.01;
		
		ActivityFunctions f_obj_2 = new ActivityFunctions(f_name_2, input);
		ActivityFunctions f_obj_3 = new ActivityFunctions(f_name_3, input);
	
		System.out.println("Eigaben: [Funktion 1: "+f_name_2+"] [Input 1: "+input+"] => [Ergebnis: "+f_obj_2.result+"]");
		System.out.println("Eigaben: [Funktion 1: "+f_name_2+"_derivate] [Input 1: "+input+"] => [Ergebnis: "+f_obj_2.derivate+"]");
		System.out.println(" ");
		System.out.println("Eigaben: [Funktion 1: "+f_name_3+"] [Input 1: "+input+"] => [Ergebnis: "+f_obj_3.result+"]");
		System.out.println("Eigaben: [Funktion 1: "+f_name_3+"_derivate] [Input 1: "+input+"] => [Ergebnis: "+f_obj_3.derivate+"]");
	}
}
