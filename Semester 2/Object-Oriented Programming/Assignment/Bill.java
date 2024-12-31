//Dont modify this file

public class Bill {
    private int billID;
    private double total;
    private String payFor;

    public Bill(int billID, double total, String payFor) {
        this.billID = billID;
        this.total = total;
        this.payFor = payFor;
    }

    public int getBillID() {
        return billID;
    }

    public double getTotal() {
        return total;
    }

    public String getPayFor() {
        return payFor;
    }

    @Override
    public String toString() {
        return billID + "," + total + "," + payFor;
    }
}
