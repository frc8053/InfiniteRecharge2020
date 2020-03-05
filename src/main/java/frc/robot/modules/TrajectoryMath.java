package frc.robot.modules;

public class TrajectoryMath {
    private static final double ANGLE = 30;
    private static final double ANGLE_RADS = Math.toRadians(ANGLE);
    private static final double SHOOTER_HEIGHT = 1.6;
    private static final double GRAVITY = 32.1522;
    private static final double GOAL_HEIGHT = 8.6;
    public static double getVelocityFromDistance(double distance)
    {
        double a = GRAVITY/2*Math.pow(distance,2);
        double b = (Math.sin(ANGLE_RADS) * Math.cos(ANGLE_RADS) * distance) - (Math.pow(Math.cos(ANGLE_RADS), 2) * (GOAL_HEIGHT-SHOOTER_HEIGHT));
        return Math.sqrt(a/b);
    }
}
