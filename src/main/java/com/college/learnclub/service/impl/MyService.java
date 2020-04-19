package com.college.learnclub.service.impl;

import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.DeploymentBuilder;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MyService {
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RepositoryService repositoryService;


    @Autowired
    private HistoryService historyService;

    public boolean deploy(){
        try {
            DeploymentBuilder builder = repositoryService.createDeployment().addClasspathResource("process/oppo_cross.bpmn");
            builder.deploy();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    public void startProcess(String assignee, String taskId) {
        Map variables = new HashMap();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey("oppo_cross")
                .latestVersion()
                .singleResult();
        variables.put("userId", assignee);
        variables.put("leader1", assignee+"1");
        variables.put("leader2", assignee+"2");

        variables.put("task", taskId);
        runtimeService.startProcessInstanceById(processDefinition.getId(),"1", variables);
    }

    public List<Task> getTasks(String assignee) {
        return taskService.createTaskQuery().taskAssignee(assignee).list();
    }

    public void complete(String assignee, String taskName){
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(assignee).list();
        String taskId = "";
        for (Task task : tasks){
            if (task.getName().equals(taskName)){
                taskId = task.getId();
            }
        }
        Map variables = new HashMap<String, Object>();
        variables.put("userId", "123");
        taskService.complete(taskId, variables);

    }


    public List<HistoricTaskInstance> getHistoryTask(String assignee){
        return historyService.createHistoricTaskInstanceQuery().taskAssignee(assignee).list();

    }
}
