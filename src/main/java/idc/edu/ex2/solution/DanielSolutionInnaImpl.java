package idc.edu.ex2.solution;

import idc.edu.ex2.geometry.Area;
import idc.edu.ex2.geometry.Beacon;
import idc.edu.ex2.geometry.Constraints;
import idc.edu.ex2.geometry.Point;

public class DanielSolutionInnaImpl implements Solution {

	@Override
	public Area createSolution(int numOfBeacons) {
		Area a = new Area();
		int beconsOnOneSide;
		int beconsOnOtherside;
		
		if (numOfBeacons%2==0) {
			beconsOnOneSide = numOfBeacons/2;
			beconsOnOtherside = beconsOnOneSide;			
			
		} else {
			beconsOnOneSide = numOfBeacons/2;
			beconsOnOtherside = beconsOnOneSide+1;
		}
		
		
		
		
		Point oneSideCorner = new Point(0, 0);
		Point otherSideCorner = new Point(0, Constraints.MAX_HEIGHT);
		
		double diagonal = getDiagonal();
		addBeconsOnTheSide(a, beconsOnOneSide, diagonal, oneSideCorner);
		addBeconsOnTheSide(a, beconsOnOtherside, diagonal, otherSideCorner);

		a.addBeacon(new Beacon(new Point(0, Constraints.MAX_HEIGHT/2), 20));
		
		return a;
	}

	private void addBeconsOnTheSide(Area a, int beconsOnOneSide,
			double diagonal, Point oneSideCorner) {
		double interval = getIntervalByNumberOfPoints(diagonal, beconsOnOneSide);
		for (int i=1; i<beconsOnOneSide+1; i++) {
			a.addBeacon(new Beacon(oneSideCorner, interval*i));
		}
	}

	private double getDiagonal() {
		return Math.sqrt(Math.pow(Constraints.MAX_HEIGHT, 2) + Math.pow(Constraints.MAX_WIDTH, 2));
	}
	
	private double getIntervalByNumberOfPoints(double diagonal, double numberOfPoints) {
		return diagonal/(numberOfPoints+1);
	}

}
