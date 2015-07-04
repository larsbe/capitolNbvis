package de.unimuenster.wi.wfm.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import static org.camunda.bpm.engine.authorization.Authorization.ANY;
import static org.camunda.bpm.engine.authorization.Authorization.AUTH_TYPE_GRANT;
import static org.camunda.bpm.engine.authorization.Permissions.ACCESS;
import static org.camunda.bpm.engine.authorization.Permissions.ALL;
import static org.camunda.bpm.engine.authorization.Permissions.READ;
import static org.camunda.bpm.engine.authorization.Permissions.UPDATE;
import static org.camunda.bpm.engine.authorization.Resources.APPLICATION;
import static org.camunda.bpm.engine.authorization.Resources.FILTER;
import static org.camunda.bpm.engine.authorization.Resources.TASK;
import static org.camunda.bpm.engine.authorization.Resources.USER;

import org.camunda.bpm.engine.AuthorizationService;
import org.camunda.bpm.engine.FilterService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.authorization.Groups;
import org.camunda.bpm.engine.filter.Filter;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.impl.persistence.entity.AuthorizationEntity;
import org.camunda.bpm.engine.task.TaskQuery;
import org.camunda.bpm.engine.authorization.Resource;
import org.camunda.bpm.engine.authorization.Resources;

import de.unimuenster.wi.wfm.ejb.CustomerServiceBean;
import de.unimuenster.wi.wfm.ejb.InsuranceContractServiceBean;
import de.unimuenster.wi.wfm.ejb.LiabilityCaseServiceBean;
import de.unimuenster.wi.wfm.entitiy.CaseStatus;
import de.unimuenster.wi.wfm.entitiy.Customer;
import de.unimuenster.wi.wfm.entitiy.InsuranceContract;
import de.unimuenster.wi.wfm.entitiy.LiabilityCase;

@Startup
@Singleton
public class DemoDataGenerator {

	@EJB
	private LiabilityCaseServiceBean liabilityCaseService;

	@EJB
	private CustomerServiceBean customerService;

	@EJB
	private InsuranceContractServiceBean insuranceContractService;

	@Inject
	protected ProcessEngine processEngine;

	protected IdentityService identityService;
	protected FilterService filterService;
	protected TaskService taskService;
	protected AuthorizationService authorizationService;

	@PostConstruct
	public void initialise() {

		// cleanup
		ProcessEngineCleaner roomService = new ProcessEngineCleaner();
		roomService.performCleaning(processEngine);

		// init
		identityService = processEngine.getIdentityService();
		filterService = processEngine.getFilterService();
		taskService = processEngine.getTaskService();
		authorizationService = processEngine.getAuthorizationService();

		// generate
		Customer c1 = new Customer();
		c1.setName("BVIS");
		customerService.createCustomer(c1);

		InsuranceContract ic1 = new InsuranceContract();
		ic1.setCustomer(c1);
		ic1.setInsuranceFee(125.88);
		insuranceContractService.createInsuranceContract(ic1);

		// only if no demo data is avl.
		if (isDemoDataAvl())
			return;

		createUser();
		createGroups();
		allocateUsersToGroups();
		createTaskListFilter();
	}

	/*
	 * Generators
	 */

	private void createUser() {
		identityService.saveUser(initUser("stromberg", "Bernd", "Stromberg",
				"demo"));
		identityService.saveUser(initUser("heisterkamp", "Berthold (Ernie)",
				"Heisterkamp", "demo"));
		identityService.saveUser(initUser("steinke", "Ulf", "Steinke", "demo"));

	}

	private void createGroups() {
		identityService.saveGroup(initGroup("capitol", "Capitol", "WORKFLOW"));
		identityService.saveGroup(initGroup("First_Level_Case_Handler",
				"First Level Case Handler", "WORKFLOW"));
		identityService.saveGroup(initGroup("Second_Level_Case_Handler",
				"Second Level Case Handler", "WORKFLOW"));
		identityService.saveGroup(initGroup("Contract_Handler",
				"Contract Handler", "WORKFLOW"));

		// create admin group if necessary
		if (identityService.createGroupQuery().groupId(Groups.CAMUNDA_ADMIN)
				.count() == 0) {
			Group camundaAdminGroup = identityService
					.newGroup(Groups.CAMUNDA_ADMIN);
			camundaAdminGroup.setName("camunda BPM Administrators");
			camundaAdminGroup.setType(Groups.GROUP_TYPE_SYSTEM);
			identityService.saveGroup(camundaAdminGroup);
		}

		// create ADMIN authorizations on all built-in resources
		for (Resource resource : Resources.values()) {
			if (authorizationService.createAuthorizationQuery()
					.groupIdIn(Groups.CAMUNDA_ADMIN).resourceType(resource)
					.resourceId(ANY).count() == 0) {
				AuthorizationEntity userAdminAuth = new AuthorizationEntity(
						AUTH_TYPE_GRANT);
				userAdminAuth.setGroupId(Groups.CAMUNDA_ADMIN);
				userAdminAuth.setResource(resource);
				userAdminAuth.setResourceId(ANY);
				userAdminAuth.addPermission(ALL);
				authorizationService.saveAuthorization(userAdminAuth);
			}
		}
	}

