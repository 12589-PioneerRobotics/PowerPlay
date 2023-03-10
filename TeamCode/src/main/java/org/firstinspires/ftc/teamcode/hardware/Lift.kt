package org.firstinspires.ftc.teamcode.hardware

import com.qualcomm.robotcore.hardware.*
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry

class Lift (hardwareMap: HardwareMap) {
    lateinit var lift: DcMotorEx
    lateinit var claw: Servo
//    lateinit var armLeft: Servo
//    lateinit var armRight: Servo
    lateinit var clawState: ClawState

    init {
        if (hardwareMap.dcMotor.contains("lift")) {
            lift = hardwareMap.dcMotor.get("lift") as DcMotorEx
            lift.power = 1.0
            lift.targetPosition = ActuationConstants.LiftConstants.IDLE
            lift.mode = DcMotor.RunMode.RUN_TO_POSITION
            lift.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        }

        if (hardwareMap.servo.contains("claw")) {
            claw = hardwareMap.servo.get("claw")
            claw.position = ActuationConstants.ClawConstants.OPEN
            clawState = ClawState.OPEN
        }
    }

    fun setLiftPosition(position: Int) {
        if (lift.targetPosition < lift.currentPosition)
            lift.power = 0.5
        lift.targetPosition = position
        lift.power = 1.0
    }

    fun raiseLift(increment: Int) {
        lift.targetPosition += increment
    }

    fun lowerLift(increment: Int) {
        lift.power = 0.5
        lift.targetPosition -= increment
        lift.power = 1.0
    }

    fun openClaw() {
        clawState = ClawState.OPEN
        claw.position = ActuationConstants.ClawConstants.OPEN
        raiseLift(ActuationConstants.LiftConstants.LIFT_INCREMENT * 3)
    }

    fun closeClaw() {
        clawState = ClawState.CLOSED
        claw.position = ActuationConstants.ClawConstants.CLOSED
    }

    enum class ClawState {
        OPEN,
        CLOSED
    }
}