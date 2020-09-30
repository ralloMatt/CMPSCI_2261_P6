import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;



public class main extends Application {
	
	// using global so I am able to handle them in the handle events
	Button[][] tiles = new Button[8][8]; // All my buttons or my tiles that are displayed
	Integer[][] mine_field = new Integer[8][8]; // the mine locations
	Text loser = new Text(); // Used to let user know he or she lost
	
	
	public void start(Stage primaryStage){
		
		BorderPane Field = new BorderPane(); // Creating my scene
		HBox Start_Show = new HBox(50); // Hbox used for the two buttons below (start and show)
		Button Start = new Button("Start");
		Button Show = new Button ("Show");
		Start.setStyle("-fx-font-size: 15.5pt;"); // making buttons a bit bigger
		Show.setStyle("-fx-font-size: 15.5pt;");
		Start_Show.getChildren().addAll(Start, Show); // adding the buttons to the hbox
		Start_Show.setAlignment(Pos.CENTER); // putting it in the center of the hbox
		Start_Show.setPadding(new Insets(10,10,10,10)); // padding
		Field.setTop(Start_Show); // puttting the hbox on the top of the borderpane which is the field
		
		
		HBox you_lose = new HBox(); // used to let user know he lost
		you_lose.getChildren().add(loser); // adding the loser text to the hbox
		loser.setFont(new Font(30)); // giving the text some style
		loser.setText(""); // leaving the text as blank so when user clicks start the text won't say they lost
		you_lose.setAlignment(Pos.CENTER); // putting hbox in the center
		Field.setBottom(you_lose); // putting the hbox in the border pane field
		
		
		tiles = create_tiles(tiles); // creating the tiles or the 2D array of buttons
		
		int i; // used for looping
		int j;
		
		VBox columns = new VBox(); // using a vbox which will contain a bunch of hboxes to displaay something like a grid
		
		for(i=0; i<8; i++){
			HBox row = new HBox(); // creating a hbox for the first 8 buttons
			for(j=0; j<8; j++){
				row.getChildren().add(tiles[i][j]); // giving that hbox the buttons
			}
			columns.getChildren().add(row); // giving the hboxes to the columns
		}
		columns.setAlignment(Pos.CENTER); // centering the columns
		columns.setPadding(new Insets(0,0,0,100)); // giving them padding for display looks

		Field.setCenter(columns); // putting the grid in the center of the borderpane
		
		
		
		mine_field = set_mines(mine_field); // setting the locations of the mines
		
		Start.setOnAction(new EventHandler<ActionEvent>() { // handling the start button event

			@Override
			public void handle(ActionEvent event) {
				 start(primaryStage);	// when the start button is clicked i call the start to "restart" the game
			}
			
		});
		
		Show.setOnAction(new EventHandler<ActionEvent>() { // handling the show button event
			@Override
				public void handle(ActionEvent e){
					
					int i = 0; // used for looping
					int j = 0;
					
					for(i=0; i<8; i++){
						for(j=0; j<8; j++){// looping through the minefield
			
							if(mine_field[i][j] == 1) { // when I find the location of the mine, it is also the location of the button that has the mine
								tiles[i][j].setStyle("-fx-font-size: 15.5pt;"); // giving the button a M and the color red so user knows mine is there
								tiles[i][j].setTextFill(Color.RED);
								tiles[i][j].setMaxHeight(150);
								tiles[i][j].setMaxWidth(251);
								tiles[i][j].setText("M");	
								}
							}
						}
					}
				});

		
		
		for(i=0; i<8; i++){
			
			for(j=0; j<8; j++){ // used to handle every 2D array button events
				Tile_Handler tile_handle = new Tile_Handler(); // creating tile handler event
				tiles[i][j].setOnAction(tile_handle);
			}
		}
		
		
		
	
		
		Scene scene = new Scene(Field, 600, 600); // creating the scene
		primaryStage.setTitle("MineSweeper!"); // title of the stage
		primaryStage.setScene(scene); // setting the scene
		primaryStage.show(); // showing the stage 
		
		
	}
	
	
	public Button[][] create_tiles(Button[][] array){ // used to create the 2D array of buttons
		
		int i; // used for looping
		int j;
		
		for(i=0; i<8; i++){
			
			for(j=0; j<8; j++){
				Button my_butt = new Button("?"); // creating a button that has a ? in it
				my_butt.setStyle("-fx-font-size: 20pt;"); // styling it
				my_butt.prefWidthProperty();
				
				array[i][j] = my_butt; // giving the 2D array the button at that location
				
			}
		}
		
		return array; // returning the 2D array
		
	}
	
