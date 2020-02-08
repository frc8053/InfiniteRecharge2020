/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
* Add your docs here.
*/
public class Climber extends SubsystemBase {

  //Instance Variables
  private WPI_VictorSPX climbWinch;

  /**
   * Instantiate Climber Subsystem.g
   */
  public Climber() {
    climbWinch = new WPI_VictorSPX(9);
  }
  

  @Override
  public void periodic() {
    // This method will be called once per scheduler run    
  }

  /**
   * Controls the winch for the climber setup.
   * @param speed sets the speed of the motor [-1, 1]
    */
  public void climbWinch(double speed) {
    climbWinch.set(ControlMode.PercentOutput, speed);
  }
}
