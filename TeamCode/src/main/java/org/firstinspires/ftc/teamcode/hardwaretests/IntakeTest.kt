package org.firstinspires.ftc.teamcode.hardwaretests

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.drive.GamepadEventPS


@TeleOp(name = "Intake Test")
class IntakeTest : OpMode() {
    private lateinit var leftExtension: Servo
    private lateinit var rightExtension: Servo

//    private lateinit var rightArm: Servo
//    private lateinit var leftArm: Servo
//
//    private lateinit var claw: Servo

    private lateinit var gamepadEvent: GamepadEventPS

    override fun init() {
        leftExtension = hardwareMap.servo.get("leftExtension")
        rightExtension = hardwareMap.servo.get("rightExtension")

        leftExtension.position = 0.0
        rightExtension.position = 0.0

//        leftArm = hardwareMap.servo.get("leftArm")
//        rightArm = hardwareMap.servo.get("rightArm")
//
//        leftArm.position = 0.0
//        rightArm.position = 1.0
//
//        claw = hardwareMap.servo.get("claw")
//        claw.position = 0.0

        gamepadEvent = GamepadEventPS(gamepad1)
        telemetry.addLine("Initialized")
    }

    override fun loop() {
        if (gamepadEvent.dPadUp()) {
            leftExtension.position += 0.05
            rightExtension.position += 0.05
        }
        if (gamepadEvent.dPadDown()) {
            leftExtension.position -= 0.05
            rightExtension.position -= 0.05
        }

//        if (gamepadEvent.triangle()) {
//            leftArm.position -= 0.05
//            rightArm.position += 0.05
//        }
//        if (gamepadEvent.cross()) {
//            leftArm.position += 0.05
//            rightArm.position -= 0.05
//        }
//
//        if (gamepad1.left_trigger > 0.5)
//            lift.raiseLift(ActuationConstants.LiftConstants.LIFT_INCREMENT)
//        else if (gamepad1.right_trigger > 0.5)
//            lift.lowerLift(ActuationConstants.LiftConstants.LIFT_INCREMENT)
//
//        if (gamepadEvent.dPadRight()) {
//            claw.position += 0.05
//        }
//        if (gamepadEvent.dPadLeft()) {
//            claw.position -= 0.05
//        }

        telemetry.addData("Left Extension Position", leftExtension.position)
        telemetry.addData("Right Extension Position", rightExtension.position)
//        telemetry.addData("Left Arm Position", leftArm.position)
//        telemetry.addData("Right Arm Position", rightArm.position)
//        telemetry.addData("Claw Position", claw.position)
        telemetry.update()
    }
}