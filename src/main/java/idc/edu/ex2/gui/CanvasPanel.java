package idc.edu.ex2.gui;

import idc.edu.ex2.geometry.Area;
import idc.edu.ex2.geometry.Beacon;
import idc.edu.ex2.geometry.Point;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.EventObject;
import java.util.List;

import static com.google.common.collect.Lists.newLinkedList;
import static java.lang.Math.round;

/**
 * Created by IntelliJ IDEA.
 * User: daniels
 * Date: 3/25/12
 */
public class CanvasPanel extends JPanel
{
    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;
    private static final Dimension DIMENSION = new Dimension(WIDTH, HEIGHT);
    private static final int PADDING_SIZE = 5;
    private static final Cursor HAND_CURSOR = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
    private static final int POINT_RADIUS = 3;
    private static final int CROSS_WIDTH = 4;

    private AreaGUI area;
    private List<MouseListener> mouseListeners = newLinkedList();

    public CanvasPanel()
    {
        setBackground(Color.WHITE);
        setOpaque(true);

        setPreferredSize(DIMENSION);
        setSize(DIMENSION);

        setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    public void setArea(Area area)
    {
        this.area = new AreaGUI(area);
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        if (area != null)
            area.draw((Graphics2D) g);
    }

    private int scaleX(double x)
    {
        return (int) round(x * 6);
    }
    
    private int scaleY(double y)
    {
        return (int) round(HEIGHT - y * 6);
    }

    public void registerMouseListener(MouseListener listener)
    {
        this.mouseListeners.add(listener);
    }

    enum Mode 
    {
        NA,
        Hover,
        Selected
    }
    
    abstract class BaseFigure<T>
    {
        private final T model;
        private int guiX;
        private int guiY;
        private Mode mode = Mode.NA;

        public BaseFigure(T model, int guiX, int guiY)
        {
            this.model = model;
            this.guiY = guiY;
            this.guiX = guiX;
        }

        public abstract void draw(Graphics2D g);

        public Mode getMode()
        {
            return mode;
        }

        public void setMode(Mode mode)
        {
            this.mode = mode;
        }

        public T getModel()
        {
            return model;
        }

        public int getGuiX()
        {
            return guiX;
        }

        public int getGuiY()
        {
            return guiY;
        }

        public void setGuiX(int guiX)
        {
            this.guiX = guiX;
            //TODO: adjust model
        }

        public void setGuiY(int guiY)
        {
            this.guiY = guiY;
            //TODO: adjust model
        }
    }
    
    class PointFigure extends BaseFigure<Point>
    {
        public PointFigure(Point point)
        {
            super(point, scaleX(point.x), scaleY(point.y));
        }

        @Override
        public void draw(Graphics2D g)
        {
            g.setColor(Color.BLUE);
            g.fillArc(getGuiX() + POINT_RADIUS / 2, getGuiY() + POINT_RADIUS / 2, POINT_RADIUS, POINT_RADIUS, 0, 360);
        }
    }
    
    class BeaconFigure extends BaseFigure<Beacon>
    {
        public BeaconFigure(Beacon beacon)
        {
            super(beacon, scaleX(beacon.center.x), scaleY(beacon.center.y));
        }
        
        @Override
        public void draw(Graphics2D g)
        {
            Composite originalComposite = g.getComposite();

            g.setColor(Color.red);
            int halfSize = CROSS_WIDTH / 2;
            g.drawLine(getGuiX() - halfSize, getGuiY(), getGuiX() + halfSize, getGuiY());
            g.drawLine(getGuiX(), getGuiY() - halfSize, getGuiX(), getGuiY() + halfSize);

            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.002f));
            int radius = scaleX(getModel().signalStrength);
            g.fillArc(getGuiX() - radius / 2, getGuiY() - radius / 2, radius, radius, 0, 360);

            g.setComposite(originalComposite);
        }
    }
    
    class AreaGUI 
    {
        private final BaseFigure<?>[][] matrix;
        
        private BaseFigure<?> overElement = null;
        
        public AreaGUI(Area area)
        {
            matrix = new BaseFigure[WIDTH][HEIGHT];
            initFigures(area);
            registerMouseMotionListener();
        }

        private void registerMouseMotionListener()
        {
            addMouseMotionListener(new MouseMotionAdapter()
            {
                @Override
                public void mouseMoved(MouseEvent e)
                {
                    BaseFigure<?> figure = matrix[e.getX()][e.getY()];

                    if (figure != null)
                    {
                        fireMouseOver(figure.getModel());
                        overElement = figure;
                        setFingerCursor();
                    }
                    else
                    {
                        if (overElement != null)
                        {
                            fireMouseOut(overElement.getModel());
                            overElement = null;
                            resetCursor();
                        }
                    }
                }

                @Override
                public void mouseDragged(MouseEvent e)
                {
                    System.out.println(e.getLocationOnScreen());
                    //TODO
                    if (overElement != null)
                        setMoveCursor();
                }
            });

            addMouseListener(new MouseAdapter()
            {
                @Override
                public void mousePressed(MouseEvent e)
                {
                    System.out.println("Mouse pressed");
                }

                @Override
                public void mouseReleased(MouseEvent e)
                {
                    System.out.println("Mouse released");
                    //TODO
                    resetCursor();
                    
                    if (overElement != null)
                    {
                        clearFigureMarker(overElement);
                        overElement.setGuiX(e.getX());
                        overElement.setGuiY(e.getY());
                        placeFigureMarker(overElement);
                        overElement = null;
                        CanvasPanel.this.repaint();
                        //TODO
                    }
                }
            });
        }

        private void setMoveCursor()
        {
            setCursor(MOVE_CURSOR());
        }

        private void resetCursor()
        {
            setCursor(Cursor.getDefaultCursor());
        }

        private void setFingerCursor()
        {
            setCursor(HAND_CURSOR);
        }

        private void initFigures(Area area)
        {
            for (Point point : area.trackedPoints)
            {
                PointFigure figure = new PointFigure(point);

                placeFigureMarker(figure);
            }

            for (Beacon beacon : area.beaconsSet)
            {
                BeaconFigure figure = new BeaconFigure(beacon);
                placeFigureMarker(figure);
            }
        }

        private void clearFigureMarker(BaseFigure figure)
        {
            for (int x = figure.getGuiX() - PADDING_SIZE; x < figure.getGuiX() + PADDING_SIZE; x++)
                for (int y = figure.getGuiY() - PADDING_SIZE; y < figure.getGuiY() + PADDING_SIZE; y++)
                    if (isValidCoordinate(x, y))
                        matrix[x][y] = null;
        }

        private void placeFigureMarker(BaseFigure figure)
        {
            for (int x = figure.getGuiX() - PADDING_SIZE; x < figure.getGuiX() + PADDING_SIZE; x++)
                for (int y = figure.getGuiY() - PADDING_SIZE; y < figure.getGuiY() + PADDING_SIZE; y++)
                    if (isValidCoordinate(x, y))
                        matrix[x][y] = figure;
        }

        private boolean isValidCoordinate(int x, int y)
        {
            return x >= 0 && y >= 0 && x < WIDTH && y < HEIGHT;
        }

        public void draw(Graphics2D g)
        {
            for (BaseFigure<?>[] row : matrix)
            {
                for (BaseFigure<?> figure : row)
                {
                    if (figure != null)
                        figure.draw(g);
                }
            }
        }
    }

    private Cursor MOVE_CURSOR()
    {
        return Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);
    }

    private void fireMouseOut(Object model)
    {
        EventObject event = new EventObject(model);

        for (MouseListener listener : mouseListeners)
            listener.onMouseOut(event);
    }

    private void fireMouseOver(Object model)
    {
        EventObject event = new EventObject(model);

        for (MouseListener listener : mouseListeners)
            listener.onMouseOver(event);
    }

    public interface MouseListener
    {
        void onMouseOver(EventObject event);
        void onMouseOut(EventObject event);
    }
}
