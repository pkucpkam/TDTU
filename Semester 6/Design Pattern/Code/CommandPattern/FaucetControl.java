public class FaucetControl {
    String location = "";

    public FaucetControl(String location) {
        this.location = location;
    }

    public void openValue() {
        System.out.println(location + " faucet is open");
    }

    public void closeValue() {
        System.out.println(location + " faucet is closed");
    }
}




