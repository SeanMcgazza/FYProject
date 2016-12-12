

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.WindowAdapter;
import java.io.IOException;
import java.io.InputStream;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.universe.*;

import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import javax.media.j3d.*;
import javax.vecmath.*;

import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Sphere;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.Timer;
//import com.fazecast.jSerialComm.SerialPort;
import gnu.io.*;


public class Knee_Gui extends Applet implements ActionListener, KeyListener, SerialPortEventListener{

    private Button startBtn = new Button("Start");
    private Button connectButton = new Button("Connect");
    private JComboBox<String> portList = new JComboBox<String>();
    private TransformGroup leg;
    private TransformGroup Knee;
    private TransformGroup line;
    private Transform3D transform = new Transform3D();
    
    InputStream inputStream;
    SerialPort serialPort;
	static SerialPort chosenPort;
    
    private float height = 0.0f;
    private float sign = 1.0f;
    private float xLoc = 0.0f;
    
    private Timer timer;
    
    public Knee_Gui()
    {
        setLayout(new BorderLayout());
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        Canvas3D canvas = new Canvas3D(config);

        add("Center", canvas);
        canvas.addKeyListener(this);
        
        timer = new Timer(100, this);
        
        //add a panel component that will contain our button
        //then, add the panel to the applet window
        Panel panel = new Panel();
        panel.add(startBtn);
        add("North", panel);
        
        
		panel.add(portList);
		panel.add(connectButton);
		
//		
//		SerialPort[] portNames = SerialPort.getCommPorts();
//		for(int i = 0; i < portNames.length; i++)
//			portList.addItem(portNames[i].getSystemPortName());
//		
        
		
        startBtn.addActionListener(this);
        startBtn.addKeyListener(this);
        
        //create our universe and scene graph and add it to the universe
        BranchGroup scene = createSceneGraph();
        SimpleUniverse universe = new SimpleUniverse(canvas);
        universe.getViewingPlatform().setNominalViewingTransform();
        universe.addBranchGraph(scene);
        
        
    }
    
    public BranchGroup createSceneGraph()
    {
        BranchGroup root = new BranchGroup();
        
        leg = new TransformGroup();
        //this allows furthur transforms after the object is drawn
        leg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        root.addChild(leg);
        
        Cylinder cylinder = new Cylinder(0.2f, 2.5f);
        Knee = new TransformGroup();
        Knee.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        Transform3D pos2 = new Transform3D();
        pos2.setTranslation(new Vector3f(0,0,0));
        
        Knee.setTransform(pos2);
        Knee.addChild(cylinder);
        root.addChild(Knee);
        
        Cylinder cylinder1 = new Cylinder(0.01f, 2.5f);
        line = new TransformGroup();
        line.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        Transform3D pos3 = new Transform3D();
        pos2.setTranslation(new Vector3f(0,0,0));
        
        line.setTransform(pos3);
        line.addChild(cylinder1);
        root.addChild(line);
             
        
        //add a shape node to the scene graph
        Sphere sphere = new Sphere(.25f);
        leg = new TransformGroup();
        leg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        
        Transform3D pos1 = new Transform3D();
        pos1.setTranslation(new Vector3f(0,0,0));
        
        leg.setTransform(pos1);
        leg.addChild(sphere);
        root.addChild(leg);
        
        //set up a directional light for our scene
        BoundingSphere bounds = new BoundingSphere(new Point3d(0,0,0), 100);
        Color3f lightColor = new Color3f(1f, 0, .2f);
        Vector3f lightDirection = new Vector3f(4,-7,-12);
        DirectionalLight light = new DirectionalLight(lightColor, lightDirection);
        light.setInfluencingBounds(bounds);
        
        root.addChild(light);
        
        //set up a white ambient light as well
        Color3f ambientColor = new Color3f(1, 1, 1);
        AmbientLight ambient = new AmbientLight(ambientColor);
        ambient.setInfluencingBounds(bounds);
        
        root.addChild(ambient);
        
        //return the complete scene graph
        return root;   
        
    }
    
    public void keyPressed(KeyEvent e)
    {
        //invoked when a key is pressed
        
        if(e.getKeyChar() == 'a')
        {
            xLoc += .1f;
        }
        if(e.getKeyChar() == 'd')
        {
            xLoc -= .1f;
        }  
    }
    
    public void keyReleased(KeyEvent e)
    {
        //invoked when a key is released
        //must be implemented to satisfy the interface
    }
    
    public void keyTyped(KeyEvent e)
    {
        //invoked when a key has been typed
        //must be implemented to satisfy the interface
    }
    
    public void actionPerformed(ActionEvent e)
    {
        //start timer when the button is pressed
        
        if(e.getSource() == startBtn)
        {
            if(!timer.isRunning())
            {
                timer.start();
            }
        }
        
//        if(e.getSource() == connectButton){
//			if(connectButton.getToolkit().equals("Connect")) {
//				// attempt to connect to the serial port
//				chosenPort = SerialPort.getCommPort(portList.getSelectedItem().toString());
//				chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
//				if(chosenPort.openPort()) {
//					connectButton.setText("Disconnect");
//					portList.setEnabled(false);
//				}
				
				// create a new thread that listens for incoming text and populates the graph
//				Thread thread = new Thread(){
//					@Override public void run() {
//						 byte[] readBuffer = new byte[20];
//						inputStream = chosenPort.getInputStream();
//						 try {
//							while (chosenPort.getInputStream().available() > 0) {
//							        int numBytes = inputStream.read(readBuffer);
//							       
//							    }
//						} catch (IOException e1) {
//							// TODO Auto-generated catch block
//							e1.printStackTrace();
//						}
//			                System.out.print(new String(readBuffer));
//						Scanner scanner = new Scanner(chosenPort.getInputStream());
//						while(scanner.hasNextLine()) {
//							try {
//								String line = scanner.nextLine();
//								int number = Integer.parseInt(line);
//								System.out.println("Number");
//								System.out.println(line);
//								series.add(x++, 1023 - numBytes);
//								window.repaint();
//							} catch(Exception e) {}
//						}
//						scanner.close();
//					}
//				};
//				thread.start();
//			} else {
//				// disconnect from the serial port
//				chosenPort.close();
//				portList.setEnabled(true);
//				//connectButton.setText("Connect");
//			
//			}
//		}
        
 
//            
           //apply the transforms
            transform.setTranslation(new Vector3f(xLoc, height, 0.0f));
            leg.setTransform(transform);
            Knee.setTransform(transform);
        }

    
    
    
    public static void Knee_gui() {
        
        System.out.println("Program Started");
        Knee_Gui Knee = new Knee_Gui();
        Knee.addKeyListener(Knee);
        MainFrame frame = new MainFrame(Knee, 800, 600);
        
    }

	@Override
	public void serialEvent(SerialPortEvent event) {
		// TODO Auto-generated method stub
		 switch(event.getEventType()) {
	        case SerialPortEvent.DATA_AVAILABLE:
	            byte[] readBuffer = new byte[20];
	            
	            
	            try {
	                while (inputStream.available() > 0) {
	                    int numBytes = inputStream.read(readBuffer);
	                }
	                System.out.print(new String(readBuffer));
	            } catch (IOException e) {System.out.println(e);}
	            break;
	        }
	}
    
}
