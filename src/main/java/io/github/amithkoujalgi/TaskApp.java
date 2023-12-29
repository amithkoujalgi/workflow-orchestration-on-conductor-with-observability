package io.github.amithkoujalgi;

import com.netflix.conductor.client.automator.TaskRunnerConfigurer;
import com.netflix.conductor.client.http.TaskClient;
import com.netflix.conductor.client.worker.Worker;
import io.github.amithkoujalgi.worker.AddNumbersWorker;
import java.util.ArrayList;
import java.util.Collection;

import io.github.amithkoujalgi.worker.SquareNumberWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaskApp {

  public static void main(String[] args) {
    TaskClient taskClient = new TaskClient();
    taskClient.setRootURI("http://localhost:8080/api/");
    // number of threads used to execute workers. To avoid starvation, should be
    // same or more than number of workers
    int threadCount = 1;

    Worker addNumbersWorker = new AddNumbersWorker("add-numbers");
    Worker squareNumber = new SquareNumberWorker("square-number");

    Collection<Worker> workerArrayList = new ArrayList<>();
    workerArrayList.add(addNumbersWorker);
    workerArrayList.add(squareNumber);

    TaskRunnerConfigurer configurer =
        new TaskRunnerConfigurer.Builder(taskClient, workerArrayList)
            .withThreadCount(threadCount)
            .build();

    // Start the polling and execution of tasks
    configurer.init();
    SpringApplication.run(TaskApp.class, args);
  }
}
