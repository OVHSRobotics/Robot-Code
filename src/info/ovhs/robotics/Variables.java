/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package info.ovhs.robotics;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;


/**
 *
 * @author Developer
 */
public class Variables {
    //public RobotDrive(int frontLeftMotor, int rearLeftMotor, int frontRightMotor, int rearRightMotor)
    static RobotDrive Del_Toro = new RobotDrive(1,3,2,4);
//    static Victor Shooter = new Victor(5);
//    static Servo ShooterRelease = new Servo(6);
    static Joystick Joystick = new Joystick(1);
    static Joystick JoystickLeft = Variables.Joystick;
    static double InitialTime = Timer.getFPGATimestamp();
    static SpeedController Motor1 = new Victor(7);
    static SpeedController Motor2 = new Victor(8);
    static SpeedController CatapultMotor2 = new Talon(9);
    static SpeedController CatapultMotor1 = new Talon(10);
    
    static boolean SquaredSpeedControl = true;    
//    static boolean GyroControl = false;

//    
    
     // prmarily used in EncoderReader class
     // Encoder needs a pair of digital channels that are not already allocated
    static Encoder encoder1 = null;
    public static void EncoderCreate() {
    try{
        if(encoder1 == null) encoder1 = new Encoder(14,13, true, EncodingType.k4X);
}
    catch(Exception ex)
    {
        System.out.println(ex);
    }
    }
    
    //Encoder encoder1 = new Encoder(14,13, false, EncodingType.k4X);
     // gets the raw value of the pulse count
     // not affect by encoder types if any are sets
    public double GetRawEncoder()
    {
        double getRaw = encoder1.getDistance();
        return getRaw;
    }
    boolean direction = false;
    
    // do NOT enable any part until it is plugged in
    // make sure each channel value corresponds to the actual value
    // failure to follow the above 2 comments results in an 'already allocated' channel error
//    static Gyro Gyro1 = new Gyro (1);
//    static Accelerometer Accelerometer1 = new Accelerometer (2);
//    static Ultrasonic Ultrasonic1 = new Ultrasonic (7, 8);

    //public Variables() {
        //this.encoder1 = new Encoder (14,13, false);
    //}
}
