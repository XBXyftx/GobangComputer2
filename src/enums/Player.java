package enums;

/**
 * 枚举类 Player 表示五子棋游戏中的两个玩家。
 * 每个玩家都有一个唯一的标识符，并且可以通过 opposite() 方法获取对方玩家。
 */
public enum Player {
    /**
     * 人类玩家，标识符为 1
     */
    HUMAN_PLAYER(1),
    /**
     * 计算机玩家，标识符为 -1
     */
    COMPUTER_PLAYER(-1);

    private final int value;

    /**
     * 构造方法，用于设置玩家的标识符
     *
     * @param value 玩家的唯一标识符
     */
    Player(int value) {
        this.value = value;
    }

    /**
     * 获取玩家的唯一标识符
     *
     * @return 玩家的标识符
     */
    public int getValue() {
        return value;
    }

    /**
     * 获取对方玩家
     *
     * @return 对方玩家
     */
    public Player opposite() {
        return this == HUMAN_PLAYER ? COMPUTER_PLAYER : HUMAN_PLAYER;
    }
}



