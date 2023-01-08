import java.util.List;

public class Production {
    public final String leftHandSide;
    public final List<String> rightHandSide;
    private int index;

    public Production(String leftHandSide, List<String> rightHandSide) {
        this.leftHandSide = leftHandSide;
        this.rightHandSide = rightHandSide;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString(){
        return leftHandSide + " -> " + rightHandSide;
    }
}
