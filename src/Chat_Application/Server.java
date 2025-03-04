package Chat_Application;

import com.sun.source.tree.WhileLoopTree;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.BoxLayout;

public class Server implements ActionListener {
    JTextField text;
    JPanel a1;
   static Box vertical = Box.createVerticalBox();
    static JFrame f = new JFrame();
    static DataOutputStream dout;
    static DataInputStream din;

    Server() {
        f.setLayout(null);
        JPanel p1 = new JPanel();
        p1.setBounds(0, 0, 450, 60);
        p1.setBackground(new Color(7, 94, 84));
        p1.setLayout(null);
        f.add(p1);
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5, 20, 25, 25);
        p1.add(back);
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/2.png"));
        Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(35, 10, 50, 50);
        p1.add(profile);
        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        video.setBounds(300, 20, 25, 25);
        p1.add(video);
        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone = new JLabel(i12);
        phone.setBounds(370, 20, 25, 25);
        p1.add(phone);
        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel moreever = new JLabel(i15);
        moreever.setBounds(420, 20, 10, 25);
        p1.add(moreever);
        JLabel name = new JLabel("Pitamber Pandey");
        name.setBounds(110, 15, 100, 18);
        name.setForeground(Color.white);
        name.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        p1.add(name);
        JLabel status = new JLabel("Active Now");
        status.setBounds(110, 35, 100, 18);
        status.setForeground(Color.white);
        status.setFont(new Font("SAN_SERIF", Font.BOLD, 14));
        p1.add(status);
        a1 = new JPanel();
        a1.setBounds(5, 65, 440, 570);
        f.add(a1);
        f.setUndecorated(true);
        text = new JTextField();
        text.setBounds(5, 655, 310, 40);
        text.setFont(new Font("SAN_SERIF", Font.BOLD, 16));
        f.add(text);
        JButton send = new JButton("Send");
        send.setBounds(320, 655, 123, 40);
        send.setBackground(new Color(7, 94, 84));
        send.addActionListener(this);
        send.setFont(new Font("SAN_SERIF", Font.BOLD, 16));
        send.setForeground(Color.BLACK);

        f.add(send);

        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
        f.setSize(450, 700);
        f.setFocusable(true);
        f.getContentPane().setBackground(Color.white);
        f.setLocation(200, 50);
        f.setVisible(true);
    }

    public static void main(String[] args) {
        new Server();
        try {
            ServerSocket skt = new ServerSocket(6001);

            while (true) {
                Socket s = skt.accept();
                DataInputStream din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());
                while (true) {
                    String msg = din.readUTF();
                    JPanel panel = formatLabel(msg);
                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel, BorderLayout.LINE_START);
                    vertical.add(left);
                    f.validate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String out = text.getText();

        JPanel p2 = formatLabel(out);
        a1.setLayout(new BorderLayout());
        JPanel right = new JPanel(new BorderLayout());
        right.add(p2, BorderLayout.LINE_END);
        vertical.add(right);
        vertical.add(Box.createHorizontalStrut(15));
        a1.add(vertical, BorderLayout.PAGE_START);
        text.setText("");
        try {
            dout.writeUTF(String.valueOf(out));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        f.repaint();
        f.validate();
        f.invalidate();

    }

    public static JPanel formatLabel(String out) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel output = new JLabel("<html><p style=\"width:150px\">" + out + "</p></html>");
        output.setFont(new Font("Tahoma", Font.PLAIN, 16));
        output.setBackground(new Color(37, 211, 102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15, 15, 15, 50));
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);
        panel.add(output);

        return panel;
    }
}
