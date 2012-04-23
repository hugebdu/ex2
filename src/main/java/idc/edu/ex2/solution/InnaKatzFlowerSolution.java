package idc.edu.ex2.solution;

import idc.edu.ex2.Constraints;
import idc.edu.ex2.Plot;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

/**
 * Created with IntelliJ IDEA.
 * User: alexanderva
 * Date: 4/9/12
 * Time: 6:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class InnaKatzFlowerSolution implements Solution {

    private static final int CONST_RATIO = 32;
    
    private static final int RADIUS_FOR_SMALL_NUM_OF_BECONS = 40;

	public static final Point2D AREA_CENTER = new Point2D.Double(Constraints.MAX_WIDTH / 2, Constraints.MAX_HEIGHT / 2);
    
    private Integer numOfBeacons;

    @Override
    public Plot createSolution(int numOfBeacons) {
    	this.numOfBeacons = numOfBeacons;

        Plot solution = new Plot();
        
		
		if (numOfBeacons == 1) {
    		solution.beacons.addAll(ImmutableList.<Ellipse2D>builder()
                    .add(Plot.Beacon(AREA_CENTER, RADIUS_FOR_SMALL_NUM_OF_BECONS)).build());
    		return solution;
    	}
    	
		
    	else if (numOfBeacons == 2) {
    		int delta = 25;
			Point2D.Double center1 = new Point2D.Double(RADIUS_FOR_SMALL_NUM_OF_BECONS, (Constraints.MAX_HEIGHT +delta) / 2);
    		Point2D.Double center2 = new Point2D.Double(Constraints.MAX_HEIGHT - RADIUS_FOR_SMALL_NUM_OF_BECONS, (Constraints.MAX_HEIGHT -delta) / 2);
       		solution.beacons.addAll(ImmutableList.<Ellipse2D>builder()
                    .add(Plot.Beacon(center1, RADIUS_FOR_SMALL_NUM_OF_BECONS)).add(Plot.Beacon(center2, RADIUS_FOR_SMALL_NUM_OF_BECONS)).build());
    		return solution;
    	}
		

	
		int helperCirclesNumber = numOfBeacons/8;
        solution.beacons.addAll(Lists.transform(getBeaconCenters(numOfBeacons - helperCirclesNumber), toBeacons(numOfBeacons - helperCirclesNumber)));
        
        if (helperCirclesNumber > 0) {
	        double interval = CONST_RATIO/helperCirclesNumber;
	        for (int i = 0; i < helperCirclesNumber; i++) {
	        	int initialRadius = numOfBeacons>=CONST_RATIO?38:45;
	        	solution.beacons.add(Plot.Beacon(AREA_CENTER, initialRadius+i*interval));       
	        }
        }
        
	    return solution;
    }

    private Function<Point2D, Ellipse2D> toBeacons(final int numOfBeacons) {
        return new Function<Point2D, Ellipse2D>() {
            @Override
            public Ellipse2D apply(Point2D point) {
                return Plot.Beacon(point, getSignalStrength(numOfBeacons));
            }
        };
    }

    private List<Point2D> getBeaconCenters(int numOfBeacons)
    {
        final double theta = Math.PI * 2 / numOfBeacons;

        double angle =  0 ;

        ImmutableList.Builder<Point2D> builder = ImmutableList.builder();

        for (int i = 0; i < numOfBeacons; i++)
        {
            builder.add(makeBeaconCenter(angle));
            angle += theta;
        }

        return builder.build();
    }

    private Point2D makeBeaconCenter(double theta) {
        return new Point2D.Double(AREA_CENTER.getX() + getDistanceFromCenter() * Math.cos(theta),
                AREA_CENTER.getY() + getDistanceFromCenter() * Math.sin(theta));
    }

    private double getDistanceFromCenter()
    {    	
    	if (numOfBeacons <=5) {
    		return 5*numOfBeacons + 5;
    	}
    	
		if (numOfBeacons >= 32) {
			return 47;
		} 
		if (numOfBeacons >= 16) {
			return 45;
		}
		if (numOfBeacons >= 8) {
			return 43;
		}
		
		return 40;	

    }

    private double getSignalStrength(int numOfPoints)
    {
    	
		if (numOfBeacons<=5) {
			return RADIUS_FOR_SMALL_NUM_OF_BECONS;
		} else {		
			return 49;
		}
    }
    
    private static int log2(int n) {
    	return 31 - Integer.numberOfLeadingZeros(n);
    }
}
