package uz.pdp.appjparelationships.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appjparelationships.entity.Address;
import uz.pdp.appjparelationships.entity.Group;
import uz.pdp.appjparelationships.entity.Student;
import uz.pdp.appjparelationships.entity.Subject;
import uz.pdp.appjparelationships.payload.StudentDto;
import uz.pdp.appjparelationships.repository.AddressRepository;
import uz.pdp.appjparelationships.repository.GroupRepository;
import uz.pdp.appjparelationships.repository.StudentRepository;
import uz.pdp.appjparelationships.repository.SubjectRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/student")
public class StudentController {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    SubjectRepository subjectRepository;

    //1. VAZIRLIK
    @GetMapping("/forMinistry")
    public Page<Student> getStudentListForMinistry(@RequestParam int page) {
        //1-1=0     2-1=1    3-1=2    4-1=3
        //select * from student limit 10 offset (0*10)
        //select * from student limit 10 offset (1*10)
        //select * from student limit 10 offset (2*10)
        //select * from student limit 10 offset (3*10)
        Pageable pageable = PageRequest.of(page, 10);
        Page<Student> studentPage = studentRepository.findAll(pageable);
        return studentPage;
    }

    //2. UNIVERSITY
    @GetMapping("/forUniversity/{universityId}")
    public Page<Student> getStudentListForUniversity(@PathVariable Integer universityId,
                                                     @RequestParam int page) {
        //1-1=0     2-1=1    3-1=2    4-1=3
        //select * from student limit 10 offset (0*10)
        //select * from student limit 10 offset (1*10)
        //select * from student limit 10 offset (2*10)
        //select * from student limit 10 offset (3*10)
        Pageable pageable = PageRequest.of(page, 10);
        Page<Student> studentPage = studentRepository.findAllByGroup_Faculty_UniversityId(universityId, pageable);
        return studentPage;
    }

    //3. FACULTY DEKANAT
    //4. GROUP OWNER

    @PostMapping("/addStudent")
    public String addStudent(@RequestBody StudentDto studentDto) {
        Student student = new Student();
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        Optional<Address> addressOptional = addressRepository.findById(studentDto.getAddress_id());
        if (!addressOptional.isPresent()) {
            return "Address not found";
        }
        student.setAddress(addressOptional.get());
        Optional<Group> groupOptional = groupRepository.findById(studentDto.getGroup_id());
        if (!groupOptional.isPresent()) {
            return "Group not found";
        }
        student.setGroup(groupOptional.get());
        List<Subject> subjects = new ArrayList<>();
        for (Integer id : studentDto.getSubject_id()) {
            Optional<Subject> subjectOptional = subjectRepository.findById(id);
            if (!subjectOptional.isPresent()) return "subject not found";
            subjects.add(subjectOptional.get());
        }
        student.setSubjects(subjects);

        studentRepository.save(student);
        return "Added student";
    }


    @PutMapping("/update/{id}")
    public String update(@PathVariable Integer id, @RequestBody StudentDto studentDto) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        if (!studentOptional.isPresent()) return "Student not found";
        Student student = studentOptional.get();
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());

        Optional<Address> addressOptional = addressRepository.findById(studentDto.getAddress_id());
        if (!addressOptional.isPresent()) {
            return "Address not found";
        }
        student.setAddress(addressOptional.get());
        Optional<Group> groupOptional = groupRepository.findById(studentDto.getGroup_id());
        if (!groupOptional.isPresent()) {
            return "Group not found";
        }
        student.setGroup(groupOptional.get());
        List<Subject> subjects = new ArrayList<>();
        for (Integer subject_id : studentDto.getSubject_id()) {
            Optional<Subject> subjectOptional = subjectRepository.findById(subject_id);
            if (!subjectOptional.isPresent()) return "subject not found";
            subjects.add(subjectOptional.get());
        }
        student.setSubjects(subjects);
        studentRepository.save(student);
        return "edited Student";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Integer id) {
        studentRepository.deleteById(id);
        return "deleted student";
    }


}
