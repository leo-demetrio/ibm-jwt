package ibm.grupo2.helloBank.service.impl;

import ibm.grupo2.helloBank.Models.Customer;
import ibm.grupo2.helloBank.data.DetailsCustomer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DetailCustomerServiceImpl implements UserDetailsService {

    private final CustomerServiceImpl service;
    @Override
    public UserDetails loadUserByUsername(String customerName) throws UsernameNotFoundException {
        Optional<Customer> customerOptional = service.findByName(customerName);
        return new DetailsCustomer(customerOptional);
    }
}
