import com.artemis.*;
import com.artemis.managers.GroupManager;
import com.artemis.systems.VoidEntitySystem;
import com.artemis.utils.Bag;
import com.artemis.utils.EntityBuilder;
import com.artemis.utils.ImmutableBag;
import java.util.Vector;

public class FrameTest extends TestAbstract<String>{

	Vector<String> entityList;


	public FrameTest(){

		World world = new World();
		world.setManager(new GroupManager());
		entityList = new Vector<String>();

		VoidEntitySystem voidSystem = new VoidEntitySystem() {
			@Override
			protected void processSystem() {
				System.out.println("Void system:");
				entityList.clear();

				ImmutableBag<Entity> monsters = world.getManager(GroupManager.class).getEntities("enemies");
				Bag<Component> components = new Bag<Component>();
				for(Entity monster : monsters){
					StringBuilder entityString = new StringBuilder();
					components = monster.getComponents(components);

					entityString.append(monster + " ");

					for(Component component : components){

						if(component instanceof Position){
							Position pos = (Position) component;
							entityString.append("X: " + pos.x + " Y: " + pos.y + " ");
						}else if(component instanceof Health){
							Health health = (Health) component;
							entityString.append("Health: " + health.health + " ");
						}else{
							entityString.append(component.getClass() + " ");
						}
					}
					components.clear();
					entityList.add(entityString.toString());
				}

				System.out.println();
			}
		};

		world.setSystem(new MovementSystem());
		world.setSystem(new AIRandomMovementSystem());
		world.setSystem(voidSystem);
		world.initialize();

		new EntityBuilder(world)
				.with(new Position(10, 10))
				.with(new Velocity(1,1))
				.group("enemies")
				.build();

		for (int i = 0; i < 10; i++) {
			world.setDelta(1);
			world.process();
			infoFrame.setListItems(entityList);
		}
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
