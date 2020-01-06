package com.depromeet.boiledegg.account.application;

import com.depromeet.boiledegg.account.domain.entity.Account;
import com.depromeet.boiledegg.account.domain.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class AccountService {

    private final AccountRepository repository;

    public Optional<Account> findById(final long id) {
        return repository.findById(id);
    }

    public Account save(final Account account) {
        return repository.save(account);
    }

    public Optional<Account> findByEmail(final String email) {
        return repository.findByEmail(email);
    }
}
