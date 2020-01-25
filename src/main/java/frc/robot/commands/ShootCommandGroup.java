/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.*;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class ShootCommandGroup extends SequentialCommandGroup {
  /**
   * Creates a new ShootCommandGroup.
   */
  public ShootCommandGroup(Intake intake, double speed, Shooter shooter) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(
        new IntakeCommand(0, -0.1, intake).withTimeout(0.1), 
        new ShootCommand(speed, shooter).withTimeout(0.2),
        new ParallelCommandGroup(
          new ShootCommand(speed, shooter),
          new IntakeCommand(0, 0.2, intake)
      )
    );
  }
}
