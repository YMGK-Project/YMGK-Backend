package proje.v1.api.controller.rollcall;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import proje.v1.api.common.messages.Response;
import proje.v1.api.common.util.BindingValidator;
import proje.v1.api.message.rollcall.RequestManuelRollCallStudent;
import proje.v1.api.message.rollcall.RequestRollCallStudent;
import proje.v1.api.service.rollcall.RollCallService;

import javax.validation.Valid;

@Api(description = "Yoklama İşlemleri")
@RestController
@RequestMapping(value = "/rollcalls")
public class RollCallController {

    @Autowired
    private RollCallService rollCallService;

    @ApiOperation(value = "Yoklamaya öğrenci eklemeyi sağlar")
    @RequestMapping(value = "/students", method = RequestMethod.POST)
    public Response<String> rollCallStudent(@Valid @RequestBody RequestRollCallStudent requestRollCallStudent, BindingResult bindingResult){
        BindingValidator.validate(bindingResult);
        rollCallService.addToRollCall(requestRollCallStudent.getDeviceId(), requestRollCallStudent.getFingerMark());
        return new Response<>(200, true, "Student roll call successfully.");
    }

    @ApiOperation(value = "Yoklamaya manuel öğrenci eklemeyi sağlar")
    @RequestMapping(value = "/students/manuel", method = RequestMethod.POST)
    public Response<String> manuelRollCallStudent(@Valid @RequestBody RequestManuelRollCallStudent requestManuelRollCallStudent, BindingResult bindingResult){
        BindingValidator.validate(bindingResult);
        rollCallService.addToRollCall(requestManuelRollCallStudent.getDeviceId(), requestManuelRollCallStudent.getStudentId());
        return new Response<>(200, true, "Student roll call successfully.");
    }
}
