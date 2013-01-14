package gui.guielements;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.text.NumberFormatter;

import tspsolver.algorithms.Algorithm;
import tspsolver.algorithms.IterationResult;
import tspsolver.model.AlgorithmParameters;
import tspsolver.model.CockroachParameters;
import tspsolver.model.GeneticParameters;
import tspsolver.model.interfaces.IModelChangeListener;
import tspsolver.model.interfaces.IProblemSolution;

class ParametersPanel extends JPanel implements IModelChangeListener{
	private List<String> labels;
	private List<JFormattedTextField> values;
	private Map<String, Integer> labelToValue;
	private JProgressBar progressBar;
	private int parametersNumber;
	private JButton startStopButton;
	private Algorithm algorithm;

	public ParametersPanel(Algorithm algorithm, Icon icon) {
		super();
		this.algorithm = algorithm;
		this.setLayout(new GridBagLayout());
		labelToValue = new HashMap<String, Integer>();
		GridBagConstraints c = new GridBagConstraints();
		int i = 0;
		c.gridx = 0;
		c.gridy = i++;
		c.gridwidth = 2;
		c.insets = new Insets(10, 65, 40, 60);
		JLabel iconLabel;
		if (icon != null)
			iconLabel = new JLabel( icon);
		else
			iconLabel = new JLabel(algorithm.name());
		this.add(iconLabel, c);
		c.gridwidth = 1;
		if (algorithm == Algorithm.Cockroach) {
			parametersNumber = 3;
			labels = new ArrayList<String>(parametersNumber);
			labels.add("iterations: ");
			labels.add("population: ");
			labels.add("steps: ");
		} else {
			parametersNumber = 5;
			labels = new ArrayList<String>(parametersNumber);
			labels.add("iteracje:");
			labels.add("liczba Osobnikow:");
			labels.add("licza krzy¿owañ:");
			labels.add("liczba mutacji:");
			labels.add("liczba podmian: ");
		}

		values = new ArrayList<JFormattedTextField>(parametersNumber);

		c = new GridBagConstraints();
		c.insets = new Insets(0,10,2,0);
		int n = 0;
		for (String s : labels) {
			c.gridx = 0;
			c.gridy = i;
			this.add(new JLabel(s), c);
			c.gridx = 1;
			c.gridy = i++;
			NumberFormatter formatter = new NumberFormatter(NumberFormat.getIntegerInstance());
			formatter.setValueClass(Integer.class);
			JFormattedTextField tf = new JFormattedTextField(formatter);
			values.add(tf);
			tf.setValue(new Integer(1));
			
			tf.setColumns(7);
			this.add(tf, c);
		}

		startStopButton = new JButton("Start");
		//startStopButton.addActionListener(this);
		c.gridx = 0;
		c.gridy = i++;
		c.gridwidth = 2;
		c.insets = new Insets(20, 10, 10, 10);
		this.add(startStopButton, c);

		progressBar = new JProgressBar();
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		c.gridx = 0;
		c.gridy = i++;
		c.gridwidth = 2;
		c.insets = new Insets(10, 10, 10, 10); // top padding
		c.anchor = GridBagConstraints.PAGE_END;
		this.add(progressBar, c);
//		c.gridx=0;
//		c.gridy = i++;
//		c.gridwidth=2;
//		JLabel label = new JLabel("a                                                           a");
//		label.setMinimumSize(new Dimension(3000,10));
//		this.add(label, c);
	}

	public void addActionListener(ActionListener ac) {
		startStopButton.addActionListener(ac);
	}

	public AlgorithmParameters getParameters() {
		if (algorithm == Algorithm.Cockroach)
			return new CockroachParameters(
					((int)values.get(0).getValue()),
					((int)values.get(1).getValue()),
					((int)values.get(2).getValue()));
		else if (algorithm == Algorithm.Genetic)
			return new GeneticParameters(
					((int)values.get(0).getValue()),
					((int)values.get(1).getValue()),
					((int)values.get(2).getValue()),
					((int)values.get(3).getValue()),
					((int)values.get(4).getValue()));
		else return null;
	}

//	@Override
//	public void actionPerformed(ActionEvent arg0) {
//		if (arg0.getActionCommand() == "Start"){
//		}
//		if (arg0.getActionCommand() == "Stop"){
//			startStopButton.setEnabled(false);			
//			for (JFormattedTextField jftf : values){
//				jftf.setEnabled(true);
//			}
////			progressBar.setValue(0);
//		}
//		
//	}


	@Override
	public void newSolutionAdded(IProblemSolution problemSolution) {
		progressBar.setMaximum((int)values.get(0).getValue());
		progressBar.setValue(0);
		progressBar.setString(null);
		startStopButton.setEnabled(false);		
	}

	@Override
	public void newIterationResultAdded(IProblemSolution problemSolution,
			IterationResult iterationResult) {
		progressBar.setValue(progressBar.getValue()+1);
	}

	@Override
	public void solutionFinished(IProblemSolution problemSolution) {
		progressBar.setString("Finished");
		startStopButton.setEnabled(true);
		for (JFormattedTextField jftf : values){
			jftf.setEnabled(true);
		}
	}
	
}