package com.example.cleanmate.data.model.dto;

import com.example.cleanmate.common.enums.PaymentType;
import com.example.cleanmate.common.enums.TransactionType;
import com.example.cleanmate.common.enums.VoucherStatus;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class dto {

// AddFeedbackDTO.java

    public class AddFeedbackDTO {
        private int bookingId;
        private String cleanerId;
        private Double rating;
        private String content;

        public AddFeedbackDTO() {}

        public AddFeedbackDTO(int bookingId, String cleanerId, Double rating, String content) {
            this.bookingId = bookingId;
            this.cleanerId = cleanerId;
            this.rating = rating;
            this.content = content;
        }

        public int getBookingId() { return bookingId; }
        public void setBookingId(int bookingId) { this.bookingId = bookingId; }

        public String getCleanerId() { return cleanerId; }
        public void setCleanerId(String cleanerId) { this.cleanerId = cleanerId; }

        public Double getRating() { return rating; }
        public void setRating(Double rating) { this.rating = rating; }

        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
    }

// ApplyVoucherDTO.java

    public class ApplyVoucherDTO {
        private String voucherCode;
        private BigDecimal totalAmount;

        public ApplyVoucherDTO() {}

        public ApplyVoucherDTO(String voucherCode, BigDecimal totalAmount) {
            this.voucherCode = voucherCode;
            this.totalAmount = totalAmount;
        }

        public String getVoucherCode() { return voucherCode; }
        public void setVoucherCode(String voucherCode) { this.voucherCode = voucherCode; }

        public BigDecimal getTotalAmount() { return totalAmount; }
        public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    }

// ApplyVoucherResult.java


    public class ApplyVoucherResult {
        private boolean success;
        private String message;
        private BigDecimal discountAmount;
        private BigDecimal newTotal;

        public ApplyVoucherResult() {}

        public ApplyVoucherResult(boolean success, String message, BigDecimal discountAmount, BigDecimal newTotal) {
            this.success = success;
            this.message = message;
            this.discountAmount = discountAmount;
            this.newTotal = newTotal;
        }

        public boolean isSuccess() { return success; }
        public void setSuccess(boolean success) { this.success = success; }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }

        public BigDecimal getDiscountAmount() { return discountAmount; }
        public void setDiscountAmount(BigDecimal discountAmount) { this.discountAmount = discountAmount; }

        public BigDecimal getNewTotal() { return newTotal; }
        public void setNewTotal(BigDecimal newTotal) { this.newTotal = newTotal; }
    }

// AssignCleanerDTO.java


    public class AssignCleanerDTO {
        private int bookingId;
        private String cleanerId;

        public AssignCleanerDTO() {}

        public AssignCleanerDTO(int bookingId, String cleanerId) {
            this.bookingId = bookingId;
            this.cleanerId = cleanerId;
        }

        public int getBookingId() { return bookingId; }
        public void setBookingId(int bookingId) { this.bookingId = bookingId; }

        public String getCleanerId() { return cleanerId; }
        public void setCleanerId(String cleanerId) { this.cleanerId = cleanerId; }
    }

// AssignVoucherDTO.java


    public class AssignVoucherDTO {
        private String userId;
        private int voucherId;
        private int quantity;

        public AssignVoucherDTO() { this.quantity = 1; }

        public AssignVoucherDTO(String userId, int voucherId, int quantity) {
            this.userId = userId;
            this.voucherId = voucherId;
            this.quantity = quantity;
        }

        public String getUserId() { return userId; }
        public void setUserId(String userId) { this.userId = userId; }

        public int getVoucherId() { return voucherId; }
        public void setVoucherId(int voucherId) { this.voucherId = voucherId; }

        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
    }