	public Integer[][] set_mines(Integer[][] array){ // function to set the locations of the mines
		Random random = new Random(); // random used for generating random numbers 0 - 7
	
		int i; // used for looping
		int j;
		int k;
	
		for(i=0; i<8; i++){
				
				for(j=0; j<8; j++){
					array[i][j] = 0; // initializing the array of mines to zero
					
				}
			}
		
		for(i=0; i<10; i++){ // giving random numbers 0 - 7 to get random locations for the mines
			j = random.nextInt(8);
			k = random.nextInt(8);
			array[j][k] = 1;	// setting that location to 1 which tells me it's a mine		
		}

		return array; // returning 2D array of mine locations
	}
	

	class Tile_Handler implements EventHandler<ActionEvent> { // used for the tiles or 2D array of buttons events
			
		public void handle(ActionEvent e){
			
			Button click = new Button(); // creating a new button
			click = (Button)e.getSource(); // setting that button to the source of the button clicked
			int x_coord = 10; // initializing a random x and y coordinate
			int y_coord = 10;
			
			
			for(int i=0; i<8; i++){
				
				for(int j=0; j<8; j++){ // loop throught the 2D array of buttons
					
					if(tiles[i][j]  == click){ // if one of the buttons is equal to the new button (which is the source of the button clicked)
						x_coord = i; // then get the x and y coordinates from i and j
						y_coord = j;
					}
					
				}
			}
			
			boolean mine = am_i_a_mine(x_coord, y_coord); // used to see if button clicked was a mine
			
			if(mine){ // if it was a mine then change the style of the button to Red and to display a B for BOOM
				tiles[x_coord][y_coord].setStyle("-fx-font-size: 18pt;");
				tiles[x_coord][y_coord].setTextFill(Color.RED);
				tiles[x_coord][y_coord].setMaxHeight(150);
				tiles[x_coord][y_coord].setMaxWidth(251);
				tiles[x_coord][y_coord].setText("B");
				tiles[x_coord][y_coord].setDisable(true); // so user can't press button twice
				
				loser.setFill(Color.RED); // Then display text from earlier to say they lost
				loser.setText("BOOM! You killed everybody!");
			}
			else { // if it is not a mine then call the function expand me
				Expand_me(x_coord, y_coord); 

			}
		
		}
	}
	
	
	public boolean am_i_a_mine(int x, int y){ // used to see if the button clicked was a mine
		if(mine_field[x][y] == 1){ // given x and y coordinates i can determine in the mine field if it was a mine
			return true; // was a mine
		}
		else { // not a mine
			return false;
		}
		
		
	}
	
	
	
	public void Expand_me(int row, int column) { // used to expand the buttons when clicked
		
		
		if(!am_i_a_mine(row, column)){ // used to see if the button neighbor is a mine, if it is not then find the number of mines around that neighbor
			

			int sum = 0; // used to determine number of mines around the button
			int smallrow = row-1; // the lowest row number for that neighbor
			if(smallrow < 0)  // if it's less than zero i do not want to go out of bounds
				smallrow = row; // so I set it to the original location
			int smallcol = column-1; // the rest is the same going down
			if(smallcol < 0)
				smallcol = column;
			int highrow = row + 1;
			if(highrow > 7)
				highrow = row;
			int highcol = column+1;
			if(highcol > 7)
				highcol = column;
			
			
			// Check to see if has mines around it
			for(int i = smallrow; i <= highrow; i++) { // loop through the neighbors
				
				for(int j = smallcol; j <= highcol; j++){
					if(am_i_a_mine(i, j)){ // if the neighbor has a mine
						sum++; // add one to the sum
					} // ends if
				}
			}// ends outer for loop
			
			String sum_text = Integer.toString(sum); // converting the int sum to a string for the button text
			tiles[row][column].setText(sum_text);
			tiles[row][column].setDisable(true); // so button appears pressed
			
			
			
			if(sum == 0){ // if the sum is zero no mines were found around that button
				// so look at the button neighbors for more mines
				for(int i = smallrow; i <= highrow; i++) { 
					// looping through the neighbors
					for(int j = smallcol; j <= highcol; j++){
						if(tiles[i][j].getText().equals("?")){ // for each neighbor not pressed
							Expand_me(i,j); // expand that to recursively display the number of mines around that neighbor
						} // ends if
					}
				}// ends outer for loop

				
			} // ends if sum == 0
				
		} // ends iffff
		
		
	}
	

	public static void main(String[] args){
		 	// nothing special just my main
			Application.launch(args);
		
	}


	

}