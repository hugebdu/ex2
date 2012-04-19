package idc.edu.ex2.solution;

import idc.edu.ex2.Constraints;
import idc.edu.ex2.Plot;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.Collection;

import static java.util.Arrays.asList;

/**
 * Created with IntelliJ IDEA.
 * User: alexanderva
 * Date: 4/9/12
 * Time: 7:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class DanilasDoubleSpectrumSolution implements Solution {
    @Override
    public Plot createSolution(int numOfBeacons) {

        Plot plot = new Plot();

        for (SingularPointDescriptor descriptor : getSingularPointDescriptors(numOfBeacons))
        {
            for (int i = 0; i < descriptor.numOfBeacons; i++)
            {
                double signalStrength = descriptor.signalStrengthProvider.getSignalStrength(descriptor.center, i, descriptor.numOfBeacons);
                plot.beacons.add(new Ellipse2D.Double(
                        descriptor.center.getX() - signalStrength,
                        descriptor.center.getY() - signalStrength,
                        signalStrength * 2,
                        signalStrength * 2));
            }
        }

        return plot;
    }

    private Collection<SingularPointDescriptor> getSingularPointDescriptors(int numOfBeacons)
    {
//        final SignalStrengthProvider signalStrengthProvider = new LinearSignalStrengthProvider(20);
        final SignalStrengthProvider signalStrengthProvider = new LinearSignalStrengthProvider(Constraints.MAX_WIDTH / (numOfBeacons /  Math.E));

        return asList(
            new SingularPointDescriptor(new Point2D.Double(0, 0),
                (int) Math.floor(numOfBeacons / 2), signalStrengthProvider),

            new SingularPointDescriptor(new Point2D.Double(0, Constraints.MAX_HEIGHT),
                (int) Math.floor(numOfBeacons / 2), signalStrengthProvider)
        );
    }

    class SingularPointDescriptor
    {
        final Point2D center;
        final int numOfBeacons;
        final SignalStrengthProvider signalStrengthProvider;

        SingularPointDescriptor(Point2D center, int numOfBeacons, SignalStrengthProvider signalStrengthProvider) {
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
        public double getSignalStrength(Point2D center, int index, int totalBeacons) {
            return (index + 1) * coefficient;
        }
    }

    interface SignalStrengthProvider
    {
        double getSignalStrength(Point2D center, int index, int totalBeacons);
    }



}
