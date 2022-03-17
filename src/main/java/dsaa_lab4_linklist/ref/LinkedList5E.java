package dsaa_lab4_linklist.ref;
import java.io.*;
import java.util.*;

public class LinkedList5E {
    public static void main(String[] args) {
        QReader1 qr = new QReader1();
        QWriter1 qw = new QWriter1();
        int n = qr.nextInt();
        int m = qr.nextInt();
        int q = qr.nextInt();
        Node[][] matrix = new Node[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrix[i][j] = new Node(qr.nextInt()); // O(n*m)
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (i != 0) {
                    matrix[i][j].up = matrix[i - 1][j];
                }
                if (j != 0) {
                    matrix[i][j].left = matrix[i][j - 1];
                }
                if (i != n - 1) {
                    matrix[i][j].down = matrix[i + 1][j];
                }
                if (j != m - 1) {
                    matrix[i][j].right = matrix[i][j + 1]; // O(n*m)
                }
            }
        }
        // initialize complete with O(n*m)

        Node head = matrix[0][0];
        for (int i = 0; i < q; i++) {
            int a = qr.nextInt();
            int b = qr.nextInt();
            int c = qr.nextInt();
            int d = qr.nextInt();
            int h = qr.nextInt();
            int w = qr.nextInt();
            Node ab = head;
            for (int j = 1; j < a; j++) {
                ab = ab.down;
            }
            for (int j = 1; j < b; j++) {
                ab = ab.right;
            }//get ab
            Node cd = head;
            for (int j = 1; j < c; j++) {
                cd = cd.down;
            }
            for (int j = 1; j < d; j++) {
                cd = cd.right;
            }//get cd

            //批注：这部分是交换考虑不周，应该修改的是指针。
            if(h > 1 && w > 1) {
                for (int j = 1; j < w; j++) {
                    //exchange the value of ab and cd
                    exchange(ab, cd);
                    //Then, go to the next one on the same row
                    ab = ab.right;
                    cd = cd.right;
                    //And this will go along, but after pointer points to the last one in upper edge, it stops before exchange.
                }
                for (int j = 1; j < h; j++) {
                    exchange(ab, cd);
                    ab = ab.down;
                    cd = cd.down;
                }
                for (int j = 1; j < w; j++) {
                    exchange(ab, cd);
                    ab = ab.left;
                    cd = cd.left;
                }
                for (int j = 1; j < h; j++) {
                    exchange(ab, cd);
                    ab = ab.up;
                    cd = cd.up;
                }//Now the pointer is on the corner of upper edge and left edge again.
            }else{
                if (h > 1) {
                    for (int j = 0; j < h; j++) {
                        exchange(ab, cd);
                        ab = ab.down;
                        cd = cd.down;
                    }
                }else if (w > 1) {
                    for (int j = 0; j < w; j++) {
                        exchange(ab, cd);
                        ab = ab.right;
                        cd = cd.right;
                    }
                }else {
                    exchange(ab, cd);
                }
            }
        }

        Node point = head;
        while (point != null) {
            Node firstInRow = point;
            while (point != null) {
                System.out.print(point.data + " ");
                point = point.right;
            }
            point = firstInRow.down;
            System.out.println("");
        }
    }

    private static void exchange(Node ab, Node cd) {
        int temp = cd.data;
        cd.data = ab.data;
        ab.data = temp;
    }

    static class Node {
        int data;
        Node left;
        Node right;
        Node up;
        Node down;
        Node(int dataIn) {
            data = dataIn;
            left = null;
            right = null;
            up = null;
            down = null;
        }
    }
}

class QReader1 {
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private StringTokenizer tokenizer = new StringTokenizer("");

    private String innerNextLine() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    public boolean hasNext() {
        while (!tokenizer.hasMoreTokens()) {
            String nextLine = innerNextLine();
            if (nextLine == null) {
                return false;
            }
            tokenizer = new StringTokenizer(nextLine);
        }
        return true;
    }

    public String nextLine() {
        tokenizer = new StringTokenizer("");
        return innerNextLine();
    }

    public String next() {
        hasNext();
        return tokenizer.nextToken();
    }

    public int nextInt() {
        return Integer.parseInt(next());
    }

    public long nextLong() {
        return Long.parseLong(next());
    }
}

class QWriter1 implements Closeable {
    private BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    public void print(Object object) {
        try {
            writer.write(object.toString());
        } catch (IOException e) {
            return;
        }
    }

    public void println(Object object) {
        try {
            writer.write(object.toString());
            writer.write("\n");
        } catch (IOException e) {
            return;
        }
    }

    @Override
    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            return;
        }
    }
}
