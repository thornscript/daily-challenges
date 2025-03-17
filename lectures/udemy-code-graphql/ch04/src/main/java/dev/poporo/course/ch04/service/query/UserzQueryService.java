package dev.poporo.course.ch04.service.query;

import dev.poporo.course.ch04.datasource.problemz.entity.Userz;
import dev.poporo.course.ch04.datasource.problemz.repository.UserzRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserzQueryService {

    @Autowired
    private UserzRepository userzRepository;

    public Optional<Userz> findUserzByAuthToken(String authToken) {
        return userzRepository.findUserByToken(authToken);
    }
}
