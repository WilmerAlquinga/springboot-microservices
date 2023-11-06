package dev.wsalquinga.accounts_service.config;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * @author wsalquinga on 27/10/2023
 */
public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("Guest User");
    }
}
