/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
* Add your docs here.
*/
public class Winch extends SubsystemBase {
  //Instance Variables
  private WPI_VictorSPX winch;

  /**
   * Instantiate Elevator Subsystem.
   */
  public Winch() {
    winch = new WPI_VictorSPX(10);
    winch.setNeutralMode(NeutralMode.Brake);
  }
  

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Winch Voltage", winch.getMotorOutputVoltage());   
  }

  /**
   * Controls the small winch for the climber setup.
   * @param speed sets the speed of the motor [-1, 1]
    */
  public void winchControl(double speed) {
    winch.set(ControlMode.PercentOutput, speed);
  }
  

  /**
   * 
   */
}