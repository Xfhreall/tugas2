
//file utama
import java.util.Scanner;

class Node {
    String nama, hobi1, hobi2, hobi3;
    int key;
    int countFoll;
    int weight = 1;

    public Node() {
    }

    public Node(String nama, String hobi1, String hobi2, String hobi3, int key, int countFoll) {
        this.nama = nama;
        this.hobi1 = hobi1;
        this.hobi2 = hobi2;
        this.hobi3 = hobi3;
        this.key = key;
        this.countFoll = countFoll;
    }

    public String getNama() {
        return nama;
    }

    public String getHobi1() {
        return hobi1;
    }

    public String getHobi2() {
        return hobi2;
    }

    public String getHobi3() {
        return hobi3;
    }

    public int getKey() {
        return key;
    }

    public int follower() {
        return ++countFoll;
    }

    public int getFollower() {
        return countFoll;
    }
}

class Graph extends CuitCuit {
    private int[][] matrix;
    private int size;
    private int[] parent;
    private int[] rank;

    public Graph(int size) {
        this.size = size;
        matrix = new int[size][size];
        parent = new int[size];
        rank = new int[size];
        for (int i = 0; i < size; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    public int getSize() {
        return size;
    }

    public void setNewSize(int size) {
        this.size = size;
        int[][] newMatrix = new int[size][size];
        int minSize = Math.min(matrix.length, size);
        for (int i = 0; i < minSize; i++) {
            System.arraycopy(matrix[i], 0, newMatrix[i], 0, Math.min(matrix[i].length, size));
        }
        matrix = newMatrix;

        int[] newParent = new int[size];
        int[] newRank = new int[size];
        for (int i = 0; i < size; i++) {
            newParent[i] = (i < parent.length) ? parent[i] : i;
            newRank[i] = (i < rank.length) ? rank[i] : 0;
        }
        parent = newParent;
        rank = newRank;
    }

    public String insert(Node a) {
        return (a.getNama() + " inserted");
    }

    public String connect(Node a, Node b) {
        matrix[a.getKey()][b.getKey()] = 1;
        // grouping
        int rootX = find(a.getKey());
        int rootY = find(b.getKey());
        if (rootX != rootY) {
            if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
        }
        b.follower();
        return "connect " + a.getNama() + " " + b.getNama() + " success";
    }

    public void print() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    private int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    public int countGroups() {
        boolean[] isRoot = new boolean[size];
        for (int i = 0; i < size; i++) {
            isRoot[find(i)] = true;
        }
        int count = 0;
        for (boolean root : isRoot) {
            if (root) {
                count++;
            }
        }
        return count;
    }

    void addEdge(Node a, Node b) {
        matrix[a.getKey()][b.getKey()] = 1;
    }

    int minCuitUlang(Node a, Node b) {
        boolean[] visited = new boolean[size];
        int[] distance = new int[size];
        int[] queue = new int[size];
        int front = 0, rear = 0;

        for (int i = 0; i < size; i++) {
            distance[i] = Integer.MAX_VALUE;
        }

        distance[a.getKey()] = 0;
        queue[rear++] = a.getKey();
        visited[a.getKey()] = true;

        while (front < rear) {
            int u = queue[front++];

            for (int v = 0; v < size; v++) {
                if (matrix[u][v] == 1 && !visited[v]) {
                    visited[v] = true;
                    distance[v] = distance[u] + 1;
                    queue[rear++] = v;
                }
            }
        }

        return (distance[b.getKey()] == Integer.MAX_VALUE) ? -1 : distance[b.getKey()] - 1;
    }

    public String hobigroup(int[] ranked) {
        int max = findMax(ranked);
        int[] counts = new int[max + 1];

        return "";
    }

    private int findMax(int[] array) {
        int max = array[0];
        for (int num : array) {
            if (num > max) {
                max = num;
            }
        }
        return max;
    }

    public Node findNode(int a, Node[] arr) {
        for (Node node : arr) {
            if (a == node.getKey()) {
                return node;
            }
        }
        return null;
    }

    String[] addSize(String[] arr) {
        String[] newArr = new String[arr.length + 1];
        System.arraycopy(arr, 0, newArr, 0, arr.length);
        return newArr;
    }

    void ascending(String[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i].compareTo(array[j]) > 0) {
                    String p = array[i];
                    array[i] = array[j];
                    array[j] = p;
                }
            }
        }
    }

    public void suggest(Node a) {
        int sugLength = 1;
        int index = 0;
        String[] suggest = new String[sugLength];
        for (int i = 0; i < arr.length; i++) {
            if (matrix[a.getKey()][i] == 1) {
                for (int j = 0; j < arr.length; j++) {
                    if (matrix[j][i] == 1) {
                        if (a.getKey() == j) {
                            continue;
                        } else {
                            if (matrix[a.getKey()][j] == 1 || matrix[j][a.getKey()] == 1) {
                                continue;
                            } else {
                                if (index == suggest.length) {
                                    suggest = addSize(suggest);
                                }
                                suggest[index++] = findNode(j, arr).getNama();
                            }
                        }
                    }
                }
            }
        }
        ascending(suggest);

        for (int i = 0; i < suggest.length; i++) {
            System.out.print(suggest[i]);
            if (suggest.length - 1 != i) {
                System.out.print(",");
            }
        }
        System.out.println();
    }

    public void hobiGrup(Node[] nodes) {
        int[] groupRoots = new int[size];
        int groupCount = 0;

        for (int i = 0; i < size; i++) {
            int root = find(i);
            boolean found = false;
            for (int j = 0; j < groupCount; j++) {
                if (groupRoots[j] == root) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                groupRoots[groupCount++] = root;
            }
        }

        String[] groupTopics = new String[groupCount];

        for (int i = 0; i < groupCount; i++) {
            int root = groupRoots[i];
            int[] hobbyCounts = new int[100];
            String[] hobbies = new String[100];
            int hobbyIndex = 0;

            for (Node node : nodes) {
                if (find(node.getKey()) == root) {
                    String[] nodeHobbies = { node.getHobi1(), node.getHobi2(), node.getHobi3() };
                    for (String hobi : nodeHobbies) {
                        boolean found = false;
                        for (int j = 0; j < hobbyIndex; j++) {
                            if (hobbies[j].equals(hobi)) {
                                hobbyCounts[j]++;
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            hobbies[hobbyIndex] = hobi;
                            hobbyCounts[hobbyIndex++] = 1;
                        }
                    }
                }
            }

            int maxCount = 0;
            for (int j = 0; j < hobbyIndex; j++) {
                if (hobbyCounts[j] > maxCount) {
                    maxCount = hobbyCounts[j];
                }
            }

            StringBuilder result = new StringBuilder();
            for (int j = 0; j < hobbyIndex; j++) {
                if (hobbyCounts[j] == maxCount) {
                    if (result.length() > 0) {
                        result.append(",");
                    }
                    result.append(hobbies[j]);
                }
            }
            groupTopics[i] = result.toString();
        }

        for (int i = 0; i < groupCount - 1; i++) {
            for (int j = i + 1; j < groupCount; j++) {
                if (groupTopics[i].compareTo(groupTopics[j]) > 0) {
                    String temp = groupTopics[i];
                    groupTopics[i] = groupTopics[j];
                    groupTopics[j] = temp;
                }
            }
        }

        for (int i = 0; i < groupCount; i++) {
            System.out.println(groupTopics[i]);
        }
    }

}

