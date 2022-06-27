package Smart_Robot_cleaner;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Menu extends Canvas implements Runnable, MouseListener {

    public Node[][] NodeList = new Node[10][10];
    public Node[][] Nodetarget = new Node[20][20];
    ArrayList<Node> openList = new ArrayList<Node>();
    ArrayList<Node> closeList = new ArrayList<Node>();
    ArrayList<Node> result = new ArrayList<Node>();

    public Node path[] = new Node[200];
    public boolean isfast;
    public boolean calced;
    public boolean calculate = false;
    Toolkit tk = Toolkit.getDefaultToolkit();
    public Image robot = tk.getImage(getClass().getResource("/Images/ro.png"));
    public Image map = tk.getImage(getClass().getResource("/Images/map.png"));
    public Image vc1 = tk.getImage(getClass().getResource("/Images/vc1.png"));
    public Image vc2 = tk.getImage(getClass().getResource("/Images/vc2.png"));
    public Image vc3 = tk.getImage(getClass().getResource("/Images/vc3.png"));
    boolean go = false;
    int[] vcx = new int[30];
    int[] vcy = new int[30];
    public int c = 0;
    public Robot ro = new Robot(2, 2);
    public static Menu running;

    public static void main(String[] args) {

        JFrame fr = new JFrame("Smart Robot Cleaner");
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.setSize(800, 550);
        fr.setResizable(false);
        fr.setLocationRelativeTo(null);
        fr.setLayout(null);
        Menu m = new Menu();
        m.setBounds(0, 25, 530, 550);
        fr.add(m);
        setUpMenu(fr);
        setupButton(fr);
        setupLabel(fr);
        setupInput(fr);
        fr.setVisible(true);
        running = m;
        m.start();
    }

    public static void setUpMenu(JFrame fr) {
        JMenuBar menubar = new JMenuBar();
        menubar.setBounds(0, 0, 800, 25);
        fr.add(menubar);
        JMenu file = new JMenu("File");
        menubar.add(file);
        JMenuItem exit = new JMenuItem("Exit");
        JMenuItem newmap = new JMenuItem("Reset");
        JMenuItem startcover = new JMenuItem("Start");

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        newmap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                running.resetNode();
            }
        });
        startcover.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                running.dfs(running.NodeList[1][1]);
                running.clean();
                running.calced = true;
            }
        });

        file.add(newmap);
        file.add(startcover);
        file.add(exit);

    }

    public static void setupInput(JFrame fr) {
        JTextField txtpin = new JTextField("Power robot");
        JButton btset = new JButton("Set");
        txtpin.setBounds(550, 150, 90, 35);
        btset.setBounds(660, 150, 90, 35);

        txtpin.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent EVT) {
                String value = txtpin.getText();
                int l = value.length();
                if (EVT.getKeyChar() >= '0' && EVT.getKeyChar() <= '9') {
                    txtpin.setEditable(true);
                } else {
                    txtpin.setEditable(false);
                    txtpin.requestFocus();

                }
            }
        });
        btset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                float txt = Float.parseFloat(txtpin.getText());
                running.ro.setPower(txt);
                JOptionPane.showMessageDialog(null, "Pin của Robot là " + txt + " %", "Infor", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // option quickly clean and set vet ban
        JButton btmark1 = new JButton("Add VB");
        JButton btmark2 = new JButton("Add VC");
        btmark1.setBounds(550, 200, 90, 35);
        btmark2.setBounds(660, 200, 90, 35);
        btmark1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
//                Vatcandidong vc = new Vatcandidong();
                running.c = 1;
            }
        });

        btmark2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                running.c = 2;
            }
        });

        JPanel algoPanel = new JPanel();
        algoPanel.setBorder(javax.swing.BorderFactory.
                createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(),
                        "Option", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                        javax.swing.border.TitledBorder.TOP, new java.awt.Font("Helvetica", 0, 16)));
        algoPanel.setLocation(540, 120);
        algoPanel.setSize(224, 130);
        fr.add(txtpin);
        fr.add(btset);
        fr.add(btmark1);
        fr.add(btmark2);
        fr.add(algoPanel);
    }

    public static void setupButton(JFrame fr) {
        JButton btFind = new JButton("Start");
        JButton btClear = new JButton("Quick");
        btFind.setBounds(550, 300, 90, 35);
        btFind.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                running.dfs(running.NodeList[1][1]);
                running.clean();
                running.calced = true;
            }
        });

        btClear.setBounds(660, 300, 90, 35);
        btClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                running.isfast = true;
                running.dfs(running.NodeList[1][1]);
                running.clean();
                running.calced = true;
            }
        });

        JPanel algoPanel = new JPanel();
        algoPanel.setBorder(javax.swing.BorderFactory.
                createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(),
                        "Menu", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                        javax.swing.border.TitledBorder.TOP, new java.awt.Font("Helvetica", 0, 16)));
        algoPanel.setLocation(540, 270);
        algoPanel.setSize(224, 80);

        fr.add(btFind);
        fr.add(btClear);
        fr.add(algoPanel);
    }

    public static void setupLabel(JFrame fr) {
        JLabel lb0 = new JLabel("Final Assignment AI", JLabel.CENTER);
        JLabel lb1 = new JLabel("Smart Cleaner Robot ", JLabel.CENTER);
        JLabel lb2 = new JLabel("Đinh Minh Lộc ", JLabel.CENTER);
        JLabel lb3 = new JLabel("Phạm Dương Thành Long ", JLabel.CENTER);
        lb0.setFont(new Font("Time New Roman", 3, 18));
        lb1.setFont(new Font("Time New Roman", 3, 18));
        lb2.setFont(new Font("Time New Roman", 3, 14));
        lb3.setFont(new Font("Time New Roman", 3, 14));
        lb0.setBounds(540, 400, 250, 30);
        lb0.setForeground(Color.green);
        lb1.setBounds(540, 420, 250, 30);
        lb1.setForeground(Color.red);
        lb2.setBounds(540, 440, 250, 30);
        lb2.setForeground(Color.black);
        lb3.setBounds(540, 460, 250, 30);
        lb3.setForeground(Color.black);

        JPanel algoPanel = new JPanel();
        algoPanel.setBorder(javax.swing.BorderFactory.
                createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(),
                        "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                        javax.swing.border.TitledBorder.TOP, new java.awt.Font("Helvetica", 0, 16)));
        algoPanel.setLocation(530, 26);
        algoPanel.setSize(250, 490);
        algoPanel.setOpaque(false);
        fr.add(algoPanel);
        fr.add(lb0);
        fr.add(lb1);
        fr.add(lb2);
        fr.add(lb3);

    }

    public void render(Graphics2D g) {
        g.setColor(Color.black);

        g.fillRect(0, 0, 502, 502);
        g.drawImage(map, 1, 0, this);

        for (int i = 0; i < Nodetarget.length; i++) {
            for (int j = 0; j < Nodetarget[i].length; j++) {
                Nodetarget[i][j].renderNode(g);
            }
        }
        if (go) {
            for (int i = 0; i < 30; i++) {
                if (vcx[i] != 0 | vcy[i] != 0) {
                    if (i % 3 == 0) {
                        g.drawImage(vc1, vcx[i] * 25, vcy[i] * 25, this);
                    } else if (i % 3 == 1) {
                        g.drawImage(vc2, vcx[i] * 25, vcy[i] * 25, this);
                    } else if (i % 3 == 2) {
                        g.drawImage(vc3, vcx[i] * 25, vcy[i] * 25, this);
                    }
                }
            }
        }
        g.drawImage(robot, ro.getX() * 25, ro.getY() * 25, this);

        g.setPaintMode();
    }

    public void run() {
        init();
        while (true) {
            BufferStrategy bs = getBufferStrategy();
            if (bs == null) {
                createBufferStrategy(2);
                continue;
            }

            Graphics2D g = (Graphics2D) bs.getDrawGraphics();
            render(g);
            bs.show();
            try {
                Thread.sleep(2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void init() {
        requestFocus();
        calced = true;
        NodeList = new Node[10][10];
        Nodetarget = new Node[20][20];
        resetNode();
        addMouseListener(this);
    }

    public void start() {
        new Thread(this).start();
    }

    public Menu() {
        resetNode();
    }

    public void dfs(Node root) {
        if (root == null) {
            return;
        }
        openList.add(root);
        //System.out.print(root.getVertex() + "\t");
        root.state = State.Visited;

        //for every child
        for (Node n : root.getChild()) {
            //if childs state is not visited then recurse
            if (n.state == State.Unvisited) {
                dfs(n);
            }
        }
    }

    public void covering() {
        //Node start = Nodetarget[2][2];
        ArrayList<Node> temp = new ArrayList<Node>();
        temp.add(openList.get(0));
        for (int i = 1; i < openList.size(); i++) {
            Node parent = openList.get(i - 1);
            Node sub = openList.get(i);
            if (parent.getPos(sub) != 0) {
                parent.addChildnewNode(sub);
                sub.addChildnewNode(parent);
                temp.add(sub);
            } else {
                int j = temp.size() - 1;
                System.out.println(temp.size());
                while (j >= 0) {
                    Node n = temp.get(j);
                    if (n.getPos(sub) != 0) {
                        sub.addChildnewNode(n);
                        n.addChildnewNode(sub);
                        temp.add(n);
                        break;
                    } else {
                        temp.remove(j);
                    }
                    j--;
                }
            }
        }

        for (Node n : openList) {
            for (Node k : n.getChildnew()) {
                //1 up, 2 right 3 down 4 left
                if (n.getPos(k) == 1) {
                    n.getSubL_U().child.remove(n.getSubR_U());
                    n.getSubR_U().child.remove(n.getSubL_U());
                    n.getSubL_U().right = null;
                    n.getSubR_U().left = null;
                    n.getSubL_U().up = k.getSubL_D();
                    n.getSubR_U().up = k.getSubR_D();
                    n.getSubL_U().addChildNode(k.getSubL_D());
                    n.getSubR_U().addChildNode(k.getSubR_D());
                } else if (n.getPos(k) == 2) {
                    n.getSubR_U().child.remove(n.getSubR_D());
                    n.getSubR_D().child.remove(n.getSubR_U());
                    n.getSubR_U().down = null;
                    n.getSubR_D().up = null;
                    n.getSubR_U().right = k.getSubL_U();
                    n.getSubR_D().right = k.getSubL_D();
                    n.getSubR_U().addChildNode(k.getSubL_U());
                    n.getSubR_D().addChildNode(k.getSubL_D());
                } else if (n.getPos(k) == 3) {
                    n.getSubL_D().child.remove(n.getSubR_D());
                    n.getSubR_D().child.remove(n.getSubL_D());
                    n.getSubL_D().right = null;
                    n.getSubR_D().left = null;
                    n.getSubL_D().down = k.getSubL_U();
                    n.getSubR_D().down = k.getSubR_U();
                    n.getSubL_D().addChildNode(k.getSubL_U());
                    n.getSubR_D().addChildNode(k.getSubR_U());
                } else if (n.getPos(k) == 4) {
                    n.getSubL_U().child.remove(n.getSubL_D());
                    n.getSubL_D().child.remove(n.getSubL_U());
                    n.getSubL_U().down = null;
                    n.getSubL_D().up = null;
                    n.getSubL_U().left = k.getSubR_U();
                    n.getSubL_D().left = k.getSubR_D();
                    n.getSubL_U().addChildNode(k.getSubR_U());
                    n.getSubL_D().addChildNode(k.getSubR_D());
                } else {
                    System.out.println("xuất hiện lỗi");
                }
            }
        }
        Node start = Nodetarget[2][2];
        Node temp_ = start;
        Node temp1 = null;
        if (openList.get(0).getLeft() != null) {
            temp1 = start.getLeft();
        } else if (openList.get(0).getDown() != null) {
            result.add(temp_);
            temp_ = temp_.getDown();
            temp1 = temp_.getDown();
        } else if (openList.get(0).getRight() != null) {
            result.add(temp_);
            temp_ = start.getDown();
            temp1 = temp_.getDown();
            result.add(temp_);
            temp_ = start.getRight();
            temp1 = temp_.getRight();

        } else if (openList.get(0).getUp() != null) {
            result.add(temp_);
            temp_ = start.getDown();
            temp1 = temp_.getDown();
            result.add(temp_);
            temp_ = start.getRight();
            temp1 = temp_.getUp();
            result.add(temp_);
            temp_ = start.getUp();
            temp1 = temp_.getUp();

        }

        while (temp1 != start) {
            result.add(temp_);
            for (Node n : temp1.getChild()) {
                if (n != temp_) {
                    temp_ = temp1;
                    temp1 = n;
                    break;
                } else {
                    continue;
                }

            }

        }
        result.add(temp_);
        result.remove(start);
        result.add(start);
        System.out.println(result.size());

    }

    public void clean() {
       int time = 400;
        covering();
        int temp;
        for (int i = 0; i < Nodetarget.length; i++) {
            for (int j = 0; j < Nodetarget[i].length; j++) {

                int l = j - 1;
                int r = j + 1;
                int u = i - 1;
                int d = i + 1;
                Node left = null, right = null, up = null, down = null;

                if (l >= 0 && l < Nodetarget[i].length) {
                    Nodetarget[i][j].addChildNode(Nodetarget[i][l]);
                    left = Nodetarget[i][l];
                }
                if (d >= 0 && d < Nodetarget.length) {
                    Nodetarget[i][j].addChildNode(Nodetarget[d][j]);
                    down = Nodetarget[d][j];
                }
                if (r >= 0 && r < Nodetarget[i].length) {
                    Nodetarget[i][j].addChildNode(Nodetarget[i][r]);
                    right = Nodetarget[i][r];
                }
                if (u >= 0 && u < Nodetarget.length) {
                    Nodetarget[i][j].addChildNode(Nodetarget[u][j]);
                    up = Nodetarget[u][j];
                }
                Nodetarget[i][j].setDirections(left, right, up, down);
            }
        }
        if (ro.getPower() >= 10) {

            for (Node n : result) {
                while (n.isVcdd()) {
                    try {
                        Thread.sleep(200);
                    } catch (Exception e) {
                    }
                }
                if (n.getChild() != null) {
                    for (Node k : n.getChild()) {
                        while (k.isVcdd()) {
                            try {
                                Thread.sleep(200);
                            } catch (Exception e) {
                            }
                            while (n.isVcdd()) {
                                try {
                                    Thread.sleep(200);
                                } catch (Exception e) {
                                }
                            }
                        }

                    }

                    ro.move(n);
                    n.used_c();
                    try {
                        Node node1 = result.get(result.indexOf(n) - 1);
                        Nodetarget[node1.fx][node1.fy].setStart(false);

                    } catch (Exception e) {
                        System.out.println("Xuất hiện lỗi");
                    }
                    n.setStart(true);

                }
                // xu ly luc o ban hoac binh thuong hoac lau nhanh
                if (isfast) {
                    time = 100;
                } else if (n.impuse) {
                    time = 750;
                } else {
                    time = 200;
                }
                if (ro.getPower() < 10) { // khi pin nho hon 10% thong bao va tim ve cho sac
                    System.out.println("Yeah! het pin :))");
                    JOptionPane.showMessageDialog(null, "10% Pin yeu, Robot sẽ quay về vị trí sạc", "Error", JOptionPane.ERROR_MESSAGE);
                    FindPath fp = new FindPath();
                    //fp.resetNode();

                    fp.NodeList = Nodetarget;
                    fp.start = n;
                    result.get(result.indexOf(n)).setStart(true);
                    fp.target = Nodetarget[2][2];
                    Nodetarget[2][2].setTarget(true);
                    fp.findPath();
                    for (int i = fp.count2 + 1; i >= 0; i--) {
                        while (fp.path[i].isVcdd()) {
                            try {
                                Thread.sleep(200);
                            } catch (Exception e) {
                            }
                        }
                        if (fp.path[i].getChild() != null) {
                            for (Node k : fp.path[i].getChild()) {
                                while (k.isVcdd()) {
                                    try {
                                        Thread.sleep(200);
                                    } catch (Exception e) {
                                    }
                                    while (fp.path[i].isVcdd()) {
                                        try {
                                            Thread.sleep(200);
                                        } catch (Exception e) {
                                        }
                                    }
                                }

                            }
                        }
                        try{
                        fp.path[i-1].setStart(false);
                        }catch(Exception ex){}
                        ro.move(fp.path[i]);
                        fp.path[i].used_c();
                        fp.path[i].setStart(true);
                        repaint();
                        try {
                            Thread.sleep(200);
                        } catch (Exception ex) {

                        }
                    }

                    break;
                }
                repaint();
                try {
                    Thread.sleep(time);
                } catch (InterruptedException ex) {
                }
            }

        } else {
            JOptionPane.showMessageDialog(null, "Pin dưới 10%, Robot đang sạc", "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void resetNode() {
        isfast = false;
        ro.setX(2);
        ro.setY(2);
        calculate = false;
        openList.removeAll(openList);
        closeList.removeAll(openList);
        result.removeAll(result);
        vcx = new int[30];
        vcy = new int[30];

        //cai dat cho not con truoc
        for (int i = 0; i < Nodetarget.length; i++) {
            for (int j = 0; j < Nodetarget[i].length; j++) {
                Nodetarget[i][j] = new Node(i, j).setX(i * 25).setY(j * 25);
            }
        }
        for (int i = 0; i < NodeList.length; i++) {
            for (int j = 0; j < NodeList[i].length; j++) {
                NodeList[i][j] = new Node(i, j).setX(i * 50).setY(j * 50);
                Node subleft_up = null, subright_up = null, subleft_down = null, subright_down = null;
                subleft_up = Nodetarget[2 * i][2 * j];
                subright_up = Nodetarget[2 * i][2 * j + 1];
                subleft_down = Nodetarget[2 * i + 1][2 * j];
                subright_down = Nodetarget[2 * i + 1][2 * j + 1];
                NodeList[i][j].setSub(subleft_up, subright_up, subleft_down, subright_down);

                // can kiem tra xem chinh xac chua
                Nodetarget[2 * i][2 * j].setDirections(null, subright_up, null, subleft_down);
                Nodetarget[2 * i][2 * j].addChildNode(subright_up);
                Nodetarget[2 * i][2 * j].addChildNode(subleft_down);
                Nodetarget[2 * i][2 * j + 1].setDirections(subleft_up, null, null, subright_down);
                Nodetarget[2 * i][2 * j + 1].addChildNode(subleft_up);
                Nodetarget[2 * i][2 * j + 1].addChildNode(subright_down);
                Nodetarget[2 * i + 1][2 * j].setDirections(null, subright_down, subleft_up, null);
                Nodetarget[2 * i + 1][2 * j].addChildNode(subright_down);
                Nodetarget[2 * i + 1][2 * j].addChildNode(subleft_up);
                Nodetarget[2 * i + 1][2 * j + 1].setDirections(subleft_down, null, subright_up, null);
                Nodetarget[2 * i + 1][2 * j + 1].addChildNode(subleft_down);
                Nodetarget[2 * i + 1][2 * j + 1].addChildNode(subright_up);

            }
        }

        NodeList[1][0].iswall = true;
        NodeList[3][1].iswall = true;
        NodeList[4][4].iswall = true;
        NodeList[4][5].iswall = true;
        NodeList[4][6].iswall = true;
        NodeList[6][9].iswall = true;
        NodeList[9][6].iswall = true;

        NodeList[0][0].iswall = true;
        NodeList[0][1].iswall = true;
        NodeList[0][1].iswall = true;
        NodeList[0][6].iswall = true;
        NodeList[1][6].iswall = true;
        NodeList[2][6].iswall = true;
        NodeList[3][6].iswall = true;
        NodeList[0][4].iswall = true;
        NodeList[1][4].iswall = true;
        NodeList[2][4].iswall = true;
        NodeList[3][9].iswall = true;
        NodeList[6][8].iswall = true;

        NodeList[6][0].iswall = true;
        NodeList[6][1].iswall = true;
        NodeList[9][4].iswall = true;
        NodeList[8][4].iswall = true;

        for (int i = 0; i < NodeList.length; i++) {
            for (int j = 0; j < NodeList[i].length; j++) {
                if (NodeList[i][j].iswall != true) {
                    int l = j - 1;
                    int r = j + 1;
                    int u = i - 1;
                    int d = i + 1;
                    Node left = null, right = null, up = null, down = null;

                    if (l >= 0 && l < NodeList[i].length && NodeList[i][l].iswall != true) {
                        NodeList[i][j].addChildNode(NodeList[i][l]);
                        left = NodeList[i][l];
                    }
                    if (d >= 0 && d < NodeList.length && NodeList[d][j].iswall != true) {
                        NodeList[i][j].addChildNode(NodeList[d][j]);
                        down = NodeList[d][j];
                    }
                    if (r >= 0 && r < NodeList[i].length && NodeList[i][r].iswall != true) {
                        NodeList[i][j].addChildNode(NodeList[i][r]);
                        right = NodeList[i][r];
                    }
                    if (u >= 0 && u < NodeList.length && NodeList[u][j].iswall != true) {
                        NodeList[i][j].addChildNode(NodeList[u][j]);
                        up = NodeList[u][j];
                    }
                    NodeList[i][j].setDirections(left, right, up, down);
                }

            }

        }
        //cai dat not con     
        //Neu nut cha la tuong thi 4 nut con cua no cung la tuong
        for (int i = 0; i < NodeList.length; i++) {
            for (int j = 0; j < NodeList[i].length; j++) {
                if (NodeList[i][j].iswall == true) {

                    Nodetarget[2 * i][2 * j].iswall = true;
                    Nodetarget[2 * i + 1][2 * j].iswall = true;
                    Nodetarget[2 * i + 1][2 * j + 1].iswall = true;
                    Nodetarget[2 * i][2 * j + 1].iswall = true;

                }
            }
        }
//        Nodetarget[13][0].iswall = false;
//        Nodetarget[19][12].iswall = false;
//        Nodetarget[13][18].iswall = false;
//        Nodetarget[12][18].iswall = false;
//        Nodetarget[5][12].iswall = false;
//        Nodetarget[5][13].iswall = false;
//        Nodetarget[4][12].iswall = false;
        repaint();
    }

    public Node getNodeAt(int x, int y) {
        x /= 25;
        y /= 25;
        if (x >= 0 && y >= 0 && x < Nodetarget.length && y < Nodetarget.length) {
            return Nodetarget[x][y];
        }
        return null;
    }
int num = 0;
    int[] dx = new int[4];
    int[] dy = new int[4];

    synchronized public void randomPos(int x, int y, int j) {
        int l = y - 1;
        int r = y + 1;
        int u = x - 1;
        int d = x + 1;
        Nodetarget[x][y].setVcdd(false);

        int count = 0;
        if (l >= 0 && l < 20 && !Nodetarget[x][l].iswall && !Nodetarget[x][l].isStart()) {
            dx[count] = x;
            dy[count] = l;
            count++;
        }
        if (r >= 0 && r < 20 && !Nodetarget[x][r].iswall && !Nodetarget[x][r].isStart()) {
            dx[count] = x;
            dy[count] = r;
            count++;
        }
        if (u >= 0 && u < 20 && !Nodetarget[u][y].iswall && !Nodetarget[u][y].isStart()) {
            dx[count] = u;
            dy[count] = y;
            count++;
        }
        if (d >= 0 && d < 20 && !Nodetarget[d][y].iswall && !Nodetarget[d][y].isStart()) {
            dx[count] = d;
            dy[count] = y;
            count++;
        }
        if (count == 0) {
            vcx[j] = -vcx[j];
            vcy[j] = -vcy[j];
        } else {
            count = (int) (Math.random() * count);
            if (count > 3) {
                count = 3;
            }
            vcx[j] = dx[count];
            vcy[j] = dy[count];
        }
        Nodetarget[vcx[j]][vcy[j]].setVcdd(true);
    }
    @Override
    public void mousePressed(MouseEvent e) {
       if (e.getX() >= 10 && e.getX() <= 750 && e.getY() >= 10 && e.getY() <= 550) {
            Node n = getNodeAt(e.getX(), e.getY());
            if (n != null && !n.iswall) {

                if (c == 1) {
                    n.draw();

                } else if (c == 2) {
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            vcx[num] = n.fx;
                            vcy[num] = n.fy;
                            int j = num;
                            num++;
                            go = true;
                            while (go == true) {
                                randomPos(vcx[j], vcy[j], j);
                                try {
                                    Thread.sleep(600);
                                } catch (Exception e) {
                                }
                            }
                        }
                    }).start();
                }
            }
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
