package com.costumestore.userservice.mapper;

import com.costumestore.userservice.dto.response.AddressResponse;
import com.costumestore.userservice.dto.response.FullNameResponse;
import com.costumestore.userservice.dto.response.UserResponse;
import com.costumestore.userservice.entity.Customer;
import com.costumestore.userservice.entity.Manager;
import com.costumestore.userservice.entity.Staff;
import com.costumestore.userservice.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toResponse(User user) {
        if (user == null) return null;

        UserResponse.UserResponseBuilder builder = UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername());

        if (user.getAddress() != null) {
            builder.address(AddressResponse.builder()
                    .id(user.getAddress().getId())
                    .city(user.getAddress().getCity())
                    .street(user.getAddress().getStreet())
                    .build());
        }

        if (user.getFullName() != null) {
            builder.fullName(FullNameResponse.builder()
                    .id(user.getFullName().getId())
                    .firstName(user.getFullName().getFirstName())
                    .lastName(user.getFullName().getLastName())
                    .build());
        }

        if (user instanceof Manager manager) {
            builder.userType("MANAGER")
                    .salary(manager.getSalary())
                    .position(manager.getPosition())
                    .managerCode(manager.getManagerCode())
                    .title(manager.getTitle());
        } else if (user instanceof Staff staff) {
            builder.userType("STAFF")
                    .salary(staff.getSalary())
                    .position(staff.getPosition());
        } else if (user instanceof Customer customer) {
            builder.userType("CUSTOMER")
                    .rewardPoint(customer.getRewardPoint())
                    .customerRanking(customer.getCustomerRanking());
        } else {
            builder.userType("USER");
        }

        return builder.build();
    }
}
