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
public class Reverse extends Variables {
    public void ReverseMotors() {
            CatapultMotor1.set(-0.15);
            CatapultMotor2.set(0.15);
    }
}
