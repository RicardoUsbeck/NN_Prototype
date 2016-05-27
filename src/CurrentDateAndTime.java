import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CurrentDateAndTime 
{
	public String current_Date_Time()
	{
		String timestamp = "";
		//DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		DateFormat dateFormat = new SimpleDateFormat("(yyyy.MM.dd) (HH-mm-ss)");
		Date date = new Date();
		timestamp = dateFormat.format(date);
		
		return timestamp;
	}
}
