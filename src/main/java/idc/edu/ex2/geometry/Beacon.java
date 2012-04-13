package idc.edu.ex2.geometry;

import static idc.edu.ex2.geometry.GeometryUtils.distance;

/**
 * Created by IntelliJ IDEA.
 * User: daniels
 * Date: 3/25/12
 */
public class Beacon
{
    public final Point center;
    public final double signalStrength;

    public Beacon(Point center, double signalStrength)
    {
//        checkArgument(signalStrength > 0);

        this.center = center;
        this.signalStrength = signalStrength;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Beacon beacon = (Beacon) o;

        if (Double.compare(beacon.signalStrength, signalStrength) != 0) return false;
        if (center != null ? !center.equals(beacon.center) : beacon.center != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result;
        long temp;
        result = center != null ? center.hashCode() : 0;
        temp = signalStrength != +0.0d ? Double.doubleToLongBits(signalStrength) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString()
    {
        return "Beacon{" +
                "center=" + center +
                ", signalStrength=" + signalStrength +
                '}';
    }

    public boolean isTracking(Point point)
    {
        return distance(center, point) <= signalStrength;
    }
    
    public static Beacon Beacon(Point center, double signalStrength)
    {
        return new Beacon(center, signalStrength);
    }
}
