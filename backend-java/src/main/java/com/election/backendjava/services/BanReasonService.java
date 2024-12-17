package com.election.backendjava.services;

import com.election.backendjava.entities.BanReason;
import com.election.backendjava.repositories.BanReasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BanReasonService {

    @Autowired
    private BanReasonRepository banReasonRepository;

    public List<BanReason> getAllBanReasons() {
        return banReasonRepository.findAll();
    }

    public void saveBanReason(BanReason banReason) {
        banReasonRepository.save(banReason);
    }
}