	private void allocateUsersToGroups() {
		identityService.createMembership("stromberg", Groups.CAMUNDA_ADMIN);

		identityService.createMembership("stromberg", "capitol");
		identityService.createMembership("heisterkamp", "capitol");
		identityService.createMembership("steinke", "capitol");

		identityService.createMembership("stromberg", "Contract_Handler");
		identityService.createMembership("heisterkamp",
				"First_Level_Case_Handler");
		identityService
				.createMembership("steinke", "Second_Level_Case_Handler");
	}

	private void createTaskListFilter() {
		Map<String, Object> filterProperties = new HashMap<String, Object>();
		TaskQuery query;

		/* New Liability Cases */
		filterProperties.clear();
		filterProperties.put("description",
				"All Liability Cases that have not been checked yet");
		filterProperties.put("priority", 10);
		filterProperties.put("refresh", true);
		filterProperties.put("color", "#0cb52c");
		addVariables(filterProperties);
		query = taskService.createTaskQuery().taskName("Check eligibility");
		Filter newLiabilityCasesFilter = filterService.newTaskFilter()
				.setName("New Liability Cases").setProperties(filterProperties)
				.setOwner("stromberg").setQuery(query);
		filterService.saveFilter(newLiabilityCasesFilter);

		/* New Insurance Contract */
		filterProperties.clear();
		filterProperties.put("description", "All Open Negotiation Cases");
		filterProperties.put("priority", 10);
		filterProperties.put("refresh", true);
		filterProperties.put("color", "#0cb52c");
		addVariables(filterProperties);
		query = taskService.createTaskQuery()
				.taskName("Add insurance benefits");
		Filter openIncuranceContractsFilter = filterService.newTaskFilter()
				.setName("Open Negotiation Cases")
				.setProperties(filterProperties).setOwner("stromberg")
				.setQuery(query);
		filterService.saveFilter(openIncuranceContractsFilter);

		// all tasks
		filterProperties.clear();
		filterProperties.put("description",
				"All Tasks");
		filterProperties.put("priority", 10);
		addVariables(filterProperties);
		query = taskService.createTaskQuery();
		Filter allTasksFilter = filterService.newTaskFilter()
				.setName("All Tasks").setProperties(filterProperties)
				.setOwner("stromberg").setQuery(query);
		filterService.saveFilter(allTasksFilter);
	}

	/*
	 * Helpers
	 */

	private User initUser(String login, String fname, String lname, String pwd) {
		User user = identityService.newUser(login);
		user.setFirstName(fname);
		user.setLastName(lname);
		user.setPassword(pwd);
		user.setEmail("capitol.wfm+" + login + "@gmail.com");
		return user;
	}

	private Group initGroup(String id, String name, String type) {
		Group salesGroup = identityService.newGroup(id);
		salesGroup.setName(name);
		salesGroup.setType(type);
		identityService.saveGroup(salesGroup);
		return salesGroup;
	}

	private boolean isDemoDataAvl() {
		User singleResult = identityService.createUserQuery()
				.userId("stromberg").singleResult();
		if (singleResult != null) {
			return true;
		}
		return false;
	}

	private void addVariables(Map<String, Object> filterProperties) {
		List<Object> variables = new ArrayList<Object>();
		addVariable(variables, "amount", "Invoice Amount");
		addVariable(variables, "invoiceNumber", "Invoice Number");
		addVariable(variables, "creditor", "Creditor");
		addVariable(variables, "approver", "Approver");
		filterProperties.put("variables", variables);
	}

	private void addVariable(List<Object> variables, String name, String label) {
		Map<String, String> variable = new HashMap<String, String>();
		variable.put("name", name);
		variable.put("label", label);
		variables.add(variable);
	}

}
