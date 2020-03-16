/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.modules.Pipelines;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.PidShooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class VisionCommandGroup extends SequentialCommandGroup {
  private DriveTrain driveTrain;

  /**
   * The vision command group turns on the vision light and turns to the target.
   * @param driveTrain the driveTrain subsystem used
   */
  public VisionCommandGroup(DriveTrain driveTrain, Intake intake, PidShooter pidShooter) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(
        new InstantCommand(() -> driveTrain.turnOnLight(true), driveTrain),
        new VisionTurnCommand(driveTrain).raceWith(
          new ShootCommand(pidShooter, driveTrain)
        ),
        new PidShootCommandGroup(driveTrain, intake, pidShooter)
    );
    this.driveTrain = driveTrain;
  }

  @Override
  public void initialize() {
    driveTrain.setShootDriverMode(false);
    driveTrain.setShootPipeline(Pipelines.DEFAULT);
    super.initialize();
  }

  @Override
  public void end(boolean interrupted) {
    //driveTrain.turnOnLight(false);
    driveTrain.setShootDriverMode(false);
    //driveTrain.setShootPipeline(Pipelines.DRIVER);
    super.end(interrupted);
  }
}
