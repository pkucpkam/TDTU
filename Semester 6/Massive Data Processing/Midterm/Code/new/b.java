import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class A PrioriPass2 {
    // Mapper
    public static class Pass2Mapper extends Mapper<Object, Text, Text, Text> {
        private Set<String> frequentCustomers = new HashSet<>();

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            // Giả sử danh sách từ Pass 1: 1381, 1450 (trong thực tế, đọc từ file đầu ra của Pass 1)
            frequentCustomers.add("1381");
            frequentCustomers.add("1450");
        }

        @Override
        protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString().trim();
            if (!line.contains("CustomerID")) {
                String[] fields = line.split(",");
                if (fields.length >= 2) {
                    String customerID = fields[0].trim();
                    String date = fields[1].trim();
                    if (frequentCustomers.contains(customerID)) {
                        context.write(new Text(date), new Text(customerID));
                    }
                }
            }
        }
    }

    // Reducer
    public static class Pass2Reducer extends Reducer<Text, Text, Text, Text> {
        private static final int SUPPORT_THRESHOLD = 2;

        @Override
        protected void reduce(Text dateKey, Iterable<Text> customerIDs, Context context) throws IOException, InterruptedException {
            List<String> customers = new ArrayList<>();
            for (Text customer : customerIDs) {
                customers.add(customer.toString());
            }
            // Tạo tất cả các cặp từ danh sách khách hàng
            for (int i = 0; i < customers.size(); i++) {
                for (int j = i + 1; j < customers.size(); j++) {
                    String customer1 = customers.get(i);
                    String customer2 = customers.get(j);
                    // Đảm bảo thứ tự để tránh trùng lặp
                    String pair = customer1.compareTo(customer2) < 0 ? customer1 + "," + customer2 : customer2 + "," + customer1;
                    context.write(new Text(pair), new Text("1"));
                }
            }
        }
    }
}