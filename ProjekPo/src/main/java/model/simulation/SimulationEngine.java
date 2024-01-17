package model.simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationEngine {
    private final List<Simulation> simulations;
    private final List<Thread> threads = new ArrayList<>();
    private final ExecutorService executorService;
    public SimulationEngine(List<Simulation> simulations, int numberOfThreadsInPool){
        this.simulations = simulations;
        executorService = Executors.newFixedThreadPool(numberOfThreadsInPool);
        for(Simulation simulation : simulations){
            threads.add(new Thread(simulation));
        }
    }

    public void runSync(){
        for(Simulation simulation : simulations){
            simulation.run();
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
        for(Simulation simulation : simulations) {
            executorService.submit(simulation); // task runanble wywoła się na jednym z 8 wątków lub poczeka, aż któryś się zwolni
        }
        awaitSimulationEnd();
    }
}
