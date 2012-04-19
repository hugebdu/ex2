package idc.edu.ex2.solution;

import idc.edu.ex2.Constraints;
import idc.edu.ex2.Plot;

import java.awt.geom.Point2D;

public class DanielSolutionInnaImpl implements Solution {

	@Override
	public Plot createSolution(int numOfBeacons) {
		Plot a = new Plot();
		int beconsOnOneSide;
		int beconsOnOtherside;
		
		if (numOfBeacons%2==0) {
			beconsOnOneSide = numOfBeacons/2;
			beconsOnOtherside = beconsOnOneSide;			
			
		} else {
			beconsOnOneSide = numOfBeacons/2;
			beconsOnOtherside = beconsOnOneSide+1;
		}
		
		
		
		
		Point2D oneSideCorner = new Point2D.Double(0, 0);
		Point2D otherSideCorner = new Point2D.Double(0, Constraints.MAX_HEIGHT);
		
		double diagonal = getDiagonal();
		addBeconsOnTheSide(a, beconsOnOneSide, diagonal, oneSideCorner);
		addBeconsOnTheSide(a, beconsOnOtherside, diagonal, otherSideCorner);

		a.beacons.add(Plot.Beacon(new Point2D.Double(0, Constraints.MAX_HEIGHT / 2), 20));
		
		return a;
	}

	private void addBeconsOnTheSide(Plot a, int beconsOnOneSide,
			double diagonal, Point2D oneSideCorner) {
		double interval = getIntervalByNumberOfPoints(diagonal, beconsOnOneSide);
		for (int i=1; i<beconsOnOneSide+1; i++) {
			a.beacons.add(Plot.Beacon(oneSideCorner, interval * i));
		}
	}

	private double getDiagonal() {
		return Math.sqrt(Math.pow(Constraints.MAX_HEIGHT, 2) + Math.pow(Constraints.MAX_WIDTH, 2));
	}
	
	private double getIntervalByNumberOfPoints(double diagonal, double numberOfPoints) {
		return diagonal/(numberOfPoints+1);
	}

}
