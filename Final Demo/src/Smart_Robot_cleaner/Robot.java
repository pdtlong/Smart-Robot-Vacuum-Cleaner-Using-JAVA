package Smart_Robot_cleaner;

import javax.swing.ImageIcon;

public class Robot{
    ImageIcon icon;
    private int x;
    private int y;
    private float power;

    public Robot(int x, int y) {
      this.x = x;
      this.y = y;
      this.power = 100;
      icon = new ImageIcon("Image\\robot1.jpg");
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }


    public void move(Node n){
        x = n.fx;
        y = n.fy;
        setPower(getPower() - 0.2f);
    }

    public void setPower(float power) {
        this.power = power;
    }

    public float getPower() {
        return power;
    }
}
