package de.unimuenster.wi.wfm.application;

import static org.camunda.bpm.engine.authorization.Authorization.ANY;
import static org.camunda.bpm.engine.authorization.Authorization.AUTH_TYPE_GRANT;
import static org.camunda.bpm.engine.authorization.Permissions.ACCESS;
import static org.camunda.bpm.engine.authorization.Permissions.ALL;
import static org.camunda.bpm.engine.authorization.Permissions.READ;
import static org.camunda.bpm.engine.authorization.Resources.APPLICATION;
import static org.camunda.bpm.engine.authorization.Resources.FILTER;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.camunda.bpm.engine.AuthorizationService;
import org.camunda.bpm.engine.FilterService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.authorization.Authorization;
import org.camunda.bpm.engine.authorization.Groups;
import org.camunda.bpm.engine.authorization.Resource;
import org.camunda.bpm.engine.authorization.Resources;
import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.camunda.bpm.engine.filter.Filter;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.impl.persistence.entity.AuthorizationEntity;
import org.camunda.bpm.engine.task.TaskQuery;

import de.unimuenster.wi.wfm.persistence.CarData;
import de.unimuenster.wi.wfm.persistence.CarPool;
import de.unimuenster.wi.wfm.persistence.Customer;
import de.unimuenster.wi.wfm.persistence.RentalAgreementContract;
import de.unimuenster.wi.wfm.persistence.RentalAgreementRequest;
import de.unimuenster.wi.wfm.persistence.RentalAgreementRequestType;
import de.unimuenster.wi.wfm.persistence.StandardAgreementType;

@Startup
@Singleton
public class ApplicationInitializer {

	@PersistenceContext
	EntityManager em;

	@Inject
	private BusinessProcess businessProcess;

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

		// create Customers
		Customer c1 = new Customer();
		c1.setName("David Jauernig");
		c1.setEmail("david.jauernig@googlemail.com");
		c1.setAddress("Gruppenarbeitsraum");
		em.persist(c1);

		Customer c2 = new Customer();
		c2 = new Customer();
		c2.setName("Andi Hermann");
		c2.setEmail("a_herm14@uni-muenster.de");
		c2.setAddress("McFit");
		em.persist(c2);

		// create some Cars

		CarData carData61 = new CarData();
		carData61.setName("BMW 645 CI CABRIO");
		carData61.setHsn("0005");
		carData61.setTsn("156");
		carData61.setYear("2005");
		carData61.setLicenseNumber("MS-WF-1337");
		carData61 = em.merge(carData61);

		CarData carData62 = new CarData();
		carData62.setName("Audi A8 4.2");
		carData62.setHsn("0588");
		carData62.setTsn("581");
		carData62.setYear("2014");
		carData62.setLicenseNumber("ST-AR-2015");
		carData62 = em.merge(carData62);

		CarData carData63 = new CarData();
		carData63.setName("BMW 325 I Cabrio");
		carData63.setHsn("0005");
		carData63.setTsn("531");
		carData63.setYear("2012");
		carData63.setLicenseNumber("SE-XY-69");
		carData63 = em.merge(carData63);

		CarData carData64 = new CarData();
		carData64.setName("BMW 530 D");
		carData64.setHsn("0005");
		carData64.setTsn("761");
		carData64.setYear("2013");
		carData64.setLicenseNumber("FRI-TZ-99");
		carData64 = em.merge(carData64);

		CarData carData65 = new CarData();
		carData65.setName("Audi A3 2.0 TDI");
		carData65.setHsn("0588");
		carData65.setTsn("ADU");
		carData65.setYear("2014");
		carData65.setLicenseNumber("MS-JB-64");
		carData65 = em.merge(carData65);

		CarData carData6 = new CarData();
		carData6.setName("Opel Corsa-C 1.0");
		carData6.setHsn("0035");
		carData6.setTsn("394");
		carData6.setYear("2003");
		carData6.setLicenseNumber("MS-WI-90");
		carData6 = em.merge(carData6);

		RentalAgreementRequest req = new RentalAgreementRequest();
		req.setRentalAgreementRequestType(RentalAgreementRequestType.INDIVIDUAL);
		req.setCustomer(c2);
		req.setDate(new Date());
		req.setRequirementsOfCustomer("Ich habe tolle Anforderungen");
		req = em.merge(req);

		Collection<CarPool> carPool = new ArrayList<CarPool>();
		CarPool carPool_Item = new CarPool();
		carPool_Item.setCarData(carData61);
		carPool_Item.setQuantity(3);
		carPool_Item.setRentalAgreementRequest(req);
		carPool.add(carPool_Item);

		carPool_Item.setCarData(carData65);
		carPool_Item.setQuantity(7);
		carPool_Item.setRentalAgreementRequest(req);
		carPool.add(carPool_Item);

