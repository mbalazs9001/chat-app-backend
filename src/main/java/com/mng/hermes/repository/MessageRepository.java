package com.mng.hermes.repository;

import com.mng.hermes.model.Message;
import com.mng.hermes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Integer> {

    List<Message> findAllByTargetAddressEquals(String targetAddress);
    List<Message> findAllByTargetAddressIsNullAndTypeIsNull();
    List<Message> findAllByTargetAddressAndOwner(String targetAddress, User ownerId);

}
