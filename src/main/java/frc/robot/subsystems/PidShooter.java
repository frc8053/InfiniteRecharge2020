/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Shoot;

public class PidShooter extends SubsystemBase {
  
  private WPI_VictorSPX shooterLeft;
  private WPI_VictorSPX shooterRight;

  private final Encoder shootLeftEncoder;
  private final Encoder shootRightEncoder;

  private PIDController leftPidController;
  private PIDController rightPidController;

  private double setpoint;
  private double tolerance = 50;


  /**
   * The shooter PID subsystem contains the necessary motors and sensors needed
   * to accurately fire balls.
   */
  public PidShooter() {
    shooterLeft = new WPI_VictorSPX(8);
    shooterRight = new WPI_VictorSPX(7);

    shooterLeft.setInverted(false);
    shooterLeft.setNeutralMode(NeutralMode.Coast);
    shooterRight.setInverted(true);
    shooterRight.setNeutralMode(NeutralMode.Coast);

    shootLeftEncoder = new Encoder(0, 1, true, EncodingType.k1X);   
    shootRightEncoder = new Encoder(2, 3, false, EncodingType.k1X);   

    shootLeftEncoder.setSamplesToAverage(5);
    shootRightEncoder.setSamplesToAverage(5);
    double dpr = (Shoot.SHOOTRATE);
    shootLeftEncoder.setDistancePerPulse(dpr);
    shootRightEncoder.setDistancePerPulse(dpr);

    setpoint = 3000;

    leftPidController = new PIDController(Shoot.P_LEFT_SHOOTER, Shoot.I_LEFT_SHOOTER, 
      Shoot.D_LEFT_SHOOTER);
    rightPidController = new PIDController(Shoot.P_RIGHT_SHOOTER, Shoot.I_RIGHT_SHOOTER, 
      Shoot.D_RIGHT_SHOOTER);
    leftPidController.setTolerance(tolerance);
    rightPidController.setTolerance(tolerance);
    leftPidController.setSetpoint(setpoint);
    rightPidController.setSetpoint(setpoint);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Shooter Error", leftPidController.getPositionError());
    SmartDashboard.putNumber("Shooter PID Result", leftPidController.calculate(getLeftRpm()));
    SmartDashboard.putNumber("Left Shooter CLicks", shootLeftEncoder.get());
    SmartDashboard.putNumber("Left Shooter RPM", getLeftRpm());
    SmartDashboard.putNumber("Left Shooter Voltage", shooterLeft.getMotorOutputVoltage());
    SmartDashboard.putNumber("Right Shooter CLicks", shootRightEncoder.get());
    SmartDashboard.putNumber("Right Shooter RPM", getRightRpm());
    SmartDashboard.putNumber("Right Shooter Voltage", shooterRight.getMotorOutputVoltage());
  }

  /**
   * Sets the desired rpm of the shooter PID controllers.
   * @param setpointRpm the setpoint of the PID controllers in rpm [-5000, 5000]
   */
  public void setSetpoint(double setpointRpm) {
    leftPidController.setSetpoint(setpointRpm);
    rightPidController.setSetpoint(setpointRpm);
  }

  /**
   * Runs the PID shooter.
   */
  private void runRpm() {
    double ff = (12.0 / 5000);
    shooterLeft.setVoltage(ff * leftPidController.getSetpoint() 
          + leftPidController.calculate(getLeftRpm()));
    shooterRight.setVoltage(ff * rightPidController.getSetpoint()
          + rightPidController.calculate(getRightRpm()));
    //System.out.println("Trying to shoot!!");
  }

  /**
   * Sets the power to the shooter wheels.
   * @param power The power of the motors [-1. 1]
   */
  public void shootPower(double power) {
    shooterLeft.set(ControlMode.PercentOutput, power);
    shooterRight.set(ControlMode.PercentOutput, power);
  }

  /**
   * Sets the desired rpm and runs the shooter PID.
   * @param setpoint the desired rpm of the PID shooter [-5000, 5000]
   */
  public void shootRpm(double setpoint) {
    setSetpoint(setpoint);
    runRpm();
  }

  /**
   * Runs the shooter PID.
   */
  public void shootRpm() {
    runRpm();
  }

  /**
   * Stops the PIDShooter.
   */
  public void stopShooting() {
    shooterLeft.setVoltage(0);
    shooterRight.setVoltage(0);
    leftPidController.reset();
    rightPidController.reset();
  }
  
  /**
   * Returns whether both shooter wheels have reached their setpoint.
   * @return
   */
  public boolean reachedSetpoint() {
    return leftPidController.atSetpoint() && rightPidController.atSetpoint();
  }

  /**
   * Returns the rpm of the left shooter wheel.
   * @return
   */
  public double getLeftRpm() {
    return shootLeftEncoder.getRate() * 60;
  }

  /**
   * returns the linear velocity of the left shooter wheel.
   * @return
   */
  public double getLeftSpeed() {
    return shootLeftEncoder.getRate() * Math.PI * 1.33333333333333333333333333333333333333333333333;
  }

  /**
   * Returns the rpm of the right shooter wheel.
   * @return
   */
  public double getRightRpm() {
    return shootRightEncoder.getRate() * 60;
  }

  /**
   * Returns the linear velocity of the right shooter wheel.
   * @return
   */
  public double getRightSpeed() {
    return shootRightEncoder.getRate() * Math.PI * 1.3333333333333333333333333333333333333333333333;
  }
}
