/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.modules.TrajectoryMath;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.PidShooter;

public class ShootCommand extends CommandBase {
  private DriveTrain drive;
  private PidShooter shooter;
  
  /**
   * Spins the shoot motors to the speed needed by the vision target.
   * 
   * @param shooter the shooter subsystem used
   * @param drive the drivetrain
   */
  public ShootCommand(PidShooter shooter, DriveTrain drive) {
    // Use addRequirements() here to declare subsystem dependencies.

    this.shooter = shooter;
    this.drive = drive;
    this.addRequirements(shooter);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //shooter.setSetpoint(5000);
    shooter.setSetpoint(TrajectoryMath.getVelocityFromDistance(
        TrajectoryMath.getDistanceFromPitch(drive.getShootVisionPitch())));
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
