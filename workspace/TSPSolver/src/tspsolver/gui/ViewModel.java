package tspsolver.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import org.jfree.chart.JFreeChart;

/*
Szymku:
Do napisania
1. Sam projekt wizualny gui
2. Mo¿liwoœæ odczytu/zapisu do pliku
3. Wyœwietlenie wyniku w postaci po³¹czonych punktów na grafie
4. Mo¿liwoœc tworzenia nowych punktów na grafie
5. Konwerter wspó³rzêdnych punktów na tablicê odleg³oœci
*/

public class ViewModel {
	
/* Fields */
	
	private JFrame frameMain;
	
	/* Top Menu */
	private JPanel panelTop;
	private JButton buttonGenetic;
	private JButton buttonCockroach;
	private JButton buttonSave;
	private JButton buttonLoad;
	private ImageIcon imageGenetic;
	private ImageIcon imageCockroach;
	/* End of Top Menu */
	
	/* Middle Panel */
	private JPanel panelMid;
	private MapPanel main;
	/* End of Middle Panel */
	
	/* Right Menu */
	private JPanel panelMenu;
	private JPanel panelComboBox;
	private JComboBox <String> comboBox;
	private JPanel panelCards;
	private JPanel panelCockroach;
	private JPanel panelGenetic;
	private JButton menu;
	private JTabbedPane tab;
	/* End of Right Menu */
	
/* Constructors */
	
	public ViewModel ( ) {
	
		this.frameMain = new JFrame("TspSolver");
		frameMain.setLayout(new BorderLayout(2,2));
		frameMain.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frameMain.setVisible(true);
		frameMain.setMinimumSize(new Dimension(800,600));
		
	/* Top Menu */
		
		this.panelTop = new JPanel();
		panelTop.setLayout(new FlowLayout(FlowLayout.LEFT));
		frameMain.add(panelTop,BorderLayout.NORTH);

		imageGenetic = createImageIcon("images/genetic.jpg","Algorytm Genetyczny");
		this.buttonGenetic = new JButton("Oblicz",imageGenetic);
		buttonGenetic.setFont(new Font("Verdana", Font.BOLD, 12));
		buttonGenetic.setPreferredSize(new Dimension(100,100));
		panelTop.add(buttonGenetic);
		
		imageCockroach = createImageIcon("images/cockroach.jpg","Algorytm Karaluchów");
		this.buttonCockroach = new JButton("Oblicz",imageCockroach);
		buttonCockroach.setFont( new Font("Verdana", Font.BOLD, 12));
		buttonCockroach.setPreferredSize(new Dimension(100,100));
		panelTop.add(buttonCockroach);

		this.buttonSave = new JButton("Zapisz");
		buttonSave.setFont( new Font("Verdana", Font.BOLD, 12));
		buttonSave.setPreferredSize(new Dimension(100,100));
		panelTop.add(buttonSave);
		
		this.buttonLoad = new JButton("Wczytaj");
		buttonLoad.setFont( new Font("Verdana", Font.BOLD, 12));
		buttonLoad.setPreferredSize(new Dimension(100,100));
		panelTop.add(buttonLoad);

	/* End of Top Menu */

		
	/* Middle Panel */
		
		this.panelMid = new JPanel();
		panelMid.setLayout(new GridLayout());
		frameMain.add(panelMid,BorderLayout.CENTER);
	
		this.main = new MapPanel();
		panelMid.add(main);
	
	/* End of Middle Panel */
		
		
	/* Right Menu */
		
		this.panelMenu = new JPanel();
		panelMenu.setLayout(new FlowLayout());
		panelMenu.setPreferredSize(new Dimension(250,300));
		frameMain.add(panelMenu,BorderLayout.EAST);
		
		this.tab = new JTabbedPane(JTabbedPane.RIGHT,JTabbedPane.WRAP_TAB_LAYOUT);
		panelMenu.add(tab);

		this.panelCards = new JPanel();
		panelCards.setLayout(new CardLayout());
		panelMenu.add(panelCards);

		this.panelGenetic = new JPanel();
		panelGenetic.setName("Genetic    ");
		panelGenetic.setLayout(new GridLayout());
		panelCards.add(panelGenetic,"Genetic");
		JButton test1 = new JButton("TEST !");
		tab.addTab("Genetic",panelGenetic);
		
		this.panelCockroach = new JPanel();
		panelCockroach.setName("Cockroach");
		panelCockroach.setLayout(new GridLayout());
		panelCards.add(panelCockroach,"Cockroach");
		JButton test2 = new JButton("TEST @");
		tab.addTab("Cockroach",panelCockroach);
		
	/* End of Right Menu */


	}
	
/* Methods */
	
	protected ImageIcon createImageIcon ( String path, String description ) {
	    java.net.URL imgURL = getClass().getResource(path);
	    if ( imgURL != null ) return new ImageIcon(imgURL,description); 
	    else {
	        System.err.println("Couldn't find file: " + path);
	        return null;
	    }
	}
	
	public static void main ( String [] args ) {
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
        		new ViewModel();
            }
        });
	}
}