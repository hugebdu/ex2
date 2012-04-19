package idc.edu.ex2.solution;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import idc.edu.ex2.Constraints;
import idc.edu.ex2.Plot;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alexanderva
 * Date: 4/9/12
 * Time: 6:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class InnaKatzFlowerSolution implements Solution {

    public static final Point2D AREA_CENTER = new Point2D.Double(Constraints.MAX_WIDTH / 2, Constraints.MAX_HEIGHT / 2);

    @Override
    public Plot createSolution(int numOfBeacons) {

        Plot solution = new Plot();

        solution.beacons.addAll(Lists.transform(getBeaconCenters(numOfBeacons - 3), toBeacons(numOfBeacons - 3)));

        solution.beacons.addAll(ImmutableList.<Ellipse2D>builder()
                .add(Plot.Beacon(AREA_CENTER, 35))
                .add(Plot.Beacon(AREA_CENTER, 45))
                .add(Plot.Beacon(AREA_CENTER, 60))
                .build());

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

        double angle = 0;

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
        //TODO:
        return 50d;
    }

    private double getSignalStrength(int numOfPoints)
    {
        //TODO: do it wiselly
        return 55d;
    }
}
