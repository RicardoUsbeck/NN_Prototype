import java.util.LinkedList;

//[THIS CLASS WORKS COMPLETELY]
/**
 * 
 * @author Tobias Turke
 *
 */
public class RandValueCreator 
{

		public RandValueCreator()
		{
			
		}
		
		//[Works]
		/**
		 * Randomweightcreator erstelle "set" viele Randomwerte
		 * @param set
		 * @param max
		 * @param min
		 * @return random weight list
		 */
		public LinkedList<Double> rnd_weights(int set, double max, double min)
		{
			if(set == 0)
			{
				System.err.println("Keine korrekte Anzahl an zu erstellenden Gewichten!");
				System.exit(0);
			}
			
			LinkedList<Double> rnds = new LinkedList<Double>();
			
			for(int k = 0; k < set; k++)
			{
				rnds.add(random_weight(max, min));
			}
			
			
			
			return rnds;
		}
		
		//=======================================================================================================================
		
		//[Works]
		/**
		 * Einfacher Zahlengenerator [min, max]
		 * @param min
		 * @param max
		 * @return Double
		 */
		public double random_weight(double min, double max)
		{
			double out;
			
			do
			{
				out = min + Math.random()* (max - min);
				
			}while(out < min || out > max);

			return out;
		}

	
		//=======================================================================================================================

		/*
		 * Test
		 */
		public static void main(String[] args)
		{
			RandValueCreator rvc = new RandValueCreator();

			double test;	
			
			test = rvc.random_weight(-10.23,10.31);
					
			System.out.println("Test: "+test);
			
		}
}
