package code;

public class WaterSortState implements State {

    static int numOfBottles;
    static int bottleCapacity;
    char[][] bottles;

    public WaterSortState(String initialState) {
        String[] lines = initialState.split(";");
        this.numOfBottles = Integer.parseInt(lines[0]);
        this.bottleCapacity = Integer.parseInt(lines[1]);
        this.bottles = new char[numOfBottles][bottleCapacity];
        for (int i = 0; i < numOfBottles; i++) {
            String[] bottle = lines[i].split(",");
            for (int j = bottleCapacity - 1; j >= 0; j--) {
                this.bottles[i][j] = bottle[j].charAt(0);
            }
        }
    }

    public Object getDS() {
        return bottles;
    }

    public WaterSortState(char[][] bottles) {
        this.bottles = bottles;
    }

    public String toString() {
        //print bottle contents
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numOfBottles; i++) {
            for (int j = 0; j < bottleCapacity; j++) {
                sb.append(bottles[i][j]);
                if (j != bottleCapacity - 1) {
                    sb.append(",");
                }
            }
            if (i != numOfBottles - 1) {
                sb.append(";");
            }
        }
        return sb.toString();
    }
}
