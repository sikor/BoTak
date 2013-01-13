package tspsolver.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MapPanelV2 extends JPanel {
	private List<Point> pointList;
	private int dotSize = 3;
	private double margin = 0.2;
	private Dimension dim;
	double minX = Double.MAX_VALUE, maxX = Double.MIN_VALUE,
			minY = Double.MAX_VALUE, maxY = Double.MIN_VALUE;
	JPanel c;
	public MapPanelV2() {
		super();
		
		pointList = new ArrayList<Point>();
		this.setSize(400, 400);
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				addPoint(e.getX(), e.getY());
			}
		});
		minX = 0;
		maxX = 1000;
		minY = 0;
		maxY = 1000;
		this.setLayout(new GridBagLayout());
		GridBagConstraints cons = new GridBagConstraints();
		
		
	}

	public MapPanelV2(List<Point> pointList) {
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
		double dx = minX + ((x / dim.getWidth()) * (maxX - minX));
		double dy = minY + ((y / dim.getHeight()) * (maxY - minY));
		pointList.add(new Point(dx, dy));
		repaint();
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		dim = this.getSize();
		for (Point p : pointList) {
			int x = (int) ((p.getX() - minX)/(maxX-minX));
			int y = (int) ((p.getY() - minY)/(maxY-minY));
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

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame f = new JFrame();
				f.setSize(new Dimension(400, 600));
				f.add(new MapPanelV2());
				f.setVisible(true);
			}
		});
	}

}
