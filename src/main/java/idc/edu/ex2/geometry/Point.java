package idc.edu.ex2.geometry;

import java.util.Random;

import static com.google.common.base.Preconditions.checkArgument;
import static idc.edu.ex2.geometry.Constraints.MAX_HEIGHT;
import static idc.edu.ex2.geometry.Constraints.MAX_WIDTH;

/**
 * Created by IntelliJ IDEA.
 * User: daniels
 * Date: 3/25/12
 */
public class Point
{
    public final double x;
    public final double y;

    public Point(double x, double y)
    {
        checkArgument(x >= 0 && x <= MAX_WIDTH);
        checkArgument(y >= 0 && y <= MAX_HEIGHT);

        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (Double.compare(point.x, x) != 0) return false;
        if (Double.compare(point.y, y) != 0) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result;
        long temp;
        temp = x != +0.0d ? Double.doubleToLongBits(x) : 0L;
        result = (int) (temp ^ (temp >>> 32));
        temp = y != +0.0d ? Double.doubleToLongBits(y) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString()
    {
        return "(" + x + "," + y + ")";
    }

    public static Point Point(double x, double y)
    {
        return new Point(x, y);
    }

    public static Point random()
    {
        Random random = new Random();
        return Point(random.nextDouble() * 100d, random.nextDouble() * 100d);
    }
}
