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
import frc.robot.subsystems.LeftShooter;
import frc.robot.subsystems.RightShooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class PidShootCommandGroup extends SequentialCommandGroup {
  private LeftShooter leftShooter;
  private RightShooter rightShooter;
  private Intake intake;
  /**
   * Shoots balls at the specified rpm using a PID control system.
   * 
   * @param rpm the target speed of the wheels
   * @param intake the intake subsystem used
   * @param leftShooter the left shooter subsystem used
   * @param rightShooter the right shooter subsystem used
   */
  
  public PidShootCommandGroup(double rpm, Intake intake, LeftShooter leftShooter, 
                              RightShooter rightShooter) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(
        //new InstantCommand(() -> leftShooter.shoot(-0.2), leftShooter),
        //new InstantCommand(() -> rightShooter.shoot(-0.2), rightShooter),
        new RunCommand(() -> intake.conveyorControl(-0.1), intake).withTimeout(0.3), 
        new InstantCommand(() -> intake.conveyorControl(0), intake),
        new InstantCommand(() -> leftShooter.setSetpoint(rpm), leftShooter),
        new InstantCommand(() -> rightShooter.setSetpoint(rpm), rightShooter),
        new InstantCommand(leftShooter::enable, leftShooter),
        new InstantCommand(rightShooter::enable, rightShooter),
        new WaitUntilCommand(() -> (leftShooter.reachedSetpoint() 
                                    && rightShooter.reachedSetpoint())),
        new RunCommand(() -> intake.conveyorControl(0.2), intake)
    );
    this.leftShooter = leftShooter;
    this.rightShooter = rightShooter;
    this.intake = intake;
  }

  @Override
  public void end(boolean interrupted) {
    super.end(interrupted);
    new InstantCommand(leftShooter::disable, leftShooter);
    new InstantCommand(rightShooter::disable, rightShooter);
    rightShooter.shoot(0);
    leftShooter.shoot(0);
    new InstantCommand(() -> intake.conveyorControl(0), intake);
    System.out.println("disabled the shooters" + this.runsWhenDisabled());
  }
}
