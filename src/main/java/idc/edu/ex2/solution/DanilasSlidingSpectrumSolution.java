package idc.edu.ex2.solution;

import com.google.common.primitives.Doubles;
import idc.edu.ex2.Constraints;
import idc.edu.ex2.Plot;

import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: daniels
 * Date: 4/18/12
 */
public class DanilasSlidingSpectrumSolution implements Solution
{
    enum Diagonal { Left, Right }

    private double[] shifts = null;

    @Override
    public Plot createSolution(int numOfBeacons)
    {
        Plot plot = new Plot();

        int numOfBeaconsPerDiagonal = numOfBeacons / 2;
        
        for (int i = 0; i < numOfBeaconsPerDiagonal; i++)
        {
            Point2D center = getDiagonalCenter(Diagonal.Left, i, numOfBeaconsPerDiagonal);
            plot.beacons.add(Plot.Beacon(center, getSignalStrength(Diagonal.Left, i, numOfBeaconsPerDiagonal, center)));
        }

        for (int i = 0; i < numOfBeaconsPerDiagonal; i++)
        {
            Point2D center = getDiagonalCenter(Diagonal.Right, i, numOfBeaconsPerDiagonal);
            plot.beacons.add(Plot.Beacon(center, getSignalStrength(Diagonal.Left, i, numOfBeaconsPerDiagonal, center)));
        }

        return plot;
    }
    
    private double getSignalStrength(Diagonal diagonal, int index, int totalPerDiagonal, Point2D center)
    {
        return Doubles.min(center.getX(), center.getY(), Constraints.MAX_HEIGHT - center.getY(), Constraints.MAX_WIDTH - center.getX()) + 2;
//        return 2;
    }
    
    private Point2D getDiagonalCenter(Diagonal diagonal, int index, int totalPerDiagonal)
    {
        double shift = calculateShift(index, totalPerDiagonal);
        System.out.println(shift);
        double x = Math.sin(Math.PI / 4) * shift;
        
        double y = Diagonal.Left.equals(diagonal) ? x : Constraints.MAX_HEIGHT - x;
        return new Point2D.Double(x, y);
    }
    
    private double calculateShift(int index, int totalPerDiagonal)
    {
        if (shifts == null)
            calculateAllShifts(totalPerDiagonal);

        return shifts[index];
    }

    private void calculateAllShifts(int totalPerDiagonal)
    {
        shifts = new double[totalPerDiagonal];

        double diagonalLength = Math.sqrt(Math.pow(Constraints.MAX_HEIGHT, 2) + Math.pow(Constraints.MAX_WIDTH, 2));
        double baseStep = diagonalLength / totalPerDiagonal;
        double bias = baseStep / 2;
        double shift = bias;

        for (int i = 0; i < shifts.length; i++)
        {
            shifts[i] = shift;
            shift += baseStep;
        }

        int half = totalPerDiagonal / 2;

        for (int i = 0; i < half - 1; i++)
        {
            double delta = 0.7;

            for (int j = i; j < half; j++)
            {
                shifts[j] += delta;
                shifts[totalPerDiagonal - 1 - j] -= delta;
            }
        }

        System.out.println(shifts);
    }
}
