package idc.edu.ex2.geometry;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;

import java.util.Set;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * Created by IntelliJ IDEA.
 * User: daniels
 * Date: 3/25/12
 */
public abstract class GeometryUtils
{
    public static Set<Beacon> getTrackingBeacons(Point point, Set<Beacon> beacons)
    {
        ImmutableSet.Builder<Beacon> builder = ImmutableSet.builder();
        
        for (Beacon beacon : beacons)
        {
            if (beacon.isTracking(point))
                builder.add(beacon);
        }
        
        return builder.build();
    }
    
    public static double distance(Point point1, Point point2)
    {
        return sqrt(pow(point2.x - point1.x, 2) + pow(point2.y - point1.y, 2));
    }
    
    public static Multimap<Set<Beacon>, Point> buildCoverageMap(Area area)
    {
        ImmutableMultimap.Builder<Set<Beacon>, Point> builder = ImmutableMultimap.builder();

        for (Point point : area.trackedPoints)
            builder.put(getTrackingBeacons(point, area.beaconsSet), point);

        return builder.build();
    }
}
