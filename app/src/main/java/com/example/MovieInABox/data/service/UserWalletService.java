package com.example.MovieInABox.data.service;


import com.example.MovieInABox.common.utils.DateTimeVN;
import com.example.MovieInABox.data.model.User;
import com.example.MovieInABox.data.model.UserWallet;
import com.example.MovieInABox.data.model.dto.dto;
import com.example.MovieInABox.data.repository.CustomerRepository;
import com.example.MovieInABox.data.repository.UserWalletRepository;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class UserWalletService {

    private final UserWalletRepository walletRepo;
    private final CustomerRepository userRepo;

    public UserWalletService(UserWalletRepository walletRepo, CustomerRepository userRepo) {
        this.walletRepo         = walletRepo;
        this.userRepo           = userRepo;
    }

    /**
     * Create a new wallet with zero balance.
     */
    public UserWallet addNewWallet(String userId) throws SQLException {
        UserWallet w = new UserWallet();
        w.setUserId(userId);
        w.setBalance(BigDecimal.ZERO);
        // convert ZonedDateTime → LocalDateTime
        w.setUpdatedAt(DateTimeVN.getNow().toString());
        return walletRepo.addNewWallet(w);
    }

    /**
     * Return the current balance for a user.
     */
    public BigDecimal getWalletBalance(String userId) throws SQLException {
        UserWallet w = walletRepo.getWalletByUserId(userId);
        if (w == null) throw new IllegalStateException("Wallet not found");
        return w.getBalance();
    }

    /**
     * Get or create the wallet DTO for UI.
     */
    public dto.UserWalletDTO getWallet(String userId) throws SQLException {
        UserWallet w = walletRepo.getWalletByUserId(userId);
        if (w == null) {
            w = addNewWallet(userId);
        }
        User u = userRepo.getUserById(w.getUserId());
        return new dto.UserWalletDTO(
                w.getWalletId(),
                w.getUserId(),
                u.getFullName(),
                w.getBalance(),
                LocalDateTime.parse(w.getUpdatedAt())
        );
    }

}
