public class Match {
    private Team[] blueAlliance;
    private Team[] redAlliance;

    public Match(Team[] blueAlliance, Team[] redAlliance) {
        this.blueAlliance = blueAlliance;
        this.redAlliance = redAlliance;
    }

    public void assignRps() {
        int redAllianceRps = 0;
        int blueAllianceRps = 0;

        if(calcWinner() == 2){
            redAllianceRps += 2;
        }
        else if (calcWinner() == 1) {
            blueAllianceRps += 2;
        }
        else{
            redAllianceRps++;
            blueAllianceRps++;
        }

        //Assigning Climb Rps
        if(calcClimbingRp(blueAlliance)){
            blueAllianceRps++;
        }

        if(calcClimbingRp(redAlliance)){
            redAllianceRps++;
        }

        //Assigning Cargo RP
        if(calcCargoRP(blueAlliance)){
            blueAllianceRps++;
        }
        if(calcCargoRP(redAlliance)){
            redAllianceRps++;
        }

        //Assign RPs to Teams
        for(Team i : blueAlliance){
            i.addRps(blueAllianceRps);
        }
        for(Team i : redAlliance){
            i.addRps(redAllianceRps);
        }
    }

    //Needs to be rewritten for tie
    //returns true if red alliance wins
    public int calcWinner() {
        double blueAllianceScore = 0;
        double blueAllianceAutoCargo = 0;
        double redAllianceScore = 0;
        double redAllianceAutoCargo = 0;

        for (Team i : blueAlliance) {
            blueAllianceScore += i.getAvgTeleopCargo() * 2 + i.getAvgClimb();
            blueAllianceAutoCargo += i.getAvgAutoCargo();
        }
        if (blueAllianceAutoCargo > 8) {
            blueAllianceScore += 8 * 4;
        } else {
            blueAllianceScore += blueAllianceAutoCargo * 4;
        }

        for (Team i : redAlliance) {
            redAllianceScore += i.getAvgTeleopCargo() * 2 + i.getAvgClimb();
            redAllianceAutoCargo += i.getAvgAutoCargo();
        }
        if (redAllianceAutoCargo > 8) {
            redAllianceScore += 8 * 4;
        } else {
            redAllianceScore += redAllianceAutoCargo * 4;
        }

        if(blueAllianceScore > redAllianceScore){
            return 1;
        }
        else if (redAllianceScore > blueAllianceScore){
            return 2;
        }
        return 0;
    }

    //returns true if alliance gets climbing RP
    public boolean calcClimbingRp(Team[] alliance){
        double allianceClimbingPoints = 0;
        for (Team i: alliance){
            allianceClimbingPoints += i.getAvgClimb();
        }

        if (allianceClimbingPoints >= 16){
            return true;
        }
        return false;
    }

    //return true if alliance gets Cargo RP
    public boolean calcCargoRP(Team[] alliance){
        double allianceTotalCargo = 0;
        double allianceAutoCargo = 0;
        for(Team i : alliance){
            allianceTotalCargo += i.getAvgTeleopCargo();
            allianceAutoCargo += i.getAvgAutoCargo();
        }

        //calc Auto cargo w/ auto cap
        if (allianceAutoCargo > 8) {
            allianceTotalCargo += 8;
        } else {
            allianceTotalCargo += allianceAutoCargo;
        }

        if (allianceTotalCargo >= 20){
            return true;
        }
        return false;
    }
}