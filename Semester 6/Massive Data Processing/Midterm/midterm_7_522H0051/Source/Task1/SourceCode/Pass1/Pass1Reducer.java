import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;

public class Pass1Reducer extends Reducer<Text, IntWritable, Text, IntWritable> {
    private final int SUPPORT_THRESHOLD = 2;

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context)
            throws IOException, InterruptedException {
        int sum = 0;
        for (IntWritable val : values) {
            sum += val.get();
        }

        if (sum >= SUPPORT_THRESHOLD) {
            context.write(key, new IntWritable(sum));
        }
    }
}