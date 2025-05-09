import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class Pass2Mapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    private final static IntWritable one = new IntWritable(1);
    private Set<String> frequentCustomers = new HashSet<>();

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        Path path = new Path("/user/midterm/output1/part-r-00000");
        FileSystem fs = FileSystem.get(context.getConfiguration());
        FSDataInputStream inputStream = fs.open(path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("\t");
            if (parts.length == 2) {
                frequentCustomers.add(parts[0]);
            }
        }
        reader.close();
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        if (key.get() == 0 && value.toString().contains("Member_number")) {
            return;
        }

        String[] fields = value.toString().split(",");
        if (fields.length < 2)
            return;

        String customer = fields[0];
        String date = fields[1];

        if (frequentCustomers.contains(customer)) {
            context.write(new Text(date), new Text(customer));
        }
    }
}