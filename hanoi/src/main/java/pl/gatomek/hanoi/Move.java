package pl.gatomek.hanoi;

record Move(Integer v, String from, String to, String state) {
    public static Move of(Integer v, String from, String to, String state) {
        return new Move(v, from, to, state);
    }

    @Override
    public String toString() {
        return v + " : " + from + "->" + to + " | " + state;
    }
}
