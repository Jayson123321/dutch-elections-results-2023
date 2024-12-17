package com.election.backendjava.controllers;

import com.election.backendjava.entities.BanReason;
import com.election.backendjava.services.BanReasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/ban-reasons")
public class BanReasonController {

    @Autowired
    private BanReasonService banReasonService;

    @GetMapping
    public List<BanReason> getAllBanReasons() {
        return banReasonService.getAllBanReasons();
    }

    @PostMapping
    public void addBanReason(@RequestBody BanReason banReason) {
        banReasonService.saveBanReason(banReason);
    }
}
