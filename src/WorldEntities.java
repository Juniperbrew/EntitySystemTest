import com.artemis.*;
import com.artemis.managers.*;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.systems.VoidEntitySystem;
import com.artemis.utils.Bag;
import com.artemis.utils.EntityBuilder;
import com.artemis.utils.ImmutableBag;

import java.util.UUID;
import java.util.Vector;

/**
 * Created by Litude on 6.1.2015.
 */
public class WorldEntities {

    //Test commit
    private Vector<String> entityList;

    public WorldEntities(){
        entityList = new Vector<String>();

        World world = new World();
        world.setManager(new GroupManager());
        world.setManager(new TagManager());
        world.setManager(new PlayerManager());
        world.setManager(new UuidEntityManager());

        EntityProcessingSystem plainSystem = new EntityProcessingSystem(Aspect.getAspectForAll(Position.class)) {
            @Override
            protected void process(Entity e) {
                System.out.println("Entity system:");
                System.out.println(e);
                Bag<Component> components = new Bag<Component>();
                e.getComponents(components);
                for(Component component : components){
                    System.out.println(component.getClass());
                    if(component instanceof Position){
                        Position pos = (Position) component;
                        System.out.println("X: " + pos.x + " Y: " + pos.y);
                    }
                }
            }
        };

        VoidEntitySystem voidSystem = new VoidEntitySystem() {
            @Override
            protected void processSystem() {
                System.out.println("Void system:");
                ImmutableBag<Entity> monsters = world.getManager(GroupManager.class).getEntities("enemies");
                Bag<Component> components = new Bag<Component>();
                for(Entity monster : monsters){
                    StringBuilder entityString = new StringBuilder();
                    Entity alternateMonster = world.getEntity(monster.getId());
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

        world.setSystem(voidSystem);
        world.setSystem(plainSystem);
        world.initialize();

        //#1
        Entity myEntity1 = new EntityBuilder(world)
                .with(new Position(10,10))
                .tag("boss")
                .group("enemies")
                .build();

        //#2
        Entity myEntity2 = world.createEntity()
                .edit()
                .add(new Position(10,10))
                .getEntity();
        world.getManager(TagManager.class).register("boss", myEntity2);
        world.getManager(GroupManager.class).add(myEntity2,"enemies");

        //#3
        Entity myEntity3 = new EntityBuilder(world)
                .with(new Position(10, 10))
                .with(new Health(100))
                .group("enemies")
                .build();

        //#4
        Entity myEntity4 = new EntityBuilder(world)
                .group("enemies")
                .build();

        //#Player
        Entity player = new EntityBuilder(world)
                .with(new Position(30,20))
                .player("Jack")
                .build();

        world.process();

    }

    public Vector<String> getEntityList(){
        return entityList;
    }

    public static void main(String args[]){
        WorldEntities worldEntities = new WorldEntities();
        Vector<String> entityList = worldEntities.getEntityList();
        System.out.println();
        for(String entity : entityList){
            System.out.println(entity);
        }
    }
}
