package de.unimuenster.wi.wfm.application;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.camunda.bpm.engine.AuthorizationService;
import org.camunda.bpm.engine.FilterService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.authorization.Authorization;
import org.camunda.bpm.engine.authorization.Groups;
import org.camunda.bpm.engine.filter.Filter;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;

// big thanks goes to Lars :-)

@Startup
@Singleton
public class ProcessEngineCleaner {

	protected ProcessEngine processEngine;

	protected IdentityService identityService;
	protected RepositoryService repositoryService;
	protected RuntimeService runtimeService;
	protected FilterService filterService;
	protected AuthorizationService authorizationService;

	public void performCleaning(ProcessEngine pProcessEngine) {

		// init
		processEngine = pProcessEngine;
		identityService = processEngine.getIdentityService();
		repositoryService = processEngine.getRepositoryService();
		runtimeService = processEngine.getRuntimeService();
		filterService = processEngine.getFilterService();
		authorizationService = processEngine.getAuthorizationService();

		// actions
		deleteAllInstances();
		cleanMemberships();
		cleanUsers();
		cleanGroups();
		cleanFilter();
		cleanAuth();
	}

	private void cleanUsers() {
		identityService.deleteUser("bvis");
		identityService.deleteUser("jeremy");
		identityService.deleteUser("james");
		identityService.deleteUser("richard");
	}

	private void cleanGroups() {
		identityService.deleteGroup("bvis");
		identityService.deleteGroup("Contract_Handler");
		identityService.deleteGroup("ClaimHandler");
	}

	private void cleanMemberships() {
		identityService.deleteMembership("bvis", Groups.CAMUNDA_ADMIN);

		identityService.deleteMembership("jeremy", "bvis");
		identityService.deleteMembership("james", "bvis");
		identityService.deleteMembership("richard", "bvis");

		identityService.deleteMembership("jeremy", "Contract_Handler");
		identityService.deleteMembership("james", "Contract_Handler");
		identityService.deleteMembership("richard", "ClaimHandler");
	}

	private void cleanAuth() {
		List<Authorization> auths = authorizationService
				.createAuthorizationQuery().list();
		for (Authorization a : auths) {
			authorizationService.deleteAuthorization(a.getId());
		}
	}

	private void cleanFilter() {
		List<Filter> filters = filterService.createFilterQuery().list();
		for (Filter f : filters) {
			filterService.deleteFilter(f.getId());
		}
	}

	private void deleteAllInstances() {
		List<ProcessDefinition> definitions = repositoryService
				.createProcessDefinitionQuery().list();

		for (ProcessDefinition definition : definitions) {
			List<ProcessInstance> instances = runtimeService
					.createProcessInstanceQuery()
					.processDefinitionId(definition.getId()).list();
			for (ProcessInstance instance : instances) {
				runtimeService.deleteProcessInstance(instance.getId(), null);
				System.out.println("Deleted instance " + instance.getId());
			}
		}
	}
}
