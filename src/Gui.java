import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

public class Gui extends JFrame implements ActionListener {
    JLabel labelx = new JLabel("What is the number of wifi connection");
    JLabel labely = new JLabel("What is the number of devices?!");
    JLabel labelz = new JLabel("Enter the Name of the devices followed by the device type ");


    JButton button = new JButton("run");
    JTextField text1 = new JTextField();
    JTextField text2 = new JTextField();
    JTextArea text3 = new JTextArea();
    JTextArea text4 = new JTextArea();


    int N;
    int TC;

    Gui() {
        this.setVisible(true);
        this.setSize(400, 850);
        this.getContentPane().setBackground(new Color(255, 86, 0));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);

        this.add(labelx);
        labelx.setBounds(100, 0, 400, 20);

        this.setTitle("Java Synchronization");
        this.add(text1);
        text1.setBounds(100, 20, 200, 30);
        this.add(labely);
        labely.setBounds(100, 60, 400, 20);
        this.add(text2);
        text2.setBounds(100, 85, 200, 30);
        this.add(labelz);
        labelz.setBounds(50, 80, 500, 100);
        JScrollPane scroll = new JScrollPane(text3);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(text3);
        text3.setBounds(100, 150, 200, 120);
        this.add(button);
        button.setBounds(150, 280, 70, 40);
        button.setFocusPainted(false);
        button.addActionListener(this);
        this.add(text4);
        text4.setBounds(70, 350, 250, 400);
        text4.setEditable(false);
        redirectSystemStreams();


        //labelx.setVerticalAlignment();
        //	this.add(button) ;


    }


    @Override
    public void actionPerformed(ActionEvent e) {
        text4.setText("");
        String str;
        str = text1.getText();
        N = Integer.valueOf(str);

        String str2;
        str2 = text2.getText();


        try {


            TC = Integer.valueOf(str2);
            String[] temp = text3.getText().split("\n");

            Device[] TClines = new Device[TC];
            for (int i = 0; i < TC; i++) {
                String[] device = temp[i].split(" ");
                TClines[i] = new Device(device[0], device[1]);
            }
            new Router(N, TC, TClines).connect();

        } catch (Exception r) {
            System.out.println("Error");
        }

    }


    private void updateTextArea(final String text) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                text4.append(text);
            }
        });
    }

    private void redirectSystemStreams() {
        OutputStream out = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                updateTextArea(String.valueOf((char) b));
            }

            @Override
            public void write(byte[] b, int off, int len) throws IOException {
                updateTextArea(new String(b, off, len));
            }

            @Override
            public void write(byte[] b) throws IOException {
                write(b, 0, b.length);
            }
        };

        System.setOut(new PrintStream(out, true));
        System.setErr(new PrintStream(out, true));
    }

}
