package Design;

public class MinStack {
    public static void main(String[] args) {
        MinStack minStack = new MinStack();
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        minStack.getMin(); // return -3
        minStack.pop();
        minStack.top(); // return 0
        minStack.getMin(); // return -2
        // System.out.println("Min Stack : "+minStack);
    }

    /*
     * 
     * https://leetcode.com/explore/interview/card/top-interview-questions-easy/98/
     * design/562/
     * 
     * Design a stack that supports push, pop, top, and retrieving the minimum
     * element in constant time.
     * 
     * Implement the MinStack class:
     * 
     * MinStack() initializes the stack object.
     * void push(int val) pushes the element val onto the stack.
     * void pop() removes the element on the top of the stack.
     * int top() gets the top element of the stack.
     * int getMin() retrieves the minimum element in the stack.
     * You must implement a solution with O(1) time complexity for each function.
     * 
     * 
     * 
     * Example 1:
     * 
     * Input
     * ["MinStack","push","push","push","getMin","pop","top","getMin"]
     * [[],[-2],[0],[-3],[],[],[],[]]
     * 
     * Output
     * [null,null,null,null,-3,null,0,-2]
     * 
     * Explanation
     * MinStack minStack = new MinStack();
     * minStack.push(-2);
     * minStack.push(0);
     * minStack.push(-3);
     * minStack.getMin(); // return -3
     * minStack.pop();
     * minStack.top(); // return 0
     * minStack.getMin(); // return -2
     * 
     * 
     * Constraints:
     * 
     * -231 <= val <= 231 - 1
     * Methods pop, top and getMin operations will always be called on non-empty
     * stacks.
     * At most 3 * 104 calls will be made to push, pop, top, and getMin.
     */

    private int top = -1;
    private int[] stack;
    private int[] minStack;
    private int maxSize = 30000;

    public MinStack() {
        stack = new int[maxSize];
        minStack = new int[maxSize];
    }

    public void push(int val) {
        top++;
        stack[top] = val;
        if (top == 0) {
            minStack[top] = val;
        } else {
            minStack[top] = Math.min(val, this.minStack[top - 1]);
        }
    }

    public void pop() {
        if (top >= 0) {
            top--;
        }
    }

    public int top() {
        if (top >= 0) {
            return stack[this.top];
        }
        throw new RuntimeException("Empty Stack");
    }

    public int getMin() {
        if (top >= 0) {
            return minStack[this.top];
        }
        throw new RuntimeException("Empty Stack");
    }

    public boolean isEmpty() {
        return this.top == -1;
    }

    // public void storeInSortedOrder(int val) {
    // if (val <= this.minElement) {
    // for (int i = 0; i < this.stack.length; i++) {
    // int nextIndex = i + 1;
    // int temp = this.stack[i];
    // // this.stackArray[i] = this.s
    // }
    // }
    // }
}
