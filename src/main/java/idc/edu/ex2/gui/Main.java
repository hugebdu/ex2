package idc.edu.ex2.gui;

import com.google.common.base.Charsets;
import com.google.common.eventbus.EventBus;
import idc.edu.ex2.Constraints;
import idc.edu.ex2.Plot;
import idc.edu.ex2.solution.InnaKatzFlowerSolution;
import org.apache.commons.lang3.StringUtils;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static com.google.common.io.Resources.newReaderSupplier;

/**
 * Created by IntelliJ IDEA.
 * User: daniels
 * Date: 3/25/12
 */
public class Main
{
    private static String inputFile;
    private static String outputFile;
    private static int     numOfBeacons = 1;
    private static boolean showGui      = true;

    public static void main(String[] args) throws Throwable
    {
        readArguments(args);

        if (showGui)
        {
            openGui();
            return;
        }

        if (StringUtils.isBlank(inputFile) || StringUtils.isBlank(outputFile))
        {
            System.out.println("Usage: java -jar ex2.jar <-i InputFile -o OutputFile [-n NumberOfBeacons] | -GUI >");
            return;
        }

        createSilentSolution();
    }

    private static void openGui()
    {
        JFrame frame = new JFrame("Beacons GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setResizable(false);

        EventBus eventBus = new EventBus();

        CanvasPanel canvasPanel = new CanvasPanel(eventBus);
        OptionsPanel optionsPanel = new OptionsPanel(eventBus);

        frame.add(canvasPanel, BorderLayout.LINE_START);
        frame.add(optionsPanel, BorderLayout.LINE_END);
        frame.getRootPane().setDefaultButton(optionsPanel.calculateButton);

        frame.pack();
        frame.setVisible(true);
    }

    private static void readArguments(String[] args)
    {
        showGui = false;
        inputFile = null;
        outputFile = null;
        numOfBeacons = Constraints.MAX_NUM_OF_BEACONS;

        for (int i = 0; i < args.length; i++)
        {
            if (args[i].equals("-i"))
            {
                if (++i < args.length)
                {
                    inputFile = args[i];
                }
            }
            else if (args[i].equals("-o"))
            {
                if (++i < args.length)
                {
                    outputFile = args[i];
                }
            }
            else if (args[i].equals("-n"))
            {
                if (++i < args.length)
                {
                    numOfBeacons = Integer.parseInt(args[i]);
                }
            }
            else if (args[i].equals("-GUI"))
            {
                showGui = true;
            }
        }
    }

    private static void createSilentSolution() throws IOException
    {
        Plot plot = new InnaKatzFlowerSolution().createSolution(numOfBeacons);
        plot.loadSamplingPoints(newReaderSupplier(new File(inputFile).toURI().toURL(), Charsets.ISO_8859_1));
        FileWriter fileStream = new FileWriter(outputFile);
        for (Ellipse2D ellipse : plot.beacons)
        {
            fileStream.append(String.format("%s, %s, %s\n", ellipse.getCenterX(), ellipse.getCenterY(), ellipse.getWidth()));
        }
        fileStream.close();
    }
}
