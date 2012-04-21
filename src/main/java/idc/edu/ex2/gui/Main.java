package idc.edu.ex2.gui;

import static com.google.common.io.Resources.newReaderSupplier;
import idc.edu.ex2.Plot;
import idc.edu.ex2.solution.DanielSolutionImproveIdea;
import idc.edu.ex2.solution.DanielSolutionInnaImpl;
import idc.edu.ex2.solution.DanilasDoubleSpectrumSolution;
import idc.edu.ex2.solution.DanilasSlidingSpectrumSolution;
import idc.edu.ex2.solution.InnaKatzFlowerSolution;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

import javax.swing.JFrame;

import com.google.common.base.Charsets;

/**
 * Created by IntelliJ IDEA. User: daniels Date: 3/25/12
 */
public class Main {
	public static final int NUM_OF_BEACONS = 32;

	public static void main(String[] args) throws Throwable {
		// for (int i=64; i > 31; i--){
		JFrame frame = new JFrame("Beacons GUI");
		frame.addWindowListener(existApp());

		frame.setSize(600, 629);
		frame.setResizable(false);

		CanvasPanel canvasPanel = new CanvasPanel();
		URL url = new URL(
				"file:///C:/Users/Innak/workspace/GitRepo/ex2/src/main/resources/bm_grid10000_.txt");

		Plot plot = new InnaKatzFlowerSolution().createSolution(NUM_OF_BEACONS);
		// plot.loadSamplingPoints(newReaderSupplier(getResource("bm_grid10000_.txt"), Charsets.ISO_8859_1));
		plot.loadSamplingPoints(newReaderSupplier(url, Charsets.ISO_8859_1));
		canvasPanel.setPlot(plot);

		frame.add(canvasPanel, BorderLayout.LINE_START);

		frame.setVisible(true);
		// }
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
