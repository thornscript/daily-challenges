package dev.poporo.course.ch04.service.command;

import dev.poporo.course.ch04.datasource.problemz.entity.UserzToken;
import dev.poporo.course.ch04.datasource.problemz.repository.UserzRepository;
import dev.poporo.course.ch04.datasource.problemz.repository.UserzTokenRepository;
import dev.poporo.course.ch04.exception.ProblemzAuthenticationException;
import dev.poporo.course.ch04.util.HashUtil;
import dev.poporo.course.ch04.util.RandomStringUtils;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserzCommandService {

    @Autowired
    private UserzRepository userzRepository;

    @Autowired
    private UserzTokenRepository userzTokenRepository;

    public UserzToken login(String username, String password) {
        var userzQueryResult = userzRepository.findByUsernameIgnoreCase(username);

        if (userzQueryResult.isEmpty() || !HashUtil.isBcryptMatch(password, userzQueryResult.get().getHashedPassword())) {
            // throw new IllegalArgumentException("Invalid credential");
            throw new ProblemzAuthenticationException();
        }

        var randomAuthToken = RandomStringUtils.randomAlphanumeric(40);

        return refreshToken(userzQueryResult.get().getId(), randomAuthToken);
    }

    private UserzToken refreshToken(UUID userId, String authToken) {
        var  userzToken = new UserzToken();
        userzToken.setUserId(userId);
        userzToken.setAuthToken(authToken);

        var now = LocalDateTime.now();
        userzToken.setCreationTimestamp(now);
        userzToken.setExpiryTimestamp(now.plusHours(2));

        return userzTokenRepository.save(userzToken);
    }
}
