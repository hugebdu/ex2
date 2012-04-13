package idc.edu.ex2.solution;


import idc.edu.ex2.geometry.Area;
import idc.edu.ex2.geometry.Beacon;
import idc.edu.ex2.geometry.Constraints;
import idc.edu.ex2.geometry.Point;

public class CenterTargetSolution implements Solution {

	@Override
	public Area createSolution(int numOfBeacons) {

		Area a = new Area();
		Point centerPoint = getCenterPoint();

		for (int i=1; i<numOfBeacons+1; i++) {
			double radius = computeRadiusRelativeToNumOfPointsAndPos(numOfBeacons,i,50);
			a.addBeacon(new Beacon(centerPoint, radius));
		}
		
		double smallestInterval = 50-computeRadiusRelativeToNumOfPointsAndPos(numOfBeacons, numOfBeacons-1, 50);
		
		double numOfExtraIter = 20D/smallestInterval;

		for (int i=1; i<numOfExtraIter; i++) {
			double radius = 50+i*smallestInterval;
			a.addBeacon(new Beacon(centerPoint, radius));
		}
		
		return a;
	}
	
	
	private double getDiagonal() {		
		double halfmMaxHeight = Constraints.MAX_HEIGHT/2;
		return Math.sqrt(Math.pow(halfmMaxHeight, 2) + Math.pow(halfmMaxHeight, 2));
	}
	
	private Point getCenterPoint() {		
		return new Point(Constraints.MAX_WIDTH/2, Constraints.MAX_HEIGHT/2);
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
