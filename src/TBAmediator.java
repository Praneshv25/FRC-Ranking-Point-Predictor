import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
public class TBAmediator {
    private static final String TBA_BASE_URL = "https://www.thebluealliance.com/api/v3/event/2023cave";
    private static final String HEADER_AUTH = "X-TBA-Auth-Key";
    private static StringBuilder response = new StringBuilder();

    public static void getDataTBA(String request) throws IOException {
        URL url;
        url = new URL(TBA_BASE_URL + "/" + request);
        System.out.println(url);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.addRequestProperty(HEADER_AUTH, ""); //Removed auth-key
        getData(con);
    }

    private static void getData(HttpURLConnection con) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

        } catch (IOException ex) {
            System.out.println("l");
        }
    }

    public static void getTeams() {
        try {
            TBAmediator.getDataTBA("teams");
            while (response.indexOf("team_number") != -1) {
                int x = 0;
                int index4 = response.toString().indexOf("team_number") + 11;
                while (!response.substring(index4 + x, (index4 + x + 1)).equals(",")) {
                    x++;
                }
                Calculations.addTeams(Integer.parseInt(response.substring(index4 + 3, index4 + x)));
                response = new StringBuilder(response.substring(index4 + 5));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void getCurrentRPs() {
        try {
            TBAmediator.getDataTBA("rankings");
            while (response.indexOf("team_key") != -1) {
                //finds team num
                int x = 0;
                int index = response.toString().indexOf("team_key") + 15;
                while (!response.substring(index + x, (index + x + 1)).equals("\"")) {
                    x++;
                }

                //gets current Rp for team num
                int t = 0;
                int indexTwo = response.toString().indexOf("extra_stats\"") + 23;
                while (!response.substring(indexTwo + t, (indexTwo + t + 1)).equals(" ")) {
                    t++;
                }

                for (Team i : Calculations.getTeams()) {
                    if (i.getTeamNumber() == Integer.parseInt(response.substring(index, index + x))) {
                        i.setCurrentRps(Integer.parseInt(response.substring(indexTwo, indexTwo + t)));
                    }
                }
                response = new StringBuilder(response.substring(index + 3));

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Gets remaining matches, creates alliances, assigns predicted rps
    public static void getRemainingMatches() {
        try {
            TBAmediator.getDataTBA("matches");

            //removes playoff matches Could implement at some point (Running at picklist though, so it doesn't matter


            //might have to change, do not know if values are null if match has not started
            //Creates alliances, creates Match, removes calculated data from @var response
            if(response.indexOf("\"actual_time\": null") != -1){
                response = new StringBuilder(response.substring(response.indexOf("\"actual_time\": null")));
            }
            while(response.indexOf("\"actual_time\": null") != -1){
                Team[] blueAlliance = new Team[3];
                Team[] redAlliance = new Team[3];

                for(int i = 0; i < 3; i++){
                    int index = response.indexOf("frc")+3;
                    int endIndex = 0;
                    while (!response.substring(index + endIndex, (index + endIndex + 1)).equals("\"")) {
                        System.out.print(response.substring(index + endIndex, (index + endIndex + 1)));
                        endIndex++;
                    }
                    for(Team team : Calculations.getTeams()){
                        if(team.getTeamNumber() == Integer.parseInt(response.substring(index, index + endIndex))){
                            blueAlliance[i] = team;
                        }
                    }
                    System.out.println();
                    response = new StringBuilder(response.substring(index));
                }

                for(int i = 0; i < 3; i++){
                    int index = response.indexOf("frc")+3;
                    int endIndex = 0;
                    while (!response.substring(index + endIndex, (index + endIndex + 1)).equals("\"")) {
                        System.out.print(response.substring(index + endIndex, (index + endIndex + 1)));
                        endIndex++;
                    }
                    for(Team team : Calculations.getTeams()){
                        if(team.getTeamNumber() == Integer.parseInt(response.substring(index, index + endIndex))){
                            redAlliance[i] = team;
                        }
                    }
                    System.out.println();
                    response = new StringBuilder(response.substring(index));
                }

                Match match = new Match(blueAlliance, redAlliance);
                match.assignRps();
                System.out.println();
            }

        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
