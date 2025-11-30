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
        angle = (angle % 360 + 360) % 360;

        if (angle >= 337.5 || angle < 22.5) return NORTH;       // 0°
        if (angle >= 22.5 && angle < 67.5) return NORTHWEST;    // 45°
        if (angle >= 67.5 && angle < 112.5) return WEST;        // 90°
        if (angle >= 112.5 && angle < 157.5) return SOUTHWEST;  // 135°
        if (angle >= 157.5 && angle < 202.5) return SOUTH;      // 180°
        if (angle >= 202.5 && angle < 247.5) return SOUTHEAST;  // 225°
        if (angle >= 247.5 && angle < 292.5) return EAST;       // 270°
        return NORTHEAST; // 292.5-337.5° (315°)
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
