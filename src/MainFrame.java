import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class MainFrame extends JFrame {

    public MainFrame()  {

        setTitle("Let's Play");
        add(new MainMenu());

    }

    public MainFrame(int i) {

        add (new GameScreen());

    }

    public static void main(String[] args) {

        JFrame yes = new MainFrame();
        yes.setSize(750,750);
        yes.setLocationRelativeTo(null);
        yes.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        yes.setVisible(true);
    }
}

class GameScreen extends JPanel {
    int sec;
    int msec;
    int x, y;
    String ssec;
    boolean end = false;
    Sounds sound = new Sounds();
    ArrayList<Integer> xList = new ArrayList<>();
    ArrayList<Integer> yList = new ArrayList<>();
    ArrayList<Integer> hList = new ArrayList<>();
    ArrayList<Integer> wList = new ArrayList<>();
    ArrayList<Integer> xaList = new ArrayList<>();
    ArrayList<Integer> yaList = new ArrayList<>();
    ArrayList<Color> cList = new ArrayList<>();


    Timer timer = new Timer(50, new TimerListener());

    public GameScreen(){
        mouse gs = new mouse();
        this.addMouseListener(gs);
        this.addMouseMotionListener(gs);
        sound.playwiiTheme();

    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Font fontLarge = new Font("Chiller", Font.BOLD, getHeight() / 7);
        Font fontSmall = new Font("Chiller", Font.BOLD, getHeight() / 10);
        FontMetrics fl = g.getFontMetrics(fontLarge);
        FontMetrics fs = g.getFontMetrics(fontSmall);
        ssec = String.valueOf(sec);

        if (!end) {
            drawObstacles(g);
        }

        else {
            g.setColor(Color.WHITE);
            g.fillRect(0,0,getWidth(),getHeight());
            sound.stopwiiTheme();
            g.setFont(fontLarge);
            g.setColor(new Color(205, 45, 100));
            g.drawString("Game Over", (getWidth() / 2) - (fl.stringWidth("Game Over") / 2),getHeight() / 2 - fl.getAscent() / 2);
            g.setFont(fontSmall);
            g.drawString("You survived " + ssec + " seconds", (getWidth() / 2) - (fs.stringWidth("You survived " + ssec + " seconds") / 2),getHeight() / 2 + fs.getAscent() / 2);
        }
    }


    public void drawObstacles(Graphics g) {

        for (int i = 0; i < xList.size(); i++) {
            g.setColor(cList.get(i));
            g.fillRect(xList.get(i), yList.get(i), wList.get(i), hList.get(i));

        }
    }

    class mouse extends MouseAdapter {

        @Override
        public void mouseDragged(MouseEvent e) {
            x = e.getX();
            y = e.getY();
            for (int i = 0; i < xList.size(); i++) {
                if (x < xList.get(i) + wList.get(i) && x > xList.get(i) && y < yList.get(i) + hList.get(i) && y  > yList.get(i)) {
                    end = true;
                }
            }

            repaint();

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            x = e.getX();
            y = e.getY();
            for (int i = 0; i < xList.size(); i++) {
                if (x < xList.get(i) + wList.get(i) && x > xList.get(i) && y < yList.get(i) + hList.get(i) && y > yList.get(i)) {
                    end = true;
                }
            }
            repaint();
        }
        @Override
        public void mouseEntered(MouseEvent e){
            timer.start();
            repaint();
        }
        @Override
        public void mouseExited(MouseEvent e){
            end = true;
            repaint();
        }

    }


    public class TimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!end) {

                Random boi = new Random();
                int obX = boi.nextInt(750);
                int obY = boi.nextInt(750);
                int obH = boi.nextInt(30) + 20;
                int obW = boi.nextInt(30) + 20;
                int obXa = boi.nextInt(10)-5;
                int obYa = boi.nextInt(10)-5;

                if (x < obX + obW + 20 && x > obX-20 && y < obY + obH + 20 && y > obY-20) {
                    obX = boi.nextInt(750);
                    obY = boi.nextInt(750);
                }

                Color colour = new Color(boi.nextInt(255), boi.nextInt(255), boi.nextInt(255));

                xList.add(obX);
                yList.add(obY);
                hList.add(obH);
                wList.add(obW);
                xaList.add(obXa);
                yaList.add(obYa);
                cList.add(colour);

                for (int d = 0; d < xList.size(); d++) {
                    yList.set(d, yList.get(d) + yaList.get(d));
                    xList.set(d, xList.get(d) + xaList.get(d));

                }
                msec+=1;
                if (msec%20==0){
                    sec +=1;
                }
                for (int i = 0; i < xList.size(); i++) {
                    if (x < xList.get(i) + wList.get(i) && x > xList.get(i) && y < yList.get(i) + hList.get(i) && y > yList.get(i)) {
                        end = true;
                    }
                }

            }

            repaint();

        }
    }
}

class MainMenu extends JPanel {

    public MainMenu() {

        setLayout(new BorderLayout());

        Font font = new Font("Chiller",Font.BOLD,74);
        Font font2 = new Font("Castellar",Font.BOLD,100);

        Color colour = new Color(188, 240, 225);

        JButton begin = new JButton("Start!");
        begin.setFont(font2);
        begin.setForeground(Color.BLACK);
        begin.setBackground(colour);
        begin.setToolTipText("Why haven't you clicked me yet?");


        JLabel msg = new JLabel("Click Button to Start Game!");
        msg.setFont(font);
        msg.setForeground(new Color(205, 45, 100));

        begin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                JFrame yess = new MainFrame(1);
                yess.setTitle("Let's Play!");
                yess.setSize(750,750);
                yess.setLocationRelativeTo(null);
                yess.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                yess.setVisible(true);
            }

        });

        add(msg, BorderLayout.NORTH);
        add(begin, BorderLayout.CENTER);

    }

}


class Sounds {
    private String[] files = {"wiiMusic.wav"};
    private Clip[] clips = new Clip[files.length];

    public Sounds() {
        try {
            for (int i=0; i<files.length; i++) {
                File file = new File(files[i]);

                AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                AudioFormat format = audioStream.getFormat();
                DataLine.Info info = new DataLine.Info(Clip.class, format, (int) (audioStream.getFrameLength() * format.getFrameSize()));
                clips[i] = (Clip) AudioSystem.getLine(info);
                // deathClip.addLineListener(this);
                clips[i].open(audioStream);
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Unable to initialize sounds.", e);
        }
    }

    void playwiiTheme() {
        clips[0].loop(20000);
        clips[0].setFramePosition(0);
        clips[0].start();
    }
    void stopwiiTheme() {
        clips[0].stop();
    }
/*
    void playJumpClip() {
        clips[1].setFramePosition(0);
        clips[1].start();
    }

    void playTheme() {
        clips[2].loop(20000);
        clips[2].setFramePosition(0);
        clips[2].start();
    }*/

}
