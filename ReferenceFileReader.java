import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @File FileNameReader.java
 * @author Jonathan Hepplewhite
 * @date 8 Dec 2016
 * @brief ReferenceFileReader class that reads the reference file
 * \n\n
 * ReferenceFileReader class that reads the reference file and retrieves
 * information required by other classes
 */
public class ReferenceFileReader 
{
	/**
	 * @return The name of the file the user is working on
	 */
	public static String getFileName()
	{
		return m_FileName;
	}
	
	/**
	 * @return The current user's name
	 */
	public static String getUserName()
	{
		return m_UserName;
	}
	
	/**
	 * @return The array of users that can access an image file
	 */
	public static String[] getUsers()
	{
		return m_UserList;
	}
	
	/**
	 * Set the list of users who can access an image file 
	 * @param users The list of users
	 */
	public static void setUsers(String[] users)
	{
		m_UserList = users;
	}
	
	/**
	 * Set the working file name
	 * @param name The name to set to 
	 */
	public static void setFileName(String name)
	{
		m_FileName = name;
	}
	
	/**
	 * Set the current name of the user
	 * @param name The name of the user
	 */
	public static void setUserName(String name)
	{
		m_UserName = name;
	}
	
	/**
	 * Get the list of file name
	 * @return An Arraylist containing the list of image file names
	 */
	public static ArrayList<String> getFileNames()
	{
		//Create a new string ArrayList that will be populated and returned
		ArrayList<String> fileNames = new ArrayList<String>();
		//Initialise a File object that references the reference file
		File file = new File(FILE_LOCATION);
		try 
		{
			//If the file does not exist
			if(!file.exists())
			{
				//Create the file
				file.createNewFile();
				FileOutputStream oFile = new FileOutputStream(file, false);
			}
		} 
		catch (IOException e1) 
		{
			e1.printStackTrace();
		}
		//Create a Scanner
		Scanner in = null;
		try 
		{
			//Initialise the Scanner with the reference file
			in = new Scanner(file);
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		//Set the Scanner delimiter
		in.useDelimiter(SCANNER_DELIMITER);
		//While there are line to be read by the Scanner
		while(in.hasNextLine() == true)
		{
			//Read the first token (the file name) and add it to the ArrayList
			fileNames.add(in.next());
			//Move to the next line
			in.nextLine();
		}
		//Return the ArrayList
		return fileNames;
	}
	
	/**
	 * Method that checks if the current user can access the image file
	 * @param user The current user
	 * @param fileName The name of the file they want to access
	 * @return True if the user can access the file, false otherwise
	 */
	public static Boolean checkUserValid(String user, String fileName)
	{
		//Initialise a File object that references the reference file
		File file = new File(FILE_LOCATION);
		//Create a new Scanner
		Scanner in = null;
		try 
		{
			//Initialise the Scanner with the reference file
			in = new Scanner(file);
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		//Set the Scanner delimiter
		in.useDelimiter(SCANNER_DELIMITER);
		//While the Scanner has lines to read
		while(in.hasNextLine() == true)
		{
			//Set the line to a string
			String line = in.nextLine();	
			//Create and initialise a new Scanner with reference to reference file
			Scanner lineRead = new Scanner(line);
			//Set Scanner delimiter
			lineRead.useDelimiter(SCANNER_DELIMITER);
			//If the next token is the filename
			if(lineRead.next().equals(fileName))
			{
				//Read through the line
				while(lineRead.hasNext())
				{
					//If the user is in the line
					if(lineRead.next().equals(user))
					{
						//Release Scanner resources
						lineRead.close();
						//Return true as the user has access to the image
						return true;
					}
				}
			}
			//Release Scanner resources
			lineRead.close();
		}
		//The user does not have access
		return false;
	}
	
	/**
	 * Write to the reference file with the new image file
	 */
	public static void createFile()
	{
		//If the the file name is not used
		if(!getFileNames().contains(m_FileName))
		{
			//Create a reference to the image reference file
			File file = new File(FILE_LOCATION);
			//Create a PrintWriter
			PrintWriter writer = null;
			try
			{
				//Initilise the PrintWriter, ready to save the output file 
				writer = new PrintWriter(new FileOutputStream(file, true));
				//Write the necessary info to the file 
				writer.append(m_FileName + SCANNER_DELIMITER);
				writer.append(m_UserName);
				//For each user in the list
				for(int i = 0; i < m_UserList.length; i++)
				{
					//Add each user to the file
					writer.append(SCANNER_DELIMITER + m_UserList[i]);
				}
				//End each line with a new line character
				writer.append(NEW_LINE);
			}
			catch (FileNotFoundException e)
			{
				System.out.println(FILE_NOT_FOUND);
			}
			finally
			{
				//Release PrintWriter resources
				writer.close();
			}
		}
	}
	
	/** The name and location of the image reference file */
	private static final String FILE_LOCATION = 
								"SavedDrawings.txt";
	/** Scanner delimiter */
	private static final String SCANNER_DELIMITER = ",";
	/** New line character */
	private static final String NEW_LINE = "\n";
	/** File not found error message */
	private static final String FILE_NOT_FOUND = "File Not Found";
	/** Array of users that can access each file */
	private static String[] m_UserList;
	/** The file name */
	private static String m_FileName;
	/** The current users name */
	private static String m_UserName;
}