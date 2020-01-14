package frc.robot.modules;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrain; 
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

public class ChameleonVision {
    private NetworkTableEntry pitch;
    private NetworkTableEntry yaw;
    private NetworkTableEntry pipeline;
    private NetworkTableEntry timestamp;
    private NetworkTableEntry driver_mode;
    private NetworkTableEntry is_valid;

    

    public ChameleonVision(String camera){
        NetworkTableInstance netTables = NetworkTableInstance.getDefault();
        NetworkTable ctable = netTables.getTable("chameleon-vision").getSubTable(camera);
        pitch = ctable.getEntry("pitch");
        yaw = ctable.getEntry("yaw");
        pipeline = ctable.getEntry("pipeline");
        timestamp = ctable.getEntry("timestamp");
        driver_mode = ctable.getEntry("driver_mode");
        is_valid = ctable.getEntry("is_valid");
    }
    public ChameleonVision(String camera, double pipelinex){
        NetworkTableInstance netTables = NetworkTableInstance.getDefault();
        NetworkTable ctable = netTables.getTable("chameleon-vision").getSubTable(camera);
        pitch = ctable.getEntry("pitch");
        yaw = ctable.getEntry("yaw");
        pipeline = ctable.getEntry("pipeline");
        timestamp = ctable.getEntry("timestamp");
        driver_mode = ctable.getEntry("driver_mode");
        is_valid = ctable.getEntry("is_valid");
        pipeline.setValue(pipelinex);
    }
    public void SetVisionPipeline(double pipelinx)
    {
        pipeline.setValue(pipelinx);
    }
    public Rotational GetRotation()
    {
        return new Rotational(pitch.getDouble(0.0), yaw.getDouble(0.0));
    }
    public void SetDriverMode(boolean modes)
    {
        driver_mode.setValue(modes);
    }
    public boolean GetDriverMode()
    {
        return driver_mode.getBoolean(false);
    }
    public double GetVisionPipeline()
    {
        return pipeline.getDouble(0.0);
    }
    public boolean GetValidity()
    {
        return is_valid.getBoolean(false);
    }
    public double GetTimestamp()
    {
        return timestamp.getDouble(0.0);
    }


}
