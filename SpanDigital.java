import java.io.*;
import java.util.*;

public class SpanDigital {
    public enum Rules {
        WIN(3),
        DRAW(1),
        LOSS(0);

        final int points;

        Rules(int points) {
            this.points = points;
        }
    }

    public static <K, V extends Comparable<V>> Map<K, V> valueSort(final Map<K, V> map) {
        // Static Method with return type Map and
        // extending comparator class which compares values
        // associated with two keys
        Comparator<K> valueComparator = new Comparator<K>() {

            // return comparison results of values of
            // two keys
            public int compare(K k1, K k2) {
                int comp = map.get(k1).compareTo(
                        map.get(k2));
                if (comp == 0)
                    return 1;
                else
                    return comp;
            }

        };

        // SortedMap created using the comparator
        Map<K, V> sorted = new TreeMap<K, V>(valueComparator);

        sorted.putAll(map);

        return sorted;
    }

    public static void getTeamScore(String line, TreeMap<String, Integer> hmap) {
        String arr[] = line.split(", ");
        // String arr[] = scores.split("[^a-zA-Z]");
        String team1 = arr[0];
        String team2 = arr[1];
        // System.out.println(team1 + " " + team2);
        String team1Array[] = team1.split(" ");
        String team2Array[] = team2.split(" ");
        String homeTeam = team1Array[0];
        String awayTeam = team2Array[0];
        // int points = 0;
        int score1 = Integer.parseInt(team1Array[team1Array.length - 1]);
        // System.out.println(score1);
        int score2 = Integer.parseInt(team2Array[team2Array.length - 1]);
        // System.out.println(score2);
        if (score1 > score2) {
            if (hmap.containsKey(homeTeam)) {
                hmap.put(homeTeam, hmap.get(homeTeam) + Rules.WIN.points);
            } else {
                hmap.put(homeTeam, 3);
            }
            if (!hmap.containsKey(awayTeam)) {
                hmap.put(awayTeam, 0);
            }
        } else if (score1 < score2) {
            if (hmap.containsKey(awayTeam)) {
                hmap.put(homeTeam, hmap.get(awayTeam) + Rules.WIN.points);
            } else {
                hmap.put(awayTeam, 3);
            }
            if (!hmap.containsKey(homeTeam)) {
                hmap.put(homeTeam, 0);
            }

        } else {
            if (hmap.containsKey(awayTeam)) {
                hmap.put(awayTeam, hmap.get(awayTeam) + Rules.DRAW.points);
            } else {
                hmap.put(awayTeam, 1);
            }

            if (hmap.containsKey(homeTeam)) {
                hmap.put(homeTeam, hmap.get(homeTeam) + Rules.DRAW.points);
            } else {
                hmap.put(homeTeam, 1);
            }
        }

    }

    public static void main(String[] args) {
        // System.out.println("===TESTING===");
        TreeMap<String, Integer> hmap = new TreeMap<String, Integer>(Collections.reverseOrder());
        ArrayList<String> arr = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("sample_input.txt"));
            String line;
            String[] lines;
            String team1, team2;
            while ((line = reader.readLine()) != null) {
                getTeamScore(line, hmap);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        Map sortedMap = valueSort(hmap);
        // System.out.println(sortedMap);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("expected_output.txt"));
            // int lineCount = 1;
            ArrayList<String> keys = new ArrayList<String>(sortedMap.keySet());
            // System.out.println(keys);
            // System.out.println(sortedMap);
            int count = 1;
            for (int k = keys.size() - 1; k >= 0; k--) {
                // System.out.println(hmap.get(keys.get(k)));
                writer.write(count + "." + " " + keys.get(k) + ", ");
                if (hmap.get(keys.get(k)) == 1) {
                    writer.write(hmap.get(keys.get(k)) + " pt");
                } else {
                    writer.write(hmap.get(keys.get(k)) + " pts");
                }

                writer.write("\n");
                count++;

            }

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("==OPEN expected_output.txt for Results==");

    }
}
