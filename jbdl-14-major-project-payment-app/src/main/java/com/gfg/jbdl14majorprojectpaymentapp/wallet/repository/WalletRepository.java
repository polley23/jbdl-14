package com.gfg.jbdl14majorprojectpaymentapp.wallet.repository;

import com.gfg.jbdl14majorprojectpaymentapp.user.entity.User;
import com.gfg.jbdl14majorprojectpaymentapp.wallet.entity.Wallet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends CrudRepository<Wallet,Long> {
    Optional<Wallet> findByUsername(String username);
}
