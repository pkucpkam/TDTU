
public class Main {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Invalid expression");
            return;
        }

        try {
            double num1 = Double.parseDouble(args[0]);
            double num2 = Double.parseDouble(args[2]);
            String operator = args[1];

            double result;
            switch (operator) {
                case "+":
                    result = num1 + num2;
                    break;
                case "-":
                    result = num1 - num2;
                    break;
                case "/":
                    result = num1 / num2;
                    break;
                case "x":
                    result = num1 * num2;
                    break;
                case "^":
                    result = Math.pow(num1, num2);
                    break;
                default:
                    System.out.println("Unsupported operator");
                    return;
            }

            System.out.println(result);
        } catch (NumberFormatException e) {
            System.out.println("Invalid expression");
        }
    }
}

