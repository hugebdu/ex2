package idc.edu.ex2.solution;

import static java.util.Arrays.asList;
import idc.edu.ex2.geometry.Area;
import idc.edu.ex2.geometry.Beacon;
import idc.edu.ex2.geometry.Constraints;
import idc.edu.ex2.geometry.Point;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: alexanderva
 * Date: 4/9/12
 * Time: 7:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class DanilasDoubleSpectrumSolution implements Solution {
    @Override
    public Area createSolution(int numOfBeacons) {

        Area area = new Area();

        for (SingularPointDescriptor descriptor : getSingularPointDescriptors(numOfBeacons))
        {
            for (int i = 0; i < descriptor.numOfBeacons; i++)
            {
                area.addBeacon(Beacon.Beacon(descriptor.center,
                        descriptor.signalStrengthProvider.getSignalStrength(descriptor.center, i, descriptor.numOfBeacons)));
            }
        }

        return area;
    }

    private Collection<SingularPointDescriptor> getSingularPointDescriptors(int numOfBeacons)
    {
//        final SignalStrengthProvider signalStrengthProvider = new LinearSignalStrengthProvider(20);
        final SignalStrengthProvider signalStrengthProvider = new LinearSignalStrengthProvider(Constraints.MAX_WIDTH / (numOfBeacons /  Math.E));

        return asList(
            new SingularPointDescriptor(Point.Point(0, 0),
                (int) Math.floor(numOfBeacons / 2), signalStrengthProvider),

            new SingularPointDescriptor(Point.Point(0, Constraints.MAX_HEIGHT),
                (int) Math.floor(numOfBeacons / 2), signalStrengthProvider)
        );
    }

    class SingularPointDescriptor
    {
        final Point center;
        final int numOfBeacons;
        final SignalStrengthProvider signalStrengthProvider;

        SingularPointDescriptor(Point center, int numOfBeacons, SignalStrengthProvider signalStrengthProvider) {
            this.center = center;
            this.numOfBeacons = numOfBeacons;
            this.signalStrengthProvider = signalStrengthProvider;
        }
    }

    class LinearSignalStrengthProvider implements SignalStrengthProvider
    {
        final double coefficient;

        LinearSignalStrengthProvider(double coefficient) {
            this.coefficient = coefficient;
        }

        @Override
        public double getSignalStrength(Point center, int index, int totalBeacons) {
            return (index + 1) * coefficient;
        }
    }

    interface SignalStrengthProvider
    {
        double getSignalStrength(Point center, int index, int totalBeacons);
    }



}
