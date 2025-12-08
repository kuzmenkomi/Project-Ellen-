package sk.tuke.kpi.oop.game;

public enum Direction {
    NORTH(0, 1),
    EAST(1, 0),
    SOUTH(0, -1),
    WEST(-1, 0),
    NORTHEAST(1, 1),
    SOUTHEAST(1, -1),
    SOUTHWEST(-1, -1),
    NORTHWEST(-1, 1),
    NONE(0, 0);

    static {
        NORTH.angle = 0;
        NORTHWEST.angle = 45;
        WEST.angle = 90;
        SOUTHWEST.angle = 135;
        SOUTH.angle = 180;
        SOUTHEAST.angle = 225;
        EAST.angle = 270;
        NORTHEAST.angle = 315;
    }

    private float angle;
    private final int dx, dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public static Direction fromAngle(float angle) {
        float normalAngle = (angle % 360 + 360) % 360;
        Direction[] directions = {
            NORTH,      // 0° (и 360°)
            NORTHWEST,  // 45°
            WEST,       // 90°
            SOUTHWEST,  // 135°
            SOUTH,      // 180°
            SOUTHEAST,  // 225°
            EAST,       // 270°
            NORTHEAST   // 315°
        };

        int index = (int) ((normalAngle + 22.5) / 45);

        if (index >= 8) index = 0;
        return directions[index];
    }

    public int getDx() {return dx;}
    public int getDy() {return dy;}
    public float getAngle() {return angle;}

    public Direction combine(Direction other) {
        if (this == other) return this;

        int newX = (getDx() == other.getDx()) ? getDx() : getDx() + other.getDx();
        int newY = (getDy() == other.getDy()) ? getDy() : getDy() + other.getDy();

        Direction direction = NONE;

        for (Direction value: Direction.values()) {
            if (value.getDx() == newX && value.getDy() == newY) { direction = value; }
        }
        return direction;
    }
}
