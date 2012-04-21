package idc.edu.ex2;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.io.CharStreams;
import com.google.common.io.InputSupplier;
import com.google.common.io.LineProcessor;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static com.google.common.collect.Iterables.toArray;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;

/**
 * Created by IntelliJ IDEA.
 * User: daniels
 * Date: 4/15/12
 */
public class Plot
{
    private static final Splitter SPLITTER = Splitter.on(',').trimResults();

    private static final Comparator<Map.Entry<BitSet, Collection<Point2D>>> byNumOfPoints = new Comparator<Map.Entry<BitSet, Collection<Point2D>>>()
    {
        @Override
        public int compare(Map.Entry<BitSet, Collection<Point2D>> o1, Map.Entry<BitSet, Collection<Point2D>> o2)
        {
            return o1.getValue().size() - o2.getValue().size();
        }
    };

    public List<Ellipse2D> beacons = newArrayList();
    public Set<Point2D> samplingPoints = newHashSet();

    public void loadSamplingPoints(InputSupplier<InputStreamReader> inputStream)
    {
        try
        {
            samplingPoints = CharStreams.readLines(inputStream, new Point2DSetLineProcessor());
        }
        catch (IOException e)
        {
            throw new RuntimeException("Failed to load sampling points from file", e);
        }
    }

    public ImmutableMultimap<BitSet, Point2D> calculateSegmentsMap()
    {
        ImmutableMultimap.Builder<BitSet, Point2D> builder = ImmutableMultimap.builder();

        for (Point2D point : samplingPoints)
        {
            final BitSet key = new BitSet(beacons.size());

            for (int i = 0; i < beacons.size(); i++)
            {
                if (beacons.get(i).contains(point))
                    key.set(i);
            }

            builder.put(key, point);
        }

        return builder.build();
    }

    public Map.Entry<BitSet, Collection<Point2D>> calculateAndGetLargestSegment()
    {
        return Collections.max(calculateSegmentsMap().asMap().entrySet(), byNumOfPoints);
    }
    
    public static Ellipse2D Beacon(Point2D center, double signalStrength)
    {
        double x = center.getX() - signalStrength;
        double y = center.getY() - signalStrength;
        double width = signalStrength * 2;
        double height = signalStrength * 2;
        return new Ellipse2D.Double(x, y, width, height);
    }

    private static class Point2DSetLineProcessor implements LineProcessor<Set<Point2D>>
    {
        final Set<Point2D> result = newHashSet();

        @Override
        public boolean processLine(String line) throws IOException
        {
            String[] pair = toArray(SPLITTER.split(line), String.class);
            result.add(new Point2D.Double(Double.parseDouble(pair[0]), Double.parseDouble(pair[1])));
            return true;
        }

        @Override
        public Set<Point2D> getResult()
        {
            return result;
        }
    }
}
