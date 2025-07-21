package com.example.cleanmate.data.service;

import com.example.cleanmate.common.CommonConstants;
import com.example.cleanmate.common.utils.DateTimeVN;
import com.example.cleanmate.data.model.*;
import com.example.cleanmate.data.model.dto.dto;
import com.example.cleanmate.data.model.viewmodels.customer.CustomerReviewSummaryViewModel;
import com.example.cleanmate.data.model.viewmodels.employee.EarningsSummaryViewModel;
import com.example.cleanmate.data.model.viewmodels.employee.FeedbackHistoryViewModel;
import com.example.cleanmate.data.model.viewmodels.employee.MonthlyEarningViewModel;
import com.example.cleanmate.data.model.viewmodels.employee.PersonalProfileViewModel;
import com.example.cleanmate.data.model.viewmodels.employee.WorkDetailsViewModel;
import com.example.cleanmate.data.model.viewmodels.employee.WorkHistoryViewModel;
import com.example.cleanmate.data.model.viewmodels.employee.WorkListViewModel;
import com.example.cleanmate.data.model.viewmodels.employee.WorkSummaryViewModel;
import com.example.cleanmate.data.repository.BookingRepository;
import com.example.cleanmate.data.repository.CleanPerHourRepository;
import com.example.cleanmate.data.repository.EmployeeRepository;
import com.example.cleanmate.data.service.WalletService;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class EmployeeService {
    private final EmployeeRepository employeeRepo;
    private final WalletService      walletService;
    private final BookingRepository  bookingRepo;

    public EmployeeService(EmployeeRepository employeeRepo,
                           WalletService walletService,
                           BookingRepository bookingRepo) {
        this.employeeRepo = employeeRepo;
        this.walletService = walletService;
        this.bookingRepo  = bookingRepo;
    }

    public List<WorkListViewModel> getAllWork(Integer statusId, String employeeId) throws SQLException {
        // cancel past due work
        employeeRepo.checkAndCancelPastDueWork();
        return employeeRepo.findAllWork(statusId, employeeId);
    }

    public WorkDetailsViewModel getWorkDetails(int bookingId) throws SQLException {
        return employeeRepo.findWorkById(bookingId);
    }

    public List<WorkListViewModel> getWorkByEmployeeId(String employeeId) throws SQLException {
        return employeeRepo.findWorkByEmployeeId(employeeId);
    }

    public boolean acceptWorkRequest(int bookingId, String employeeId) throws SQLException {
        UserWallet wallet = walletService.getWalletByUserId(employeeId);
        Booking b = bookingRepo.getBookingById(bookingId);
        if (b == null) throw new IllegalArgumentException("Booking not found");

        LocalDateTime startTime   = b.getDate().atTime((b.getStartTime());
        LocalDateTime currentTime = DateTimeVN.getNow().toLocalDateTime();

        if (startTime.isBefore(currentTime))
            throw new IllegalStateException("Cannot accept past work");

        if (wallet.getBalance().compareTo(BigDecimal.valueOf(CommonConstants.MINIMUM_COINS_TO_ACCEPT_WORK)) < 0)
            throw new IllegalStateException("Insufficient balance");

        if (!employeeRepo.canCleanerAcceptWork(bookingId, employeeId))
            throw new IllegalStateException("Time conflict with other work");

        return employeeRepo.changeWorkStatus(bookingId,
                CommonConstants.BookingStatus.ACCEPT,
                employeeId);
    }

    public boolean beginWorkRequest(int bookingId, String employeeId) throws SQLException {
        Booking b = bookingRepo.getBookingById(bookingId);
        if (b == null) throw new IllegalArgumentException("Booking not found");
        if (!employeeId.equals(b.getCleanerId()))
            throw new IllegalStateException("Not your booking");
        if (b.getBookingStatusId() != CommonConstants.BookingStatus.ACCEPT)
            throw new IllegalStateException("Booking not in ACCEPT state");

        LocalDateTime startTime   = b.getDate().atTime(b.getStartTime());
        LocalDateTime currentTime = DateTimeVN.getNow().toLocalDateTime();
        Duration diff = Duration.between(startTime, currentTime).abs();
        if (diff.toMinutes() > 10)
            throw new IllegalStateException("Can only start within ±10 minutes");

        return employeeRepo.changeWorkStatus(bookingId,
                CommonConstants.BookingStatus.IN_PROGRESS,
                employeeId);
    }

    public boolean completeWorkRequest(int bookingId, String employeeId) throws SQLException {
        WorkDetailsViewModel w = employeeRepo.findWorkById(bookingId);
        if (w == null) throw new IllegalArgumentException("Booking not found");
        if (!employeeId.equals(w.getEmployeeId()))
            throw new IllegalStateException("Not your booking");
        if (w.getStatusId() != CommonConstants.BookingStatus.IN_PROGRESS)
            throw new IllegalStateException("Booking not in IN_PROGRESS state");

        return employeeRepo.changeWorkStatus(bookingId,
                CommonConstants.BookingStatus.PENDING_DONE,
                employeeId);
    }

    public boolean cancelWorkRequest(int bookingId, String employeeId) throws SQLException {
        Booking b = bookingRepo.getBookingById(bookingId);
        if (b == null) throw new IllegalArgumentException("Booking not found");
        if (!employeeId.equals(b.getCleanerId()))
            throw new IllegalStateException("Not your booking");
        if (b.getBookingStatusId() != CommonConstants.BookingStatus.ACCEPT)
            throw new IllegalStateException("Booking not in ACCEPT state");

        LocalDateTime startTime   = b.getDate().atTime(b.getStartTime());
        LocalDateTime currentTime = DateTimeVN.getNow().toLocalDateTime();
        Duration diff = Duration.between(currentTime, startTime);

        // determine refund
        BigDecimal total = b.getTotalPrice();
        BigDecimal refund;
        int newStatus = CommonConstants.BookingStatus.CANCEL;
        if (diff.toHours() >= 8) {
            refund   = total.multiply(BigDecimal.valueOf(1 - CommonConstants.COMMISSION_PERCENTAGE));
            newStatus = CommonConstants.BookingStatus.NEW;
            employeeId = null;
        } else if (diff.toHours() >= 5) {
            refund   = BigDecimal.ZERO;
            newStatus = CommonConstants.BookingStatus.NEW;
            employeeId = null;
        } else if (diff.toHours() >= 1) {
            refund   = total.multiply(BigDecimal.valueOf(0.5 - (1 - CommonConstants.COMMISSION_PERCENTAGE)));
            newStatus = CommonConstants.BookingStatus.NEW;
            employeeId = null;
        } else {
            refund   = total.multiply(BigDecimal.valueOf(-CommonConstants.COMMISSION_PERCENTAGE));
        }

        // apply refund
        if (refund.signum() != 0) {
            walletService.adjustBalance(b.getCleanerId(), refund,
                    "Refund for cancellation", bookingId);
        }

        return employeeRepo.changeWorkStatus(bookingId, newStatus, employeeId);
    }

    public boolean confirmDoneWorkRequest(int bookingId) throws SQLException {
        WorkDetailsViewModel w = employeeRepo.findWorkById(bookingId);
        if (w.getStatusId() != CommonConstants.BookingStatus.PENDING_DONE)
            throw new IllegalStateException("Booking not in PENDING_DONE");
        return employeeRepo.changeWorkStatus(bookingId,
                CommonConstants.BookingStatus.DONE,
                w.getEmployeeId());
    }

    public WorkSummaryViewModel getWorkSummary() throws SQLException {
        int newCount     = employeeRepo.countByStatus(CommonConstants.BookingStatus.NEW);
        int pendingCount = employeeRepo.countByStatus(CommonConstants.BookingStatus.PENDING_DONE);
        int acceptCount  = employeeRepo.countByStatus(CommonConstants.BookingStatus.ACCEPT);
        return new WorkSummaryViewModel(newCount, pendingCount, acceptCount);
    }
    public int countByStatus(int status) throws SQLException {
        return employeeRepo.countByStatus(status);
    }

    public boolean validateWorkAcceptance(int bookingId, String employeeId) throws SQLException {
        WorkDetailsViewModel w = repo.findWorkById(bookingId);
        if (w == null) return false;
        // only NEW status allowed
        return w.getStatus().equals(CommonConstants.GetStatusString(CommonConstants.BookingStatus.NEW));
    }

    public boolean canCleanerAcceptWork(int bookingId, String employeeId) throws SQLException {
        if (!validateWorkAcceptance(bookingId, employeeId)) return false;
        return employeeRepo.canCleanerAcceptWork(bookingId, employeeId);
    }

    public int checkAndCancelPastDueWork() throws SQLException {
        return employeeRepo.checkAndCancelPastDueWork();
    }

    public void createCleanerProfile(String userId) throws SQLException {
        employeeRepo.createCleanerProfile(userId);
    }

    public List<WorkHistoryViewModel> getWorkHistory(String employeeId) throws SQLException {
        return employeeRepo.getWorkHistory(employeeId);
    }

    public EarningsSummaryViewModel getEarningsSummary(String employeeId) throws SQLException {
        return employeeRepo.getEarningsSummary(employeeId);
    }

    public PersonalProfileViewModel getPersonalProfile(String employeeId) throws SQLException {
        return employeeRepo.getPersonalProfile(employeeId);
    }

    public boolean updatePersonalProfile(PersonalProfileViewModel profile) throws SQLException {
        if (profile.getUserId() == null || profile.getUserId().isEmpty()) {
            throw new IllegalArgumentException("UserId cannot be empty");
        }
        return repo.updatePersonalProfile(profile);
    }

    public CustomerReviewSummaryViewModel getCustomerReviews(String employeeId) throws SQLException {
        return repo.getCustomerReviews(employeeId);
    }

    public BigDecimal getMonthlyEarnings(String employeeId) throws SQLException {
        return employeeRepo.getMonthlyEarnings(employeeId);
    }

    public List<MonthlyEarningViewModel> getEarningsByMonth(String employeeId) throws SQLException {
        return employeeRepo.getEarningsByMonth(employeeId);
    }

    public List<dto.CleanerDTO> getAvailableCleaners() throws SQLException {
        return employeeRepo.getAvailableCleaners();
    }

    public List<FeedbackHistoryViewModel> getFeedbackHistory(String employeeId) throws SQLException {
        return employeeRepo.getFeedbackHistory(employeeId);
    }

    public boolean recalculateCleanerRating(String employeeId, int newRating) throws SQLException {
        return employeeRepo.recalculateCleanerRating(employeeId, newRating);
    }

    public List<CleanerListItemViewModel> getCleanerList() throws SQLException {
        return employeeRepo.getCleanerList();
    }

    public CleanerDetailViewModel getCleanerDetail(String cleanerId) throws SQLException {
        return employeeRepo.getCleanerDetail(cleanerId);
    }

    public boolean toggleCleanerAvailability(String cleanerId, boolean isAvailable) throws SQLException {
        return employeeRepo.toggleCleanerAvailability(cleanerId, isAvailable);
    }

}

