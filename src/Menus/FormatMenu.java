package Menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import say.swing.JFontChooser;
import Components.CMenu;
import Components.CMenuItem;
import Components.CTabbedPane;
import Gui.JEditor;
import OptionDialogs.ColorOptionDialog;
import Utility.ImageLoader;

import java.util.Date;

public class FormatMenu extends CMenu{

	private static final long serialVersionUID = 1L;
	public static CMenuItem chooseFont,colorOptions;
	
	// Initialise the variable
	public static CMenuItem addTimestamps;

	public FormatMenu(String text, char Mnmonic) {
		super(text, Mnmonic);
		init();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				addToMenu();
				addActions();
				addIcons();
			}
		}).start();

	}
	
	public void init(){
		chooseFont = new CMenuItem("Choose font", "choose the text font", 'C', KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK + InputEvent.ALT_DOWN_MASK));
		colorOptions = new CMenuItem("Color options", "configure color options", 'O', null);
		
		// Initialise the button
		addTimestamps = new CMenuItem("Add Timestamps", "Adds a timestamp to the start and end of the file", 'T', null);
	}
	
	public void addToMenu(){
		add(chooseFont);
		add(colorOptions);
		
		// Add the button to the format menu
		add(addTimestamps);
		
		addSeparator();
		add(new HighlightingModeMenu("Highlighting mode", 'H'));
	}
	
	public void addIcons(){
		chooseFont.setIcon(ImageLoader.loadImage("images_small/font.png"));
		colorOptions.setIcon(ImageLoader.loadImage("images_small/coloroptions.png"));
	}
	
	public void addActions(){
		chooseFont.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFontChooser fchooser = new JFontChooser();
				fchooser.setSelectedFont(CTabbedPane.getInstance().getPanel().getTextArea().getFont());
				int result = fchooser.showDialog(JEditor.frame);
				
				if(result == JFontChooser.OK_OPTION){
					CTabbedPane.getInstance().getPanel().getTextArea().setFont(fchooser.getSelectedFont());
				}
				
			}
		});
		
		colorOptions.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new ColorOptionDialog().setVisible(true);
			}
		});
		
		// Add the time stamps to the text area
		addTimestamps.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Date time = new Date();
				String timeText = time.toString();
				
				String oldText = CTabbedPane.getInstance().getPanel().getTextArea().getText();
//				String newText = timeText + "\n" + oldText + "\n" + timeText;
				String newText = String.format("%s\n\n%s\n\n%s", timeText, oldText, timeText);
				
				CTabbedPane.getInstance().getPanel().getTextArea().setText(newText);
			}
		});
	}
	
}
