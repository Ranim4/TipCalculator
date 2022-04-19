package tipCalculator;

import javax.swing.*;

public class TipCalculator {
    public static void main(String[] args) {
        MyFrame frame = new MyFrame ("Tip Calculator");
        frame.setResizable(false);
        frame.pack();
        //frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
