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
							inputStream = chosenPort.getInputStream();
							 try {
								while (chosenPort.getInputStream().available() > 0) {
								        int numBytes = inputStream.read(readBuffer);
								        series.add(x++, 1023 - numBytes);
										window.repaint();
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