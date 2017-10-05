import java.io.Console;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.naming.InvalidNameException;


public class BaconNumber {

    public static void main(String[] args) throws InvalidNameException, MalformedURLException, IOException {
	
	Graph graph = null;
	if(args.length == 0) {
	    graph = new Graph("");
	}else {
	    graph = new Graph(args[0]);
	}
	
	
	graph.recenter("Kevin Bacon (I)", false);
	System.out.println("Center: " + graph.getCenter());
	System.out.println("Input \"commands\" for list of valid commands");
	Console console = System.console();
	String input = "";
	while(input != "quit") {
	    System.out.println();
	    System.out.println();
	    input = console.readLine("Enter command:");
	    System.out.println();
	    String[] arguments = input.split(" ");
	    String command = "";
	    if(arguments[0] != null) {
		command = arguments[0];
	    }
	    String argument = "";
	    if(arguments.length > 1) {
		
		    for(int i = 1; i < arguments.length; i++) {
			argument = argument + arguments[i] + " ";
		    }
		    argument = argument.substring(0,argument.length()-1);
	    }
	    
	    if(command.equals("quit")) {
		break;
	    }else if(command.equals("find")) {
		if(argument != "") {
		    try {
			System.out.println(graph.find(argument));
		    }catch(InvalidNameException e) {
			try {
			    	argument = argument + " (I)";
				System.out.println(graph.find(argument));
			    }catch(InvalidNameException f) {
				System.out.println("Invalid Name");
			    }
		    }
		}else {
		    System.out.println("Argument Required");
		}
	    }else if(command.equals("recenter")) {
		if(argument != "") {
		    try {
			graph.recenter(argument, false);
			System.out.println("New center: " + argument);
		    }catch(InvalidNameException e) {
			try {
			    	argument = argument + " (I)";
				graph.recenter(argument, false);
				System.out.println("New center: " + argument);
			    }catch(InvalidNameException f) {
				System.out.println("Invalid Name");
			    }
		    }
		}else {
		    System.out.println("Argument Required");
		}
	    
	    }else if(command.equals("avgdist")) {
		Double[] info = graph.avgdist();
		String s1 = info[1].toString();
		String s2 = info[2].toString();
		System.out.println(info[0] + "    " + graph.getCenter() + 
		" ("+s1.substring(0, s1.length()-2)+","+s2.substring(0,s2.length()-2)+")");
	    
	    }else if(command.equals("topcenter")) {
		try {
		    Integer arg = Integer.parseInt(argument);
		    String currentCenter = graph.getCenter();
		    ArrayList<String> list = graph.topCenter();
		    System.out.println();
		    System.out.println();
		    for (int i = 0; i < arg; i++) {
			System.out.println(list.get(i));
		    }
		    graph.recenter(currentCenter, false);
		} catch (NumberFormatException e) {
		    System.out.println("Argument must be Integer");
		}
	    }else if(command.equals("movies")) {
		if(graph.vertices.get(argument) != null) {
		    try {
			System.out.println(graph.movies(argument).toString());
		    }catch(InvalidNameException e) {
			System.out.println("Please use an actor's name");
		    }
		    
		}else {
		    System.out.println("Actor not found");
		}
	    
	    }else if(command.equals("most")) {
		String most = graph.most();
		System.out.println(most + " (" + graph.graph.get(most).size()+")");

	    }else if(command.equals("longest")) {
		graph.longest();
		
	    }else if(command.equals("table")) {
		
		
		int unreachable = graph.table().get(0);
		System.out.println("Table of distances for "+ graph.getCenter());
		System.out.println("Number 0: 1");
		int count = 2;
		while(graph.table().get(count) != null) {
		    System.out.println("Number "+count/2+": "+graph.table().get(count));
		    count += 2;
		}
		System.out.println("Unreachable "+unreachable);
		
	    }else if(command.equals("commands")) {
		System.out.println("find <name>     -- finds the shortest path between the center and\n                   a given <name>");
		System.out.println("recenter <name> -- recenters the search around a new <name>");
		System.out.println("avgdist         -- displays the average distance between \n                   connected actods and the center");
		System.out.println("topcenter <n>   -- shows the top <n> lowest average distances\n                   of actors");
		System.out.println("table           -- displays the counts of bacon numbers \n                   from the given center from shortest to longest");
		System.out.println("movies <title>  -- displays a list of movies that <name>\n                   has starred in");
		System.out.println("longest         -- prints out one of the longest paths");
		System.out.println("most            -- displays the actor with the most\n                   film credits");
		System.out.println("findAll         -- ");
		System.out.println("quit            -- exits the program");
	    }else {
		System.out.println("Invalid Command");
	    }
	    
	}
	
    }
}
