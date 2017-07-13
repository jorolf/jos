package function;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import rusky.husky.Easing;

public class Function extends JPanel {

	/**
	 * 
	 */
	public JComboBox<String> func = new JComboBox<String>();
	public JTextField field = new JTextField("1000");
	public JButton btn = new JButton("Apply");
	public double[] points = new double[] {0,0};
	public double ratio = 1;
	
	private static final long serialVersionUID = 1L;
	public Function(){
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setMinimumSize(new Dimension(500, 500));
		setPreferredSize(new Dimension(500, 500));
		setLayout(null);
		frame.add(this);
		
		Arrays.stream(Easing.values()).forEach(curve -> func.addItem(curve.toString()));
		func.setBounds(0, 0, 100, 30);
		func.addActionListener(event -> btn.doClick());
		add(func);
		
		
		field.setBounds(100, 0, 100, 30);
		add(field);
		
		btn.setBounds(200, 0, 100, 30);
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int interval = Integer.parseInt(field.getText());
				ratio = 1.0/interval;
				points = new double[interval];
				for(double i = 0;i < 1;i += ratio){
					points[(int) (i/ratio)] = getFunction(i,func.getSelectedIndex());
				}
				repaint();
			}

			private double getFunction(double d, int selectedIndex) {
				return Easing.values()[selectedIndex].apply((float) d);
			}
		});
		this.add(btn);
		btn.doClick();
		
		

		Toolkit.getDefaultToolkit().setDynamicLayout(false);

		frame.setVisible(true);
		frame.pack();
//		frame.setResizable(false);
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(int i = 0;i < points.length-1;i++){
			g.drawLine((int) (ratio*i*500), (int) (points[i]*-500 + 500),(int) (ratio*(i+1)*500), (int) (points[i+1]*-500 + 500));
		}
	}

	public static void main(String[] args){
		new Function();
	}

}
