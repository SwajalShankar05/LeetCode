
class Solution {
    public int minCostConnectPoints(int[][] points) {

        int n = points.length;

        boolean[] visited = new boolean[n];
        int[] minDist = new int[n];

        for (int i = 0; i < n; i++) {
            minDist[i] = Integer.MAX_VALUE;
        }

        minDist[0] = 0;
        int totalCost = 0;

        for (int i = 0; i < n; i++) {

            int u = -1;

            // Find minimum cost unvisited point
            for (int j = 0; j < n; j++) {
                if (!visited[j] &&
                    (u == -1 || minDist[j] < minDist[u])) {
                    u = j;
                }
            }

            visited[u] = true;
            totalCost += minDist[u];

            // Update distances
            for (int v = 0; v < n; v++) {
                if (!visited[v]) {

                    int cost = Math.abs(points[u][0] - points[v][0])
                             + Math.abs(points[u][1] - points[v][1]);

                    minDist[v] = Math.min(minDist[v], cost);
                }
            }
        }

        return totalCost;
    }
}
