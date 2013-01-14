package gui.guielements;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.NumberFormatter;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import tspsolver.GuiActionsListener;
import tspsolver.algorithms.Algorithm;
import tspsolver.algorithms.IterationResult;
import tspsolver.algorithms.genetic.GeneticSolver;
import tspsolver.model.AlgorithmParameters;
import tspsolver.model.CockroachParameters;
import tspsolver.model.GeneticParameters;
import tspsolver.model.Problem;
import tspsolver.model.interfaces.IModelChangeListener;
import tspsolver.model.interfaces.IProblemSolution;
import tspsolver.utils.ImagesUtils;

public class AlgorithmMainPanel extends JPanel implements ActionListener, IModelChangeListener {
	private MapPanel map;
	private JFreeChart chart;
	private ChartPanel chartPanel;
	private ParametersPanel parametersPanel;
	private DefaultCategoryDataset dataset;
	private JPanel centralPanel;
//	private List<IterationResult> iterations;
	private JButton nextIt, prevIt, openPoint;
	private Algorithm algorithm;
	private int markedSolution;
	private int bestIteration;
	private JTextField markedSolutionTF;
	private JLabel markedSolutionLengthTF;
	private JLabel bestSolutionTF;
	private JLabel bestSolutionLengthTF;
	private GuiActionsListener guiActionListener;
	private IProblemSolution problemSolution;
	private Problem problem;

	// private GradientPaint gradientpaint0 = new GradientPaint(0.0F, 0.0F, new
	// Color(230, 230, 250), 0.0F, 0.0F, new Color(136, 136, 255));
	// private GradientPaint gradientpaint1 = new GradientPaint(0.0F, 0.0F, new
	// Color(240, 255, 240), 0.0F, 0.0F, new Color(136, 136, 255));

