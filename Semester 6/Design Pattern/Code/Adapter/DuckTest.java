public class DuckTest {
    public static void main(String[] args) {
        Duck duck = new MallarDuck();
        VietNam vit = new VitTrang();
        Duck vietNamAdapter = new VietNamAdapter(vit);
        System.out.println("vit trang noi");
        vietNamAdapter.quack();
        vietNamAdapter.fly();

        System.out.println("Duck says ....");
        duck.quack();
        duck.fly();
    }
}
