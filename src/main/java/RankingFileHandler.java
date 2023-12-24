
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.io.File;

public class RankingFileHandler {
    private static final String FILE_NAME = "ranking.txt";
    private static final Integer TOP_PLAYER_COUNT = 10;

    public static ArrayList<Player> readRanking() {
        ArrayList<Player> ranking = new ArrayList<>();
        try {
            File file = new File(FILE_NAME);
            if (file.exists()) {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split(";");
                    ranking.add(new Player(parts[0], Integer.parseInt(parts[1])));
                }
                scanner.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Collections.sort(ranking, new Comparator<Player>(){
            public int compare(Player p1, Player p2)
            {
                return p2.getScore() - p1.getScore();
            }
            });
        return ranking;
    }
    
    public static void writeRanking(String name, Integer pontos) {
        Player newPlayerScore = new Player(name, pontos);
        ArrayList<Player> ranking = readRanking();
        ranking.add(newPlayerScore);

        Collections.sort(ranking, new Comparator<Player>(){
            public int compare(Player p1, Player p2)
            {
               return p2.getScore() - p1.getScore();
            }
          });

          List<Player> topPlayers = ranking.stream().limit(TOP_PLAYER_COUNT).collect(Collectors.toList());
          try {
            PrintWriter writer = new PrintWriter(FILE_NAME, "UTF-8");

            for (Player player : topPlayers) {
                writer.println(player.getName() + ";" + player.getScore());
            }

            writer.close();
        } catch (Exception e) {
            System.out.println("Error writing to file");
        }
    }

}
