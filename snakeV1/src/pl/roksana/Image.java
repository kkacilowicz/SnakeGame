package pl.roksana;

import java.awt.*;
import javax.swing.*;

public class Image extends JFrame{
    private ImageIcon image1;
    private JLabel label1;

    Image() {
        setLayout(new FlowLayout());

        image1 = new ImageIcon(getClass().getResource("logo.png"));
        label1 = new JLabel(image1);
        add(label1);
    }


}
