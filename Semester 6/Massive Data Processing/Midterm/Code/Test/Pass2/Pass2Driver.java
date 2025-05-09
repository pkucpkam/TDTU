import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Pass2Driver {
    public static void main(String[] args) throws Exception {
        String inputPath = "../baskets.csv"; // Đường dẫn tệp đầu vào
        String frequentCustomersPath = "../output1/pass1_result.txt";
        String outputPath = "../output2/pass2_result.txt";

        Pass2Mapper mapper = new Pass2Mapper(frequentCustomersPath);
        Pass2Reducer reducer = new Pass2Reducer();

        // Đọc tệp dữ liệu đầu vào
        List<String> lines = Files.readAllLines(Paths.get(inputPath));

        // Bước 1: Nhóm khách hàng theo ngày
        Map<String, Set<String>> customersByDate = mapper.map(lines);

        // Bước 2: Tạo cặp và đếm tần suất
        Map<String, Integer> pairCounts = mapper.generatePairs(customersByDate);

        // Bước 3: Lọc và ghi kết quả
        reducer.reduce(pairCounts, outputPath);

        System.out.println("Pass 2 completed. Results written to " + outputPath);
    }
}