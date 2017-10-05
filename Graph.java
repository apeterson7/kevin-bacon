import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.Map.Entry;

import javax.naming.InvalidNameException;



public class Graph{
    
    private String center;
    //A Hashmap that maps strings of actors and films to their corresponding vertex objects
    public HashMap<String, Vertex> vertices = new HashMap<String, Vertex>();
    
    public int actors;
    //A hashmap that stores a string key, either actor or film, 
    //and an ArrayList value of the linked films or actors respectively
    public HashMap<String, HashSet<String>> graph = new HashMap<String, HashSet<String>>();
    
    public Graph(String file) throws MalformedURLException, IOException {
	Scanner scr = new Scanner(new URL("http://cs.oberlin.edu/~gr151/imdb/imdb.top250.txt").openStream());
	/**
	 * if their is no argument on the command line, a list of txt files
	 * online is given for the user to make a selection
	 */
	try {
	    scr = new Scanner(new File(file));
	}catch(IOException e){
	    System.out.println();
	    System.out.println("Invalid file name: Select from the following URLs");
	    System.out.println("------------------------------------------------");
	    System.out.println("(1) imdb.cslam.txt - a 11 line file with the example from the prelab" +
	    		"\n(2) imdb.small.txt - a 1817 line file with just a handful of performers (161), fully connected" +
	    		"\n(3) imdb.top250.txt - a 14339 line file listing just the top 250 movies on IMDB. (Disconnected groups of foreign films.)" +
	    		"\n(4) imdb.pre1950.txt - a 966338 line file with movies made before 1950" + 
	    		"\n(5) imdb.post1950.txt - a 6848516 line file with the movies made after 1950" + 
	    		"\n(6) imdb.only-tv-v.txt - a 2021636 line file with only made for TV and direct to video movies" + 
	    		"\n(7) imdb.no-tv-v.txt - a 5793218 line file without the made for TV and direct to video movies (best for the canonical Kevin Bacon game)" +
		    	"\n(8) imdb.full.txt - all 7814854 lines of IMDB for you to search through" +
	    		"\n (default is imdb.top250.txt)");
	    System.out.println("");
	    Console console = System.console();
	    String input = console.readLine("Select number (1-8):");
	    switch(Integer.parseInt(input)) {
	    	case 1: scr = new Scanner(new URL("http://cs.oberlin.edu/~gr151/imdb/imdb.cslam.txt").openStream());
	    	    	 break;
		case 2: scr = new Scanner(new URL("http://cs.oberlin.edu/~gr151/imdb/imdb.small.txt").openStream());
   	    	 	 break;
		case 3: scr = new Scanner(new URL("http://cs.oberlin.edu/~gr151/imdb/imdb.top250.txt").openStream());
   	    	 	 break;
		case 4: scr = new Scanner(new URL("http://cs.oberlin.edu/~gr151/imdb/imdb.pre1950.txt").openStream());
   	    	 	 break;
		case 5: scr = new Scanner(new URL("http://cs.oberlin.edu/~gr151/imdb/imdb.post1950.txt").openStream());
   	    	 	 break;
		case 6: scr = new Scanner(new URL("http://cs.oberlin.edu/~gr151/imdb/imdb.only-tv-v.txt").openStream());
   	    	  	 break;
		case 7: scr = new Scanner(new URL("http://cs.oberlin.edu/~gr151/imdb/imdb.no-tv-v.txt").openStream());
   	    	 	 break;
		case 8: scr = new Scanner(new URL("http://cs.oberlin.edu/~gr151/imdb/imdb.full.txt").openStream());
   	    	 	 break;
   	    	default: scr = new Scanner(new URL("http://cs.oberlin.edu/~gr151/imdb/imdb.top250.txt").openStream());
	   
	    }
	}
	
	/**
	 * while loop to scan through the selected txt file, filling in
	 * the adjacency list
	 */
	Vertex actor;
	Vertex film;
	int line = 0;
	while(scr.hasNextLine()) {
	    String[] parse = scr.nextLine().split("\\|");
	    line++;
	    if(line%100 == 0) {
		System.out.print("\rLine " + line);
	    }
	    
	    actor = new Vertex((parse[0]), true);
	    film = new Vertex((parse[1]), false);

	    vertices.put(parse[0], actor);
	    vertices.put(parse[1], film);

	    //1st index of the HashMap is performed
	    HashSet<String> list = graph.get(parse[0]);
	    if(list != null) {
		list.add(parse[1]);
	    }else {
		actors++; // counter to keep track of number of actors added for
		// topCenter feedback later
		// otherwise create a new arraylist and add the film
		HashSet<String> store = new HashSet<String>();
		store.add(parse[1]);
		// put the (actor string,film arraylist) pair to the graph
		// hashMap
		graph.put(parse[0], store);
	    }
	    //2nd index of the HashMap is performed
	    list = graph.get(parse[1]);
	    if(list != null) {
		list.add(parse[0]);
	    }else {
		HashSet<String> store = new HashSet<String>();
		store.add(parse[0]);
		graph.put(parse[1], store);
	    }
	    
	}
    }
    /**
     * 
     * @param s the string that is being searched for
     * iterates through all the vertexes in the adjacency list
     * and sets their previous and distance fields appropriately
     * 
     * @param topCenter -- a boolean to suppress output if
     * recenter is called in the context of topcenter
     * 
     * @throws InvalidNameException
     */
    private void center(String s, boolean topCenter) throws InvalidNameException {
	center = s;
	Queue<Vertex> wl = new Queue<Vertex>();
	if(vertices.get(s) == null || vertices.get(s).isActor() == false) {
	    throw new InvalidNameException();
	}
	vertices.get(s).setPrev(null);
	vertices.get(s).setDist(0);
	vertices.get(s).setVisited();
	wl.enqueue(vertices.get(s));
	int counter = 0;
	//Suppress output if center is being called in the topcenter method
	if(topCenter == false) {
	    System.out.print("\nVertex: " + counter++ + " of " + vertices.size()); 
	}
	while(wl.isEmpty() == false) {
	    Vertex v = wl.dequeue();
	    counter++;
	    if(topCenter == false && counter%100 == 0) {
		System.out.print("\rVertex: " + counter + " of " + vertices.size()); 
	    }
	    HashSet<String> edges = graph.get(v.getLabel());
	    for(String i : edges) {
		Vertex next = vertices.get(i);
		if(next.isVisited() == false) {
		    next.setVisited();
		    next.setDist(v.getDist()+1);
		    next.setPrev(v);
		    wl.enqueue(next);
		}
	    }
	}
	if(topCenter == false) {
	    System.out.println("\rVertex: " + counter++ + " of " + vertices.size());
	}
	Iterator<Entry<String, Vertex>> itr = vertices.entrySet().iterator();
	while(itr.hasNext()) {
	    itr.next().getValue().setUnvisited();
	}
	
    }
    
