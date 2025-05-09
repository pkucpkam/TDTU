import java.util.*;
import java.io.*;

public class Pass1Reducer {
    private final int SUPPORT_THRESHOLD = 2; // Ngưỡng hỗ trợ

    public void reduce(Map<String, Integer> customerCount, String outputPath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath));

        for (Map.Entry<String, Integer> entry : customerCount.entrySet()) {
            if (entry.getValue() >= SUPPORT_THRESHOLD) {
                writer.write(entry.getKey() + "\t" + entry.getValue());
                writer.newLine();
            }
        }

        writer.close();
    }
}
