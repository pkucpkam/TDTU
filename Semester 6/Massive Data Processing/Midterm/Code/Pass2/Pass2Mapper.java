import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

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
                frequentCustomers.add(parts[0]); // ✅ Lưu khách hàng phổ biến vào tập hợp
            }
        }
        reader.close();
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] fields = value.toString().split(",");
        if (fields.length < 2)
            return;

        String customer = fields[0];
        String date = fields[1];

        // ✅ Chỉ xử lý nếu khách hàng nằm trong tập phổ biến
        if (frequentCustomers.contains(customer)) {
            for (String other : frequentCustomers) {
                if (!customer.equals(other)) {
                    // ✅ Tạo cặp và xuất ra key-value
                    String pair = customer + "," + other;
                    context.write(new Text(pair), one);
                }
            }
        }
    }
}