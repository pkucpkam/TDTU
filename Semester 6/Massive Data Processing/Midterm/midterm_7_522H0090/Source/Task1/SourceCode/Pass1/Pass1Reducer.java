import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Pass1Reducer extends Reducer<Text, IntWritable, Text, IntWritable> {
    private final int SUPPORT_THRESHOLD = 2;
    
    // Lưu trữ các ngày khác nhau của từng khách hàng
    private Map<String, Set<String>> customerDates = new HashMap<>();

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context)
            throws IOException, InterruptedException {
        // Tách key thành customer và date
        String[] parts = key.toString().split(",");
        if (parts.length != 2)
            return;

        String customer = parts[0];  // Mã khách hàng
        String date = parts[1];      // Ngày giao dịch

        // Thêm ngày vào danh sách của khách hàng trong Map
        customerDates.computeIfAbsent(customer, k -> new HashSet<>()).add(date);
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        // Ghi kết quả
        for (Map.Entry<String, Set<String>> entry : customerDates.entrySet()) {
            // Số ngày khác nhau
            int count = entry.getValue().size();
            
            // Nếu số ngày >= SUPPORT_THRESHOLD, xuất ra kết quả
            if (count >= SUPPORT_THRESHOLD) {
                context.write(new Text(entry.getKey()), new IntWritable(count));
            }
        }
    }
}
