import java.util.HashSet;

public class Graph
{
    private String[] nodeNames; //所有结点的名称
    private int[][] paths;// 结点之间的路径长度
    public static int INF = -1;//正无穷距离

    public Graph(String[] nodeNames, int[][] paths)
    {
        this.nodeNames = nodeNames.clone();
        this.paths = paths.clone();
    }

    // 给结点的名称，返回Pair<上一结点编号, 最短长度>
    private Pair<int[], int[]> Dijkstra(int nodeIndex)
    {
        HashSet<Integer> processedNodes = new HashSet<>();//已经找到最短路径的结点号集合
        int[] prevNode = new int[nodeNames.length];//每个结点最短路径的前一个结点号
        int[] currentShortestPathLength = new int[nodeNames.length];//从nodeName到各个结点的目前最短长度
        int currentShortestPathIndex = 0;//每一轮查找后最短路径的编号
        int lastProcessNodeIndex = nodeIndex;//最后一个找到的距离最短的结点

        //给距离数组赋初值
        for (int i = 0; i < currentShortestPathLength.length; i++)
        {
            // 到自己距离是0
            if (i == nodeIndex)
            {
                currentShortestPathLength[i] = 0;
            }
            // 到其他的都设为INF
            else
            {
                currentShortestPathLength[i] = INF;
            }
        }
        // 起始结点算作处理过的
        processedNodes.add(nodeIndex);
        // 当已找到最短路径集合还没有包含所有结点的时候，继续循环
        while (processedNodes.size() != nodeNames.length)
        {
            // 从lastProcessNodeIndex出发比较新路径是否比老路径更短
            for (int i = 0; i < nodeNames.length; i++)
            {
                // 新路径比老路径更短，且这个距离不是INF，且这个结点不包含在已处理结点集合中，则更新路径长度与上一结点编号
                if ((paths[lastProcessNodeIndex][i] + currentShortestPathLength[lastProcessNodeIndex] < currentShortestPathLength[i] || currentShortestPathLength[i] == INF) && paths[lastProcessNodeIndex][i] != INF && !processedNodes.contains(i))
                {
                    currentShortestPathLength[i] = paths[lastProcessNodeIndex][i] + currentShortestPathLength[lastProcessNodeIndex];
                    prevNode[i] = lastProcessNodeIndex;
                }
            }
            // 在这一轮循环结束之后查找当前最短路径
            currentShortestPathIndex = findShortestPath(currentShortestPathLength, processedNodes);
            // 找到后，将其连接的结点添加到已处理集合中
            processedNodes.add(currentShortestPathIndex);
            // 修改lastProcessNodeIndex为这个结点
            lastProcessNodeIndex = currentShortestPathIndex;
        }
        return new Pair<>(prevNode, currentShortestPathLength);
    }

    public void printShortestPaths(String nodeName)
    {
        final int NODE_INDEX = getNodeIndex(nodeName);//得到结点号
        final Pair<int[], int[]> info = Dijkstra(NODE_INDEX);
        final int[] prevNode = info.getFirst();
        final int[] currentShortestPathLength = info.getSecond();
        StringBuilder[] pathStr = new StringBuilder[nodeNames.length];
        for (int i = 0; i < pathStr.length; i++)
        {
            pathStr[i] = new StringBuilder();
        }

        int currentNodeIndex = -1;
        System.out.println("从 " + nodeName + " 到各结点的最短路径与距离分别为");
        for (int i = 0; i < nodeNames.length; i++)
        {
            currentNodeIndex = i;
            while (currentNodeIndex != NODE_INDEX)
            {
                pathStr[i].append(nodeNames[currentNodeIndex]);
                currentNodeIndex = prevNode[currentNodeIndex];
            }
            pathStr[i].reverse();
            System.out.printf("%1s%-6s%3d\n", nodeName, pathStr[i].toString(), currentShortestPathLength[i]);
        }
    }

    private int getNodeIndex(String nodeName)
    {
        int index = -1;
        for (int i = 0; i < nodeNames.length; i++)
        {
            if (nodeNames[i].equals(nodeName))
            {
                index = i;
                break;
            }
        }
        return index;
    }

    // 找到数组中最小路径的下标
    private int findShortestPath(int[] currentShortestPathLength, HashSet<Integer> processedNodes)
    {
        int minIndex = -1;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < currentShortestPathLength.length; i++)
        {
            if (currentShortestPathLength[i] < min && currentShortestPathLength[i] != INF && !processedNodes.contains(i))
            {
                min = currentShortestPathLength[i];
                minIndex = i;
            }
        }
        return minIndex;
    }
}
