package idc.edu.ex2.geometry;

import com.google.common.collect.ImmutableSet;
import org.junit.Test;

import java.util.Set;

import static idc.edu.ex2.geometry.Beacon.Beacon;
import static idc.edu.ex2.geometry.GeometryUtils.distance;
import static idc.edu.ex2.geometry.GeometryUtils.getTrackingBeacons;
import static idc.edu.ex2.geometry.Point.Point;
import static java.lang.Math.sqrt;
import static org.hamcrest.collection.IsCollectionContaining.hasItem;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

/**
 * Created by IntelliJ IDEA.
 * User: daniels
 * Date: 3/25/12
 */
public class GeometryUtilsTest
{
    @Test
    public void testDistance() throws Exception
    {
        assertThat(distance(Point(0, 0), Point(0, 2)), is(2d));
        assertThat(distance(Point(0, 0), Point(1, 1)), is(sqrt(2)));
    }

    @Test
    public void testGetTrackingBeacons() throws Exception
    {
        Set<Beacon> beacons = ImmutableSet.<Beacon>builder()
                .add(Beacon(Point(2, 2), 2))
                .add(Beacon(Point(3, 0), 0.8))
                .build();
        
        Set<Beacon> tracking = getTrackingBeacons(Point(2, 0), beacons);
        
        assertThat(tracking, hasItem(Beacon(Point(2, 2), 2)));
        assertThat(tracking, not(hasItem(Beacon(Point(3, 0), 0.8))));
    }
}
