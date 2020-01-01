package proje.v1.api.controller.teacher;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import proje.v1.api.config.auth.ContextHolder;
import proje.v1.api.common.messages.Response;
import proje.v1.api.common.util.BindingValidator;
import proje.v1.api.converter.classroom.ClassroomConverter;
import proje.v1.api.converter.rollcall.RollCallConverter;
import proje.v1.api.converter.student.StudentConverter;
import proje.v1.api.converter.user.UserConverter;
import proje.v1.api.domian.classroom.Classroom;
import proje.v1.api.domian.rollcall.RollCall;
import proje.v1.api.domian.user.Users;
import proje.v1.api.dto.classroom.ClassroomDTO;
import proje.v1.api.dto.rollcall.RollCallDTO;
import proje.v1.api.dto.rollcall.RollCallStartDTO;
import proje.v1.api.dto.user.UserDTO;
import proje.v1.api.message.classroom.RequestClassroom;
import proje.v1.api.message.teacher.RequestFinishRollCall;
import proje.v1.api.message.teacher.RequestStartRollCall;
import proje.v1.api.service.classroom.ClassroomService;
import proje.v1.api.service.rollcall.RollCallService;
import proje.v1.api.service.user.RoleService;
import proje.v1.api.service.teacher.TeacherService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Api(description = "Öğretmen İşlemleri")
@RestController
@RequestMapping(value = "/teachers")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private RollCallConverter rollCallConverter;
    @Autowired
    private ClassroomService classroomService;
    @Autowired
    private ClassroomConverter classroomConverter;
    @Autowired
    private UserConverter userConverter;
    @Autowired
    private RollCallService rollCallService;
    @Autowired
    private RoleService roleService;
    private String TEACHER = "Teacher";

    @ApiOperation(value = "Öğretmenin id'ye derslik silmesini sağlar")
    @RequestMapping(value = "/classrooms/{id}", method = RequestMethod.DELETE)
    public Response<String> deleteClassroom(@PathVariable Long id){
        roleService.validatePermission(ContextHolder.user, TEACHER);
        teacherService.deleteClassroom(id);
        return new Response<>(200,true,"Derslik başarıyla silindi.");
    }

    @ApiOperation(value = "Öğretmeninin id'ye göre dersliğini getirir.")
    @RequestMapping(value = "/classrooms/{id}", method = RequestMethod.GET)
    public Response<ClassroomDTO> getClassroom(@PathVariable Long id){
        roleService.validatePermission(ContextHolder.user, TEACHER);
        Classroom classroom = classroomService.findById(id);
        ClassroomDTO classroomDTO = classroomConverter.convert(classroom);
        return new Response<>(200,true, classroomDTO);
    }

    @ApiOperation(value = "Öğretmeninin id'ye göre dersliğini günceller.")
    @RequestMapping(value = "/classrooms/{id}", method = RequestMethod.PUT)
    public Response<ClassroomDTO> updateClassroom(@PathVariable Long id, @Valid @RequestBody RequestClassroom requestClassroom, BindingResult bindingResult){
        BindingValidator.validate(bindingResult);
        roleService.validatePermission(ContextHolder.user, TEACHER);
        Classroom classroom = teacherService.updateClassroomAndGet(
                id,
                requestClassroom.getCod(),
                requestClassroom.getName(),
                requestClassroom.getSectionType(),
                requestClassroom.getEducationType()
        );
        ClassroomDTO classroomDTO = classroomConverter.convert(classroom);
        return new Response<>(200,true, classroomDTO);
    }

    @ApiOperation(value = "Öğretmen'e derslik ekler")
    @RequestMapping(value = "/classrooms", method = RequestMethod.POST)
    public Response<ClassroomDTO> addClassroom(@Valid @RequestBody RequestClassroom requestClassroom, BindingResult bindingResult){
        BindingValidator.validate(bindingResult);
        roleService.validatePermission(ContextHolder.user, TEACHER);
        Classroom classroom = teacherService.addClassroomAndGet(
                ContextHolder.user,
                requestClassroom.getCod(),
                requestClassroom.getName(),
                requestClassroom.getSectionType(),
                requestClassroom.getEducationType()
                );
        ClassroomDTO classroomDTO = classroomConverter.convert(classroom);
        return new Response<>(201, true, classroomDTO);
    }

    @ApiOperation(value = "Öğretmenin bütün dersliklerini getirir")
    @RequestMapping(value = "/classrooms", method = RequestMethod.GET)
    public Response<List<ClassroomDTO>> getAllClassroom(){
        roleService.validatePermission(ContextHolder.user, TEACHER);
        List<Classroom> classrooms = teacherService.findAllClassroomBy(ContextHolder.user.getTeacher().getId());
        List<ClassroomDTO> classroomDTOS = classroomConverter.convert(classrooms);
        return new Response<>(200, true, classroomDTOS);
    }

    @ApiOperation(value = "Öğretmenin qrCode ile yoklama başlatmasını sağlar")
    @RequestMapping(value = "/classrooms/start/rollcall", method = RequestMethod.POST)
    public Response<RollCallStartDTO> startRollCall(@RequestParam Long classroomId){
        roleService.validatePermission(ContextHolder.user, TEACHER);
        List<Users> users = teacherService.startRollCall(classroomId);
        String qrCodeStr = rollCallService.generateQrCodeAndStorage((byte)30, classroomId);
        List<UserDTO> userDTOS = new ArrayList<>();
        users.forEach(user -> userDTOS.add(userConverter.convert(user)));
        return new Response<>(200, true, new RollCallStartDTO(userDTOS, qrCodeStr));
    }

    //qr code silinecek
    @ApiOperation(value = "Öğretmenin yoklamayı bitirmesini sağlar")
    @RequestMapping(value = "/classrooms/finish/rollcall", method = RequestMethod.POST)
    public Response<RollCallDTO> finishRollCall(@RequestParam Long classroomId){
        roleService.validatePermission(ContextHolder.user, TEACHER);
        RollCall rollCall = teacherService.finishRollCall(classroomId);
        RollCallDTO rollCallDTO = rollCallConverter.convert(rollCall);
        return new Response<>(201, true, rollCallDTO);
    }

    @ApiOperation(value = "Öğretmenin aktif yoklama listesini almasını sağlar")
    @RequestMapping(value = "/classrooms/now/rollcall/{classroomId}", method = RequestMethod.GET)
    public Response<RollCallDTO> getActiveRollCall(@PathVariable Long classroomId){
        roleService.validatePermission(ContextHolder.user, TEACHER);
        RollCall rollCall = teacherService.getActiveRollCall(classroomId);
        RollCallDTO rollCallDTO = rollCallConverter.convert(rollCall);
        return new Response<>(200, true, rollCallDTO);
    }

    @ApiOperation(value = "Öğretmenin yoklamayı iptal etmesini sağlar")
    @RequestMapping(value = "/classrooms/cancel/rollcall/{deviceId}", method = RequestMethod.GET)
    public Response<String> cancelRollCall(@PathVariable Long deviceId){
        roleService.validatePermission(ContextHolder.user, TEACHER);
        teacherService.cancelRollCall(deviceId);
        return new Response<>(200, true, "Yoklama iptal edildi.");
    }
}
