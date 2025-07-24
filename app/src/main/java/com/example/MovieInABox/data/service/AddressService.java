package com.example.MovieInABox.data.service;


import com.example.MovieInABox.data.model.CustomerAddress;
import com.example.MovieInABox.data.model.dto.*;
import com.example.MovieInABox.data.repository.AddressRepository;

import java.sql.SQLException;
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
                if (addr.getIsDefault()) {
                    addr.setIsDefault(false);
                    repo.editAddress(addr);
                }
            }
        }

        // 2) Find by placeId
        CustomerAddress existing = inUse.stream()
                .filter(a -> a.getGgPlaceId().equals(dto.getGgPlaceId()))
                .findFirst().orElse(null);

        if (existing != null) {
            // update its fields
            existing.setGgFormattAdaddress(dto.getGgFormattedAddress());
            existing.setGgDispalyName(dto.getGgDispalyName());
            existing.setAddressNo(dto.getAddressNo());
            existing.setIsDefault(dto.isDefault());
            existing.setLatitude(dto.getLatitude());
            existing.setLongitude(dto.getLongitude());
            return repo.editAddress(existing);
        }

        // 3) Insert new
        CustomerAddress c = new CustomerAddress();
        c.setUserId(dto.getUserId());
        c.setGgFormattAdaddress(dto.getGgFormattedAddress());
        c.setGgDispalyName(dto.getGgDispalyName());
        c.setGgPlaceId(dto.getGgPlaceId());
        c.setAddressNo(dto.getAddressNo());
        c.setIsInUse(true);
        c.setIsDefault(dto.isDefault());
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
            old.setIsInUse(false);
            repo.editAddress(old);
        }

        if (dto.isDefault()) {
            // reset defaults
            for (CustomerAddress addr : repo.getAddressesInUseByCustomerId(dto.getUserId())) {
                if (addr.getIsDefault()) {
                    addr.setIsDefault(false);
                    repo.editAddress(addr);
                }
            }
        }

        CustomerAddress c = new CustomerAddress();
        c.setUserId(dto.getUserId());
        c.setGgFormattAdaddress(dto.getGgFormattedAddress());
        c.setGgDispalyName(dto.getGgDispalyName());
        c.setGgPlaceId(dto.getGgPlaceId());
        c.setAddressNo(dto.getAddressNo());
        c.setIsInUse(true);
        c.setIsDefault(dto.isDefault());
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
                    dto.setAddressId(a.getAddressId());
                    dto.setUserId(a.getUserId());
                    dto.setGgFormattedAddress(a.getGgFormattAdaddress());
                    dto.setGgDispalyName(a.getGgDispalyName());
                    dto.setGgPlaceId(a.getGgPlaceId());
                    dto.setAddressNo(a.getAddressNo());
                    dto.setInUse(a.getIsInUse());
                    dto.setDefault(a.getIsDefault());
                    dto.setLatitude(a.getLatitude());
                    dto.setLongitude(a.getLongitude());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}

