package pl.gatomek.hanoi;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Main {
    private static final HanoiTower towerA = new HanoiTower("A");
    private static final HanoiTower towerB = new HanoiTower("B");
    private static final HanoiTower towerC = new HanoiTower("C");

    private static final Set<String> visited = new HashSet<>();
    private static final List<Move> moves = new LinkedList<>();
    private static int solutionCount = 0;
    private static Integer minMoves = Integer.MAX_VALUE;
    private static String minSolution = "";
    private static Integer maxMoves = Integer.MIN_VALUE;
    private static String maxSolution = "";

    public static void main(String[] args) {
        Thread shutdownThread = new Thread(() -> {
            System.out.println("Found Min solution:");
            System.out.println(minSolution);
            System.out.println("Found Max solution:");
            System.out.println(maxSolution);
        });

        Runtime.getRuntime().addShutdownHook(shutdownThread);

        System.out.println("Hanoi Towers");

        //towerA.push(4);
        towerA.push(3);
        towerA.push(2);
        towerA.push(1);

        String state = calcState();

        visited.add(state);
        flow();
        visited.remove(state);
    }

    private static void flow() {
        tryMove(towerA, towerB);
        tryMove(towerA, towerC);

        tryMove(towerB, towerC);
        tryMove(towerB, towerA);

        tryMove(towerC, towerB);
        tryMove(towerC, towerA);
    }

    private static String calcState() {
        return "." + towerA.toState() + "." + towerB.toState() + "." + towerC.toState();
    }

    private static void tryMove(HanoiTower from, HanoiTower to) {
        if (canMove(from, to) && canVisit(from, to))
            move(from, to);
    }

    private static boolean canMove(HanoiTower from, HanoiTower to) {
        if (from.isEmpty())
            return false;

        if (to.isEmpty())
            return true;

        return from.peek() < to.peek();
    }

    private static boolean canVisit(HanoiTower from, HanoiTower to) {
        to.push(from.pop());
        String state = calcState();
        from.push(to.pop());

        return !visited.contains(state);
    }

    private static void move(HanoiTower from, HanoiTower to) {
        Integer v = from.pop();
        to.push(v);

        String state = calcState();
        Move m = Move.of(v, from.getName(), to.getName(), state);
        moves.add(m);
        visited.add(state);

        boolean success = solutionFound();
        if (!success)
            flow();

        visited.remove(state);
        moves.remove(m);
        from.push(to.pop());
    }

    private static boolean solutionFound() {
        boolean success = towerA.isEmpty() && towerB.isEmpty();
        if (success) {
            ++solutionCount;
            int count = moves.size();

            if (count < minMoves) {
                System.out.println(solutionCount + ". " + count + " moves");
                minSolution = movesToString();
                minMoves = count;
            }

            if (count > maxMoves) {
                maxSolution = movesToString();
                maxMoves = count;
            }
        }

        return success;
    }

    private static String movesToString() {
        StringBuilder sb = new StringBuilder(2 * moves.size());
        moves.forEach(m -> sb.append(m).append("\n"));
        return sb.toString();
    }
}
