/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Drive;
import frc.robot.modules.ChameleonVision;
import frc.robot.modules.Pipelines;
import java.util.Map;

public class DriveTrain extends SubsystemBase {
  /**
   * Creates a new driveTrain.
   */
  private final WPI_VictorSPX frontLeft;
  private final WPI_VictorSPX frontRight;
  private final WPI_VictorSPX backLeft;
  private final WPI_VictorSPX backRight;
  private final SpeedControllerGroup leftDrive;
  private final SpeedControllerGroup rightDrive;
  private final DifferentialDrive myRobot;
  
  private Solenoid visionLight;
  private Solenoid backLeftLight;
  private Solenoid backRightLight;

  private final ChameleonVision shootVision;
  private final ChameleonVision intakeVision;

  private final Encoder leftEncoder;
  private final Encoder rightEncoder;
  private final Gyro gyro;

  private Boolean switchDrive;
  private boolean useBlinkers;
  private boolean leftBlinker;
  private boolean rightBlinker;
  private double reverse;
  private double turnAngle;
  private double counter;

  private ShuffleboardTab parameterTab;
  private NetworkTableEntry maxSpeed;
  private NetworkTableEntry brakeReduction;

  /**
   * Initalizes drive motors and helper classes. Also contains vision values from the Pi.
   * 
   */
  public DriveTrain() {
    frontLeft = new WPI_VictorSPX(0);
    frontRight = new WPI_VictorSPX(1);
    backLeft = new WPI_VictorSPX(2);
    backRight = new WPI_VictorSPX(3);
    frontLeft.setInverted(true);
    frontLeft.setNeutralMode(NeutralMode.Brake);
    frontRight.setInverted(true);
    frontRight.setNeutralMode(NeutralMode.Brake);
    backLeft.setInverted(true);
    backLeft.setNeutralMode(NeutralMode.Brake);
    backRight.setInverted(true);
    backRight.setNeutralMode(NeutralMode.Brake);

    shootVision = new ChameleonVision("Shooter Cam", Pipelines.DEFAULT);
    intakeVision = new ChameleonVision("Intake Cam", Pipelines.DEFAULT);

    visionLight = new Solenoid(0);

    leftDrive = new SpeedControllerGroup(frontLeft, backLeft);
    rightDrive = new SpeedControllerGroup(frontRight, backRight);
    myRobot = new DifferentialDrive(leftDrive, rightDrive);
    leftEncoder = new Encoder(4, 5, false, EncodingType.k4X);
    leftEncoder.setDistancePerPulse(Drive.DISTANCE_PER_PULSE);
    rightEncoder = new Encoder(6, 7);
    rightEncoder.setDistancePerPulse(Drive.DISTANCE_PER_PULSE);

    backLeftLight = new Solenoid(1);
    backRightLight = new Solenoid(2);

    switchDrive = false;
    reverse = 1;

    gyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);
    gyro.calibrate();
    parameterTab = Shuffleboard.getTab("Parameter Tab");
    maxSpeed = parameterTab.add("Max Drive Speed", 1)
      .withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1))
      .getEntry();
    brakeReduction = parameterTab.add("Brake Reduction", 0.33)
      .withWidget(BuiltInWidgets.kNumberSlider)
      .withProperties(Map.of("min", 0, "max", 0.5))
      .getEntry();
  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    if (useBlinkers) {
      if (gyro.getRate() > 0.5) {
        if (counter < 500) {
          counter++;
        }
        if (counter >= 500) {
          rightBlinker = !rightBlinker;
          counter = 0;
        }
        leftBlinker = true;
      }
      if (gyro.getRate() < -0.5) {
        if (counter < 500) {
          counter++;
        }
        if (counter >= 500) {
          leftBlinker = !leftBlinker;
          counter = 0;
        }
        rightBlinker = true;
      }
      if (frontLeft.getMotorOutputVoltage() + frontRight.getMotorOutputVoltage() < 0) {
        backLeftLight.set(rightBlinker);
        backRightLight.set(leftBlinker);
      } else {
        backLeftLight.set(leftBlinker);
        backRightLight.set(rightBlinker);
      }
      if (Math.abs(gyro.getRate()) < 0.5) {
        counter = 0;
        leftBlinker = false;
        rightBlinker = false;
        backLeftLight.set(true);
        backRightLight.set(true);
      }
    }
    SmartDashboard.putNumber("Drive Encoder", leftEncoder.getDistance());
    SmartDashboard.putNumber("Yaw", gyro.getAngle());
    SmartDashboard.putNumber("Modified Yaw", getGyro());
    SmartDashboard.putNumber("Left Voltage", frontLeft.getMotorOutputVoltage());
    SmartDashboard.putNumber("Right Voltage", frontRight.getMotorOutputVoltage());
    SmartDashboard.putNumber("Vision Yaw", getShootVisionYaw());
  }

  /**
   * Powers the drive motors in arcade drive format.
   * @param left speed given to the motors along the x-axis. Between -1 and 1.
   * @param right turning power to the motors. Between -1 and 1.
   */
  public void arcadeDrive(final double left, final double right) {
    myRobot.arcadeDrive(left, right);
  }

  /**
   * Powers the drive motors in tank drive format.
   * @param left the power given to the left side of the robot
   * @param right the power given to the right side of the robot
   */
  public void tankDrive(final double left, final double right) {
    myRobot.tankDrive(left, right);
  }

  public void useDriveBlinkers(boolean useBlinkers) {
    this.useBlinkers = useBlinkers;
  }

  public void turnOnLeftBlinker(boolean on) {
    backLeftLight.set(on);
  }

  public void turnOnRightBlinker(boolean on) {
    backRightLight.set(on);
  }

  public void saveTurnAngle() {
    turnAngle = gyro.getAngle();
  }

  public double getSavedTurnAngle() {
    return -turnAngle;
  }

  /**
   * Returns the max drive speed given by Shuffleboard.
   * @return max drive speed
   */
  public double getDriveSpeed() {
    return maxSpeed.getDouble(1);
  }

  /**
   * Returns the brake reduction given by the Shuffleboard.
   * @return brake reduction
   */
  public double getBrakeReduction() {
    return brakeReduction.getDouble(0.33);
  }

  /**
   * Resets the left drive Encoder.
   */
  public void leftEncoderReset() {
    leftEncoder.reset();
  }

  /**
   * Returns the distance recieved from the left drive encoder.
   * @return the distance determined from the left drive encoder
   */
  public double leftEncoderValue() {
    return leftEncoder.getDistance();
  }
  
  /**
   * Switches the drive style boolean.
   */
  public void switchDrive() {
    switchDrive = !switchDrive;
  }

  /**
   * Returns the drive style boolean.
   * @return
   */
  public Boolean getDriver() {
    return switchDrive;
  }
  
  /**
   * Inverts the value of the reverse double.
   */
  public void switchReverse() {
    reverse = -reverse;
  }

  /**
   * Returns the value of the reverse double. 
   * Used to reverse direction of the drive train.
   * @return
   */
  public double getReverse() {
    return reverse;
  }

  /**
   * Used to toggle the vision light.
   * It inverts the current value.
   */
  public void toggleOnLight() {
    visionLight.set(!visionLight.get());
  }

  /**
   * Sets the vision light to on or off.
   * @param lightOn Set true for on, false for off.
   */
  public void turnOnLight(boolean lightOn) {
    visionLight.set(lightOn);
  }

  /**
   * Returns the yaw value of the shoot vision camera.
   * @return
   */
  public double getShootVisionYaw() {
    return shootVision.getRotation().yaw;
  }
  
  /**
   * Returns the pitch of the shooter camera.
   * @return
   */
  public double getShootVisionPitch() {
    //return 75.25 / Math.tan(shootVision.getRotation().pitch + 30); 
    return shootVision.getRotation().pitch;
  }

  /**
  * Returns if the shooter vision camera is in driver mode.
  * @return
  */
  public boolean getShootDriverMode() {
    return shootVision.isDriverMode();
  }

  /**
   * sets the driveMode settings of the shoot camera.
   * @param isDriverMode whether camera should be in drive mode.
   */
  public void setShootDriverMode(boolean isDriverMode) {
    shootVision.setDriverMode(isDriverMode);
  }

  /**
   * Returns if the shoot camera identifies the vision target.
   * @return
   */
  public boolean findShootTarget() {
    return shootVision.isValidFrame();
  }

  /**
   * Sets the pipeline of the vision camera.
   * @param pipeline the pipeline of the vision camera desired
   */
  public void setShootPipeline(double pipeline) {
    shootVision.setVisionPipeline(Pipelines.DEFAULT);
  }

  /**
   * returns the yaw in degrees to the power cell.
   * @return
   */
  public double getIntakeVisionYaw() {
    return intakeVision.getRotation().yaw;
  }  

  /**
   * Returns the Pitch in degrees to the power cell.
   * @return
   */
  public double getIntakeVisionPitch() {
    return intakeVision.getRotation().pitch;
  }

  /**
   * Returns if a power cell is identified by vision processing.
   * @return
   */
  public boolean findIntakeTarget() {
    return intakeVision.isValidFrame();
  }

  /**
   * Sets the intake cam driverMode settings.
   * @param driverMode whether driver mode should be turned on or off
  */
  public void setIntakeDriverMode(boolean driverMode) {
    intakeVision.setDriverMode(driverMode);
    if (driverMode) {
      intakeVision.setVisionPipeline(Pipelines.DRIVER);
    } else {
      intakeVision.setVisionPipeline(Pipelines.DEFAULT);
    }
  }

  /**
   * returns gyro value.
   * @return the rotational value of the gyroscope
   */
  public double getGyro() {
    if (gyro.getAngle() > 180) {
      return -(gyro.getAngle() - 360);
    } else if (Math.abs(gyro.getAngle()) < 180) {
      return -(gyro.getAngle());
    } else if (gyro.getAngle() < -180) {
      return -(gyro.getAngle() + 360);
    } else {
      return 0;
    }
  }
  
  /**
   * resets the Gyro back to 0 degrees.
   */
  public void resetGyro() {
    gyro.reset();
  }
}