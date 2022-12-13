import java.util.List;

public class Production {
    public String leftHandSide;
    public List<String> rightHandSide;

    public Production(String leftHandSide, List<String> rightHandSide) {
        this.leftHandSide = leftHandSide;
        this.rightHandSide = rightHandSide;
    }
    @Override
    public String toString(){
        return leftHandSide + " -> " + rightHandSide;
    }
}
