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
    protected HashMap<K, CacheNode<K>> nodeMap;
    protected HashMap<K, V> valueMap;
    protected HashMap<K, Boolean> isOlds;
    protected int youngCapacity, oldCapacity;
    protected int youngSize, oldSize;

    //左边young，右边old
    protected CacheNode<K> leftMargin = new CacheNode<>(null, null, null);
    protected CacheNode<K> middleBoundary = new CacheNode<>(null, null, null);
    protected CacheNode<K> rightMargin = new CacheNode<>(null, null, null);
    public V get(K key){
        return getOrDefault(key, null);
    }
    public V getOrDefault(K key, V defaultValue){
        final var keyNode = nodeMap.get(key);
        if (keyNode==null) {
            assert valueMap.get(key)==null;
            assert isOlds.get(key)==null;
            return defaultValue;
        }
        final var value = valueMap.get(key);
        assert value!=null;
        assert isOlds.get(key)!=null;
        linkYoungAfterAccess(keyNode);
        return value;
    }

    private void linkYoungAfterAccess(CacheNode<K> keyNode) {
        assert keyNode!=null;
        //delete
        keyNode.previous.next = keyNode.next;
        keyNode.next.previous = keyNode.previous;
        //insert at left
        final var previousFirst = leftMargin.next;
        previousFirst.previous = keyNode;
        leftMargin.next = keyNode;
        keyNode.previous = leftMargin;
        keyNode.next = previousFirst;
        //size re-computation and evict from young to old if full.
        final var isOld = isOlds.get(keyNode.value);
        assert isOld!=null;
        if (isOld){ //如果是从 old 提携到 young
            isOlds.put(keyNode.value, false);
            youngSize++;
            oldSize--;
            if (youngSize>youngCapacity){
                assert youngSize == youngCapacity+1;
                //撤销变化
                youngSize--;
                oldSize++;
                //把young中最老的倒到old中
                final var victim = middleBoundary.previous;
                victim.previous.next = victim.next;
                victim.next.previous = victim.previous;
                final var neighbourInOld = middleBoundary.next;
                neighbourInOld.previous = middleBoundary.next = victim;
                victim.previous = middleBoundary;
                victim.next = neighbourInOld;
                isOlds.put(victim.value, true);
            }
        } //else do nothing.
    }

    public void put(K key, V value){
        final var keyNode = nodeMap.get(key);
        if (keyNode==null)
            putNew(key, value);
        else
            putModify(key, value);
    }

    private void putModify(K key, V value) {
        valueMap.put(key, value);
        linkYoungAfterAccess(nodeMap.get(key));
    }

    private void putNew(K key, V value) {
//        new CacheNode<K>(key, ,)
        valueMap.put(key, value);
        if (youngSize>=youngCapacity){ //young区已满
            // put the pair in head of the old list
            isOlds.put(key, true);
            //链接层面
            final var newNode = new CacheNode<K>(key, middleBoundary, middleBoundary.next);
            middleBoundary.next.previous = newNode;
            middleBoundary.next = newNode;
            //map层面
            nodeMap.put(key, newNode);
            if (++oldSize>oldCapacity){
                //evict the oldest one.
                evictOldestNode();
            }
        }else{
            // put the pair in head of the young list
            isOlds.put(key, false);
            //链接层面
            final var newNode = new CacheNode<K>(key, leftMargin, leftMargin.next);
            leftMargin.next.previous = newNode;
            leftMargin.next = newNode;
            //map层面
            nodeMap.put(key, newNode);
            youngSize++;
            assert youngSize<=youngCapacity;
        }
    }

    private void evictOldestNode() {
        //size 层面
        assert oldSize>oldCapacity;
        oldSize--; //撤销变化
        //指针层面
        final var victim = rightMargin.previous;
        victim.previous.next = victim.next;
        victim.next.previous = victim.previous;
        //map层面
        isOlds.remove(victim.value);
        valueMap.remove(victim.value);
        nodeMap.remove(victim.value);
    }

    @Override
    public String toString() {
        final var stringBuilder = new StringBuilder();
        for (var it = leftMargin.next; it!=middleBoundary; it = it.next){
            stringBuilder.append(it.value).append(":")
                    .append(valueMap.get(it.value)).append(" ");
        }
        for (var it = middleBoundary.next; it!=rightMargin; it = it.next){
            stringBuilder.append(it.value).append(":")
                    .append(valueMap.get(it.value)).append(" ");
        }
        return stringBuilder.toString();
    }

    public InnoDBLikeCache(int youngCapacity, int oldCapacity) {
        this.youngCapacity = youngCapacity;
        this.oldCapacity = oldCapacity;
        nodeMap = new HashMap<>(getYoungCapacity()+ getOldCapacity());
        valueMap = new HashMap<>(getYoungCapacity()+ getOldCapacity());
        isOlds = new HashMap<>(getYoungCapacity()+ getOldCapacity());
        //互认
        leftMargin.next = middleBoundary;
        middleBoundary.previous = leftMargin;
        middleBoundary.next = rightMargin;
        rightMargin.previous = middleBoundary;
    }
    public static class CacheNode<Item> { //代码复用是世界上最大的罪恶，它让人期望落空，它让人陷入空想。
        public Item value;
        public CacheNode<Item> previous;
        public CacheNode<Item> next;

        public CacheNode(Item value, CacheNode<Item> previous, CacheNode<Item> next) {
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