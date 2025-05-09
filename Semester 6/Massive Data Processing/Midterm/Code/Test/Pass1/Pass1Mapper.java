
import java.util.*;

public class Pass1Mapper {

    public Map<String, Integer> map(List<String> lines) {
        Map<String, Integer> customerCount = new HashMap<>();
        for (String line : lines) {
            if (line.contains("Member_number")) {
                continue; // Bỏ qua dòng header
            }
            String[] fields = line.split(",");
            if (fields.length < 2)
                continue;

            String customer = fields[0]; // Mã khách hàng

            // Tăng tần suất khách hàng
            customerCount.put(customer, customerCount.getOrDefault(customer, 0) + 1);
        }
        return customerCount;
    }
}
