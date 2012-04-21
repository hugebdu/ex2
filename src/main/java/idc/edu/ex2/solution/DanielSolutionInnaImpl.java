package idc.edu.ex2.solution;

import idc.edu.ex2.Constraints;
import idc.edu.ex2.Plot;

import java.awt.geom.Point2D;

public class DanielSolutionInnaImpl implements Solution {

	@Override
	public Plot createSolution(int numOfBeacons) {
		numOfBeacons = numOfBeacons-9;
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
				
		Point2D oneSideCorner = new Point2D.Double(Constraints.MAX_HEIGHT/2, 0);
		Point2D otherSideCorner = new Point2D.Double(Constraints.MAX_HEIGHT/2, Constraints.MAX_HEIGHT);
		
		Point2D other = new Point2D.Double(0, Constraints.MAX_HEIGHT/2);
		Point2D other2 = new Point2D.Double(Constraints.MAX_HEIGHT, Constraints.MAX_HEIGHT/2);
		
		double diagonal = Constraints.MAX_HEIGHT;
		addBeconsOnTheSide(a, beconsOnOneSide, diagonal, oneSideCorner);
		addBeconsOnTheSide(a, beconsOnOtherside, diagonal, otherSideCorner);

		addTwoMoreSymetricBecons(a, 55);
		addTwoMoreSymetricBecons(a, 63);
		addTwoMoreSymetricBecons(a, 73);
		addTwoMoreSymetricBecons(a, 83);

//		
		a.beacons.add(Plot.Beacon(new Point2D.Double(Constraints.MAX_HEIGHT/2, Constraints.MAX_HEIGHT/2), getDiagonal()-7));
//		a.beacons.add(Plot.Beacon(new Point2D.Double(Constraints.MAX_HEIGHT/2, Constraints.MAX_HEIGHT/2), Constraints.MAX_HEIGHT/2-3));
		
		return a;
	}

	private void addTwoMoreSymetricBecons(Plot a, int radius) {
		Point2D other = new Point2D.Double(0, Constraints.MAX_HEIGHT/2);
		Point2D other2 = new Point2D.Double(Constraints.MAX_HEIGHT, Constraints.MAX_HEIGHT/2);
		a.beacons.add(Plot.Beacon(other, radius));
		a.beacons.add(Plot.Beacon(other2, radius));
	}
	
	private void addBeconsOnTheSide(Plot a, int beconsOnOneSide,
			double diagonal, Point2D oneSideCorner) {
		double interval = getIntervalByNumberOfPoints(diagonal, beconsOnOneSide);
		for (int i=1; i<beconsOnOneSide+1; i++) {
			a.beacons.add(Plot.Beacon(oneSideCorner, interval * i));
		}
	}

	private double getDiagonal() {
		return Math.sqrt(Math.pow(Constraints.MAX_HEIGHT/2, 2) + Math.pow(Constraints.MAX_WIDTH/2, 2));
		//return Constraints.MAX_HEIGHT;
	}
	
	private double getIntervalByNumberOfPoints(double diagonal, double numberOfPoints) {
		return diagonal/(numberOfPoints);
	}

}
