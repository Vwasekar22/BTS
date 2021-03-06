package spring.ladybug.ladybugapp.pojos;
// Generated 19 Jan, 2020 1:05:57 PM by Hibernate Tools 5.0.6.Final

import static javax.persistence.GenerationType.IDENTITY;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * Employee generated by hbm2java
 */
@Entity
@Table(name = "employees", catalog = "ladybug_app_main")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "empId")
public class Employee implements java.io.Serializable {

	//Self Join for Manager and subordinates.
	
	private Integer empId;
	private Employee empMgr;
	private String firstName;
	private String lastName;
	private LocalDate createdOn;
	private String userName;
	private Set<Employee> employeeSubOrdinates = new HashSet<>();
	private Set<BugDtls> bugDtls = new HashSet<>();
	private Login login;
	private Set<Project> projects = new HashSet<>();

	public Employee() {
		System.out.println("in emp d controller");
	}
	public Employee(int id) {
		this.empId = id;
	}
	
	public Employee(Integer empId,String firstName,String lastName) {
		this.empId=empId;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Employee(Integer empId,String firstName,String lastName, Login login) {
		this.empId=empId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.login = login;
	}
	public Employee(Employee empMgr, String firstName, String lastName, LocalDate createdOn, String userName,
			Set<Employee> employeeSubOrdinates, Set<BugDtls> bugDtls, Login login, Set<Project> projects) {
		this.empMgr = empMgr;
		this.firstName = firstName;
		this.lastName = lastName;
		this.createdOn = createdOn;
		this.userName = userName;
		this.employeeSubOrdinates = employeeSubOrdinates;
		this.bugDtls = bugDtls;
		this.login = login;
		this.projects = projects;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "emp_id", unique = true, nullable = false)
	public Integer getEmpId() {
		return this.empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	@JsonProperty(access = Access.WRITE_ONLY)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "mgr_id")
	public Employee getEmpMgr() {
		return this.empMgr;
	}

	public void setEmpMgr(Employee employees) {
		this.empMgr = employees;
	}

	@Column(name = "first_name", length = 45)
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "last_name", length = 45)
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	// @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on", length = 19)
	public LocalDate getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(LocalDate createdOn) {
		this.createdOn = createdOn;
	}

	@Column(name = "user_name", length = 45)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "empMgr", cascade = CascadeType.ALL, orphanRemoval = true)
	public Set<Employee> getEmployeeSubOrdinates() {
		return this.employeeSubOrdinates;
	}

	public void setEmployeeSubOrdinates(Set<Employee> employeeSubOrdinates) {
		this.employeeSubOrdinates = employeeSubOrdinates;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "emp", cascade = CascadeType.ALL, orphanRemoval = true)
	public Set<BugDtls> getBugDtls() {
		return this.bugDtls;
	}

	public void setBugDtls(Set<BugDtls> bugDtls) {
		this.bugDtls = bugDtls;
	}

	@OneToOne(mappedBy = "emp", cascade = CascadeType.ALL)
	public Login getLogin() {
		return this.login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "emp_project_relation", catalog = "ladybug_app_main", joinColumns = {
			@JoinColumn(name = "e_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "proj_id", nullable = false, updatable = false) })
	public Set<Project> getProjects() {
		return this.projects;
	}

	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}

	// helper function
	// helper function
	public void addLogin(Login login) {
		this.login = login;
		login.setEmp(this);
	}

	public void removeLogin() {
		this.login = null;
	}

	public void addProjects(Project projs) {
		this.getProjects().add(projs);
		projs.getEmployees().add(this);

	}

	public void removeProjects(Project projs) {
		projs.setEmployees(null);
		projects.remove(projs);
	}

	public void addBugDetails(BugDtls bug) {
		this.bugDtls.add(bug);
		bug.setEmp(this);
	}

//	@Override
//	public String toString() {
//		return "Employee [empId=" + empId + ", empMgr=" + empMgr + ", firstName=" + firstName + ", lastName=" + lastName
//				+ ", createdOn=" + createdOn + ", userName=" + userName + ", employeeSubOrdinates="
//				+ employeeSubOrdinates + ", bugDtls=" + bugDtls + ", login=" + login + ", projects=" + projects + "]";
//	}
	public void removeBugDetails(BugDtls bug) {
		this.bugDtls.remove(bug);
		if (this.getEmpMgr() != null) {
			bug.setEmp(this.getEmpMgr()); // After removing the emp its associated bug is given to his manager
										// (bug_by_emp_id ---> mgr_id)
		} else {
			bug.setEmp(null);
		}

	}
}
