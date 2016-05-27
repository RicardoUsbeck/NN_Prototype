public class Rounding 
{
	private double output;
	
	/**
	 * Automatisiertes Runden der Grenzen
	 * @param min_or_max
	 * @param testing_value
	 * @param min_rounding
	 */
	Rounding(double min_or_max, double testing_value, boolean min_rounding)
	{
		this.output = changeBorderAutomatically(min_or_max, testing_value, min_rounding);
	}
	
	/**
	 * Prozentuale Grenzverschiebung mittels vom User angegebener Prozentzahl
	 * @param min_rounding
	 * @param min_or_max
	 * @param percentage
	 */
	Rounding(boolean down_rounding, double min_or_max, double percentage)
	{
		if(percentage == Double.NaN)
		{
			percentage = 0.0;
		}
		
		this.output = changeBorderByPercentage(min_or_max, percentage, down_rounding);
	}
	
	//##############################################################################################
	
	/**
	 * Berechnung der neuen Minima bzw. Maxima
	 * @param reference_value
	 * @param check_value
	 * @param down_rounding
	 * @return neue Minima oder Maxima
	 */
	public double changeBorderAutomatically(double reference_value, double check_value, boolean down_rounding)
	{
		int step, count = 0;
		double to_check = check_value;
		String[] split_x, split_y;
		
		split_x =  Double.toString(reference_value).split("\\.");
		split_y =  Double.toString(check_value).split("\\.");
		
		if(split_x[1].length() < split_y[1].length())
		{
			step = split_y[1].length();
		}else{
			step = split_x[1].length();
		}
		
		for(int i = 0; i < step; i++)
		{
			count++;
			reference_value *= 10; 
			to_check *= 10;	
			
			split_x =  Double.toString(reference_value).split("\\.");
			split_y =  Double.toString(to_check).split("\\.");
			
			//Maxima
			if(Double.parseDouble(split_y[0]) > Double.parseDouble(split_x[0]) && !down_rounding)
			{			
				reference_value = (Math.round((reference_value / 10.0)) + 1) *10;
				
				for(int max = 0; max < count; max++)
				{
					reference_value /= 10.0; 
				}
				
				if(reference_value < check_value)
				{
					System.err.println("Rounding Failed!");
					System.exit(0);
				}
				
				return reference_value;
			}

			//Minima
			if(Double.parseDouble(split_y[0]) < Double.parseDouble(split_x[0]) && down_rounding)
			{
				reference_value = (Math.round((reference_value / 10.0)) - 1) *10;
				
				for(int max = 0; max < count; max++)
				{
					reference_value /= 10.0;
				}
				
				if(reference_value > check_value)
				{
					System.err.println("Rounding Fail");
					System.exit(0);
				}

				return reference_value;
			}
		}
		return Double.NaN;
	}
	
	//##############################################################################################
	
	/**
	 * Berechnen der neuen Minima bzw. Maxima mittels eingegebener prozentualer Verschiebung nach innen oder außen
	 * @param value
	 * @param percentage
	 * @param down_rounding
	 * @return neue Minima oder Maxima
	 */
	public double changeBorderByPercentage(double value, double percentage, boolean down_rounding)
	{
		double percentage_value = percentage / 100.0;
		
		if(!down_rounding)
		{
			percentage_value = 1.0 + percentage_value;
		}
		
		if(down_rounding)
		{
			percentage_value = 1.0 - percentage_value;
		}
		
		return value * percentage_value;
	}
	
	//##############################################################################################
	
	/**
	 * Standard Getter
	 * @return neue Minima oder Maxima
	 */
	public double getOutput() {
		return output;
	}

	//##############################################################################################
	
	/*
	 * Example
	 */
	public static void main(String[] args) 
	{
		double x = 1.2375, y = 0.5, z = 1.2345;
		Rounding r;

		System.out.println("Automatisiert \n");
		
		r = new Rounding(z, x, false);
		System.out.println("Maxima Testing");
		System.out.println("Max Ref: \t"+z);
		System.out.println("New Max: \t"+r.getOutput());
		System.out.println("");
		System.out.println("Testing Ref: \t"+x);
		
		System.out.println("\n+++++++++++++++++++++++++++++++++ \n");
		
		r = new Rounding(x, z, true);
		System.out.println("Minima Testing");
		System.out.println("Min Ref: \t"+x);
		System.out.println("New Min: \t"+r.getOutput());
		System.out.println("");
		System.out.println("Testing Ref: \t"+z);
		
		System.out.println("\n+++++++++++++++++++++++++++++++++ \n");
		
		System.out.println("Prozentuale Änderung \n");
		
		r = new Rounding(false, z, y);
		System.out.println("Maxima Testing");
		System.out.println("Max Ref: \t"+z);
		System.out.println("New Max: \t"+r.getOutput());
		System.out.println("");
		System.out.println("Testing Ref: \t"+x);
		
		System.out.println("\n+++++++++++++++++++++++++++++++++ \n");
		
		r = new Rounding(true, x, y);
		System.out.println("Minima Testing");
		System.out.println("Min Ref: \t"+x);
		System.out.println("New Min: \t"+r.getOutput());
		System.out.println("");
		System.out.println("Testing Ref: \t"+z);
	}
}
