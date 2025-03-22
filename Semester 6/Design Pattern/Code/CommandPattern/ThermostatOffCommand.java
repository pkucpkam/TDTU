public class ThermostatOffCommand implements Command {
    Thermostat thermostat;

    public ThermostatOffCommand(Thermostat thermostat) {
        this.thermostat = thermostat;
    }

    @Override
    public void execute() {
        thermostat.setTemperature(0); // Example for off state
    }

    @Override
    public void undo() {
        thermostat.setTemperature(22); // Example undo temperature
    }
}