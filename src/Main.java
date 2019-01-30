import java.io.File;

import com.googlecode.javacpp.Loader;
import com.googlecode.javacv.cpp.opencv_core;
import com.googlecode.javacv.cpp.opencv_highgui;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

/*import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;*/

public class Main extends Application {

    MediaPlayer mp;
    boolean gestureClick=false;
    int currentIndex=0;
    long mill=0;
    String gestureType=null;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        //String workingDir = System.getProperty("user.dir");
        final File f = new File("./video/bahubali.mp4");
        final File f1 = new File("./images/forward.png");
        final File f2 = new File("./images/backward.png");
        final File f3 = new File("./images/play.png");
        final File f4 = new File("./images/pause.png");
        final File f5 = new File("./images/file.png");
        final File f6 = new File("./images/full.png");
        final File f7 = new File("./images/gesture.png");
        final File video[]=new File[4];
        video[0]=new File("./video/video4.mp4");
        video[1]=new File("./video/video2.mp4");
        video[2]=new File("./video/video3.mp4");
        video[3]=new File("./video/video1.mp4");

        final Media m = new Media(video[currentIndex].toURI().toString());
        mp = new MediaPlayer(m);
        final MediaView mv = new MediaView(mp);
        //mv.setPreserveRatio(true);

        Button play_pause=new Button();
        Button open=new Button();
        Button backward=new Button();
        Button forward=new Button();
        Button fullScreen=new Button();
        Button gesture=new Button();

        ImageView forr=new ImageView(new Image(f1.toURI().toString()));
        forr.setFitHeight(30);
        forr.setFitWidth(30);
        forward.setGraphic(forr);

        ImageView backk=new ImageView(new Image(f2.toURI().toString()));
        backk.setFitHeight(30);
        backk.setFitWidth(30);
        backward.setGraphic(backk);

        ImageView play=new ImageView(new Image(f4.toURI().toString()));
        play.setFitHeight(30);
        play.setFitWidth(30);
        play_pause.setGraphic(play);

        ImageView file=new ImageView(new Image(f5.toURI().toString()));
        file.setFitHeight(30);
        file.setFitWidth(30);
        open.setGraphic(file);

        ImageView full=new ImageView(new Image(f6.toURI().toString()));
        full.setFitHeight(30);
        full.setFitWidth(30);
        fullScreen.setGraphic(full);

        ImageView gest=new ImageView(new Image(f7.toURI().toString()));
        gest.setFitHeight(30);
        gest.setFitWidth(30);
        gesture.setGraphic(gest);

        ImageView pause=new ImageView(new Image(f3.toURI().toString()));
        pause.setFitHeight(30);
        pause.setFitWidth(30);



        BorderPane root = new BorderPane();
        HBox hb=new HBox();
        hb.getChildren().add(open);
        hb.getChildren().add(fullScreen);
        hb.getChildren().add(gesture);
        hb.getChildren().add(backward);
        hb.getChildren().add(play_pause);
        hb.getChildren().add(forward);
        root.setBottom(hb);
        Slider volume=new Slider();
        volume.setValue(50);
        mp.setVolume(0.5);
        hb.getChildren().add(volume);

        HBox.setMargin(volume,new Insets(0,0,0,140));
        HBox.setMargin(backward,new Insets(0,0,0,160));

