package test;

import java.util.ArrayList;
import java.util.Scanner;

import data.Film;
import jdbc.Queries;
import source.TheNumbers;
import projet.main_1_0.main;
import mediator.Mediator;

public class TestConsole {
		//Films
	//Ed Wood
	//Avatar
	//Man of Steel
		//Actors
	//Sean Penn
	//Dev Patel
	//Mel Gibson

	public static void main(String[] args) {
		//Generate files from the-numbers.com
		TheNumbers tn = new TheNumbers();
		tn.generateMoviesInformation();

		//Fill database with Talend Jobs
		if(Queries.isFilmTableEmpty()) {
			System.out.println("Add content to database by running Talend jobs...");
			main talendJob = new main();
			talendJob.runJob(new String[]{});
		}
		else {
			System.out.println("Database is not empty, there is no need to run Talend jobs.");
		}
		
		//Ask user
		Scanner scanner = new Scanner(System.in);
		String search;
		ArrayList<Film> films = new ArrayList<Film>();
		
		do {
			search = readInputs(scanner);
	
			if(!search.equals("q")) {
				String type = search.substring(0, search.indexOf("|"));
				String name = search.substring(search.indexOf("|")+1);
				films.clear();
				
				System.out.println("You entered:" + name);
				//types: 1=title / 2=actor 

				if(Integer.parseInt(type) == 1) {
					//search by title
					films.add(Mediator.getFilmFromTitle(name));
					
					for(Film film : films) {
						if (film.getActeurs()=="null" && film.getDateSortie()==null && film.getGenre()==null && film.getPlot()==null && film.getRealisateur()=="null" && film.getProducteur()=="null" ) {
							System.out.println("Sorry, we have no information about a movie with this title.");
						}else {
							System.out.println(film.getFilmInformationForFilm());
						}
					}
					
				}
				else if(Integer.parseInt(type) == 2){
					//search by actor
					films = Mediator.getFilmsFromActor(name);
					
					for(Film film : films) {
						System.out.println(" +  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -  -"+"\n | "+film.getFilmInformationForActor());
					}
					if (films.isEmpty()) {
						System.out.println("Sorry, we have no information about this actor.");
					}
				}
			}
		}while(!search.equals("q"));
		scanner.close();
	}
	
	private static String readInputs(Scanner scanner) {
        int resultType = 0;
        String result;
        
        do {
        	System.out.println("\n\tSearch by movie 'title' or by 'actor' name (enter 'q' to quit)?\n\tPlease type 'title' or 'actor' and press Enter. Then enter the name you want to search.");
        	
        	String s = scanner.nextLine();
        	resultType = 0;
            if(s.equals("title")) {
            	resultType = 1;
            }
            else if(s.equals("actor")) {
            	resultType = 2;
            }
            else if(s.equals("q")) {
            	return "q";
            }
            
            String name = scanner.nextLine();
            if(name.equals("q")) {
            	return "q";
            }
            StringBuffer sb = new StringBuffer();
            sb.append(resultType);
            sb.append("|");
            sb.append(name);
            result = sb.toString();
            
            if(resultType == 0) {
            	System.out.println("\tError: please enter an acceptable value :(");
            }
        }while(resultType == 0);
        
        return result;
	}

}