		req.setCarPool(carPool);
		req = em.merge(req);

		RentalAgreementContract contract = new RentalAgreementContract();
		contract.setCustomer(c1);
		contract.setDate(new Date());
		contract.setRentalAgreementRequest(req);
		contract = em.merge(contract);

		// ------ store business process variables -------
		businessProcess.setVariable("contractNoBVIS", contract.getId());

		// create StandardAgreementTypes
		StandardAgreementType sat1 = new StandardAgreementType();
		sat1.setName("Bronze");
		sat1.setDetail("Bronze Contract details");
		em.persist(sat1);

		StandardAgreementType sat2 = new StandardAgreementType();
		sat2.setName("Silver");
		sat2.setDetail("Silver Contract details");
		em.persist(sat2);

		StandardAgreementType sat3 = new StandardAgreementType();
		sat3.setName("Gold");
		sat3.setDetail("Gold Contract details");
		em.persist(sat3);
		
		
		////////
		createUser();
		createGroups();
		allocateUsersToGroups();
		createAuth();
		createTaskListFilter();

	}
	
	private void createUser() {
		identityService
				.saveUser(initUser("bvis", "Admin", "BVIS", "demo"));
		identityService.saveUser(initUser("jeremy", "Jeremy", "Clarkson",
				"demo"));
		identityService.saveUser(initUser("james", "James", "May",
				"demo"));
		identityService.saveUser(initUser("richard", "Richard", "Hammond", "demo"));
	}

	private void createGroups() {
		identityService.saveGroup(initGroup("bvis", "BVIS", "WORKFLOW"));
		identityService.saveGroup(initGroup("Contract_Handler",
				"Contracting", "WORKFLOW"));
		identityService.saveGroup(initGroup("ClaimHandler",
				"Claim Handler", "WORKFLOW"));
	}

	private void createAuth() {
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

		// Task-List
		Authorization capitolTasklistAuth = authorizationService
				.createNewAuthorization(AUTH_TYPE_GRANT);
		capitolTasklistAuth.setGroupId("bvis");
		capitolTasklistAuth.addPermission(ACCESS);
		capitolTasklistAuth.setResourceId("tasklist");
		capitolTasklistAuth.setResource(APPLICATION);
		authorizationService.saveAuthorization(capitolTasklistAuth);
	}

	private void allocateUsersToGroups() {
		identityService.createMembership("bvis", Groups.CAMUNDA_ADMIN);

		identityService.createMembership("jeremy", "bvis");
		identityService.createMembership("james", "bvis");
		identityService.createMembership("richard", "bvis");

		identityService.createMembership("jeremy", "Contract_Handler");
		identityService.createMembership("james", "Contract_Handler");
		identityService.createMembership("richard", "ClaimHandler");
	}

	private void createTaskListFilter() {
		Map<String, Object> filterProperties = new HashMap<String, Object>();
		TaskQuery query;

		/* New Liability Cases */
		filterProperties.clear();
		filterProperties.put("description",
				"New Claims");
		filterProperties.put("priority", 10);
		filterProperties.put("refresh", true);
		//filterProperties.put("color", "#8ad69a");
		addVariables(filterProperties);
		query = taskService.createTaskQuery().taskName("check the claim's eligibility");
		Filter newLiabilityCasesFilter = filterService.newTaskFilter()
				.setName("New Insurance Claims").setProperties(filterProperties)
				.setOwner("bvis").setQuery(query);
		filterService.saveFilter(newLiabilityCasesFilter);

		Authorization newLiabilityCaseGroup1FilterRead = authorizationService
				.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
		newLiabilityCaseGroup1FilterRead.setResource(FILTER);
		newLiabilityCaseGroup1FilterRead.setResourceId(newLiabilityCasesFilter
				.getId());
		newLiabilityCaseGroup1FilterRead.addPermission(READ);
		newLiabilityCaseGroup1FilterRead.setGroupId("ClaimHandler");
		authorizationService
				.saveAuthorization(newLiabilityCaseGroup1FilterRead);

		// deactivate 2nd lvl
		// Authorization newLiabilityCaseGroup2FilterRead = authorizationService
		// .createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
		// newLiabilityCaseGroup2FilterRead.setResource(FILTER);
		// newLiabilityCaseGroup2FilterRead.setResourceId(newLiabilityCasesFilter
		// .getId());
		// newLiabilityCaseGroup2FilterRead.addPermission(READ);
		// newLiabilityCaseGroup2FilterRead
		// .setGroupId("Second_Level_Case_Handler");
		// authorizationService
		// .saveAuthorization(newLiabilityCaseGroup2FilterRead);

		/* New Insurance Contract */
		filterProperties.clear();
		filterProperties.put("description", "All Rental Request");
		filterProperties.put("priority", 10);
		filterProperties.put("refresh", true);
		//filterProperties.put("color", "#8ad69a");
		addVariables(filterProperties);
		query = taskService.createTaskQuery()
				.taskName("negotiate agreement conditions with customer (via telephone or face2face)");
		Filter openIncuranceContractsFilter = filterService.newTaskFilter()
				.setName("New Cases")
				.setProperties(filterProperties).setOwner("bvis")
				.setQuery(query);
		filterService.saveFilter(openIncuranceContractsFilter);

		Authorization newRentalAgreementGroupFilterRead = authorizationService
				.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
		newRentalAgreementGroupFilterRead.setResource(FILTER);
		newRentalAgreementGroupFilterRead
				.setResourceId(openIncuranceContractsFilter.getId());
		newRentalAgreementGroupFilterRead.addPermission(READ);
		newRentalAgreementGroupFilterRead.setGroupId("Contracting");
		authorizationService
				.saveAuthorization(newRentalAgreementGroupFilterRead);
		
		Authorization newRentalAgreementGroup1FilterRead = authorizationService
				.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
		newRentalAgreementGroup1FilterRead.setResource(FILTER);
		newRentalAgreementGroup1FilterRead
				.setResourceId(openIncuranceContractsFilter.getId());
		newRentalAgreementGroup1FilterRead.addPermission(READ);
		newRentalAgreementGroup1FilterRead.setGroupId("Negotiations");
		authorizationService
				.saveAuthorization(newRentalAgreementGroup1FilterRead);

		// My Tasks
		filterProperties.clear();
		filterProperties.put("description", "Tasks assigned to me");
		filterProperties.put("priority", -10);
		filterProperties.put("refresh", true);
		addVariables(filterProperties);
		query = taskService.createTaskQuery().taskAssigneeExpression(
				"${currentUser()}");
		Filter myTasksFilter = filterService.newTaskFilter()
				.setName("My Tasks").setProperties(filterProperties)
				.setOwner("bvis").setQuery(query);
		filterService.saveFilter(myTasksFilter);

		// Unasigned Tasks
		filterProperties.clear();
		filterProperties.put("description",
				"Tasks that have not been assigned yet");
		filterProperties.put("priority", -10);
		filterProperties.put("refresh", true);
		addVariables(filterProperties);
		query = taskService.createTaskQuery()
				.taskCandidateGroupInExpression("${currentUserGroups()}")
				.taskUnassigned();
		Filter unassignedTasksFilter = filterService.newTaskFilter()
				.setName("Unassigned Tasks").setProperties(filterProperties)
				.setOwner("bvis").setQuery(query);
		filterService.saveFilter(unassignedTasksFilter);

		// all tasks
		filterProperties.clear();
		filterProperties.put("description", "All Tasks");
		filterProperties.put("priority", 10);
		addVariables(filterProperties);
		query = taskService.createTaskQuery();
		Filter allTasksFilter = filterService.newTaskFilter()
				.setName("All Tasks").setProperties(filterProperties)
				.setOwner("bvis").setQuery(query);
		filterService.saveFilter(allTasksFilter);

		// global read authorizations for these filters

		Authorization globalMyTaskFilterRead = authorizationService
				.createNewAuthorization(Authorization.AUTH_TYPE_GLOBAL);
		globalMyTaskFilterRead.setResource(FILTER);
		globalMyTaskFilterRead.setResourceId(myTasksFilter.getId());
		globalMyTaskFilterRead.addPermission(READ);
		authorizationService.saveAuthorization(globalMyTaskFilterRead);

		Authorization globalUnassignedFilterRead = authorizationService
				.createNewAuthorization(Authorization.AUTH_TYPE_GLOBAL);
		globalUnassignedFilterRead.setResource(FILTER);
		globalUnassignedFilterRead.setResourceId(unassignedTasksFilter.getId());
		globalUnassignedFilterRead.addPermission(READ);
		authorizationService.saveAuthorization(globalUnassignedFilterRead);

		// only: user "capitol" for debug reasons
		// Authorization globalAllTasksFilterRead = authorizationService
		// .createNewAuthorization(Authorization.AUTH_TYPE_GLOBAL);
		// globalAllTasksFilterRead.setResource(FILTER);
		// globalAllTasksFilterRead.setResourceId(allTasksFilter.getId());
		// globalAllTasksFilterRead.addPermission(READ);
		// authorizationService.saveAuthorization(globalAllTasksFilterRead);

	}

	/*
	 * Helpers
	 */

	private User initUser(String login, String fname, String lname, String pwd) {
		User user = identityService.newUser(login);
		user.setFirstName(fname);
		user.setLastName(lname);
		user.setPassword(pwd);
		user.setEmail("bvis.service+" + login + "@gmail.com");
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
		User singleResult = identityService.createUserQuery().userId("capitol")
				.singleResult();
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