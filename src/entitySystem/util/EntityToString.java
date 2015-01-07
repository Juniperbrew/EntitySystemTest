package entitySystem.util;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.utils.Bag;
import entitySystem.components.Health;
import entitySystem.components.Position;

public class EntityToString {

    public static String convert(Entity e){
        StringBuilder entityString = new StringBuilder();
        Bag<Component> components = new Bag<Component>();
        components = e.getComponents(components);

        entityString.append(e + " ");

        for (Component component : components) {

            if (component instanceof Position) {
                Position pos = (Position) component;
                entityString.append("X: " + pos.x + " Y: " + pos.y + " ");
            } else if (component instanceof Health) {
                Health health = (Health) component;
                entityString.append("entitySystem.components.Health: " + health.health + " ");
            } else {
                entityString.append(component.getClass() + " ");
            }
        }
        components.clear();
        return entityString.toString();
    }
}
/*

    @Override
    protected void processSystem() {
        System.out.println("Void system:");
        entityList.clear();

        for(String mapName : world.getManager(GroupManager.class).getGroups()) {
            ImmutableBag<Entity> entitiesInMap = world.getManager(GroupManager.class).getEntities(mapName);
            Bag<Component> components = new Bag<Component>();

            for (Entity monster : entitiesInMap) {
                StringBuilder entityString = new StringBuilder();
                components = monster.getComponents(components);

                entityString.append(monster + " ");

                for (Component component : components) {

                    if (component instanceof entitySystem.components.Position) {
                        entitySystem.components.Position pos = (entitySystem.components.Position) component;
                        entityString.append("X: " + pos.x + " Y: " + pos.y + " ");
                    } else if (component instanceof entitySystem.components.Health) {
                        entitySystem.components.Health health = (entitySystem.components.Health) component;
                        entityString.append("entitySystem.components.Health: " + health.health + " ");
                    } else {
                        entityString.append(component.getClass() + " ");
                    }
                }
                components.clear();
                entityList.add(entityString.toString());
            }
        }

        System.out.println();
    }
    */
