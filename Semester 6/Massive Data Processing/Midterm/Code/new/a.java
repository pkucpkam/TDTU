import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class A PrioriPass1 {
    // Mapper
    public static class Pass1Mapper extends Mapper<Object, Text, Text, Text> {
        @Override
        protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString().trim();
            if (!line.contains("CustomerID")) { 
                String[] fields = line.split(",");
                if (fields.length >= 2) {
                    String customerID = fields[0].trim();
                    String date = fields[1].trim();
                    context.write(new Text(customerID), new Text(date));
                }
            }
        }
    }

    // Reducer
    public static class Pass1Reducer extends Reducer<Text, Text, Text, Text> {
        private static final int SUPPORT_THRESHOLD = 2;

        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            Set<String> uniqueDates = new HashSet<>();
            for (Text date : values) {
                uniqueDates.add(date.toString());
            }
            int frequency = uniqueDates.size();
            if (frequency >= SUPPORT_THRESHOLD) {
                context.write(key, new Text(String.valueOf(frequency)));
            }
        }
    }
}