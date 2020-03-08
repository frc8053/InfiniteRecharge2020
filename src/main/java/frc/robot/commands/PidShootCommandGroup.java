/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.PidShooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class PidShootCommandGroup extends SequentialCommandGroup {
  private PidShooter pidShooter;
  private Intake intake;
  /**
   * Shoots balls at the specified rpm using a PID control system.
   * 
   * @param driveTrain drivetrain
   * @param intake the intake subsystem used
   * @param pidShooter the right shooter subsystem used
   */
  
  public PidShootCommandGroup(DriveTrain driveTrain, Intake intake, PidShooter pidShooter) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(
        //new InstantCommand(() -> leftShooter.shoot(-0.2), leftShooter),
        //new InstantCommand(() -> rightShooter.shoot(-0.2), rightShooter),
        new InstantCommand(() -> pidShooter.shootPower(-0.2)),
        new RunCommand(() -> intake.conveyorControl(-0.15), intake).withTimeout(0.3), 
        new InstantCommand(() -> intake.conveyorControl(0), intake),
        new ParallelCommandGroup(
          new ShootCommand(pidShooter, driveTrain),
          new SequentialCommandGroup(
            new WaitUntilCommand(() -> (pidShooter.reachedSetpoint())),
            new RunCommand(() -> intake.conveyorControl(0.85), intake),
            new RunCommand(() -> intake.intakeBar(0.8), intake)
          )
          
        )
        
    );
    this.pidShooter = pidShooter;
    this.intake = intake;
  }

  @Override
  public void end(boolean interrupted) {
    super.end(interrupted);
    pidShooter.stopShooting();
    intake.conveyorControl(0);
    //System.out.println("disabled the shooters" + this.runsWhenDisabled());
  }
}
