package graph.application.CriticalPath;

import java.util.Stack;

public class CriticalPathSort {

    int[] etv, ltv;
    Stack stack = new Stack(); //存储入度为0的顶点，便于每次寻找入度为0的顶点时都遍历整个邻接表
    Stack stack2 = new Stack(); //将顶点序号压入拓扑序列的栈
    static VertexNode[] adjList;

    //通过拓扑排序求得etv
    public boolean ToplogicalSort() {
        EdgeNode e;
        int k, gettop;
        int count = 0;
        etv = new int[adjList.length];
        for (int i = 0; i < adjList.length; i++) {
            if (adjList[i].in == 0) {
                stack.push(i);
            }
        }
        for (int i = 0; i < adjList.length; i++) {
            etv[i] = 0;
        }

        while (!stack.isEmpty()) {
            gettop = (int) stack.pop();
            count++;
            stack2.push(gettop);
            for (e = adjList[gettop].firstedge; e != null; e = e.next) {
                k = e.adjevex;
                if ((--adjList[k].in) == 0) {
                    stack.push(k);
                }
                if (etv[gettop] + e.weight > etv[k]) {
                    etv[k] = etv[gettop] + e.weight;
                }
            }
        }
        if (count < adjList.length) return false;
        else return true;

    }


    public void CriticalPath() {
        EdgeNode e;
        int gettop, k, j;
        int ete, lte;
        if (!this.ToplogicalSort()) {
            System.out.println("该网中存在回路!");
            return;
        }
        ltv = new int[adjList.length];
        for (int i = 0; i < adjList.length; i++) {
            ltv[i] = etv[etv.length - 1];
        }

        while (!stack2.isEmpty()) {
            gettop = (int) stack2.pop();
            for (e = adjList[gettop].firstedge; e != null; e = e.next) {
                k = e.adjevex;
                if (ltv[k] - e.weight < ltv[gettop]) {
                    ltv[gettop] = ltv[k] - e.weight;
                }
            }
        }
        for (int i = 0; i < adjList.length; i++) {
            for (e = adjList[i].firstedge; e != null; e = e.next) {
                k = e.adjevex;
                ete = etv[i];
                lte = ltv[k] - e.weight;
                if (ete == lte) {
                    System.out.print("<" + adjList[i].data + "," + adjList[k].data + "> length: " + e.weight + ",");
                }
            }
        }
    }

    public static EdgeNode getAdjvex(VertexNode node) {
        EdgeNode e = node.firstedge;
        while (e != null) {
            if (e.next == null) break;
            else
                e = e.next;
        }
        return e;
    }

    public static void main(String[] args) {
        int[] ins = {0, 1, 1, 2, 2, 1, 1, 2, 1, 2};
        int[][] adjvexs = {
                {2, 1},
                {4, 3},
                {5, 3},
                {4},
                {7, 6},
                {7},
                {9},
                {8},
                {9},
                {}
        };
        int[][] widths = {
                {4, 3},
                {6, 5},
                {7, 8},
                {3},
                {4, 9},
                {6},
                {2},
                {5},
                {3},
                {}
        };
        adjList = new VertexNode[ins.length];
        for (int i = 0; i < ins.length; i++) {
            adjList[i] = new VertexNode("V" + i, ins[i], null);
            if (adjvexs[i].length > 0) {
                for (int j = 0; j < adjvexs[i].length; j++) {
                    if (adjList[i].firstedge == null)
                        adjList[i].firstedge = new EdgeNode(adjvexs[i][j], widths[i][j], null);
                    else {
                        getAdjvex(adjList[i]).next = new EdgeNode(adjvexs[i][j], widths[i][j], null);
                    }
                }
            }
        }

        CriticalPathSort c = new CriticalPathSort();
        c.CriticalPath();

    }


}