/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class PidShootCommandGroup extends SequentialCommandGroup {
  Shooter shooter;
  Intake intake;
  /**
   * Shoots balls at the specified rpm using a PID control system.
   * 
   * @param rpm the target speed of the wheels
   * @param intake the intake subsystem used
   * @param shooter the shooter subsystem used
   */
  
  public PidShootCommandGroup(double rpm, Intake intake, Shooter shooter) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(
        new RunCommand(() -> intake.conveyorControl(-0.1), intake).withTimeout(0.1), 
        new InstantCommand(() -> shooter.setSetpoint(rpm), shooter),
        new InstantCommand(shooter::enable),
        new WaitUntilCommand(shooter::reachedSetpoint),
        new RunCommand(() -> intake.conveyorControl(0.2), intake)
    );
    this.shooter = shooter;
    this.intake = intake;
  }

  @Override
  public void end(boolean interrupted) {
    new InstantCommand(shooter::disable);
    new InstantCommand(() -> intake.conveyorControl(0), intake);
  }
}
