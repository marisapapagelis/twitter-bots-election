/**
 * AdjListsGraph<T> implements the Graph<T> interface. It
 * also includes methods to perform a depth first search 
 * and a depth first search traversal as well as a method
 * to safe the graph information to a tgf file.
 * @author mpapagel
 * @version 05/15/20
 */
import java.util.*;
import java.io.*;
import javafoundations.*;
public class AdjListsGraph<T> implements Graph<T>
{
    // instance variables 
    private Vector<LinkedList<T>> arcs;
    private Vector<T> vertices;
    
    /** 
     * Constructor for AdjListsGraph class
     */
    public AdjListsGraph(){
        arcs = new Vector<LinkedList<T>>();
        vertices = new Vector<T>();
    }

    /** 
     * Returns a boolean indicating whether this graph is empty or not.
     * A graph is empty when it contains no vertice,and of course, no edges.
     * @return true if this graph is empty, false otherwise.
     */
    public boolean isEmpty(){
        return vertices.size() == 0;
    }

    /** 
     * Getter method for number of vertices in the graph. 
     * @return the number of vertices in this graph
     */
    public int getNumVertices(){
        return vertices.size();
    }

    /** 
     * Getter method for all the vertices in the graph.
     * @return vertices Vector<T> of all the vertices
     */
    public Vector<T> getAllVertices(){
        return vertices;
    }
    
    /** 
     * Returns the number of arcs in this graph.
     * An arc between vertices A and B exists, if a direct connection
     * from A to B exists.
     * @return the number of arcs in this graph
     *  */
    public int getNumArcs(){
        int numArcs = 0;
        for( int  i= 0; i<arcs.size(); i++){
            numArcs+= arcs.get(i).size();
        }
        return numArcs;
    }

    /** 
     * Returns true if an arc (direct connection) exists 
     * from the first vertex to the second, false otherwise
     * @return true if an arc exists between the first given vertex (vertex1),
     * and the second one (vertex2),false otherwise
     * 
     *  */
    public boolean isArc (T vertex1, T vertex2){
        if (! vertices.contains(vertex1)){
            return false;
        }
        else {
            int index = vertices.indexOf(vertex1);
            return arcs.get(index).contains(vertex2); 
        }
    }

    /** 
     * Returns true if an edge exists between two given vertices, i.e,
     * an arch exists from the first vertex to the second one, and an arc from
     * the second to the first vertex, false otherwise.
     * @param vertex1 first given vertex
     * @param vertex2 second given vertex
     * @return true if an edge exists between vertex1 and vertex2, 
     * false otherwise
     * */
    public boolean isEdge (T vertex1, T vertex2){
        return isArc(vertex1, vertex2) && isArc(vertex2, vertex1);
    }

    /** 
     * Returns true if the graph is undirected, that is, for every
     * pair of nodes i,j for which there is an arc, the opposite arc
     * is also present in the graph, false otherwise.  
     * @return true if the graph is undirected, false otherwise
     * */
    public boolean isUndirected(){
        for(int i=0; i<arcs.size(); i++){
            for(int j = 0; j<arcs.get(i).size(); j++)
                if (! isEdge((arcs.get(i).get(j)),(vertices.get(i)))){
                    return false;
                }
        }
        return true;
    }

    /** 
     * Adds the given vertex to this graph
     * If the given vertex already exists, the graph does not change
     * @param vertex the vertex to be added to this graph
     * */
    public void addVertex (T vertex){
        if (!vertices.contains(vertex)){
            vertices.add(vertex);
            arcs.add(new LinkedList<T>()); 
        }
    }

    /** 
     * Removes the given vertex from this graph.
     * If the given vertex does not exist, the graph does not change.
     * @param vertex the vertex to be removed from this graph
     *  */
    public void removeVertex (T vertex){
        if (vertices.contains(vertex)){
            int vertexIndex = vertices.indexOf(vertex);
            vertices.remove(vertex);
            arcs.remove(vertexIndex); 
            for(int i = 0; i<arcs.size(); i++){
                arcs.get(i).remove(vertex); 
            }
        }

    }

    /** 
     * Inserts an arc between two given vertices of this graph.
     * if at least one of the vertices does not exist, the graph 
     * is not changed.
     * @param vertex1 the origin of the arc to be added to this graph
     * @param vertex2 the destination of the arc to be added to this graph
     *  */
    public void addArc (T vertex1, T vertex2){
        if (vertices.contains(vertex1) && vertices.contains(vertex2)){
            int vertexIndex =vertices.indexOf(vertex1);
            arcs.get(vertexIndex).add(vertex2); 
        }
    }

    /** 
     * Removes the arc between two given vertices of this graph.
     * If one of the two vertices does not exist in the graph,
     * the graph does not change.
     * @param vertex1 the origin of the arc to be removed from this graph
     * @param vertex2 the destination of the arc to be removed from this graph
     * */
    public void removeArc (T vertex1, T vertex2){
        if (isArc(vertex1, vertex2)){
            int vertexIndex = vertices.indexOf(vertex1);
            arcs.get(vertexIndex).remove(vertex2); 
        }
    }

