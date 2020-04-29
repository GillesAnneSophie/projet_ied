package test;

import java.util.Scanner;

public class TestConsole {

	public static void main(String[] args) {
		String search = readInputs();

		String type = search.substring(0, search.indexOf("|"));
		String name = search.substring(search.indexOf("|")+1);

		//TODO appels méthodes + utilisation de type et name
		System.out.println("You entered: " + type + " - " + name);
	}
	
	private static String readInputs() {
        Scanner scanner = new Scanner(System.in);
        int resultType = 0;
        String result;
        
        do {
        	System.out.println("Search by movie 'title' or by 'actor' name?");
    		System.out.println("Please type 'title' or 'actor' and press Enter. Then enter the name you want to search.");
        	
        	String s = scanner.nextLine();
        	resultType = 0;
            if(s.equals("title")) {
            	resultType = 1;
            }
            else if(s.equals("actor")) {
            	resultType = 2;
            }
            
            String name = scanner.nextLine();
            StringBuffer sb = new StringBuffer();
            sb.append(resultType);
            sb.append("|");
            sb.append(name);
            result = sb.toString();
            
            if(resultType == 0) {
            	System.out.println("Error: please enter an acceptable value :(");
            }
        }while(resultType == 0);
        
        scanner.close();
        return result;
	}

}
