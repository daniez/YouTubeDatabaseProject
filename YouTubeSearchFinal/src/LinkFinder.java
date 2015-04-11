import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class to search YouTube and save results in a file
 * @author Danielle Zoe Aloicius
 *
 */
public class LinkFinder {

	
	/**
	 * Does IO using Java URL class, saves links to videos to file "links.html" and also puts results in display text area
	 * @param url is url of a YouTube search query
	 * @param sg is reference to GUI for searching YouTUbe
	 * @return File named links.html where each line is a video link from YouTube search result
	 */
	public static File read(String url, GUISearchYT sg){
		String line="";
		File file = null;
		try {
			//create Buffered Reader to read from web with URL
			BufferedReader reader=new BufferedReader(new InputStreamReader(new URL(url).openStream()));
			line = reader.readLine();
			
			//file to do output to
			file = new File("links.html");

			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);

			//clear search GUI display 
			sg.clearDisplay();

			//Each video link is displayed twice on the page.
			//Keep count to ignore the 2nd link
			//Count is either 0 or 1
			int count2=0;
			while (line != null) {

				//splits around html tags and put result in array
				String[] words = line.split("[<>]"); 
				String link;
				String description;
                
				//iterate through array
				for (int index=0; index<words.length; index++){
					if(isVideoLink(words[index])){ 
						//if it is a video link and count is 1 save it to file
						if(count2 ==1){
			                
							link = trimVideoLink(words[index]);
							bw.append (link);
							bw.newLine();	

							description=words[index+1];
							bw.append(description);
							bw.newLine();

							//add to result text area in search GUI
							sg.appendToDisplay("https://www.youtube.com/watch?v=" + link + "   " + description + "\n");

                            //set count to 0
							count2=0;
						} 
						else
						{
							//set count to 1 if 1st link
							count2=1;
							
						}  

					}   

				}

				
				line = reader.readLine(); 
			} // while
			bw.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return file;
	}



   /**
    * Determines is link matches pattern of link for a video
    * @param word is possible video link
    * @return true if it is valid video link, false otherwise
    */
	public static  boolean isVideoLink(String word){
		String videoLinkPattern = "a href=\"/watch\\?v=.+" ;
		Pattern p = Pattern.compile(videoLinkPattern);
		Matcher m = p.matcher(word);
		return m.matches();
	}

	/**
	 * Trims video link to save only YouTube's ID of link which follows the =, 
	 * since beginning of link is boilerplate "https://www.youtube.com/watch?v="
	 * @param fullLink is full YouTube video link
	 * @return trimmed link--only the ID of video
	 */
	public static String trimVideoLink(String fullLink){
		int startIndex = fullLink.indexOf("/watch?v=");
		int endIndex = fullLink.indexOf("\"", startIndex+9);
		String trimLink = fullLink.substring(startIndex + 9, endIndex );
		return trimLink;
	}

}
