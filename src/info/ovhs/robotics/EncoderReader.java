/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package info.ovhs.robotics;


/**
 *
 * @author Connor
 */
public class EncoderReader extends Variables {
    
    // calculates distance encoder travels per pulse
    // pulse range is [400-1440]
    double distPerPulse = (360/250)*(22/36); // 0.391304348
    boolean stopped = false;

    // resets the count, starts the encoder reader and sets distance to be read per pulse in degrees
    public void EncoderReader () {
        CatapultMotor1.set(0.00);
        CatapultMotor2.set(0.00);
        encoder1.reset();
        encoder1.start();
        // distance in degrees assuming 920 pulses per revolution
        encoder1.setDistancePerPulse(distPerPulse);
    }
    
    // resets encoder's count
    public void Reset () {
        encoder1.reset();
    }
    
    // return current speed of motor that encoder is reading
    public double Rate () {
        double Rate = encoder1.getRate();
        return Rate;
    }
    
    // sets distance per pulse to a given double
    public void SetDPP (double x) {
        encoder1.setDistancePerPulse(x);
    }
    
    // stops motor if the raw pulse value is less than 306 (about 120 degree)
    // can be called within a loop to check every cycle
    public void Stop_Forward () {
        // 306 pulses is approximately 120 degrees using above assumptions
        if ((GetRawEncoder() >= 120) && (direction == false)){
            stopped = true;
            //CatapultMotor1.set(0.00);
            //CatapultMotor2.set(0.00);
        }
    }
    
    // checks is motor is stopped, then reverses the direction the motors will spin if it is
    public void Reverse () {
        if (stopped) {
        encoder1.setReverseDirection(!direction);
        direction = !direction;
        stopped = false;
        }
    }
    
    public void Start () {
        encoder1.start();
    }
    
    public void Stop_Reverse () {
        if (GetRawEncoder() <= 0) {
            stopped = true;
            CatapultMotor1.set(0.00);
            CatapultMotor2.set(0.00);
        }
    }
   
    
    // sets motors work at full speed in one direction
    public void StartMotorsFull () {
        // might want to change values depending on how long it take for the motor to stop
        if ((GetRawEncoder() <= 120) && (direction == false)) {
            CatapultMotor1.set(-1.0);
            CatapultMotor2.set(1.0);
        }
    }
    
    public void StartMotorsToss () {
        // might want to change values depending on how long it take for the motor to stop
        if ((GetRawEncoder() <= 120) && (direction == false)) {
            CatapultMotor1.set(-.75);
            CatapultMotor2.set(.75);
        }
    }
    public void StartMotorsMedium () {
        // might want to change values depending on how long it take for the motor to stop
        if ((GetRawEncoder() <= 120) && (direction == false)) {
            CatapultMotor1.set(-.55);
            CatapultMotor2.set(.55);
    }
    }
    
    public void StartMotorsPass () {
        // might want to change values depending on how long it take for the motor to stop
        if ((GetRawEncoder() <= 120) && (direction == false)) {
            CatapultMotor1.set(-.10);
            CatapultMotor2.set(.10);
        }
    }
    
    public void StartMotorsCustom (double power) {
        // might want to change values depending on how long it take for the motor to stop
        if ((GetRawEncoder() <= 120) && (direction == false)) {
            CatapultMotor1.set(-power);
            CatapultMotor2.set(power);
        }
    }
    
    public void ReverseMotors () {
        // see previous comment
        if ((GetRawEncoder() >= 0) && (direction == true)){
            CatapultMotor1.set(.10);
            CatapultMotor2.set(-.10);
        }
    }
    
    public void Free () {
        encoder1.free();
    }
    
    // for testing setDistancePerPulse
    // should stop both motors if one revolution is completed (360 degrees)
    public void stopAtRevolution() {
        if (encoder1.getDistance() >= 360) {
            //CatapultMotor1.set(0.00);
            //CatapultMotor2.set(0.00);
        }    
    }
    
    // prints distance with all modifier accounted for
    public void printDistance() {
        System.out.println(encoder1.getDistance());
    }
    
    // prints raw distance value
    public void printRaw() {
        System.out.println(encoder1.getRaw());
    }
    
    public double getEncDistance() {
        return encoder1.getDistance();
    }
}
