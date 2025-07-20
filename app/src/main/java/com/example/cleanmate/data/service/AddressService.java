package com.example.cleanmate.data.service;


import com.example.cleanmate.data.model.CustomerAddress;
import com.example.cleanmate.data.model.dto.*;
import com.example.cleanmate.data.repository.AddressRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AddressService {
    private final AddressRepository repo;

    public AddressService(AddressRepository repo) {
        this.repo = repo;
    }

    /**
     * Add or update an address, respecting "default" semantics.
     */
    public CustomerAddress addNewAddress(dto.CustomerAddressDTO dto) throws SQLException {
        // 1) Reset existing defaults if needed
        List<CustomerAddress> inUse = repo.getAddressesInUseByCustomerId(dto.getUserId());
        if (dto.isDefault()) {
            for (CustomerAddress addr : inUse) {
                if (addr.getIsdefault()) {
                    addr.setIsdefault(false);
                    repo.editAddress(addr);
                }
            }
        }

        // 2) Find by placeId
        CustomerAddress existing = inUse.stream()
                .filter(a -> a.getGgPlaceid().equals(dto.getGgPlaceId()))
                .findFirst().orElse(null);

        if (existing != null) {
            // update its fields
            existing.setGgFormattedaddress(dto.getGgFormattedAddress());
            existing.setGgDispalyname(dto.getGgDispalyName());
            existing.setAddressno(dto.getAddressNo());
            existing.setIsdefault(dto.isDefault());
            existing.setLatitude(dto.getLatitude());
            existing.setLongitude(dto.getLongitude());
            return repo.editAddress(existing);
        }

        // 3) Insert new
        CustomerAddress c = new CustomerAddress();
        c.setUserid(dto.getUserId());
        c.setGgFormattedaddress(dto.getGgFormattedAddress());
        c.setGgDispalyname(dto.getGgDispalyName());
        c.setGgPlaceid(dto.getGgPlaceId());
        c.setAddressno(dto.getAddressNo());
        c.setIsinuse(true);
        c.setIsdefault(dto.isDefault());
        c.setLatitude(dto.getLatitude());
        c.setLongitude(dto.getLongitude());
        return repo.addAddress(c);
    }

    /**
     * “Edit” here means mark old as inactive and insert a new one.
     */
    public CustomerAddress editAddress(dto.CustomerAddressDTO dto) throws SQLException {
        CustomerAddress old = repo.getAddressByAddressId(dto.getAddressId());
        if (old != null) {
            old.setIsinuse(false);
            repo.editAddress(old);
        }

        if (dto.isDefault()) {
            // reset defaults
            for (CustomerAddress addr : repo.getAddressesInUseByCustomerId(dto.getUserId())) {
                if (addr.getIsdefault()) {
                    addr.setIsdefault(false);
                    repo.editAddress(addr);
                }
            }
        }

        CustomerAddress c = new CustomerAddress();
        c.setUserid(dto.getUserId());
        c.setGgFormattedaddress(dto.getGgFormattedAddress());
        c.setGgDispalyname(dto.getGgDispalyName());
        c.setGgPlaceid(dto.getGgPlaceId());
        c.setAddressno(dto.getAddressNo());
        c.setIsinuse(true);
        c.setIsdefault(dto.isDefault());
        c.setLatitude(dto.getLatitude());
        c.setLongitude(dto.getLongitude());
        return repo.addAddress(c);
    }

    /** Returns DTOs for all “in use” addresses */
    public List<dto.CustomerAddressDTO> getAddressesInUse(String userId) throws SQLException {
        List<CustomerAddress> list = repo.getAddressesInUseByCustomerId(userId);
        return list.stream()
                .map(a -> {
                    dto.CustomerAddressDTO dto = new dto.CustomerAddressDTO();
                    dto.setAddressId(a.getAddressid());
                    dto.setUserId(a.getUserid());
                    dto.setGgFormattedAddress(a.getGgFormattedaddress());
                    dto.setGgDispalyName(a.getGgDispalyname());
                    dto.setGgPlaceId(a.getGgPlaceid());
                    dto.setAddressNo(a.getAddressno());
                    dto.setInUse(a.getIsinuse());
                    dto.setDefault(a.getIsdefault());
                    dto.setLatitude(a.getLatitude());
                    dto.setLongitude(a.getLongitude());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}

