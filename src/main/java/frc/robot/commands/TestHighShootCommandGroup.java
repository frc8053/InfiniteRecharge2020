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
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html

public class TestHighShootCommandGroup extends SequentialCommandGroup {
  Intake intake;
  Shooter shooter;
  /**
   * Creates a new testHighShootCommandGroup.
   */
  
  public TestHighShootCommandGroup(Intake intake, Shooter shooter) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(
        new RunCommand(() -> intake.conveyorControl(-0.1), intake).withTimeout(0.3), 
        new InstantCommand(() -> intake.conveyorControl(0), intake),
        new InstantCommand(() -> shooter.shoot(1), shooter),
        new WaitCommand(2),
        new ParallelCommandGroup(
          new IntakeCommand(0.8, 0.87, intake)
        )
    );
    this.intake = intake;
    this.shooter = shooter;

  }
  
  @Override
  public void end(boolean interrupted) {
    new InstantCommand(() -> intake.conveyorControl(0), intake);
    new InstantCommand(() -> shooter.shoot(0), shooter);
    new InstantCommand(() -> intake.intakeBar(0), intake);
  }
}
