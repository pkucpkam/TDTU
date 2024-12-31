public class IDCard {
      protected int soDinhDanh;
      protected String hoTen;
      protected String gioiTinh;
      protected String ngaySinh;
      protected String diaChi;
      protected int soDienThoai;

      public IDCard(int soDinhDanh, String hoTen, String gioiTinh, String ngaySinh, String diaChi, int soDienThoai) {
            this.soDinhDanh = soDinhDanh;
            this.hoTen = hoTen;
            this.gioiTinh = gioiTinh;
            this.ngaySinh = ngaySinh;
            this.diaChi = diaChi;
            this.soDienThoai = soDienThoai;
      }

      public void setSoDinhDanh(int soDinhDanh) {
            this.soDinhDanh = soDinhDanh;
      }

      public int getSoDinhDanh() {
            return this.soDinhDanh;
      }

      public void setHoTen(String hoTen) {
            this.hoTen = hoTen;
      }

      public String getHoTen() {
            return this.hoTen;
      }

      public void setGioiTinh(String gioiTinh) {
            this.gioiTinh = gioiTinh;
      }

      public String getGioiTinh() {
            return this.gioiTinh;
      }

      public void setNgaySinh(String ngaySinh) {
            this.ngaySinh = ngaySinh;
      }

      public String getNgaySinh() {
            return this.ngaySinh;
      }

      public void setDiaChi(String diaChi) {
            this.diaChi = diaChi;
      }

      public String getDiaChi() {
            return this.diaChi;
      }

      public void setSoDienThoai(int soDienThoai) {
            this.soDienThoai = soDienThoai;
      }

      public int getSoDienThoai() {
            return this.soDienThoai;
      }

      public String toString() {
            return this.soDinhDanh + "," + this.hoTen + "," + this.gioiTinh + "," + this.ngaySinh + "," + this.diaChi
                        + ","
                        + this.soDienThoai;
      }
}
