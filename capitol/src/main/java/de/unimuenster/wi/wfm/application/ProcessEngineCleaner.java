package de.unimuenster.wi.wfm.application;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.camunda.bpm.engine.FilterService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.authorization.Groups;
import org.camunda.bpm.engine.filter.Filter;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;

import de.unimuenster.wi.wfm.sharedLib.data.AgreementConditions;
import de.unimuenster.wi.wfm.sharedLib.data.CarData;
import de.unimuenster.wi.wfm.sharedLib.data.CustomerData;
import de.unimuenster.wi.wfm.sharedLib.data.InsuranceBenefit;
import de.unimuenster.wi.wfm.sharedLib.data.RentalAgreementMessage;

public class ProcessEngineCleaner {

	protected ProcessEngine processEngine;

	protected IdentityService identityService;
	protected RepositoryService repositoryService;
	protected RuntimeService runtimeService;
	protected FilterService filterService;

	public void performCleaning(ProcessEngine pProcessEngine) {

		// init
		processEngine = pProcessEngine;
		identityService = processEngine.getIdentityService();
		repositoryService = processEngine.getRepositoryService();
		runtimeService = processEngine.getRuntimeService();
		filterService = processEngine.getFilterService();

		// actions
		deleteAllInstances();
		cleanMemberships();
		cleanUsers();
		cleanGroups();
		cleanFilter();
	}

	private void cleanUsers() {
		identityService.deleteUser("stromberg");
		identityService.deleteUser("heisterkamp");
		identityService.deleteUser("steinke");
	}

	private void cleanGroups() {
		identityService.deleteGroup("capitol");
		identityService.deleteGroup("First_Level_Case_Handler");
		identityService.deleteGroup("Second_Level_Case_Handler");
		identityService.deleteGroup("Contract_Handler");
	}

	private void cleanMemberships() {
		identityService.deleteMembership("stromberg", Groups.CAMUNDA_ADMIN);

		identityService.deleteMembership("stromberg", "capitol");
		identityService.deleteMembership("heisterkamp", "capitol");
		identityService.deleteMembership("steinke", "capitol");

		identityService.deleteMembership("stromberg", "Contract_Handler");
		identityService.deleteMembership("heisterkamp",
				"First_Level_Case_Handler");
		identityService
				.deleteMembership("steinke", "Second_Level_Case_Handler");
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
