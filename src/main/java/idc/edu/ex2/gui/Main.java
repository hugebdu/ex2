package idc.edu.ex2.gui;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import idc.edu.ex2.Plot;
import idc.edu.ex2.solution.*;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static com.google.common.io.Resources.getResource;
import static com.google.common.io.Resources.newReaderSupplier;

/**
 * Created by IntelliJ IDEA.
 * User: daniels
 * Date: 3/25/12
 */
public class Main
{
    public static final int NUM_OF_BEACONS = 32;

    public static void main(String[] args) throws Throwable
    {
        JFrame frame = new JFrame("Beacons GUI");
        frame.addWindowListener(existApp());

        frame.setSize(600, 629);
        frame.setResizable(false);

        CanvasPanel canvasPanel = new CanvasPanel();

//        Plot plot = new DanilasDoubleSpectrumSolution().createSolution(NUM_OF_BEACONS);
//        Plot plot = new InnaKatzFlowerSolution().createSolution(NUM_OF_BEACONS);
        Plot plot = new CenterTargetSolution().createSolution(NUM_OF_BEACONS);
//        Plot plot = new SymetricFlowerSolution().createSolution(NUM_OF_BEACONS);
//        Plot plot = new AlexandersGridSolution().createSolution(NUM_OF_BEACONS);
//        Plot plot = new DanilasSlidingSpectrumSolution().createSolution(NUM_OF_BEACONS);
        plot.loadSamplingPoints(newReaderSupplier(getResource("bm_grid10000_.txt"), Charsets.ISO_8859_1));
        canvasPanel.setPlot(plot);

        frame.add(canvasPanel, BorderLayout.LINE_START);

        frame.setVisible(true);
    }

    public static WindowAdapter existApp()
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
}
