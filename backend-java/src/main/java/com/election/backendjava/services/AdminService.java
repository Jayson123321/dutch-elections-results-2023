package com.election.backendjava.services;

import com.election.backendjava.Admin;
import com.election.backendjava.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    // Repository toegevoegd. Nodig als je data wilt ophalen uit de database.
    @Autowired
    private AdminRepository adminRepository;


    // Dus door de naamgevind 'getAllAdmins' weet Spring dat het alle data van Admins uit de database moet halen. Door findAll te gebruiken haal je alle data op.
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }
}