/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ColorWheel extends SubsystemBase {

  WPI_VictorSPX spinner;

  /**
   * The color wheel subsytem containing the color sensor and motor necessary to turn
   * the control panal.
   */
  
  public ColorWheel() {
    spinner = new WPI_VictorSPX(11);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
