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
 * Time: 8:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class AlexandersGridSolution implements Solution 
{
    @Override
    public Plot createSolution(int numOfBeacons) {

        List<Point2D> beaconCenters = generateCenters(numOfBeacons);

        Plot plot = new Plot();
        plot.beacons.addAll(Lists.transform(beaconCenters, applySignalStrength(numOfBeacons)));

        fillRemaining(plot, numOfBeacons);

        return plot;
    }

    private void fillRemaining(Plot plot, int numOfBeacons) {
        Plot partialSolution = new InnaKatzFlowerSolution().createSolution(numOfBeacons - plot.beacons.size());
        plot.beacons.addAll(partialSolution.beacons);
    }

    private Function<Point2D, Ellipse2D> applySignalStrength(final int numOfBeacons) {
        return new Function<Point2D, Ellipse2D>()
        {
            @Override
            public Ellipse2D apply(Point2D point)
            {
                return Plot.Beacon(point, getSignalStrength(numOfBeacons));
            }
        };
    }

    private double getSignalStrength(int numOfBeacons)
    {
        //TODO: wise
        return 50;
    }

    private List<Point2D> generateCenters(int numOfBeacons) {
        //TODO: handle extremal input

        int numPerAxis = calculatePerAxis(numOfBeacons);

        ImmutableList.Builder<Point2D> builder = ImmutableList.builder();

        double deltaX = Constraints.MAX_WIDTH / numPerAxis;
        double deltaY = Constraints.MAX_HEIGHT / numPerAxis;

        for (int x = 1; x <= numPerAxis; x++)
        {
            for (int y = 1; y <= numPerAxis; y++)
            {
                builder.add(new Point2D.Double(-deltaX / 2 + deltaX * x, -deltaY / 2 + deltaY * y));
            }
        }

        return builder.build();
    }

    private int calculatePerAxis(int numOfBeacons) {
        return (int) Math.floor(Math.sqrt(numOfBeacons));
    }
}
