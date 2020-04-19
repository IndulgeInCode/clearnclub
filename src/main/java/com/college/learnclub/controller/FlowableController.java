package com.college.learnclub.controller;

import com.college.learnclub.entity.TaskRepresentation;
import com.college.learnclub.service.impl.MyService;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FlowableController {
    @Autowired
    private MyService myService;

    /**
     * 部署process
     */
    @RequestMapping(value="/deploy", method= RequestMethod.POST)
    public String deploy() {
        boolean flag = myService.deploy();
        if(flag){
            return "部署成功";
        }else {
            return "部署失败";
        }
    }



    /**
     * 启动process
     */
    @RequestMapping(value="/process", method= RequestMethod.GET)
    public void startProcessInstance(@RequestParam String assignee, @RequestParam String taskId) {
        myService.startProcess(assignee, taskId);
    }

    /**
     * 查询需要执行的任务tasks
     * @param assignee
     * @return
     */
    @RequestMapping(value="/tasks", method= RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
    public List<TaskRepresentation> getTasks(@RequestParam String assignee) {
        List<Task> tasks = myService.getTasks(assignee);
        List<TaskRepresentation> dtos = new ArrayList<TaskRepresentation>();
        for (Task task : tasks) {
            dtos.add(new TaskRepresentation(task.getId(), task.getName()));
        }
        return dtos;
    }


    /**
     * 查询需要执行的任务tasks
     * @return
     */
    @RequestMapping(value="/complete", method= RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
    public String complete(@RequestParam String assignee,@RequestParam String taskName) {
        myService.complete(assignee, taskName);
        return "流程走到下一步";
    }

    /**
     * 获取历史任务
     * @return
     */
    @RequestMapping(value="/getHistoryTask", method= RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
    public List<TaskRepresentation> getHistoryTask(@RequestParam String assignee) {
        List<HistoricTaskInstance> historicTaskInstances = myService.getHistoryTask(assignee);
        List<TaskRepresentation> dtos = new ArrayList<TaskRepresentation>();

        for (HistoricTaskInstance historicTaskInstance : historicTaskInstances) {
            dtos.add(new TaskRepresentation(historicTaskInstance.getId(), historicTaskInstance.getName()));
        }
        return dtos;
    }
}
