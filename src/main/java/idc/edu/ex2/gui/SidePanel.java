package idc.edu.ex2.gui;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.EventObject;

/**
 * Created by IntelliJ IDEA.
 * User: daniels
 * Date: 3/30/12
 */
public class SidePanel extends JPanel
{
    public SidePanel(CanvasPanel canvasPanel)
    {
        add(new MouseOverElementPanel(canvasPanel), BorderLayout.PAGE_END);

    }



    class MouseOverElementPanel extends JPanel implements CanvasPanel.MouseListener
    {
        private static final String NONE = "";
        private final JLabel label;

        public MouseOverElementPanel(CanvasPanel canvasPanel)
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
