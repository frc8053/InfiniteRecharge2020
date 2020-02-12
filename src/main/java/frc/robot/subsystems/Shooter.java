/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import frc.robot.Constants.Shoot;

public class Shooter extends PIDSubsystem {

  private WPI_VictorSPX shooterLeft;
  private WPI_VictorSPX shooterRight;

  private Encoder shootEncoder;

  private SimpleMotorFeedforward shooterFeedForward;

  private SpeedControllerGroup shooterGroup;

  private double setpoint;


  /**
   * The shooter PID subsystem contains the necessary motors and sensors needed
   * to accurately fire balls.
   */
  public Shooter() {
    super(new PIDController(Shoot.PSHOOT, Shoot.ISHOOT, Shoot.DSHOOT));
    getController().setTolerance(Shoot.SHOOT_TOLERANCE);
    shooterLeft = new WPI_VictorSPX(7);
    shooterLeft.setInverted(true);
    shooterRight = new WPI_VictorSPX(8);

    shootEncoder = new Encoder(4,5);    

    shootEncoder.setDistancePerPulse(Shoot.SHOOTRATE);

    shooterGroup = new SpeedControllerGroup(shooterLeft, shooterRight);
    setpoint = 3000;
    getController().setSetpoint(setpoint);
    shooterFeedForward = new SimpleMotorFeedforward(Shoot.KS, Shoot.KV);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Shooter RPM", shootEncoder.getRate());
  }

  @Override
  protected void useOutput(double output, double setpoint) {
    shooterGroup.setVoltage(output + shooterFeedForward.calculate(setpoint));
  }

  @Override
  protected double getMeasurement() {
    return shootEncoder.getRate();
  }

  @Override
  public void setSetpoint(double setpoint) {
    super.setSetpoint(setpoint);
  }

  public void shoot(double speed) {
    shooterGroup.set(speed);
  }

  public double getRpm() {
    //return shootEncoder.getRate();
    return 2;
  }

  public boolean reachedSetpoint() {
    return getController().atSetpoint();
  }
}