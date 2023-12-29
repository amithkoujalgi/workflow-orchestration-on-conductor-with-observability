package io.github.amithkoujalgi.worker;

import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskExecLog;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.slf4j.event.Level;

public class SquareNumberWorker implements Worker {
  Logger logger = Logger.getLogger(SquareNumberWorker.class.getName());

  private final String taskDefName;
  private final List<TaskExecLog> logs = new ArrayList<>();

  public SquareNumberWorker(String taskDefName) {
    this.taskDefName = taskDefName;
  }

  @Override
  public String getTaskDefName() {
    return taskDefName;
  }

  @Override
  public TaskResult execute(Task task) {
    TaskResult result = new TaskResult(task);
    Integer num = (Integer) task.getInputData().get("num");

    Integer square = num * num;
    result.addOutputData("square", square);

    log("Square of " + num + " is " + square, Level.INFO);

    try {
      log("Waiting for 60 secs", Level.INFO);
      Thread.sleep(60 * 1000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    result.setLogs(logs);
    result.setStatus(TaskResult.Status.COMPLETED);
    return result;
  }

  private void log(String log, Level level) {
    logs.add(new TaskExecLog(log));
    if (level.equals(Level.INFO)) {
      logger.info(log);
    } else if (level.equals(Level.WARN)) {
      logger.warning(log);
    } else if (level.equals(Level.ERROR)) {
      logger.severe(log);
    } else {
      logger.info(log);
    }
  }
}
