package interfaz;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
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
	public static final String PLAY = "Play";


	private JButton btnEliminar;
	private Reprod mundo;
	private Map<String, Record> records;
	private JPanel panel;
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
		
		panel = new JPanel();
		panel.setBounds(39, 50, 398, 272);
		getContentPane().add(panel);
		panel.setLayout(new GridLayout(records.keySet().size(), 1, 0, 0));
		
//		JLabel lblHmmm = new JLabel("hmmm");
//		panel.add(lblHmmm);

//		setResizable(false);
		setTitle("Reproductor");

		Iterator<String> it = records.keySet().iterator();
		int i =0;
		int j=0;
		while(it.hasNext()){
//			if(i/15>j){
//				j++;
//				panel = new JPanel();
//
//				panel.setBounds(0, 0, 298, 30*records.keySet().size());
//				getContentPane().add(panel);
//				panel.setLayout(new GridLayout(records.size(), 1));
//			}
//
//			JPanel panelAux = new JPanel();
//
//			panelAux.setBounds(6, 66, 461*(j+1), 40);
//
//			GridBagLayout gbl_panel = new GridBagLayout();
//			gbl_panel.columnWidths = new int[]{88, 61, 0, 0, 0};
//			gbl_panel.rowHeights = new int[]{5, 0};
//			gbl_panel.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
//			gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
//			panelAux.setLayout(gbl_panel);
//
			String key = it.next();
			System.out.println(key);
			String fecha = records.get(key).getAdded().toString();
//
//			JLabel lblNewLabel = new JLabel("s - s");
//			GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
//			lblNewLabel.setMinimumSize(new Dimension(50, 50));
//			gbc_lblNewLabel.insets = new Insets(0, 0, 0, records.keySet().size());
//			gbc_lblNewLabel.gridwidth = 4;
//			gbc_lblNewLabel.gridx = 0;
//			gbc_lblNewLabel.gridy = 0;
//			lblNewLabel.setEnabled(false);
//			panelAux.add(lblNewLabel, gbc_lblNewLabel);
//
//			JButton button = new JButton("play");
//			button.setActionCommand(key+" "+PLAY);
//			button.addActionListener(this);
//			button.setPreferredSize(new Dimension(30, 30));
//			button.setMinimumSize(new Dimension(30, 30));
//			button.setMaximumSize(new Dimension(30, 30));
//			GridBagConstraints gbc_button = new GridBagConstraints();
//			gbc_button.insets = new Insets(0, 0, 0,  records.keySet().size());
//			gbc_button.gridx = 2;
//			gbc_button.gridy = 0;
//			panelAux.add(button, gbc_button);
//
//			JButton button_1 = new JButton("b");
//			button_1.setActionCommand(key+" "+ELIMINAR);
//			button_1.addActionListener(this);
//			button_1.setMinimumSize(new Dimension(30, 30));
//			button_1.setMaximumSize(new Dimension(30, 30));
//			button_1.setPreferredSize(new Dimension(30, 30));
//			GridBagConstraints gbc_button_1 = new GridBagConstraints();
//			gbc_button_1.gridx = 3;
//			gbc_button_1.gridy = 0;
//			panelAux.add(button_1, gbc_button_1);
//			i++;
//			panel.add(panelAux);
			JLabel h = new JLabel(key+" - "+records.get(key).getVoiceFile().split("/")[records.get(key).getVoiceFile().split("/").length-1]+" - "+records.get(key).getAdded());
			panel.add(h);
		}
		

	}

	private void refrescar(){
		System.out.println("whyy");
//		panel.removeAll();
		crearVentana();
		panel.repaint();
		panel.revalidate();
		panel.validate();
//		repaint();
//		revalidate();
//		validate();
//		pack();
	}

	public boolean agregarOnce(String name, String voiceFile, Date date){

		boolean b =  mundo.scheduleOnce(name, voiceFile, new Date(), date);
		refrescar();
		return b;

	}

	public boolean agregarLoop(Metric metric, String name, String voiceFile, int cadaN, Date start){
		System.out.println(metric);
		boolean b;
		if (metric==Metric.ANHO||metric==Metric.MES){
			 b= mundo.scheduleBigLoop(name, voiceFile, new Date(), cadaN, start, metric);
		}else{
			 b= mundo.scheduleSmallLoop(name, voiceFile, new Date(), cadaN, start, metric);	
		}
		refrescar();
		return b;
	}

	public boolean agregarLista(String name, String voiceFile, Date date, boolean[] dias){
		
		boolean b =  mundo.scheduleList(name, voiceFile, new Date(), date, dias);
		refrescar();
		return b;
	}

	public boolean eliminarRecord(String name){
		boolean b =  mundo.eraseTask(name);
		refrescar();
		return b;
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
			String aa = JOptionPane.showInputDialog("Entre el nomrbe del record a borrar");
			System.out.println(eliminarRecord(aa));
		}

	}
}
