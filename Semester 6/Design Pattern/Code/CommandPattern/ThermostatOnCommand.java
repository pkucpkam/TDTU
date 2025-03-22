public class ThermostatOnCommand implements Command {
    Thermostat thermostat;

    public ThermostatOnCommand(Thermostat thermostat) {
        this.thermostat = thermostat;
    }

    @Override
    public void execute() {
        thermostat.setTemperature(22); // Example default temperature
    }

    @Override
    public void undo() {
        thermostat.setTemperature(20); // Example default undo temperature
    }
}
