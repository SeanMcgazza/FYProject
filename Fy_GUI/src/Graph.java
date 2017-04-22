import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import com.fazecast.jSerialComm.SerialPort;
import gnu.io.*;

public class Graph {
	
	static SerialPort chosenPort;
	static int x = 0;
	static InputStream inputStream;
	static int numBytes;

	public  void Graph() {
		
		// create and configure the window
		JFrame window = new JFrame();
		window.setTitle("EMG SENSOR");
		window.setSize(600, 400);
		window.setLayout(new BorderLayout());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		double ecg[] = {0.8999, 0.8999, 0.9, 0.8997, 0.9333, 0.9961, 1.0493, 1.0901, 1.1147, 1.1286, 1.1277,
	               1.1079, 1.0714, 1.0184, 0.9533, 0.8986, 0.8991, 0.8992, 0.8991, 0.899, 0.8988, 0.8982,
	               0.8957, 1.0727, 1.3045, 1.537, 1.7698, 2.0028, 2.2362, 2.4672, 2.2959, 2.0652, 1.8332,
	               1.6009, 1.3685, 1.1364, 0.9142, 0.8553, 0.7946, 0.7338, 0.672, 0.6876, 0.7482, 0.8088,
	               0.8696, 0.901, 0.9006, 0.9006, 0.9005, 0.9005, 0.9007, 0.9075, 0.9683, 1.0276, 1.083,
	               1.1326, 1.1749, 1.2087, 1.2328, 1.2465, 1.2494, 1.2414, 1.2227, 1.1939, 1.156, 1.11, 1.0575,
	               1.0002, 0.9397, 0.8989, 0.8996, 0.8998, 0.8999, 0.9, 0.9, 0.9, 0.9001, 0.9001, 0.9, 0.9,
	               0.8997, 0.913,0.9275, 0.9346, 0.9323, 0.9211, 0.904, 0.9, 0.8999, 0.8998, 0.8998, 0.8998,
	               0.8997, 0.8997, 0.8998, 0.8998, 0.8998, 0.8998, 0.8999, 0.8999};
		
		
		JComboBox<String> portList = new JComboBox<String>();
		JButton connectButton = new JButton("Connect");
		JPanel topPanel = new JPanel();
		topPanel.add(portList);
		topPanel.add(connectButton);
		window.add(topPanel, BorderLayout.NORTH);
		
		// populate the drop-down box
		SerialPort[] portNames = SerialPort.getCommPorts();
		for(int i = 0; i < portNames.length; i++)
			portList.addItem(portNames[i].getSystemPortName());
		
		// create the line graph
		XYSeries series = new XYSeries("EMG Readings");
		XYSeriesCollection dataset = new XYSeriesCollection(series);
		JFreeChart chart = ChartFactory.createXYLineChart("EMG Readings", "Time", "ADC Reading", dataset);
		window.add(new ChartPanel(chart), BorderLayout.CENTER);
		
		// configure the connect button and use another thread to listen for data
		connectButton.addActionListener(new ActionListener(){
			@Override public void actionPerformed(ActionEvent arg0) {
				if(connectButton.getText().equals("Connect")) {
					// attempt to connect to the serial port
					chosenPort = SerialPort.getCommPort(portList.getSelectedItem().toString());
					chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
					if(chosenPort.openPort()) { 
						connectButton.setText("Disconnect");
						portList.setEnabled(false);
					}
					
					// create a new thread that listens for incoming text and populates the graph
					Thread thread = new Thread(){
						@Override public void run() {
							 byte[] readBuffer = new byte[20];
							 System.out.println("Got as far as here");
							inputStream = chosenPort.getInputStream();
							 try {
								 int y =0;
								while (chosenPort.getInputStream().available() > 0) {
								        int numBytes = inputStream.read(readBuffer);
								        System.out.println("Got as far as here 1");
								        System.out.println(102 - numBytes);
								        System.out.println("Got as far as here 2");
								        series.add(x++, ecg[y]);
										window.repaint();
										y++;
								    }
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
//				                System.out.print(new String(readBuffer));
//							Scanner scanner = new Scanner(chosenPort.getInputStream());
//							while(scanner.hasNextLine()) {
//								try {
//									String line = scanner.nextLine();
//									int number = Integer.parseInt(line);
//									System.out.println("Number");
//									System.out.println(line);
//									series.add(x++, 1023 - numBytes);
//									window.repaint();
//								} catch(Exception e) {}
//							}
//							scanner.close();
						}
					};
					thread.start();
				} else {
					// disconnect from the serial port
					chosenPort.closePort();
					portList.setEnabled(true);
					connectButton.setText("Connect");
					series.clear();
					x = 0;
				}
			}
		});
		
		// show the window
		window.setVisible(true);
	}
	
	

}