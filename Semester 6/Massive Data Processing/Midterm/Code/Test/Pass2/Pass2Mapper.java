import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Pass2Mapper {
    private Set<String> frequentCustomers;

    public Pass2Mapper(String frequentCustomersPath) throws IOException {
        this.frequentCustomers = new HashSet<>();
        List<String> lines = Files.readAllLines(Paths.get(frequentCustomersPath));
        for (String line : lines) {
            String[] parts = line.split("\t");
            if (parts.length == 2) {
                frequentCustomers.add(parts[0]);
            }
        }
    }

    public Map<String, Set<String>> map(List<String> lines) {
        // Nhóm khách hàng theo ngày
        Map<String, Set<String>> customersByDate = new HashMap<>();

        for (String line : lines) {
            if (line.contains("Member_number")) {
                continue; // Bỏ qua dòng header
            }

            String[] fields = line.split(",");
            if (fields.length < 2)
                continue;

            String customer = fields[0];
            String date = fields[1];

            if (frequentCustomers.contains(customer)) {
                // Thêm khách hàng vào tập hợp của ngày tương ứng
                customersByDate.computeIfAbsent(date, k -> new HashSet<>()).add(customer);
            }
        }

        return customersByDate;
    }

    // Tạo cặp từ danh sách khách hàng trong cùng ngày
    public Map<String, Integer> generatePairs(Map<String, Set<String>> customersByDate) {
        Map<String, Integer> pairCounts = new HashMap<>();

        for (Set<String> customers : customersByDate.values()) {
            List<String> customerList = new ArrayList<>(customers);
            for (int i = 0; i < customerList.size(); i++) {
                for (int j = i + 1; j < customerList.size(); j++) {
                    String customer1 = customerList.get(i);
                    String customer2 = customerList.get(j);
                    String pair = customer1.compareTo(customer2) < 0 ? customer1 + "," + customer2
                            : customer2 + "," + customer1;
                    pairCounts.put(pair, pairCounts.getOrDefault(pair, 0) + 1);
                }
            }
        }

        return pairCounts;
    }
}