
public class BankAccount implements Payment, Transfer {
    private int soTK;
    private double tiLeLaiSuat;
    private double soDuTK;

    public BankAccount(int soTK, double tiLeLaiSuat) {
        this.soTK = soTK;
        this.tiLeLaiSuat = tiLeLaiSuat;
        this.soDuTK = 50;
    }

    public BankAccount(int soTK, double tiLeLaiSuat, double soDuTK) {
        this.soTK = soTK;
        this.tiLeLaiSuat = tiLeLaiSuat;
        this.soDuTK = soDuTK;
    }

    public int getSoTK() {
        return this.soTK;
    }

    public double getLaiSuat() {
        return this.tiLeLaiSuat;
    }

    @Override
    public boolean pay(double amount) {
        if (amount + 50 <= this.soDuTK) {
            this.soDuTK -= amount;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public double checkBalance() {
        return this.soDuTK;
    }

    public void setSoDuTK(double soDuTK) {
        this.soDuTK = soDuTK;
    }

    @Override
    public boolean transfer(double amount, Transfer to) {
        double soTienCK = amount + amount * Transfer.transferFee;
        if (soTienCK + 50 <= checkBalance()) {
            this.soDuTK -= soTienCK;
            // nhan tien
            if (to instanceof EWallet) {
                EWallet trungGian = (EWallet) to;
                double tien = trungGian.checkBalance();
                trungGian.setSoDuTK(amount + tien);
            } else if (to instanceof BankAccount) {
                BankAccount trungGian = (BankAccount) to;
                trungGian.setSoDuTK(trungGian.checkBalance() + amount);
            }
            return true;
        } else {
            return false;
        }
    }

    public void topUp(double tien) {
        this.soDuTK += tien;
    }

    public String toString() {
        return this.soTK + "," + this.tiLeLaiSuat + "," + this.soDuTK;
    }
}
