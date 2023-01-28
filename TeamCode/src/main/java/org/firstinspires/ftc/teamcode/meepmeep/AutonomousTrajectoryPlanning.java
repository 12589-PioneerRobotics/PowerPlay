//package org.firstinspires.ftc.teamcode.meepmeep;
//
//import com.acmerobotics.roadrunner.geometry.Pose2d;
//import com.noahbres.meepmeep.MeepMeep;
//import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
//import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;
//
//public class AutonomousTrajectoryPlanning {
//    public static void main(String[] args) {
//        System.setProperty("sun.java2d.opengl", "true");
//        MeepMeep meepMeep = new MeepMeep(640);
//
//        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
//                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
//                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 10.4)
//                .setStartPose(new Pose2d(-36.75, 58, Math.toRadians(-90)))
//                .followTrajectorySequence(drive ->
//                        drive.trajectorySequenceBuilder(drive.getPoseEstimate())
//                                .lineToSplineHeading(new Pose2d(-30, 45, Math.toRadians(-120)))
//                                .build()
//                );
//
//        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
//                .setDarkMode(true)
//                .setBackgroundAlpha(0.95f)
//                .addEntity(myBot)
//                .start();
//    }
//}
