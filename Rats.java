/**
 * Class reads in data from a csv file and creates an empty graph
 * to add data from the file into to produce a network, then saves it
 * as a .tgf file. This class also determines most and least 
 * popular stories and their names as well as the longest and 
 * shortest connected component within the graph. 
 * 
 * @author mpapagel
 * @version 05/15/20
 */
import java.io.*;
import java.util.*;
import java.net.URL;
public class Rats
{
    // instance variables
    private AdjListsGraph<String> RATgraph;
    private int allstories;
    private int allusers;
    private int alltweets;
    private Hashtable<String, Integer> storyTable;
    private String mostPopularStory;
    private String leastPopularStory;
    private String mostActiveRAT;
    private String urlFindStoryTitle = "http://twittertrails.wellesley.edu/~trails/stories/title.php?id=";

    /**
     * Constructor for Rat class creates a Rats object
     * @param csvFile name of file to be read in
     */
    public Rats(String csvFile)
    {
        this.RATgraph = new AdjListsGraph<String>();
        this.storyTable = new Hashtable<String, Integer>();
        this.readRats(csvFile);
    }
    
    /**
     * Getter method returns total number of stories
     * @return allstories total number of stories
     */
    public int getAllStories() {
        return this.allstories;
    }

    /**
     * Getter method returns total number of tweets
     * @return alltweets total number of tweets
     */
    public int getAllTweets() {
        return this.alltweets;
    }

    /**
     * Getter method returns total number of users
     * @return allusers total number of users
     */
    public int getAllUsers() {
        return this.allusers;
    }
    
    /**
     * Method reads in the data from the csv file and creates
     * a graph to represent the networks created  by the Russian 
     * accounts
     * @param csvFile name of file to be read in
     */
    private void readRats(String csvFile) {
        try{
            Scanner scan = new Scanner(new File(csvFile));
            String header = scan.nextLine(); //skip header line
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                String[] allInfo = line.split("\t"); //separate categories of data in array
                //separate values in array into appropriate variables
                String screenName = allInfo[0];
                RATgraph.addVertex(screenName); //add screen names as vertices in graph
                String userid = allInfo[1];
                String tweetCount = allInfo[2];
                String storyCount = allInfo[3];
                //accumulate total number of tweets and users
                alltweets += Integer.parseInt(tweetCount); 
                allusers++;
                //put stories into an array separating by commas
                String[] storiesArray = allInfo[4].split(",");
                //set variable for number of users who used a story
                int usedStory = 0; 
                for (int i = 0; i < storiesArray.length; i++) {
                    if (storyTable.containsKey(storiesArray[i])) {
                        usedStory = storyTable.get(storiesArray[i]);
                        //increment frequency of story in hashtable
                        storyTable.put(storiesArray[i], usedStory++);
                    } else {
                        storyTable.put(storiesArray[i], 1); //if a story has a frequency of 1
                        allstories++;
                        //add stories to graph as vertices
                        RATgraph.addVertex(storiesArray[i]);
                    }
                    //add edges between each screen name and its associated story to graph
                    RATgraph.addEdge(screenName, storiesArray[i]);
                }
            }
        } catch(FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }
   
      /**
     * Getter method returns the RATgraph.
     * @return RATgraph graph of users and stories
     */
    public AdjListsGraph<String> getGraph(){
        return RATgraph;
    }
    
    /**
     * Method finds the most and least popular stories,
     * by iterating through stories in the hashtable, and
     * sets the most/least popular instance variables to these
     * numbers without returning anything.
     */
    public void popularity() {
        int largestSize = 0;
        int currentSize;
        int smallestSize = Integer.MAX_VALUE;
        for(String storyid: storyTable.keySet()) { 
            currentSize = (RATgraph.getSuccessors(storyid)).size();
            if (currentSize > largestSize) {
                this.mostPopularStory = storyid;
                largestSize = currentSize;
            }
            if (currentSize < smallestSize) {
                this.leastPopularStory = storyid;
                smallestSize = currentSize;
            }
        }
    } 
    
