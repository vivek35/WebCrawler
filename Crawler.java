import java.io.*;
import java.util.Scanner;
import java.net.*;
import java.util.ArrayList;
class WebCrawler{

	private ArrayList<String> foundURL;
	private ArrayList<String> visitedURL;

	static int counter;
	private String startURL;
	private String texturl;
	private String visitURL;
	private int noofpage;
	
	public WebCrawler(String startURL,int noofpage){
		this.startURL=startURL;//Encpasulation
		this.noofpage=noofpage;//Encpasulation
		foundURL = new ArrayList<String>(noofpage);
		visitedURL = new ArrayList<String>(noofpage);
		counter=0;
		CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));// This line of code is added to avoid the error "java.net.ProtocolException: Server redirected too many times” Error after visiting near about 58 URL's
	}
	public void startCrawling(){
		visitURL(startURL);
	}
	
	private void visitURL(String visitURL){
		try{
			this.visitURL=visitURL;//Encpasulation

			visitedURL.add(visitURL);

			counter++;

			System.out.println("URL: "+counter+"::"+visitURL);
			if(counter!=noofpage){

				java.net.URL urld = new java.net.URL(visitURL);
				Scanner input = new Scanner(urld.openStream());
				while(input.hasNext()){	

					traverse(input.nextLine());
				}
				for(int k=0;k<=foundURL.size()-1;k++){
					if(foundURL.get(k)!=" "){
						String justvisit=foundURL.get(k);
						if(visitedURL.contains(justvisit)==false){
							visitURL(foundURL.get(k));
						}
					}				
				}
			}else{
				System.out.println("Successfully Traversed "+noofpage+" URL's");
				System.exit(0);
			}
			
		}
		catch(MalformedURLException e){
			System.out.println("URL not found: "+e);
		}
		catch(IOException e){
			System.out.println("IOException found: "+e);
		}

	}
	private void traverse(String texturl){
		int i=0;

		texturl=texturl; //Encapsulation

		if(texturl.indexOf("<a href")!=-1){
			int startindex=0;
			int endindex=0;
			if(texturl.indexOf("http://")!=-1 || texturl.indexOf("https://")!=-1){
				if(texturl.indexOf("http://")!=-1){
					startindex = texturl.indexOf("http://");
				}else if(texturl.indexOf("https://")!=-1){
					startindex = texturl.indexOf("https://");
				}
				endindex=texturl.indexOf("\"",startindex);
				foundURL.add(texturl.substring(startindex,endindex));
			}
			i++;
		}
	}
	
}
public class Crawler{

	public static void main(String []args){
		try{
			int i=0;			
			String startURL = "";
			File file = new File("/home/vivek/Java/ex.txt");
    		Scanner sc = new Scanner(file);


		    while (sc.hasNextLine()){
		  	  startURL=sc.nextLine();
		  	}
		  	WebCrawler w = new WebCrawler(startURL,100);
		  	w.startCrawling();
		  }catch(FileNotFoundException e){
		  		System.out.println("File Not Found: "+e);
		  }
	}
}

