package hellofx;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application
{
	Stage mainwindow;
	
	Scene mainscene,signupscene,signinscene,signupsuccess,signupwarning,signinsuccess,signinwarning;
    @Override
    public void start(Stage primaryStage) throws Exception
    {
    	mainwindow=primaryStage;
    	mainwindow.setTitle("MEALME MAIN PAGE");
    	final Label MainWelcome=new Label("WELCOME TO MEALME");
    	MainWelcome.setFont(new Font("Arial", 30));
    	GridPane grid=new GridPane();
    	grid.setPadding(new Insets(10,10,10,10));
    	grid.setVgap(8);
    	grid.setHgap(10);
    	GridPane.setConstraints(MainWelcome, 0, 0);
    	Button SignupButton=new Button("Sign Up");
    	SignupButton.setMaxSize(100,100);
    	SignupButton.setOnAction(e->mainwindow.setScene(signupscene));
    	GridPane.setConstraints(SignupButton, 0, 3);
    	Button SigninButton=new Button("Sign In");
    	SigninButton.setMaxSize(100,100);
    	SigninButton.setOnAction(e->mainwindow.setScene(signinscene));
    	GridPane.setConstraints(SigninButton, 0, 6);
    	grid.setPadding(new Insets(10, 10, 10, 10));
    	grid.getChildren().addAll(MainWelcome,SignupButton,SigninButton);
    	mainscene=new Scene(grid,1000,600);
    	
    	final Label SignupLabel=new Label("SIGN UP");
    	Label NameLabel=new Label("Name :");
    	TextField namefeild=new TextField();
    	final Label PasswordLabel=new Label("Password :");
    	TextField passwordfeild=new TextField();
    	final Label PhoneLabel=new Label("Phone number :");
    	TextField phonefeild=new TextField();
    	final Label EmailLabel=new Label("email :");
    	TextField Emailfeild=new TextField();
    	Button SubmitInfo=new Button("Submit");
    	SubmitInfo.setOnAction(e->
    	{
    		if(	checkTextFormat(namefeild.getText(),1)&&
    			checkTextFormat(passwordfeild.getText(),2)&&
    			checkTextFormat(phonefeild.getText(),3)&&
    			checkTextFormat(Emailfeild.getText(),4))
    		{
    			mainwindow.setScene(signupsuccess);
    		}
    		else
    		{
    			mainwindow.setScene(signupwarning);
    		}
    	});
    	VBox SignupArrange=new VBox();
    	SignupArrange.setPadding(new Insets(10, 10, 10, 10));
    	SignupArrange.getChildren().addAll(SignupLabel,NameLabel,namefeild,PasswordLabel,passwordfeild,PhoneLabel,phonefeild,EmailLabel,Emailfeild,SubmitInfo);
    	signupscene=new Scene(SignupArrange,500,500);
    	
    	Label SigninLabel=new Label("SIGN IN");
    	VBox SigninArrange=new VBox();
    	SigninArrange.getChildren().addAll(SigninLabel);
    	signinscene=new Scene(SigninArrange,300,300);
    	
    	mainwindow.setScene(mainscene);
    	mainwindow.show();
    }
    
    private boolean checkTextFormat(String format,int inputType)
    {
    	return true;
    }
    

    public static void main(String[] args) 
    {
        launch(args);
    }
}
