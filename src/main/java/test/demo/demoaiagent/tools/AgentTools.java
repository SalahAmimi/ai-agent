package test.demo.demoaiagent.tools;


import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;
import test.demo.demoaiagent.model.EmployeeInfo;

@Component
public class AgentTools {

    @Tool(description = "Get Information about an employee (name, salary, seniority)")
    public EmployeeInfo getEmployeeInfo(
            @ToolParam(description = "Employee Name") String employeeName){
        return new EmployeeInfo(
                employeeName, 13000, 5
        );
    }
}
