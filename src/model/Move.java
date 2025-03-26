package model;

/**
 * 类 Move 表示五子棋游戏中的一个走法。
 * 包含行坐标和列坐标，用于表示玩家在棋盘上的落子位置。
 */
public class Move {
    private final int row;
    private final int col;

    /**
     * 构造方法，用于设置走法的行坐标和列坐标
     *
     * @param row 行坐标
     * @param col 列坐标
     */
    public Move(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * 获取行坐标
     *
     * @return 行坐标
     */
    public int getRow() {
        return row;
    }

    /**
     * 获取列坐标
     *
     * @return 列坐标
     */
    public int getCol() {
        return col;
    }

    /**
     * 重写 toString 方法，方便调试和日志输出
     *
     * @return 走法的字符串表示
     */
    @Override
    public String toString() {
        return "Move{" +
                "row=" + row +
                ", col=" + col +
                '}';
    }
}



