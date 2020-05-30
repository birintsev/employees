package ua.edu.sumdu.employees.service;

import org.springframework.stereotype.Service;

@Service
public interface TokenGenerator {
    String generate();
}
