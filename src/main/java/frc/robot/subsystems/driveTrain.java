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

public class DriveTrain extends SubsystemBase {
  /**
   * Creates a new driveTrain.
   */
  private WPI_VictorSPX m_frontLeft;
  private WPI_VictorSPX m_frontRight;
  private WPI_VictorSPX m_backLeft;
  private WPI_VictorSPX m_backRight;
  private SpeedControllerGroup driveLeft;
  private SpeedControllerGroup driveRight;
  private DifferentialDrive myRobot;;
  public DriveTrain() {
    m_frontLeft = new WPI_VictorSPX(0);
    m_frontRight = new WPI_VictorSPX(1);
    m_backLeft = new WPI_VictorSPX(2);
    m_backRight = new WPI_VictorSPX(3);

    driveLeft = new SpeedControllerGroup(m_frontLeft, m_backLeft);
    driveRight = new SpeedControllerGroup(m_frontRight, m_backRight);
    myRobot = new DifferentialDrive(driveLeft, driveRight);
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
