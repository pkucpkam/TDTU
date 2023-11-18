import java.io.*;
import java.util.ArrayList;

public class Test {
    public static <E> boolean writeFile(String path, ArrayList<E> list) {
        try {
            PrintWriter pw = new PrintWriter(path);
            for(E item : list) {
                pw.println(item);
            }
            pw.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        // Create folder output to contain the files of result.
        File f = new File(".\\output");
        if(!f.exists()) {
            f.mkdirs();
        }

        // Create TransactionProcessing object to test Requirement 3, 4, 5
        TransactionProcessing tp = new TransactionProcessing("data\\IDCard.txt", "data\\PaymentInformation.txt");
        int option = 0;
        if(args.length == 1) {
            option = Integer.parseInt(args[0]);
        } else {
            System.out.println("Incorrect argument");
        }

        switch(option) {
            case 1:
                ArrayList<Payment> req3 = tp.getPaymentObject();
                writeFile("output\\Req3.txt", req3);
                break;
            case 2:
                ArrayList<ConvenientCard> req4 = tp.getAdultConvenientCards();
                writeFile("output\\Req4.txt",req4);
                break;
            case 3:
                ArrayList<IDCard> req5 = tp.getCustomersHaveBoth();
                writeFile("output\\Req5.txt",req5);
                break;
            case 4:
                TransactionProcessing tp1 = new TransactionProcessing("data\\IDCard.txt", "data\\PaymentInformation.txt");
                tp1.processTopUp("data\\TopUpHistory.txt");
                ArrayList<Payment> req6 = tp1.getPaymentObject();
                writeFile("output\\Req6.txt", req6);
                break;
            case 5:
                TransactionProcessing tp2 = new TransactionProcessing("data\\IDCard.txt", "data\\PaymentInformation.txt");
                tp2.processTopUp("data\\TopUpHistory.txt");
                ArrayList<Bill> req7 = tp2.getUnsuccessfulTransactions("data\\Bill.txt");
                writeFile("output\\Req7.txt", req7);
                break;
            case 6:
                TransactionProcessing tp3 = new TransactionProcessing("data\\IDCard.txt", "data\\PaymentInformation.txt");
                tp3.processTopUp("data\\TopUpHistory.txt");
                ArrayList<BankAccount> req8 = tp3.getLargestPaymentByBA("data\\Bill.txt");
                writeFile("output\\Req8.txt", req8);
                break;
            case 7:
                TransactionProcessing tp4 = new TransactionProcessing("data\\IDCard.txt", "data\\PaymentInformation.txt");
                tp4.processTopUp("data\\TopUpHistory.txt");
                tp4.processTransactionWithDiscount("data\\Bill.txt");
                ArrayList<Payment> req9 = tp4.getPaymentObject();
                writeFile("output\\Req9.txt", req9);
                break;
            case 0:
                break;
            default:
                System.out.println("Your option do not exist");
        }
    }
}