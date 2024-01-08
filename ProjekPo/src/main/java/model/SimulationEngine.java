package model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationEngine {
    private final List<WorldMap> maps;
    private final List<Thread> threads = new ArrayList<>();
    private final ExecutorService executorService;
    public SimulationEngine(List<WorldMap> maps, int numberOfThreadsInPool){
        this.maps = maps;
        executorService = Executors.newFixedThreadPool(numberOfThreadsInPool);
        for(WorldMap map : maps){
            threads.add(new Thread(map));
        }
    }

    public void runSync(){
        for(WorldMap map : maps){
            map.run();
        }
    }

    public void runAsync() throws InterruptedException {
        for(Thread thread : threads){
            thread.start();
        }
        awaitSimulationEnd();
    }

    public void runAsyncNoWait(){
        for(Thread thread : threads){
            thread.start();
        }
    }
    public void awaitSimulationEnd() throws InterruptedException {
        for(Thread thread : threads){
            thread.join();
        }
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);
    }

    public void runAsyncInThreadPool() throws InterruptedException {
        for(WorldMap map : maps) {
            executorService.submit(map); // task runanble wywoła się na jednym z 8 wątków lub poczeka, aż któryś się zwolni
        }
        awaitSimulationEnd();
    }
}
