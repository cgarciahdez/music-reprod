package interfaz;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.ImageIcon;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.util.Calendar;
import java.util.Date; 
import java.util.Properties;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;

import world.List;
import world.Metric;
import world.Reprod;

import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings.TimeIncrement;
import java.awt.Font;

public class DialogoAdd extends JDialog implements ActionListener {
	private JTextField nombre;
	private File chosen;
	private JCheckBox[] dias;
	private String type;

	public static final String ARCHIVO = "Archivos";
	public static final String AGREGAR = "Agregar";
	public static final String CANCELAR = "Cancelar";
	public static final String ONCE = "Once";
	public static final String LOOP = "Loop";
	public static final String LIST = "List";
	public static final String REPETIR = "Repetir";
	public static final String END = "End";
	public static final Integer[] HORAS = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24};
	public static final Integer[] MINUTOS = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,
		36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59};

	private JTextField cadaN;
	private Tabla frame;
	private Reprod mundo;
	private JComboBox<Metric> metric;
	private JLabel lblRepetirCada;
	private DatePicker datePicker;
	private TimePicker timePicker;
	private JLabel lblDasDeLa;
	private JCheckBox chckbxRepetir;
	private JLabel lblEnd;
	private TimePicker timePicker_1;
	private JCheckBox chckbxFin;
	
	private boolean edit = false;
	private String oldName;
	private JTextField textFieldArchivo;
	private JButton btnAgregar;
	private JLabel titulo;


	public DialogoAdd(Tabla frame, Reprod mundo) {
		
		this.mundo = mundo;

		this.frame = frame;
		getContentPane().setLayout(null);

		setMinimumSize(new Dimension(723, 500));

		titulo = new JLabel("Agregar nuevo Mensaje");
		titulo.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		titulo.setBounds(246, 6, 246, 50);
		getContentPane().add(titulo);

		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(93, 63, 67, 38);
		getContentPane().add(lblNombre);

		nombre = new JTextField();
		nombre.setBounds(168, 68, 134, 28);
		getContentPane().add(nombre);
		nombre.setColumns(10);

		JLabel lblArchivoDeSonido = new JLabel("Archivo de sonido");
		lblArchivoDeSonido.setBounds(43, 112, 117, 38);
		getContentPane().add(lblArchivoDeSonido);
		
		try {

			BufferedImage imgFolder = ImageIO.read((Tabla.class.getResource("/resources/icon-folder-128.png")));
			Image iFolder = imgFolder.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			ImageIcon folder = new ImageIcon(iFolder);
			
			JButton btnChooseFile = new JButton(folder);
			btnChooseFile.setBounds(261, 114, 34, 29);
			btnChooseFile.setActionCommand(ARCHIVO);
			btnChooseFile.addActionListener(this);
			getContentPane().add(btnChooseFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		TimePickerSettings timeSettings = new TimePickerSettings();
		timeSettings.setDisplaySpinnerButtons(true);
		timeSettings.setInitialTimeToNow();
		timeSettings.generatePotentialMenuTimes(TimeIncrement.FiveMinutes, null, null);

		dias = new JCheckBox[7];


		btnAgregar = new JButton("Agregar");
		btnAgregar.setBounds(261, 308, 117, 29);
		btnAgregar.setActionCommand(AGREGAR);
		btnAgregar.addActionListener(this);
		getContentPane().add(btnAgregar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setActionCommand(CANCELAR);
		btnCancelar.addActionListener(this);
		btnCancelar.setBounds(390, 308, 117, 29);
		getContentPane().add(btnCancelar);

		lblDasDeLa = new JLabel("D\u00EDas de la semana para reproducir");
		lblDasDeLa.setBounds(23, 188, 245, 16);
		getContentPane().add(lblDasDeLa);

		JCheckBox chckbxL = new JCheckBox("L");
		chckbxL.setBounds(26, 205, 37, 28);
		getContentPane().add(chckbxL);
		dias[1]=chckbxL;

		JCheckBox chckbxM = new JCheckBox("M");
		chckbxM.setBounds(65, 205, 41, 28);
		getContentPane().add(chckbxM);
		dias[2]=chckbxM;

		JCheckBox chckbxI = new JCheckBox("M");
		chckbxI.setBounds(106, 205, 38, 28);
		getContentPane().add(chckbxI);
		dias[3]=chckbxI;

		JCheckBox chckbxJ = new JCheckBox("J");
		chckbxJ.setBounds(145, 205, 36, 28);
		getContentPane().add(chckbxJ);
		dias[4]=chckbxJ;

		JCheckBox chckbxV = new JCheckBox("V");
		chckbxV.setBounds(182, 205, 34, 28);
		getContentPane().add(chckbxV);
		dias[5]=chckbxV;

		JCheckBox chckbxS = new JCheckBox("S");
		chckbxS.setBounds(221, 205, 34, 28);
		getContentPane().add(chckbxS);
		dias[6]=chckbxS;

		JCheckBox chckbxD = new JCheckBox("D");
		chckbxD.setBounds(258, 205, 44, 28);
		getContentPane().add(chckbxD);
		dias[0]=chckbxD;

		lblRepetirCada = new JLabel("cada");
		lblRepetirCada.setBounds(100, 261, 34, 16);
		getContentPane().add(lblRepetirCada);

		chckbxRepetir = new JCheckBox("Repetir");
		chckbxRepetir.setBounds(23, 257, 67, 23);
		chckbxRepetir.addActionListener(this);
		chckbxRepetir.setActionCommand(REPETIR);
		getContentPane().add(chckbxRepetir);

		cadaN = new JTextField();
		cadaN.setBounds(137, 255, 44, 28);
		getContentPane().add(cadaN);
		cadaN.setColumns(10);

		Metric[] metrics = {Metric.HOUR,Metric.MINUTE, Metric.SECOND};


		metric = new JComboBox<Metric>(metrics);
//		metric = new JComboBox<>();
		metric.setBounds(189, 257, 129, 27);
		getContentPane().add(metric);
		
		try {
			BufferedImage imgCal = ImageIO.read((Tabla.class.getResource("/resources/404-200.png")));
			Image iCal = imgCal.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			ImageIcon cal = new ImageIcon(iCal);
			
			datePicker = new DatePicker();
			datePicker.setBounds(495, 69, 220, 32);
			getContentPane().add(datePicker);
			
			JButton datePickerButton = datePicker.getComponentToggleCalendarButton();
	        datePickerButton.setText("");
	        datePickerButton.setIcon(cal);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JLabel lblFecha = new JLabel("Fecha");
		lblFecha.setBounds(431, 74, 34, 16);
		getContentPane().add(lblFecha);
		timePicker = new TimePicker(timeSettings);
		timePicker.setBounds(495, 141, 220, 29);
		getContentPane().add(timePicker);

		JLabel lblHora = new JLabel("Hora Inicio");
		lblHora.setBounds(409, 147, 55, 16);
		getContentPane().add(lblHora);

		timePicker_1 = new TimePicker(timeSettings);
		timePicker_1.setBounds(495, 225, 220, 29);
		getContentPane().add(timePicker_1);

		lblEnd = new JLabel("Hora Fin");
		lblEnd.setBounds(422, 231, 61, 16);
		getContentPane().add(lblEnd);

		type=ONCE;
		
		cadaN.setEnabled(false);
		metric.setEnabled(false);
		lblRepetirCada.setEnabled(false);
		
		
		chckbxFin = new JCheckBox("Agregar hora fin");
		chckbxFin.setBounds(419, 184, 169, 23);
		getContentPane().add(chckbxFin);
		chckbxFin.setActionCommand(END);
		chckbxFin.addActionListener(this);
		
		lblEnd.setEnabled(false);
		timePicker_1.setEnabled(false);
		chckbxFin.setEnabled(false);
		
		textFieldArchivo = new JTextField();
		textFieldArchivo.setBounds(168, 113, 90, 28);
		getContentPane().add(textFieldArchivo);
		textFieldArchivo.setColumns(10);


	}
	
	public void setPast (List list){
		edit=true;
		oldName=list.getName();
		nombre.setText(list.getName());
		chosen = new File (list.getVoiceFile());
		textFieldArchivo.setText(list.getVoiceFile());
		timePicker.setTime(LocalDateTime.ofInstant(list.getTime().toInstant(), ZoneId.systemDefault()).toLocalTime());
		for(int i = 0; i< dias.length;i++){
			if (list.getDays()[i]){
				dias[i].setSelected(true);
			}else{
				dias[i].setSelected(false);
			}
		}
		if(list.getEnd()!=null){
			chckbxFin.setSelected(true);
			timePicker_1.setTime(LocalDateTime.ofInstant(list.getEnd().toInstant(), ZoneId.systemDefault()).toLocalTime());
		}
		if(list.getMetric()!=null){
			chckbxRepetir.setSelected(true);
			cadaN.setText(list.getCadaN()+"");
			metric.setSelectedItem(list.getMetric());
		}
		
		btnAgregar.setText("Guardar");
		
		titulo.setText("Editar reproducción");
		
		
		
	}

	private void managePanelChange(String type){

		if (type==ONCE){
			lblRepetirCada.setEnabled(false);
			cadaN.setEnabled(false);
			metric.setEnabled(false);
			for (JCheckBox jc : dias){
				jc.setEnabled(false);
			}
			lblDasDeLa.setEnabled(false);
		} else if(type==LOOP){
			lblRepetirCada.setEnabled(true);
			cadaN.setEnabled(true);
			metric.setEnabled(true);
			for (JCheckBox jc : dias){
				jc.setEnabled(false);
			}
			lblDasDeLa.setEnabled(false);
		} else if(type==LIST){
			lblRepetirCada.setEnabled(false);
			cadaN.setEnabled(false);
			metric.setEnabled(false);
			for (JCheckBox jc : dias){
				jc.setEnabled(true);
			}
			lblDasDeLa.setEnabled(true);
		}


	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand()==ARCHIVO){
			JFileChooser fc = new JFileChooser();
			if(chosen!=null){
				fc.setCurrentDirectory(chosen);
			}
			else{
				fc.setCurrentDirectory(new File(textFieldArchivo.getText()));
			}

			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"Archivos de sonido WAV o MP3", "wav", "mp3");
			fc.setFileFilter(filter);
			int returnVal = fc.showOpenDialog(this);
			System.out.println(returnVal);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				chosen = fc.getSelectedFile();
				textFieldArchivo.setText(chosen.getName());
				//This is where a real application would open the file.
			}else{
				chosen=null;
//				textFieldArchivo.setText("");
			}
		} else if (e.getActionCommand()==AGREGAR){
			if(edit){
				mundo.eraseList(oldName);
			}
			LocalDate date = datePicker.getDate();
			LocalTime time = timePicker.getTime();
			if(nombre.getText()==""||time==null){
				System.out.println("whaat");
				JOptionPane.showMessageDialog(this, "Debe llenar todos los campos","Campos incompletos", JOptionPane.WARNING_MESSAGE);
			}
			if(date==null){
				date = LocalDate.now();
			}
			if (chosen == null){
				File f = new File(textFieldArchivo.getText());
				if (!f.exists()){
					JOptionPane.showMessageDialog(this, "La ruta seleccionada no lleva a un archivo válido");
				}else{
					chosen = f;
				}
			}

			LocalDate nowDate = LocalDate.now();
			Date add = new Date(date.get(ChronoField.YEAR)-1900,date.getMonthValue()-1,date.getDayOfMonth(),time.getHour(),time.getMinute());

			if(type==LOOP){
//				frame.agregarLoop(((Metric)metric.getSelectedItem()), nombre.getName(), chosen.getAbsolutePath(), Integer.parseInt(cadaN.getText()), add);
			}else if(type == LIST){
				boolean days[] = new boolean[7];
				for(int i=0;i<days.length;i++){
					days[i]=dias[i].isSelected();
				}
//				frame.agregarLista(nombre.getText(), chosen.getAbsolutePath(), add, days);
			}
			if(!chckbxRepetir.isSelected()){
				boolean days[] = new boolean[7];
				boolean none = false;
				for(int i=0;i<days.length;i++){
					days[i]=dias[i].isSelected();
					none = none || dias[i].isSelected();
				}
				if(!none){
					JOptionPane.showMessageDialog(this, "Debe escoger al menos un día","Error", JOptionPane.WARNING_MESSAGE);
				}else{
					if(mundo.scheduleListNoRep(nombre.getText(), chosen.getAbsolutePath(), add, days)){
						this.dispose();
						frame.refresh();
						JOptionPane.showMessageDialog(this, "La reproducción se ha "+ (edit?"editado":"agregado") +" con éxito");
					}else{
						JOptionPane.showMessageDialog(this, "El nombre que escogió ya existe. Por favor elija otro.","Error", JOptionPane.WARNING_MESSAGE);
					}
				}
			}else if(chckbxFin.isSelected()){
				LocalTime endTime = timePicker_1.getTime();
				Date end = new Date(nowDate.get(ChronoField.YEAR)-1900,nowDate.getMonthValue()-1,nowDate.getDayOfMonth(),endTime.getHour(),endTime.getMinute());
				boolean days[] = new boolean[7];
				boolean none = false;
				for(int i=0;i<days.length;i++){
					days[i]=dias[i].isSelected();
					none = none || dias[i].isSelected();
				}
				if(!none){
					JOptionPane.showMessageDialog(this, "Debe escoger al menos un día","Error", JOptionPane.WARNING_MESSAGE);
				}else{
					if(mundo.scheduleListRep(nombre.getText(), chosen.getAbsolutePath(), add, days, Integer.parseInt(cadaN.getText()), ((Metric)metric.getSelectedItem()), end)){
						this.dispose();
						frame.refresh();
						JOptionPane.showMessageDialog(this, "La reproducción se ha "+ (edit?"editado":"agregado") +" con éxito");
					}else{
						JOptionPane.showMessageDialog(this, "El nombre que escogió ya existe. Por favor elija otro.","Error", JOptionPane.WARNING_MESSAGE);
					}
				}
			}else{
				boolean days[] = new boolean[7];
				boolean none = false;
				for(int i=0;i<days.length;i++){
					days[i]=dias[i].isSelected();
					none = none || dias[i].isSelected();
				}
				if(!none){
					JOptionPane.showMessageDialog(this, "Debe escoger al menos un día","Error", JOptionPane.WARNING_MESSAGE);
				}else{
					if(mundo.scheduleListRep(nombre.getText(), chosen.getAbsolutePath(), add, days, Integer.parseInt(cadaN.getText()), ((Metric)metric.getSelectedItem()))){
						this.dispose();
						frame.refresh();
						JOptionPane.showMessageDialog(this, "La reproducción se ha "+ (edit?"editado":"agregado") +" con éxito");
					}else{
						JOptionPane.showMessageDialog(this, "El nombre que escogió ya existe. Por favor elija otro.","Error", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
			System.out.println(chosen);

		} else if (e.getActionCommand()==CANCELAR){
			this.dispose();

		} else if (e.getActionCommand()==ONCE||e.getActionCommand()==LOOP||e.getActionCommand()==LIST){
			System.out.println(e.getActionCommand());
			managePanelChange(e.getActionCommand());
			type = e.getActionCommand();
		} else if(e.getActionCommand() == REPETIR){
			if(chckbxRepetir.isSelected()){
				cadaN.setEnabled(true);
				metric.setEnabled(true);
				lblRepetirCada.setEnabled(true);
				chckbxFin.setEnabled(true);
				
			}else{
				cadaN.setEnabled(false);
				metric.setEnabled(false);
				lblRepetirCada.setEnabled(false);
				lblEnd.setEnabled(false);
				timePicker_1.setEnabled(false);
				chckbxFin.setEnabled(false);
				chckbxFin.setSelected(false);
			}
		} else if(e.getActionCommand()==END){
			if(chckbxFin.isSelected()){
				lblEnd.setEnabled(true);
				timePicker_1.setEnabled(true); 
			}else{
				lblEnd.setEnabled(false);
				timePicker_1.setEnabled(false);
			}
		}

	}
}
