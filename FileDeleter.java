import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * @File FileDeleter.java
 * @author Jonathan Hepplewhite
 * @date 10 Dec 2016
 * @brief Removes an image and reference to it from file system
 * \n \n
 * FileDeleter is a class that contains a static method that removes an
 * image file and reference to a image file within the SavedDrawings.txt file
 */
public class FileDeleter 
{
	/**
	 * Static method that deletes an image and its reference
	 * @param fileName The image file to delete
	 */
	public static void deleteFile(String fileName)
	{
		//Make a reference to the image reference file
		File file = new File(REFERENCE_FILE_LOCATION);
		//The location and name of a temp file
		File tempFile = new File(TEMP_FILE_LOCATION);
		//Reference to the image file to be deleted
		File imageFile = new File(fileName + FILE_EXTENSION);
		//If the image file exists
		if(imageFile.exists())
		{
			//Delete the image file
			imageFile.delete();
		}
		//Create a Scanner to read the reference file
		Scanner in = null;
		try
		{
			//Initialize the Scanner with the reference file
			in = new Scanner(file);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		//Call the method to rewrite the reference file
		writeNewTempFile(in, tempFile, file, fileName);
	}
	
	/**
	 * Method that rewrites the reference file. Removing the required reference
	 * @param in The Scanner reading the reference file
	 * @param tempFile The temp file to write
	 * @param file The file to be replaced
	 * @param fileName The name of the image file
	 */
	private static void writeNewTempFile(Scanner in, File tempFile, File file, String fileName)
	{
		//Create a PrintWriter to write the temp file
		PrintWriter writer = null;
		try
		{
			//Initilise the PrintWriter, ready to save the output file
			writer = new PrintWriter(new FileOutputStream(tempFile, true));
			//While there are lines to read in the origninal reference file
			while(in.hasNextLine() == true)
			{
				//String that contains the next line
				String line = in.nextLine();
				//Initialise a new Scanner to read the line
				Scanner lineRead = new Scanner(line);
				//Set the delimiter
				lineRead.useDelimiter(SCANNER_DELIMITER);
				
				//If the next token in the line is the NOT filename
				if(!lineRead.next().equals(fileName))
				{
					//Write the line to the new file
					writer.append(line);
					writer.append(NEW_LINE);
				}
				//Release Scanner resources
				lineRead.close();
			}
		}
		catch(FileNotFoundException e)
		{
			System.out.println(FILE_NOT_FOUND);
		}
		finally
		{
			//Release writer resources
			writer.close();
		}
		//Overwrite the original file with the new reference file
		tempFile.renameTo(file);
	}
	
	/** The location and name of the reference file */
	private static final String REFERENCE_FILE_LOCATION = 
										"SavedDrawings.txt";
	/** The location and name of the temp reference file */
	private static final String TEMP_FILE_LOCATION = 
										"/myTempFile.txt";
	/** The location reference and image files */
	/** The image file extension */
	private static final String FILE_EXTENSION = ".png";
	/** Error message for if the file is not found */
	private static final String FILE_NOT_FOUND = "File Not Found";
	/** Scanner delimiter */
	private static final String SCANNER_DELIMITER = ",";
	/** New line character */
	private static final String NEW_LINE = "\n";
}
