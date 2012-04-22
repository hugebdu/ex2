package idc.edu.ex2.gui;

import com.google.common.base.Charsets;
import com.google.common.collect.Iterables;
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
import java.util.Arrays;
import java.util.HashSet;

import static com.google.common.collect.Sets.newHashSet;
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
            System.out.println("Usage: java -jar ex2.jar <NumberOfBeacons InputFile OutputFile | -GUI >");
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
        if(newHashSet(args).contains("-GUI"))
        {
            showGui = true;
            return;
        }

        showGui = false;

        try
        {
            numOfBeacons = Integer.parseInt(args[0]);
            inputFile = args[1];
            outputFile = args[2];
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            inputFile = null;
            outputFile = null;
        }
    }

    private static void createSilentSolution() throws IOException
    {
        Plot plot = new InnaKatzFlowerSolution().createSolution(numOfBeacons);
        plot.loadSamplingPoints(newReaderSupplier(new File(inputFile).toURI().toURL(), Charsets.ISO_8859_1));
        FileWriter fileStream = new FileWriter(outputFile);
        for (Ellipse2D ellipse : plot.beacons)
        {
            fileStream.append(String.format("%s, %s, %s\n", ellipse.getCenterX(), ellipse.getCenterY(), ellipse.getWidth() / 2));
        }
        fileStream.close();
    }
}
