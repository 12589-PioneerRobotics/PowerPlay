package org.firstinspires.ftc.teamcode.hardwaretests

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.CRServo
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.drive.GamepadEventPS
import org.firstinspires.ftc.teamcode.hardware.ActuationConstants
import org.firstinspires.ftc.teamcode.hardware.Lift

@TeleOp(name = "Intake Test")
class IntakeTest : OpMode() {
    private lateinit var leftExtension: CRServo
    private lateinit var rightExtension: CRServo

    private lateinit var rightArm: Servo
    private lateinit var leftArm: Servo

    private lateinit var claw: Servo

    private lateinit var lift: Lift

    private lateinit var gamepadEvent: GamepadEventPS

    override fun init() {
        leftExtension = hardwareMap.crservo.get("leftExtension")
        rightExtension = hardwareMap.crservo.get("rightExtension")

        leftArm = hardwareMap.servo.get("leftArm")
        rightArm = hardwareMap.servo.get("rightArm")

        leftArm.position = 0.0
        rightArm.position = 1.0

        claw = hardwareMap.servo.get("claw")
        claw.position = 0.0

        lift = Lift(hardwareMap)

        gamepadEvent = GamepadEventPS(gamepad1)
        telemetry.addLine("Initialized")
    }

    override fun loop() {
        if (gamepad1.dpad_up) {
            leftExtension.power = -1.0
            rightExtension.power = 1.0
        } else if (gamepad1.dpad_down) {
            leftExtension.power = 1.0
            rightExtension.power = -1.0
        } else {
            leftExtension.power = 0.0
            rightExtension.power = 0.0
        }

        if (gamepadEvent.triangle()) {
            leftArm.position -= 0.05
            rightArm.position += 0.05
        }
        if (gamepadEvent.cross()) {
            leftArm.position += 0.05
            rightArm.position -= 0.05
        }

        if (gamepad1.left_trigger > 0.5)
            lift.raiseLift(ActuationConstants.LiftConstants.LIFT_INCREMENT)
        else if (gamepad1.right_trigger > 0.5)
            lift.lowerLift(ActuationConstants.LiftConstants.LIFT_INCREMENT)

        if (gamepadEvent.dPadRight()) {
            claw.position += 0.05
        }
        if (gamepadEvent.dPadLeft()) {
            claw.position -= 0.05
        }

        telemetry.addData("Left Extension Power", leftExtension.power)
        telemetry.addData("Right Extension Power", rightExtension.power)
        telemetry.addData("Left Arm Position", leftArm.position)
        telemetry.addData("Right Arm Position", rightArm.position)
        telemetry.addData("Claw Position", claw.position)
        telemetry.update()
    }
}