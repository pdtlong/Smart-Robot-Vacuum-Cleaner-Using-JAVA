package Smart_Robot_cleaner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.JOptionPane;

public class FindPath {

    public Node start;
    public Node target;
    public Node checkingNode;
    public Node[][] NodeList = new Node[20][20];
    public Node path[] = new Node[200];
    public int count1 = 0, count2 = 0;
    public boolean calculate = false;
    ArrayList<Node> openList = new ArrayList<Node>();
    ArrayList<Node> closeList = new ArrayList<Node>();

    public void checkNodeNext(Node currentNode, Node testingNode) {

        if (testingNode == null) {
            count1 += 1;
            return;
        }

        if (testingNode.isTarget()) {
            count1 += 1;
            testingNode.setParent(currentNode);
            calculate = true;
            return;
        }

        if (testingNode.isWall()) {
            return;
        }
        if (closeList.contains(testingNode) == false) {
            if (openList.contains(testingNode) == true) {
                double newGcost = 0;
                if (testingNode == currentNode.getLeft() || testingNode == currentNode.getRight()
                        || testingNode == currentNode.getUp() || testingNode == currentNode.getDown()) {
                    newGcost = currentNode.Gcost + 10;
                }
                if (newGcost < testingNode.Gcost) {
                    testingNode.setParent(currentNode);
                    testingNode.Gcost = newGcost;
                    testingNode.calcF();
                }
            } else {
                testingNode.setParent(currentNode);
                testingNode.calcH(target);
                testingNode.calcG(currentNode, testingNode);
                testingNode.calcF();
                openList.add(testingNode);
                testingNode.used();
                count1 += 1;
            }
        }

    }

// check chi phi tam thoi min khong?
    public Node getSmallestFcost(ArrayList<Node> al) {
        Collections.sort(al, new Comparator<Node>() {
            @Override
            public int compare(Node n1, Node n2) {
                if (n1.Fcost < n2.Fcost) {
                    return -1;
                } else if (n1.Fcost == n2.Fcost) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });
        return al.get(0);
    }

    public void findPath() {

        openList.add(start);
        checkingNode = start;
        while (!calculate) {
            if (openList.size() > 0) {
                if (checkingNode.getLeft() != null) {
                    checkNodeNext(checkingNode, checkingNode.getLeft());
                }

                if (checkingNode.getRight() != null) {
                    checkNodeNext(checkingNode, checkingNode.getRight());
                }

                if (checkingNode.getUp() != null) {
                    checkNodeNext(checkingNode, checkingNode.getUp());
                }

                if (checkingNode.getDown() != null) {
                    checkNodeNext(checkingNode, checkingNode.getDown());
                }

                if (!calculate) {
                    closeList.add(checkingNode);
                    openList.remove(checkingNode);
                }
                if (openList.size() > 0) {
                    checkingNode = getSmallestFcost(openList);
                }
                if (calculate) {
                    path[0] = target;
                    path[1] = checkingNode;
                    Node n = checkingNode.traceBack();
                    path[2] = n;
                    count2 += 1;
                    while (n != start) {
                        n = n.traceBack();
                        path[count2 + 2] = n;
                        count2 += 1;
                    }

                    JOptionPane.showMessageDialog(null, " Đã tìm thấy đường đi", "Information", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Không tìm thấy đường đi", "Path Finder", JOptionPane.INFORMATION_MESSAGE);
                break;
            }
        }
    }
}
