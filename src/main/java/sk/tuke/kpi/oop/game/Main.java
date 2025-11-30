package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.backends.lwjgl.LwjglBackend;
import sk.tuke.kpi.oop.game.scenarios.EscapeRoom;
import sk.tuke.kpi.oop.game.scenarios.MissionImpossible;

public class Main {
    public static void main(String[] args) {
        WindowSetup windowSetup = new WindowSetup("Project Ellen", 800, 600);

        Game game = new GameApplication(windowSetup, new LwjglBackend());
        //Scene scene = new World("world", "maps/mission-impossible.tmx", new MissionImpossible.Factory());
        Scene scene = new World("World", "maps/escape-room.tmx", new EscapeRoom.Factory());
        //MissionImpossible missionImpossible = new MissionImpossible();
        EscapeRoom escapeRoom = new EscapeRoom();

        game.addScene(scene);
        //scene.addListener(missionImpossible);
        scene.addListener(escapeRoom);
        game.getInput().onKeyPressed(Input.Key.ESCAPE, game::stop);
        game.start();
    }
}
