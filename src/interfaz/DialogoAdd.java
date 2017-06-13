package interfaz;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date; 
import java.util.Properties;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;

import world.Metric;

import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings.TimeIncrement;

public class DialogoAdd extends JDialog implements ActionListener {
	private JTextField nombre;
	private File chosen;

	public static final String ARCHIVO = "Archivos";
	public static final String AGREGAR = "Agregar";
	public static final String CANCELAR = "Cancelar";
	public static final String ONCE = "Once";
	public static final String LOOP = "Loop";
	public static final String LIST = "List";
	public static final Integer[] HORAS = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24};
	public static final Integer[] MINUTOS = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,
		36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59};

	private JTextField cadaN;
	private JPanel panel;
	private Main frame;
	private JComboBox metric;
	private JLabel lblChosenfile;
	private JLabel lblRepetirCada;
	public DialogoAdd(Main frame) {

		this.frame = frame;
		getContentPane().setLayout(null);

		setMinimumSize(new Dimension(600, 377));

		JLabel lblAgregarNuevaReproduccin = new JLabel("Agregar nueva Reproducción");
		lblAgregarNuevaReproduccin.setBounds(179, 6, 185, 50);
		getContentPane().add(lblAgregarNuevaReproduccin);

		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(23, 63, 74, 38);
		getContentPane().add(lblNombre);

		nombre = new JTextField();
		nombre.setBounds(136, 68, 134, 28);
		getContentPane().add(nombre);
		nombre.setColumns(10);

		JLabel lblArchivoDeSonido = new JLabel("Archivo de sonido");
		lblArchivoDeSonido.setBounds(6, 108, 200, 38);
		getContentPane().add(lblArchivoDeSonido);

		JButton btnChooseFile = new JButton("Choose File");
		btnChooseFile.setBounds(146, 114, 117, 29);
		btnChooseFile.setActionCommand(ARCHIVO);
		btnChooseFile.addActionListener(this);
		getContentPane().add(btnChooseFile);

		panel = new JPanel();
		panel.setBounds(282, 51, 288, 245);
		getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblFecha = new JLabel("Fecha");
		lblFecha.setBounds(17, 21, 61, 16);
		panel.add(lblFecha);

		JLabel lblHora = new JLabel("Hora");
		lblHora.setBounds(17, 76, 61, 16);
		panel.add(lblHora);

		lblRepetirCada = new JLabel("Repetir cada");
		lblRepetirCada.setBounds(17, 121, 83, 16);
		panel.add(lblRepetirCada);

		cadaN = new JTextField();
		cadaN.setBounds(97, 115, 44, 28);
		panel.add(cadaN);
		cadaN.setColumns(10);

		//		Metric[] metrics = Metric.values();
		//		
		//		String[] metricNames = new String[metrics.length];

		metric = new JComboBox(Metric.values());
		metric.setBounds(153, 117, 129, 27);
		panel.add(metric);
		
		DatePicker datePicker_1 = new DatePicker();
		datePicker_1.setBounds(62, 15, 220, 29);
		panel.add(datePicker_1);
		
		
		TimePickerSettings timeSettings = new TimePickerSettings();
        timeSettings.setDisplaySpinnerButtons(true);
        timeSettings.setInitialTimeToNow();
        timeSettings.generatePotentialMenuTimes(TimeIncrement.FiveMinutes, null, null);
        TimePicker timePicker_1 = new TimePicker(timeSettings);
		timePicker_1.setBounds(62, 70, 200, 29);
		panel.add(timePicker_1);
		
		JLabel lblDasDeLa = new JLabel("Días de la semana para reproducir");
		lblDasDeLa.setBounds(17, 159, 245, 16);
		panel.add(lblDasDeLa);
		
		JCheckBox chckbxL = new JCheckBox("L");
		chckbxL.setBounds(6, 176, 44, 50);
		panel.add(chckbxL);
		
		JCheckBox chckbxM = new JCheckBox("M");
		chckbxM.setBounds(45, 176, 44, 50);
		panel.add(chckbxM);
		
		JCheckBox chckbxI = new JCheckBox("I");
		chckbxI.setBounds(88, 176, 36, 50);
		panel.add(chckbxI);
		
		JCheckBox chckbxJ = new JCheckBox("J");
		chckbxJ.setBounds(125, 176, 36, 50);
		panel.add(chckbxJ);
		
		JCheckBox chckbxV = new JCheckBox("V");
		chckbxV.setBounds(162, 176, 44, 50);
		panel.add(chckbxV);
		
		JCheckBox chckbxS = new JCheckBox("S");
		chckbxS.setBounds(201, 176, 44, 50);
		panel.add(chckbxS);
		
		JCheckBox chckbxD = new JCheckBox("D");
		chckbxD.setBounds(238, 176, 44, 50);
		panel.add(chckbxD);

		JRadioButton chckbxRepetirPorIntervalo = new JRadioButton("Repetir por intervalo");
		chckbxRepetirPorIntervalo.setBounds(30, 217, 176, 23);
		getContentPane().add(chckbxRepetirPorIntervalo);
		chckbxRepetirPorIntervalo.setActionCommand(LOOP);
		chckbxRepetirPorIntervalo.addActionListener(this);

		JRadioButton chckbxNoRepetir = new JRadioButton("No repetir");
		chckbxNoRepetir.setBounds(30, 192, 128, 23);
		getContentPane().add(chckbxNoRepetir);
		chckbxRepetirPorIntervalo.setActionCommand(ONCE);
		chckbxNoRepetir.addActionListener(this);
		chckbxNoRepetir.setSelected(true);

		JRadioButton chckbxRepetirPorDas = new JRadioButton("Repetir por días");
		chckbxRepetirPorDas.setBounds(30, 247, 155, 23);
		getContentPane().add(chckbxRepetirPorDas);
		chckbxRepetirPorDas.setActionCommand(LIST);
		chckbxRepetirPorDas.addActionListener(this);

		ButtonGroup bg = new ButtonGroup();
		bg.add(chckbxRepetirPorDas);
		bg.add(chckbxNoRepetir);
		bg.add(chckbxRepetirPorIntervalo);

		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.setBounds(166, 308, 117, 29);
		btnAgregar.setActionCommand(AGREGAR);
		btnAgregar.addActionListener(this);
		getContentPane().add(btnAgregar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setActionCommand(CANCELAR);
		btnCancelar.addActionListener(this);
		btnCancelar.setBounds(295, 308, 117, 29);
		getContentPane().add(btnCancelar);

		lblChosenfile = new JLabel("");
		lblChosenfile.setBounds(30, 141, 205, 16);
		getContentPane().add(lblChosenfile);


	}

	private void managePanelChange(String type){
		

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand()==ARCHIVO){
			JFileChooser fc = new JFileChooser();

			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"Archivos de sonido WAV o MP3", "wav", "mp3");
			fc.setFileFilter(filter);
			int returnVal = fc.showOpenDialog(this);
			System.out.println(returnVal);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				chosen = fc.getSelectedFile();
				lblChosenfile.setText(chosen.getName());
				//This is where a real application would open the file.
			}else{
				chosen=null;
				lblChosenfile.setText("");
			}
		} else if (e.getActionCommand()==AGREGAR){
			if(chosen==null){
				System.out.println("whaat");
				JOptionPane.showMessageDialog(this, "Debe escoger un archivo de sonido","Capos incompletos", JOptionPane.WARNING_MESSAGE);
			}
			System.out.println(chosen);

		} else if (e.getActionCommand()==CANCELAR){
			this.dispose();

		} else if (e.getActionCommand()==ONCE){

		} else if(e.getActionCommand()==LOOP){

		} else if(e.getActionCommand()==LIST){

		}

	}
}