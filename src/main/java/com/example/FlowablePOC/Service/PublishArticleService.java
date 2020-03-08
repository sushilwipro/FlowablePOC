package com.example.FlowablePOC.Service;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

import java.awt.*;

public class PublishArticleService implements JavaDelegate {

    public void execute(DelegateExecution execution){
        System.out.println("Publishing the approved article.");
    }
}
