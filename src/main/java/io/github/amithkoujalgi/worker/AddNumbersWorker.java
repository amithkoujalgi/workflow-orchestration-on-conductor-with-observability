package io.github.amithkoujalgi.worker;

import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskExecLog;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.slf4j.event.Level;

public class AddNumbersWorker implements Worker {
  Logger logger = Logger.getLogger(AddNumbersWorker.class.getName());

  private final String taskDefName;
  private final List<TaskExecLog> logs = new ArrayList<>();

  public AddNumbersWorker(String taskDefName) {
    this.taskDefName = taskDefName;
  }

  @Override
  public String getTaskDefName() {
    return taskDefName;
  }

  @Override
  public TaskResult execute(Task task) {
    TaskResult result = new TaskResult(task);
    Integer num1 = (Integer) task.getInputData().get("num1");
    Integer num2 = (Integer) task.getInputData().get("num2");

    Integer sum = num1 + num2;
    result.addOutputData("sum", sum);

    log("Sum of " + num1 + " and " + num2 + " is " + sum, Level.INFO);

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
