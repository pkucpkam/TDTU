import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Pass2Reducer extends Reducer<Text, Text, Text, IntWritable> {
    private final int SUPPORT_THRESHOLD = 2;
    private Map<String, Integer> pairCounts = new HashMap<>();

    @Override
    protected void reduce(Text date, Iterable<Text> customers, Context context)
            throws IOException, InterruptedException {
        Set<String> customerSet = new HashSet<>();

        // Collect all customers for this date
        for (Text customer : customers) {
            customerSet.add(customer.toString());
        }

        // Generate all possible pairs
        List<String> customerList = new ArrayList<>(customerSet);
        for (int i = 0; i < customerList.size(); i++) {
            for (int j = i + 1; j < customerList.size(); j++) {
                // Sort the pair to ensure consistent ordering (e.g., "A,B" not "B,A")
                String customer1 = customerList.get(i);
                String customer2 = customerList.get(j);
                String pair = customer1.compareTo(customer2) < 0 ? customer1 + "," + customer2
                        : customer2 + "," + customer1;
                pairCounts.put(pair, pairCounts.getOrDefault(pair, 0) + 1);
            }
        }
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        for (Map.Entry<String, Integer> entry : pairCounts.entrySet()) {
            if (entry.getValue() >= SUPPORT_THRESHOLD) {
                context.write(new Text(entry.getKey()), new IntWritable(entry.getValue()));
            }
        }
    }
}