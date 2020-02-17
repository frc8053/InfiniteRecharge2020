/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import frc.robot.Constants.Shoot;

public class RightShooter extends PIDSubsystem {

  private WPI_VictorSPX shooterRight;

  private final Encoder shootRightEncoder;

  //private final DutyCycleEncoder shootRightEncoder; Duty Cycle returned value but
  //it didnt make sense and could not be used

  private SimpleMotorFeedforward shooterFeedForward;

  private double setpoint;

  private double clicks;
  private double rate;


  /**
   * The shooter PID subsystem contains the necessary motors and sensors needed
   * to accurately fire balls.
   */
  public RightShooter() {
    super(new PIDController(Shoot.PSHOOT, Shoot.ISHOOT, Shoot.DSHOOT));
    getController().setTolerance(Shoot.SHOOT_TOLERANCE);
    shooterRight = new WPI_VictorSPX(8);

    shootRightEncoder = new Encoder(2, 3, false, CounterBase.EncodingType.k4X);    

    //shootRightEncoder = new DutyCycleEncoder(3);
    
    shootRightEncoder.setDistancePerPulse(Shoot.SHOOTRATE);

    setpoint = 3000;
    getController().setSetpoint(setpoint);
    shooterFeedForward = new SimpleMotorFeedforward(Shoot.KS, Shoot.KV);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    super.periodic();
    clicks = shootRightEncoder.get();
    rate = shootRightEncoder.getRate() * 60;
    SmartDashboard.putNumber("Right Shooter Clicks", clicks);
    SmartDashboard.putNumber("Right Shooter RPM", rate);
  }

  @Override
public void enable() {
    super.enable();
    System.out.println("enabeling the right thing");
  }

  @Override
  protected void useOutput(double output, double setpoint) {
    shooterRight.setVoltage(output + ((12.0 / 5000.0) * setpoint));
  }

  @Override
  protected double getMeasurement() {
    return rate;
  }

  @Override
  public void setSetpoint(double setpoint) {
    super.setSetpoint(setpoint);
  }

  public void shoot(double speed) {
    shooterRight.set(speed);
  }

  public double getRpm() {
    return shootRightEncoder.getRate();
  }

  public boolean reachedSetpoint() {
    return Math.abs(this.setpoint - rate) < 50;
  }
}