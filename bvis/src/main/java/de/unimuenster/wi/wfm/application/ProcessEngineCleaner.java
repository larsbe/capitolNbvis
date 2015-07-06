package de.unimuenster.wi.wfm.application;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;

// big thanks goes to Lars :-)

@Startup
@Singleton
public class ProcessEngineCleaner {
	
	@Inject
	protected ProcessEngine processEngine;

	@PostConstruct
	public void deleteAllRunningInstances() {
		RepositoryService repositoryService = processEngine.getRepositoryService();
		RuntimeService runtimeService = processEngine.getRuntimeService();
		
		List<ProcessDefinition> definitions = repositoryService
				.createProcessDefinitionQuery()
				.list();
		
		for (ProcessDefinition definition : definitions) {
			List<ProcessInstance> instances = runtimeService
					.createProcessInstanceQuery()
					.processDefinitionId(definition.getId())
					.list();
			for (ProcessInstance instance : instances) {
				runtimeService.deleteProcessInstance(instance.getId(), null);
				System.out.println("Deleted instance "+instance.getId());
			}
		}
	}
}
