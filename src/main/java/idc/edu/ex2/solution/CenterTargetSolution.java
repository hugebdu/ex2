package idc.edu.ex2.solution;


import idc.edu.ex2.Constraints;
import idc.edu.ex2.Plot;

import java.awt.geom.Point2D;

public class CenterTargetSolution implements Solution {

	@Override
	public Plot createSolution(int numOfBeacons) {

		Plot a = new Plot();
		Point2D centerPoint = getCenterPoint();

		for (int i=1; i<numOfBeacons+1; i++) {
			double radius = computeRadiusRelativeToNumOfPointsAndPos(numOfBeacons,i,50);
			a.beacons.add(Plot.Beacon(centerPoint, radius));
		}
		
		double smallestInterval = 50-computeRadiusRelativeToNumOfPointsAndPos(numOfBeacons, numOfBeacons-1, 50);
		
		double numOfExtraIter = 20D/smallestInterval;

		for (int i=1; i<numOfExtraIter; i++) {
			double radius = 50+i*smallestInterval;
			a.beacons.add(Plot.Beacon(centerPoint, radius));
		}
		
		return a;
	}
	
	
	private double getDiagonal() {		
		double halfmMaxHeight = Constraints.MAX_HEIGHT/2;
		return Math.sqrt(Math.pow(halfmMaxHeight, 2) + Math.pow(halfmMaxHeight, 2));
	}
	
	private Point2D getCenterPoint() {		
		return new Point2D.Double(Constraints.MAX_WIDTH/2, Constraints.MAX_HEIGHT/2);
	}
	
	private double computeSmallestRadius(int numOfPoints, double bigRadius) {
		return bigRadius / Math.sqrt(numOfPoints);
	}
	
	private double computeRadiusRelativeToNumOfPointsAndPos(int numOfPoints, int position, double bigRadius) {
		double d = Math.sqrt(position);
		double e = Math.sqrt(numOfPoints);
		System.out.println("ss");
		return (d / e) * bigRadius;
	}
}
