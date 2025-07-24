package com.example.MovieInABox.data.service;

import com.example.MovieInABox.data.model.viewmodels.authen.AuthResult;
import com.example.MovieInABox.data.model.viewmodels.authen.LoginModel;
import com.example.MovieInABox.data.model.viewmodels.authen.RegisterModel;
import com.example.MovieInABox.data.model.User;
import com.example.MovieInABox.data.repository.AuthRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthService {
    public AuthResult registerCustomer(RegisterModel model) {
        List<String> errors = new ArrayList<>();
        try (AuthRepository repo = new AuthRepository()) {
            if (repo.existsByEmail(model.getEmail())) {
                errors.add("Email đã được sử dụng.");
            }
            if (repo.existsByPhone(model.getPhoneNumber())) {
                errors.add("Số điện thoại đã được sử dụng.");
            }
            boolean ok = repo.register(model);
            if (!errors.isEmpty()) {
                return AuthResult.fail(errors);
            }
            if (ok) {
                User u = repo.login( new LoginModel( model.getEmail(),model.getPassword()));
                return AuthResult.success(u);
            } else {
                return AuthResult.fail("Đăng ký thất bại");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return AuthResult.fail("Lỗi: " + ex.getMessage());
        }
    }

    public AuthResult login(LoginModel model) {
        try (AuthRepository repo = new AuthRepository()) {
            User u = repo.login(model);
            if (u == null) {
                return AuthResult.fail("Email hoặc mật khẩu không đúng.");
            }
            return AuthResult.success(u);
        } catch (Exception ex) {
            ex.printStackTrace();
            return AuthResult.fail("Lỗi: " + ex.getMessage());
        }
    }

    public AuthResult registerEmployee(RegisterModel m) {
        List<String> errors = new ArrayList<>();
        try (AuthRepository repo = new AuthRepository()) {
            if (repo.existsByEmail(m.getEmail())) {
                errors.add("Email đã được sử dụng.");
            }
            if (repo.existsByPhone(m.getPhoneNumber())) {
                errors.add("Số điện thoại đã được sử dụng.");
            }
            if (repo.existsByIdentification(m.getIdentification())) {
                errors.add("Số định danh đã được sử dụng.");
            }
            if (m.getBank() == null || m.getBank().isEmpty()) {
                errors.add("Mã ngân hàng không hợp lệ.");
            }

            if (!errors.isEmpty()) {
                return AuthResult.fail(errors);
            }

            // perform the registration + role assignment
            boolean ok = repo.registerEmployee(m);
            if (!ok) {
                return AuthResult.fail("Đăng ký nhân viên thất bại. Vui lòng thử lại.");
            }

            // load back the created user
            User u = repo.login( new LoginModel( m.getEmail(),m.getPassword()));
            return AuthResult.success(u);
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return AuthResult.fail("Lỗi hệ thống: " + ex.getMessage());
        }
    }
}
