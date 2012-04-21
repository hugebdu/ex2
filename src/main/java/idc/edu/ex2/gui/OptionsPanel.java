package idc.edu.ex2.gui;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import idc.edu.ex2.Constraints;
import idc.edu.ex2.ResourceUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by IntelliJ IDEA.
 * User: daniels
 * Date: 4/21/12
 */
public class OptionsPanel extends JPanel
{
    private static final String N_A = "N/A";
    private static final String _64_BEACONS = "64";
    
    final JComboBox pointsFile;
    final JTextField numOfBeacons;
    final JTextField maxSegmentSize;
    final JButton calculateButton;
    final EventBus eventBus;

    public OptionsPanel(EventBus eventBus)
    {
        this.eventBus = eventBus;
        eventBus.register(this);
        
        setBorder(new EmptyBorder(5, 10, 5, 10));
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;

        pointsFile = createPointsFileCompoBox();
        numOfBeacons = createNumOfBeaconsTextBox();
        maxSegmentSize = createMaxSegmentSizeTextBox();
        calculateButton = createCalculateButton();

        c.gridx = 0;
        c.gridy = 0;
        add(new JLabel("Input points file:"), c);

        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 1;
        add(pointsFile, c);

        c.gridx = 0;
        c.gridy = 1;
        add(new JLabel("Number of beacons:"), c);

        c.gridx = 1;
        c.gridy = 1;
        add(numOfBeacons, c);

        c.gridx = 0;
        c.gridy = 2;
        add(new JLabel("Max segment size (pts):"), c);

        c.gridx = 1;
        c.gridy = 2;
        add(maxSegmentSize, c);

        c.gridx = 1;
        c.gridy = 3;
        add(calculateButton, c);

        eventBus.post(new OptionsChangedEvent(
                (String) pointsFile.getSelectedItem(),
                64));
    }



    private JButton createCalculateButton()
    {
        JButton button = new JButton("Calculate");
        button.addActionListener(new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    int numOfBeaconsValue = Integer.parseInt(numOfBeacons.getText());

                    checkArgument(numOfBeacons != null &&
                            numOfBeaconsValue >= 0 &&
                            numOfBeaconsValue <= Constraints.MAX_NUM_OF_BEACONS,
                            "Illegal number of beacons entered");

                    eventBus.post(new OptionsChangedEvent(
                            (String) pointsFile.getSelectedItem(),
                            numOfBeaconsValue
                    ));
                }
                catch (Exception e1)
                {
                    JOptionPane.showMessageDialog(OptionsPanel.this, e1.getMessage(), "Illegal input value", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        return button;
    }

    private JTextField createMaxSegmentSizeTextBox()
    {
        JTextField field = new JTextField(N_A);
        field.setEditable(false);
        return field;
    }

    private JTextField createNumOfBeaconsTextBox()
    {
        JTextField field = new JTextField(_64_BEACONS);
        return field;
    }

    private JComboBox createPointsFileCompoBox()
    {
        JComboBox box = new JComboBox(ResourceUtils.listPointFiles());
        return box;
    }

    @Subscribe
    public void onMaxSegmentChangedEvent(MaxSegmentChangedEvent event)
    {
        this.maxSegmentSize.setText(event.maxSegmentSize == null ? N_A : event.maxSegmentSize.toString());
    }
    
    public static class MaxSegmentChangedEvent
    {
        public final Integer maxSegmentSize;

        public MaxSegmentChangedEvent(Integer maxSegmentSize)
        {
            this.maxSegmentSize = maxSegmentSize;
        }

        @Override
        public String toString()
        {
            return "MaxSegmentChangedEvent{" +
                    "maxSegmentSize=" + maxSegmentSize +
                    '}';
        }
    }

    public static class OptionsChangedEvent
    {
        public final String pointsFile;
        public final int numOfBeacons;

        OptionsChangedEvent(String pointsFile, int numOfBeacons)
        {
            this.pointsFile = pointsFile;
            this.numOfBeacons = numOfBeacons;
        }


        @Override
        public String toString()
        {
            return "OptionsChangedEvent{" +
                    "pointsFile='" + pointsFile + '\'' +
                    ", numOfBeacons=" + numOfBeacons +
                    '}';
        }
    }
}