// AssignVoucherMultipleDTO.java


    public class AssignVoucherMultipleDTO {
        private List<String> userIds;
        private int voucherId;
        private int quantity;

        public AssignVoucherMultipleDTO() { this.quantity = 1; }

        public AssignVoucherMultipleDTO(List<String> userIds, int voucherId, int quantity) {
            this.userIds = userIds;
            this.voucherId = voucherId;
            this.quantity = quantity;
        }

        public List<String> getUserIds() { return userIds; }
        public void setUserIds(List<String> userIds) { this.userIds = userIds; }

        public int getVoucherId() { return voucherId; }
        public void setVoucherId(int voucherId) { this.voucherId = voucherId; }

        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
    }

// BookingAdminDTO.java



    public class BookingAdminDTO {
        private int bookingId;
        private String serviceName;
        private String customerFullName;
        private LocalDate date;
        private LocalTime startTime;
        private int duration;
        private String address;
        private String note;
        private BigDecimal totalPrice;
        private String status;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private String cleanerId;
        private String cleanerName;

        public BookingAdminDTO() {}

        public BookingAdminDTO(int bookingId, String serviceName, String customerFullName, LocalDate date,
                               LocalTime startTime, int duration, String address, String note,
                               BigDecimal totalPrice, String status, LocalDateTime createdAt,
                               LocalDateTime updatedAt, String cleanerId, String cleanerName) {
            this.bookingId = bookingId;
            this.serviceName = serviceName;
            this.customerFullName = customerFullName;
            this.date = date;
            this.startTime = startTime;
            this.duration = duration;
            this.address = address;
            this.note = note;
            this.totalPrice = totalPrice;
            this.status = status;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.cleanerId = cleanerId;
            this.cleanerName = cleanerName;
        }

        public int getBookingId() { return bookingId; }
        public void setBookingId(int bookingId) { this.bookingId = bookingId; }

        public String getServiceName() { return serviceName; }
        public void setServiceName(String serviceName) { this.serviceName = serviceName; }

        public String getCustomerFullName() { return customerFullName; }
        public void setCustomerFullName(String customerFullName) { this.customerFullName = customerFullName; }

        public LocalDate getDate() { return date; }
        public void setDate(LocalDate date) { this.date = date; }

        public LocalTime getStartTime() { return startTime; }
        public void setStartTime(LocalTime startTime) { this.startTime = startTime; }

        public int getDuration() { return duration; }
        public void setDuration(int duration) { this.duration = duration; }

        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }

        public String getNote() { return note; }
        public void setNote(String note) { this.note = note; }

        public BigDecimal getTotalPrice() { return totalPrice; }
        public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public LocalDateTime getCreatedAt() { return createdAt; }
        public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

        public LocalDateTime getUpdatedAt() { return updatedAt; }
        public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

        public String getCleanerId() { return cleanerId; }
        public void setCleanerId(String cleanerId) { this.cleanerId = cleanerId; }

        public String getCleanerName() { return cleanerName; }
        public void setCleanerName(String cleanerName) { this.cleanerName = cleanerName; }
    }



    // 1. BookingCreateDTO
    public class BookingCreateDTO {
        private int servicePriceId;
        private String cleanerId;
        private String userId;
        private Integer bookingStatusId;
        private String note;
        private int addressId;
        private LocalDate date;
        private LocalTime startTime;
        private BigDecimal totalPrice;

        public BookingCreateDTO() {}

        public BookingCreateDTO(int servicePriceId, String cleanerId, String userId,
                                Integer bookingStatusId, String note, int addressId,
                                LocalDate date, LocalTime startTime, BigDecimal totalPrice) {
            this.servicePriceId = servicePriceId;
            this.cleanerId = cleanerId;
            this.userId = userId;
            this.bookingStatusId = bookingStatusId;
            this.note = note;
            this.addressId = addressId;
            this.date = date;
            this.startTime = startTime;
            this.totalPrice = totalPrice;
        }

        public int getServicePriceId() { return servicePriceId; }
        public void setServicePriceId(int servicePriceId) { this.servicePriceId = servicePriceId; }
        public String getCleanerId() { return cleanerId; }
        public void setCleanerId(String cleanerId) { this.cleanerId = cleanerId; }
        public String getUserId() { return userId; }
        public void setUserId(String userId) { this.userId = userId; }
        public Integer getBookingStatusId() { return bookingStatusId; }
        public void setBookingStatusId(Integer bookingStatusId) { this.bookingStatusId = bookingStatusId; }
        public String getNote() { return note; }
        public void setNote(String note) { this.note = note; }
        public int getAddressId() { return addressId; }
        public void setAddressId(int addressId) { this.addressId = addressId; }
        public LocalDate getDate() { return date; }
        public void setDate(LocalDate date) { this.date = date; }
        public LocalTime getStartTime() { return startTime; }
        public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
        public BigDecimal getTotalPrice() { return totalPrice; }
        public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }
    }

    // 2. BookingDTO
    public static class BookingDTO {
        private int bookingId;
        private int servicePriceId;
        private String serviceName;
        private int durationTime;
        private String durationSquareMeter;
        private BigDecimal price;
        private String cleanerId;
        private String cleanerName;
        private String userId;
        private String userName;
        private int bookingStatusId;
        private String status;
        private String statusDescription;
        private String note;
        private Integer addressId;
        private String addressFormatted;
        private String addressNo;
        private String paymentMethod;
        private String paymentStatus;
        private LocalDate date;
        private LocalTime startTime;
        private BigDecimal totalPrice;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private boolean hasFeedback;

        public BookingDTO() {}

        public BookingDTO(int bookingId, int servicePriceId, String serviceName,
                          int durationTime, String durationSquareMeter, BigDecimal price,
                          String cleanerId, String cleanerName, String userId, String userName,
                          int bookingStatusId, String status, String statusDescription, String note,
                          Integer addressId, String addressFormatted, String addressNo,
                          String paymentMethod, String paymentStatus,
                          LocalDate date, LocalTime startTime, BigDecimal totalPrice,
                          LocalDateTime createdAt, LocalDateTime updatedAt, boolean hasFeedback) {
            this.bookingId = bookingId;
            this.servicePriceId = servicePriceId;
            this.serviceName = serviceName;
            this.durationTime = durationTime;
            this.durationSquareMeter = durationSquareMeter;
            this.price = price;
            this.cleanerId = cleanerId;
            this.cleanerName = cleanerName;
            this.userId = userId;
            this.userName = userName;
            this.bookingStatusId = bookingStatusId;
            this.status = status;
            this.statusDescription = statusDescription;
            this.note = note;
            this.addressId = addressId;
            this.addressFormatted = addressFormatted;
            this.addressNo = addressNo;
            this.paymentMethod = paymentMethod;
            this.paymentStatus = paymentStatus;
            this.date = date;
            this.startTime = startTime;
            this.totalPrice = totalPrice;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.hasFeedback = hasFeedback;
        }

        public int getBookingId() {
            return bookingId;
        }

        public void setBookingId(int bookingId) {
            this.bookingId = bookingId;
        }

        public int getServicePriceId() {
            return servicePriceId;
        }

        public void setServicePriceId(int servicePriceId) {
            this.servicePriceId = servicePriceId;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public int getDurationTime() {
            return durationTime;
        }

        public void setDurationTime(int durationTime) {
            this.durationTime = durationTime;
        }

        public String getDurationSquareMeter() {
            return durationSquareMeter;
        }

        public void setDurationSquareMeter(String durationSquareMeter) {
            this.durationSquareMeter = durationSquareMeter;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public String getCleanerId() {
            return cleanerId;
        }

        public void setCleanerId(String cleanerId) {
            this.cleanerId = cleanerId;
        }

        public String getCleanerName() {
            return cleanerName;
        }

        public void setCleanerName(String cleanerName) {
            this.cleanerName = cleanerName;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getBookingStatusId() {
            return bookingStatusId;
        }

        public void setBookingStatusId(int bookingStatusId) {
            this.bookingStatusId = bookingStatusId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStatusDescription() {
            return statusDescription;
        }

        public void setStatusDescription(String statusDescription) {
            this.statusDescription = statusDescription;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public Integer getAddressId() {
            return addressId;
        }

        public void setAddressId(Integer addressId) {
            this.addressId = addressId;
        }

        public String getAddressFormatted() {
            return addressFormatted;
        }

        public void setAddressFormatted(String addressFormatted) {
            this.addressFormatted = addressFormatted;
        }

        public String getAddressNo() {
            return addressNo;
        }

        public void setAddressNo(String addressNo) {
            this.addressNo = addressNo;
        }

        public String getPaymentMethod() {
            return paymentMethod;
        }

        public void setPaymentMethod(String paymentMethod) {
            this.paymentMethod = paymentMethod;
        }

        public String getPaymentStatus() {
            return paymentStatus;
        }

        public void setPaymentStatus(String paymentStatus) {
            this.paymentStatus = paymentStatus;
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public LocalTime getStartTime() {
            return startTime;
        }

        public void setStartTime(LocalTime startTime) {
            this.startTime = startTime;
        }

        public BigDecimal getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
        }

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Timestamp createdAt) {
            this.createdAt = createdAt;
        }

        public LocalDateTime getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Timestamp updatedAt) {
            this.updatedAt = updatedAt;
        }

        public boolean isHasFeedback() {
            return hasFeedback;
        }

        public void setHasFeedback(boolean hasFeedback) {
            this.hasFeedback = hasFeedback;
        }

        public void setBookingid(int bookingId) {
        }
    }

    // 3. CancelBookingDTO
    public class CancelBookingDTO {
        private int bookingId;

        public CancelBookingDTO() {}
        public CancelBookingDTO(int bookingId) { this.bookingId = bookingId; }

        public int getBookingId() { return bookingId; }
        public void setBookingId(int bookingId) { this.bookingId = bookingId; }
    }

    // 4. CleanerDetailDTO
    public class CleanerDetailDTO {
        private int cleanerId;
        private String fullName;
        private String email;
        private String phoneNumber;
        private String area;
        private Boolean available;
        private Integer experienceYear;
        private BigDecimal walletBalance;
        private List<WalletTransactionDTO> transactions;
        private List<BookingDTO> bookings;

        public CleanerDetailDTO() {}

        public CleanerDetailDTO(int cleanerId, String fullName, String email,
                                String phoneNumber, String area, Boolean available,
                                Integer experienceYear, BigDecimal walletBalance,
                                List<WalletTransactionDTO> transactions,
                                List<BookingDTO> bookings) {
            this.cleanerId = cleanerId;
            this.fullName = fullName;
            this.email = email;
            this.phoneNumber = phoneNumber;
            this.area = area;
            this.available = available;
            this.experienceYear = experienceYear;
            this.walletBalance = walletBalance;
            this.transactions = transactions;
            this.bookings = bookings;
        }

        public int getCleanerId() {
            return cleanerId;
        }

        public void setCleanerId(int cleanerId) {
            this.cleanerId = cleanerId;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public Boolean getAvailable() {
            return available;
        }

        public void setAvailable(Boolean available) {
            this.available = available;
        }

        public Integer getExperienceYear() {
            return experienceYear;
        }

        public void setExperienceYear(Integer experienceYear) {
            this.experienceYear = experienceYear;
        }

        public BigDecimal getWalletBalance() {
            return walletBalance;
        }

        public void setWalletBalance(BigDecimal walletBalance) {
            this.walletBalance = walletBalance;
        }

        public List<WalletTransactionDTO> getTransactions() {
            return transactions;
        }

        public void setTransactions(List<WalletTransactionDTO> transactions) {
            this.transactions = transactions;
        }

        public List<BookingDTO> getBooking () {
            return bookings;
        }

        public void setBooking (List<BookingDTO> bookings) {
            this.bookings = bookings;
        }
    }

    // 5. CleanerDTO
    public static class CleanerDTO {
        private String cleanerId;
        private String name;

        public CleanerDTO() {}
        public CleanerDTO(String cleanerId, String name) {
            this.cleanerId = cleanerId;
            this.name = name;
        }

        public String getCleanerId() { return cleanerId; }
        public void setCleanerId(String cleanerId) { this.cleanerId = cleanerId; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }

    // 6. CleanerListItemDTO
    public class CleanerListItemDTO {
        private String cleanerId;
        private String fullName;
        private String email;
        private String phoneNumber;
        private String area;
        private Boolean available;
        private Integer experienceYear;

        public CleanerListItemDTO() {}
        public CleanerListItemDTO(String cleanerId, String fullName, String email,
                                  String phoneNumber, String area,
                                  Boolean available, Integer experienceYear) {
            this.cleanerId = cleanerId;
            this.fullName = fullName;
            this.email = email;
            this.phoneNumber = phoneNumber;
            this.area = area;
            this.available = available;
            this.experienceYear = experienceYear;
        }

        public String getCleanerId() {
            return cleanerId;
        }

        public void setCleanerId(String cleanerId) {
            this.cleanerId = cleanerId;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public Boolean getAvailable() {
            return available;
        }

        public void setAvailable(Boolean available) {
            this.available = available;
        }

        public Integer getExperienceYear() {
            return experienceYear;
        }

        public void setExperienceYear(Integer experienceYear) {
            this.experienceYear = experienceYear;
        }
    }

    // 7. CustomerAddressDTO
    public static class CustomerAddressDTO {
        private int addressId;
        private String userId;
        private String ggFormattedAddress;
        private String ggDispalyName;
        private String ggPlaceId;
        private String addressNo;
        private boolean isInUse;
        private boolean isDefault;
        private BigDecimal latitude;
        private BigDecimal longitude;

        public CustomerAddressDTO() {}

        public CustomerAddressDTO(int addressId, String userId, String ggFormattedAddress,
                                  String ggDispalyName, String ggPlaceId, String addressNo,
                                  boolean isInUse, boolean isDefault,
                                  BigDecimal latitude, BigDecimal longitude) {
            this.addressId = addressId;
            this.userId = userId;
            this.ggFormattedAddress = ggFormattedAddress;
            this.ggDispalyName = ggDispalyName;
            this.ggPlaceId = ggPlaceId;
            this.addressNo = addressNo;
            this.isInUse = isInUse;
            this.isDefault = isDefault;
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public int getAddressId() {
            return addressId;
        }

        public void setAddressId(int addressId) {
            this.addressId = addressId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getGgFormattedAddress() {
            return ggFormattedAddress;
        }

        public void setGgFormattedAddress(String ggFormattedAddress) {
            this.ggFormattedAddress = ggFormattedAddress;
        }

        public String getGgDispalyName() {
            return ggDispalyName;
        }

        public void setGgDispalyName(String ggDispalyName) {
            this.ggDispalyName = ggDispalyName;
        }

        public String getGgPlaceId() {
            return ggPlaceId;
        }

        public void setGgPlaceId(String ggPlaceId) {
            this.ggPlaceId = ggPlaceId;
        }

        public String getAddressNo() {
            return addressNo;
        }

        public void setAddressNo(String addressNo) {
            this.addressNo = addressNo;
        }

        public boolean isInUse() {
            return isInUse;
        }

        public void setInUse(boolean inUse) {
            isInUse = inUse;
        }

        public boolean isDefault() {
            return isDefault;
        }

        public void setDefault(boolean aDefault) {
            isDefault = aDefault;
        }

        public BigDecimal getLatitude() {
            return latitude;
        }

        public void setLatitude(BigDecimal latitude) {
            this.latitude = latitude;
        }

        public BigDecimal getLongitude() {
            return longitude;
        }

        public void setLongitude(BigDecimal longitude) {
            this.longitude = longitude;
        }
    }

    // 8. CustomerDetailDTO
    public static class CustomerDetailDTO {
        private Integer id;
        private String fullName;
        private String email;
        private String phoneNumber;
        private LocalDateTime createdDate;
        private boolean isActive;
        private List<CustomerAddressDTO> addresses;
        private BigDecimal walletBalance;
        private List<WalletTransactionDTO> transactions;
        private List<BookingDTO> bookings;

        public CustomerDetailDTO() {}

        public CustomerDetailDTO(Integer id, String fullName, String email,
                                 String phoneNumber, LocalDateTime createdDate,
                                 boolean isActive, List<CustomerAddressDTO> addresses,
                                 BigDecimal walletBalance,
                                 List<WalletTransactionDTO> transactions,
                                 List<BookingDTO> bookings) {
            this.id = id;
            this.fullName = fullName;
            this.email = email;
            this.phoneNumber = phoneNumber;
            this.createdDate = createdDate;
            this.isActive = isActive;
            this.addresses = addresses;
            this.walletBalance = walletBalance;
            this.transactions = transactions;
            this.bookings = bookings;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public LocalDateTime getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(LocalDateTime createdDate) {
            this.createdDate = createdDate;
        }

        public boolean isActive() {
            return isActive;
        }

        public void setActive(boolean active) {
            isActive = active;
        }

        public List<CustomerAddressDTO> getAddresses() {
            return addresses;
        }

        public void setAddresses(List<CustomerAddressDTO> addresses) {
            this.addresses = addresses;
        }

        public BigDecimal getWalletBalance() {
            return walletBalance;
        }

        public void setWalletBalance(BigDecimal walletBalance) {
            this.walletBalance = walletBalance;
        }

        public List<WalletTransactionDTO> getTransactions() {
            return transactions;
        }

        public void setTransactions(List<WalletTransactionDTO> transactions) {
            this.transactions = transactions;
        }

        public List<BookingDTO> getBooking () {
            return bookings;
        }

        public void setBooking (List<BookingDTO> bookings) {
            this.bookings = bookings;
        }
    }

    // 9. CustomerListItemDTO
    public static class CustomerListItemDTO {
        private String id;
        private String fullName;
        private String email;
        private String phoneNumber;
        private LocalDateTime createdDate;
        private boolean isActive;

        public CustomerListItemDTO() {}
        public CustomerListItemDTO(String id, String fullName, String email,
                                   String phoneNumber, LocalDateTime createdDate,
                                   boolean isActive) {
            this.id = id;
            this.fullName = fullName;
            this.email = email;
            this.phoneNumber = phoneNumber;
            this.createdDate = createdDate;
            this.isActive = isActive;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public LocalDateTime getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(LocalDateTime createdDate) {
            this.createdDate = createdDate;
        }

        public boolean isActive() {
            return isActive;
        }

        public void setActive(boolean active) {
            isActive = active;
        }
    }

    // 10. PaymentDTO
    public class PaymentDTO {
        private Integer paymentId;
        private int bookingId;
        private BigDecimal amount;
        private PaymentType paymentMethod;
        private String paymentStatus;
        private String transactionId;
        private LocalDateTime createdAt;

        public PaymentDTO() {}
        public PaymentDTO(Integer paymentId, int bookingId, BigDecimal amount,
                          PaymentType paymentMethod, String paymentStatus,
                          String transactionId, LocalDateTime createdAt) {
            this.paymentId = paymentId;
            this.bookingId = bookingId;
            this.amount = amount;
            this.paymentMethod = paymentMethod;
            this.paymentStatus = paymentStatus;
            this.transactionId = transactionId;
            this.createdAt = createdAt;
        }

        public Integer getPaymentId() {
            return paymentId;
        }

        public void setPaymentId(Integer paymentId) {
            this.paymentId = paymentId;
        }

        public int getBookingId() {
            return bookingId;
        }

        public void setBookingId(int bookingId) {
            this.bookingId = bookingId;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }

        public PaymentType getPaymentMethod() {
            return paymentMethod;
        }

        public void setPaymentMethod(PaymentType paymentMethod) {
            this.paymentMethod = paymentMethod;
        }

        public String getPaymentStatus() {
            return paymentStatus;
        }

        public void setPaymentStatus(String paymentStatus) {
            this.paymentStatus = paymentStatus;
        }

        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
        }
    }

    // 11. ResendEmailModel
    public class ResendEmailModel {
        private String email;

        public ResendEmailModel() {}
        public ResendEmailModel(String email) { this.email = email; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }

    // 12. ResetPasswordModel
    public class ResetPasswordModel {
        private String userId;
        private String token;
        private String newPassword;

        public ResetPasswordModel() {}
        public ResetPasswordModel(String userId, String token, String newPassword) {
            this.userId = userId;
            this.token = token;
            this.newPassword = newPassword;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getNewPassword() {
            return newPassword;
        }

        public void setNewPassword(String newPassword) {
            this.newPassword = newPassword;
        }
    }

    // 13. ServicePriceDTO
    public class ServicePriceDTO {
        private int priceId;
        private BigDecimal price;
        private int durationId;
        private String squareMeterSpecific;
        private int durationTime;
        private int serviceId;
        private String serviceName;
        private String serviceDescription;

        public ServicePriceDTO() {}
        public ServicePriceDTO(int priceId, BigDecimal price, int durationId,
                               String squareMeterSpecific, int durationTime,
                               int serviceId, String serviceName,
                               String serviceDescription) {
            this.priceId = priceId;
            this.price = price;
            this.durationId = durationId;
            this.squareMeterSpecific = squareMeterSpecific;
            this.durationTime = durationTime;
            this.serviceId = serviceId;
            this.serviceName = serviceName;
            this.serviceDescription = serviceDescription;
        }

        public int getPriceId() {
            return priceId;
        }

        public void setPriceId(int priceId) {
            this.priceId = priceId;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public int getDurationId() {
            return durationId;
        }

        public void setDurationId(int durationId) {
            this.durationId = durationId;
        }

        public String getSquareMeterSpecific() {
            return squareMeterSpecific;
        }

        public void setSquareMeterSpecific(String squareMeterSpecific) {
            this.squareMeterSpecific = squareMeterSpecific;
        }

        public int getDurationTime() {
            return durationTime;
        }

        public void setDurationTime(int durationTime) {
            this.durationTime = durationTime;
        }

        public int getServiceId() {
            return serviceId;
        }

        public void setServiceId(int serviceId) {
            this.serviceId = serviceId;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public String getServiceDescription() {
            return serviceDescription;
        }

        public void setServiceDescription(String serviceDescription) {
            this.serviceDescription = serviceDescription;
        }
    }

    // 14. UpdateFeedbackDTO
    public class UpdateFeedbackDTO {
        private Double rating;
        private String content;

        public UpdateFeedbackDTO() {}
        public UpdateFeedbackDTO(Double rating, String content) {
            this.rating = rating;
            this.content = content;
        }

        public Double getRating() {
            return rating;
        }

        public void setRating(Double rating) {
            this.rating = rating;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    // 15. UserVoucherDTO
    public class UserVoucherDTO {
        private int userVoucherId;
        private int voucherId;
        private String voucherCode;
        private String description;
        private BigDecimal discountPercentage;
        private LocalDate expireDate;

        public UserVoucherDTO() {}
        public UserVoucherDTO(int userVoucherId, int voucherId, String voucherCode,
                              String description, BigDecimal discountPercentage,
                              LocalDate expireDate) {
            this.userVoucherId = userVoucherId;
            this.voucherId = voucherId;
            this.voucherCode = voucherCode;
            this.description = description;
            this.discountPercentage = discountPercentage;
            this.expireDate = expireDate;
        }

        public int getUserVoucherId() {
            return userVoucherId;
        }

        public void setUserVoucherId(int userVoucherId) {
            this.userVoucherId = userVoucherId;
        }

        public int getVoucherId() {
            return voucherId;
        }

        public void setVoucherId(int voucherId) {
            this.voucherId = voucherId;
        }

        public String getVoucherCode() {
            return voucherCode;
        }

        public void setVoucherCode(String voucherCode) {
            this.voucherCode = voucherCode;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public BigDecimal getDiscountPercentage() {
            return discountPercentage;
        }

        public void setDiscountPercentage(BigDecimal discountPercentage) {
            this.discountPercentage = discountPercentage;
        }

        public LocalDate getExpireDate() {
            return expireDate;
        }

        public void setExpireDate(LocalDate expireDate) {
            this.expireDate = expireDate;
        }
    }

    // 16. UserWalletDTO
    public class UserWalletDTO {
        private int walletId;
        private String userId;
        private String userFullName;
        private BigDecimal balance;
        private LocalDateTime updatedAt;

        public UserWalletDTO() {}
        public UserWalletDTO(int walletId, String userId, String userFullName,
                             BigDecimal balance, LocalDateTime updatedAt) {
            this.walletId = walletId;
            this.userId = userId;
            this.userFullName = userFullName;
            this.balance = balance;
            this.updatedAt = updatedAt;
        }

        public int getWalletId() {
            return walletId;
        }

        public void setWalletId(int walletId) {
            this.walletId = walletId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserFullName() {
            return userFullName;
        }

        public void setUserFullName(String userFullName) {
            this.userFullName = userFullName;
        }

        public BigDecimal getBalance() {
            return balance;
        }

        public void setBalance(BigDecimal balance) {
            this.balance = balance;
        }

        public LocalDateTime getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
        }
    }

    // 17. VoucherDTO
    public class VoucherDTO {
        private int voucherId;
        private String description;
        private BigDecimal discountPercentage;
        private LocalDateTime createdAt;
        private LocalDate expireDate;
        private String voucherCode;
        private boolean isEventVoucher;
        private String createdBy;
        private VoucherStatus status;

        public VoucherDTO() {}
        public VoucherDTO(int voucherId, String description, BigDecimal discountPercentage,
                          LocalDateTime createdAt, LocalDate expireDate, String voucherCode,
                          boolean isEventVoucher, String createdBy, VoucherStatus status) {
            this.voucherId = voucherId;
            this.description = description;
            this.discountPercentage = discountPercentage;
            this.createdAt = createdAt;
            this.expireDate = expireDate;
            this.voucherCode = voucherCode;
            this.isEventVoucher = isEventVoucher;
            this.createdBy = createdBy;
            this.status = status;
        }

        public int getVoucherId() {
            return voucherId;
        }

        public void setVoucherId(int voucherId) {
            this.voucherId = voucherId;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public BigDecimal getDiscountPercentage() {
            return discountPercentage;
        }

        public void setDiscountPercentage(BigDecimal discountPercentage) {
            this.discountPercentage = discountPercentage;
        }

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
        }

        public LocalDate getExpireDate() {
            return expireDate;
        }

        public void setExpireDate(LocalDate expireDate) {
            this.expireDate = expireDate;
        }

        public String getVoucherCode() {
            return voucherCode;
        }

        public void setVoucherCode(String voucherCode) {
            this.voucherCode = voucherCode;
        }

        public boolean isEventVoucher() {
            return isEventVoucher;
        }

        public void setEventVoucher(boolean eventVoucher) {
            isEventVoucher = eventVoucher;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public VoucherStatus getStatus() {
            return status;
        }

        public void setStatus(VoucherStatus status) {
            this.status = status;
        }
    }

    // 18. WalletTransactionDTO
    public static class WalletTransactionDTO {
        private int transactionId;
        private BigDecimal amount;
        private TransactionType transactionType;
        private String description;
        private LocalDateTime createdAt;

        public WalletTransactionDTO() {}
        public WalletTransactionDTO(int transactionId, BigDecimal amount,
                                    TransactionType transactionType,
                                    String description, LocalDateTime createdAt) {
            this.transactionId = transactionId;
            this.amount = amount;
            this.transactionType = transactionType;
            this.description = description;
            this.createdAt = createdAt;
        }

        public int getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(int transactionId) {
            this.transactionId = transactionId;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }

        public TransactionType getTransactionType() {
            return transactionType;
        }

        public void setTransactionType(TransactionType transactionType) {
            this.transactionType = transactionType;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
        }
    }

}
