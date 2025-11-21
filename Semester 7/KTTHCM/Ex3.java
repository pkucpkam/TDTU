public class Ex3 {
    public static double sum (int n ) {
        double sum = 0;
        double powerOf = 1.0;
        for (int i = 1; i <= n; i++ ) {
            powerOf *= 2;
            sum = sum + (powerOf/(i+1));
        }
        return sum;
    }

    public static void string1(String s) {
        

        String arr[] = s.split(" ");

        String d = arr[0];
        String n = arr[0];
        
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].length() > d.length()) {
                d = arr[i];
            }
            if (arr[i].length() <= n.length()) {
                n = arr[i];
            }
        }

        String result = d + " " + n;
        System.out.println(result);
    }

    public static void main(String[] args) {
        string1("co thanh lap trinh do");
    }
}
