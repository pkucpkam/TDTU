import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Pass1Driver {
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: Pass1Driver <input path> <output path>");
            System.exit(-1);
        }

        // Tạo đối tượng cấu hình Hadoop
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Apriori Pass 1");

        // Thiết lập class chính cho job
        job.setJarByClass(Pass1Driver.class);

        // Thiết lập các lớp Mapper và Reducer
        job.setMapperClass(Pass1Mapper.class);
        job.setReducerClass(Pass1Reducer.class);

        // Thiết lập loại key-value đầu ra của Mapper
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class); // Sửa từ NullWritable sang IntWritable

        // Thiết lập loại key-value đầu ra của job
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        // Đọc dữ liệu từ input path và ghi kết quả vào output path
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        // Chạy job và thoát
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
