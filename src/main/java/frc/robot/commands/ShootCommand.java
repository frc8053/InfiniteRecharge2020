/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.PidShooter;

public class ShootCommand extends CommandBase {
  

  private double speed;
  private PidShooter shooter;
  /**
   * Spins the shoot motors.
   * 
   * @param speed the speed of the shooter wheels
   * @param shooter the shooter subsystem used
   */
  
  public ShootCommand(double speed, PidShooter shooter) {
    // Use addRequirements() here to declare subsystem dependencies.

    this.speed = speed;
    this.shooter = shooter;

    this.addRequirements(shooter);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    shooter.setSetpoint(speed);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    shooter.shootRpm();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.stopShooting();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
