/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.Constants.IntakeConstant;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.PidShooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class Auto11BallCommandGroup extends SequentialCommandGroup {
  /**
   * Auto that uses vision processing to collect and fire up to 11 power cells.
   * @param driveTrain the driveTrain subsystem used
   * @param intake the intake subsystem used
   * @param pidShooter the pidShooter subsystem used
   */
  public Auto11BallCommandGroup(DriveTrain driveTrain, Intake intake, PidShooter pidShooter) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(
        new SequentialCommandGroup(
          new DriveDistanceCommand(86.63, false, driveTrain),
          new AutoTurnCommandGroup(driveTrain, intake, pidShooter).withTimeout(2.5),
          new DriveTurnCommand(driveTrain.getSavedTurnAngle(), driveTrain),
          new DriveDistanceCommand(114, true, driveTrain)
            .raceWith(new IntakeCommand(IntakeConstant.INTAKE_SPEED, 
            IntakeConstant.CONVEYOR_SPEED, intake)),
          new AutoTurnCommandGroup(driveTrain, intake, pidShooter).withTimeout(2.5),
          new DriveTurnCommand(170, driveTrain),
          new AutoBallAlignmentCommand(0.55, driveTrain, intake)
        ).withTimeout(12.5),
        new InstantCommand(() -> driveTrain.arcadeDrive(0, 0.5), driveTrain),
        new WaitUntilCommand(driveTrain::findShootTarget),
        new VisionCommandGroup(driveTrain, intake, pidShooter)
    );
  }
}
