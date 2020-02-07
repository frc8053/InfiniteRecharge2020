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
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.Shoot;
import frc.robot.commands.AutoLeftShootCommandGroup;
import frc.robot.commands.AutoRightDumpCommandGroup;
import frc.robot.commands.AutoRightShootCommandGroup;
import frc.robot.commands.ClimbCommand;
import frc.robot.commands.DefaultDriveCommand;
import frc.robot.commands.DefaultIntakeCommand;
import frc.robot.commands.PidShootCommandGroup;
import frc.robot.commands.TestHighShootCommandGroup;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.triggers.AnalogTrigger;
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

  private DriveTrain driveTrain;
  private Intake intake;
  private Shooter shooter;
  private Climber climber;

  private AutoLeftShootCommandGroup autoLeftShootCommandGroup;
  private AutoRightShootCommandGroup autoRightShootCommandGroup;
  private AutoRightDumpCommandGroup autoRightDumpCommandGroup;
  private TestHighShootCommandGroup testHighShootCommand;
  private PidShootCommandGroup lowShootCommand;
  private PidShootCommandGroup highShootCommand;
  private ClimbCommand climbCommand;

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
  AnalogTrigger analogTrigger;

  SendableChooser<Command> autoChooser;
  

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Initalize subsystems
    driveTrain = new DriveTrain();
    intake = new Intake();
    shooter = new Shooter();
    climber = new Climber();    

    // Initalize commands
    autoLeftShootCommandGroup = new AutoLeftShootCommandGroup(driveTrain, intake, shooter);
    autoRightShootCommandGroup = new AutoRightShootCommandGroup(driveTrain, intake, shooter);
    autoRightDumpCommandGroup = new AutoRightDumpCommandGroup(driveTrain, intake, shooter);
    lowShootCommand = new PidShootCommandGroup(Shoot.SLOW_RPM, intake, shooter);
    highShootCommand = new PidShootCommandGroup(Shoot.FAST_RPM, intake, shooter);
    testHighShootCommand = new TestHighShootCommandGroup(intake, shooter);
    
    // Initialize Gamepads
    driverController = new XboxController(0);
    driverPovDown = new POVButton(driverController, 180);
    driverPovUp = new POVButton(driverController, 0);
    driverRightBumper = new JoystickButton(driverController, Button.kBumperRight.value);
    driverLeftBumper = new JoystickButton(driverController, Button.kBumperLeft.value);
    driverLeftTrigger = new TriggerButton(driverController.getTriggerAxis(Hand.kLeft));
    driverRightTrigger = new TriggerButton(driverController.getTriggerAxis(Hand.kRight));
    
    manipulatorController = new XboxController(1);
    analogTrigger = new AnalogTrigger(manipulatorController, Hand.kRight);

    // Configure the button bindings
    maniButtonA = new JoystickButton(manipulatorController, Button.kA.value);
    maniButtonX = new JoystickButton(manipulatorController, Button.kX.value);
    maniButtonY = new JoystickButton(manipulatorController, Button.kY.value);
    
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
    climbCommand = new ClimbCommand(analogTrigger::getDY, climber);

    intake.setDefaultCommand(new DefaultIntakeCommand(
        () -> manipulatorController.getY(Hand.kLeft),
        intake));
    // Configure the button bindings
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
    maniButtonY.whenHeld(testHighShootCommand)  
        .whenReleased(new InstantCommand(
            () -> shooter.shoot(0), shooter));
    maniButtonX.whenHeld(highShootCommand);         
    analogTrigger.whileActiveOnce(climbCommand);          
    
    autoChooser = new SendableChooser<Command>();
    autoChooser.setDefaultOption("Auto Left Shoot", autoLeftShootCommandGroup);
    autoChooser.addOption("Auto Middle Shoot", autoLeftShootCommandGroup);
    autoChooser.addOption("Auto Middle Dump", autoLeftShootCommandGroup);
    autoChooser.addOption("Auto Right Shoot", autoRightShootCommandGroup);
    autoChooser.addOption("Auto Right Dump", autoRightDumpCommandGroup);
    Shuffleboard.getTab("SmartDashboard")
      .add("Auto Chooser", autoChooser)
      .withWidget(BuiltInWidgets.kComboBoxChooser);
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return autoChooser.getSelected();
  }
}
