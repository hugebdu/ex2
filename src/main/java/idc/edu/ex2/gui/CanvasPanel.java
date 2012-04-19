package idc.edu.ex2.gui;

import idc.edu.ex2.Plot;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.util.BitSet;
import java.util.Collection;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: daniels
 * Date: 4/15/12
 */
public class CanvasPanel extends JPanel
{
    private static final Dimension DIMENSION = new Dimension(600, 600);
    private static final double POINT_WIDTH_HEIGHT = 0.3;
    private static final int POINT_ARCHW = 1;

    private Plot plot = new Plot();

    public CanvasPanel()
    {
        setBackground(Color.white);
        setPreferredSize(DIMENSION);
        setSize(DIMENSION);
    }

    public Plot getPlot()
    {
        return plot;
    }

    public void setPlot(Plot plot)
    {
        this.plot = plot;
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Graphics2D gg = (Graphics2D) g.create();
        gg.scale(6, 6);

        drawBeacons(gg);

        drawSamplingPoints(gg);

        drawLargestSegment(gg);

        gg.dispose();
    }

    private void drawSamplingPoints(Graphics2D gg)
    {
        gg.setColor(Color.gray);

        for (Point2D point : plot.samplingPoints)
            drawPoint(gg, point, POINT_WIDTH_HEIGHT);
    }

    private void drawPoint(Graphics2D gg, Point2D point, double size)
    {
        gg.fill(new RoundRectangle2D.Double(point.getX(), point.getY(),
                size, size,
                POINT_ARCHW, POINT_ARCHW));
    }

    private void drawBeacons(Graphics2D gg)
    {
        gg.setColor(Color.red);

        Composite previousComposite = gg.getComposite();
        gg.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.02f));

        for (Ellipse2D beacon : plot.beacons)
            gg.fill(beacon);

        gg.setComposite(previousComposite);
    }

    private void drawLargestSegment(Graphics2D gg)
    {
        Map.Entry<BitSet,Collection<Point2D>> maxSegment = plot.calculateAndGetLargestSegment();
        System.out.println(String.format("Largest segment size (pts): %d", maxSegment.getValue().size()));

        gg.setColor(Color.green);

        for (Point2D point : maxSegment.getValue())
            drawPoint(gg, point, POINT_WIDTH_HEIGHT * 2);
    }
}
