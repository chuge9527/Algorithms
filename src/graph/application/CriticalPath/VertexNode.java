package graph.application.CriticalPath;
//顶点数组
public class VertexNode {

    public int in;      //入度？
    public Object data;  //数据
    public EdgeNode firstedge;

    public VertexNode(Object data, int in, EdgeNode firstedge) {
        this.data = data;
        this.in = in;
        this.firstedge = firstedge;
    }
}