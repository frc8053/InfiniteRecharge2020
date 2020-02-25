/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
* Add your docs here.
*/
public class Elevator extends SubsystemBase {

  //Instance Variables
  private WPI_VictorSPX elevatorMotor;
  private final double weight = 0.3;
  private Encoder encoder;

  /**
   * Instantiate Elevator Subsystem.
   */
  public Elevator() {
    elevatorMotor = new WPI_VictorSPX(9);
    elevatorMotor.setInverted(true);
    encoder = new Encoder(8, 9, false, EncodingType.k4X);
    encoder.setDistancePerPulse(Constants.Elevator.DISTANCE_PER_PULSE);
    Shuffleboard.getTab("Electrical Tab") 
      .add("Elevator Voltage", elevatorMotor.getMotorOutputVoltage()); 
  }
  

  @Override
  public void periodic() {
    // This method will be called once per scheduler run  
    
  }

  public double getDistance() {
    return encoder.getDistance() + Constants.Elevator.HEIGHT;
  }


  /**
   * Controls the small winch for the climber setup.
   * @param speed sets the speed of the motor [-1, 1]
    */
  public void motorControl(double speed) {
    elevatorMotor.set(ControlMode.PercentOutput, speed * weight);
    
  }

  /**
   * 
   */
}