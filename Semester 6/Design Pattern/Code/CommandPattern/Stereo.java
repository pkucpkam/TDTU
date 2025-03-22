public class Stereo {
    String location = "";

    public Stereo(String location) {
        this.location = location;
    }

    public void on() {
        System.out.println(location + " stereo is on");
    }

    public void off() {
        System.out.println(location + " stereo is off");
    }

    public void setCd() {
        System.out.println(location + " stereo CD is set");
    }

    public void setDvd() {
        System.out.println(location + " stereo DVD is set");
    }

    public void setRadio() {
        System.out.println(location + " stereo radio is set");
    }

    public void setVolume(int volume) {
        System.out.println(location + " stereo volume set to " + volume);
    }
}




