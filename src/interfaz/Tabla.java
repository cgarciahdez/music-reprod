package interfaz;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

import javafx.scene.layout.Pane;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import javazoom.jl.player.Player;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import world.List;
import world.Reprod;
import world.WeeklyLoop;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class Tabla extends JFrame implements ActionListener {

	public static final String AGREGAR = "Agregar";
	public static final String SALVAR = "Savar";
	public static final String ABRIR = "Abrir";
	public static final String AYUDA = "Ayuda";

	private Reprod mundo;
	private Map<String, List> lists;
	private JPanel panel;
	private JPanel panel2;
	private JScrollPane scrollPane;
	private JTable table;
	private Tabla frame;
	private int currentlyPlaying = -1;

	private AudioStream as;
	private Player p;

	public Tabla (){

		try{
			mundo = new Reprod();
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Hubo un problema cargando la aplicaciï¿½n");
			super.dispose();
		}

		
		frame = this;

		crearVentana();

	}

	private void crearVentana(){
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		setMinimumSize(new Dimension(730, 520));
		BufferedImage icon = null;
		try {
			//		    img = ImageIO.read(new File("./imgs/logo.png"));));
			icon = ImageIO.read(Tabla.class.getResource("/resources/Icono.png"));
			System.out.println(icon.getHeight());
			System.out.println(icon.getWidth());
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		lists = mundo.getLists();

		setMinimumSize(new Dimension(900, 429));

		String[] columnNames = {"Nombre", "Archivo", "Hora", "Días de reproducción","Repetición", "Hora fin", "Editar", "Borrar","Muestra"};

		Object[][] data = new Object[lists.keySet().size()][9];

		java.util.List<List> listValues = new ArrayList<List>(lists.values());
		Collections.sort(listValues, new Comparator<List>() {

			@Override
			public int compare(List o1, List o2) {
				// TODO Auto-generated method stub
				LocalTime lt1 = LocalDateTime.ofInstant(o1.getTime().toInstant(), ZoneId.systemDefault()).toLocalTime();
				LocalTime lt2 = LocalDateTime.ofInstant(o2.getTime().toInstant(), ZoneId.systemDefault()).toLocalTime();
				return lt1.compareTo(lt2);
			}
		});
		int i =0;
		
		try {
			BufferedImage imgthrash = ImageIO.read((Tabla.class.getResource("/resources/trash1600 copy.png")));
			Image ithrash = imgthrash.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
			ImageIcon trash = new ImageIcon(ithrash);
			
			BufferedImage imgEdit = ImageIO.read((Tabla.class.getResource("/resources/61456.png")));
			Image iEdit = imgEdit.getScaledInstance(13, 13, Image.SCALE_SMOOTH);
			ImageIcon edit = new ImageIcon(iEdit);
			
			BufferedImage imgPlay = ImageIO.read((Tabla.class.getResource("/resources/icon-play-128.png")));
			Image iPlay = imgPlay.getScaledInstance(13, 13, Image.SCALE_SMOOTH);
			ImageIcon play = new ImageIcon(iPlay);
			
			BufferedImage imgStop = ImageIO.read((Tabla.class.getResource("/resources/Multimedia__stop-512.png")));
			Image iStop = imgStop.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
			ImageIcon stop = new ImageIcon(iStop);
			
			for(List list: listValues){
				System.out.println(list.getName());
				Object[] listDate = new Object[9];
				listDate[0]=list.getName();
				listDate[1]=list.getVoiceFile().split("\\\\")[list.getVoiceFile().split("\\\\").length-1];
				listDate[2] = LocalDateTime.ofInstant(list.getTime().toInstant(), ZoneId.systemDefault()).toLocalTime();
				StringBuilder sb = new StringBuilder();
				boolean[] dias = list.getDays();
				String[] ds ={"Do","Lu", "Ma", "Mi","Ju","Vi","Sa"};
				for (int j = 0;j<dias.length;j++){
					if(dias[j]){
						sb.append(ds[j]+" ");
					}	
					else {
						sb.append("__ ");
					}
				}
				listDate[3] = sb.toString();
				listDate[4] = list.getMetric()==null?"-":"Cada "+list.getCadaN()+" "+list.getMetric().getName();
				listDate[5] = list.getEnd()==null?"-":LocalDateTime.ofInstant(list.getEnd().toInstant(), ZoneId.systemDefault()).toLocalTime();
				listDate[6] = edit;
				listDate[7] = trash;
				listDate[8] = currentlyPlaying==i?stop:play;
				data[i]=listDate;
				i++;
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		//		Object[][] data = {{"nom","arcfsdjhfjshfjkshfksjfhjskfskjh","hora","dias","rep","fin","edit","erae"},{"nom","arcfsdjhfjshfjkshfksjfhjskfskjh","hora","dias","rep","fin","eee","erae"}};

		table = new JTable();
		MyTableModel tableModel = new MyTableModel(data, columnNames);

		table.setModel(tableModel);

		Action delete = new AbstractAction()
		{
			public void actionPerformed(ActionEvent e)
			{
				//		        JTable table = (JTable)e.getSource();
				int resp = JOptionPane.showConfirmDialog(frame, "Esta seguro que desea borrar esta reproducción? No podrá recuperarla","Confirmación",JOptionPane.YES_NO_OPTION);

				if(resp == JOptionPane.YES_OPTION){

					int modelRow = Integer.valueOf( e.getActionCommand() );


					String name = (String)table.getModel().getValueAt(modelRow, 0);
					System.out.println(name);

					mundo.eraseList(name);
					refresh();
				}
			}
		};

		Action edit = new AbstractAction()
		{
			public void actionPerformed(ActionEvent e)
			{
				//		        JTable table = (JTable)e.getSource();
				List list = mundo.getListByName((String)table.getModel().getValueAt(Integer.valueOf(e.getActionCommand()), 0));
				DialogoAdd da = new DialogoAdd(frame, mundo);
				da.setPast(list);
				da.setVisible(true);
			}
		};

		Action playStop = new AbstractAction()
		{
			public void actionPerformed(ActionEvent e)
			{
				//		        JTable table = (JTable)e.getSource();
				int row = Integer.valueOf(e.getActionCommand());
				List list = mundo.getListByName((String)table.getModel().getValueAt(row, 0));
				if(p!=null){
					p.close();
					p=null;
				}
				if(as!=null){
					AudioPlayer.player.stop(as);
					as = null;
				}
				System.out.println(row);
				System.out.println(currentlyPlaying);
				if(row!=currentlyPlaying){
					System.out.println("hi");
					try{
						String voiceFile = list.getVoiceFile();
						if(voiceFile.endsWith(".mp3")){
							System.out.println("mp3");
							System.out.println(voiceFile);
							BufferedInputStream bs = new BufferedInputStream(new FileInputStream(voiceFile));
							p = new Player(bs);
							new Thread() {
								@Override
								public void run() {
									try {
										System.out.println("thread..");
										p.play();
										if(p!=null){

											p=null;
											currentlyPlaying=-1;
											refresh();
										}

									} catch (Exception e) {
										System.err.printf("%s\n", e.getMessage());
									}
								}
							}.start();
						}
						else{

							InputStream in = new FileInputStream(voiceFile);
							as = new AudioStream(in);

							new Thread() {
								@Override
								public void run() {
									try {
										System.out.println("thread..");

										AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(voiceFile));
										AudioFormat format = audioInputStream.getFormat();
										long frames = audioInputStream.getFrameLength();
										double durationInSeconds = (frames+0.0) / format.getFrameRate();
										System.out.println("duration: "+((long)durationInSeconds));
										AudioPlayer.player.start(as);
										sleep((long)(durationInSeconds*1000));
										System.out.println(as);
										if(as!=null){
											as=null;
											currentlyPlaying=-1;
											refresh();
											System.out.println("help");
										}

									} catch (Exception e) {
										System.err.printf("%s\n", e.getMessage());
									}
								}
							}.start();

						}
					}catch(Exception ex){
						JOptionPane.showMessageDialog(null, "Hubo un problema reproduciendo el archivo de sonido. Asegúrese que el archivo que escogió es un archivo de sonido váildo y que se encuentra en la ruta original.");
					}
					currentlyPlaying=row;
				}else{
					currentlyPlaying=-1;
				}
				table.getModel().setValueAt("Stop", row, 1);


				((MyTableModel) table.getModel()).fireTableDataChanged();
				refresh();
				//		    	panel.revalidate();
				//		    	panel.repaint();

			}
		};

		ButtonColumn buttonColumn = new ButtonColumn(table, delete, 7);
		buttonColumn.setMnemonic(KeyEvent.VK_D);

		ButtonColumn bc = new ButtonColumn(table, edit, 6);
		bc.setMnemonic(KeyEvent.VK_S);

		ButtonColumn bc2 = new ButtonColumn(table, playStop, 8);


		//		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//		table.getColumnModel().getColumn(1).setMinWidth(150);
//		table.getColumnModel().getColumn(1).setWidth(150);
		table.getColumnModel().getColumn(1).setPreferredWidth(150);
//		table.getColumnModel().getColumn(2).setMinWidth(50);
//		table.getColumnModel().getColumn(2).setWidth(50);
		table.getColumnModel().getColumn(2).setPreferredWidth(50);
		table.getColumnModel().getColumn(0).setPreferredWidth(150);
		table.getColumnModel().getColumn(3).setPreferredWidth(170);
		table.getColumnModel().getColumn(4).setPreferredWidth(80);
		table.getColumnModel().getColumn(5).setPreferredWidth(50);
		table.getColumnModel().getColumn(6).setPreferredWidth(40);
		table.getColumnModel().getColumn(7).setPreferredWidth(40);
		table.getColumnModel().getColumn(8).setPreferredWidth(40);
		
		scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);

		panel2 = new JPanel();
		panel2.setMaximumSize(new Dimension(730, 300));
		panel2.setLayout(new BorderLayout());

		panel2.add(scrollPane, BorderLayout.CENTER);

		getContentPane().add(panel2, BorderLayout.CENTER);
		
		JPanel panelTit = new JPanel();
		JLabel titulo = new JLabel("Mensajes programados");
		titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		titulo.setFont(new Font(titulo.getName(), Font.BOLD, 20));

		panelTit.add(titulo);
		
		
		getContentPane().add(panelTit,BorderLayout.NORTH);

		panel = new JPanel();
		panel.setMinimumSize(new Dimension(600, 400));
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{900, 0};
		gbl_panel.rowHeights = new int[] {50, 50, 0};
		gbl_panel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		getContentPane().add(panel, BorderLayout.SOUTH);	

		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(this);
		btnAgregar.setActionCommand(AGREGAR);
		GridBagConstraints gbc_btnAgregar = new GridBagConstraints();
		gbc_btnAgregar.insets = new Insets(0, 0, 5, 0);
		gbc_btnAgregar.gridx = 0;
		gbc_btnAgregar.gridy = 0;
		panel.add(btnAgregar, gbc_btnAgregar);

		JPanel panelAux  = new JPanel();
		GridBagLayout gbl_panelAux = new GridBagLayout();
		gbl_panelAux.columnWidths = new int[]{450, 450, 0};
		gbl_panelAux.rowHeights = new int[]{29, 0};
		gbl_panelAux.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panelAux.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panelAux.setLayout(gbl_panelAux);

		JButton btnAbrir = new JButton(" Cargar ");
		btnAbrir.addActionListener(this);
		btnAbrir.setActionCommand(ABRIR);
		GridBagConstraints gbc_btnAbrir = new GridBagConstraints();
		gbc_btnAbrir.anchor = GridBagConstraints.EAST;
		gbc_btnAbrir.fill = GridBagConstraints.VERTICAL;
		gbc_btnAbrir.insets = new Insets(0, 0, 0, 5);
		gbc_btnAbrir.gridx = 0;
		gbc_btnAbrir.gridy = 0;
		panelAux.add(btnAbrir, gbc_btnAbrir);

		GridBagConstraints gbc_panelAux = new GridBagConstraints();
		gbc_panelAux.anchor = GridBagConstraints.NORTH;
		gbc_panelAux.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelAux.gridx = 0;
		gbc_panelAux.gridy = 1;
		panel.add(panelAux, gbc_panelAux);

		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(this);
		btnGuardar.setActionCommand(SALVAR);
		GridBagConstraints gbc_btnGuardar = new GridBagConstraints();
		gbc_btnGuardar.anchor = GridBagConstraints.WEST;
		gbc_btnGuardar.fill = GridBagConstraints.VERTICAL;
		gbc_btnGuardar.gridx = 1;
		gbc_btnGuardar.gridy = 0;
		panelAux.add(btnGuardar, gbc_btnGuardar);
		
		JMenuBar mb = new JMenuBar();
		JMenu menu = new JMenu("Ayuda");
		JMenuItem mi = new JMenuItem("haga click aquí para acceder a la ayuda");
		mb.add(menu);
		menu.add(mi);
		mi.addActionListener(this);
		mi.setActionCommand(AYUDA);
		
		
		this.setJMenuBar(mb);
		
		setIconImage(new ImageIcon(icon).getImage());

		
		
		
		




	}

	class MyTableModel extends AbstractTableModel {
		private String[] columnNames;
		private Object[][] data;

		public MyTableModel(Object[][] data, String[] columnNames){
			this.data = data;
			this.columnNames = columnNames;
		}

		public int getColumnCount() {
			return columnNames.length;
		}

		public int getRowCount() {
			return data.length;
		}

		public String getColumnName(int col) {
			return columnNames[col];
		}

		public Object getValueAt(int row, int col) {
			return data[row][col];
		}

		/*
		 * JTable uses this method to determine the default renderer/
		 * editor for each cell.  If we didn't implement this method,
		 * then the last column would contain text ("true"/"false"),
		 * rather than a check box.
		 */
		public Class getColumnClass(int c) {
			return getValueAt(0, c).getClass();
		}

		/*
		 * Don't need to implement this method unless your table's
		 * editable.
		 */
		public boolean isCellEditable(int row, int col) {
			//Note that the data/cell address is constant,
			//no matter where the cell appears onscreen.
			if(col==6||col==7||col==8)return true;
			return false;
		}



		private void printDebugData() {
			int numRows = getRowCount();
			int numCols = getColumnCount();

			for (int i=0; i < numRows; i++) {
				System.out.print("    row " + i + ":");
				for (int j=0; j < numCols; j++) {
					System.out.print("  " + data[i][j]);
				}
				System.out.println();
			}
			System.out.println("--------------------------");
		}
	}




	public void dispose( )
	{
		try
		{
			System.out.println("salvÃ©");
			mundo.salvar( );
			super.dispose( );
			System.exit(0);
		}
		catch( Exception e )
		{
			e.printStackTrace();
			setVisible( true );
			int respuesta = JOptionPane.showConfirmDialog( this,"Problemas salvando la informaciï¿½n:\n" + e.getMessage( ) +
					"\nï¿½Quiere cerrar el programa sin salvar?",
					"Error", JOptionPane.YES_NO_OPTION );
			if( respuesta == JOptionPane.YES_OPTION )
			{
				super.dispose( );
				System.exit(0);
			}
		}
	}

	public void refresh(){
		//		removeAll();
		getContentPane().remove(panel);
		getContentPane().remove(panel2);
		panel2.removeAll();
		panel.removeAll();
		panel2=null;
		panel=null;
		crearVentana();

		//		((MyTableModel) table.getModel()).fireTableDataChanged();
		panel2.repaint();
		panel2.revalidate();
		panel2.validate();
		panel.repaint();
		panel.revalidate();
		panel.validate();
		//		repaint();
		//		revalidate();
		//		validate();
		//		pack();

	}

	public static void main(String[] args) {
		Tabla t = new Tabla();
		t.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand()==AGREGAR){
			DialogoAdd da = new DialogoAdd(this, mundo);
			da.setVisible(true);

		}
		else if(e.getActionCommand()==SALVAR){
			JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(new File(Reprod.PATH+Reprod.SEP+"sesiones"));
			int rVal = fc.showSaveDialog(this);
			
			if (rVal == JFileChooser.APPROVE_OPTION) {
				System.out.println(fc.getSelectedFile().getName());
				System.out.println(fc.getCurrentDirectory().toString());
				try {
					mundo.salvar(fc.getCurrentDirectory().toString()+File.separator+fc.getSelectedFile().getName());
					//					refresh();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(this, "Hubo un problema guardando el archivo.");
				}
			}
			//		      if (rVal == JFileChooser.CANCEL_OPTION) {
			//		        filename.setText("You pressed cancel");
			//		        dir.setText("");
			//		      }
		}

		else if (e.getActionCommand()==ABRIR){
			JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(new File(Reprod.PATH+Reprod.SEP+"sesiones"));
			// Demonstrate "Open" dialog:
			int rVal = fc.showOpenDialog(this);
			if (rVal == JFileChooser.APPROVE_OPTION) {
				//		        filename.setText(c.getSelectedFile().getName());
				//		        dir.setText(c.getCurrentDirectory().toString());

				try {
					mundo.load(fc.getCurrentDirectory().toString()+File.separator+fc.getSelectedFile().getName());
					refresh();
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(this, "Hubo un problema cargando el archivo. Asegúrese que sea un archivo válido que guardó con anterioridad");
				}
			}
			//		      if (rVal == JFileChooser.CANCEL_OPTION) {
			//		        filename.setText("You pressed cancel");
			//		        dir.setText("");
			//		      }
		}
		else if (e.getActionCommand()==AYUDA) {
			JOptionPane.showMessageDialog(this, "Para agregar un nuevo mensaje, haga click en el botón de agregar. La última sesión es guardada de manera automática"
				+ ", \npero si desea guardar una sesión específica en otro archivo puede hacerlo haciendo click en el botón guardar. Para cargar una sesión "
				+ "\nguardada anteriormente, haga click en el botón cargar. Para escuchar el archivo de sonido que eligió para un mensaje,"
				+ " \nhaga click en el botón de play en la columna Muestra de la fila correspondiente. \n \n"
				+ "En el diálogo de agregar, puede escoger una fecha si desea que la reproducción comience en el futuro. La fecha no"
				+ "\ntiene que ser uno de los días de la semana específicados; la reproducción comenzará en el siguiente día válido"
				+ "\ndespués de la fecha indicada. Si la fecha se deja en blanco, la reproducción empezará el siguiente día válido"
				+ "\ndespués del momento en que se añada el mensaje. Si se escoge repetir el mensaje, y no escoje una hora final, este"
				+ "\nse repetirá hasta el final del día.");
		}

	}




}
