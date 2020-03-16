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
    driverMode = ctable.getEntry("driverMode");
    isValid = ctable.getEntry("isValid");
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
    driverMode = ctable.getEntry("driverMode");
    isValid = ctable.getEntry("isValid");
    pipeline.setValue(pipelinex);
  }

  /**
   * Sets the pipeline of the camera.
   * @param pipelinex the desired pipeline
   */
  public void setVisionPipeline(double pipelinex) {
    pipeline.setValue(pipelinex);
  }

  /**
   * Returns the degree to the target. Can either be Yaw or Pitch.
   * @return 
   */
  public Rotational getRotation() {
    return new Rotational(pitch.getDouble(0.0), yaw.getDouble(0.0));
  }

  /**
   * Sets driver Mode.
   * @param enabled the desired state of driver Mode. True or false.
   */
  public void setDriverMode(boolean enabled) {
    driverMode.setBoolean(enabled);
  }

  /**
   * Returns whether driverMode is enabled.
   * @return
   */
  public boolean isDriverMode() {
    return driverMode.getBoolean(false);
  }

  /**
   * gets the current pipeline used by the camera.
   * 0 is the vision processing pipeline
   * -1 is the driver pipeline
   * @return pipeline as a double
   */
  public double getVisionPipeline() {
    return pipeline.getDouble(0.0);
  }

  /**
   * Returns whether a vision target is detected.
   * @return
   */
  public boolean isValidFrame() {
    return isValid.getBoolean(false);
  }

  /**
   * Returns the timestamp as a double.
   * @return
   */
  public double getTimestamp() {
    return timestamp.getDouble(0.0);
  }
}
