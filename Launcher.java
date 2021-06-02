import java.util.ArrayList;
import java.util.Arrays;

public class Launcher 
{
	public static void main(String[] args) 
	{
		String user = "Bob";
		ArrayList<String> contactList = new ArrayList<String>(Arrays.asList("Bob", "Bill", "Frank", "Fred", "Jimmy"));
		new DrawingEnvironmentLauncher(user, contactList);
	}
}
