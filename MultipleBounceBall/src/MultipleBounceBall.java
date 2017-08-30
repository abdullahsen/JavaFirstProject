import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
// it is my first commit and push
public class MultipleBounceBall extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		MultiplaBallPane ballPane = new MultiplaBallPane();
		ballPane.setStyle("-fx-border-color: yellow");
		
		Button btnAdd = new Button("+");
		Button btnSubstract = new Button("-");
		
		HBox hBox = new HBox(10);
		hBox.getChildren().addAll(btnAdd,btnSubstract);
		hBox.setAlignment(Pos.CENTER);
		
		
		btnAdd.setOnAction(e->ballPane.add());
		btnSubstract.setOnAction(e->ballPane.substract());
		
		ballPane.setOnMousePressed(e->ballPane.pause());
		ballPane.setOnMouseReleased(e->ballPane.play());
		
		ScrollBar sbSpeed = new ScrollBar();
		sbSpeed.setMax(20);
		sbSpeed.setValue(10);
		ballPane.rateProperty().bind(sbSpeed.valueProperty());
		
		BorderPane pane = new BorderPane();
		pane.setCenter(ballPane);
		pane.setBottom(hBox);
		pane.setTop(sbSpeed);
		
		Scene scene = new Scene(pane,400,400);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private class MultiplaBallPane extends Pane {
		private Timeline animation;

		public MultiplaBallPane() {
			animation = new Timeline(new KeyFrame(Duration.millis(50), e -> moveBall()));
			animation.setCycleCount(Timeline.INDEFINITE);
			animation.play();

		}

		public void add() {
			Color color = new Color(Math.random(), Math.random(), Math.random(), 0.5);
			getChildren().add(new Ball(30, 30, 20, color));
		}

		public void substract() {
			if (getChildren().size() > 0) {
				getChildren().remove(getChildren().size() - 1);
			}
		}

		public void play() {
			animation.play();

		}

		public void pause() {
			animation.pause();
		}

		public void increaseSpeed() {
			animation.setRate(animation.getRate() + 0.1);
		}

		public void decreaseSpeed() {
			if (animation.getRate() > 0) {
				animation.setRate(animation.getRate() - 0.1);
			}
		}

		public DoubleProperty rateProperty() {
			return animation.rateProperty();
		}

		protected void moveBall() {
			for (Node node : this.getChildren()) {
				Ball ball = (Ball) node;

				if (ball.getCenterX() < ball.getRadius() || ball.getCenterX() > getWidth() - ball.getRadius())
					ball.dx *= -1;
				if (ball.getCenterY() < ball.getRadius() || ball.getCenterY() > getHeight() - ball.getRadius())
					ball.dy *= -1;
					
				ball.setCenterX(ball.dx + ball.getCenterX());
				ball.setCenterY(ball.dy + ball.getCenterY());
			}
		}

	}

	class Ball extends Circle {
		private double dx = 1, dy = 1;

		Ball(double x, double y, double radius, Color color) {
			super(x, y, radius);
			setFill(color);
		}

	}
}
