package source;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

import jdbc.Queries;


public class TheNumbers {
	// https://www.the-numbers.com/market/<year>/genre/<genre>
	// genre = Adventure, Comedy, Drama, Action, Thriller-or-Suspense, Romantic-Comedy
	// year = 2000 to 2015
	private ArrayList<String> genres = new ArrayList<String>();
	private String filepath;
	private String generatedFilename;
	
	public TheNumbers() {
		genres.add("Adventure");
		genres.add("Comedy");
		genres.add("Drama");
		genres.add("Action");
		genres.add("Thriller-or-Suspense");
		genres.add("Romantic-Comedy");
		
		ArrayList<String> csvInformation = Queries.getCsvInformation();
		filepath = csvInformation.get(1);
		generatedFilename = csvInformation.get(2);
	}
	
	public void generateMoviesInformation() {
		if(areFilesGenerated() == false) {
			ArrayList<ArrayList<String>> moviesInformation = new ArrayList<ArrayList<String>>();
			String baseUrl = "https://www.the-numbers.com";
			
			System.out.println("Generate CSV files...");
			
			for(String genre : genres) {
				System.out.println("Current genre = " + genre);
				moviesInformation.clear();
				for(int year=2000 ; year<=2015 ; year++) {
					Document doc = null;
					try {
						doc = Jsoup.connect(baseUrl+"/market/"+year+"/genre/"+genre).get();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					Element divTable = doc.getElementById("page_filling_chart").nextElementSibling().nextElementSibling();
				    Element table = divTable.select("table").first();
				    Elements rows = table.select("tr");
	
				    for(int i=1 ; i<(rows.size()-2) ; i++) {
				        Element row = rows.get(i);
				        Elements cols = row.select("td");
	
				        String title = cols.get(1).text();
				        if(title.contains("…")) {
				        	String url = baseUrl+cols.get(1).select("a").attr("href");
				        	
				        	Document doc1 = null;
							try {
								doc1 = Jsoup.connect(url).get();
							} catch (IOException e) {
								e.printStackTrace();
							}
							Element heading1 = doc1.select("h1").first();
							title = heading1.text();
							title = title.substring(0, title.length()-6);
				        }
				        
				        String distributor = cols.get(3).text();
				        
				        ArrayList<String> currentArray = new ArrayList<String>();
				        currentArray.add(title);
				        currentArray.add(genre);
				        currentArray.add(distributor);
				        
				        moviesInformation.add(currentArray);
				    }
				}
				
				createCsvFile(genre, moviesInformation);
			}
			System.out.println("Files generated!");
		}
		else {
			System.out.println("Files are already generated.");
		}
	}
	
	private void createCsvFile(String genre, ArrayList<ArrayList<String>> moviesInformation) {
		String[] tabFilename = {generatedFilename.substring(0, generatedFilename.indexOf(".*\\")), generatedFilename.substring(generatedFilename.indexOf(".*\\")+3)};
		String fileName = tabFilename[0]+genre+tabFilename[1];
		
		File file = new File(filepath+fileName);
		try {
			Files.deleteIfExists(file.toPath());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			FileWriter csvWriter = new FileWriter(filepath+fileName);
		
			csvWriter.append("titre\tgenre\tdistributeur\n");
			for(ArrayList<String> information : moviesInformation) {
			    csvWriter.append(String.join("\t", information));
			    csvWriter.append("\n");
			}
	
			csvWriter.flush();
			csvWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private boolean areFilesGenerated() {
		String[] tabFilename = {generatedFilename.substring(0, generatedFilename.indexOf(".*\\")), generatedFilename.substring(generatedFilename.indexOf(".*\\")+3)};
	    File dir = new File(filepath);
	    File[] listOfFiles = dir.listFiles();

	    for(int i=0 ; i<listOfFiles.length ; i++) {
	        if(listOfFiles[i].isFile()) {
	            String currentFile = listOfFiles[i].getName();
	            if (currentFile.startsWith(tabFilename[0]) && currentFile.endsWith(tabFilename[1])) {
	                return true;
	            }
	        }
	    }
	    return false;
	}
}
