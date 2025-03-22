public class RemoteLoader {
    public static void main(String[] args) {
        // Tạo các đối tượng RemoteControl và thiết bị
        RemoteControl remoteControl = new RemoteControl();

        // Các thiết bị
        Light livingRoomLight = new Light("Living Room");
        TV livingRoomTV = new TV("Living Room");
        CeilingFan ceilingFan = new CeilingFan("Living Room");
        Stereo stereo = new Stereo("Living Room");
        Thermostat thermostat = new Thermostat("Living Room");
        FaucetControl faucetControl = new FaucetControl("Kitchen");

        // Các lệnh cho thiết bị
        LightOnCommand livingRoomLightOn = new LightOnCommand(livingRoomLight);
        LightOffCommand livingRoomLightOff = new LightOffCommand(livingRoomLight);
        TVOnCommand livingRoomTVOn = new TVOnCommand(livingRoomTV);
        TVOffCommand livingRoomTVOff = new TVOffCommand(livingRoomTV);
        CeilingFanOnCommand ceilingFanOn = new CeilingFanOnCommand(ceilingFan);
        CeilingFanOffCommand ceilingFanOff = new CeilingFanOffCommand(ceilingFan);
        StereoOnCommand stereoOn = new StereoOnCommand(stereo);
        StereoOffCommand stereoOff = new StereoOffCommand(stereo);
        ThermostatOnCommand thermostatOn = new ThermostatOnCommand(thermostat);
        ThermostatOffCommand thermostatOff = new ThermostatOffCommand(thermostat);
        FaucetControlOnCommand faucetControlOn = new FaucetControlOnCommand(faucetControl);
        FaucetControlOffCommand faucetControlOff = new FaucetControlOffCommand(faucetControl);

        remoteControl.setComand(0, livingRoomLightOn, livingRoomLightOff);
        remoteControl.setComand(1, livingRoomTVOn, livingRoomTVOff);
        remoteControl.setComand(2, ceilingFanOn, ceilingFanOff);
        remoteControl.setComand(3, stereoOn, stereoOff);
        remoteControl.setComand(4, thermostatOn, thermostatOff);
        remoteControl.setComand(5, faucetControlOn, faucetControlOff);

        System.out.println(remoteControl);

        remoteControl.onButtonWasPushed(0);
        remoteControl.offButtonWasPushed(0);

        remoteControl.onButtonWasPushed(1);
        remoteControl.offButtonWasPushed(1);

        remoteControl.onButtonWasPushed(2);
        remoteControl.offButtonWasPushed(2);

        remoteControl.onButtonWasPushed(3);
        remoteControl.offButtonWasPushed(3);

        remoteControl.onButtonWasPushed(4);
        remoteControl.offButtonWasPushed(4);

        remoteControl.onButtonWasPushed(5);
        remoteControl.offButtonWasPushed(5);
    }
}
