package Smart_Robot_cleaner;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

public class Node {

    private boolean start;
    private boolean target;
    private boolean vcdd;

    public boolean isVcdd() {
        return vcdd;
    }

    public void setVcdd(boolean vcdd) {
        this.vcdd = vcdd;
    }
    private int xPos;
    private int yPos;
    public int fx, fy;
    private Node parent;
    private boolean onPath;
     public double Hcost, Gcost, Fcost;
    public Node left, right, up, down;
    private Node subleftup, subrightup, subleftdown, subrightdown;
    public ArrayList<Node> child = new ArrayList<Node>();
    public State state;
    boolean iswall;
    boolean impuse;
    public ArrayList<Node> childnew = new ArrayList<Node>();
    private boolean used;
    private boolean used_c;
    Toolkit tk = Toolkit.getDefaultToolkit();
    public Image o = tk.getImage(getClass().getResource("/Images/im.jpg")); // anh cleaned                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
    public Image x = tk.getImage(getClass().getResource("/Images/x.png")); // anh vet ban

    public Node(int x, int y) {
        start = false;
        target = false;
        vcdd = false;
        fx = x;
        fy = y;

    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public void setTarget(boolean target) {
        this.target = target;
    }

    public Node setX(int x) {
        xPos = x;
        return this;
    }

    public Node setY(int y) {
        yPos = y;
        return this;
    }
 public void setParent(Node parent) {
        this.parent = parent;
    }

    public Node getParent() {
        return parent;
    }
    public int getFx() {
        return fx;
    }

    public int getFy() {
        return fy;
    }
    public boolean isStart() {
        return start;
    }

    public boolean isTarget() {
        return target;
    }

    public boolean isWall() {
        return iswall;
    }

    public void renderNode(Graphics2D g) {
        if (used_c) {
            g.drawImage(o, xPos, yPos, null);
        } else if (impuse) {
             g.drawImage(x, xPos, yPos, null);
           
        } 
    }

    public void used() {
        used = true;
    }
     public void used_c() {
        used_c = true;
    }

    public void clear() {
        start = false;
        target = false;
        vcdd = false;
        impuse = false;

    }

    public void draw() {
        if (impuse) {
            clear();
            return;
        }

        impuse = true;
    }
    public void setDirections(Node l, Node r, Node u, Node d) {
        left = l;
        right = r;
        up = u;
        down = d;
    }

    public void setSub(Node l, Node r, Node u, Node d) {
        subleftup = l;
        subrightup = r;
        subleftdown = u;
        subrightdown = d;
    }

    public int getPos(Node node) {
        int id = 0;
        if (node == getUp()) {
            id = 1;
        } else if (node == getRight()) {
            id = 2;
        } else if (node == getDown()) {
            id = 3;
        } else if (node == getLeft()) {
            id = 4;
        }
        return id;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public Node getUp() {
        return up;
    }

    public Node getDown() {
        return down;
    }

    public Node getSubL_U() {
        return subleftup;
    }

    public Node getSubR_U() {
        return subrightup;
    }

    public Node getSubL_D() {
        return subleftdown;
    }

    public Node getSubR_D() {
        return subrightdown;
    }

    public void addChildNode(Node adj) {
        adj.state = State.Unvisited;
        child.add(adj);
    }

    public ArrayList<Node> getChild() {
        return child;
    }

    public void addChildnewNode(Node adj) {
        childnew.add(adj);
    }

    public ArrayList<Node> getChildnew() {
        return childnew;
    }
     public void calcH(Node target) {
        
                Hcost = (Math.pow(fx - target.fx, 2) + Math.pow(fy - target.fy, 2)) * 10;
        } 
    

    public void calcG(Node currentNode, Node testingNode) {
        
            if (testingNode == currentNode.getLeft() || testingNode == currentNode.getRight()
                    || testingNode == currentNode.getUp() || testingNode == currentNode.getDown()) {
                testingNode.Gcost = currentNode.Gcost + 10;
        }
    }

    public void calcF() {
        Fcost = Gcost + Hcost;
    }

    public Node traceBack() {
        onPath = true;
        return parent;
    }
}
