package idc.edu.ex2.gui;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.EventObject;

import static java.lang.String.format;

/**
 * Created by IntelliJ IDEA.
 * User: daniels
 * Date: 3/30/12
 */
public class SidePanel extends JPanel
{
    private final CanvasPanel canvasPanel;

    public SidePanel(CanvasPanel canvasPanel)
    {
        this.canvasPanel = canvasPanel;
        add(new ToolBar(), BorderLayout.PAGE_START);
        add(new MouseOverElementPanel(), BorderLayout.PAGE_END);
        add(new SegmentAreaPanel(), BorderLayout.PAGE_START);
        add(new MaxSegmentSizePanel(), BorderLayout.PAGE_END);
    }

    class ToolBar extends JToolBar
    {
          public ToolBar()
          {
              add(createAreaButton());
              //TODO
          }

        private JToggleButton createAreaButton()
        {
            return new JToggleButton("Area");
        }
    }
    
    class SegmentAreaPanel extends JPanel
    {
        public SegmentAreaPanel()
        {
            add(new JLabel("Segment area: "));
            //TODO
        }
    }
    
    class BeaconInfoPanel extends JPanel
    {
        //TODO
    }

    class MaxSegmentSizePanel extends JPanel implements CanvasPanel.MaxSegmentChangeListener
    {
        private JLabel label;

        public MaxSegmentSizePanel()
        {
            this.label = new JLabel("Max segment size: NA");
            add(label);
            canvasPanel.registerMaxSegmentChangeListener(this);
        }

        public void onMaxSegmentCalculated(int size)
        {
            label.setText(format("Max segment size: %d", size));
        }
    }

    class MouseOverElementPanel extends JPanel implements CanvasPanel.MouseListener
    {
        private static final String NONE = "";
        private final JLabel label;

        public MouseOverElementPanel()
        {
            canvasPanel.registerMouseListener(this);
            setLayout(new GridLayout(1, 2));
            label = new JLabel(NONE, SwingConstants.LEFT);
            add(label);
        }

        public void onMouseOver(EventObject event)
        {
            label.setText(event.getSource().toString());
        }

        public void onMouseOut(EventObject event)
        {
            label.setText(NONE);
        }
    }
}
