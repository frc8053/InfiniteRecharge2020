/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class driveTrain extends SubsystemBase {
  /**
   * Creates a new driveTrain.
   */
  private final WPI_VictorSPX m_frontLeft = new WPI_VictorSPX(0);
  private final WPI_VictorSPX m_frontRight = new WPI_VictorSPX(1);
  private final WPI_VictorSPX m_backLeft = new WPI_VictorSPX(2);
  private final WPI_VictorSPX m_backRight = new WPI_VictorSPX(3);
  private final SpeedControllerGroup driveLeft = new SpeedControllerGroup(m_frontLeft, m_backLeft);
  private final SpeedControllerGroup driveRight = new SpeedControllerGroup(m_frontRight, m_backRight);
  private final DifferentialDrive myRobot = new DifferentialDrive(driveLeft, driveRight);
  public driveTrain() {

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  public void driveArcade(double left, double right)
  {
    myRobot.arcadeDrive(left, right);
  }
  public void driveTank(double left, double right)
  {
    myRobot.tankDrive(left, right);
  }
}
