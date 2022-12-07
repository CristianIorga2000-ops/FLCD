public class Production {
    public String leftHandSide;
    public String rightHandSide;

    public Production(String leftHandSide, String rightHandSide) {
        this.leftHandSide = leftHandSide;
        this.rightHandSide = rightHandSide;
    }
    @Override
    public String toString(){
        return leftHandSide + " -> " + rightHandSide;
    }
}
