import java.util.HashMap;
import java.util.Vector;

public class FrameTest extends TestAbstract<String>{


	public FrameTest(){
		WorldEntities worldEntities = new WorldEntities();
		Vector<String> entityList = worldEntities.getEntityList();
		infoFrame.setListItems(entityList);
	}

	@Override
	void initialize(){
		// TODO Auto-generated method stub
		
	}

	@Override
	void doLogic() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void oneSecondElapsed() {
		// TODO Auto-generated method stub
		
	}

	public static void main(String args[]){
		new FrameTest();
	}
}
