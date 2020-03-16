/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.controller.PIDController;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static double HIGH_SPEED = 0.85;
  public static double MID_SPEED = 0.6;
  public static double LOW_SPEED = 0.4;
  
  public static final class Elevator {
    public static PIDController PID = new PIDController(1, 0, 0);
    public static double HEIGHT = 25;
    public static double DISTANCE_PER_PULSE = (0.962 * Math.PI) / 2048;

  }

  public static final class Climber {
    public static double HEIGHT;
    public static double SPEED;

    public static double clamp(double val) {
      return Math.max(Elevator.HEIGHT, Math.min(70, val));
    }
  }

  public static final class ColorWheel {
    public static double HEIGHT = 36.5;
  }
  
  public static final class Drive {
    public static double DISTANCE_PER_PULSE = (6 * Math.PI / 2048);
  }

  public static final class DrivePid {
    public static double P_TURN = 0.15;
    public static double I_TURN = 0.014;
    public static double D_TURN = 0.0242;
    public static double P_BALL = 0.05;
    public static double I_BALL = 0.007;
    public static double D_BALL = 0.035;
    public static double P_DISTANCE = 0.3;
    public static double I_DISTANCE = 0.014;
    public static double D_DISTANCE = 0.06;
    public static double TURN_TOLERANCE = 0.5;
    public static double SPEED_TOLERANCE = 3;
  }

  public static final class IntakeConstant {
    public static double INTAKE_SPEED = 0.7;
    public static double CONVEYOR_SPEED = 0.5;
  }

  public static final class Shoot {
    public static double SHOOTRATE = 1.0 / 2048.0;
    public static double SLOW_RPM = 1000;
    public static double FAST_RPM = 3000;
    public static double P_LEFT_SHOOTER = 0.007;
    public static double I_LEFT_SHOOTER = 0.0005;
    public static double D_LEFT_SHOOTER = 0;
    public static double P_RIGHT_SHOOTER = 0.007;
    public static double I_RIGHT_SHOOTER = 0.00007;
    public static double D_RIGHT_SHOOTER = 0;
    public static double SHOOT_TOLERANCE = 50;
  }

  public static final class Climb {
    public static double CLIMB_SPEED = 0.8;
  }
}


