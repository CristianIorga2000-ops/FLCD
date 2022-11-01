package util;

public class Truple<A, B, C> {
    public A fst;
    public B snd;
    public C trd;

    public Truple(A fst, B snd, C trd) {
        this.fst = fst;
        this.snd = snd;
        this.trd = trd;
    }

    @Override
    public String toString() {
        return fst + " --- " + snd + " --- " + trd;
    }
}
