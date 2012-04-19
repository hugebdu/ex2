package idc.edu.ex2.solution;

import com.google.common.primitives.Doubles;
import idc.edu.ex2.Constraints;
import idc.edu.ex2.Plot;

import java.awt.geom.Ellipse2D;

/**
 * Created by IntelliJ IDEA.
 * User: daniels
 * Date: 4/18/12
 */
public class DanilasSlidingSpectrumSolution implements Solution
{
    @Override
    public Plot createSolution(int numOfBeacons)
    {
        Plot plot = new Plot();
        final double goldenRatio = (1d + Math.sqrt(5)) / 2d;

        for (int i = 0; i < numOfBeacons; i++)
        {
            double factor = Math.pow(0.997, Math.min(i, numOfBeacons - i));
            double x = Constraints.MAX_WIDTH / numOfBeacons * (i + 1) - Constraints.MAX_WIDTH / numOfBeacons / 2;
            double radius = Doubles.min(x * factor, (Constraints.MAX_WIDTH - x) * factor) + 2;
            plot.beacons.add(new Ellipse2D.Double(x - radius, x - radius, radius * 2, radius * 2));
        }

//        for (int i = 0; i < numOfBeacons; i++)
//        {
//            double factor = Math.pow(0.997, Math.min(i, numOfBeacons - i));
//            double x = Constraints.MAX_WIDTH / numOfBeacons * (i + 1) - Constraints.MAX_WIDTH / numOfBeacons / 2;
//            double y = Constraints.MAX_HEIGHT - Constraints.MAX_HEIGHT / numOfBeacons * (i + 1) + Constraints.MAX_HEIGHT / numOfBeacons / 2;
//            double radius = Doubles.min(x * factor, (Constraints.MAX_WIDTH - x) * factor) + 2;
//            plot.beacons.add(new Ellipse2D.Double(x - radius, y - radius, radius * 2, radius * 2));
//        }

        return plot;
    }
}
