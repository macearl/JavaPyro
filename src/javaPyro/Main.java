package javaPyro;

import javaPyro.controller.MainController;
import jssc.SerialPort;

public class Main {
	public static final SerialPort SERIALPORT = new SerialPort("/dev/ttyUSB0");
	public static final String DELIMITER = ";";

	public static void main(String[] args) {
		new MainController();
	}
}