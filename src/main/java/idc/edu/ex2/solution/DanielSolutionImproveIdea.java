package idc.edu.ex2.solution;

import idc.edu.ex2.Constraints;
import idc.edu.ex2.Plot;

import java.awt.geom.Point2D;

public class DanielSolutionImproveIdea implements Solution {

	@Override
	public Plot createSolution(int numOfBeacons) {
		numOfBeacons = numOfBeacons-8;
		Plot a = new Plot();
		int beconsOnTop;
		int beconsOnCenter;
		int beconsOnBottom;
		
		if (numOfBeacons%2==0) {
			int halfBecons = numOfBeacons/2;
			if (halfBecons%2==0) {
				beconsOnTop = halfBecons/2;
				beconsOnBottom = beconsOnTop;
				beconsOnCenter = halfBecons;
			} else {
				beconsOnTop = halfBecons/2;
				beconsOnBottom = beconsOnTop;			
			}			
			beconsOnCenter = halfBecons;			
		} else {
			beconsOnCenter = numOfBeacons/2;			
			beconsOnTop = (beconsOnCenter+1)/2;
			beconsOnBottom = beconsOnTop;
		}
		
		
		
		
		Point2D top = new Point2D.Double(Constraints.MAX_HEIGHT/2, 0);
		Point2D bottom = new Point2D.Double(Constraints.MAX_HEIGHT/2, Constraints.MAX_HEIGHT);
		Point2D center = new Point2D.Double(Constraints.MAX_HEIGHT/2, Constraints.MAX_HEIGHT/2);
				
		addBeconsOnTheSide(a, beconsOnTop, getDiagonal()/2, top);
		addBeconsOnTheSide(a, beconsOnBottom, getDiagonal()/2, bottom);
		addBeconsOnTheSide(a, beconsOnCenter, getDiagonal(), center);
		
		//addTwoMoreSymetricBecons(a, 55);
		addTwoMoreSymetricBecons(a, 63);
		addTwoMoreSymetricBecons(a, 73);
		addTwoMoreSymetricBecons(a, 83);
		addTwoMoreSymetricBecons(a, 93);

		return a;
	}

	private void addBeconsOnTheSide(Plot a, int beconsOnOneSide,
			double diagonal, Point2D oneSideCorner) {
		double interval = getIntervalByNumberOfPoints(diagonal, beconsOnOneSide);
		for (int i=1; i<beconsOnOneSide+1; i++) {
			a.beacons.add(Plot.Beacon(oneSideCorner, interval * i));
		}
	}
	
	private void addTwoMoreSymetricBecons(Plot a, int radius) {
		Point2D other = new Point2D.Double(0, Constraints.MAX_HEIGHT/2);
		Point2D other2 = new Point2D.Double(Constraints.MAX_HEIGHT, Constraints.MAX_HEIGHT/2);
		a.beacons.add(Plot.Beacon(other, radius));
		a.beacons.add(Plot.Beacon(other2, radius));
	}

	private double getDiagonal() {
		return Math.sqrt(Math.pow(Constraints.MAX_HEIGHT, 2) + Math.pow(Constraints.MAX_WIDTH, 2));		
	}
	
	private double getIntervalByNumberOfPoints(double diagonal, double numberOfPoints) {
		return diagonal/(numberOfPoints);
	}

}
