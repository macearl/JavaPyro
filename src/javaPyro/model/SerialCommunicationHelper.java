package javaPyro.model;

import javaPyro.Main;
import jssc.SerialPortException;

public abstract class SerialCommunicationHelper {
	public static void sendToCom(byte[] data) {
		// Send data to ComPort
		try {
			// open Port
			Main.SERIALPORT.openPort();
			// set Port to 8N1
			Main.SERIALPORT.setParams(9600, 8, 1, 0, false, false);
			// write Data to Port
			Main.SERIALPORT.writeBytes(data);
			// close Port
			Main.SERIALPORT.closePort();
		} catch (SerialPortException ex) {
			System.out.println(ex);
		}
	}
}
