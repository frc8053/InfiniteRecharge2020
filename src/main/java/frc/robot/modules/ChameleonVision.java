package frc.robot.modules;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;


public class ChameleonVision {
  private NetworkTableEntry pitch;
  private NetworkTableEntry yaw;
  private NetworkTableEntry pipeline;
  private NetworkTableEntry timestamp;
  private NetworkTableEntry driverMode;
  private NetworkTableEntry isValid;

  /**
   * Creates a new vision referencing object.
   * @param camera The name of the camera that you are trying to use.
   */
  public ChameleonVision(String camera) {
    NetworkTableInstance netTables = NetworkTableInstance.getDefault();
    NetworkTable ctable = netTables.getTable("chameleon-vision").getSubTable(camera);
    pitch = ctable.getEntry("pitch");
    yaw = ctable.getEntry("yaw");
    pipeline = ctable.getEntry("pipeline");
    timestamp = ctable.getEntry("timestamp");
    driverMode = ctable.getEntry("driver_mode");
    isValid = ctable.getEntry("is_valid");
  }

  /**
   * Creates a new vision referencing object.
   * @param camera The name of the camera you are trying to use.
   * @param pipelinex The id of the pipeline you are wanting it to initialize and run.
   */
  public ChameleonVision(String camera, double pipelinex) {
    NetworkTableInstance netTables = NetworkTableInstance.getDefault();
    NetworkTable ctable = netTables.getTable("chameleon-vision").getSubTable(camera);
    pitch = ctable.getEntry("targetPitch");
    yaw = ctable.getEntry("targetYaw");
    pipeline = ctable.getEntry("pipeline");
    timestamp = ctable.getEntry("timestamp");
    driverMode = ctable.getEntry("driver_mode");
    isValid = ctable.getEntry("is_valid");
    pipeline.setValue(pipelinex);
  }

  public void setVisionPipeline(double pipelinx) {
    pipeline.setValue(pipelinx);
  }

  public Rotational getRotation() {
    return new Rotational(pitch.getDouble(0.0), yaw.getDouble(0.0));
  }

  public void setDriverMode(boolean enabled) {
    driverMode.setBoolean(enabled);
  }

  public boolean isDriverMode() {
    return driverMode.getBoolean(false);
  }

  public double getVisionPipeline() {
    return pipeline.getDouble(0.0);
  }

  public boolean isValidFrame() {
    return isValid.getBoolean(false);
  }

  public double getTimestamp() {
    return timestamp.getDouble(0.0);
  }


}
