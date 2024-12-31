public class EWallet implements Payment, Transfer {
	private int soDienThoai;
	private double soDuTK;

	public EWallet(int soDienThoai) {
		this.soDienThoai = soDienThoai;
		this.soDuTK = 0;
	}

	public void setSoDienThoai(int soDienThoai) {
		this.soDienThoai = soDienThoai;
	}

	public int getSoDienThoai() {
		return this.soDienThoai;
	}

	@Override
	public boolean pay(double amount) {
		if (amount <= this.soDuTK) {
			this.soDuTK -= amount;
			return true;
		} else {
			return false;
		}
	}

	public void setSoDuTK(double soDuTK) {
		this.soDuTK = soDuTK;
	}

	@Override
	public double checkBalance() {
		return this.soDuTK;
	}

	@Override
	public boolean transfer(double amount, Transfer to) {
		double soTienCK = amount + amount * Transfer.transferFee;
		if (soTienCK <= checkBalance()) {
			this.soDuTK -= soTienCK;
			// nhan tien
			if (to instanceof EWallet) {
				EWallet trungGian = (EWallet) to;
				double tien = trungGian.checkBalance();
				trungGian.setSoDuTK(tien + amount);
			} else if (to instanceof BankAccount) {
				BankAccount trungGian = (BankAccount) to;
				trungGian.setSoDuTK(amount + trungGian.checkBalance());
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
		return this.soDienThoai + "," + this.soDuTK;
	}

}
