package idc.edu.ex2.gui;

import static com.google.common.io.Resources.getResource;
import static com.google.common.io.Resources.newReaderSupplier;

import idc.edu.ex2.Plot;
import idc.edu.ex2.solution.InnaKatzFlowerSolution;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.JFrame;

import com.google.common.base.Charsets;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by IntelliJ IDEA. User: daniels Date: 3/25/12
 */
public class Main {
    public static final int NUM_OF_BEACONS = 64;
    public static final int MAX_NUM_OF_BEACONS = 64;
    private static String inputFile;
    private static String outputFile;
    private static Integer numOfBeacons;
    private static Boolean showGui;

    public static void main(String[] args) throws Throwable {

        readArguments(args);

        if(StringUtils.isBlank(inputFile) || StringUtils.isBlank(outputFile))
        {
            System.out.println("Usage: java -jar ex2.jar <-i InputFile -o OutputFile [-n NumberOfBeacons] | -GUI >");
            return;
        }

        if(showGui)
            openGUI();
        else
            createSilentSolution();
    }

    private static void readArguments(String[] args) {
        showGui=false;
        inputFile=null;
        outputFile=null;
        numOfBeacons=MAX_NUM_OF_BEACONS;

        for(int i=0;i<args.length;i++)
        {
            if(args[i].equals("-i"))
            {
                if(++i<args.length)
                    inputFile = args[i];
            }
            else if(args[i].equals("-o"))
            {
                if(++i<args.length)
                    outputFile = args[i];
            }
            else if(args[i].equals("-n"))
            {
                if(++i<args.length)
                    numOfBeacons = Integer.parseInt(args[i]);
            }
            else if(args[i].equals("-GUI"))
            {
                showGui = true;
            }
        }
    }

    private static void openGUI() throws IOException {
        // for (int i=64; i > 31; i--){
        JFrame frame = new JFrame("Beacons GUI");
        frame.addWindowListener(existApp());

        frame.setSize(600, 629);
        frame.setResizable(false);

        CanvasPanel canvasPanel = new CanvasPanel();
        //URL url = new URL("file:///C:/Users/Innak/workspace/GitRepo/ex2/src/main/resources/bm_grid10000_.txt");

        Plot plot = new InnaKatzFlowerSolution().createSolution(NUM_OF_BEACONS);
        plot.loadSamplingPoints(newReaderSupplier(getResource("bm_grid10000_.txt"), Charsets.ISO_8859_1));
        //plot.loadSamplingPoints(newReaderSupplier(url, Charsets.ISO_8859_1));
        canvasPanel.setPlot(plot);

        frame.add(canvasPanel, BorderLayout.LINE_START);

        frame.setVisible(true);
        // }
    }

    private static void createSilentSolution() throws IOException{
        Plot plot = new InnaKatzFlowerSolution().createSolution(numOfBeacons);
        plot.loadSamplingPoints(newReaderSupplier(new File(inputFile).toURI().toURL(), Charsets.ISO_8859_1));
        FileWriter fileStream = new FileWriter(outputFile);
        for(Ellipse2D ellipse:plot.beacons)
        {
            fileStream.append(String.format("%s, %s, %s\n", ellipse.getCenterX(), ellipse.getCenterY(), ellipse.getWidth()));
        }
        fileStream.close();
    }

    public static WindowAdapter existApp() {
		return new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		};
	}
}
