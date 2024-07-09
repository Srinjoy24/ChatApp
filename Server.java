import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

class Server implements ActionListener {

    JTextField textField;
    JPanel text;
    static Box vertical=Box.createVerticalBox();
    static JFrame jFrame=new JFrame();
    static DataOutputStream dout;

    Server() {
        // Frame
        jFrame.setSize(450, 700);
        jFrame.setLocation(200, 100);
        jFrame.setLayout(null);
        jFrame.setBackground(Color.white);

        // Green panel on top
        JPanel jPanel = new JPanel();
        jPanel.setBackground(new Color(7, 94, 84));
        jPanel.setBounds(0, 0, 450, 70);
        jPanel.setLayout(null);
        jFrame.add(jPanel);

        // Arrow image
        ImageIcon imageIcon = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image img = imageIcon.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon imageIcon1 = new ImageIcon(img);
        JLabel jLabel = new JLabel(imageIcon1);
        jLabel.setBounds(5, 20, 25, 25);
        jPanel.add(jLabel);
        // Function to close the project on clicking back
        jLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        // Profile Picture
        ImageIcon profileImg = new ImageIcon(ClassLoader.getSystemResource("icons/1.png"));
        Image img1 = profileImg.getImage().getScaledInstance(45, 45, Image.SCALE_DEFAULT);
        ImageIcon profileFinalImage = new ImageIcon(img1);
        JLabel profile = new JLabel(profileFinalImage);
        profile.setBounds(40, 15, 45, 45);
        jPanel.add(profile);

        // Video Call Image
        ImageIcon videoCallImg = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image img2 = videoCallImg.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon videoCallFinal = new ImageIcon(img2);
        JLabel video = new JLabel(videoCallFinal);
        video.setBounds(300, 20, 30, 30);
        jPanel.add(video);

        // Phone Image
        ImageIcon phoneImage = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image img3 = phoneImage.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon phoneFinalImage = new ImageIcon(img3);
        JLabel phone = new JLabel(phoneFinalImage);
        phone.setBounds(350, 20, 30, 30);
        jPanel.add(phone);

        // More Icon Image
        ImageIcon moreImage = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image img4 = moreImage.getImage().getScaledInstance(10, 30, Image.SCALE_DEFAULT);
        ImageIcon moreFinalImage = new ImageIcon(img4);
        JLabel more = new JLabel(moreFinalImage);
        more.setBounds(400, 20, 10, 30);
        jPanel.add(more);

        // Account Name
        JLabel name = new JLabel("Ram");
        name.setBounds(110, 10, 100, 40);
        name.setForeground(Color.white);
        name.setFont(new Font("Times_New_Roman", Font.BOLD, 18));
        jPanel.add(name);

        // Status
        JLabel status = new JLabel("Active now");
        status.setBounds(110, 35, 100, 40);
        status.setForeground(Color.white);
        status.setFont(new Font("Times_New_Roman", Font.BOLD, 14));
        jPanel.add(status);

        // Text Area
        text = new JPanel(); 
        text.setBounds(5, 80, 440, 570);
        text.setLayout(new BorderLayout());
        jFrame.add(text);

        // Writing Text
        textField = new JTextField(); 
        textField.setBounds(5, 650, 300, 40);
        textField.setFont(new Font("Times_New_Roman", Font.PLAIN, 17));
        jFrame.add(textField);

        // Sending Text Button
        JButton send = new JButton("Send");
        send.setBounds(310, 650, 130, 40);
        send.setBackground(new Color(7, 94, 84));
        send.setForeground(Color.WHITE);
        send.setFont(new Font("Times_New_Roman", Font.BOLD, 16));
        send.addActionListener(this); 
        jFrame.add(send);

        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setUndecorated(true);
        jFrame.setVisible(true);
    }

    // Function For Sending Message
    public void actionPerformed(ActionEvent e) {
        try {
            String msg = textField.getText();
            JPanel panel=formatText(msg);
            text.setLayout(new BorderLayout());
            JPanel rightAlign = new JPanel(new BorderLayout());
            rightAlign.add(panel, BorderLayout.LINE_END);
            vertical.add(rightAlign);
            vertical.add(Box.createVerticalStrut(15));
            text.add(vertical, BorderLayout.PAGE_START);
            textField.setText("");//to set the text field empty after sending text
            dout.writeUTF(msg);
            jFrame.repaint();
            jFrame.invalidate();
            jFrame.validate(); 
        } 
        catch (Exception ae) {
           ae.printStackTrace();
        }
        
    }

    //Function for formatting text
    public static JPanel formatText(String msg) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel label = new JLabel("<html><p style=\"width: 100px\">"+ msg +"</p></html>");
        label.setFont(new Font("Times_New_Roman", Font.PLAIN, 16));
        label.setBackground(new Color(37, 211, 102));
        label.setOpaque(true);
        label.setBorder(new EmptyBorder(15, 15, 15, 50));
        panel.add(label);
        //For Time
        Calendar calender=Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
        JLabel time=new JLabel();
        time.setText(sdf.format(calender.getTime()));
        time.setFont(new Font("Times_New_Roman",Font.BOLD,11));
        panel.add(time);
        return panel;
    }

    public static void main(String[] args) {
        Server s=new Server();
        try {
            //To make this the server
            ServerSocket skt=new ServerSocket(6000);
            //using a infinite loop to continue accepting messages
            while (true) { 
                //accepting the messages and storing it in socket soc
                Socket soc=skt.accept();
                //datainputStream class is used to receive messages
                DataInputStream din=new DataInputStream(soc.getInputStream());
                //dataOutputStream class is used for sending messages
                dout=new DataOutputStream(soc.getOutputStream());
                while(true){
                    String message=din.readUTF();//readUTF is a protocol used to read the received message
                    JPanel panel1=formatText(message);
                    JPanel leftPanel=new JPanel(new BorderLayout());
                    leftPanel.add(panel1,BorderLayout.LINE_START);
                    vertical.add(leftPanel);
                    jFrame.validate();
                }
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}