    /**
     * Getter method returns the number of the most popular story.
     * @return mostPopularStory most popular story
     */
    public String getMostPopStory() {
        return this.mostPopularStory;
    }

    /**
     * Getter method returns the number of the least popular story.
     * @return leastPopularStory least popular story
     */
    public String getLeastPopStory() {
        return this.leastPopularStory;
    }
    
     /**
     * Method that takes in the identification number of a story 
     * as a parameter and and returns the story's title using a URL
     * search to find it in the TwitterTrails database
     * @param storyId identification number of the story
     * @return storyTitle title of the story
     */
    public String getStoryTitle(String storyId) {
        String storyTitle = ""; 
        try {
            URL url = new URL(urlFindStoryTitle + storyId);
            Scanner scan = new Scanner(url.openStream());
            scan.nextLine();
            storyTitle = scan.nextLine();
        } catch(IOException e){
            System.out.println(e);  
        }
        return storyTitle;
    }
    
    /**
     * Getter method that returns the screen name of the user who participated 
     * in the most stories
     * @return mostActiveRat the screen name of the most active RAT
     */
    public String getMostActiveRAT() {
        return this.mostActiveRAT;
    }

    /**
     * Method returns the size of the largest connected component
     * in the graph
     * @return largestSize size of the LCC
     */
    public int findLCC() {
        Vector<String> vertices = RATgraph.getAllVertices();
        int largestSize = 0;
        int currentSize;
        LinkedList<String> dfsList = new LinkedList<String>(); 
        for (int i = 0; i < vertices.size(); i++) {
            dfsList = RATgraph.depthFirstSearch((vertices.elementAt(i)));
            currentSize = dfsList.size();
            if (currentSize > largestSize) {
                largestSize = currentSize;
            }
        }
        return largestSize;
    }

    /**
     * Method returns the size of the smallest connected component
     * in the graph
     * @return smallestSize size of the SCC
     */
    public int findSCC() {
        Vector<String> vertices = RATgraph.getAllVertices();
        int smallestSize = Integer.MAX_VALUE;
        int currentSize;
        for (int i = 0; i < vertices.size(); i++) {        
           LinkedList<String> bfsList = RATgraph.breadthFirstSearch((vertices.elementAt(i)));
            currentSize = bfsList.size();
            if (currentSize < smallestSize) {
                smallestSize = currentSize;
            }
        }
        return smallestSize;
    }

    /**
     * Method returns true if the graph is completely connected.
     * @return true if the graph is completely connected
     */
    public boolean isConnected() {
        return this.findLCC() == RATgraph.getNumVertices();
    }

    /**
     * main method for testing
     */ 
    public static void main(String[] args){
        //create new collection of RATs using given csv file
        Rats rats = new Rats("All_Russian-Accounts-in-TT-stories.csv");
        //save collection of RATs to a .tgf
        rats.getGraph().saveToTGF("RATgraph.tgf");
        //print general information regarding the Russian data
        System.out.println("Total Users: " + rats.getAllUsers());
        System.out.println("Total Stories: " + rats.getAllStories());
        System.out.println("Total Tweets: " + rats.getAllTweets());
        //determine most active RAT
        System.out.println("Most Active RAT: "+ rats.getMostActiveRAT());
        //find most/least popular stories
        rats.popularity();
        String mostPopular = rats.getMostPopStory();
        String leastPopular = rats.getLeastPopStory();
        System.out.println("Most Popular Story: " + rats.getMostPopStory() + " " + rats.getStoryTitle(mostPopular));
        System.out.println("Least Popular Story: " + rats.getLeastPopStory() + " " + rats.getStoryTitle(leastPopular));
        //find LCC and SCC
        System.out.println("LCC size: " + rats.findLCC());
        System.out.println("SCC size: " + rats.findSCC());
        //find total vertices and see if graph is completely connected
        System.out.println("total vertices: " + rats.getGraph().getNumVertices());
        System.out.println("Is this graph completely connected? " + rats.isConnected());

    }
}