	public AlgorithmMainPanel(Algorithm al, GuiActionsListener guiActionListener) {
		super();
		map = new MapPanel();
		this.algorithm = al;
		this.guiActionListener = guiActionListener;
//		iterations = new ArrayList<IterationResult>();
		dataset = new DefaultCategoryDataset();
		// dataset.addValue(1.0, new Double(1), new Double(1));
		chart = ChartFactory.createBarChart(null, null, "result", dataset,
				PlotOrientation.VERTICAL, false, false, false);
		chartPanel = new ChartPanel(chart, 300, 100, 100, 100, 1000, 100, true,
				false, false, false, false, false);
		// chartPanel.setMaximumDrawHeight(300);
		centralPanel = new JPanel(new BorderLayout());
		this.setLayout(new BorderLayout());

		nextIt = new JButton(ImagesUtils.getImageIcon("images/right.png"));
		nextIt.setActionCommand("next");
		nextIt.addActionListener(this);
		nextIt.setEnabled(false);

		prevIt = new JButton(ImagesUtils.getImageIcon("images/left.png"));
		prevIt.addActionListener(this);
		prevIt.setActionCommand("prev");
		prevIt.setEnabled(false);

		openPoint = new JButton(ImagesUtils.getImageIcon("images/open.png"));
		openPoint.setActionCommand("open");
		openPoint.addActionListener(this);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = (new Insets(3, 3, 3, 3));
		gbc.gridwidth = 2;
		buttonPanel.add(openPoint, gbc);
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		buttonPanel.add(prevIt, gbc);
		buttonPanel.add(nextIt, gbc);
		gbc.gridy = 2;
		// gbc.gridx=1;
		gbc.gridwidth = 1;
		buttonPanel.add(new JLabel("Iteration"), gbc);
		NumberFormatter formatter = new NumberFormatter(
				NumberFormat.getIntegerInstance());
		formatter.setValueClass(Integer.class);
		
		markedSolutionLengthTF = new JLabel();
		buttonPanel.add(markedSolutionTF = new JTextField(5), gbc);
		gbc.gridy = 3;
		buttonPanel.add(new JLabel("Length:"), gbc);
		buttonPanel.add(markedSolutionLengthTF, gbc);
		gbc.gridy = 4;
		buttonPanel.add(new JLabel("Best it no:"), gbc);
		buttonPanel.add(bestSolutionTF = new JLabel(), gbc);
		gbc.gridy = 5;
		buttonPanel.add(new JLabel("Shortest Route"), gbc);
		buttonPanel.add(bestSolutionLengthTF = new JLabel("adsf"), gbc);

		markedSolutionTF.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent arg0) {

			}
			@Override
			public void focusLost(FocusEvent arg0) {

			}

		});

		centralPanel.add(buttonPanel, BorderLayout.WEST);
		centralPanel.add(chartPanel, BorderLayout.SOUTH);
		centralPanel.add(map, BorderLayout.CENTER);
		this.add(centralPanel);
		ImageIcon icon;
		if (al == Algorithm.Cockroach) {
			icon = ImagesUtils.getImageIcon("./images/cockroach.png");
		} else
			icon = ImagesUtils.getImageIcon("./images/genetic.png");
		parametersPanel = new ParametersPanel(al, icon);
		parametersPanel.setMinimumSize(new Dimension(333, 400));
		parametersPanel.addActionListener(this);
		this.add(parametersPanel, BorderLayout.EAST);
	}

	public void actionPerformed(ActionEvent e) {
		if ("Start" == e.getActionCommand()) {
			dataset.clear();
			map.setSolution(null);
			problem = map.getProblem();
			AlgorithmParameters parameters = parametersPanel.getParameters();
			if (algorithm == Algorithm.Cockroach) {
				guiActionListener.solveCurrentProblemWithCockRoach((CockroachParameters)parameters, problem);
			} else if (algorithm == Algorithm.Genetic) {
				GeneticParameters genetic = (GeneticParameters) parameters;
				guiActionListener.solveCurrentProblemWithGenetic(genetic, problem);
			}
			
		} else if ("next" == e.getActionCommand()) {
			repaintSolution(markedSolution+1);
			
		} else if ("prev" == e.getActionCommand()) {
			repaintSolution(markedSolution-1);
			
		} else if (e.getActionCommand() == "open") {
			JFileChooser chooser = new JFileChooser();
			chooser.setFileFilter(new FileFilter() {
				@Override
				public boolean accept(File arg0) {
					return arg0.getName().matches(".*\\.tsp");
				}
				@Override
				public String getDescription() {
					return "Point-array Files";
				}
			});
			int returnVal = chooser.showOpenDialog(AlgorithmMainPanel.this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = chooser.getSelectedFile();
			}
		}
	}

	private void repaintSolution(int interationNumber) {
		if (interationNumber >= 0 && interationNumber < problemSolution.getIterationsCount()){
			markedSolution = interationNumber;
			map.setSolution(problemSolution.getIterationResult(markedSolution).getPath());
			markedSolutionTF.setText(String.valueOf(problemSolution.getIterationResult(markedSolution).getIterationNumber()));
			markedSolutionLengthTF.setText(String.valueOf((int)problemSolution.getIterationResult(markedSolution).getLength()));
			
		}

	}

	public Problem getProblem() {
		return problem;
	}

	@Override
	public void newSolutionAdded(IProblemSolution problemSolution) {
		System.out.println("Algo: Sol added");
		map.setIsClicable(false);
		nextIt.setEnabled(false);
		prevIt.setEnabled(false);
		openPoint.setEnabled(false);
		this.problemSolution = problemSolution;
		parametersPanel.newSolutionAdded(problemSolution);
		bestIteration = 0;
	}

	@Override
	public void newIterationResultAdded(IProblemSolution problemSolution,
			IterationResult iterationResult) {
		map.setSolution(iterationResult.getPath());
		dataset.addValue(iterationResult.getLength(),(Integer) 1, (Integer)iterationResult.getIterationNumber());
		parametersPanel.newIterationResultAdded(problemSolution, iterationResult);
		markedSolutionTF.setText(String.valueOf(iterationResult.getIterationNumber()));
		markedSolutionLengthTF.setText(String.valueOf((int) iterationResult.getLength()));
		markedSolution = iterationResult.getIterationNumber();
		if (iterationResult.getLength() < problemSolution.getIterationResult(bestIteration).getLength()){
			bestIteration = iterationResult.getIterationNumber();
			bestSolutionTF.setText(String.valueOf(bestIteration));
			bestSolutionLengthTF.setText(String.valueOf((int) iterationResult.getLength()));
		}
	}

	@Override
	public void solutionFinished(IProblemSolution problemSolution) {
		map.setIsClicable(true);
		nextIt.setEnabled(true);
		prevIt.setEnabled(true);
		openPoint.setEnabled(true);
		parametersPanel.solutionFinished(problemSolution);
	}

}
