package lab1;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//#pragma OJ main
public class ProblemB_ManyToOneStableMatching {
    //其实这个reader最好可以重定向，这样才方便OJUnit测试main的输入输出格式对不对。
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();

    public static void main(String[] args) {
        final int N = in.nextInt(); //students
        final int M = in.nextInt(); //colleges
        final int[] capacities = in.nextIntArray(M);
        int[][] studentPreferences = new int[N][];
        for (int i = 0; i < N; i++) {
            studentPreferences[i] = in.nextIntArray(M);
        }
        int[][] collegePreferences = new int[M][];
        for (int i = 0; i < M; i++) {
            collegePreferences[i] = in.nextIntArray(N); //第i个学院对N个学生的评价
        }
        final int[][] solution = solve(capacities, studentPreferences, collegePreferences);
        for (int i = 0; i < M; i++) {
            out.print(solution[i].length); out.print(" ");
//            out.printlnIntArray(solution[i]);
            for (int j = 0; j < solution[i].length; j++) {
                out.print(solution[i][j]+1);//题目编号与我们不一致
                out.print(" ");
            }
            out.println();
        }
    }

    /**
     * @param capacities         the capacity for each college numbered 0...M-1
     * @param studentPreferences the (i,j) element means the preference of the i th student for the j th college.
     * @param collegePreferences the (i,j) element means the preference of the i th college for the j th student.
     * @return Stable Matching M: the final enrollment to students of each college.
     */
    static int[][] solve(int[] capacities, int[][] studentPreferences, int[][] collegePreferences) {
        //N and M
        int N = studentPreferences.length;
        int M = collegePreferences.length;
        //return 的结果
        PriorityQueue<Integer>[] collegePreferenceEnrollments = new PriorityQueue[M];
        for (int i = 0; i < M; i++) {
            final int finalI = i;
            collegePreferenceEnrollments[i] = new PriorityQueue<>(capacities[i]+1, Comparator.comparingInt(
//                    j -> collegePreferences[finalI][(int) j]).reversed()
                    j -> collegePreferences[finalI][(int) j])
            ); //这是临时的预推免列表，初始没有人
        }
        //prepare stack
        Stack<Integer> freeStudents = new Stack<>();
        for (int i = 0; i < N; i++) {
            freeStudents.add(i);
        }
        //优先队列。为了让学生能找到自己当前没有申请过的最爱高校。
        final List<Integer> collegeList = IntStream.range(0, M).boxed().collect(Collectors.toList());
        PriorityQueue<Integer>[] studentPreferenceOrders = new PriorityQueue[N];
        for (int i = 0; i < N; i++) {
            final int finalI = i;
            studentPreferenceOrders[i] = new PriorityQueue<>(M, Comparator.comparingInt(
//                    j -> studentPreferences[finalI][j])); //pq存的是college的编号。
                    j -> studentPreferences[finalI][(int) j]).reversed()); //pq存的是college的编号。
            studentPreferenceOrders[i].addAll(collegeList);
        }
        //开始算法核心
        while (!freeStudents.isEmpty()) {
            final Integer newStudent = freeStudents.pop();
            final Integer currentFavoriteSchool = studentPreferenceOrders[newStudent].poll();
            if (currentFavoriteSchool == null
                    || studentPreferences[newStudent][currentFavoriteSchool] < 0)
                continue; //这个学生已经申请完了所有他可以接受的学校, 只能保持自由了
            assert studentPreferences[newStudent][currentFavoriteSchool] != 0; //题目条件
            if (collegePreferences[currentFavoriteSchool][newStudent] < 0) { //如果学校明确拒绝他
                freeStudents.push(newStudent); //回到自由列表，继续下一所申请
                continue;
            }
            //上面已经确认，互相不明确拒绝。下面讨论自由与容量
            final var collegePreferenceEnrollment = collegePreferenceEnrollments[currentFavoriteSchool];
            if (collegePreferenceEnrollment.size()<capacities[currentFavoriteSchool]){
                //容量没满的情况
                collegePreferenceEnrollment.add(newStudent);
            }else{
                //容量已满
                collegePreferenceEnrollment.add(newStudent);//先加进去
                //然后把最菜的学生请出来。
                final Integer worstStudent = collegePreferenceEnrollment.poll();
                freeStudents.push(worstStudent);
            }
        }
        // 从collegePreferenceEnrollment 转换为return
        int[][] collegeEnrollmentResult = new int[M][];
        for (int i = 0; i < M; i++) {
            collegeEnrollmentResult[i] = collegePreferenceEnrollments[i].stream()
                    .mapToInt(Integer::intValue).toArray();
        }
        return collegeEnrollmentResult;
    }
    //        return new int[][]{{1,2,3},{2,3,4}}; //回归测试，能够输入输出
}
//#include "OnlineJudgeIO.java"
