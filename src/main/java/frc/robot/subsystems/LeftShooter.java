/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import frc.robot.Constants.Shoot;

public class LeftShooter extends PIDSubsystem {

  private WPI_VictorSPX shooterLeft;

  private final Encoder shootLeftEncoder;

  private SimpleMotorFeedforward shooterFeedForward;

  private double setpoint;

  private double clicks;

  private double rate;


  /**
   * The shooter PID subsystem contains the necessary motors and sensors needed
   * to accurately fire balls.
   */
  public LeftShooter() {
    super(new PIDController(Shoot.PSHOOT, Shoot.ISHOOT, Shoot.DSHOOT));
    getController().setTolerance(Shoot.SHOOT_TOLERANCE);
    shooterLeft = new WPI_VictorSPX(7);
    shooterLeft.setInverted(true);
    shooterLeft.setNeutralMode(NeutralMode.Coast);

    shootLeftEncoder = new Encoder(0, 1, true, CounterBase.EncodingType.k4X);   

    shootLeftEncoder.setDistancePerPulse(Shoot.SHOOTRATE);

    setpoint = 3000;
    getController().setSetpoint(setpoint);
    shooterFeedForward = new SimpleMotorFeedforward(Shoot.KS, Shoot.KV);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    clicks = shootLeftEncoder.get();
    rate = shootLeftEncoder.getRate() * 60;
    SmartDashboard.putNumber("Left Shooter CLicks", clicks);
    SmartDashboard.putNumber("Left Shooter RPM", rate);
    SmartDashboard.putNumber("Left Shooter Voltage", shooterLeft.getMotorOutputVoltage());
    
    super.periodic();
  }

  @Override
  protected void useOutput(double output, double setpoint) {
    shooterLeft.setVoltage((output + ((12.0 / 5000.0) * setpoint)));
  }

  @Override
  protected double getMeasurement() {
    return rate;
  }

  @Override
  public void setSetpoint(double setpoint) {
    super.setSetpoint(setpoint);
    this.setpoint = setpoint;
  }

  public void shoot(double speed) {
    shooterLeft.set(speed);
  }

  public double getRpm() {
    return shootLeftEncoder.getRate();
  }

  public boolean reachedSetpoint() {
    return Math.abs(this.setpoint - rate) < 50;
  }
}