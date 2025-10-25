package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.Scenario;
import sk.tuke.kpi.gamelib.map.MapMarker;
import sk.tuke.kpi.oop.game.tools.Hammer;

import java.util.Map;


public class Gameplay extends Scenario {

    @Override
    public void setupPlay(Scene scene) {
        Map<String, MapMarker> markers = scene.getMap().getMarkers();
        MapMarker computerArea = markers.get("computer-area");
        MapMarker coolerArea1 = markers.get("cooler-area-1");
        MapMarker reactorArea = markers.get("reactor-area-1");

        Reactor reactor = new Reactor();
        scene.addActor(reactor, reactorArea.getPosX(), reactorArea.getPosY());
        reactor.turnOn();


        Cooler cooler = new Cooler(reactor);
        scene.addActor(cooler, coolerArea1.getPosX(), coolerArea1.getPosY());
        cooler.turnOff();

        new ActionSequence<>(
            new Wait<>(5),
            new Invoke<>(cooler::turnOn)
        ).scheduleFor(cooler);

        Hammer hammer = new Hammer();
        scene.addActor(hammer, 200, 45);

        Computer computer = new Computer();
        scene.addActor(computer, computerArea.getPosX(), computerArea.getPosY());

        Teleport teleport1 = new Teleport(null);
        scene.addActor(teleport1, 22, 30);

        Teleport teleport2 = new Teleport(teleport1);
        scene.addActor(teleport2, 22, 200);

        Teleport teleport3 = new Teleport(teleport2);
        scene.addActor(teleport3, 22, 350);

        new When<>(
            () -> reactor.getTemperature() >= 3000,
            new Invoke<>(() -> hammer.useWith(reactor))
        ).scheduleFor(reactor);
    }
}
