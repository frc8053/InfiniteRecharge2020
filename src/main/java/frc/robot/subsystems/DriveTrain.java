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

  private final ChameleonVision shootVision;
  private final ChameleonVision intakeVision;

  private final Solenoid visionLight;
  private final Encoder leftEncoder;
  private final Encoder rightEncoder;

  private Boolean switchDrive;
  private double reverse;

  private String driver;

  private final Gyro gyro;

  private ShuffleboardTab parameterTab;
  private NetworkTableEntry maxSpeed;
  private NetworkTableEntry brakeReduction;

  private double speed;

  /**
   * Initalizes drive motors and helper classes.
   * </p>
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

    shootVision = new ChameleonVision("Shooter Cam", Pipelines.DRIVER);
    intakeVision = new ChameleonVision("Intake Cam", Pipelines.DRIVER);

    visionLight = new Solenoid(0);

    leftDrive = new SpeedControllerGroup(frontLeft, backLeft);
    rightDrive = new SpeedControllerGroup(frontRight, backRight);
    myRobot = new DifferentialDrive(leftDrive, rightDrive);
    leftEncoder = new Encoder(4, 5, false, EncodingType.k4X);
    leftEncoder.setDistancePerPulse(Drive.DISTANCE_PER_PULSE);
    rightEncoder = new Encoder(6, 7);
    rightEncoder.setDistancePerPulse(Drive.DISTANCE_PER_PULSE);

    switchDrive = false;
    reverse = 1;

    gyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);
    gyro.calibrate();

    parameterTab = Shuffleboard.getTab("Parameter Tab");
    maxSpeed = parameterTab.add("Max Drive Speed", 1)
      .withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1))
      .getEntry();
    brakeReduction = parameterTab.add("Brake Reduction", 0.5)
      .withWidget(BuiltInWidgets.kNumberSlider)
      .withProperties(Map.of("min", 0, "max", maxSpeed.getDouble(1)))
      .getEntry();
  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    if (switchDrive) {
      driver = "Chris";
    } else {
      driver = "Trey";
    }
    SmartDashboard.putString("Driver", driver);
    SmartDashboard.putNumber("Drive Encoder", leftEncoder.getDistance());
    SmartDashboard.putNumber("Yaw", gyro.getAngle());
    SmartDashboard.putNumber("Modified Yaw", getGyro());
    SmartDashboard.putNumber("Left Voltage", frontLeft.getMotorOutputVoltage());
    SmartDashboard.putNumber("Right Voltage", frontRight.getMotorOutputVoltage());
    SmartDashboard.putNumber("Distance to Goal", getVisionDistance());
    SmartDashboard.putNumber("Vision Yaw", getVisionYaw());
  }

  public void arcadeDrive(final double left, final double right) {
    myRobot.arcadeDrive(left, right);
  }

  public void tankDrive(final double left, final double right) {
    myRobot.tankDrive(left, right);
  }

  public double getDriveSpeed() {
    return maxSpeed.getDouble(1);
  }

  public double getBrakeReduction() {
    return brakeReduction.getDouble(0.33);
  }

  public void leftEncoderReset() {
    leftEncoder.reset();
  }

  public double leftEncoderValue() {
    return leftEncoder.getDistance();
  }
  
  public void switchDrive() {
    switchDrive = !switchDrive;
  }

  public Boolean getDriver() {
    return switchDrive;
  }
  
  public void switchReverse() {
    reverse = -reverse;
  }

  public double getReverse() {
    return reverse;
  }

  public void turnOnLight() {
    visionLight.set(!visionLight.get());
  }

  public double getVisionYaw() {
    return shootVision.getRotation().yaw;
  }

  public void toggleDriverMode(boolean isDriverMode) {
    shootVision.setDriverMode(isDriverMode);
  }

  public boolean findTarget() {
    return shootVision.isValidFrame();
  }

  public boolean getDriverMode() {
    return shootVision.isDriverMode();
  }

  public void setPipeline(double pipeline) {
    shootVision.setVisionPipeline(pipeline);
  }

  public double getVisionDistance() {
    return 75.25 / Math.tan(shootVision.getRotation().pitch); 
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
  
  public void resetGyro() {
    gyro.reset();
  }

}