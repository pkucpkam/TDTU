import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Pass1Reducer extends Reducer<Text, Text, Text, IntWritable> {
    private final int SUPPORT_THRESHOLD = 2;

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context)
            throws IOException, InterruptedException {
        Set<String> uniqueDates = new HashSet<>();

        for (Text val : values) {
            uniqueDates.add(val.toString());
        }

        if (uniqueDates.size() >= SUPPORT_THRESHOLD) {
            context.write(key, new IntWritable(uniqueDates.size()));
        }
    }
}
