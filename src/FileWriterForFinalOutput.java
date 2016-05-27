import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
//import java.util.List;

public class FileWriterForFinalOutput 
{

	public String directoryPath;
	
	public FileWriterForFinalOutput(String path)
	{
		directoryPath = path;
	}
	
//======================================================================================================================
		
	/**
	 * The method creates a new service File in the Source Folder with timestamp
	 * and return the full path of the new created file
	 * @author TTurke
	 * @param path
	 * @param counter
	 * @throws IOException
	 * @return String
	 */
	public String FileCreationTimestamp(String timestamp, String path) throws IOException
	{		
		String newFilePath = path+"ANN Output "+timestamp+" .txt";			
		File f = new File(newFilePath);
		f.getParentFile().mkdirs(); 
		f.createNewFile();
		
		return newFilePath;
	}
	
	/**
	 * The method creates a new service File in the Source Folder 
	 * and return the full path of the new created file
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public boolean FileCreation(String path) throws IOException
	{				
		File f = new File(path);
		f.getParentFile().mkdirs(); 
		f.createNewFile();
		return f.createNewFile();
	}
	
//======================================================================================================================
	
	/**
	 * This method write a Service Object to a new Service File in the Source Directory
	 * @author TTurke
	 * @param service
	 * @return
	 */
	public void WriteFile(LinkedList<String> input_content) throws IOException
	{
		String content = "";
		OutputStream os = new FileOutputStream(directoryPath);

		if(os != null )
		{
			OutputStreamWriter osr = new OutputStreamWriter(os);
			BufferedWriter bw = null;
			
			try
			{
				bw = new BufferedWriter(osr);
				for(int i=0; i < input_content.size(); i++)
				{	
					content = input_content.get(i);
					bw.write(content);
					bw.newLine();
				}
			
				content = "";
				bw.close();
				osr.close();
				os.close();
			
			}catch(IOException e){
				
				e.printStackTrace();
			}
			
			bw.close();
			osr.close();
		}
		os.close();
		
	}
	
//======================================================================================================================
	
	/*
	 * Example
	 */
	public static void main(String[] args) throws IOException 
	{
		System.out.println("Pfad pls \n");
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String input = in.readLine();
		
		String pfad = input;
		pfad = pfad.replace("\\", "/");
		
		System.out.println("Neuer Pfad: "+pfad);
		
		
		/*
		String path = "C:/Users/Subadmin/Desktop/";
		String file_path = "";
		FileWriterForFinalOutput fwffo = new FileWriterForFinalOutput(path);
		
		CurrentDateAndTime cdat = new CurrentDateAndTime();
		String timestamp = cdat.current_Date_Time();
		
		file_path = fwffo.FileCreation(timestamp, path);
		
		//Neuer Pfad wichtig
		fwffo = new FileWriterForFinalOutput(file_path);
		
		System.out.println("Path: "+file_path);

		LinkedList<String> yay = new LinkedList<String>();
		
		yay.add("This Program");
		yay.add("works");
		yay.add("definitly");
		yay.add("correct!");
		
	
		fwffo.WriteServiceFile(yay);
		*/
	}

}
