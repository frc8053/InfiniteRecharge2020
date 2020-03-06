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
public class AutoMidShootCommand extends SequentialCommandGroup {
  /**
   * Creates a new AutoMidShootCommand.
   */
  public AutoMidShootCommand(DriveTrain driveTrain, Intake intake, PidShooter pidShooter) {

    super(
          new DriveTurnCommand(48.471614533921, driveTrain),
          new DriveDistanceCommand(90.498964082468922, driveTrain),
          new DriveTurnCommand(-48.471614533921, driveTrain),
          //new PidShootCommandGroup(/*help*/0, intake, shooter)
          //new VisionCommandGroup(driveTrain),
          new TestHighShootCommandGroup(0.95, 2, intake, pidShooter).withTimeout(5)
          );
  }
}