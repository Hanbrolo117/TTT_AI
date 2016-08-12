/**
 * Created by CptAmerica on 8/11/16.
 */
public class MovePoint {

    private int i;
    private int j;
    private int value;
    private int depth;

    /**
     * The MovePoint Constructor.
     * @param i The ith position of this move.
     * @param j The jth position of this move.
     * @param value The Move value to the Player of this MovePoint.
     */
    public MovePoint(int i, int j, int value){//int depth
        this.i = i;
        this.j = j;
        this.value = value;
    }

    /**
     * Get the row position of this MovePoint.
     * @return the row position of this MovePoint.
     */
    public int getI() {
        return i;
    }

    /**
     * Get the column position of this MovePoint.
     * @return the column position of this MovePoint.
     */
    public int getJ() {
        return j;
    }

    /**
     * Get the Move Value of this MovePoint.
     * @return The value of this MovePoint.
     */
    public int getValue() {
        return value;
    }
}
