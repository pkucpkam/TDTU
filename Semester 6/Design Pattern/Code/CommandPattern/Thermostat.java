public class Thermostat {
    String location = "";

    public Thermostat(String location) {
        this.location = location;
    }

    public void setTemperature(int temperature) {
        System.out.println(location + " thermostat temperature set to " + temperature);
    }
}



