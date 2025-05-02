package pl.gatomek.hanoi;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Stack;

@RequiredArgsConstructor
@Getter
class HanoiTower {
    private final String name;

    @Getter(AccessLevel.NONE)
    final private Stack<Integer> stack = new Stack<>();

    public void push(Integer v) {
        stack.push(v);
    }

    public String toState() {
        StringBuilder sb = new StringBuilder( 5);
        sb.append( name);
        stack.forEach(sb::append);
        return sb.toString();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public Integer peek() {
        return stack.peek();
    }

    public Integer pop() {
        return stack.pop();
    }
}
