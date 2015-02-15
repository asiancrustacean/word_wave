package deltaHacks;

import java.io.IOException;

import com.leapmotion.leap.Controller;

public class LeapFun {

	public static void main(String[] args) {
		
		SineScreen main_window = new SineScreen();
		Controller controller = new Controller();
		LeapListener listener = new LeapListener(main_window);
		
		controller.addListener(listener);
		System.out.println("Press Enter to Quit");

		//catch Enter keypress to exit program
		try{
			System.in.read();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		controller.removeListener(listener);
		
	}

}
