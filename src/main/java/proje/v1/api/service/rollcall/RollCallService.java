package proje.v1.api.service.rollcall;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import proje.v1.api.common.util.Crypt;
import proje.v1.api.domian.rollcall.RollCall;
import proje.v1.api.domian.rollcall.RollCallRepository;
import proje.v1.api.domian.student.Student;
import proje.v1.api.exception.BadRequestExcepiton;
import proje.v1.api.exception.NotFoundException;
import proje.v1.api.service.student.StudentService;

import java.util.List;

@Service
public class RollCallService {

    @Autowired
    private StudentService studentService;
    @Autowired
    private RollCallRepository rollCallRepository;
    private RedisTemplate<String, RollCall> redisTemplate;
    private HashOperations hashOperations;
    private final String KEY = "RollCall";

    public RollCallService(RedisTemplate<String, RollCall> redisTemplate){
        this.redisTemplate = redisTemplate;
        hashOperations = redisTemplate.opsForHash();
    }

    public RollCall save(RollCall rollCall){
        return rollCallRepository.save(rollCall);
    }

    public List<RollCall> findAll() {
        return rollCallRepository.findAll();
    }

    public void startRollCall(Long deviceId) {
        verifyRollCallIsNotAlreadyStarting(deviceId);
        RollCall rollCall = new RollCall();
        hashOperations.put(KEY, deviceId, rollCall);
    }

    public void cancelRollCall(Long deviceId){
        boolean isExist = isRollCallExist(deviceId);
        if(isExist)
            hashOperations.delete(KEY, deviceId);
        throw new NotFoundException("Not found any roll call currently progress.");
    }

    private void verifyRollCallIsNotAlreadyStarting(Long deviceId){
        if(isRollCallExist(deviceId))
            throw new BadRequestExcepiton("Roll call already begin.");
    }

    private boolean isRollCallExist(Long deviceId){
        RollCall rollCall = (RollCall)hashOperations.get(KEY, deviceId);
        return rollCall != null;
    }

    public RollCall finishRollCall(Long deviceId) {
        RollCall rollCall = (RollCall)hashOperations.get(KEY, deviceId);
        if(rollCall == null)
            throw new NotFoundException("Not found any roll call currently progress.");
        hashOperations.delete(KEY, deviceId);
        return rollCall;
    }

    public void addToRollCall(Long deviceId, String fingerMark) {
        RollCall rollCall = (RollCall)hashOperations.get(KEY, deviceId);
        if(rollCall == null)
            throw new NotFoundException("Not found any roll call currently progress.");
        String hashedFingerMark = Crypt.hashWithSha256(fingerMark);
        Student student = studentService.findByFingerMark(hashedFingerMark);
        checkStudentIsNotExist(rollCall, student);
        rollCall.getInComingStudents().add(student);
        hashOperations.put(KEY, deviceId, rollCall);
    }

    private void checkStudentIsNotExist(RollCall rollCall, Student mStudent) {
        rollCall.getInComingStudents().forEach(student -> {
            if(student.getId().equals(mStudent.getId()))
                throw new BadRequestExcepiton("Student already in rollcall");
        });
    }

    public RollCall getActiveRollCall(Long deviceId) {
        RollCall rollCall = (RollCall)hashOperations.get(KEY, deviceId);
        if(rollCall == null)
            throw new NotFoundException("Not found any roll call currently progress.");
        return rollCall;
    }
}
