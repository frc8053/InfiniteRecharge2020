/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Drive;

public class DriveTrain extends SubsystemBase {
  /**
   * Creates a new driveTrain.
   */
  private final WPI_VictorSPX frontLeft;
  private final WPI_VictorSPX frontRight;
  private final WPI_VictorSPX backLeft;
  private final WPI_VictorSPX backRight;

  private final SpeedControllerGroup leftDrive;
  private final SpeedControllerGroup rightDrive;
  private final DifferentialDrive myRobot;

  private final Encoder leftEncoder;
  private final Encoder rightEncoder;

  private final Gyro gyro;

  /**
   * Initalizes drive motors and helper classes.
   * </p>
   */

  public DriveTrain() {
    frontLeft = new WPI_VictorSPX(0);
    frontRight = new WPI_VictorSPX(1);
    backLeft = new WPI_VictorSPX(2);
    backRight = new WPI_VictorSPX(3);
    frontLeft.setInverted(true);
    frontRight.setInverted(true);
    backLeft.setInverted(true);
    backRight.setInverted(true);

    leftDrive = new SpeedControllerGroup(frontLeft, backLeft);
    rightDrive = new SpeedControllerGroup(frontRight, backRight);
    myRobot = new DifferentialDrive(leftDrive, rightDrive);

    leftEncoder = new Encoder(0, 1);
    leftEncoder.setDistancePerPulse(Drive.DISTANCE_PER_PULSE);
    rightEncoder = new Encoder(2, 3);
    rightEncoder.setDistancePerPulse(Drive.DISTANCE_PER_PULSE);

    gyro = new ADXRS450_Gyro(SPI.Port.kMXP);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void arcadeDrive(final double left, final double right) {
    myRobot.arcadeDrive(left, right);
  }

  public void tankDrive(final double left, final double right) {
    myRobot.tankDrive(left, right);
  }

  public void leftEncoderReset() {
    leftEncoder.reset();
  }

  public double leftEncoderValue() {
    return leftEncoder.getDistance();
  }

  /**
   * returns gyro value.
   * @return the rotational value of the gyroscope
   */

  public double getGyro() {
    if (gyro.getAngle() > 180) {
      return gyro.getAngle() - 360;
    } else {
      return gyro.getAngle();
    }
    
  }

}