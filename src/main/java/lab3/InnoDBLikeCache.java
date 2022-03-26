package lab3;

import java.util.HashMap;

/**
 * 实现思路：黑盒依赖Java类库
 *    - 因为是黑盒，所以我们不能获得 package java.util 下可访问的方法。
 *
 * @param <K>
 * @param <V>
 */
class InnoDBLikeCache <K,V>{
    protected HashMap<K, CacheNode<V>> map;
    protected int youngCapacity, oldCapacity;
    protected int youngSize, oldSize;
    protected CacheNode<V> leftMargin = new CacheNode<>(null, null, null);
    protected CacheNode<V> middleBoundary = new CacheNode<>(null, null, null);
    protected CacheNode<V> rightMargin = new CacheNode<>(null, null, null);
    public V get(K key){

    }
    public void put(K key, V value){

    }

    @Override
    public String toString() {
        return super.toString();
    }

    public InnoDBLikeCache(int youngCapacity, int oldCapacity) {
        this.youngCapacity = youngCapacity;
        this.oldCapacity = oldCapacity;
        map = new HashMap<>(getYoungCapacity()+ getOldCapacity());
        //互认
        leftMargin.next = middleBoundary;
        middleBoundary.previous = leftMargin;
        middleBoundary.next = rightMargin;
        rightMargin.previous = middleBoundary;
    }
    public static class CacheNode<V> { //代码复用是世界上最大的罪恶，它让人期望落空，它让人陷入空想。
        public V value;
        public CacheNode<V> previous;
        public CacheNode<V> next;

        public CacheNode(V value, CacheNode<V> previous, CacheNode<V> next) {
            this.value = value;
            this.previous = previous;
            this.next = next;
        }

        @Override
        public String toString() {
            return "CacheNode{" +
                    "value=" + value +
                    ", prev=" + previous.value +
                    ", next=" + next.value +
                    '}';
        }
    }


    public int getYoungCapacity() {
        return youngCapacity;
    }

    public int getOldCapacity() {
        return oldCapacity;
    }
}
//#include "BidirectionalLinkedNodes.java"