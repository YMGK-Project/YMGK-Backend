package proje.v1.api.service.rollcall;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import proje.v1.api.common.util.Crypt;
import proje.v1.api.common.util.RandomOperations;
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
    private RedisTemplate<String, RollCall> rollCallRedisTemplate;
    private RedisTemplate<String, String> qrRedisTemplate;
    private HashOperations hashOperations;
    private ValueOperations valueOperations;
    private final String KEY = "RollCall";
    private final String QR_KEY = "QR";

    public RollCallService(RedisTemplate<String, RollCall> rollCallRedisTemplate, @Qualifier("QrTemplate") RedisTemplate<String, String> qrRedisTemplate){
        this.rollCallRedisTemplate = rollCallRedisTemplate;
        this.qrRedisTemplate = qrRedisTemplate;
        hashOperations = rollCallRedisTemplate.opsForHash();
        valueOperations = qrRedisTemplate.opsForValue();
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

    public void startRollCallWith(Long classroomId) {
        verifyRollCallIsNotAlreadyStarting(classroomId);
        RollCall rollCall = new RollCall();
        hashOperations.put(QR_KEY, classroomId, rollCall);
    }

    public void cancelRollCall(Long classroomId){
        boolean isExist = isRollCallExist(classroomId);
        if(!isExist)
            throw new NotFoundException("Not found any roll call currently progress.");
        hashOperations.delete(QR_KEY, classroomId);
    }

    private void verifyRollCallIsNotAlreadyStarting(Long classroomId){
        if(isRollCallExist(classroomId))
            throw new BadRequestExcepiton("Roll call already begin.");
    }

    private boolean isRollCallExist(Long classroomId){
        RollCall rollCall = (RollCall)hashOperations.get(QR_KEY, classroomId);
        return rollCall != null;
    }

    public RollCall finishRollCall(Long classroomId) {
        RollCall rollCall = (RollCall)hashOperations.get(QR_KEY, classroomId);
        if(rollCall == null)
            throw new NotFoundException("Not found any roll call currently progress.");
        hashOperations.delete(QR_KEY, classroomId);
        return rollCall;
    }

    /*public void addToRollCall(Long deviceId, String fingerMark) {
        RollCall rollCall = (RollCall)hashOperations.get(KEY, deviceId);
        if(rollCall == null)
            throw new NotFoundException("Not found any roll call currently progress.");
        String hashedFingerMark = Crypt.hashWithSha256(fingerMark);
        Student student = studentService.findByFingerMark(hashedFingerMark);
        checkStudentIsNotExist(rollCall, student);
        rollCall.getInComingStudents().add(student);
        hashOperations.put(KEY, deviceId, rollCall);
    }*/

    public void addToRollCall(Long deviceId, Long studentId) {
        RollCall rollCall = (RollCall)hashOperations.get(KEY, deviceId);
        if(rollCall == null)
            throw new NotFoundException("Not found any roll call currently progress.");
        Student student = studentService.findById(studentId);
        checkStudentIsNotExist(rollCall, student);
        rollCall.getInComingStudents().add(student);
        hashOperations.put(KEY, deviceId, rollCall);
    }

    public void addToRollCall(String qRCodeStr, Long studentId) {
        Long classroomId = (long) (int) valueOperations.get(qRCodeStr);
        System.out.println(classroomId);
        System.out.println(qRCodeStr);
        RollCall rollCall = (RollCall)hashOperations.get(QR_KEY, classroomId);
        if(rollCall == null)
            throw new NotFoundException("Not found any roll call currently progress.");
        Student student = studentService.findById(studentId);
        checkStudentIsNotExist(rollCall, student);
        rollCall.getInComingStudents().add(student);
        hashOperations.put(QR_KEY, classroomId, rollCall);
    }

    private void checkStudentIsNotExist(RollCall rollCall, Student mStudent) {
        rollCall.getInComingStudents().forEach(student -> {
            if(student.getId().equals(mStudent.getId()))
                throw new BadRequestExcepiton("Student already in rollcall");
        });
    }

    public RollCall getActiveRollCall(Long classroomId) {
        RollCall rollCall = (RollCall)hashOperations.get(QR_KEY, classroomId);
        if(rollCall == null)
            throw new NotFoundException("Not found any roll call currently progress.");
        return rollCall;
    }

    public String generateQrCodeAndStorage(byte size, Long classroomId){
        String qrString = new RandomOperations().generateRandomString(size);
        valueOperations.append(qrString, classroomId.toString());
        return qrString;
    }
}
