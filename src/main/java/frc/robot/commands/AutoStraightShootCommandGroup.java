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
public class AutoStraightShootCommandGroup extends SequentialCommandGroup {
  /**
   * Auto commands that moves straight, turns to vision target and shoots.
   * @param driveTrain the driveTrain subsystem used
   * @param intake the intake subsystem used
   * @param pidShooter the pidShooter used
   */
  public AutoStraightShootCommandGroup(DriveTrain driveTrain, Intake intake, 
                                      PidShooter pidShooter) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(
        new DriveDistanceCommand(82, false, driveTrain),
        new AutoTurnCommandGroup(driveTrain, intake, pidShooter),
        new PidShootCommandGroup(driveTrain, intake, pidShooter)
    );
  }
}
