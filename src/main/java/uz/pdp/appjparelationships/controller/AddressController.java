package uz.pdp.appjparelationships.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appjparelationships.entity.Address;
import uz.pdp.appjparelationships.repository.AddressRepository;

import java.util.List;

@RestController
@RequestMapping(value = "/address")
public class AddressController {
    @Autowired
    AddressRepository addressRepository;

    @PostMapping("/addAddress")
    public String addAddress(@RequestBody Address address) {
        addressRepository.save(address);
        return "Added address";
    }
    @GetMapping("/getAddress")
    public List<Address> getAddress(){
        return addressRepository.findAll();
    }
}
