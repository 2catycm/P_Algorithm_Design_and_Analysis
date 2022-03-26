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
    protected HashMap<K, CacheNode<K, V>> map;
    protected int youngSize, oldSize;
    public InnoDBLikeCache(int youngSize, int oldSize) {
        this.youngSize = youngSize;
        this.oldSize = oldSize;
        map = new HashMap<>(getYoungSize()+getOldSize());
    }

    public int getYoungSize() {
        return youngSize;
    }

    public int getOldSize() {
        return oldSize;
    }
    //重构：或许CacheNode不需要存K
    public static class CacheNode<K, V> { //代码复用是世界上最大的罪恶，它让人期望落空，它让人陷入空想。
        public K key;
        public V value;
        public CacheNode<K, V> prev;
        public CacheNode<K, V> next;

        public CacheNode(K key, V value, CacheNode<K, V> prev, CacheNode<K, V> next) {
            this.key = key;
            this.value = value;
            this.prev = prev;
            this.next = next;
        }

        @Override
        public String toString() {
            return "CacheNode{" +
                    "key=" + key +
                    ", value=" + value +
                    ", prev=" + prev.key +
                    ", next=" + next.key +
                    '}';
        }
    }
}
//#include "BidirectionalLinkedNodes.java"