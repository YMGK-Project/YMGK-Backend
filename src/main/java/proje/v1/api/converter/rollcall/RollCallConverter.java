package proje.v1.api.converter.rollcall;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import proje.v1.api.converter.Converter;
import proje.v1.api.converter.student.StudentConverter;
import proje.v1.api.domian.rollcall.RollCall;
import proje.v1.api.dto.rollcall.RollCallDTO;
import proje.v1.api.dto.student.StudentDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class RollCallConverter implements Converter<RollCall, RollCallDTO> {

    @Autowired
    private StudentConverter studentConverter;

    @Override
    public RollCallDTO convert(RollCall rollCall) {
        List<StudentDTO> inComingStudents = new ArrayList<>();
        List<StudentDTO> nonStudents = new ArrayList<>();
        rollCall.getInComingStudents().forEach(student -> inComingStudents.add(studentConverter.convert(student)));
        rollCall.getNonStudents().forEach(student -> nonStudents.add(studentConverter.convert(student)));
        return new RollCallDTO(rollCall.getId(), inComingStudents, nonStudents);
    }


}
