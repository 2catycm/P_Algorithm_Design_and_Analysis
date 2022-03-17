package lab2;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

//#pragma OJ main
public class ProblemA_ChainStore {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();

    public static void main(String[] args) {
        final int N = in.nextInt();
        final int M = in.nextInt();
        final int W = in.nextInt();
        final int H = in.nextInt();
        final int[] openDays = in.nextIntArray(N);
        final int[] openStores = in.nextIntArray(M);
        out.println(new ProblemA_ChainStore().solve(N, M, W, H, openDays, openStores) ? "Yes" : "No");
    }

    boolean solve(int n, int m, int w, int h, int[] openDays, int[] openStores) {
//        reverseSort(openDays);
        final PriorityQueue<Store> popularStores = new PriorityQueue<>(
                Comparator.reverseOrder());
        popularStores.addAll(Arrays.stream(openDays).boxed().map(
                days->new Store(days, 0)).collect(Collectors.toList()));
//        final int[] nextAvailableDay = new int[n]; // 一开始是0，表示第0天所有商店都是可用的。
        for (int i = 0; i < m; i++) {
            if (openStores[i]<0)
                return false; //前几天的太大了，顶到了今天。
//            for (int j = 0; j < openStores[i]; j++) { //满足openStores的需要
//
//            }
            final ArrayList<Store> observedStores = new ArrayList<>();
            while(openStores[i]>0){
                if (popularStores.isEmpty())
                    return false; //账下无人，而仍有敌军
                if (i+w>m) //比如有0,1,2天，现在是第二天了，不可以征调商店，然而需求未能满足
                    return false;
                final Store popularStore = popularStores.poll(); //剩余天数最多的商店
                observedStores.add(popularStore); //暂时保存
                if (i>=popularStore.nextAvailableDay && popularStore.remainDays>0) {
                    //那么我们就征用这个商店
                    popularStore.remainDays-=w; //比如，W=2，wi=4， 那么只剩下2天可以用
                    popularStore.nextAvailableDay=i+w+h; //比如，第0天征用2天，休息2天，第4天可以使用。
                    for (int j = 0; j < w; j++) {
                        openStores[i+j]--; //被当前征用的商店满足了要求。
                    }
                }
            }
            popularStores.addAll(observedStores);
        }
        return popularStores.stream().mapToInt(o -> o.remainDays).noneMatch(i -> i != 0);
    }
    static class Store implements Comparable<Store>{
        int remainDays;
        int nextAvailableDay;
        public Store(int remainDays, int nextAvailableDay) {
            this.remainDays = remainDays;
            this.nextAvailableDay = nextAvailableDay;
        }
        @Override
        public int compareTo(Store o) {
            return Integer.compare(this.remainDays, o.remainDays);
        }
    }
    private void reverseSort(int[] array){
        Arrays.sort(array);
        int half = (array.length >> 1);
        for (int i = 0; i < half; i++) {
            final int index = array.length - 1 - i;
            int temp = array[i];
            array[i] = array[index];
            array[index] = temp;
        }
    }
}
//#include "OnlineJudgeIO.java"
