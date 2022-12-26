public class Team {

    private int teamNumber;
    private int currentRPs;
    private int predictedRPs;
    private int matchesPlayed;
    private double avgAutoCargo;
    private double avgTeleopCargo;
    private double avgClimb;

    public Team(int teamNumber){
        this.teamNumber = teamNumber;
        currentRPs = 0;
        predictedRPs = 0;
        matchesPlayed = 0;
        avgAutoCargo = 0;
        avgTeleopCargo = 0;
        avgClimb = 0;
    }

    //Getters
    public double getAvgClimb() {return avgClimb;}
    public double getAvgAutoCargo() {return avgAutoCargo;}
    public double getAvgTeleopCargo() {return avgTeleopCargo;}
    public int getTeamNumber() { return teamNumber;}
    public int getCurrentRPs(){ return currentRPs;}
    public int getPredictedRPs() {return predictedRPs;}
    public int getMatchesPlayed() {return matchesPlayed;}
    public double getRankingScore() {
        return currentRPs / matchesPlayed;
    }

    //Setters
    public void setAvgClimb(double avgClimb) {this.avgClimb = avgClimb;}
    public void setAvgAutoCargo(double avgAutoCargo) {this.avgAutoCargo = avgAutoCargo;}
    public void setAvgTeleopCargo(double avgTeleopCargo) {this.avgTeleopCargo = avgTeleopCargo;}

    //Initalizes current rps and predicted rps to current rps
    public void setCurrentRps(int currentRPs){
        this.currentRPs = currentRPs;
        this.predictedRPs = currentRPs;
    }

    //Adds predicted Rps from matches to total predicted RPs for team
    public void addRps(int predictedRPs){
        this.predictedRPs += predictedRPs;
    }

}