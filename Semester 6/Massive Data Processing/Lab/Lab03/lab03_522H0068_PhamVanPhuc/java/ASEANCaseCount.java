import java.io.IOException;

import javax.naming.Context;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class ASEANCaseCount {

    // Mapper Class
    public static class ASEANMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

        private Text region = new Text();
        private IntWritable caseCount = new IntWritable();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            String[] fields = line.split("\t");

            if (fields.length >= 3) {
                String regionName = fields[1];
                String casesStr = fields[2];

                if (regionName.equalsIgnoreCase("South-East Asia")) {
                    try {
                        int cases = (int) Double.parseDouble(casesStr.replaceAll(",", ""));
                        caseCount.set(cases);

                        region.set("South-East Asia");
                        context.write(region, caseCount);

                    } catch (NumberFormatException e) {
                        System.err.println("Lỗi parse số: " + casesStr + " tại dòng: " + line);
                    }
                }
            }
        }
    }

    // Reducer Class
    public static class ASEANReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context)
                throws IOException, InterruptedException {
            int totalCases = 0;

            for (IntWritable val : values) {
                totalCases += val.get();
            }

            context.write(key, new IntWritable(totalCases));
        }
    }

    // Main Method
    public static void main(String[] args) throws Exception {

        if (args.length != 2) {
            System.err.println("Usage: ASEANCaseCount <input path> <output path>");
            System.exit(-1);
        }

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "ASEAN COVID Case Count");

        job.setJarByClass(ASEANCaseCount.class);
        job.setMapperClass(ASEANMapper.class);
        job.setReducerClass(ASEANReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
