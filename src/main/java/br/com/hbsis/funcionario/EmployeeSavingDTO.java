package br.com.hbsis.funcionario;

public class EmployeeSavingDTO {
    private String employeeName;
    private String employeeUuid;

    public EmployeeSavingDTO() {
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeUuid() {
        return employeeUuid;
    }

    public void setEmployeeUuid(String employeeUuid) {
        this.employeeUuid = employeeUuid;
    }
}
