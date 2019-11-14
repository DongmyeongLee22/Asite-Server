package me.asite.student;

import lombok.RequiredArgsConstructor;
import me.asite.student.dto.StudentJoinRequestDto;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentService implements UserDetailsService {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;


    public Student join(StudentJoinRequestDto joinRequestDto) {
        Student student = modelMapper.map(joinRequestDto, Student.class);
        student.encodingPassword(this.passwordEncoder.encode(student.getPassword()));
        return this.studentRepository.save(student);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Student student = studentRepository.findByStudentNumber(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return new User(student.getStudentNumber(), student.getPassword(), authorities(student.getRoles()));
    }

    private Collection<? extends GrantedAuthority> authorities(Set<StudentRole> roles) {
        return roles.stream()
                .map(r -> new SimpleGrantedAuthority("ROLS_" + r.name()))
                .collect(Collectors.toSet());
    }
}
