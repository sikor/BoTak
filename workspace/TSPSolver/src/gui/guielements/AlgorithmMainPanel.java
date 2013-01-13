package gui.guielements;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.text.DecimalFormat;
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

import tspsolver.algorithms.Algorithm;
import tspsolver.algorithms.IterationResult;
import tspsolver.algorithms.cockroach.CockroachSolver;
import tspsolver.algorithms.genetic.GeneticSolver;
import tspsolver.model.AlgorithmParameters;
import tspsolver.model.CockroachParameters;
import tspsolver.model.GeneticParameters;

public class AlgorithmMainPanel extends JPanel implements ActionListener,
		PropertyChangeListener {
	private MapPanel map;
	private JFreeChart chart;
	private ChartPanel chartPanel;
	private ParametersPanel parametersPanel;
	private DefaultCategoryDataset dataset;
	private JPanel centralPanel;
	private List<IterationResult> iterations;
	private JButton nextIt, prevIt, openPoint;
	private Algorithm algorithm;
	private SolutionWorker worker;
	private int markedSolution;
	private JTextField markedSolutionTF;
	private JTextField markedSolutionLengthTF;  
	private JTextField bestSolutionTF;
	private JTextField bestSolutionLengthTF;
	private GradientPaint gradientpaint0 = new GradientPaint(0.0F, 0.0F, new Color(230, 230, 250), 0.0F, 0.0F, new Color(136, 136, 255));   
    private GradientPaint gradientpaint1 = new GradientPaint(0.0F, 0.0F, new Color(240, 255, 240), 0.0F, 0.0F, new Color(136, 136, 255));

	public AlgorithmMainPanel(Algorithm al) {
		super();
		map = new MapPanel();
		algorithm = al;
		iterations = new ArrayList<IterationResult>();
		dataset = new DefaultCategoryDataset();
		// dataset.addValue(1.0, new Double(1), new Double(1));
		chart = ChartFactory.createBarChart(null, null, "result", dataset,
				PlotOrientation.VERTICAL, false, false, false);

		chartPanel = new ChartPanel(chart, 300, 100, 100, 100, 1000, 100, true,
				false, false, false, false, false);
		// chartPanel.setMaximumDrawHeight(300);
		centralPanel = new JPanel(new BorderLayout());
		this.setLayout(new BorderLayout());

		nextIt = new JButton("Prev");
		nextIt.addActionListener(this);
		prevIt = new JButton("Next");
		prevIt.addActionListener(this);
		openPoint = new JButton("Points from file");

		openPoint.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileFilter(new FileFilter() {

					@Override
					public boolean accept(File arg0) {
						return arg0.getName().matches(".*\\.png");
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

		});

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = (new Insets(3, 3, 3, 3));
		gbc.gridwidth = 2;
		buttonPanel.add(openPoint, gbc);
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		buttonPanel.add(nextIt, gbc);
		buttonPanel.add(prevIt, gbc);
		gbc.gridy=2;
		//gbc.gridx=1;
		gbc.gridwidth=1;
		buttonPanel.add(new JLabel("Iteration"),gbc);
		NumberFormatter formatter = new NumberFormatter(NumberFormat.getIntegerInstance());
		formatter.setValueClass(Integer.class);
		markedSolutionLengthTF = new JFormattedTextField(formatter);
		markedSolutionLengthTF.setColumns(5);
		buttonPanel.add(markedSolutionTF=new JTextField(5), gbc);
		gbc.gridy=3;
		buttonPanel.add(new JLabel("Length:"), gbc);
		buttonPanel.add(markedSolutionLengthTF,gbc);
		gbc.gridy=4;
		buttonPanel.add(new JLabel("Best it no:"), gbc);
		buttonPanel.add(bestSolutionTF = new JTextField(5), gbc);
		gbc.gridy = 5;
		buttonPanel.add(new JLabel("Shortest Route"), gbc);
		buttonPanel.add(bestSolutionLengthTF = new JTextField(5),gbc);

		markedSolutionTF.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent arg0) {
				
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				int newValue = (int)((JFormattedTextField)arg0.getComponent()).getValue();
				System.out.println(newValue);
				if (newValue > 0 && newValue < iterations.size() ){
					markedSolution = newValue;
					repaintSolution();
				}
						
			}
			
		});
		
		centralPanel.add(buttonPanel, BorderLayout.WEST);
		centralPanel.add(chartPanel, BorderLayout.SOUTH);
		centralPanel.add(map, BorderLayout.CENTER);
		this.add(centralPanel);
		ImageIcon icon = createImageIcon("images/cockroach.png", "Cockroach");
		parametersPanel = new ParametersPanel(al, icon);
		parametersPanel.setMinimumSize(new Dimension(333,400));
		parametersPanel.addActionListener(this);
		this.add(parametersPanel, BorderLayout.EAST);
	}

	protected ImageIcon createImageIcon(String path, String description) {
		java.net.URL imgURL = getClass().getResource(path);
		if (imgURL != null)
			return new ImageIcon(imgURL, description);
		else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	public void addIterationResult(IterationResult itr) {
		iterations.add(itr);
	}

	public void actionPerformed(ActionEvent e) {
		if ("Start" == e.getActionCommand()) {
			AlgorithmParameters parameters = parametersPanel.getParameters();
			worker = null;
			dataset.clear();
			if (algorithm == Algorithm.Cockroach) {
				worker = new SolutionWorker(new CockroachSolver(
						map.getDistances(), (CockroachParameters) parameters), this);
			} else if (algorithm == Algorithm.Genetic) {
				GeneticParameters genetic = (GeneticParameters) parameters;
				worker = new SolutionWorker(new GeneticSolver(
						map.getDistances(),
						genetic.getIterationCount(),
						genetic.getLiczbaOsobnikow(),
						genetic.getLiczbaKrzyzowan(), 
						genetic.getLiczbaMutacji(),
						genetic.getLiczbaPodmianWObrebieMutacji()), this);
			}
			worker.addPropertyChangeListener(this);
			worker.addPropertyChangeListener(parametersPanel);
			worker.execute();
			map.setSolution(null);
		} else if ("Stop" == e.getActionCommand()) {
			worker.cancel(true);
		}
		else if ("Next" == e.getActionCommand()){
			if (markedSolution < iterations.size()-1){
				markedSolution++;
				repaintSolution();
			}
		}
		else if ("Prev" == e.getActionCommand()){
			if (markedSolution > 0){
				markedSolution--;
				repaintSolution();
			}
		}
	}
	
	private void repaintSolution(){
		map.setSolution(iterations.get(markedSolution).getPath());
		markedSolutionTF.setText(String.valueOf(iterations.get(markedSolution).getIterationNumber()));
		markedSolutionLengthTF.setText(roundTwoDecimals(iterations.get(markedSolution).getLength()));
		
	}

	private String roundTwoDecimals(double d) {
//        DecimalFormat twoDForm = new DecimalFormat("#.##");
//    return String.valueOf(Double.valueOf(twoDForm.format(d)));
		return String.valueOf(d);
}
	public void addIterationResults(List<IterationResult> list) {
		int size = iterations.size();
		iterations.addAll(list);
		for (IterationResult ir : list){
			dataset.addValue(ir.getLength(), new Double(1), new Double(size++));
			
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		System.out.println(arg0.getPropertyName());
		if (arg0.getPropertyName() == "progress") {
		}
		else if (arg0.getPropertyName() == "state"){
			
		}
	}

}
