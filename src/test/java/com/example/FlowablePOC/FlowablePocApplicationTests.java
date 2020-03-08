package com.example.FlowablePOC;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.test.Deployment;
import org.flowable.spring.impl.test.FlowableSpringExtension;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(FlowableSpringExtension.class)
@ExtendWith(SpringExtension.class)
class FlowablePocApplicationTests {

	@Test
	void contextLoads() {
	}
	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Test
	@Deployment(resources = {"Processses/article-workflow.bpmn20.xml"})
	void articleApprovalTest() {
		Map<String, Object> variables = new HashMap<>();
		variables.put("author", "test@baeldung.com");
		variables.put("url", "http://baeldung.com/dummy");

		runtimeService.startProcessInstanceByKey("articleReview", variables);
		Task task = taskService.createTaskQuery().singleResult();

		assertEquals("Review the submitted tutorial", task.getName());
		variables.put("approved", true);
		taskService.complete(task.getId(), variables);

		assertEquals(0, runtimeService.createProcessInstanceQuery().count());
	}

}
