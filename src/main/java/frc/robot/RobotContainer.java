/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.DefaultDriveCommand;
import frc.robot.commands.DefaultIntakeCommand;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.PidShootCommandGroup;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.triggers.TriggerButton;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  int leftHoriz = 0;
  int leftVert = 1;
  int rightHoriz = 4;
  int rightVert = 5;

  
  private ExampleSubsystem exampleSubsystem;
  private DriveTrain driveTrain;
  private Intake intake;
  private Shooter shooter;

  private ExampleCommand exampleAutoCommand;
  private PidShootCommandGroup highShootCommand;
  private PidShootCommandGroup lowShootCommand;
  private IntakeCommand intakeCommand;

  XboxController driverController;
  JoystickButton driverRightBumper;
  JoystickButton driverLeftBumper;
  POVButton driverPovDown;
  POVButton driverPovUp;
  Trigger driverLeftTrigger;
  Trigger driverRightTrigger;
  
  XboxController manipulatorController;
  JoystickButton maniButtonY;
  JoystickButton maniButtonA;
  JoystickButton maniButtonX;
  Trigger maniLeftTrigger;
  Trigger maniRightTrigger;

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {

    // Initalize subsystems
    exampleSubsystem = new ExampleSubsystem();
    driveTrain = new DriveTrain();
    intake = new Intake();
    shooter = new Shooter();

    // Initalize commands
    exampleAutoCommand = new ExampleCommand(exampleSubsystem);
    intakeCommand = new IntakeCommand(Constants.IntakeConstant.INTAKE_SPEED, 0, intake);
    lowShootCommand = new PidShootCommandGroup(Constants.Shoot.SHOOT_LOW, intake, shooter);
    highShootCommand = new PidShootCommandGroup(Constants.Shoot.SHOOT_HIGH, intake, shooter);
    
    // Initialize Gamepads
    driverController = new XboxController(0);
    driverPovDown = new POVButton(driverController, 180);
    driverPovUp = new POVButton(driverController, 0);
    driverRightBumper = new JoystickButton(driverController, Button.kBumperRight.value);
    driverLeftBumper = new JoystickButton(driverController, Button.kBumperLeft.value);
    driverLeftTrigger = new TriggerButton(driverController.getTriggerAxis(Hand.kLeft));
    driverRightTrigger = new TriggerButton(driverController.getTriggerAxis(Hand.kRight));
    
    manipulatorController = new XboxController(1);
    maniButtonA = new JoystickButton(manipulatorController, Button.kA.value);
    maniButtonX = new JoystickButton(manipulatorController, Button.kX.value);
    // Configure the button bindings
    // Set the default drive command to split-stick arcade drive
    driveTrain.setDefaultCommand(new DefaultDriveCommand(
        () -> driverController.getY(Hand.kLeft),
        () -> driverController.getY(Hand.kRight), 
        () -> driverController.getX(Hand.kRight), 
        () -> driverController.getXButtonReleased(), 
        () -> driverController.getAButtonReleased(),
        () -> driverLeftTrigger.get(),
        () -> driverRightTrigger.get(),
        driveTrain));

    intake.setDefaultCommand(new DefaultIntakeCommand(
        () -> manipulatorController.getY(Hand.kLeft),
        intake));
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    //driverRightBumper.whenHeld(intakeCommand);
    //driverLeftBumper.whenHeld(intakeCommandGroup);
    //driverPovDown.whenHeld(povDownCommand);
    //driverPovUp.whenHeld(povUpCommand);
    maniButtonA.whenHeld(lowShootCommand);
    maniButtonY.whenHeld(new SequentialCommandGroup(
        new RunCommand(() -> intake.conveyorControl(-0.1), intake).withTimeout(0.1), 
        new InstantCommand(() -> shooter.shoot(1), shooter),
        new WaitCommand(0.1),
        new RunCommand(() -> intake.conveyorControl(0.2), intake)
        ));
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return exampleAutoCommand;
  }
}
