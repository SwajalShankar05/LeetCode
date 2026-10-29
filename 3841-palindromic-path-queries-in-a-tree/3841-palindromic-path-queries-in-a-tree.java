
import java.util.*;

class Solution {
    List<Integer>[] adj;
    int[][] up;
    int[] depth, tin, size, parent, order;
    int timer, LOG;
    SegTree seg;
    char[] s;

    public List<Boolean> palindromePath(int n, int[][] edges,
                                    String str, String[] queries) {
        s = str.toCharArray();

        adj = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int[] e : edges) {
            adj[e[0]].add(e[1]);
            adj[e[1]].add(e[0]);
        }

        LOG = 1;
        while ((1 << LOG) <= n) LOG++;

        up = new int[LOG][n];
        depth = new int[n];
        tin = new int[n];
        size = new int[n];
        parent = new int[n];
        order = new int[n];

        Arrays.fill(parent, -1);

        // Iterative DFS
        int[] stack = new int[n];
        int top = 0;
        stack[top++] = 0;
        parent[0] = 0;

        while (top > 0) {
            int u = stack[--top];
            tin[u] = timer;
            order[timer++] = u;

            for (int v : adj[u]) {
                if (v == parent[u]) continue;

                parent[v] = u;
                depth[v] = depth[u] + 1;
                stack[top++] = v;
            }
        }

        for (int i = n - 1; i >= 0; i--) {
            int u = order[i];
            size[u]++;

            if (u != 0) {
                size[parent[u]] += size[u];
            }
        }

        for (int i = 0; i < n; i++) {
            up[0][i] = parent[i];
        }

        for (int k = 1; k < LOG; k++) {
            for (int i = 0; i < n; i++) {
                up[k][i] = up[k - 1][up[k - 1][i]];
            }
        }

        // Prefix XOR masks from root to each node
        int[] masks = new int[n];

        for (int i = 0; i < n; i++) {
            int u = order[i];
            int bit = 1 << (s[u] - 'a');

            masks[tin[u]] = bit;
            if (u != 0) {
                masks[tin[u]] ^= masks[tin[parent[u]]];
            }
        }

        seg = new SegTree(masks);

        List<Boolean> result = new ArrayList<>();

        for (String q : queries) {
            String[] p = q.split(" ");
            int u = Integer.parseInt(p[1]);

            if (p[0].equals("update")) {
                char c = p[2].charAt(0);

                int delta = (1 << (s[u] - 'a'))
                          ^ (1 << (c - 'a'));

                seg.update(tin[u], tin[u] + size[u] - 1, delta);
                s[u] = c;

            } else {
                int v = Integer.parseInt(p[2]);
                int lca = getLCA(u, v);

                int mask = seg.query(tin[u])
                          ^ seg.query(tin[v])
                          ^ (1 << (s[lca] - 'a'));

                result.add((mask & (mask - 1)) == 0);
            }
        }

        

        return result;
    }

    int getLCA(int u, int v) {
        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        int diff = depth[u] - depth[v];

        for (int k = 0; k < LOG; k++) {
            if ((diff & (1 << k)) != 0) {
                u = up[k][u];
            }
        }

        if (u == v) return u;

        for (int k = LOG - 1; k >= 0; k--) {
            if (up[k][u] != up[k][v]) {
                u = up[k][u];
                v = up[k][v];
            }
        }

        return parent[u];
    }

    class SegTree {
        int n;
        int[] tree, lazy;

        SegTree(int[] a) {
            n = a.length;
            tree = new int[4 * n];
            lazy = new int[4 * n];
            build(1, 0, n - 1, a);
        }

        void build(int node, int l, int r, int[] a) {
            if (l == r) {
                tree[node] = a[l];
                return;
            }

            int mid = (l + r) / 2;
            build(node * 2, l, mid, a);
            build(node * 2 + 1, mid + 1, r, a);
        }

        void apply(int node, int x) {
            tree[node] ^= x;
            lazy[node] ^= x;
        }

        void push(int node) {
            if (lazy[node] != 0) {
                apply(node * 2, lazy[node]);
                apply(node * 2 + 1, lazy[node]);
                lazy[node] = 0;
            }
        }

        void update(int l, int r, int x) {
            update(1, 0, n - 1, l, r, x);
        }

        void update(int node, int l, int r,
                    int ql, int qr, int x) {
            if (ql <= l && r <= qr) {
                apply(node, x);
                return;
            }

            push(node);
            int mid = (l + r) / 2;

            if (ql <= mid) {
                update(node * 2, l, mid, ql, qr, x);
            }
            if (qr > mid) {
                update(node * 2 + 1, mid + 1, r,
                       ql, qr, x);
            }
        }

        int query(int idx) {
            return query(1, 0, n - 1, idx);
        }

        int query(int node, int l, int r, int idx) {
            if (l == r) return tree[node];

            push(node);
            int mid = (l + r) / 2;

            if (idx <= mid) {
                return query(node * 2, l, mid, idx);
            }
            return query(node * 2 + 1, mid + 1, r, idx);
        }
    }
}
