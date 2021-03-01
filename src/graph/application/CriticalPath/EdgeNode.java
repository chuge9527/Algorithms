package graph.application.CriticalPath;
// 邻接表 -边表节点
public class EdgeNode {

    public int adjevex;//对应顶点
    public int weight;
    public EdgeNode next;

    public EdgeNode(int adjevex, EdgeNode next) {
        this.adjevex = adjevex;
        this.next = next;
    }

    public EdgeNode(int adjevex, int weight, EdgeNode next) {
        this.adjevex = adjevex;
        this.weight = weight;
        this.next = next;
    }

}
