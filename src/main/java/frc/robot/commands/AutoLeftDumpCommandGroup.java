/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.PidShooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class AutoLeftDumpCommandGroup extends SequentialCommandGroup {
  /**
   * Auto that starts on the left and dumps balls into the low goal.
   * 
   * @param driveTrain the driveTrain subsystem used
   * @param intake the intake subsystem used
   * @param pidShooter the shooter subsystem used
   */
  public AutoLeftDumpCommandGroup(DriveTrain driveTrain, Intake intake, 
                                  PidShooter pidShooter) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(
        new DriveTurnCommand(23.4946, driveTrain),
        new DriveDistanceCommand(-228.26352227, false, driveTrain),
        new DriveTurnCommand(90 - 23.4946, driveTrain),
        new DriveDistanceCommand(-12, false, driveTrain),
        new SetRpmShootCommandGroup(1000, intake, pidShooter).withTimeout(3)
    );
  }
}
