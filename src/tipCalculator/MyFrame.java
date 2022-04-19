package tipCalculator;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class MyFrame extends JFrame implements ActionListener {
    private JLabel amount;
    private JTextField amountText;

    private JLabel tipPercent;
    private JSlider tipSlider;

    private JLabel tip;
    private JTextField tipValue;

    private JLabel total;
    private JTextField totalField;

    private JButton calculate;
    private JLabel gainCalculator;
    private JTextField gainValue;

    private List<BigDecimal> gain = new ArrayList<BigDecimal>();

    private final GridBagLayout layout;
    private final GridBagConstraints c;
    private final NumberFormat percentageFormatter = NumberFormat.getPercentInstance();
    private final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
    private BigDecimal tipPercentage = new BigDecimal("0.10");

    public MyFrame(String title) {
        super(title);

        layout = new GridBagLayout();
        c = new GridBagConstraints();
        setLayout(layout);
        ((JComponent)getContentPane()).setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));
        c.insets = new Insets(5, 5, 5, 5);

        UIManager.LookAndFeelInfo[] looks = UIManager.getInstalledLookAndFeels();
        for (UIManager.LookAndFeelInfo look : looks) {
            if ("Nimbus".equals(look.getName())){
                try {
                    UIManager.setLookAndFeel(look.getClassName());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                SwingUtilities.updateComponentTreeUI(this);
            }
            System.out.println(look.getName());
        }

        amount = new JLabel("Amount", SwingConstants.RIGHT);
        amountText = new JTextField(12);
       // c.anchor = GridBagConstraints.LINE_END;
        //c.fill = GridBagConstraints.NONE;
        addComponent(amount, 0, 0);
        addComponent(amountText, 0, 1);

        tipPercent = new JLabel("10%", SwingConstants.RIGHT);
        tipSlider = new JSlider(0,100, 15);
        tipSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                tipPercentage = BigDecimal.valueOf(tipSlider.getValue() / 100.0);
                String percentageFormatted = percentageFormatter.format(tipPercentage);
               // tipPercent.setText(tipSlider.getValue() + "%");
                tipPercent.setText(percentageFormatted);
            }
        });
        addComponent(tipPercent, 1, 0);
        addComponent(tipSlider, 1, 1);

        tip = new JLabel("Tip", SwingConstants.RIGHT);
        tipValue = new JTextField(12);
        tipValue.setEditable(false);
        tipValue.setFocusable(false);
        addComponent(tip, 2, 0);
        addComponent(tipValue, 2, 1);

        total = new JLabel("Total", SwingConstants.RIGHT);
        totalField = new JTextField(12);
        totalField.setEditable(false);
        totalField.setFocusable(false);
        addComponent(total, 3, 0);
        addComponent(totalField, 3, 1);

        //empty = new JLabel();
        calculate = new JButton("Calculate");
        //add(empty);
        addComponent(calculate, 4, 1);
        calculate.addActionListener(this);

        gainCalculator = new JLabel("Gain");
        addComponent(gainCalculator, 5, 0);

        gainValue = new JTextField(12);
        gainValue.setEditable(false);
        gainValue.setFocusable(false);
        addComponent(gainValue, 5, 1);
    }
    private void addComponent (Component comp, int line, int column){
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = line;
        c.gridx = column;
        layout.setConstraints(comp, c);
        add(comp);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            BigDecimal amount = new BigDecimal(amountText.getText());
            if (amount.compareTo(BigDecimal.valueOf(0.0)) > 0) {
                BigDecimal tip = amount.multiply(tipPercentage);
                gain.add(tip);
                BigDecimal sum = BigDecimal.valueOf(0.0);
                for (BigDecimal value: gain) {
                    sum = sum.add(value);
                }
                gainValue.setText(NumberFormat.getNumberInstance().format(sum));
                System.out.println("the sum is " +sum);
                BigDecimal total = amount.add(tip);
                currencyFormatter.setRoundingMode(RoundingMode.HALF_UP);
                String tipFormatted = currencyFormatter.format(tip);
                String totalFormatted = currencyFormatter.format(total);
                tipValue.setText(tipFormatted);
                totalField.setText(totalFormatted);
                amountText.requestFocus();
                amountText.selectAll();
            } else if (amount.compareTo(BigDecimal.valueOf(0.0)) < 0){
                amountText.setText("Enter a positive amount");
                amountText.requestFocus();
                amountText.selectAll();
            }
        } catch (NumberFormatException ex){
            amountText.setText("Enter a valid amount");
            amountText.requestFocus();
            amountText.selectAll();
        }

    }
}
