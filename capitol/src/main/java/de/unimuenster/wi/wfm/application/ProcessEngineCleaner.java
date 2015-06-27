package de.unimuenster.wi.wfm.application;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.ProcessDefinition;

@Startup
@Singleton
public class ProcessEngineCleaner {
	
	@Inject
	protected ProcessEngine processEngine;

	@PostConstruct
	public void initialise() {
		RepositoryService repositoryService = processEngine.getRepositoryService();
		
		List<ProcessDefinition> definitions = repositoryService
				.createProcessDefinitionQuery()
				.active()
				.list();
		
		for (ProcessDefinition definition : definitions) {
			repositoryService.deleteDeployment(definition.getId(), true);
			System.out.println("Deleted definition "+definition.getId());
		}
		
		if (false) {
			long count = repositoryService.createProcessDefinitionQuery().count();
			throw new RuntimeException("test" + count);
		}
	}
}
