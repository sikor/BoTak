package gui.guielements;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import tspsolver.GuiActionsListener;
import tspsolver.ProblemSolution;
import tspsolver.algorithms.Algorithm;
import tspsolver.algorithms.IterationResult;
import tspsolver.model.interfaces.IModelChangeListener;
import tspsolver.model.interfaces.IProblemSolution;
import tspsolver.utils.ImagesUtils;

public class MainWindow extends JFrame implements IModelChangeListener{
	
	private Map<IProblemSolution, AlgorithmMainPanel> algorithmPanels; 
	private List<AlgorithmMainPanel> unmapedAlgorithmPanels;
	private JTabbedPane tabb;
	private GuiActionsListener guiActionListener;
	private static int algoCounter = 0;
	public MainWindow (){
		super();
		this.setSize(900,700);
		algorithmPanels = new HashMap<IProblemSolution,AlgorithmMainPanel>();
		unmapedAlgorithmPanels = new ArrayList<AlgorithmMainPanel>();
		JPanel buttonPanel = new JPanel(new FlowLayout());		
		
		JButton cockButton = new JButton(ImagesUtils.getImageIcon("images/add-cockroach.png"));
		JButton geneButton = new JButton(ImagesUtils.getImageIcon("images/add-genetic.png"));
		
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
		setResizable(false);
		setVisible(true);
	}
	public void setGuiActionListener(GuiActionsListener gal){
		this.guiActionListener = gal;
	}

	@Override
	public void newSolutionAdded(IProblemSolution problemSolution) {
		algorithmPanels.put(problemSolution, (AlgorithmMainPanel) tabb.getSelectedComponent());
		((AlgorithmMainPanel) tabb.getSelectedComponent()).newSolutionAdded(problemSolution);
	}
	

	@Override
	public void newIterationResultAdded(IProblemSolution problemSolution,
			IterationResult iterationResult) {
		algorithmPanels.get(problemSolution).newIterationResultAdded(problemSolution, iterationResult);
	}

	@Override
	public void solutionFinished(IProblemSolution problemSolution) {
		algorithmPanels.get(problemSolution).solutionFinished(problemSolution);
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
			AlgorithmMainPanel algo = new AlgorithmMainPanel(algorithm, guiActionListener);
			unmapedAlgorithmPanels.add(algo);
			algo.setName(algorithm.name() +" #" + algoCounter++);
			tabb.add(algo);
			tabb.setSelectedComponent(algo);
			MainWindow.this.repaint();
		}
		
	}
	

}
