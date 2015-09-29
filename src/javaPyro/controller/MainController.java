package javaPyro.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javaPyro.Main;
import javaPyro.model.CRC8;
import javaPyro.model.SerialCommunicationHelper;

public class MainController {
	private static byte[][] _cues;
	private static long[] _timings;

	public MainController() {
		new MainUIController(this);
	}

	public void calculateShow(List<String[]> input) {
		// calculate checksums
		CRC8 crc8 = new CRC8();
		_cues = new byte[input.size()][4];
		_timings = new long[input.size()];

		int i = 0;
		while (i < input.size()) {
			for (String[] strA : input) {
				crc8.update(new byte[] { Byte.parseByte(strA[0]), Byte.parseByte(strA[1]) });

				byte[] tmp = new byte[4];
				tmp[0] = (byte) 255;
				tmp[1] = Byte.parseByte(strA[0]);
				tmp[2] = Byte.parseByte(strA[1]);
				tmp[3] = (byte) crc8.getValue();
				_cues[i] = tmp;
				crc8.reset();

				String[] tmpTime = strA[2].split("[:\\.]");
				_timings[i] = TimeUnit.MINUTES.toMillis(Long.parseLong(tmpTime[0]))
						+ TimeUnit.SECONDS.toMillis(Long.parseLong(tmpTime[1])) + Long.parseLong(tmpTime[2]);
				i++;
			}
		}
	}

	public List<String[]> openZPLFile(File file) {
		assert file != null : "Precondition violated: file != null";
		List<String[]> result = new ArrayList<String[]>();

		// Create the file reader
		BufferedReader fileReader = null;
		try {
			fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
			// Read the file line by line
			String line = "";

			// XXX skip first two lines for now
			fileReader.readLine();
			fileReader.readLine();

			while ((line = fileReader.readLine()) != null) {
				// Get all tokens available in line
				String[] tokens = line.split(Main.DELIMITER);

				// Save them in the list
				result.add(tokens);

			}
		} catch (IOException e) {
			System.err.println("Could not open \"" + file + "\". Please check if it exists and has read permissions.");
		} finally {
			try {
				fileReader.close();
			} catch (IOException e) {
				System.err.println("Could not close \"" + file + "\". Please check read permissions.");
			}
		}

		assert result != null : "Postcondition violated: result != null";
		return result;
	}

	public void startShow() {
		assert _cues.length == _timings.length : "Precondition violated: data.length == timings.length";

		int i = 0;
		for (byte[] bArray : _cues) {
			// wait for next cue
			try {
				if (i == 0) {
					Thread.sleep(_timings[i]);
				} else {
					Thread.sleep(_timings[i] - _timings[i - 1]);
				}
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}

			SerialCommunicationHelper.sendToCom(bArray);

			i++;
		}
	}
}