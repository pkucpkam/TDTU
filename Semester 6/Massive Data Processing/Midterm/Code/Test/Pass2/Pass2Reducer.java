import java.util.*;
import java.io.*;

public class Pass2Reducer {
    private final int SUPPORT_THRESHOLD = 2;

    public void reduce(Map<String, Integer> pairCounts, String outputPath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath));

        for (Map.Entry<String, Integer> entry : pairCounts.entrySet()) {
            if (entry.getValue() >= SUPPORT_THRESHOLD) {
                writer.write(entry.getKey() + "\t" + entry.getValue());
                writer.newLine();
            }
        }

        writer.close();
    }
}