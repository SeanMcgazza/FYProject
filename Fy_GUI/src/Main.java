import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;

public class Main extends JFrame {

	private JPanel contentPane;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JButton GraphEMG = new JButton("Graph EMG");
		GraphEMG.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Graph g = new Graph();
				g.Graph();
			}
		});
		panel.add(GraphEMG);
		
		JButton DataBase = new JButton("DataBase");
		DataBase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DataBase db = new DataBase();
				db.DataBase();
			}
		});
		panel.add(DataBase);
		
		JButton AccMove = new JButton("Acc Movable");
		AccMove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Knee_Gui kg = new Knee_Gui();
				kg.Knee_gui();
			}
		});
		panel.add(AccMove);
	}

	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
