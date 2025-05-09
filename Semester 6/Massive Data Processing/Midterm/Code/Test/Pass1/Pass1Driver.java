import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Pass1Driver {
    public static void main(String[] args) throws Exception {
        String inputPath = "../baskets.csv"; // Đường dẫn tệp đầu vào
        String outputPath = "../output1/pass1_result.txt";

        Pass1Mapper mapper = new Pass1Mapper();
        Pass1Reducer reducer = new Pass1Reducer();

        // Đọc tệp dữ liệu đầu vào
        List<String> lines = Files.readAllLines(Paths.get(inputPath));
        Map<String, Integer> customerCount = mapper.map(lines);

        // Chạy reducer để lọc ra các khách hàng có tần suất >= ngưỡng
        reducer.reduce(customerCount, outputPath);
    }
}
