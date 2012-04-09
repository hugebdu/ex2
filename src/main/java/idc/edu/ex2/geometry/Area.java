package idc.edu.ex2.geometry;

import java.util.Set;

import static com.google.common.collect.Lists.newLinkedList;
import static com.google.common.collect.Sets.newHashSet;

/**
 * Created by IntelliJ IDEA.
 * User: daniels
 * Date: 3/25/12
 */
public class Area
{
    public final Set<Beacon> beaconsSet = newHashSet();
    public final Set<Point> trackedPoints = newHashSet();
    
    public Area addBeacon(Beacon beacon)
    {
        beaconsSet.add(beacon);
        return this;
    }

    public Area addAllBeacons(Iterable<Beacon> beacons)
    {
        beaconsSet.addAll(newLinkedList(beacons));
        return this;
    }
    
    public Area addTrackedPoint(Point point)
    {
        trackedPoints.add(point);
        return this;
    }
}