    /**
     * 
     * @param name -- string to recenter
     * @param topCenter -- a boolean to suppress output if
     * recenter is called in the context of topcenter
     * @throws InvalidNameException
     */
    public void recenter(String name, boolean topCenter) throws InvalidNameException {
	center(name, topCenter);
    }
    
    public String getCenter() {
	return center;
    }
    
    /**
     * 
     * @param name -- string representing the vertex to start from
     * trace backwards through the path via 'prev' fields in each vertex
     * until the start is found
     * --- conditionals exist to handle cases of unreachable vertexes
     * or if the vertex is the center
     * @return a string representation of the nodes traversed to get to
     * the center
     * @throws InvalidNameException
     */
    public String find(String name) throws InvalidNameException {
	if(vertices.get(name) == null || vertices.get(name).isActor() == false) {
	    throw new InvalidNameException();
	}else if(vertices.get(name).getLabel().equals(center)) {
	    return name + " (" + vertices.get(name).getDist() + ")";
	}else if(vertices.get(name).getPrev() == null) {
	    return name + " is unreachable";
	}
	Vertex v = vertices.get(name);
	int degree = v.getDist()/2;
	String path = v.getLabel();
	while(v.getPrev() != null) {
	    path = path + " -> " + v.getPrev().getLabel();
	    v = v.getPrev();
	}
	path = path + " (" + degree + ")";
	return path;
    }

    
    /**
     * Uses a Double[] of size 3 to hold the average distance of vertexes
     * from the center, the reachable node count, and the unreachable node count
     * 
     * @return
     */
    public Double[] avgdist() {
	Iterator<Entry<String,Vertex>> itr = vertices.entrySet().iterator();
	Double[] info = new Double[3];
	//Running sum of distances of reachable nodes
	info[0] = 0.0;
	//Reachable node counter
	info[1] = 0.0;
	//Unreachable node counter
	info[2] = 0.0;
	while(itr.hasNext()) {
	    Vertex v = itr.next().getValue();
	    
	    //The node is unreachable (distance was never assigned)
	    if(v.getDist() == 0 && v.getLabel().equals(center) == false) {
		info[2]++;
	    //The node is reachable
	    }else {
		//add the distance to the running sum of distances
		info[0] += v.getDist();
		//increment the reachable node counter
		info[1]++;
	    }
	}
	info[0] = info[0]/(info[1]*2);
	return info;
    }
    
