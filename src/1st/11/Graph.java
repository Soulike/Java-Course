import javax.management.ConstructorParameters;

public class Graph
{
    private String[] nodeNames; //所有结点的名称
    private int[][] paths;// 结点之间的路径长度

    public Graph()
    {
        nodeNames = null;
        paths = null;
    }

    public Graph(String[] nodeNames, int[][] paths)
    {
        this.nodeNames = nodeNames.clone();
        this.paths = paths.clone();
    }

    public String[] getNodeNames()
    {
        return nodeNames.clone();
    }

    public int[][] getPaths()
    {
        return paths.clone();
    }

    // 给结点的名称，在屏幕上输出到所有结点的最短路径
    public void showShortestPaths(String nodeName)
    {

    }
}
