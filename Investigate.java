
/**
 * Driver class creates a collection of rats from the given
 * csv file and runs methods from Rats class to investigate
 * data from the Russian accounts csv file and depicted graph.
 * 
 * @author mpapagel
 * @version 05/15/20
 */
public class Investigate
{
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
