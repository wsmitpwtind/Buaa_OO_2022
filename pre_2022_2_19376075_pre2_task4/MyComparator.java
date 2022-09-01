import java.util.Comparator;

class MyComparator implements Comparator<Equipment> {
    public int compare(Equipment o1, Equipment o2) {
        if (o1.getPrice() > o2.getPrice()) {
            return 1;
        } else if (o1.getPrice() < o2.getPrice()) {
            return -1;
        } else {
            return o1.getId() - o2.getId();
        }
    }
}