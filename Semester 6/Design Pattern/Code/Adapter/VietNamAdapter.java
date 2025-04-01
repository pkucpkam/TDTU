public class VietNamAdapter implements Duck {
    VietNam vietNam;

    public VietNamAdapter(VietNam viet){
        this.vietNam = viet;
    }

    @Override
    public void quack() {
        // TODO Auto-generated method stub
        vietNam.capcap();
    }

    @Override
    public void fly() {
        // TODO Auto-generated method stub
        vietNam.baybay();
    }
    
}
