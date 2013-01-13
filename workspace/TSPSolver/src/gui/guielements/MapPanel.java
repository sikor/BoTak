package gui.guielements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;

import tspsolver.model.Point;
import tspsolver.model.Problem;

public class MapPanel extends JPanel {
	private List<Point> pointList;
	private List<int[]> solutionList;
	private int solutionPointer;
	private int dotSize = 3;
	private double margin = 0.2;
	private Dimension dim;
	double minX = Double.MAX_VALUE, maxX = Double.MIN_VALUE,
			minY = Double.MAX_VALUE, maxY = Double.MIN_VALUE;
	JPanel c;

	public MapPanel() {
		super();
		solutionList = new ArrayList<int[]>();
		pointList = new ArrayList<Point>();
		this.setSize(400, 400);
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				addPoint(e.getX(), e.getY());
			}
		});
		minX = -1;
		maxX = 1;
		minY = -1;
		maxY = 1;
		
		JButton nextButton = new JButton((new AbstractAction("Next"){
			public void actionPerformed(ActionEvent e){
				solutionPointer = Math.min(solutionPointer+1, solutionList.size()-1);
			}
		}));
		JButton prevButton = new JButton((new AbstractAction("Prev"){
			public void actionPerformed(ActionEvent e){
				solutionPointer = Math.min(solutionPointer+1, solutionList.size()-1);
			}
		}));
		JPanel bp = new JPanel(new FlowLayout());
	
	}
	
	public MapPanel(List<Point> pointList) {
		this();
		this.pointList = pointList;
		minX = Double.MAX_VALUE;
		maxX = Double.MIN_VALUE;
		minY = Double.MAX_VALUE;
		maxY = Double.MIN_VALUE;
		double x, y;
		for (Point p : pointList) {
			x = p.getX();
			y = p.getY();
			minX = Math.min(x, minX);
			maxX = Math.max(x, maxX);
			minY = Math.min(y, minY);
			maxY = Math.max(y, maxY);
		}
		double margin = (maxX - minX) * this.margin;
		minX -= margin;
		maxX += margin;
		margin = (maxY - minY) * this.margin;
		minY -= margin;
		maxY += margin;
	}

	public void addPoint(int x, int y) {
		dim = this.getSize();
		double factorX = dim.getWidth() / (maxX - minX);
		double factorY = dim.getHeight() / (maxY - minY);
		double dx = 0, dy = 0;
		if (factorX < factorY) {
			dx = minX + ((x / dim.getWidth()) * (maxX - minX));
			dy = (minY + (y / dim.getHeight()) * (maxY - minY)) * factorY
					/ factorX;
		} else {
			dy = minY + ((y / dim.getHeight()) * (maxY - minY));
			dx = (minX + (x / dim.getWidth()) * (maxX - minX)) * factorX
					/ factorY;
		}
		if (dy > minY && dy < maxY && dx > minX && dx < maxX) {
			System.out.println(dx + " + " + dy);
			pointList.add(new Point(dx, dy));
			repaint();
		}
	}

	public void addSolution(int[] solution) {
		solutionList.add(solution);
		solutionPointer = solutionList.size()-1;
		repaint();
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		dim = this.getSize();
		int addX = 0, addY = 0;
		double factorX = dim.getWidth() / (maxX - minX);
		double factorY = dim.getHeight() / (maxY - minY);
		double factor;
		if (factorX < factorY) {
			factor = factorX;
			addY = (int) Math
					.round((dim.getHeight() - (maxY - minY) * factor) / 2);
		} else {
			factor = factorY;
			addX = (int) Math
					.round((dim.getWidth() - (maxX - minX) * factor) / 2);
		}
		g.setColor(Color.gray);
		g.fillRect(0, 0, dim.width, dim.height);
		g.setColor(Color.white);
		g.fillRect(0 + addX, 0 + addY, (int) ((maxX - minX) * factor),
				(int) ((maxY - minY) * factor));

		if (!solutionList.isEmpty()) {
			int[] path = solutionList.get(solutionList.size() - 1);
			int max = path.length;
			g.setColor(Color.green);
			Point p1 = pointList.get(path[0]);
			int x1 = (int) (addX + (p1.getX() - minX) * factor);
			int y1 = (int) (addY + (p1.getY() - minY) * factor);
			for (int i = 0; i < max - 1; i++) {
				Point p2 = pointList.get(path[i + 1]);
				int x2 = (int) (addX + (p2.getX() - minX) * factor);
				int y2 = (int) (addY + (p2.getY() - minY) * factor);
				g.drawLine(x1, y1, x2, y2);
				p1 = p2;
				x1 = x2;
				y1 = y2;
			}
		}

		for (Point p : pointList) {
			int x = (int) (addX + (p.getX() - minX) * factor);
			int y = (int) (addY + (p.getY() - minY) * factor);
			g.setColor(Color.red);
			g.fillOval(x - dotSize, y - dotSize, 2 * dotSize, 2 * dotSize);
			g.setColor(Color.black);
			g.drawOval(x - dotSize, y - dotSize, 2 * dotSize, 2 * dotSize);

		}
	}

	public void componentResized(ComponentEvent e) {
		System.out.println(e.getComponent().getClass().getName()
				+ " --- Resized ");
	}
	
	public double[][] getDistances(){
		return (new Problem(pointList)).getDistances();
	}

}