    /**
     * 
     * @param name the actor that is being looked at
     * returns empty list if name is not associated with an actor
     * @return
     * @throws InvalidNameException 
     */
    public HashSet<String> movies(String name) throws InvalidNameException {
	if(vertices.get(name).isActor()) {
	    return graph.get(name);
	}else {
	    throw new InvalidNameException();
	}
    }
    
    /**
     * 
     * @return an ArrayList of strings containing the top lowest average distances
     * @throws InvalidNameException
     */
    public ArrayList<String> topCenter() throws InvalidNameException{
	ArrayList<String> 	list = new ArrayList<String>();
	Iterator<Entry<String,Vertex>> itr = vertices.entrySet().iterator();
	int counter = 0;
	while(itr.hasNext()) {
	    Vertex v = itr.next().getValue();
	    if(v.isActor()) {
		System.out.print("\r" + counter++ + " of " + actors + " actors");
		recenter(v.getLabel(), true); //recenter while suppressing the methods commandline output
		list.add(avgdist()[0] + " " + v.getLabel());
	    }
	}
	Collections.sort(list);
	return list;
	
    }
    
    /**
     * @return the name of one of the actors with most associated movies
     */
    public String most() {
	Iterator<Entry<String, HashSet<String>>> itr = graph.entrySet().iterator();
	String temp = itr.next().getKey();
	while(itr.hasNext()) {
	    String next =  itr.next().getKey();
	    if(graph.get(temp).size() < graph.get(next).size() && vertices.get(next).isActor()) {
		temp = next;
	    }
	}
	return temp;
    }
    
    /**
     * displays one of the longest paths to the center
     * @throws InvalidNameException
     */
    public void longest() throws InvalidNameException {
	Iterator<Entry<String, Vertex>> itr = vertices.entrySet().iterator();
	String temp = itr.next().getKey();
	while(itr.hasNext()) {
	    String next =  itr.next().getKey();
	    if(vertices.get(next).isActor() && vertices.get(next).getDist() > vertices.get(temp).getDist()) {
		temp = next;
	    }
	}
	System.out.println(find(temp));
    }
    
    /**
     * 
     * @returns a table of distance lengths and the number of vertexes
     * with that distance from the center
     * includes a field for unreachable nodes
     */
    public HashMap<Integer,Integer> table() {
	HashMap<Integer,Integer> countTable = new HashMap<Integer,Integer>();
	Iterator<Entry<String,Vertex>> itr = vertices.entrySet().iterator();	
	while(itr.hasNext()) {
	    Vertex v = itr.next().getValue();
	    if(countTable.containsKey(v.getDist())) {
		countTable.put(v.getDist(), countTable.get(v.getDist())+1);
	    }else {
		countTable.put(v.getDist(), 1);
	    }
	}
	return countTable;
    }
    
}
