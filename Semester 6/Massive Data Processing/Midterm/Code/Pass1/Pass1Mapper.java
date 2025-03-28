import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;
import java.io.IOException;

public class Pass1Mapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    private final static IntWritable one = new IntWritable(1);

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // Bỏ qua dòng header
        if (key.get() == 0 && value.toString().contains("Member_number")) {
            return;
        }

        String[] fields = value.toString().split(",");
        if (fields.length < 2)
            return;

        String customer = fields[0]; // Mã khách hàng
        String date = fields[1]; // Ngày mua sắm

        // Xuất ra cặp <customer, 1>
        context.write(new Text(customer), one);
    }
}