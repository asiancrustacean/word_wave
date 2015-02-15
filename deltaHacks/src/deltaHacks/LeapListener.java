package deltaHacks;
import java.io.IOException;
import com.leapmotion.leap.*;
import javax.swing.JFrame;


class LeapListener extends Listener {
	
	int yes_counter;
	int no_counter;
	int name_counter;
	int your_counter;
	int whats_counter;
	SineScreen window;
	
	boolean flag_yes;
	double yes_timestamp;
	
	public LeapListener(SineScreen main_window) {
		yes_counter = 1;
		no_counter = 1;
		name_counter = 1;
		your_counter = 1;
		whats_counter=1;
		
		window = main_window;
	}
	
	public void onInit( Controller controller) {
		System.out.println("Initialized");
	}
	
	public void onConnect(Controller controller){
		
		System.out.println("Connected to Motion Sensor");
		controller.enableGesture(Gesture.Type.TYPE_SWIPE);
		controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
		controller.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);
		controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);
		
	}
		
	public void onDisconnect(Controller controller) {
		System.out.println("Motion sensor disconnected");
	}
	
	public void onExit(Controller controller) {
		System.out.println("Exit");
	} 
	
	public void onFrame(Controller controller) {
		
		Frame frame = controller.frame();
		
		boolean grab = (frame.hands().rightmost().grabStrength() >= 0.9);
		//boolean pinch = (frame.hands().rightmost().pinchStrength() >= 0.7);
		
		Finger index = frame.hands().rightmost().fingers().get(1);
		Finger middle = frame.hands().rightmost().fingers().get(2);
		Finger thumb = frame.hands().rightmost().fingers().get(0);
		Finger index_left = frame.hands().leftmost().fingers().get(1);
		Finger middle_left = frame.hands().leftmost().fingers().get(2);
		
		//code to catch a YES
		if (grab) {
			
			double palm_vel_y = frame.hands().rightmost().palmVelocity().get(1);
			double palm_vel_z = frame.hands().rightmost().palmVelocity().get(2);

			if ((palm_vel_y > 150) && (palm_vel_z > 150)) {
				System.out.println("YES: " + yes_counter);
				yes_counter++;			
				try {
					Thread.sleep(700);
				} catch (InterruptedException e) {}
			}
		}
		
		
		//code to catch a NO
		if (middle.isExtended() && !grab) {
			if ((index.tipVelocity().get(1) < -80) && (middle.tipVelocity().get(1) < -80)) {
				
				if (thumb.tipPosition().distanceTo(index.tipPosition()) < 50) {
					System.out.println("NO: " + no_counter);
					no_counter++;				
					try {
						Thread.sleep(700);
					} catch (InterruptedException e) {}
				}
			}
		}
		
		
		//code to catch a NAME
		if(frame.hands().count() == 2) {
			if (index.isExtended() && middle.isExtended() && index_left.isExtended() && middle_left.isExtended()) {
				
				double angle = middle.direction().angleTo(middle_left.direction());
				if ((angle > 7*Math.PI/18) && (angle < 5*Math.PI/6) && (middle.tipVelocity().magnitude() > 50)) {
					if (middle.bone(Bone.Type.TYPE_INTERMEDIATE).prevJoint().distanceTo(index_left.bone(Bone.Type.TYPE_INTERMEDIATE).prevJoint()) < 25) {
						System.out.println("NAME: " + name_counter);
						name_counter++;
						try {Thread.sleep(700);} catch (InterruptedException e) {}
					}
				}
			}
		}
		
		//code to catch a YOUR
		if (frame.hands().rightmost().isValid()) {
			if (frame.hands().rightmost().palmNormal().angleTo(new Vector(0,0,-1)) < Math.PI/6) {
				if (frame.hands().rightmost().palmVelocity().get(2) < -50) {
					System.out.println("YOUR: " + your_counter);
					your_counter++;
					try {Thread.sleep(700);} catch (InterruptedException e) {}
				}
			}
		}
		
		//code to catch a WHAT'S
		if (frame.hands().count() == 2) {
			boolean flag = false;
			for (Hand hand: frame.hands()) {
				if (hand.direction().angleTo(new Vector(0,0,-1)) > Math.PI/12) {
					flag=true;
					break;
				}
				if (Math.abs(hand.palmVelocity().get(0)) < 110) {
					flag=true;
					break;
				}
			}
			
			if (!flag) {
				System.out.println("WHAT'S: " + whats_counter);
				whats_counter++;
				try {Thread.sleep(700);} catch (InterruptedException e) {}
			}
		}
		
		
		/*System.out.println("Fram:" + frame.id()
				+", Timestamp"+ frame.timestamp()
				+", Hands"+frame.hands().count() 
				+", Fingers:"+frame.fingers().count()
				+", Gestures:"+frame.gestures().count());
		
		for (Hand hand: frame.hands()){
			String handtype = hand.isLeft() ? "Left hand" : "Right Hand";
			System.out.println(handtype + "  "+", Id: " + hand.id()
					+", Palm Position: " + hand.palmPosition());
			
			
			Vector normal = hand.palmNormal();
			Vector direction = hand.direction();
			
			System.out.println("Pitch: "+ Math.toDegrees(direction.pitch())
					+ "  Roll: " + Math.toDegrees(normal.roll())
					+ "  Yaw: " + Math.toDegrees(direction.yaw()));		
		}

		GestureList gestures = frame.gestures();
		for (int i = 0; i< gestures.count(); i++){
			Gesture gesture = gestures.get(i);
			
			switch (gesture.type()){
			case TYPE_CIRCLE:
				CircleGesture circle = new CircleGesture(gesture);
				String clockwiseness;
				
				if(circle.pointable().direction().angleTo(circle.normal()) <= Math.PI/4){
					clockwiseness = "clockwise";
				}else{
					clockwiseness = "counter-clockwise";		
				}
				double sweptAngle = 0;
				if(circle.state() != State.STATE_START){
					CircleGesture previous = new CircleGesture(controller.frame(1).gesture(circle.id()));
					sweptAngle = (circle.progress() - previous.progress())*2*Math.PI;
				}
				
				System.out.println("Circle ID: " + circle.id()
						+ "State: "+circle.state()
						+ " Progess: "+circle.state()
						+ " Radius: "+circle.radius()
						+ " Angle: "+Math.toDegrees(sweptAngle)
						+ " "+clockwiseness);
				break;
				
			}
			
		}
		*/
		
	}
}
