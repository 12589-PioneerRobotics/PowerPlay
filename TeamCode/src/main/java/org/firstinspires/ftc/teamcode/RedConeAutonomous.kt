package org.firstinspires.ftc.teamcode

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.trajectory.Trajectory
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName
import org.firstinspires.ftc.teamcode.cvtests.AprilTagDetectionPipeline
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive
import org.firstinspires.ftc.teamcode.hardware.ActuationConstants
import org.firstinspires.ftc.teamcode.hardware.Lift
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence
import org.openftc.apriltag.AprilTagDetection
import org.openftc.easyopencv.OpenCvCamera
import org.openftc.easyopencv.OpenCvCameraFactory
import org.openftc.easyopencv.OpenCvCameraRotation

@Autonomous(name = "Red Cone Autonomous")
class RedConeAutonomous: LinearOpMode() {
    private val fx = 578.272
    private val fy = 578.272
    private val cx = 402.145
    private val cy = 221.506
    private val tagsize = 0.166
    private var tagId = 2

    private lateinit var park: TrajectorySequence
    private lateinit var lift: Lift
    private lateinit var drive: SampleMecanumDrive

    override fun runOpMode() {
        lift = Lift(hardwareMap)
        drive = SampleMecanumDrive(hardwareMap)
        drive.poseEstimate = Pose2d(0.0, 0.0, Math.toRadians(90.0))

        val cameraMonitorViewId = hardwareMap.appContext.resources.getIdentifier(
            "cameraMonitorViewId",
            "id",
            hardwareMap.appContext.packageName
        )

        val camera = OpenCvCameraFactory.getInstance().createWebcam(
            hardwareMap.get(
                WebcamName::class.java, "Webcam 1"
            ), cameraMonitorViewId
        )

        val pipeline = AprilTagDetectionPipeline(tagsize, fx, fy, cx, cy)

        camera.setPipeline(pipeline)

        camera.openCameraDeviceAsync(object : OpenCvCamera.AsyncCameraOpenListener {
            override fun onOpened() {
                camera.startStreaming(1280, 720, OpenCvCameraRotation.UPRIGHT)
            }

            override fun onError(errorCode: Int) {}
        })

        FtcDashboard.getInstance().startCameraStream(camera, 0.0)

        while (!isStarted && !isStopRequested) {
            val currentDetections: ArrayList<AprilTagDetection> = pipeline.latestDetections

            if (currentDetections.size != 0) {
                when (currentDetections[0].id) {
                    11 -> tagId = 1
                    15 -> tagId = 3
                }
            }

            telemetry.addData("Tag ID", tagId)
            telemetry.update()
            sleep(20)
        }

        val toPole: TrajectorySequence = drive.trajectorySequenceBuilder(drive.poseEstimate)
            .addDisplacementMarker {
                lift.closeClaw()
            }
            .strafeLeft(42.0)
            .back(15.0)
            .addDisplacementMarker {
                lift.setLiftPosition(ActuationConstants.LiftConstants.THIRD_LEVEL + 300)
            }
            .forward(28.0)
            .build()

        drive.followTrajectorySequence(toPole)

        when (tagId) {
            1 -> park = drive.trajectorySequenceBuilder(toPole.end())
                .addDisplacementMarker {
                    lift.openClaw()
                }
                .back(5.0)
                 .addDisplacementMarker {
                     lift.setLiftPosition(ActuationConstants.LiftConstants.IDLE)
                 }
                .strafeRight(10.0)
                .build()
            2 -> park = drive.trajectorySequenceBuilder(toPole.end())
                .addDisplacementMarker {
                    lift.openClaw()
                }
                .back(5.0)
                 .addDisplacementMarker {
                     lift.setLiftPosition(ActuationConstants.LiftConstants.IDLE)
                 }
                .strafeRight(40.0)
                .build()
            3 -> park = drive.trajectorySequenceBuilder(toPole.end())
                .addDisplacementMarker {
                    lift.openClaw()
                }
                .back(5.0)
                .addDisplacementMarker {
                    lift.setLiftPosition(ActuationConstants.LiftConstants.IDLE)
                }
                .strafeRight(70.0)
                .build()
        }

        drive.followTrajectorySequence(park)
    }
}