public class TestReq2 {
    public static void main(String[] args) {
        EWallet ew = new EWallet(8365214);
        EWallet ew1 = new EWallet(8365216);
        ew.topUp(500);
        System.out.println("EWallet 1: " + ew.checkBalance());
        System.out.println("EWallet 2: " + ew1.checkBalance());
        ew.transfer(200, ew1);
        System.out.println("------------------");
        System.out.println("EWallet 1: " + ew.checkBalance());
        System.out.println("EWallet 2: " + ew1.checkBalance());
        System.out.println("------------------");
        BankAccount ba = new BankAccount(1000023, 0.02);
        System.out.println("BankAccount 1: " + ba.checkBalance());
        ew.transfer(200, ba);
        System.out.println("------------------");
        System.out.println("EWallet 1: " + ew.checkBalance());
        System.out.println("BankAccount 1: " + ba.checkBalance());
        System.out.println("------------------");
        ba.transfer(200, ew1);
        System.out.println("BankAccount 1: " + ba.checkBalance());
        System.out.println("EWallet 2: " + ew1.checkBalance());
    }
}
