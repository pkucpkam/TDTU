import java.util.*;
import java.io.*;

public class TransactionProcessing {
    private ArrayList<Payment> paymentObjects;
    private IDCardManagement idcm;

    public TransactionProcessing(String idCardPath, String paymentPath) {
        idcm = new IDCardManagement(idCardPath);
        readPaymentObject(paymentPath);

    }

    public ArrayList<Payment> getPaymentObject() {
        return this.paymentObjects;
    }

    // Requirement 3
    // Them ConvenientCard vao arraylist paymentObject
    public void addID(String line) {
        ArrayList<IDCard> idCardsList = idcm.getIDCards();
        for (IDCard idc : idCardsList) {
            String idname = Integer.toString(idc.getSoDinhDanh());
            if (line.equals(idname)) {
                try {
                    ConvenientCard cvn = new ConvenientCard(idc);
                    paymentObjects.add(cvn);
                } catch (CannotCreateCard e) {
                    System.out.println(e);
                }
            }
        }
    }

    // them EWallet vao paymentObject
    public void addEW(String line) {
        int sdt = Integer.parseInt(line);
        EWallet ew = new EWallet(sdt);
        paymentObjects.add(ew);
    }

    // them BankAccount vao paymentObject
    public void addBA(String components1, String components2) {
        int acc = Integer.parseInt(components1);
        double lai = Double.parseDouble(components2);
        BankAccount ba = new BankAccount(acc, lai);
        paymentObjects.add(ba);
    }

    public boolean readPaymentObject(String path) {
        paymentObjects = new ArrayList<>();
        try {
            File f = new File(path);
            Scanner sc = new Scanner(f);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.length() == 6) {
                    addID(line);
                } else if (line.length() == 7) {
                    addEW(line);
                } else {
                    String[] components = line.split(",");
                    addBA(components[0], components[1]);
                }
            }
            sc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    // Requirement 4
    public ArrayList<ConvenientCard> getAdultConvenientCards() {
        ArrayList<ConvenientCard> adultCards = new ArrayList<>();
        for (Payment pm : paymentObjects) {
            if (pm instanceof ConvenientCard) {
                ConvenientCard cvn = (ConvenientCard) pm;
                if (cvn.getType().equals("Adult")) {
                    adultCards.add(cvn);
                }
            }
        }
        return adultCards;
    }

    // Requirement 5
    // check EWallet
    public boolean checkEW(int sdt) {
        for (Payment pm : paymentObjects) {
            if (pm instanceof EWallet) {
                EWallet ew = (EWallet) pm;
                int sdt1 = ew.getSoDienThoai();
                if (sdt == sdt1) {
                    return true;
                }
            }
        }
        return false;
    }

    // check BankAccount
    public boolean checkBAcc(int sdd) {
        for (Payment pm : paymentObjects) {
            if (pm instanceof BankAccount) {
                BankAccount bac = (BankAccount) pm;
                int stk = bac.getSoTK();
                if (stk == sdd) {
                    return true;
                }
            }
        }
        return false;
    }

