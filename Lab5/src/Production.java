import java.util.List;
import java.util.Objects;

public record Production(String leftHandSide, List<String> rightHandSide, int index) {

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(leftHandSide).append(" -> ");
        rightHandSide.forEach(stringBuilder::append);
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Production that = (Production) o;
        return index == that.index;
    }

    @Override
    public int hashCode() {
        return Objects.hash(index);
    }
}
