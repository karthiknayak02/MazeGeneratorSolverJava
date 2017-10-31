import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;

public class controlBoard {

    private JButton generate, solve, stop;
    private JLabel speedLabel, rowsLabel, colsLabel;
    private JSlider rowsSlider, colsSlider;
    public JCheckBox animation;
    public static JSlider speedSlider;
    public int x = 10, y = 10;

    public controlBoard() {}

    public controlBoard(JPanel controlView, ActionListener AL){

        generate    = new JButton("Generate");
        solve       = new JButton("Solve");
        stop        = new JButton("Stop");

        generate.addActionListener(AL);
        solve.addActionListener(AL);
        stop.addActionListener(AL);

        animation   = new JCheckBox("Show Animation", true);

        speedLabel      = new JLabel("Speed:");
        rowsLabel       = new JLabel("Rows: 10");
        colsLabel       = new JLabel("Cols: 10");

        speedSlider     = new JSlider( SwingConstants.HORIZONTAL, 1, 50, 10 );
        rowsSlider      = new JSlider( SwingConstants.HORIZONTAL, 10, 50, 10 );
        colsSlider      = new JSlider( SwingConstants.HORIZONTAL, 10, 50, 10 );

        speedSlider.setMajorTickSpacing( 10 );
        rowsSlider.setMajorTickSpacing( 10 );
        colsSlider.setMajorTickSpacing( 10 );

        speedSlider.setPaintTicks( true );
        rowsSlider.setPaintTicks( true );
        colsSlider.setPaintTicks( true );

        speedSlider.setInverted(true);

        rowsSlider.addChangeListener( new ChangeListener() { public void stateChanged( ChangeEvent e ) {
            x = rowsSlider.getValue();
            rowsLabel.setText("Rows: " + x);
        }});

        colsSlider.addChangeListener( new ChangeListener() { public void stateChanged( ChangeEvent e ) {
            y = colsSlider.getValue();
            colsLabel.setText("Cols: " + y);
        }});

        controlView.setLayout(new GridLayout(10, 1, 0, 0));
        controlView.add(generate);
        controlView.add(solve);
        controlView.add(animation);
        controlView.add(speedLabel);
        controlView.add(speedSlider);
        controlView.add(rowsLabel);
        controlView.add(rowsSlider);
        controlView.add(colsLabel);
        controlView.add(colsSlider);
        controlView.add(stop);
    }

}
