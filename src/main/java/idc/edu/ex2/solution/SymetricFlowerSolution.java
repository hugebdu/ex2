package idc.edu.ex2.solution;

import idc.edu.ex2.Constraints;
import idc.edu.ex2.Plot;

import java.awt.geom.Point2D;

public class SymetricFlowerSolution implements Solution {

	@Override
	public Plot createSolution(int numOfBeacons) {
		Plot a = new Plot();

		double edge = 100/3;
		double height = Math.sqrt(edge*edge - edge*edge/4);
		double thirdHeight = height/3;
		double radius = thirdHeight*2;
		
		int numOfBeconsInLine=4;
		putLineOfBecons(a, numOfBeconsInLine, edge, thirdHeight, radius,  0, 0);
		putLineOfBecons(a, numOfBeconsInLine, edge, thirdHeight, radius,  edge/2, 1);
		putLineOfBecons(a, numOfBeconsInLine, edge, thirdHeight, radius,  0, 2);
		putLineOfBecons(a, numOfBeconsInLine, edge, thirdHeight, radius,  edge/2, 3);
		putLineOfBecons(a, numOfBeconsInLine, edge, thirdHeight, radius,  0, 4);
		putLineOfBecons(a, numOfBeconsInLine, edge, thirdHeight, radius,  edge/2, 5);
		putLineOfBecons(a, numOfBeconsInLine, edge, thirdHeight, radius,  0, 6);
		putLineOfBecons(a, numOfBeconsInLine, edge, thirdHeight, radius,  edge/2, 7);
		putLineOfBecons(a, numOfBeconsInLine, edge, thirdHeight, radius,  0, 8);
		putLineOfBecons(a, numOfBeconsInLine, edge, thirdHeight, radius,  edge/2, 9);
		putLineOfBecons(a, numOfBeconsInLine, edge, thirdHeight, radius,  0, 10);
		putLineOfBecons(a, numOfBeconsInLine, edge, thirdHeight, radius,  edge/2, 11);
		putLineOfBecons(a, numOfBeconsInLine, edge, thirdHeight, radius,  0, 12);
		putLineOfBecons(a, numOfBeconsInLine, edge, thirdHeight, radius,  edge/2, 13);
//		putLineOfBecons(a, numOfBeconsInLine, edge, thirdHeight, radius,  0, 14);
//		putLineOfBecons(a, numOfBeconsInLine, edge, thirdHeight, radius,  edge/2, 15);
//		putLineOfBecons(a, numOfBeconsInLine, edge, thirdHeight, radius,  0, 14);


		return a;
	}

	private void putLineOfBecons(Plot a, int numOfBeconsInLine, double edge, double thirdHeight,
			double radius, double offset, int j) {
		for (int i=0; i<numOfBeconsInLine; i++) {
			a.beacons.add(Plot.Beacon(new Point2D.Double(offset + i * edge, Constraints.MAX_HEIGHT - thirdHeight * j), radius));
		}
	}

}
