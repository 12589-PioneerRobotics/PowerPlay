package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.drive.FieldConstants;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.PipelineRecordingParameters;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

// /home/programlaptop/Documents/test.mp4

@TeleOp
public class LocalizationLogging extends LinearOpMode {
    OpenCvCamera phoneCam;
    SampleMecanumDrive drive;

    @Override
    public void runOpMode() throws InterruptedException {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        phoneCam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);

        phoneCam.setPipeline(new SamplePipeline());
        phoneCam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                phoneCam.startStreaming(640, 480, OpenCvCameraRotation.SIDEWAYS_LEFT);
            }

            @Override
            public void onError(int errorCode)
            {
                /*
                 * This will be called if the camera could not be opened
                 */
            }
        });

        drive = new SampleMecanumDrive(hardwareMap);
        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        telemetry.addLine("Waiting for start");
        telemetry.update();

        waitForStart();

        while (opModeIsActive())
        {
            /*
             * Send some stats to the telemetry
             */
            drive.setWeightedDrivePower(
                    new Pose2d(
                            -gamepad1.left_stick_y,
                            -gamepad1.left_stick_x,
                            -gamepad1.right_stick_x
                    )
            );

            telemetry.addData("Coords: ", drive.getPoseEstimate());
            telemetry.update();

            /*
             * For the purposes of this sample, throttle ourselves to 10Hz loop to avoid burning
             * excess CPU cycles for no reason. (By default, telemetry is only sent to the DS at 4Hz
             * anyway). Of course in a real OpMode you will likely not want to do this.
             */
            sleep(100);
        }
    }


    class SamplePipeline extends OpenCvPipeline {
        boolean toggleRecording = false;
        int frames = 0;

        @Override
        public Mat processFrame(Mat input) {
            String path = "frame" + frames + ".png";

            telemetry.addData("CurrentFrame: ", path);

            try {
                File frame = new File(path);
                if (frame.createNewFile()) {
                    telemetry.addLine("Created File");
                }
            }
            catch (IOException e) {
                telemetry.addLine(e.toString());
            }

            try {
                FileWriter writer = new FileWriter(path);
                writer.write(input.toString());
                writer.close();
            }
            catch (IOException e) {
                telemetry.addLine("Could not write to file");
            }

            frames += 1;
            return input;
        }

//        @Override
//        public void onViewportTapped()
//        {
//            telemetry.addData("Tapped", "");
//            telemetry.update();
//
//            toggleRecording = !toggleRecording;
//
//            if(toggleRecording) {
//                /*
//                 * This is all you need to do to start recording.
//                 */
//                phoneCam.startRecordingPipeline(
//                        new PipelineRecordingParameters.Builder()
//                                .setBitrate(4, PipelineRecordingParameters.BitrateUnits.Mbps)
//                                .setEncoder(PipelineRecordingParameters.Encoder.H264)
//                                .setOutputFormat(PipelineRecordingParameters.OutputFormat.MPEG_4)
//                                .setFrameRate(30)
//                                .setPath("/home/programlaptop/Documents/test.mp4")
//                                .build());
//            } else {
//                /*
//                 * Note: if you don't stop recording by yourself, it will be automatically
//                 * stopped for you at the end of your OpMode
//                 */
//                phoneCam.stopRecordingPipeline();
//            }
//        }
    }
}