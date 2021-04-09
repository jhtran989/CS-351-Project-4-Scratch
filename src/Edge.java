public class Edge {
    private final Cell CELL_ONE;
    private final Cell CELL_TWO;
    private final boolean VERTICAL;
    private final boolean HORIZONTAL;

    public Edge(Cell cellOne, Cell cellTwo,
                boolean vertical, boolean horizontal) {
        this.CELL_ONE = cellOne;
        this.CELL_TWO = cellTwo;
        this.VERTICAL = vertical;
        this.HORIZONTAL = horizontal;
    }

    public void takeDownVerticalWall(){
        CELL_ONE.setRightWall(false);
        CELL_TWO.setLeftWall(false);
    }

    public void takeDownHorizontalWall(){
        CELL_ONE.setDownWall(false);
        CELL_TWO.setUpWall(false);
    }

    public Cell getCELL_ONE() {
        return CELL_ONE;
    }

    public Cell getCELL_TWO() {
        return CELL_TWO;
    }

    public boolean isVERTICAL() {
        return VERTICAL;
    }

    public boolean isHORIZONTAL() {
        return HORIZONTAL;
    }
}