    /** 
     * Inserts the edge between the two given vertices of this graph,
     * if both vertices exist, else the graph is not changed. 
     * @param vertex1 the origin of the edge to be added to this graph
     * @param vertex2 the destination of the edge to be added to this graph
     *  */
    public void addEdge (T vertex1, T vertex2){
        if(! isEdge (vertex1, vertex2)){
            addArc (vertex1,vertex2);
            addArc (vertex2, vertex1);
        }
    }

    /** 
     * Removes the edge between the two given vertices of this graph,
     * if both vertices exist, else the graph is not changed.
     * @param vertex1 the origin of the edge to be removed from this graph
     * @param vertex2 the destination of the edge to be removed from this graph
     * 
     */
    public void removeEdge (T vertex1, T vertex2){
        if (isEdge (vertex1, vertex2)){
            removeArc(vertex1,vertex2);
            removeArc(vertex2,vertex1);
        }
    }

    /** 
     * Return all the vertices, in this graph, adjacent to the given vertex.
     * @param vertex a vertex in the graph whose successors will be returned.
     * @return LinkedList containing all the vertices x in the graph,
     * for which an arc exists from the given vertex to x (vertex -> x).
     * */
    public LinkedList<T> getSuccessors(T vertex){
        int vertexIndex = vertices.indexOf(vertex);
        return arcs.get(vertexIndex); 
    }

    /** 
     * Return all the vertices x, in this graph, that precede a given
     * vertex.
     * @param vertex a vertex in the graph whose predecessors will be returned.
     * @return LinkedList containing all the vertices x in the graph,
     * for which an arc exists from x to the given vertex (x -> vertex).
     * */
    public LinkedList<T> getPredecessors(T vertex){
        LinkedList<T> predecessors = new LinkedList<T>();
        if (vertices.contains(vertex)){

            //for (int i = 0; i<vertices.indexOf(vertex); i++){
            int i = 0;
            while (vertices.get(i) != vertex );
            {
                predecessors.add(vertices.get(i));
            }
        }
        return predecessors;
    }

    /** 
     * Returns a string representation of this graph.
     * @return a string represenation of this graph, containing its vertices
     * and its arcs/edges
     *  */
    public String toString(){
        String result = "Verticies";
        result += vertices.toString();
        result += "Edges";
        for (int i = 0; i < arcs.size(); i++) {
            result  += "from" + vertices.elementAt(i) + ": " 
            + arcs.elementAt(i) + "\n";
        }
        return result;
    }

    /** 
     * Writes this graph into a file in the TGF format.
     * @param tgf_file_name the name of the file where this graph will be written 
     * in the TGF format.
     * */
    public void saveToTGF(String tgf_file_name){
        try{
            PrintWriter printer = new PrintWriter(new File(tgf_file_name));
            for (int i = 0; i<vertices.size(); i++){
                printer.println((i+1)+" "+vertices.get(i));
            }
            printer.println("#");
            for (int i = 0; i<arcs.size(); i++){
                for (int j = 0; j<arcs.get(i).size(); j++){
                    printer.println((i+1)+" "+arcs.get(i).get(j));
                }
            }
        }
        catch(IOException e){
            System.out.println(e);
        }
    }

    /**
     * Performs a depth first search of the entire graph using a stack
     * starting at the given point with no specific end point.
     * @param T starting vertex for DFS traversal
     * @return LinkedList<T> containing the verticies that are the
     * outcome of the depth first search
     */

    public LinkedList<T> depthFirstSearch(T vertex) {
        ArrayStack<T> stk = new ArrayStack<T>();
        LinkedList<T> result = new LinkedList<T>();
        boolean [] marked = new boolean[vertices.size()];
        for (int v = 0; v < vertices.size(); v++) {
            marked[v] = false; //mark each vertex as unvisited
        }
        T currentNode;
        int currentIndex;
        //push/add starting vertex into stack, then mark it as unvisited
        stk.push(vertex);
        result.add(vertex);
        marked[vertices.indexOf(vertex)] = true;
        while(!stk.isEmpty()) {
            currentNode = stk.peek();
            currentIndex = vertices.indexOf(currentNode);
            LinkedList<T> currentArcsList = arcs.get(currentIndex);
            int indexOfList = 0;
            while (indexOfList == currentArcsList.size()) {
              if (indexOfList == currentArcsList.size()){
                  stk.pop();
                }
              else if (marked[vertices.indexOf(currentArcsList.get(indexOfList))] == false) {
                 //push vertex into stack and add to result list if it hasn't been visited
                 stk.push(currentArcsList.get(indexOfList));
                 result.add(currentArcsList.get(indexOfList));
                 marked[vertices.indexOf(currentArcsList.get(indexOfList))] = true;
                 indexOfList = currentArcsList.size() + 1;
                }
              indexOfList++;
            }
        }
        return result;
    }
    
