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
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import frc.robot.Constants.Shoot;

public class Shooter extends PIDSubsystem {

  private WPI_VictorSPX shooterLeft;
  private WPI_VictorSPX shooterRight;

  private Encoder shootEncoder;

  private SimpleMotorFeedforward shooterFeedForward;

  private SpeedControllerGroup shooterGroup;


  /**
   * Creates a new Shooter.
   */
  public Shooter() {
    super(new PIDController(0, 1, 0));
    getController().setTolerance(10);
    shooterLeft = new WPI_VictorSPX(7);
    shooterRight = new WPI_VictorSPX(8);

    shootEncoder = new Encoder(4,5);    

    shootEncoder.setDistancePerPulse(Shoot.SHOOTRATE);

    shooterGroup = new SpeedControllerGroup(shooterLeft, shooterRight);

    shooterFeedForward = new SimpleMotorFeedforward(Shoot.KS, Shoot.KV);

    shooterLeft.setInverted(true);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  protected void useOutput(double output, double setpoint) {
    shooterGroup.setVoltage(output + shooterFeedForward.calculate(setpoint));
  }

  @Override
  protected double getMeasurement() {
    return shootEncoder.getRate();
  }

  public void shoot(double speed) {
    shooterGroup.set(speed);
  }

  public void shootVoltage(double voltage, double setpoint) {
    shooterGroup.setVoltage(voltage + shooterFeedForward.calculate(setpoint));
  }

  public double getRpm() {
    return shootEncoder.getRate();
  }

  public boolean reachedSetpoint() {
    return getController().atSetpoint();
  }
}