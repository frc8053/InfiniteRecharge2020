/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.IntakeConstant;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.PidShooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class AutoRightShootCommandGroup extends SequentialCommandGroup {
  /**
   * Auto that starts in the right side of the field, picks up balls in the trench and 
   * shoots into the high goal.
   * 
   * @param driveTrain the driveTrain subsystem used
   * @param intake the intake subsytem used
   * @param pidShooter the pidShooter used
   */
  public AutoRightShootCommandGroup(DriveTrain driveTrain, Intake intake, 
                                    PidShooter pidShooter) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(
        new DriveDistanceCommand(86.63, false, driveTrain),
        new AutoTurnCommandGroup(driveTrain, intake, pidShooter).withTimeout(3),
        new DriveTurnCommand(driveTrain.getSavedTurnAngle(), driveTrain),
        new DriveDistanceCommand(114, true, driveTrain)
          .raceWith(new IntakeCommand(IntakeConstant.INTAKE_SPEED, 
          IntakeConstant.CONVEYOR_SPEED, intake)),
        new AutoTurnCommandGroup(driveTrain, intake, pidShooter).withTimeout(5)
    );
  }
}
