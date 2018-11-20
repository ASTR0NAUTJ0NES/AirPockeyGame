/**
 * Skeleton for 'AirPockey.fxml' Controller Class
 * 
 * @author - Andrew Peyton Albanese
 * @version - 1.0 Build 1 Nov 5, 2017
 */

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Sphere;
import javafx.util.Duration;

public class AirPockeyController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="anchorPaneOuter"
    private AnchorPane anchorPaneOuter; // Value injected by FXMLLoader
    
    @FXML // fx:id="menuBar"
    private MenuBar menuBar; // Value injected by FXMLLoader

    @FXML // fx:id="closeMenu"
    private MenuItem closeMenu; // Value injected by FXMLLoader

    @FXML // fx:id="anchorPaneInner"
    private AnchorPane anchorPaneInner; // Value injected by FXMLLoader

    @FXML // fx:id="AirPockeyBoardImage"
    private ImageView AirPockeyBoardImage; // Value injected by FXMLLoader

    @FXML // fx:id="AirPockeyBall"
    private Sphere AirPockeyBall; // Value injected by FXMLLoader

    @FXML // fx:id="player1Paddle"
    private Rectangle player1Paddle; // Value injected by FXMLLoader

    @FXML // fx:id="player2Paddle"
    private Rectangle player2Paddle; // Value injected by FXMLLoader

    @FXML // fx:id="player1TopLeftEdge"
    private Rectangle player1TopLeftEdge; // Value injected by FXMLLoader

    @FXML // fx:id="player2TopRightEdge"
    private Rectangle player2TopRightEdge; // Value injected by FXMLLoader

    @FXML // fx:id="player1BottomLeftEdge"
    private Rectangle player1BottomLeftEdge; // Value injected by FXMLLoader

    @FXML // fx:id="player2BottomRightEdge"
    private Rectangle player2BottomRightEdge; // Value injected by FXMLLoader

    @FXML // fx:id="player1Score"
    private Label player1Score; // Value injected by FXMLLoader

    @FXML // fx:id="player2Score"
    private Label player2Score; // Value injected by FXMLLoader
    
    @FXML // fx:id="startLabel"
    private Label startLabel; // Value injected by FXMLLoader
    
    final double minPaddleY = -170; // top boundaries for both paddles
    
    final double maxPaddleY = 170; // bottom boundaries for both paddles
    
    final double player1Speed = 270 ; // pixels per second velocity
    
    final double player2Speed = 270 ; // pixels per second velocity
    
    final DoubleProperty rectangle1Velocity = new SimpleDoubleProperty(); 
    
    final DoubleProperty rectangle2Velocity = new SimpleDoubleProperty();
    
    private Timeline ballAnimation; // TimeLine object for use with getAirPockeyBallAnimation
    
    private AnimationTimer paddle1Mover; // AnimationTimer object for use with initPaddle1AnimationTimer
    
    private AnimationTimer paddle2Mover; // AnimationTimer object for use with initPaddle2AnimationTimer
    
    private int deltaX; // variable to indicate change in x for use with ballAnimation
    
    private int deltaY; // variable to indicate change in y for use with ballAnimation
    
    Random rand = new Random(); // Random object for use with ballAnimation
    
    private boolean isGameOver = false; // Boolean type variable to indicate whether a game is completed or not
    
    private boolean isBallMoving = false; //Boolean type variable to indicate whether the ball is moving or not
    
    @FXML
    private void onCloseMenuAction(ActionEvent event) {
    	Platform.exit();
    }
    
    /*
     * initialize - This method is called by the FXMLLoader when initialization is complete
     * 
     * @param - Scene type which is passed to be initialized
     */
    public void initialize(Scene scene) {
    	assert anchorPaneOuter != null : "fx:id=\"anchorPaneOuter\" was not injected: check your FXML file 'AirPockey.fxml'.";
        assert menuBar != null : "fx:id=\"menuBar\" was not injected: check your FXML file 'AirPockey.fxml'.";
        assert closeMenu != null : "fx:id=\"closeMenu\" was not injected: check your FXML file 'AirPockey.fxml'.";
        assert anchorPaneInner != null : "fx:id=\"anchorPaneInner\" was not injected: check your FXML file 'AirPockey.fxml'.";
        assert AirPockeyBoardImage != null : "fx:id=\"AirPockeyBoardImage\" was not injected: check your FXML file 'AirPockey.fxml'.";
        assert AirPockeyBall != null : "fx:id=\"AirPockeyBall\" was not injected: check your FXML file 'AirPockey.fxml'.";
        assert player1Paddle != null : "fx:id=\"player1Paddle\" was not injected: check your FXML file 'AirPockey.fxml'.";
        assert player2Paddle != null : "fx:id=\"player2Paddle\" was not injected: check your FXML file 'AirPockey.fxml'.";
        assert player1TopLeftEdge != null : "fx:id=\"player1TopLeftEdge\" was not injected: check your FXML file 'AirPockey.fxml'.";
        assert player2TopRightEdge != null : "fx:id=\"player2TopRightEdge\" was not injected: check your FXML file 'AirPockey.fxml'.";
        assert player1BottomLeftEdge != null : "fx:id=\"player1BottomLeftEdge\" was not injected: check your FXML file 'AirPockey.fxml'.";
        assert player2BottomRightEdge != null : "fx:id=\"player2BottomRightEdge\" was not injected: check your FXML file 'AirPockey.fxml'.";
        assert player1Score != null : "fx:id=\"player1Score\" was not injected: check your FXML file 'AirPockey.fxml'.";
        assert player2Score != null : "fx:id=\"player2Score\" was not injected: check your FXML file 'AirPockey.fxml'.";
        assert startLabel != null : "fx:id=\"startLabel\" was not injected: check your FXML file 'AirPockey.fxml'.";
        
        //setting styling for the Air Pockey Ball Sphere object
        final PhongMaterial sphereMaterial = new PhongMaterial();
        sphereMaterial.setDiffuseColor(Color.ORANGE);
        sphereMaterial.setSpecularColor(Color.CORAL);
        AirPockeyBall.setMaterial(sphereMaterial);
    	
    	scene.setOnKeyPressed(new EventHandler<KeyEvent>()
		{
			@Override
			public void handle(KeyEvent arg) {
				if(arg.getCode() == KeyCode.ENTER && !isBallMoving) {
					boolean xNegative = rand.nextInt() % 2 != 0;
					boolean yNegative = rand.nextInt() % 2 != 0;
		    		int x = 1 * (xNegative ? -1 : 1);		    		
		    		int y = 1 * (yNegative ? -1 : 1);
		            x = x != 0 ? x : 1;
		            y = y != 0 ? y : 1;
		            deltaX = x;
		            deltaY = y;
					System.out.println("x:"+x+" y:"+y );
					
					startLabel.setText("");
					if(isGameOver) {
						player1Score.setText("0");
						player2Score.setText("0");
						isGameOver = false;
					}
			    	ballAnimation.play();
			    	isBallMoving = true;
				}
				if(arg.getCode() == KeyCode.W) {
					rectangle1Velocity.set(-1 * player1Speed);
				}
				if(arg.getCode() == KeyCode.S) {
					rectangle1Velocity.set(player1Speed);
				}
				if(arg.getCode() == KeyCode.UP) {
					rectangle2Velocity.set(-1 * player2Speed);
				}
				if(arg.getCode() == KeyCode.DOWN) {
					rectangle2Velocity.set(player2Speed);
				}
		    }			
	    });
    	
    	scene.setOnKeyReleased(new EventHandler<KeyEvent>()
		{
			@Override
			public void handle(KeyEvent arg) {
				if(arg.getCode() == KeyCode.W) {
					rectangle1Velocity.set(0);
				}
				if(arg.getCode() == KeyCode.S) {
					rectangle1Velocity.set(0);
				}
				if(arg.getCode() == KeyCode.UP) {
					rectangle2Velocity.set(0);
				}
				if(arg.getCode() == KeyCode.DOWN) {
					rectangle2Velocity.set(0);
				}
		    }			
	    });    	
    	
    	ballAnimation = getAirPockeyBallAnimation(AirPockeyBall);
    	ballAnimation.setCycleCount(Timeline.INDEFINITE);
    	
    	initPaddle1AnimationTimer();
    	paddle1Mover.start();
    	initPaddle2AnimationTimer();
    	paddle2Mover.start();    	
	}
    
    /*
     * ResetBallAfterScore - resets the animation of the sphere and sets it back to its original coordinates 
     */
    private void ResetBallAfterScore() {
    	ballAnimation.stop();
    	isBallMoving = false;
    	AirPockeyBall.setLayoutX(AirPockeyBoardImage.getFitWidth() / 2 - AirPockeyBall.getRadius());
    	AirPockeyBall.setLayoutY(AirPockeyBoardImage.getFitHeight() / 2 - AirPockeyBall.getRadius());
    	startLabel.setText("Press [ENTER] to continue");
    }
    
    /*
     * initPaddle1AnimationTimer - initializes the animation for the player1Paddle
     */
    private void initPaddle1AnimationTimer() {
        final LongProperty lastUpdateTime = new SimpleLongProperty();    	
        paddle1Mover = new AnimationTimer() {
          @Override
          public void handle(long timestamp) {
            if (lastUpdateTime.get() > 0) {
              final double elapsedSeconds = (timestamp - lastUpdateTime.get()) / 1_000_000_000.0 ;
              final double deltaY = elapsedSeconds * rectangle1Velocity.get();
              final double oldY = player1Paddle.getTranslateY();
              final double newY = Math.max(minPaddleY, Math.min(maxPaddleY, oldY + deltaY));
              player1Paddle.setTranslateY(newY);
            }
            lastUpdateTime.set(timestamp);
          }
        };        
    }
    
    /*
     * initPaddle2AnimationTimer - initializes the animation for the player2Paddle
     */
    private void initPaddle2AnimationTimer() {
        final LongProperty lastUpdateTime = new SimpleLongProperty();    	
        paddle2Mover = new AnimationTimer() {
          @Override
          public void handle(long timestamp) {
            if (lastUpdateTime.get() > 0) {
              final double elapsedSeconds = (timestamp - lastUpdateTime.get()) / 1_000_000_000.0 ;
              final double deltaY = elapsedSeconds * rectangle2Velocity.get();
              final double oldY = player2Paddle.getTranslateY();
              final double newY = Math.max(minPaddleY, Math.min(maxPaddleY, oldY + deltaY));
              player2Paddle.setTranslateY(newY);
            }
            lastUpdateTime.set(timestamp);
          }
        };        
    }         
    
    /*
     * getAirPockeyBallAnimation - sets all the physics of the Sphere object passed to it
     * 
     * @param - Sphere type object which will be manipulated
     * @return - returns an object of type TimeLine
     */
    private Timeline getAirPockeyBallAnimation(Sphere airPockeyBall) {
    	return new Timeline(new KeyFrame(Duration.millis(2), new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent t) {
                double borderWidth = 30;
                airPockeyBall.setLayoutX(airPockeyBall.getLayoutX() + deltaX);
                airPockeyBall.setLayoutY(airPockeyBall.getLayoutY() + deltaY);

                final Bounds bounds = AirPockeyBoardImage.getBoundsInLocal();
                final double ballRadius = airPockeyBall.getRadius();
                final double ballDiam = (ballRadius * 2);
                final double ballX = airPockeyBall.getLayoutX();
                final double ballY = airPockeyBall.getLayoutY();                
                final double ballTop = airPockeyBall.getLayoutY();
                final double ballBottom = Math.round(airPockeyBall.getLayoutY() + ballDiam);
                final double ballLeft = airPockeyBall.getLayoutX();
                final double ballRight = Math.round(airPockeyBall.getLayoutX() + airPockeyBall.getTranslateX() + ballDiam);
                final double paddleHgt = player1Paddle.getHeight();
                final double paddleWidth = player1Paddle.getWidth();
                final double paddle1Left = Math.round(player1Paddle.getLayoutX());
                final double paddle1Top = Math.round(player1Paddle.getLayoutY() + player1Paddle.getTranslateY()) - 15;
                final double paddle2Top = Math.round(player1Paddle.getLayoutY() + player2Paddle.getTranslateY()) - 15;
                final double paddle2Left = Math.round(player2Paddle.getLayoutX()) - 15;
                final double paddle1Bottom = paddle1Top + paddleHgt + 15;
                final double paddle2Bottom = paddle2Top + paddleHgt + 15;
                final double paddle1Right = paddle1Left + paddleWidth + 15;
             
                
//                System.out.println("Ball: X:" + ballLeft + "Y:" + ballTop + "R:" + ballRight);
//                System.out.println("Paddle1: Right:" + paddle1Right + " Top:" + paddle1Top + " Bottom:" + paddle1Bottom);
//                System.out.println("Paddle2: Left:" + paddle2Left + " Top:" + paddle2Top + " Bottom:" + paddle2Bottom);
                
                final boolean atRightBorder = ballX >= (bounds.getMaxX() - ballDiam - borderWidth);
                final boolean atLeftBorder = ballX <= (bounds.getMinX() + ballDiam + borderWidth);
                final boolean atGoal = ballY >= 159 && ballY <= 306;
                final boolean atLeftGoal = atLeftBorder && atGoal;
                final boolean atBottomBorder = ballY >= (bounds.getMaxY() - ballDiam - borderWidth);
                final boolean atTopBorder = ballY <= (bounds.getMinY() + ballDiam + borderWidth);
                 
                final boolean atPlayer1PaddleSideEdge = ballLeft == paddle1Right;
                final boolean atPlayer2PaddleSideEdge = ballRight == paddle2Left;
                final boolean atPlayer1PaddleYRange = ballTop >= paddle1Top && ballBottom <= paddle1Bottom;
                final boolean atPlayer2PaddleYRange = ballTop >= paddle2Top && ballBottom <= paddle2Bottom;
                final boolean atPlayer1Paddle = atPlayer1PaddleSideEdge && atPlayer1PaddleYRange;
                final boolean atPlayer2Paddle = atPlayer2PaddleSideEdge && atPlayer2PaddleYRange;

                if (atPlayer1Paddle) {
                	// no ricochet
                	if(deltaX < 0)
              	  		deltaX *= -1;
                } else if (atPlayer2Paddle) {
                	// no ricochet
                	if(deltaX > 0)
                		deltaX *= -1;
                } else if (atRightBorder || atLeftBorder) {
                	if(!atGoal) {
                      deltaX *= -1;
                	}
                	else 
                	{
                	  if(atLeftGoal) {
                		  player2Score.setText((Integer.parseInt(player2Score.getText()) + 1) + "");
                	  } else {
                		  player1Score.setText((Integer.parseInt(player1Score.getText()) + 1) + "");
                	  }
                	  ResetBallAfterScore();
                	  if(Integer.parseInt(player1Score.getText()) >= 7) {
                		  startLabel.setText("Player 1 Wins!\\r\\nPress [ENTER] to Play Another Game!");
                		  isGameOver = true;
                	  }else if(Integer.parseInt(player2Score.getText()) >= 7) {
                		  startLabel.setText("Player 2 Wins!\r\nPress [ENTER] to Play Another Game!");
                		  isGameOver = true;
                	  }
                	}
                }
                
                if (atBottomBorder || atTopBorder) {
                    deltaY *= -1;
                }
            }
        }));    	
    }
}