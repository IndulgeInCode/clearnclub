package com.college.learnclub.service.impl;

import org.flowable.engine.common.api.delegate.event.FlowableEvent;
import org.flowable.engine.common.api.delegate.event.FlowableEventListener;
import org.springframework.stereotype.Service;

import static org.flowable.engine.common.api.delegate.event.FlowableEngineEventType.JOB_EXECUTION_FAILURE;
import static org.flowable.engine.common.api.delegate.event.FlowableEngineEventType.JOB_EXECUTION_SUCCESS;

@Service
public class MyEventListener implements FlowableEventListener {

    @Override
    public void onEvent(FlowableEvent event) {
//        switch (event.getType()) {
//
//            case JOB_EXECUTION_SUCCESS:
//                System.out.println("A job well done!");
//                break;
//
//            case JOB_EXECUTION_FAILURE:
//                System.out.println("A job has failed...");
//                break;
//
//            default:
//                System.out.println("Event received: " + event.getType());
//        }
    }

    @Override
    public boolean isFailOnException() {
        // onEvent方法中的逻辑并不重要，可以忽略日志失败异常……

        return false;
    }

    @Override
    public boolean isFireOnTransactionLifecycleEvent() {
        return false;
    }

    @Override
    public String getOnTransaction() {
        return null;
    }
}