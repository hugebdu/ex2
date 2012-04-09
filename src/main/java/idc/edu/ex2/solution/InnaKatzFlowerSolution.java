package idc.edu.ex2.solution;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import idc.edu.ex2.geometry.Area;
import idc.edu.ex2.geometry.Beacon;
import idc.edu.ex2.geometry.Constraints;
import idc.edu.ex2.geometry.Point;

import java.util.Collection;

import static com.google.common.collect.Iterables.transform;

/**
 * Created with IntelliJ IDEA.
 * User: alexanderva
 * Date: 4/9/12
 * Time: 6:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class InnaKatzFlowerSolution implements Solution {

    public static final Point AREA_CENTER = new Point(Constraints.MAX_WIDTH / 2, Constraints.MAX_HEIGHT / 2);

    @Override
    public Area createSolution(int numOfBeacons) {
        Area solution = new Area().addAllBeacons(transform(getBeaconCenters(numOfBeacons - 3), toBeacons(numOfBeacons - 3)));

        solution.addBeacon(Beacon.Beacon(AREA_CENTER, 35))
                .addBeacon(Beacon.Beacon(AREA_CENTER, 45))
                .addBeacon(Beacon.Beacon(AREA_CENTER, 55));

        return solution;
    }

    private Function<Point, Beacon> toBeacons(final int numOfBeacons) {
        return new Function<Point, Beacon>() {
            @Override
            public Beacon apply(Point point) {
                return new Beacon(point, getSignalStrength(numOfBeacons));
            }
        };
    }

    private Collection<Point> getBeaconCenters(int numOfBeacons)
    {
        final double theta = Math.PI * 2 / numOfBeacons;

        double angle = 0;

        ImmutableList.Builder<Point> builder = ImmutableList.builder();

        for (int i = 0; i < numOfBeacons; i++)
        {
            builder.add(makeBeaconCenter(angle));
            angle += theta;
        }

        return builder.build();
    }

    private Point makeBeaconCenter(double theta) {
        return Point.Point(AREA_CENTER.x + getDistanceFromCenter() * Math.cos(theta),
                AREA_CENTER.y + getDistanceFromCenter() * Math.sin(theta));
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
