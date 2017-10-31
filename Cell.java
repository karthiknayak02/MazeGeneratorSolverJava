import javax.swing.*;
import java.awt.*;

public class Cell extends JPanel
{
    // location
    public int x, y;
    public static int rows = 0, cols = 0;
    int[] sides = {1, 1, 1, 1};

    // walls
    public boolean visited;  // is reset for generator and solver

    public Cell(int i, int j){
        super();
        super.setBackground(new Color(17, 17, 17));
        super.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(244, 180, 0)));

        setPreferredSize(new Dimension(30, 30));

        rows++;
        cols++;

        x = i;
        y = j;
    }

    //(244, 180, 0)
    public void fillOrange() { setBackground(new Color(244, 180, 0)); }
    public void fillPurple() { setBackground(new Color(65, 38, 155)); }
    public void fillGrey()   { setBackground(new Color(43, 45, 45)); }
    public void fillPink()   { setBackground(new Color(79, 21, 72)); }
    public void fillGreen()   { setBackground(new Color(53, 189, 184)); }

    /*
            0
        1       3
            2
     */
    public void removeWall(int i){
        sides[i] = 0;
        super.setBorder(BorderFactory.createMatteBorder(sides[0], sides[1], sides[2], sides[3], Color.ORANGE));
    }

}
