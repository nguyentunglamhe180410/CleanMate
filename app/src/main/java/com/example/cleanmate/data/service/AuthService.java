//package com.example.cleanmate.data.service;
//
//
//import com.example.cleanmate.common.utils.DateTimeVN;
//import com.example.cleanmate.data.repository.CustomerRepository;
//import com.example.cleanmate.models.viewmodels.authen.RegisterModel;
//import com.example.cleanmate.data.model.Aspnetusers;
//
//import java.sql.SQLException;
//import java.util.List;
//
//public class AuthService {
//    private final CustomerRepository userRepo;
//    private final EmailService   emailSvc;
//
//    public AuthService(CustomerRepository userRepo,
//                       EmailService emailSvc,
//                       JwtProvider jwtProvider) {
//        this.userRepo    = userRepo;
//        this.emailSvc    = emailSvc;
//        this.jwtProvider = jwtProvider;
//    }
//
//    /**
//     * Registers a new customer: checks duplicates, creates user, sends confirmation.
//     */
//    public boolean registerCustomer(RegisterModel m) throws SQLException {
//        List<String> errs = userRepo.checkEmailPhone(m.getEmail(), m.getPhonenumber());
//        if (!errs.isEmpty()) return AuthResult.fail(errs);
//
//        Aspnetusers u = new Aspnetusers();
//        u.setEmail(m.getEmail());
//        u.setPhonenumber(m.getPhoneNumber());
//        u.setFullname(m.getFullName());
//        u.setPasswordhash(userRepo.hashPassword(m.getPassword()));
//        u.setCreateddate(DateTimeVN.getNow());
//        u.setRole("Customer");
//
//        userRepo.insert(u);
//        // generate token + send confirmation
//        String token = userRepo.generateEmailToken(u.getUserid());
//        String link  = "https://yourdomain.com/confirm?uid=" + u.getUserid() + "&t=" + token;
//        emailSvc.sendConfirm(u.getEmail(), link);
//
//        return true;
//    }
//
//    /**
//     * Logs in and returns JWT on success, or error message.
//     */
//    public AuthResult login(LoginModel m) throws SQLException {
//        User u = userRepo.findByEmail(m.getEmail());
//        if (u == null)  return AuthResult.fail("Tài khoản không tồn tại.");
//        if (!u.isEmailConfirmed())
//            return AuthResult.fail("Email chưa được xác thực.");
//        if (!userRepo.verifyPassword(u, m.getPassword()))
//            return AuthResult.fail("Sai mật khẩu.");
//
//        String jwt = jwtProvider.createToken(u.getUserid(), u.getRole());
//        return AuthResult.success(jwt);
//    }
//
//    // ... forgotPassword, resetPassword, resendConfirmation similar patterns ...
//}
//
