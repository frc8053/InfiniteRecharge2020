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
    public static double PTURN = 0.15;
    public static double ITURN = 0.014;
    public static double DTURN = 0.0238;
    public static double TURN_TOLERANCE = 0.5;
    public static double SPEED_TOLERANCE = 3;
  }

  public static final class IntakeConstant {
    public static double INTAKE_SPEED = 0.7;
    public static double CONVEYOR_SPEED = 0.7;
  }

  public static final class Shoot {
    public static double SHOOT_LOW = 0.2;
    public static double SHOOT_HIGH = 0.8;
    public static double SHOOTRATE = 1.0 / 2048.0;
    public static double SLOW_RPM = 1000;
    public static double FAST_RPM = 3000;
    public static double KS = 1;
    public static double KV = 1;
    public static double PSHOOT = 1;
    public static double ISHOOT = 0;
    public static double DSHOOT = 0;
    public static double SHOOT_TOLERANCE = 10;
  }

  public static final class Climb {
    public static double CLIMB_SPEED = 0.8;
  }

  public static final class DioPorts {
    public static int[] DRIVE_RIGHT_ENCODER = {4, 5};
    public static int[] DRIVE_LEFT_ENCODER = {6, 7};
    public static int[] SHOOT_LEFT_ENCODER = {0, 1};
    public static int[] SHOOT_RIGHT_ENCODER = {2, 3};
    public static int[] ELEVATOR_ENCODER = {8, 9};
    public static int INTAKE_IR_SWITCH = 10;
  }
}


