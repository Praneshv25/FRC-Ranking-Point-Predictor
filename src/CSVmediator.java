import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class CSVmediator {
    private static List<List<String>> lines = new ArrayList();


    public static void readCSV() {
        String file = "/Users/PV/Downloads/Data Formatted w:;.txt";
        String delimiter = ";";
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(file))){
            while((line = br.readLine()) != null) {
                List<String> values = Arrays.asList(line.split(delimiter));
                lines.add(values);
            }
        } catch (Exception e){

        }
    }

    public static void assignAvgs() {
        for (int i = 1; i < lines.size(); i++){
            for(Team x : Calculations.getTeams()){
                if(Integer.parseInt(lines.get(i).get(0)) == x.getTeamNumber()){
                    x.setAvgTeleopCargo(Double.parseDouble(lines.get(i).get(3)));
                    x.setAvgClimb(Double.parseDouble(lines.get(i).get(4)));
                }
            }
        }
    }
}
