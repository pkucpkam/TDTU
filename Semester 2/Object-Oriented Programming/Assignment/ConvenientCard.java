
public class ConvenientCard implements Payment {
	private IDCard id;
	private double soDuTK;
	private String type;

	public String getType() {
		return this.type;
	}

	public int getSoDienThoai() {
		return id.getSoDienThoai();
	}

	public int getNgaySinh() {
		return Integer.parseInt(id.ngaySinh.substring(6));
	}

	public int getSoDinhDanh() {
		return id.soDinhDanh;
	}

	public String getGioiTinh() {
		return id.getGioiTinh();
	}

	public ConvenientCard(IDCard id) throws CannotCreateCard {
		this.id = id;
		this.soDuTK = 100;
		int old = 2023 - Integer.parseInt(id.ngaySinh.substring(6));
		if (old < 12) {
			throw new CannotCreateCard("Not enough age");
		} else if (old <= 18) {
			this.type = "Student";
		} else {
			this.type = "Adult";
		}
	}

	@Override
	public boolean pay(double amount) {
		// tinh tien phai tra
		double soTienPhaiTra = 0;
		if (this.type.equals("Student")) {
			soTienPhaiTra = amount;
		} else {
			soTienPhaiTra = amount + amount * 0.01;
		}
		// check
		if (soTienPhaiTra <= this.soDuTK) {
			this.soDuTK -= soTienPhaiTra;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public double checkBalance() {
		return this.soDuTK;
	}

	public void topUp(double tien) {
		this.soDuTK += tien;
	}

	public String toString() {
		return this.id + "," + this.type + "," + this.soDuTK;
	}
}