    /**
     * Performs a breadth first search of the entire graph
     * using a queue.
     * @param vertex starting vertex for BFS traversal
     * @return LinkedList<T> containing the verticies that are
     * the outcome of the breadth first search
     */
    public LinkedList<T> breadthFirstSearch(T vertex){
        LinkedQueue<T> q = new LinkedQueue<T>();       
        LinkedList<T> iterator = new LinkedList<T>();
        int count = 0;
        boolean[] marked = new boolean[vertices.size()];
        for (int v = 0; v < vertices.size(); v++){ // mark each vertex as unvisited
            marked[v] = false;
        }
        q.enqueue(vertex);
        marked[(vertices.indexOf(vertex))] = true;
        T current;
        int currentIndex;
        LinkedList<T> currentList;
        while (!q.isEmpty()){
            current = q.dequeue();
            currentIndex = vertices.indexOf(current);
            iterator.add(current);
            count++;
            currentList = arcs.get(currentIndex);
            for(int i = 0; i < currentList.size(); i++){
                T currentNodeInList = currentList.get(i);
                // enqueue vertex if it hasn't been visited and if arcs exist at from vertex (not null)
                if (!marked[vertices.indexOf(currentNodeInList)] && !(currentNodeInList ==null)){
                    q.enqueue(currentNodeInList);
                    marked[vertices.indexOf(currentNodeInList)] = true;
                }
            }
        }
        return iterator;
    }

    /**
     * Main method used for testing implementation on sample graphs.
     */
    public static void main(String [] args) {
        AdjListsGraph<String> a = new AdjListsGraph<String>();
        a.addVertex("a");
        a.addVertex("b");
        a.addVertex("c");
        a.addVertex("d");
        a.addVertex("e");
        a.addArc("a", "b");
        a.addArc("b", "c");
        a.addArc("c", "a");
        a.addEdge("a", "c");
        a.removeVertex("e");
        System.out.println("Expected isArc() true: " + a.isArc("a", "b"));
        System.out.println("Expected isEdge() false: " + a.isEdge("a", "b"));
        System.out.println(a.depthFirstSearch("a"));
        System.out.println((a.breadthFirstSearch("a")));
        System.out.println(a.toString());
        a.saveToTGF("a.txt");

        AdjListsGraph<String> tree = new AdjListsGraph<String>();
        tree.addVertex("a");
        tree.addVertex("b");
        tree.addVertex("c");
        tree.addVertex("d");
        tree.addVertex("e");
        tree.addVertex("f");
        tree.addVertex("g");
        tree.addVertex("h");
        tree.addVertex("i");
        tree.addVertex("j");
        tree.addEdge("a","b");
        tree.addEdge("a","c");
        tree.addEdge("b","d");
        tree.addEdge("b","e");
        tree.addEdge("c","f");
        tree.addEdge("c","g");
        tree.addEdge("d","h");
        tree.addEdge("d","i");
        tree.addEdge("e","j");
        System.out.println("Tree BFS: a,b,c,d,e,f,g,h,i,j");
        System.out.println((tree.breadthFirstSearch("a")));
        System.out.println("Tree DFS: a,b,c,d,e,f,g,h,i,j");
        System.out.println((tree.depthFirstSearch("a")));
        System.out.println(tree.toString());
        tree.saveToTGF("Tree.tgf");

        AdjListsGraph<String> cycle = new AdjListsGraph<String>();
        cycle.addVertex("1");
        cycle.addVertex("2");
        cycle.addVertex("3");
        cycle.addVertex("4");
        cycle.addVertex("5");
        cycle.addEdge("1","2");
        cycle.addEdge("2","3");
        cycle.addEdge("3","4");
        cycle.addEdge("4","5");
        cycle.addEdge("5","1");
        System.out.println("Cycle BFS: a,b,c,d,e,f,g,h,i,j");
        System.out.println((cycle.breadthFirstSearch("1")));
        System.out.println("Cycle DFS: a,b,c,d,e,f,g,h,i,j");
        System.out.println((cycle.depthFirstSearch("1")));
        System.out.println(cycle.toString());
        cycle.saveToTGF("Cycle.tgf");

        AdjListsGraph<String> disconnected = new AdjListsGraph<String>();
        disconnected.addVertex("1");
        disconnected.addVertex("2");
        disconnected.addVertex("3");
        disconnected.addVertex("4");
        disconnected.addVertex("5");
        disconnected.addVertex("2");
        disconnected.addEdge("1","2");
        disconnected.addArc("2","3");
        disconnected.addEdge("3","4");
        System.out.println(disconnected.toString());
        System.out.println("Disconnected BFS: a,b,c,d,e,f,g,h,i,j");
        System.out.println((disconnected.breadthFirstSearch("1")));
        System.out.println("Disconnected DFS: a,b,c,d,e,f,g,h,i,j");
        System.out.println((disconnected.depthFirstSearch("1")));
        disconnected.saveToTGF("Disconnected.tgf");

    }
}
    

