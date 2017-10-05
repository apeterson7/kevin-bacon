
public class Vertex {
    
    /**
     * label is a classmember to store the vertex's label (actor or film name in implementation)
     */
    private String label;
    
    /**
     * prev stores the vertex that is previous in the path towards a start vertex
     * as set by the Unweighted BFS algorithm 
     */
    private Vertex prev;
    
    /**
     * distance is the number of vertex between this and the start vertex
     */
    private Integer distance;
    
    /**
     * boolean to differentiate between vertexes containing actors versus films
     */
    private boolean isActor;
    
    /**
     * class member to keep track of whether the vertex has been visited.
     */
    private boolean visited;
    
    
    /**
     * constructor
     * @param label
     * @param prev
     * @param dist
     */
    public Vertex(String label, Vertex prev, Integer dist, Boolean isActor) {
	this.label = label;
	this.prev = prev;
	this.distance = dist;
	this.isActor = isActor;
    }
    
    public Vertex(String label, Boolean isActor) {
	this.label = label;
	this.prev = null;
	this.distance = 0;
	this.isActor = isActor;
    }
  
    

    /**
     * get functions for class members
     */
    public String getLabel() {
	return label;
    }
    
    public Vertex getPrev() {
	return prev;
    }
    
    public Integer getDist() {
	return distance;
    }
    
    public boolean isActor() {
	return isActor;
    }
    
    public boolean isVisited() {
	return visited;
    }
    
    /**
     * set methods for class members
     */
    public void setLabel(String label) {
	this.label = label;
    }
    
    public void setPrev(Vertex v) {
	prev = v;   
    }
    
    public void setDist(Integer d) {
	distance = d;
    }
    
    public void setVisited() {
	visited = true;
    }
    public void setUnvisited() {
	visited = false;
    }
    
    /**
     * isEqual method to compare the strings
     * @param v
     * @return
     */
    

    public boolean equals(Vertex v) {
	if(v.label.compareTo(this.label) == 0) {
	    return true;
	}else {
	    return false;
	}
    }
    
    @Override
    public int hashCode() {
	return label.hashCode();
	
    }
    /**
     * @returns the label
     */
    public String toString() {
	return this.label;
    }

}