    // check ConvenientCard
    public boolean checkCV(int sdd) {
        for (Payment pm : paymentObjects) {
            if (pm instanceof ConvenientCard) {
                ConvenientCard cvn = (ConvenientCard) pm;
                int sdd1 = cvn.getSoDinhDanh();
                if (sdd == sdd1) {
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<IDCard> getCustomersHaveBoth() {
        ArrayList<IDCard> idcard = new ArrayList<>();
        ArrayList<IDCard> idCardsList = idcm.getIDCards();
        for (IDCard idc : idCardsList) {
            int sdd = idc.getSoDinhDanh();
            int sdt = idc.getSoDienThoai();
            if (checkBAcc(sdd) && checkCV(sdd) && checkEW(sdt)) {
                idcard.add(idc);
            }
        }
        return idcard;
    }

    // Requirement 6
    // nap tien ConvenientCard
    public void napTienCC(int stk, double amount) {
        for (Payment pm : paymentObjects) {
            if (pm instanceof ConvenientCard) {
                ConvenientCard cvn = (ConvenientCard) pm;
                if (cvn.getSoDinhDanh() == stk) {
                    cvn.topUp(amount);
                }
            }
        }
    }

    // nap tien EWallet
    public void napTienEW(int sdt, double amount) {
        for (Payment pm : paymentObjects) {
            if (pm instanceof EWallet) {
                EWallet ew = (EWallet) pm;
                if (ew.getSoDienThoai() == sdt) {
                    ew.topUp(amount);
                }
            }
        }
    }

    // nap tien BankAccount
    public void napTienBA(int stk, double amount) {
        for (Payment pm : paymentObjects) {
            if (pm instanceof BankAccount) {
                BankAccount ba = (BankAccount) pm;
                if (ba.getSoTK() == stk) {
                    ba.topUp(amount);
                }
            }
        }
    }

    public void processTopUp(String path) {
        try {
            File f = new File(path);
            Scanner sc = new Scanner(f);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String components[] = line.split(",");
                int stk = Integer.parseInt(components[1]);
                double amount = Double.parseDouble(components[2]);
                if (components[0].equals("CC")) {
                    napTienCC(stk, amount);
                } else if (components[0].equals("EW")) {
                    napTienEW(stk, amount);
                } else {
                    napTienBA(stk, amount);
                }
            }
            sc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Requirement 7
    // Pay ConvenientCard
    public boolean payCC(int stk, double amount) {
        for (Payment pm : paymentObjects) {
            if (pm instanceof ConvenientCard) {
                ConvenientCard cvn = (ConvenientCard) pm;
                if (cvn.getSoDinhDanh() == stk) {
                    if (cvn.pay(amount)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Pay EWallet
    public boolean payEW(int stk, double amount) {
        for (Payment pm : paymentObjects) {
            if (pm instanceof EWallet) {
                EWallet ew = (EWallet) pm;
                if (ew.getSoDienThoai() == stk) {
                    if (ew.pay(amount)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Pay BankAccount
    public boolean payBA(int stk, double amount) {
        for (Payment pm : paymentObjects) {
            if (pm instanceof BankAccount) {
                BankAccount ba = (BankAccount) pm;
                if (ba.getSoTK() == stk) {
                    if (ba.pay(amount)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public ArrayList<Bill> getUnsuccessfulTransactions(String path) {
        ArrayList<Bill> bills = new ArrayList<>();
        try {
            File f = new File(path);
            Scanner sc = new Scanner(f);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String components[] = line.split(",");
                int billID = Integer.parseInt(components[0]);
                double amount = Double.parseDouble(components[1]);
                String purpose = components[2];
                String pt = components[3];
                int stk = Integer.parseInt(components[4]);
                if (pt.equals("CC")) {
                    if (payCC(stk, amount) == false) {
                        Bill a = new Bill(billID, amount, purpose);
                        bills.add(a);
                    }
                } else if (pt.equals("EW")) {
                    if (payEW(stk, amount) == false) {
                        Bill a = new Bill(billID, amount, purpose);
                        bills.add(a);
                    }
                } else {
                    if (payBA(stk, amount) == false) {
                        Bill a = new Bill(billID, amount, purpose);
                        bills.add(a);
                    }
                }
            }
            sc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bills;
    }

    // Requirement 8
    // tao list cac Bank Account
    public ArrayList<BankAccount> listBankAccounts() {
        ArrayList<BankAccount> listBA = new ArrayList<>();
        for (Payment pm : paymentObjects) {
            if (pm instanceof BankAccount) {
                BankAccount ba = (BankAccount) pm;
                listBA.add(ba);
            }
        }
        return listBA;
    }

    // tim chenh lech nhieu nhat
    public double maxPay(ArrayList<BankAccount> listBA, ArrayList<BankAccount> listBA1) {
        double max = 0;
        double amount;
        for (BankAccount bac : listBA) {
            int stk = bac.getSoTK();
            for (BankAccount bac1 : listBA1) {
                if (bac1.getSoTK() == stk) {
                    amount = bac1.checkBalance() - bac.checkBalance();
                    if (amount > max) {
                        max = amount;
                    }
                }
            }
        }
        return max;
    }

    public ArrayList<BankAccount> getLargestPaymentByBA(String path) {
        ArrayList<BankAccount> largetPA = new ArrayList<>();
        ArrayList<BankAccount> listBA = listBankAccounts();
        ArrayList<BankAccount> listBA1 = new ArrayList<>();
        for (BankAccount ba : listBA) {
            BankAccount baCopy = new BankAccount(ba.getSoTK(), ba.getLaiSuat(), ba.checkBalance());
            listBA1.add(baCopy);
        }

        try {
            File f = new File(path);
            Scanner sc = new Scanner(f);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String components[] = line.split(",");
                double amount = Double.parseDouble(components[1]);
                String pt = components[3];
                int stk = Integer.parseInt(components[4]);
                if (pt.equals("BA")) {
                    for (BankAccount ba : listBA) {
                        if (stk == ba.getSoTK()) {
                            ba.pay(amount);
                        }
                    }
                }
            }
            sc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        double max = maxPay(listBA, listBA1);
        double amount;
        for (BankAccount bac : listBA) {
            int stk = bac.getSoTK();
            for (BankAccount bac1 : listBA1) {
                if (bac1.getSoTK() == stk) {
                    amount = bac1.checkBalance() - bac.checkBalance();
                    if (amount == max) {
                        largetPA.add(bac);
                    }
                }
            }
        }
        return largetPA;
    }

    // Requirement 9
    // check Discount Ewallet
    public boolean checkDiscountEW(int sdt) {
        for (Payment pm : paymentObjects) {
            if (pm instanceof ConvenientCard) {
                ConvenientCard cvn = (ConvenientCard) pm;
                if (sdt == cvn.getSoDienThoai()) {
                    if ((2023 - cvn.getNgaySinh() < 18) && (cvn.getGioiTinh().equals("Female"))) {
                        return true;
                    } else if ((2023 - cvn.getNgaySinh() < 20) && (cvn.getGioiTinh().equals("Male"))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void processTransactionWithDiscount(String path) {
        try {
            File f = new File(path);
            Scanner sc = new Scanner(f);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String components[] = line.split(",");
                double amount = Double.parseDouble(components[1]);
                String purpose = components[2];
                String pt = components[3];
                int stk = Integer.parseInt(components[4]);
                if (pt.equals("EW")) {
                    if (purpose.equals("Clothing") && (amount > 500) && (checkDiscountEW(stk))) {
                        payEW(stk, amount * 0.85);
                    } else {
                        payEW(stk, amount);
                    }
                } else if (pt.equals("BA")) {
                    payBA(stk, amount);
                } else {
                    payCC(stk, amount);
                }
            }
            sc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
