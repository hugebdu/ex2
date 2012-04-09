package idc.edu.ex2.gui;

import idc.edu.ex2.geometry.Area;
import idc.edu.ex2.geometry.Point;
import idc.edu.ex2.solution.AlexandersGridSolution;
import idc.edu.ex2.solution.DanilasDoubleSpectrumSolution;
import idc.edu.ex2.solution.InnaKatzFlowerSolution;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static idc.edu.ex2.geometry.Beacon.Beacon;
import static idc.edu.ex2.geometry.Point.Point;

/**
 * Created by IntelliJ IDEA.
 * User: daniels
 * Date: 3/25/12
 */
public class Main
{

    public static final int NUM_OF_BEACONS = 14;

    public static void main(String[] args) throws Throwable
    {
        JFrame frame = new JFrame("Beacons GUI");
        frame.addWindowListener(existApp());

        frame.setSize(1024, 629);
        frame.setResizable(false);

        CanvasPanel canvasPanel = new CanvasPanel();
        frame.add(canvasPanel, BorderLayout.LINE_START);
        frame.add(new SidePanel(canvasPanel), BorderLayout.LINE_END);

        canvasPanel.setArea(new InnaKatzFlowerSolution().createSolution(NUM_OF_BEACONS));
//        canvasPanel.setArea(new DanilasDoubleSpectrumSolution().createSolution(NUM_OF_BEACONS));
//        canvasPanel.setArea(new AlexandersGridSolution().createSolution(NUM_OF_BEACONS));

        frame.setVisible(true);
    }

    private static Area createSimpleArea() {
        return new Area()
                .addBeacon(Beacon(Point(0, 50), 45))
                .addBeacon(Beacon(Point(99, 50), 45));
    }

    private static WindowAdapter existApp()
    {
        return new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        };
    }

    private static Area createArea()
    {
        Area area = new Area();

        for (int i = 0; i < 2; i++)
        {
            area.addBeacon(Beacon(new Point(0, 0), 20 * (i + 1)));
            area.addBeacon(Beacon(new Point(0, 99), 20 * (i + 1)));
        }

        return area;
    }
}
