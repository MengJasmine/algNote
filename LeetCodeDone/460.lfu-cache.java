import java.util.*;

/*
 * @lc app=leetcode id=460 lang=java
 *
 * [460] LFU Cache
 */

// @lc code=start
class LFUCache {
  Map<Integer, MyNode> keyToNodeMap;
  Map<Integer, MyCountList> countToListMap;
  int capacity;
  int minCount;
  int size;

  public LFUCache(int capacity) {
    this.keyToNodeMap = new HashMap<>();
    this.countToListMap = new HashMap<>();
    this.minCount = 0;
    this.capacity = capacity;

  }

  public int get(int key) {
    if (!keyToNodeMap.containsKey(key)) {
      return -1;
    }
    MyNode cur = keyToNodeMap.get(key);
    update(cur);
    return cur.val;
  }

  private void update(MyNode node) {
    MyCountList oldList = this.countToListMap.get(node.count);
    oldList.remove(node);
    if (oldList.size == 0 && node.count == this.minCount) {
      this.minCount++;
    }
    node.count = node.count + 1;
    MyCountList newList = this.countToListMap.getOrDefault(node.count, new MyCountList());
    newList.addAtHead(node);
    this.countToListMap.put(node.count, newList);

  }

  public void put(int key, int value) {
    if (this.capacity == 0) return;
    if (this.keyToNodeMap.containsKey(key)) {
      MyNode curNode = this.keyToNodeMap.get(key);
      curNode.val = value;
      update(curNode);
    } else {
      MyNode newNode = new MyNode(key, value);
      keyToNodeMap.put(key, newNode);
      if (this.size == this.capacity) {
        MyCountList lastCountList = this.countToListMap.get(this.minCount);
        this.keyToNodeMap.remove(lastCountList.removeLast().key);
        this.size--;
      }
      this.minCount = 1;
      MyCountList curCountList = this.countToListMap.getOrDefault(newNode.count, new MyCountList());
      curCountList.addAtHead(newNode);
      this.countToListMap.put(newNode.count, curCountList);
      this.size++;
    }
  }
}

class MyNode {
  int key;
  int val;
  MyNode prev;
  MyNode next;
  int count;

  MyNode(int key, int val) {
    this.key = key;
    this.val = val;
    this.prev = null;
    this.next = null;
    this.count = 1;
  }
}

class MyCountList {
  MyNode head;
  MyNode tail;
  int size;

  MyCountList() {
    this.head = new MyNode(0, 0);
    this.tail = new MyNode(0, 0);
    this.head.next = this.tail;
    this.tail.prev = this.head;
    this.size = 0;
  }

  public void addAtHead(MyNode node) {
    this.head.next.prev = node;
    node.next = this.head.next;
    node.prev = this.head;
    this.head.next = node;
    this.size++;
  }

  public void remove(MyNode node) {
    MyNode prev = node.prev;
    MyNode next = node.next;
    prev.next = next;
    next.prev = prev;
    this.size--;
  }

  public MyNode removeLast() {
    if (this.size == 0) {
      return null;
    }
    MyNode removeNode = this.tail.prev;
    remove(removeNode);
    return removeNode;
  }

  public static void main(String[] args) {
    LFUCache lfu = new LFUCache(2);
    lfu.put(1,1);
    lfu.put(2,2);
    lfu.get(1);
    lfu.put(3, 3);
    lfu.get(2);
    lfu.get(3);
    lfu.put (4,4);
    lfu.get (1);
    lfu.get (3);
    lfu.get(4);
  }
}

/**
 * Your LFUCache object will be instantiated and called as such: LFUCache obj =
 * new LFUCache(capacity); int param_1 = obj.get(key); obj.put(key,value);
 */
// @lc code=end
