/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {

  private WPI_VictorSPX shooterLeft;
  private WPI_VictorSPX shooterRight;

  private SpeedControllerGroup shooterGroup;

  /**
   * Creates a new Shooter.
   */
  public Shooter() {

    shooterLeft = new WPI_VictorSPX(7);
    shooterRight = new WPI_VictorSPX(8);

    shooterGroup = new SpeedControllerGroup(shooterLeft, shooterRight);

    shooterLeft.setInverted(true);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void shoot(double speed) {
    shooterGroup.set(speed);
  }
}