        volume.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mp.setVolume(volume.getValue()/100);
            }
        });
        volume.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mp.setVolume(volume.getValue()/100);
            }
        });


        Slider videoPosition =new Slider();
        mv.setFitHeight(400);
        StackPane vid=new StackPane();
        vid.getChildren().add(mv);
        vid.getChildren().add(videoPosition);
        StackPane.setMargin(videoPosition,new Insets(0,0,20,0));
        StackPane.setAlignment(videoPosition,Pos.BOTTOM_CENTER);
        root.setCenter(vid);

        videoPosition.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double dur = videoPosition.getValue() / 100 * m.getDuration().toMillis();
                mp.seek(Duration.millis(dur));
            }
        });
        videoPosition.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double dur = videoPosition.getValue() / 100 * m.getDuration().toMillis();
                mp.seek(Duration.millis(dur));
            }
        });

        mp.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                if(!videoPosition.isHover())
                    videoPosition.setValue((newValue.toMillis()/ m.getDuration().toMillis()) * 100);
            }
        });

        final Scene scene = new Scene(root, 800, 540);
        scene.setFill(Color.BLACK);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Full Screen Video Player");
        //primaryStage.setFullScreen(true);
        primaryStage.show();
        mp.play();

        play_pause.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(mp.getStatus()==MediaPlayer.Status.PAUSED)
                {
                    mp.play();
                    play_pause.setGraphic(play);
                }
                else if(mp.getStatus()==MediaPlayer.Status.PLAYING)
                {
                    mp.pause();
                    play_pause.setGraphic(pause);
                }
            }
        });

        forward.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event){
                mp.seek(Duration.millis(mp.getCurrentTime().toMillis()+10000));
            }
        });

        backward.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mp.seek(Duration.millis(mp.getCurrentTime().toMillis()-10000));
            }
        });

        fullScreen.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setFullScreen(true);
                mv.setFitHeight(primaryStage.getHeight());
                mv.setFitWidth(primaryStage.getWidth());
            }
        });

        open.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FileChooser fc=new FileChooser();
                fc.setTitle("Choose Video File");
                File f=fc.showOpenDialog(primaryStage);
                if(f!=null)
                {
                    play_pause.setGraphic(play);
                    mp.pause();
                    Media mm=new Media(f.toURI().toString());
                    mp=new MediaPlayer(mm);
                    mv.setMediaPlayer(mp);
                    mp.play();
                }
            }
        });

        gesture.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                gestureClick=true;
            }
        });










































        //WebCam
        new Thread(new Runnable() {
            @Override
            public void run() {


                opencv_highgui.CvCapture cap=opencv_highgui.cvCreateCameraCapture(opencv_highgui.CV_CAP_ANY);
                IplImage fram,hsv,bin;
                CvMoments moments=new CvMoments(Loader.sizeof(CvMoments.class));
                double x,y,xy;
                int pos_x=0,pos_y=0;
                int frame_count=0,prev_y=0,prev_x=0,difference_y,difference_x;

                CvSeq contour1=new CvSeq();
                CvMemStorage storage=CvMemStorage.create();
                double area_c;
                boolean object=false;

                for(;;)
                {
                    contour1=new CvSeq();
                    frame_count++;
                    fram=opencv_highgui.cvQueryFrame(cap);
                    hsv=opencv_core.cvCreateImage(opencv_core.cvGetSize(fram),opencv_core.IPL_DEPTH_8U,3);
                    bin=opencv_core.cvCreateImage(opencv_core.cvGetSize(fram),opencv_core.IPL_DEPTH_8U,1);
                    cvCvtColor(fram,hsv,CV_BGR2HSV);
                    //yellow 20,100,100,30,255,255
                    opencv_core.cvInRangeS(hsv,cvScalar(30,150,50,0),cvScalar(255,255,180,0),bin);

                    cvFindContours(bin,storage,contour1,Loader.sizeof(CvContour.class),CV_RETR_LIST,CV_LINK_RUNS,cvPoint(0,0));
                    object=false;
                    while(contour1!=null && !contour1.isNull())
                    {
                        area_c=cvContourArea(contour1,CV_WHOLE_SEQ,1);
                        if(area_c<800)
                        {
                            cvDrawContours(bin,contour1,CV_RGB(0,0,0),CV_RGB(0,0,0),0,CV_FILLED,8,cvPoint(0,0));
                        }
                        else
                        {
                            object=true;
                        }
                        contour1=contour1.h_next();
                    }
                    if(!object)
                    {
                        frame_count=0;
                    }

                    cvMoments(bin,moments,1);
                    x=cvGetSpatialMoment(moments,1,0);
                    y=cvGetSpatialMoment(moments,0,1);
                    xy=cvGetCentralMoment(moments,0,0);
                    pos_x=(int)(x/xy);
                    pos_y=(int)(y/xy);
                    if(frame_count==1)
                    {
                        prev_y=pos_y;
                        prev_x=pos_x;
                    }
                    // System.out.println(pos_x+"   "+pos_y);
                    if(pos_x>5 && pos_y>5)
                        cvCircle(fram,cvPoint(pos_x,pos_y),20,cvScalar(0,255,0,0),9,0,0);

                    if(gestureClick)
                    {
                        opencv_highgui.cvShowImage("hsv",hsv);
                        opencv_highgui.cvShowImage("bin",bin);
                        opencv_highgui.cvShowImage("original",fram);
                    }


                    if(frame_count==5)
                    {
                        frame_count=0;
                            difference_y=prev_y-pos_y;
                        difference_x=prev_x-pos_x;
                        if(Math.abs(difference_x)<Math.abs(difference_y))
                        {
                            if(difference_y>0)
                            {
                                if(difference_y>10)
                                {
                                    mp.setVolume(mp.getVolume()+0.1);
                                    volume.setValue(mp.getVolume()*100);
                                }
                            }
                            else if(difference_y<0)
                            {
                                if(difference_y<10)
                                {
                                    mp.setVolume(mp.getVolume()-0.1);
                                    volume.setValue(mp.getVolume()*100);
                                }
                            }
                        }
                        else
                        {
                            if(difference_x>0)
                            {
                                if(difference_x>120)
                                {
                                    mp.pause();
                                    currentIndex=(currentIndex+1)%4;
                                    Media mm=new Media(video[(currentIndex+1)%4].toURI().toString());
                                    mp=new MediaPlayer(mm);
                                    mv.setMediaPlayer(mp);
                                    mp.play();
                                }
                            }
                            else if(difference_x<0)
                            {
                                if(difference_x<120)
                                {
                                    mp.pause();
                                    if(currentIndex==0)
                                        currentIndex=4;
                                    currentIndex=currentIndex-1;
                                    Media mm=new Media(video[currentIndex==0?3:(currentIndex-1)].toURI().toString());
                                    mp=new MediaPlayer(mm);
                                    mv.setMediaPlayer(mp);
                                    mp.play();
                                }

                            }
                        }

                    }
                    char ch=(char)opencv_highgui.cvWaitKey(30);
                    if(ch==27)
                    {
                        break;
                    }
                }
            }
        }).start();

    }
}
