import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Converter {

	private double t = 1;
	private JFrame f;
	private JPanel panelMain;
	private JPanel panel2;
	private JPanel panel1;
	private JButton quit;
	private JLabel label2;
	private JSpinner spin2;
	private JTextField euro;
	private JTextField dollar;
	private JLabel arrow;

	public Converter() {
		f = new JFrame("Convertisseur");
		panelMain = new JPanel();
		panelMain.setLayout(new GridLayout(3,1));
		panelMain.setBounds(0,0,300,90);
		panelMain.setBackground(Color.gray);

		// ROW 1
		panel1 = new JPanel();
		panel1.setBounds(0,0,100,300);
		panel1.setBackground(Color.white);
		euro = new JTextField();
		euro.setPreferredSize(new Dimension(100,30));
		euro.addActionListener(new TextFieldListener());
		panel1.add(euro);
		panel1.add(new JLabel("€"));
		arrow = new JLabel("  ");
		panel1.add(arrow);
		dollar = new JTextField();
		dollar.setPreferredSize(new Dimension(100,30));
		dollar.addActionListener(new TextFieldListener());
		panel1.add(dollar);
		panel1.add(new JLabel("$"));
		panelMain.add(panel1);


		// ROW 2
		panel2 = new JPanel();
		panel2.setBounds(0,0,100,300);
		panel2.setBackground(Color.white);
		label2 = new JLabel("TAUX 1€ = ");
		panel2.add(label2);
		spin2 = new JSpinner(new SpinnerNumberModel(1.0, 0.01, 100.0,  0.01));
		panel2.add(spin2);
		panel2.add(new JLabel("$"));
		panelMain.add(panel2);

		// ROW 3
		quit = new JButton("Quitter");
		quit.setPreferredSize(new Dimension(40, 20));
		panelMain.add(quit);
		f.add(panelMain);


		// CONFIG
		f.setSize(300,120);
		f.setLayout(null);
		f.setLocationRelativeTo(null);
		f.getContentPane().setBackground(Color.white);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);

		quit.addMouseListener(new MouseAdapter() {
      		public void mousePressed(MouseEvent e) {
		    	if (e.getSource() == quit) {
		    		f.dispose();
		    	}
		  	}
		});
		spin2.addChangeListener(new SpinnerListener());
	}

	public double toDollars(double s) {
		return s * this.t;
	}

	public double toEuros(double s) {
		return s / this.t;
	}

	public void setTaux(double t) {
		this.t = t;
	}

	private class SpinnerListener implements ChangeListener 
	{
		public void stateChanged(ChangeEvent e)
		{
			if (e.getSource() == spin2) {
				double value = (double) spin2.getValue();
				if (value < 0.01 || value > 100.0) {
					setTaux(1.0);
					spin2.setValue(1.0);
				}
				else {
					setTaux(value);
				}	
			}
		}
	}
	private class TextFieldListener implements ActionListener
	{
		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource() == euro) {
				try {
					double valueEuro = Double.parseDouble(euro.getText());
					dollar.setText(Double.toString((Math.floor(toDollars(valueEuro)*1000)/1000)));
					arrow.setText("=>");
				}
				catch (Exception e) {
					JOptionPane pane = new JOptionPane();
					Object[] options = { "OK", "CANCEL" };
					pane.showOptionDialog(null, "Impossible de convertir", "Alerte", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
				}
			}
			else if (ae.getSource() == dollar) {
				try {
					double valueDollar = Double.parseDouble(dollar.getText());
					euro.setText(Double.toString((Math.floor(toEuros(valueDollar)*1000)/1000)));
					arrow.setText("<=");
				}
				catch (Exception e) {
					JOptionPane pane = new JOptionPane();
					Object[] options = { "OK", "CANCEL" };
					pane.showOptionDialog(null, "Impossible de convertir", "Alerte", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
				}
			}
    	}
	}
}