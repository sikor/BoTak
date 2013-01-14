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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
import tspsolver.model.AlgorithmParameters;
import tspsolver.model.CockroachParameters;
import tspsolver.model.GeneticParameters;
import tspsolver.model.Point;
import tspsolver.model.Problem;
import tspsolver.model.interfaces.IModelChangeListener;
import tspsolver.model.interfaces.IProblemSolution;
import tspsolver.parser.Parser;
import tspsolver.utils.ImagesUtils;

public class AlgorithmMainPanel extends JPanel implements ActionListener,
		IModelChangeListener {
	private MapPanel map;
	private JFreeChart chart;
	private ChartPanel chartPanel;
	private ParametersPanel parametersPanel;
	private DefaultCategoryDataset dataset;
	private JPanel centralPanel;
	private JButton nextIt, prevIt, openPoint;
	private Algorithm algorithm;
	private int markedSolution;
	private int bestIteration;
	private JTextField markedSolutionTF;
	private JLabel markedSolutionLengthTF;
	private JLabel bestSolutionTF;
	private JLabel bestSolutionLengthTF;
	private JLabel mapName;
	private GuiActionsListener guiActionListener;
	private IProblemSolution problemSolution;
	private Problem problem;
	private int maxIt;

	public AlgorithmMainPanel(Algorithm al, GuiActionsListener guiActionListener) {
		super();
		map = new MapPanel();
		this.algorithm = al;
		this.guiActionListener = guiActionListener;
		// iterations = new ArrayList<IterationResult>();
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
		int gridYcounter = 0;
		gbc.insets = (new Insets(3, 3, 3, 3));
		gbc.gridwidth = 2;
		gbc.gridy = gridYcounter++;
		buttonPanel.add(openPoint, gbc);
		gbc.gridy = gridYcounter++;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(3, 3, 3, 0);
		buttonPanel.add(new JLabel("Map:"), gbc);
		gbc.insets = new Insets(3, 0, 3, 3);
		buttonPanel.add(mapName = new JLabel("<<no name>>"), gbc);
		gbc.gridy = gridYcounter++;
		buttonPanel.add(prevIt, gbc);
		buttonPanel.add(nextIt, gbc);
		gbc.gridy = gridYcounter++;
		// gbc.gridx=1;
		gbc.gridwidth = 1;
		buttonPanel.add(new JLabel("Iteration:"), gbc);
		NumberFormatter formatter = new NumberFormatter(
				NumberFormat.getIntegerInstance());
		formatter.setValueClass(Integer.class);

		markedSolutionLengthTF = new JLabel();
		buttonPanel.add(markedSolutionTF = new JTextField(5), gbc);
		gbc.gridy = gridYcounter++;
		buttonPanel.add(new JLabel("Length:"), gbc);
		buttonPanel.add(markedSolutionLengthTF, gbc);
		gbc.gridy = gridYcounter++;
		buttonPanel.add(new JLabel("Best it no:"), gbc);
		buttonPanel.add(bestSolutionTF = new JLabel(), gbc);
		gbc.gridy = gridYcounter++;
		buttonPanel.add(new JLabel("Shortest Route"), gbc);
		buttonPanel.add(bestSolutionLengthTF = new JLabel(""), gbc);

		markedSolutionTF.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent arg0) {
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				String v = ((JTextField) arg0.getComponent()).getText();
				repaintSolution(Integer.valueOf(v));
			}

		});
		markedSolutionTF.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyChar() == KeyEvent.VK_ENTER) {
					String v = markedSolutionTF.getText();
					repaintSolution(Integer.valueOf(v));
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

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
			if (problem == null) {
				JOptionPane.showMessageDialog(this, "No Points");
			} else {
				AlgorithmParameters parameters = parametersPanel
						.getParameters();
				maxIt = parameters.getIterationCount();
				if (algorithm == Algorithm.Cockroach) {
					guiActionListener.solveCurrentProblemWithCockRoach(
							(CockroachParameters) parameters, problem);
				} else if (algorithm == Algorithm.Genetic) {
					GeneticParameters genetic = (GeneticParameters) parameters;
					guiActionListener.solveCurrentProblemWithGenetic(genetic,
							problem);
				}
			}
		} else if ("next" == e.getActionCommand()) {
			repaintSolution(markedSolution + 1);

		} else if ("prev" == e.getActionCommand()) {
			repaintSolution(markedSolution - 1);

		} else if (e.getActionCommand() == "open") {
			JFileChooser chooser = new JFileChooser("./../../tsp_good_examples");
			chooser.setFileFilter(new FileFilter() {
				@Override
				public boolean accept(File arg0) {
					// return arg0.getName().matches(".*\\.tsp");
					return true;
				}

				@Override
				public String getDescription() {
					return "Point-array Files";
				}
			});
			int returnVal = chooser.showOpenDialog(AlgorithmMainPanel.this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = chooser.getSelectedFile();
				AlgorithmMainPanel.this.setName(file.getName());
				mapName.setText(file.getName());
				FileReader fr = null;
				boolean error = false;
				try {
					fr = new FileReader(file);
					List<Point> points = Parser.parseTspLibFile(fr);
					map.setPoints(points);
					fr.close();
				} catch (IllegalStateException eis) {
					eis.printStackTrace();
					error = true;
				} catch (FileNotFoundException fnf) {
					fnf.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} finally {
					if (fr != null)
						try {
							fr.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				}
			}
		}
	}

	private void repaintSolution(int interationNumber) {
		if (interationNumber >= 0
				&& interationNumber < problemSolution.getIterationsCount()) {
			markedSolution = interationNumber;
			map.setSolution(problemSolution.getIterationResult(markedSolution)
					.getPath());
			markedSolutionTF.setText(String.valueOf(problemSolution
					.getIterationResult(markedSolution).getIterationNumber()));
			markedSolutionLengthTF.setText(String.valueOf((int) problemSolution
					.getIterationResult(markedSolution).getLength()));

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
	public synchronized void newIterationResultAdded(
			IProblemSolution problemSolution, IterationResult iterationResult) {
		map.setSolution(iterationResult.getPath());
		int thisIt = iterationResult.getIterationNumber();
		if (maxIt > 1000) {
			if (thisIt % (maxIt / 1000) == 1){
				dataset.addValue(iterationResult.getLength(), (Integer) 1,
						(Integer) iterationResult.getIterationNumber());
			}
		} else {
			dataset.addValue(iterationResult.getLength(), (Integer) 1,
					(Integer) iterationResult.getIterationNumber());
		}
		parametersPanel.newIterationResultAdded(problemSolution,
				iterationResult);
		markedSolutionTF.setText(String.valueOf(iterationResult
				.getIterationNumber()));
		markedSolutionLengthTF.setText(String.valueOf((int) iterationResult
				.getLength()));
		markedSolution = iterationResult.getIterationNumber();
		if (iterationResult.getLength() < problemSolution.getIterationResult(
				bestIteration).getLength()) {
			bestIteration = iterationResult.getIterationNumber();
			bestSolutionTF.setText(String.valueOf(bestIteration));
			bestSolutionLengthTF.setText(String.valueOf((int) iterationResult
					.getLength()));
		}
	}

	@Override
	public synchronized void solutionFinished(IProblemSolution problemSolution) {
		map.setIsClicable(true);
		prevIt.setEnabled(true);
		openPoint.setEnabled(true);
		parametersPanel.solutionFinished(problemSolution);
	}

}