public class CuitCuit {
    static Node[] arr = new Node[0];

    static Node find(String a, Node[] arr) {
        for (Node node : arr) {
            if (a.equals(node.getNama())) {
                return node;
            }
        }
        return null;
    }

    static String getMostFoll(Node[] arr) {
        int max = 0;
        String result = null;
        for (Node node : arr) {
            if (max < node.getFollower()) {
                max = node.getFollower();
                result = node.getNama();
            }
        }
        return result;
    }

    static Node[] addSize(Node[] arr) {
        Node[] newArr = new Node[arr.length + 1];
        System.arraycopy(arr, 0, newArr, 0, arr.length);
        return newArr;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int size = in.nextInt();
        arr = new Node[size];
        int interaksi = in.nextInt();
        int key = 0;
        Graph a = new Graph(size);
        int count = 0;

        // insert
        for (int i = 0; i < size; i++) {
            String nama = in.next();
            String hobi1 = in.next();
            String hobi2 = in.next();
            String hobi3 = in.next();
            arr[i] = new Node(nama, hobi1, hobi2, hobi3, key, 0);
            a.insert(arr[i]);
            key++;
            count++;
        }

        // connect & most
        for (int i = 0; i < interaksi; i++) {
            String nama1 = in.next();
            String nama2 = in.next();
            a.connect(find(nama1, arr), find(nama2, arr));
            find(nama2, arr).follower();
        }
        in.nextLine();
        while (true) {
            String operasi = in.nextLine();
            if (count > arr.length) {
                arr = addSize(arr);
            }
            if (operasi.isEmpty()) {
                break;
            }
            a.setNewSize(arr.length);
            String[] res = operasi.split(" ");
            switch (res[0]) {
                case "insert":
                    String nama = res[1];
                    String hobi1 = res[2];
                    String hobi2 = res[3];
                    String hobi3 = res[4];
                    if (count == arr.length) {
                        arr = addSize(arr);
                    }
                    a.setNewSize(arr.length);
                    arr[count] = new Node(nama, hobi1, hobi2, hobi3, key, 0);
                    System.out.println(a.insert(arr[count]));
                    key++;
                    count++;
                    break;
                case "connect":
                    String nama1 = res[1];
                    String nama2 = res[2];
                    System.out.println(a.connect(find(nama1, arr), find(nama2, arr)));
                    find(nama2, arr).follower();
                    break;
                case "mostfollowed":
                    System.out.println(getMostFoll(arr));
                    break;
                case "mincuit":
                    String user1 = res[1];
                    String user2 = res[2];
                    Node node1 = find(user1, arr);
                    Node node2 = find(user2, arr);
                    if (node1 == null || node2 == null) {
                        System.out.println(-1);
                    } else {
                        System.out.println(a.minCuitUlang(node1, node2));
                    }
                    break;
                case "numgroup":
                    System.out.println(a.countGroups());
                    break;
                case "grouptopic":
                    a.hobiGrup(arr);
                    break;
                case "suggestfriend":
                    String s = res[1];
                    a.suggest(find(s, arr));
                    break;
            }
        }
    }
}