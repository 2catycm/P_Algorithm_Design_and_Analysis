package lab1;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

import static org.junit.jupiter.api.Assertions.*;

class ProblemB_ManyToOneStableMatchingTest {
    @Test
    void testPQ(){
        var integers = new PriorityQueue<Integer>(2, Comparator.comparingInt(i-> (int) i).reversed());
        integers.addAll(Arrays.asList(5,3,2,4,1));
        while(!integers.isEmpty()){
            System.out.println(integers.poll());
        }
        integers = new PriorityQueue<Integer>();
        integers.addAll(Arrays.asList(5,3,2,4,1));
        while(!integers.isEmpty()){
            System.out.println(integers.poll());
        }
    }

    @Test
    void solve() {

    }
}