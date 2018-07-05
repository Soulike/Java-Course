public class Main
{
    public static void main(String[] args)
    {
        final int INF = Graph.INF;
        String[] nodeNames = {"A", "B", "C", "D", "E", "F"};
        int[][] paths = {{0, 2, 5, 1, INF, INF}, {2, 0, 3, 2, INF, INF}, {5, 3, 0, 3, 1, 5}, {1, 2, 3, 0, 1, INF}, {-1, -1, 1, 1, 0, 2}, {INF, INF, 5, INF, 2, 0}};
        Graph graph = new Graph(nodeNames, paths);
        graph.printShortestPaths("A");
    }
}
