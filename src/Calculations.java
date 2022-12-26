import java.util.ArrayList;

public class Calculations {
    private static ArrayList<Team> teams = new ArrayList<>();
    public static void run() {
        TBAmediator.getTeams();
        TBAmediator.getCurrentRPs();
        CSVmediator.readCSV();
        CSVmediator.assignAvgs();
        TBAmediator.getRemainingMatches();
        printPredictedRankings();
    }

    //getters
    public static ArrayList<Team> getTeams() {
        return teams;
    }

    public static void addTeams(int teamNumber){
        teams.add(new Team(teamNumber));
    }

    //tests team list
    public static void print(){
        String allTeams = "";
        for(Team i : teams){
            allTeams += i.getTeamNumber() + ", ";
        }
        System.out.println(allTeams);
    }

    //Prints predicted rankings
    public static void printPredictedRankings(){
        doSelectionSort(teams);

        String rankedList = "";

        int counter = 1;
        for(Team i : teams){
                rankedList += counter+"."+i.getTeamNumber()+"  "+i.getPredictedRPs()+"\n";
                counter++;
        }
        System.out.println("Rank:  Team Number:  Predicted Rps:");
        System.out.println(rankedList);
    }

    //Sorts teams by predicted RPs
    public static void doSelectionSort(ArrayList<Team> arr) {
        for (int i = 0; i < arr.size(); i++) {
            int pos = i;
            for (int j = i; j < arr.size(); j++) {
                if (arr.get(j).getPredictedRPs() > arr.get(pos).getPredictedRPs()) {
                    pos = j;
                }
            }

            Team min = arr.get(pos);
            arr.set(pos, arr.get(i));
            arr.set(i, min);
        }
    }
}