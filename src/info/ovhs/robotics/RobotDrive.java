/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

    //code
    // descriptive comments
//     suggetions and concerns
package info.ovhs.robotics;

import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.lang.Math;

//import edu.wpi.first.wpilibj.RobotDrive;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SimpleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotDrive extends SimpleRobot {
    
        EncoderReader EncoderRead = new EncoderReader();    
//    
    /**
     * This function is called once each time the robot enters autonomous mode.
     */
  public void autonomous() {

                //turns off safety
        Variables.Del_Toro.setSafetyEnabled(false);
        Variables.EncoderCreate();
        
        //driving
        double inputSpeedX = 0;
        double inputSpeedY = -1;
        double inputSpeedTheta = 0;  
        double inputSpeedX2 = 0;
        double inputSpeedY2 = -.25;
        double inputSpeedTheta2 = 0;
        // Invert the two front motors because of the mirroring of the gearboxes
        Variables.Del_Toro.setInvertedMotor(edu.wpi.first.wpilibj.RobotDrive.MotorType.kRearRight, true);
        Variables.Del_Toro.setInvertedMotor(edu.wpi.first.wpilibj.RobotDrive.MotorType.kFrontRight, true);
        // Sets the robot moving full speed for 2 seconds
        Variables.Del_Toro.mecanumDrive_Cartesian(inputSpeedX, inputSpeedY, inputSpeedTheta, 0);
        Timer.delay(2);
        // Sets the robot moving at 1/4 speed for 2.5 seconds.
        Variables.Del_Toro.mecanumDrive_Cartesian(inputSpeedX2, inputSpeedY2, inputSpeedTheta2, 0);
        Timer.delay(2.5);
        // Stops the robot
        Variables.Del_Toro.mecanumDrive_Cartesian(0, 0, 0, 0);
       
        //catapult  {{{{{{
        // Warning Text for Debugging
        String warningText;
        
        // Flags for Catapult Launch
        boolean flag1 = false;
        boolean flag2 = false;
        // Debugging tool to tell if catapult launch completed
        boolean completed = false;
        // Sets forward angle to 140
        double angle = 140;
        // Sets backwards angle to 15
        double angleback = 15;
        // Sets the resetdelay 1 and 2 for the catapult
        double resetDelay = 0;
        double resetDelay2 = 0;
       
        //statement 1
        flag1 = true;
        completed = false;
        warningText = "Running Catapult System";
       while (flag1 || flag2){
       warningText = "yes";
           //statement 2
        if ((EncoderRead.getEncDistance() < angle) && flag1 && !flag2 && !completed){
            Variables.CatapultMotor1.set(1);
            Variables.CatapultMotor2.set(-1);
            
        }
        //statement 3
        else if (flag1 && (EncoderRead.getEncDistance() > angle) && !flag2 && !completed){
            flag2 = true;
            Variables.CatapultMotor1.set(0.00);
            Variables.CatapultMotor2.set(0.00);
            resetDelay = System.currentTimeMillis();
            flag1 = false;
        }
       
        //statement 4
        else if (flag2 && resetDelay2 < (resetDelay + 250) && !completed ) {
            resetDelay2 = System.currentTimeMillis();
        }
        
        //statement 5
        else if ((resetDelay2 >= (resetDelay + 250)) && (EncoderRead.getEncDistance() > angleback && !completed )) {
            Variables.CatapultMotor1.set(-.25);
            Variables.CatapultMotor2.set(.25);
        }
        //statement 6
        else {
            Variables.CatapultMotor1.set(0.00);
            Variables.CatapultMotor2.set(0.00);
            flag2 = false;
            flag1 = false;
            completed = true;
            warningText = "Catapult system completed";
        }
        
                
//Driving SmartDashboard Outputs    
SmartDashboard.putNumber("Input Speed X", inputSpeedX);
SmartDashboard.putNumber("Input Speed Y", inputSpeedY);
SmartDashboard.putNumber("Input Speed Theta", inputSpeedTheta);
//Motor Value outputs for grabber
SmartDashboard.putNumber("Motor 1", Variables.Motor1.getSpeed());
SmartDashboard.putNumber("Motor 2", Variables.Motor2.getSpeed());
//Catapult motor value outputs
SmartDashboard.putNumber("Catapult Motor 1", Variables.CatapultMotor1.getSpeed());
SmartDashboard.putNumber("Catapult Motor 2", Variables.CatapultMotor2.getSpeed());  
//Catapult SmartDashboard Outputs
SmartDashboard.putBoolean("Flag1", flag1);
SmartDashboard.putBoolean("Flag2", flag2);
SmartDashboard.putNumber("ResetDelay1", resetDelay);
SmartDashboard.putNumber("ResetDelay2", resetDelay2);
SmartDashboard.putNumber("Current Time", System.currentTimeMillis());        
SmartDashboard.putNumber("EncoderReader Distance", EncoderReader.encoder1.getDistance());
SmartDashboard.putString("Warning Text for Debugging", warningText);
SmartDashboard.putBoolean("Completed", completed);
       } 
    }
    /**
     * This function is called once each time the robot enters operator control.
     */
    public void operatorControl() {

    //driving
        double inputSpeedX;
        double inputSpeedY;
        double inputSpeedTheta;
        double lastPressBackButton = 0;
        
        Variables.EncoderCreate();
 //encoder & catapult       
        long resetDelay = 0;
        long resetDelay2 = 0;
        EncoderRead.Start();
         boolean flag1 = false;  //Catapult Motor Flag
         boolean flag2 = false;
         
         //creates variable to control power of catapult motors
         double catapultPower = 0;
        while (isOperatorControl() && isEnabled()) { 
        //driving
            inputSpeedX = Variables.Joystick.getRawAxis(1);
            inputSpeedY = Variables.Joystick.getRawAxis(2);
            inputSpeedTheta = Variables.Joystick.getRawAxis(4);
            
            // toggles deadzone
            boolean isDead = false;
            
            // controls size of deadzone
            double zoneSize = 1;
            
            // create deadzone on controllers
            if ((inputSpeedX <= zoneSize) && (inputSpeedX >= -zoneSize) && isDead){
                inputSpeedX = 0;
            }
            if ((inputSpeedY <= zoneSize) && (inputSpeedY >= -zoneSize && isDead)) {
                inputSpeedY = 0;
            }
            if ((inputSpeedTheta <= zoneSize) && (inputSpeedTheta >= -zoneSize) && isDead) {
                inputSpeedTheta = 0;
            }
            // Use back button to change squared inputs
            // Also use 1000ms button debounce time
            if (Variables.Joystick.getRawButton(7)
                    && System.currentTimeMillis()-lastPressBackButton > 1000)
            {
                // Update squared speed control variable
                Variables.SquaredSpeedControl = !Variables.SquaredSpeedControl;
                
                // Update last button press time
                lastPressBackButton = System.currentTimeMillis();
            }
            SmartDashboard.putBoolean("Squared Speeds", Variables.SquaredSpeedControl);
            // Convert if using squared inputs
            if (Variables.SquaredSpeedControl)
            {
                inputSpeedX = inputSpeedX * Math.abs(inputSpeedX);
                inputSpeedY = inputSpeedY * Math.abs(inputSpeedY);
                inputSpeedTheta = inputSpeedTheta * Math.abs(inputSpeedTheta);
            } 
        // Invert the two front motors because of the mirroring of the gearboxes
        Variables.Del_Toro.setInvertedMotor(edu.wpi.first.wpilibj.RobotDrive.MotorType.kRearRight, true);
        Variables.Del_Toro.setInvertedMotor(edu.wpi.first.wpilibj.RobotDrive.MotorType.kFrontRight, true);
        // Set motors on robot to desired values
        Variables.Del_Toro.mecanumDrive_Cartesian(inputSpeedX, inputSpeedY, inputSpeedTheta, 0);
//        }
        // Putting the numberson the Smart Dashboard for use to tell what the inputs are and for personal help

//grabber speeds        
            double GrabberSpeed = .50;
            double GrabberSpeed2 = .3;
// Catapult up angle and down angle
            double angle = 115;
            double angleback = 15;
         

        
        
        //catapult
        // x-box button 'A'
        // 100% power
        //statement 1
        if (Variables.Joystick.getRawButton(1)){
            flag1 = true;
            catapultPower = 1;
        }          
        
        // x-box button 'X'
        // 80% power
        if (Variables.Joystick.getRawButton(3)){
            flag1 = true;
            catapultPower = 0.8;
        }
        
        // x-box button 'Y'
        // 70% power
        if (Variables.Joystick.getRawButton(4)){
            flag1 = true;
            catapultPower = 0.7;
        }
        if (Variables.Joystick.getRawButton(2)) {
            flag1 = true;
            catapultPower = .5;
        }
        
        //statement 2
        if ((EncoderRead.getEncDistance() < angle) && flag1 && !flag2){
            Variables.CatapultMotor1.set(catapultPower);
            Variables.CatapultMotor2.set(-catapultPower);
        }
        //statement 3
        else if (flag1 && (EncoderRead.getEncDistance() > angle) && !flag2){
            flag2 = true;
            Variables.CatapultMotor1.set(0.00);
            Variables.CatapultMotor2.set(0.00);
            resetDelay = System.currentTimeMillis();
            flag1 = false;
        }
       
        //statement 4
        else if (flag2 && resetDelay2 < (resetDelay + 250) ) {
            resetDelay2 = System.currentTimeMillis();
        }
        
        //statement 5
        else if ((resetDelay2 >= (resetDelay + 250)) && (EncoderRead.getEncDistance() > angleback )) {
            Variables.CatapultMotor1.set(-.25);
            Variables.CatapultMotor2.set(.25);
        }
        //statement 6
        else {
            Variables.CatapultMotor1.set(0.00);
            Variables.CatapultMotor2.set(0.00);
            flag2 = false;
            flag1 = false;
        }
        
//          
Reverse MotorReverse = new Reverse();
if (Variables.Joystick.getRawButton(7)) {
    MotorReverse.ReverseMotors();
    EncoderReader.encoder1.reset();
}


//grabber
// Loader and unloader code
// getRawButton(5) refers to left bumper, (6) refers to right bumper
        if (Variables.Joystick.getRawButton(5) && !Variables.Joystick.getRawButton(6)) {
            // Pick up ball
            Variables.Motor1.set(GrabberSpeed);
            Variables.Motor2.set(-GrabberSpeed);
        }
        else if (Variables.Joystick.getRawButton(6) && !Variables.Joystick.getRawButton(5)) {
            // Release ball
            Variables.Motor1.set(GrabberSpeed2);
            Variables.Motor2.set(-GrabberSpeed2);
        }
        else if (Variables.Joystick.getRawButton(5) && Variables.Joystick.getRawButton(6)) {
            // sets speed to predetermined constant
            // 42% speed was the minimm speed in testing
            Variables.Motor1.set(.42);
            Variables.Motor2.set(-.42);
        }
        else {
            Variables.Motor1.set(0);
            Variables.Motor2.set(0);
        }

        
//Driving SmartDashboard Outputs    
SmartDashboard.putNumber("Input Speed X", inputSpeedX);
SmartDashboard.putNumber("Input Speed Y", inputSpeedY);
SmartDashboard.putNumber("Input Speed Theta", inputSpeedTheta);
//Motor Value outputs for grabber
SmartDashboard.putNumber("Motor 1", Variables.Motor1.getSpeed());
SmartDashboard.putNumber("Motor 2", Variables.Motor2.getSpeed());
//Catapult motor value outputs
SmartDashboard.putNumber("Catapult Motor 1", Variables.CatapultMotor1.getSpeed());
SmartDashboard.putNumber("Catapult Motor 2", Variables.CatapultMotor2.getSpeed());  
//Catapult SmartDashboard Outputs
SmartDashboard.putBoolean("Flag1", flag1);
SmartDashboard.putBoolean("Flag2", flag2);
SmartDashboard.putNumber("ResetDelay1", resetDelay);
SmartDashboard.putNumber("ResetDelay2", resetDelay2);
SmartDashboard.putNumber("Current Time", System.currentTimeMillis());        
SmartDashboard.putNumber("EncoderReader Distance", EncoderReader.encoder1.getDistance());



SmartDashboard.getNumber("Input Speed X", inputSpeedX);
SmartDashboard.getNumber("Input Speed Y", inputSpeedY);
SmartDashboard.getNumber("Input Speed Theta", inputSpeedTheta);
SmartDashboard.getBoolean("Flag1", flag1);
        } 
    }
    
    
    /**
     * This function is called once each time the robot enters test mode.
     */
    public void test() {
    
    }
    

}
