import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

public class ParseOutput {
    public static class Node {
        public Node(String info, int parent, int rightSibling) {
            this.info = info;
            this.parent = parent;
            this.rightSibling = rightSibling;
        }

        String info;
        int parent;
        int rightSibling;

        @Override
        public String toString() {
            return "Node{" +
                    ", info='" + info + '\'' +
                    ", parent=" + parent +
                    ", rightSibling=" + rightSibling +
                    '}';
        }
    }

    public static class ParentSiblingTable extends ArrayList<Node> {
        @Override
        public String toString() {
            StringBuilder finalString = new StringBuilder(
            );
            for (Node tableRow : this) {
                finalString.append(this.indexOf(tableRow))
                        .append(" -> ")
                        .append(tableRow).
                        append("\n");
            }

            return finalString.toString();
        }
        
        public int getLastSiblingOfParent(int parentId){
            ListIterator<Node> listIterator = this.listIterator(this.size());
            while(listIterator.hasPrevious()){
                Node iteratedNode = listIterator.previous();
                if (parentId == iteratedNode.parent) return this.indexOf(iteratedNode);
            }
            return 0;
        }

        public int getNodeId(String nodeInfo){
            for(Node n : this){
                if (Objects.equals(n.info, nodeInfo)) return this.indexOf(n);
            }
            return -1;
        }
    }


    public static ParentSiblingTable formatOutputTable(List<Production> productions, List<Integer> parserOutput) {
        ParentSiblingTable table = new ParentSiblingTable();

        String rootName = productions.get(0).leftHandSide();
        table.add(new Node(rootName, 0, 0));

        for (Integer productionId : parserOutput) {
            Production production = productions.get(productionId);
            String parentName = production.leftHandSide();
            int parentId = table.getNodeId(parentName);

            for (String string : production.rightHandSide()) {
                table.add(new Node(string, parentId, table.getLastSiblingOfParent(parentId)));
            }
        }

        return table;
    }

}
