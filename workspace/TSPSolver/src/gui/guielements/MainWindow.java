package gui.guielements;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import tspsolver.algorithms.Algorithm;
import tspsolver.algorithms.IterationResult;
import tspsolver.model.interfaces.IModelChangeListener;
import tspsolver.model.interfaces.IProblemSolution;

public class MainWindow extends JFrame implements IModelChangeListener{
	
	private Map<Integer, AlgorithmMainPanel> algorithmPanels; 
	private JTabbedPane tabb;
	private static int algoCounter = 0;
	public MainWindow (){
		super();
		this.setSize(900,500);
		algorithmPanels = new HashMap<Integer,AlgorithmMainPanel>();
		JPanel buttonPanel = new JPanel(new FlowLayout());		
//		ImageIcon cockImage = createImageIcon("images/cockroach.jpg");
//		ImageIcon geneImage = createImageIcon("images/genetic.jpg");
		
		JButton cockButton = new JButton("cock");
		JButton geneButton = new JButton("gene");
		
		cockButton.setActionCommand("add_cockroach");
		geneButton.setActionCommand("add_genetic");
		
		cockButton.setToolTipText("add cockroach calculating tab");
		cockButton.addActionListener(new AddAlgoButton(Algorithm.Cockroach));
		geneButton.setToolTipText("add genetic calculating tab");
		geneButton.addActionListener(new AddAlgoButton(Algorithm.Genetic));
		buttonPanel.add(cockButton);
		buttonPanel.add(geneButton);
		
		this.add(buttonPanel, BorderLayout.NORTH);
		tabb = new JTabbedPane();
		//tabb.add(new AlgorithmMainPanel(Algorithm.Cockroach));
		this.add(tabb, BorderLayout.CENTER);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void newSolutionAdded(IProblemSolution problemSolution) {
		System.out.println("New SolutionAdded");
	}

	@Override
	public void newIterationResultAdded(IProblemSolution problemSolution,
			IterationResult iterationResult) {
		System.out.println("new Iteration result");
	}

	@Override
	public void solutionFinished(IProblemSolution problemSolution) {
		System.out.println("solution finished");
	}
	
	protected static ImageIcon createImageIcon(String path) {
	    java.net.URL imgURL = MainWindow.class.getResource(path);
	    return new ImageIcon(imgURL);
	}
	
	private class AddAlgoButton implements ActionListener{
		Algorithm algorithm;
		AddAlgoButton(Algorithm algorithm){
			this.algorithm = algorithm;
		}
		public void actionPerformed(ActionEvent arg0) {
			AlgorithmMainPanel algo = new AlgorithmMainPanel(algorithm);
			algorithmPanels.put(algoCounter++, algo);
			algo.setName("Genetic #" + algoCounter);
			tabb.add(algo);
			tabb.setSelectedComponent(algo);
			MainWindow.this.repaint();
		}
		
	}
	

}
