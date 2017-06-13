package interfaz;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JLabel;

import world.Metric;
import world.Record;
import world.Reprod;

import javax.swing.JScrollBar;

import java.awt.BorderLayout;

public class Main extends JFrame implements ActionListener {

	public static final String AGREGAR = "Agregar";
	public static final String ELIMINAR = "Eliminar";


	private JButton btnEliminar;
	private Reprod mundo;
	private Map<String, Record> records;
	private JPanel panel;
	private JPanel panel_1;
	public Main() {

		try {
			mundo = new Reprod();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Hubo un problema cargando la aplicaci�n");
			super.dispose();
		}

		records = mundo.getRecords();

		crearVentana();


	}

	private void crearVentana(){
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);


		setMinimumSize(new Dimension(473, 429));


		getContentPane().setLayout(null);
		
		

		panel = new JPanel();
		JScrollPane jsp = new JScrollPane(panel);
		jsp.setBounds(6, 66, 461, 285);
		getContentPane().add(jsp);
		panel.setLayout(null);
		
		panel_1 = new JPanel();
		panel_1.setBounds(461, 0, 0, 285);
		panel.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));

		btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(this);
		btnEliminar.setActionCommand(ELIMINAR);
		btnEliminar.setBounds(288, 363, 117, 29);
		getContentPane().add(btnEliminar);

		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.setBounds(86, 363, 117, 29);
		btnAgregar.addActionListener(this);
		btnAgregar.setActionCommand(AGREGAR);
		getContentPane().add(btnAgregar);

		JLabel lblReproduccionesAgendadas = new JLabel("Reproducciones agendadas");
		lblReproduccionesAgendadas.setBounds(140, 6, 200, 50);
		getContentPane().add(lblReproduccionesAgendadas);

		setResizable(false);
		setTitle("Reproductor");

		Iterator<String> it = records.keySet().iterator();
		while(it.hasNext()){
			String nombre = it.next();
			
		}


	}

	private void refrescar(){
		panel.removeAll();
		crearVentana();
		panel.repaint();
		panel.revalidate();
		panel.validate();
		repaint();
		revalidate();
		validate();
		pack();
	}

	public boolean agregarOnce(String name, String voiceFile, Date date){

		boolean b =  mundo.scheduleOnce(name, voiceFile, new Date(), date);
		refrescar();
		return b;

	}

	public boolean agregarLoop(Metric metric, String name, String voiceFile, int cadaN, Date start){
		if (metric==Metric.ANHO||metric==Metric.MES){
			return mundo.scheduleBigLoop(name, voiceFile, new Date(), cadaN, start, metric);
		}else{
			return mundo.scheduleSmallLoop(name, voiceFile, new Date(), cadaN, start, metric);	
		}
	}

	public boolean agregarLista(String name, String voiceFile, Date date, boolean[] dias){
		return mundo.scheduleList(name, voiceFile, new Date(), date, dias);
	}

	public boolean eliminarRecord(String name){
		return mundo.eraseTask(name);

	}

	public static void main(String[] args) {
		Main m = new Main();
		Date after = new Date();
		after = new Date(after.getYear(), after.getMonth(), after.getDate(), after.getHours(), after.getMinutes(), after.getSeconds()+10);
		m.agregarOnce("test","/Users/camilagarciahernandez/Downloads/2015-2 ISIS1304 - Proyecto 1(1)/Muestras/piano1.wav",after);
		m.setVisible(true);

	}
	
	public void dispose( )
	{
		try
		{
			System.out.println("salvé");
			mundo.salvar( );
			super.dispose( );
			System.exit(0);
		}
		catch( Exception e )
		{
			e.printStackTrace();
			setVisible( true );
			int respuesta = JOptionPane.showConfirmDialog( this,"Problemas salvando la informaci�n:\n" + e.getMessage( ) +
					"\n�Quiere cerrar el programa sin salvar?",
					"Error", JOptionPane.YES_NO_OPTION );
			if( respuesta == JOptionPane.YES_OPTION )
			{
				super.dispose( );
				System.exit(0);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand()==AGREGAR){
			DialogoAdd da = new DialogoAdd(this);
			da.setVisible(true);
		} else if (e.getActionCommand()==ELIMINAR){
			
		}

	}
}
