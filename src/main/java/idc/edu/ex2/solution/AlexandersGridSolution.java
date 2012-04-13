package idc.edu.ex2.solution;

import static com.google.common.collect.Iterables.transform;
import idc.edu.ex2.geometry.Area;
import idc.edu.ex2.geometry.Beacon;
import idc.edu.ex2.geometry.Constraints;
import idc.edu.ex2.geometry.Point;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;

/**
 * Created with IntelliJ IDEA.
 * User: alexanderva
 * Date: 4/9/12
 * Time: 8:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class AlexandersGridSolution implements Solution {
    @Override
    public Area createSolution(int numOfBeacons) {

        Iterable<Point> beaconCenters = generateCenters(numOfBeacons);

        Area area = new Area();
        area.addAllBeacons(transform(beaconCenters, applySignalStrength(numOfBeacons)));

        fillRemaining(area, numOfBeacons);

        return area;
    }

    private void fillRemaining(Area area, int numOfBeacons) {
        Area partialSolution = new InnaKatzFlowerSolution().createSolution(numOfBeacons - area.beaconsSet.size());
        area.addAllBeacons(partialSolution.beaconsSet);
    }

    private Function<Point, Beacon> applySignalStrength(final int numOfBeacons) {
        return new Function<Point, Beacon>()
        {
            @Override
            public Beacon apply(Point point)
            {
                return Beacon.Beacon(point, getSignalStrength(numOfBeacons));
            }
        };
    }

    private double getSignalStrength(int numOfBeacons)
    {
        //TODO: wise
        return 50;
    }

    private Iterable<Point> generateCenters(int numOfBeacons) {
        //TODO: handle extremal input

        int numPerAxis = calculatePerAxis(numOfBeacons);

        ImmutableList.Builder<Point> builder = ImmutableList.builder();

        double deltaX = Constraints.MAX_WIDTH / numPerAxis;
        double deltaY = Constraints.MAX_HEIGHT / numPerAxis;

        for (int x = 1; x <= numPerAxis; x++)
        {
            for (int y = 1; y <= numPerAxis; y++)
            {
                builder.add(Point.Point(-deltaX / 2 + deltaX * x, -deltaY / 2 + deltaY * y));
            }
        }

        return builder.build();
    }

    private int calculatePerAxis(int numOfBeacons) {
        return (int) Math.floor(Math.sqrt(numOfBeacons));
    }
}
